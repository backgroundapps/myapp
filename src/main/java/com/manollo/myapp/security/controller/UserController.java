package com.manollo.myapp.security.controller;

import javax.validation.Valid;

import com.manollo.myapp.security.UserValidator;
import com.manollo.myapp.security.entity.User;
import com.manollo.myapp.security.service.SecurityService;
import com.manollo.myapp.security.service.UserService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * UserController
 */
@Controller
public class UserController {
    private static final Logger log = LoggerFactory.getLogger(UserController.class);

    @Autowired
    private UserService userService;
    
    @Autowired
    private SecurityService securityService;
    
    @Autowired
    private UserValidator userValidator;
    
    @GetMapping("username")
    @ResponseBody
    public String currentUserName(Authentication authentication) {
        return authentication.getName();
    }

    @GetMapping("/registration")
    public String registration(Model model) {
        model.addAttribute("userForm", new User());
        return "registration";
    }

    @GetMapping("/welcome")  
    public String welcome(Model model) {
        return "welcome";
    }

    @PostMapping("/registration")
    public String registration(@Valid @ModelAttribute("userForm") User userForm, BindingResult bindingResult) {
        userValidator.validate(userForm, bindingResult);
        
        if(bindingResult.hasErrors()){
            return "registration";
        }
        userService.save(userForm);
        securityService.autoLogin(userForm.getUsername(), userForm.getPasswordConfirm());
        return "redirect:/welcome";
    }
}