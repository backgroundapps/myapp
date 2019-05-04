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
        log.info("\n --- reigstration view new user --- \n");
        model.addAttribute("userForm", new User());
        log.info(" \n --- \n ");
        return "registration";
    }

    @GetMapping("/welcome")  
    public String welcome(Model model) {
        return "welcome";
    }

    @PostMapping("/registration")
    public String registration(@Valid @ModelAttribute("userForm") User userForm, BindingResult bindingResult) {
        log.info("\n --- reigstration view add user --- \n");
        log.info(" --- validating ---");
        userValidator.validate(userForm, bindingResult);
        
        if(bindingResult.hasErrors()){
            log.info(" --- not valid --- ");
            log.info("Errors: " + bindingResult.getErrorCount() + " obj: " + bindingResult.getObjectName());
            log.info(String.format("\n ---User > name: %s, pass: %s, confirm: %s --- \n", userForm.getUsername(), userForm.getPassword(), userForm.getPasswordConfirm()));
            return "registration";
        }
        log.info("--- saving user ---");
        userService.save(userForm);
        log.info("--- user saved --- ");
        securityService.autoLogin(userForm.getUsername(), userForm.getPassword());
        return "redirect:/welcome";
    }
}