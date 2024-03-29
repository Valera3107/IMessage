package com.ua.controller;

import com.ua.domain.User;
import com.ua.domain.Role;
import com.ua.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@Controller
@RequestMapping("/user")
public class UserController {

  @Autowired
  private UserService userService;

  @PreAuthorize("hasAuthority('ADMIN')")
  @GetMapping
  public String userList(Model model){
    model.addAttribute("users", userService.findAll());
    return "userList";
  }

  @PreAuthorize("hasAuthority('ADMIN')")
  @GetMapping("{user}")
  public String editUser(@PathVariable User user, Model model){
    model.addAttribute("user", user);
    model.addAttribute("roles", Role.values());
    return "userEdit";
  }

  @PreAuthorize("hasAuthority('ADMIN')")
  @PostMapping
  public String userSave(@RequestParam String name,
                         @RequestParam String username,
                         @RequestParam("id") User user,
                         @RequestParam Map<String, Object> model){

    userService.saveUser(name, username, user, model);

    return "redirect:/user";
  }

  @GetMapping("profile")
  public String getProfile(Model model, @AuthenticationPrincipal User user){
    model.addAttribute("name", user.getName());
    model.addAttribute("username", user.getUsername());
    model.addAttribute("email", user.getEmail());
    return "profile";
  }

  @PostMapping("profile")
  public String updateProfile(
    @AuthenticationPrincipal User user,
    @RequestParam String name,
    @RequestParam String username,
    @RequestParam String email){

    userService.updateProfile(user, name, username, email);

    return "redirect:/user/profile";
  }
}
