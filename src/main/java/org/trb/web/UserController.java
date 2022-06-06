package org.trb.web;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.trb.model.User;
import org.trb.repository.UserRepository;
import org.trb.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.trb.utils.AdminLock;
import org.trb.utils.PasswordUpdater;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import java.security.SecureRandom;

import java.security.Principal;
import java.util.Optional;

@Controller
@RequestMapping("/user")
public class UserController {

    private static final Logger log = LoggerFactory.getLogger(UserController.class);

    @Autowired
    private UserService userService;

    @Autowired
    private UserRepository userrepository;

    private static final String SALT = "salt"; // Salt should be protected carefully

    @RequestMapping(value = "/profile", method = RequestMethod.GET)
    public String profile(Principal principal, Model model) {
        User user = userService.findByUsername(principal.getName());
        PasswordUpdater passwordUpdater = new PasswordUpdater(user.getUserId());

        model.addAttribute("user", user);
        model.addAttribute("passwordUpdater", passwordUpdater);

        return "profile";
    }

    @RequestMapping(value = "/profile", method = RequestMethod.POST)
    public String profilePost(@ModelAttribute("user") User newUser, Model model) {
        User user = userService.findByUsername(newUser.getUsername());
        user.setUsername(newUser.getUsername());
        user.setFirstName(newUser.getFirstName());
        user.setLastName(newUser.getLastName());
        user.setEmail(newUser.getEmail());
        user.setPhone(newUser.getPhone());

        PasswordUpdater passwordUpdater = new PasswordUpdater(user.getUserId());

        model.addAttribute("user", user);
        model.addAttribute("passwordUpdater", passwordUpdater);

        userService.saveUser(user);

        return "profile";
    }

    @RequestMapping(value = "/profile/changePassword", method = RequestMethod.POST)
    public String profileChangePasswordPost(@ModelAttribute("passwordUpdater") PasswordUpdater newPasswordUpdater, Model model) {

        PasswordUpdater passwordUpdater = newPasswordUpdater;

        long userId = passwordUpdater.getUserId();
        String oldPassword = passwordUpdater.getOldPassword();
        String newPassword = passwordUpdater.getNewPassword();
        String confirmedNewPassword = passwordUpdater.getConfirmedNewPassword();

        Optional<User> byId = userrepository.findById(userId);
        if (!byId.isPresent()){
            log.info("Unknown Error. User not found.User Id: " + userId + " not found.");
            model.addAttribute("msg", "Unknown Error Occurred: User NOt Found" +
                    ". Please contact the bank.");
            PasswordUpdater passwordUpdaterReBound = new PasswordUpdater();
            model.addAttribute("passwordUpdater", passwordUpdaterReBound);
            return "profile";
        }
        User user = byId.get();

        if(!newPassword.equals(confirmedNewPassword)){
            log.info("New Password Mismatched");
            model.addAttribute("msg", "New Password Mismatched");
            PasswordUpdater passwordUpdaterReBound = new PasswordUpdater(userId);
            model.addAttribute("user", user);
            model.addAttribute("passwordUpdater", passwordUpdaterReBound);
            return "profile";
        }

        BCryptPasswordEncoder bCryptPasswordEncoder = passwordEncoder();
        /*String encryptedOldPassword = bCryptPasswordEncoder.encode(oldPassword);
        log.info("/////////////////===== " + encryptedOldPassword + " =============");*/

        if(!oldPassword.equals(intToString(user.getUserCode()))){
            log.info("Incorrect Old Password");
            model.addAttribute("msg", "Incorrect Old Password");
            PasswordUpdater passwordUpdaterReBound = new PasswordUpdater(userId);
            model.addAttribute("user", user);
            model.addAttribute("passwordUpdater", passwordUpdaterReBound);
            return "profile";
        }

        String encryptedNewPassword = bCryptPasswordEncoder.encode(newPassword);

        user.setUserCode(stringToInt(newPassword));
        user.setPassword(encryptedNewPassword);

        userService.saveUser(user);

        log.info("Password Changed successfully!");
        model.addAttribute("msg1", "Password Update Successful!");
        PasswordUpdater passwordUpdaterReBound = new PasswordUpdater(userId);
        model.addAttribute("user", user);
        model.addAttribute("passwordUpdater", passwordUpdaterReBound);
        return "profile";

    }

    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(12, new SecureRandom(SALT.getBytes()));
    }

    public boolean passwordChecker(PasswordUpdater passwordUpdater, User user) {

        BCryptPasswordEncoder bCryptPasswordEncoder = passwordEncoder();
        String encryptedAdminPassword = bCryptPasswordEncoder.encode(passwordUpdater.getOldPassword());

        log.info("OLDPASSWORD====" + encryptedAdminPassword + "====");

        User adminTRB = user;
        if(adminTRB.getPassword().equals(encryptedAdminPassword)){
            log.info("Matching Old Password.");
            return true;

        }

        log.info("Old Password Mismatched.");
        return false;

    }

    private String stringToInt(String string){
        int[] charInts = new int[string.length()];
        int i = 0;
        for(Character ch: string.toCharArray()){
            charInts[i] = ch;
            i++;
        }
        StringBuilder intString =new StringBuilder();
        for(int k: charInts){
            intString.append(k);
            intString.append(":");
        }
        intString.deleteCharAt(intString.length()-1);
        System.out.println(intString);
        return intString.toString();
    }

    private String intToString(String intString){
        String[] split = intString.split(":");
        StringBuilder convertedString = new StringBuilder();
        for(String s: split){
            Integer integerChar = Integer.valueOf(s);
            int intChar = integerChar;
            char c = (char) intChar;
            convertedString.append(c);
        }
        System.out.println(convertedString);
        return convertedString.toString();
    }

}
