package org.trb.service.UserServiceImpl;

import org.trb.model.security.Role;
import org.trb.repository.PrimaryAccountRepository;
import org.trb.repository.RoleRepository;
import org.trb.repository.SavingsAccountRepository;
import org.trb.repository.UserRepository;
import org.trb.model.User;
import org.trb.model.security.UserRole;
import org.trb.service.AccountService;
import org.trb.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
@Transactional
public class UserServiceImpl implements UserService{
	
	private static final Logger LOG = LoggerFactory.getLogger(UserService.class);
	
	@Autowired
	private UserRepository userrepository;
	
	@Autowired
    private RoleRepository rolerepository;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;
    
    @Autowired
    private AccountService accountService;

    @Autowired
    private PrimaryAccountRepository primaryAccountrepository;

    @Autowired
    private SavingsAccountRepository savingsAccountrepository;

	public void save(User user) {
        userrepository.save(user);
    }

    public User findByUsername(String username) {
        return userrepository.findByUsername(username);
    }

    public User findByEmail(String email) {
        return userrepository.findByEmail(email);
    }
    
    
    public User createUser(User user, Set<UserRole> userRoles) {


        User localUser = userrepository.findByUsername(user.getUsername());


        if (localUser != null) {
            LOG.info("User with username {} already exist. Nothing will be done. ", user.getUsername());
        } else {

            String encryptedPassword = passwordEncoder.encode(user.getPassword());
            user.setPassword(encryptedPassword);

            LOG.info(String.valueOf(userRoles.size()));
            for (UserRole ur : userRoles) {
                ur.setUser(user);

                Role role = new Role();
                Iterable<Role> allRoles = rolerepository.findAll();
                int i = 0;
                for(Role r : allRoles){
                    i += 1;
                }
                role.setRoleId(++i);
                role.setName("ROLE_USER");
                role.setUserRoles(new HashSet<>());
                ur.setRole(role);
                rolerepository.save(ur.getRole());

            }
            user.getUserRoles().addAll(userRoles);

            /*user.setPrimaryAccount(accountService.createPrimaryAccount());
            user.setSavingsAccount(accountService.createSavingsAccount());*/
            primaryAccountrepository.save(user.getPrimaryAccount());
            savingsAccountrepository.save(user.getSavingsAccount());

            localUser = userrepository.save(user);
        }

        return localUser;
    }
    
    public boolean checkUserExists(String username, String email){
        if (checkUsernameExists(username) || checkEmailExists(username)) {
            return true;
        } else {
            return false;
        }
    }

    public boolean checkUsernameExists(String username) {
        if (null != findByUsername(username)) {
            return true;
        }

        return false;
    }
    
    public boolean checkEmailExists(String email) {
        if (null != findByEmail(email)) {
            return true;
        }

        return false;
    }

    public User saveUser (User user) {
        return userrepository.save(user);
    }
    
    public List<User> findUserList() {
        return userrepository.findAll();
    }

    public void enableUser (String username) {
        User user = findByUsername(username);
        user.setEnabled(true);
        userrepository.save(user);
    }

    public void disableUser (String username) {
        User user = findByUsername(username);
        user.setEnabled(false);
        System.out.println(user.isEnabled());
        userrepository.save(user);
        System.out.println(username + " is disabled.");
    }
}