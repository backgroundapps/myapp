package com.manollo.myapp.security;

import com.manollo.myapp.security.entity.User;
import com.manollo.myapp.security.service.UserService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

/**
 * UserValidator
 */
@Component
public class UserValidator implements Validator {
    private static final Logger log = LoggerFactory.getLogger(UserValidator.class);
    @Autowired
    private UserService userService;

    @Override
    public boolean supports(Class<?> clazz) {
        return User.class.equals(clazz);
    }

    @Override
    public void validate(Object o, Errors errors) {
        log.info("\n\n --- starting validation --- \n\n");
        log.info("\n  counting errors: " + errors.getErrorCount());
        User user = (User) o;
        log.info("\n --- user: " + user.toString());

        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "username", "NotEmpty");
        if (user.getUsername().length() < 3 || user.getUsername().length() > 32) {
            log.info("Size error username");
            errors.rejectValue("username", "Size.userForm.username");
        }
        if (userService.findByUsername(user.getUsername()) != null) {
            log.info("duplicated username");
            errors.rejectValue("username", "Duplicate.userForm.username");
        }

        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "password", "NotEmpty");
        if (user.getPassword().length() < 8 || user.getPassword().length() > 32) {
            log.info("erro size upassword");
            errors.rejectValue("password", "Size.userForm.password");
        }

        if (!user.getPasswordConfirm().equals(user.getPassword())) {
            log.info("error diff password");
            errors.rejectValue("passwordConfirm", "Diff.userForm.passwordConfirm");
        }
        log.info("\n  counting errors: " + errors.getErrorCount());
    }
}