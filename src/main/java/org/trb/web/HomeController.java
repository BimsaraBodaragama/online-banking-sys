package org.trb.web;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
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
import org.trb.utils.AdminLock;

import java.security.Principal;
import java.security.SecureRandom;
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

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    private static final String SALT = "salt"; // Salt should be protected carefully
	
	@RequestMapping("/")
	public String home() {
		return "redirect:/index";
	}
	
	@RequestMapping("/index")
    public String index() {
        userService.disableUser("AdminTRB");
        return "index";
    }
	
	@RequestMapping(value = "/signup", method = RequestMethod.GET)
    public String signup(Model model) {
        User user = new User();

        model.addAttribute("user", user);

        return "signup";
    }

    @RequestMapping(value = "/admin-lock-signup-get", method = RequestMethod.GET)
    public String adminLockSignup(Model model) {
        AdminLock adminLock = new AdminLock();

        model.addAttribute("adminLock", adminLock);
        return "admin-lock-signup";
    }

    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(12, new SecureRandom(SALT.getBytes()));
    }

    @RequestMapping(value = "/admin-lock-signup-get", method = RequestMethod.POST)
    public String adminLockSignupPost(@ModelAttribute("adminLock") AdminLock adminLock,
                                      Model model) {

        BCryptPasswordEncoder bCryptPasswordEncoder = passwordEncoder();
        String encryptedAdminPassword = bCryptPasswordEncoder.encode(adminLock.getPassword());

        //log.info("AQQAQQADMIN====" + encryptedAdminPassword + "====");

        User adminTRB = userRepository.findByUsername("AdminTRB");
        if(adminTRB.getPassword().equals(encryptedAdminPassword)){

            log.info("ADMINTRB PASSWORD MATCHED!");

            User user = new User();

            model.addAttribute("user", user);

            return "signup";

        }

        model.addAttribute("msg", "Incorrect Password!");
        return "admin-lock-signup";

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
            log.info("aaa555");
             long newUserRoleId = (userRepository.findAll().size()*2)+1;
             userRoles.add(new UserRole(user, rolerepository.findByName("ROLE_USER"), newUserRoleId));
            log.info("aaa666");
            User user1 = userService.createUser(user, userRoles);
            if (user1==null){
                model.addAttribute("msg", "Either User Name, Email, Primary " +
                        "Account No or Savings Account No has been used by another user." +
                        " Please check again and try re-entering values for relevant fields!");
                return "signup";
            }

            return "redirect:/";
        }
    }
	
	@RequestMapping("/userFront")
	public String userFront(Principal principal, Model model) {
        User user = userService.findByUsername(principal.getName());
        PrimaryAccount primaryAccount = user.getPrimaryAccount();
        SavingsAccount savingsAccount = user.getSavingsAccount();

        model.addAttribute("user", user);
        model.addAttribute("primaryAccount", primaryAccount);
        model.addAttribute("savingsAccount", savingsAccount);

        return "userFront";
    }
}
