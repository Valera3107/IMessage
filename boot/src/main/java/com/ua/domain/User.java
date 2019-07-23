package com.ua.domain;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import java.util.*;


@Entity
@Table
public class User implements UserDetails{

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column
  @NotBlank(message = "User name can not be empty!")
  private String name;

  @Column
  @NotBlank(message = "User username can not be empty!")
  private String username;

  @Column
  @NotBlank(message = "User password can not be empty!")
  private String password;

  @Column
  private boolean active;

  @Column
  @Email(message = "Email is not correct")
  @NotBlank(message = "Email is empty")
  private String email;

  @Column
  private String activationCode;

  @ElementCollection(targetClass = Role.class, fetch = FetchType.EAGER)
  @CollectionTable(name = "user_role", joinColumns = @JoinColumn(name = "user_id"))
  @Enumerated(EnumType.STRING)
  private Set<Role> roles;

  @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
  private List<Message> messages;

  @ManyToMany
  @JoinTable(name = "user_subscriptions", joinColumns = {
    @JoinColumn(name = "channel_id")},
    inverseJoinColumns = {@JoinColumn(name = "subscriber_id")})
  private Set<User> subscribers = new HashSet<>();

  @ManyToMany
  @JoinTable(name = "user_subscriptions", joinColumns = {
    @JoinColumn(name = "subscriber_id")},
    inverseJoinColumns = {@JoinColumn(name = "channel_id")})
  private Set<User> subscriptions= new HashSet<>();

  public Set<User> getSubscribers() {
    return subscribers;
  }

  public void setSubscribers(Set<User> subscribers) {
    this.subscribers = subscribers;
  }

  public Set<User> getSubscriptions() {
    return subscriptions;
  }

  public void setSubscriptions(Set<User> subscriptions) {
    this.subscriptions = subscriptions;
  }

  public User() {
  }

  public User(String name, String username, String password) {
    this.name = name;
    this.username = username;
    this.password = password;
  }

  public boolean isAdmin(){
    return roles.contains(Role.ADMIN);
  }

  public Long getId() {
    return id;
  }

  public String getUsername() {
    return username;
  }

  @Override
  public boolean isAccountNonExpired() {
    return true;
  }

  @Override
  public boolean isAccountNonLocked() {
    return true;
  }

  @Override
  public boolean isCredentialsNonExpired() {
    return true;
  }

  @Override
  public boolean isEnabled() {
    return isActive();
  }

  public void setUsername(String username) {
    this.username = username;
  }

  @Override
  public Collection<? extends GrantedAuthority> getAuthorities() {
    return getRoles();
  }

  public String getPassword() {
    return password;
  }

  public void setPassword(String password) {
    this.password = password;
  }

  public boolean isActive() {
    return active;
  }

  public void setActive(boolean active) {
    this.active = active;
  }

  public Set<Role> getRoles() {
    return roles;
  }

  public void setRoles(Set<Role> roles) {
    this.roles = roles;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public String getActivationCode() {
    return activationCode;
  }

  public void setActivationCode(String activationCode) {
    this.activationCode = activationCode;
  }

  public List<Message> getMessages() {
    return messages;
  }

  public void setMessages(List<Message> messages) {
    this.messages = messages;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (!(o instanceof User)) return false;
    User user = (User) o;
    return isActive() == user.isActive() &&
      Objects.equals(getId(), user.getId()) &&
      Objects.equals(getName(), user.getName()) &&
      Objects.equals(getUsername(), user.getUsername()) &&
      Objects.equals(getPassword(), user.getPassword()) &&
      Objects.equals(getEmail(), user.getEmail()) &&
      Objects.equals(getActivationCode(), user.getActivationCode()) &&
      Objects.equals(getRoles(), user.getRoles()) &&
      Objects.equals(getMessages(), user.getMessages());
  }

  @Override
  public int hashCode() {
    return Objects.hash(getId(), getName(), getUsername(), getPassword(), isActive(), getEmail(), getActivationCode(), getRoles(), getMessages());
  }

  @Override
  public String toString() {
    return "User{" +
      "id=" + id +
      ", name='" + name + '\'' +
      ", username='" + username + '\'' +
      ", password='" + password + '\'' +
      ", active=" + active +
      ", email='" + email + '\'' +
      ", activationCode='" + activationCode + '\'' +
      ", roles=" + roles +
      '}';
  }
}
