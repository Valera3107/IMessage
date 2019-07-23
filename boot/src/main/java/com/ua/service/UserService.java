package com.ua.service;

import com.ua.domain.User;
import com.ua.domain.Role;
import com.ua.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.*;
import java.util.Collections;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class UserService implements UserDetailsService{

  @Autowired
  private UserRepository userRepository;

  @Autowired
  private MailService mailService;

  @Autowired
  private PasswordEncoder passwordEncoder;

  @Override
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

    Optional<User> byUsername = userRepository.findByUsername(username);
    if(!byUsername.isPresent()){
      throw new UsernameNotFoundException("User not found!");
    }

    return byUsername.get();
  }

  public boolean addUser(User user){
    Optional<User> man = userRepository.findByUsername(user.getUsername());

    if(man.isPresent()){
      return false;
    }

    user.setActive(true);
    user.setRoles(Collections.singleton(Role.USER));
    user.setActivationCode(UUID.randomUUID().toString());
    user.setPassword(passwordEncoder.encode(user.getPassword()));

    sendMessage(user);

    userRepository.save(user);
    return true;
  }

  public boolean activateUser(String code) {
    User user = userRepository.findByActivationCode(code);

    if(user ==null){
      return false;
    }

    user.setActivationCode(null);
    userRepository.save(user);

    return true;
  }

  public List<User> findAll() {
    return userRepository.findAll();
  }

  public void saveUser(String name, String username, User user, Map<String, Object> model) {
    Set<String> set = Arrays.stream(Role.values()).map(Enum::name).collect(Collectors.toSet());

    user.getRoles().clear();

    for(String key : model.keySet()){
      if(set.contains(key))
        user.getRoles().add(Role.valueOf(key));
    }

    user.setName(name);
    user.setUsername(username);
    userRepository.save(user);
  }

  public void updateProfile(User user, String name, String username, String email) {
    boolean isEmailChanged = (email!=null && !email.equals(user.getEmail())) ||
      (user.getEmail()!=null && !user.getEmail().equals(email));

    if(isEmailChanged){
      user.setEmail(email);

      if(!StringUtils.isEmpty(email)){
        user.setActivationCode(UUID.randomUUID().toString());
      }
    }

    if(!StringUtils.isEmpty(name)){
      user.setName(name);
    }

    if(!StringUtils.isEmpty(username)){
      user.setName(username);
    }

    userRepository.save(user);

    if(isEmailChanged)
      sendMessage(user);

  }

  private void sendMessage(User user) {
    if(!StringUtils.isEmpty(user.getEmail())){
      String message = String.format(
        "Hello, %s! \n" + "Welcome to IMessage. Please visit next link: http://localhost:8080/activate/%s ",
        user.getName(), user.getActivationCode()
      );

      mailService.send(user.getEmail(), "Activation code", message);
    }
  }

  public void subscribe(User currentUser, User user){
    user.getSubscribers().add(currentUser);
    userRepository.save(user);
  }

  public void unsubscribe(User currentUser, User user) {
    user.getSubscribers().removeIf(e->e.getUsername().equals(currentUser.getUsername()));
    userRepository.save(user);
  }
}
