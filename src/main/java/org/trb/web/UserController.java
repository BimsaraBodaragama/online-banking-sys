package org.trb.web;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.trb.model.User;
import org.trb.repository.UserRepository;
import org.trb.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.trb.utils.PasswordUpdater;

import java.security.Principal;
import java.security.SecureRandom;
import java.util.Optional;

@Controller
@RequestMapping("/user")
public class UserController {

    Logger log = LoggerFactory.getLogger(UserController.class);

    @Autowired
    private UserService userService;

    @Autowired
    private UserRepository userrepository;

    private static final String SALT = "salt"; // Salt should be protected carefully

    @RequestMapping(value = "/profile", method = RequestMethod.GET)
    public String profile(Principal principal, Model model) {
        User user = userService.findByUsername(principal.getName());
        PasswordUpdater passwordUpdater = new PasswordUpdater();

        model.addAttribute("user", user);
        model.addAttribute("passwordUpdater", passwordUpdater);

        return "profile";
    }

    @RequestMapping(value = "/profile", method = RequestMethod.POST)
    public String profilePost(@ModelAttribute("user") User newUser, Model model) {
        log.info("bbbbb");
        Optional<User> userOptional = userService.findByID(newUser.getUserId());

        if (!userOptional.isPresent()){
            log.info("qqqqq");
        }
        User user = userOptional.get();

        log.info("ddddd");
        user.setUsername(newUser.getUsername());
        log.info("eeee");
        user.setFirstName(newUser.getFirstName());
        log.info("ffff");
        user.setLastName(newUser.getLastName());
        log.info("ggggg");
        user.setEmail(newUser.getEmail());
        log.info("hhhh");
        user.setPhone(newUser.getPhone());
        log.info("iiii");

        model.addAttribute("user", user);
        log.info("jjjjj");
        userService.saveUser(user);
        log.info("kkkkk");

        return "profile";
    }

    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(12, new SecureRandom(SALT.getBytes()));
    }

    @RequestMapping(value = "/profile-change-password", method = RequestMethod.GET)
    public String profileChangePasswordGet(@ModelAttribute("passwordUpdater") PasswordUpdater passwordUpdater, BindingResult result, Model model) {
        return "profile";
    }

        @RequestMapping(value = "/profile-change-password", method = RequestMethod.POST)
    public String profileChangePasswordPost(@ModelAttribute("passwordUpdater") PasswordUpdater passwordUpdater, BindingResult result, Model model) {

        log.info("AAAAAAAAAAAAAAAAAAAAAAA");

        long userId = passwordUpdater.getUserId();
        String oldPassword = passwordUpdater.getOldPassword();
        String newPassword = passwordUpdater.getNewPassword();
        String confirmedNewPassword = passwordUpdater.getConfirmedNewPassword();

        if(!newPassword.equals(confirmedNewPassword)){
            log.info("New Password Mismatched");
            result.addError(new FieldError("passwordUpdater",
                    "confirmedNewPassword", "New Password Mismatched"));
            model.addAttribute("msg", "New Password Mismatched");
            return "profile";
        }

        BCryptPasswordEncoder bCryptPasswordEncoder = passwordEncoder();
        String encryptedOldPassword = bCryptPasswordEncoder.encode(oldPassword);

        Optional<User> byId = userrepository.findById(userId);
        if (!byId.isPresent()){
            log.info("Unknown Error. User not found.");
            model.addAttribute("msg", "Unknown Error Occurred: User NOt Found" +
                    ". Please contact the bank.");
            return "profile";
        }

        User user = byId.get();

        if(!user.getPassword().equals(oldPassword)){
            log.info("Incorrect Old Password");
            result.addError(new FieldError("passwordUpdater",
                    "oldPassword", "Incorrect Old Password"));
            model.addAttribute("msg", "Incorrect Old Password");
            return "profile";
        }

        String encryptedNewPassword = bCryptPasswordEncoder.encode(newPassword);

        user.setPassword(encryptedNewPassword);

        userService.saveUser(user);

        return "profile";
    }




}

