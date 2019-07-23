package com.ua.controller;

import com.ua.domain.User;
import com.ua.dto.CaptchaResponseDto;
import com.ua.service.UserService;
import org.hibernate.engine.internal.Collections;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.RestTemplate;

import javax.validation.Valid;
import java.util.*;

@Controller
public class RegistrationController {
  private final static String CAPTCHA_URL = "https://www.google.com/recaptcha/api/siteverify?secret=%s&response=%s";

  @Autowired
  private UserService userService;

  @Value("${recaptcha.secret}")
  private String secret;

  @Autowired
  private RestTemplate restTemplate;

  @GetMapping("/registration")
  public String openRegistration(){
    return "registration";
  }

  @PostMapping("/registration")
  public String addUser(
    @RequestParam("g-recaptcha-response") String captchaResponse,
    @RequestParam("confirmPassword") String confirmPassword,
    @Valid User user, BindingResult bindingResult, Model model){

    String url = String.format(CAPTCHA_URL, secret, captchaResponse);
    CaptchaResponseDto response = restTemplate.postForObject(url, java.util.Collections.emptyList(), CaptchaResponseDto.class);

    if(!response.isSuccess()) {
       model.addAttribute("captchaError", "Fill captcha");
    }


    if(StringUtils.isEmpty(confirmPassword)){
      model.addAttribute("confirmPasswordError", "You must confirm the password!");
    }

    boolean isConfirm = user.getPassword().equals(confirmPassword);

    if(user.getPassword()!=null && !isConfirm){
      model.addAttribute("passwordError", "Password are different!");
    }

    if(bindingResult.hasErrors() || !isConfirm || !response.isSuccess()){
      Map<String, String> errors = ControllerUtils.getErrors(bindingResult);
      model.mergeAttributes(errors);
      return "registration";
    }

    if(!userService.addUser(user)){
      model.addAttribute("usernameError", "User already exists!");
      return "registration";
    }

    return "redirect:/login";
  }

  @GetMapping("/activate/{code}")
  public String activate(Model model, @PathVariable String code){

    boolean isActivated = userService.activateUser(code);

    if(isActivated){
      model.addAttribute("messageType", "success");
      model.addAttribute("message", "User successfully activated!");
    } else {
      model.addAttribute("messageType", "danger");
      model.addAttribute("message", "Activation code is not found!");
    }

    return "login";
  }
}
