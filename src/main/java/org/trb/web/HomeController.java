package org.trb.web;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.trb.repository.RoleRepository;
import org.trb.model.PrimaryAccount;
import org.trb.model.SavingsAccount;
import org.trb.model.User;
import org.trb.model.security.UserRole;
import org.trb.repository.UserRepository;
import org.trb.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.security.Principal;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Controller
public class HomeController {

    Logger log = LoggerFactory.getLogger(HomeController.class);
	
	@Autowired
	private UserService userService;
	
	@Autowired
    private RoleRepository rolerepository;

	@Autowired
    private UserRepository userRepository;
	
	@RequestMapping("/")
	public String home() {
		return "redirect:/index";
	}
	
	@RequestMapping("/index")
    public String index() {
        return "index";
    }
	
	@RequestMapping(value = "/signup", method = RequestMethod.GET)
    public String signup(Model model) {
        User user = new User();

        model.addAttribute("user", user);

        return "signup";
    }
	
	@RequestMapping(value = "/signup", method = RequestMethod.POST)
    public String signupPost(@ModelAttribute("user") User user,  Model model) {

	    PrimaryAccount primaryAccount = new PrimaryAccount();
	    primaryAccount.setAccountNumber(user.getPrimaryAccountNo());
	    primaryAccount.setAccountBalance(user.getPrimaryAccountBalance());
	    user.setPrimaryAccount(primaryAccount);

	    SavingsAccount savingsAccount = new SavingsAccount();
	    savingsAccount.setAccountNumber(user.getSavingsAccountNo());
	    savingsAccount.setAccountBalance(user.getSavingsAccountBalance());
	    user.setSavingsAccount(savingsAccount);

	    user.setRecipientList(new ArrayList<>());
	    /*user.setUserRoles(new HashSet<>());*/

        List<User> allUsers = userRepository.findAll();
        int allUserSize = allUsers.size();
        Long lastUserId = Long.valueOf(allUserSize);
        user.setUserId(++lastUserId);

        log.info(user.getUsername());
        log.info(user.getEmail());
        log.info(user.getFirstName());
        log.info(user.getLastName());
        log.info(user.getPassword());
        log.info(user.getPhone());
        log.info(user.getAuthorities().toString());
        log.info(user.getPrimaryAccount().toString());
        log.info(user.getSavingsAccount().toString());
        //log.info(user.getRecipientList().toString());
        //log.info(user.getUserId().toString());
        //log.info(user.getUserRoles().toString());

        if(userService.checkUserExists(user.getUsername(), user.getEmail()))  {
            if (userService.checkEmailExists(user.getEmail())) {
                model.addAttribute("emailExists", true);
            }

            if (userService.checkUsernameExists(user.getUsername())) {
                model.addAttribute("usernameExists", true);
            }

            return "signup";
        } else {
        	 Set<UserRole> userRoles = new HashSet<>();
             userRoles.add(new UserRole(user, rolerepository.findByName("ROLE_USER")));
             userService.createUser(user, userRoles);

            return "redirect:/";
        }
    }
	
	@RequestMapping("/userFront")
	public String userFront(Principal principal, Model model) {
        User user = userService.findByUsername(principal.getName());
        PrimaryAccount primaryAccount = user.getPrimaryAccount();
        SavingsAccount savingsAccount = user.getSavingsAccount();

        model.addAttribute("primaryAccount", primaryAccount);
        model.addAttribute("savingsAccount", savingsAccount);

        return "userFront";
    }
}
