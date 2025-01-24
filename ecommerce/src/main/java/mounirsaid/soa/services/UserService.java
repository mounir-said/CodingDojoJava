package mounirsaid.soa.services;

import java.util.List;
import java.util.Optional;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import mounirsaid.soa.models.User;
import mounirsaid.soa.repositories.RoleRepository;
import mounirsaid.soa.repositories.UserRepository;

@Service
public class UserService {

	private UserRepository userRepo;
	private RoleRepository roleRepo;
	private BCryptPasswordEncoder bCryptPwEncoder;
	
	public UserService(UserRepository userRepo, RoleRepository roleRepo, BCryptPasswordEncoder bCryptPwEncoder) {
		this.userRepo = userRepo;
		this.roleRepo = roleRepo;
		this.bCryptPwEncoder = bCryptPwEncoder;
	}
	
	public void newUser(User user, String role) {
		user.setPassword(bCryptPwEncoder.encode(user.getPassword()));
		user.setRoles(roleRepo.findByName(role));
		userRepo.save(user);
	}
	
	public void updateUser(User user) {
		userRepo.save(user);
	}
	
	public User findByEmail(String email) {
		return userRepo.findByEmail(email);
	}
	
	public List<User> allUsers(){
		return userRepo.findAll();
	}
	
	public User upgradeUser(User user) {
		user.setRoles(roleRepo.findByName("ROLE_ADMIN"));
		return userRepo.save(user);
	}
	
	public void deleteUser(User user) {
		userRepo.delete(user);
	}
    
    public User findById(Long id) {
    	Optional<User> potentialUser = userRepo.findById(id);
    	if(potentialUser.isPresent()) {
    		return potentialUser.get();
    	}
    	return null;
    }
	
}