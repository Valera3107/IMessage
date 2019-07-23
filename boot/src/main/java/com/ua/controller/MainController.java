package com.ua.controller;

import com.ua.domain.Message;
import com.ua.domain.User;
import com.ua.repository.MessageRepository;
import com.ua.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.io.IOException;
import java.util.Base64;
import java.util.List;
import java.util.Map;

@Controller
public class MainController {

  @Autowired
  private MessageRepository messageRepository;

  @Autowired
  private UserService userService;

  @Value("{upload.path}")
  private String uploadPath;

  @GetMapping("/")
  public String greeting(){
    return "greeting";
  }

  @GetMapping("/login")
  public String login(){
    return "login";
  }

  @GetMapping("/main")
  public String welcome(@AuthenticationPrincipal User user,
                        @RequestParam(required = false, defaultValue = "") String filter ,Model model) {
    List<Message> list = null;

    if("all".equals(filter) || "*".equals(filter) || filter==null || "".equals(filter))
      list =  messageRepository.findAll();
    else if(!filter.isEmpty())
      list = messageRepository.findByTag(filter);

    model.addAttribute("messages", list);
    model.addAttribute("filter", filter);
    return "main";
  }

  @PostMapping("/main")
  public String add(@AuthenticationPrincipal User user,
                    @Valid Message message,
                    BindingResult bindingResult,
                    Model model,
                    @RequestParam("image") MultipartFile file) throws IOException {

    if(bindingResult.hasErrors()){
      Map<String, String> errors = ControllerUtils.getErrors(bindingResult);
      model.mergeAttributes(errors);
      model.addAttribute("message", message);
    } else {

      if(file!=null && !file.getOriginalFilename().isEmpty())
        message.setFile(Base64.getEncoder().encodeToString(file.getBytes()));

      message.setUser(user);
      model.addAttribute("message", null);

      messageRepository.save(message);
    }

    model.addAttribute("messages", messageRepository.findAll());
    return "main";
  }

  @GetMapping("/user-messages/{user}")
  public String getUserMessages(
    @AuthenticationPrincipal User currentUser,
    @PathVariable User user,
    Model model,
    @RequestParam(required = false) Message mes
  ){

    List<Message> messages = user.getMessages();
    model.addAttribute("userChannel", user);
    model.addAttribute("subscriptionsCount", user.getSubscriptions().size());
    model.addAttribute("subscribersCount", user.getSubscribers().size());
    model.addAttribute("isSubscribe", user.getSubscribers().stream().anyMatch(e->e.getUsername().equals(currentUser.getUsername())));
    model.addAttribute("messages", messages);
    model.addAttribute("message", mes);
    model.addAttribute("isCurrentUser", user.getUsername().equals(currentUser.getUsername()));
    return "userMessage";
  }

  @PostMapping("/user-messages/{user}")
  public String saveMessage(
    @AuthenticationPrincipal User currentUser,
    @PathVariable Long user,
    @RequestParam("id") Message message,
    @RequestParam("text") String text,
    @RequestParam("tag") String tag,
    @RequestParam("image") MultipartFile file
  ) throws IOException {

    if(message.getUser().getUsername().equals(currentUser.getUsername())){
      if(!StringUtils.isEmpty(text)){
        message.setText(text);
      }

      if(!StringUtils.isEmpty(tag)){
        message.setTag(tag);
      }

      if(file!=null && !file.getOriginalFilename().isEmpty()){
        message.setFile(Base64.getEncoder().encodeToString(file.getBytes()));
      }

      messageRepository.save(message);
    }


    return "redirect:/user-messages/"+user;
  }


  @GetMapping("/user/subscribe/{user}")
  public String subscribe(
    @PathVariable User user,
    @AuthenticationPrincipal User currentUser
  ){
    userService.subscribe(currentUser, user);
    return "redirect:/user-messages/" + user.getId();
  }

  @GetMapping("/user/unsubscribe/{user}")
  public String unsubscribe(
    @PathVariable User user,
    @AuthenticationPrincipal User currentUser
  ){
    userService.unsubscribe(currentUser, user);
    return "redirect:/user-messages/" + user.getId();
  }

  @GetMapping("/user/{type}/{user}/list")
  public String userList(Model model, @PathVariable User user, @PathVariable String type){
    model.addAttribute("userChannel", user);
    model.addAttribute("type", type);

    if("subscriptions".equals(type)){
      model.addAttribute("users", user.getSubscriptions());
    } else {
      model.addAttribute("users", user.getSubscribers());
    }

    return "subscriptions";
  }
}
