package com.fsd.stockmarket.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import com.fsd.stockmarket.entity.User;
import com.fsd.stockmarket.repository.UserRepository;
import java.util.stream.Collectors;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class UserDetailsServiceImpl implements UserDetailsService {

	@Autowired
	private  UserRepository userRepository;
	 
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username);
        if(user == null) {
        	throw new UsernameNotFoundException("User " + username + " doesn't exist");
        }
        log.info("find user by username = '"+username+"'");
        String password = user.getPassword();
        String rolesStr = user.getUserType();
        String[] roles = new String[0];
        if(!StringUtils.isEmpty(rolesStr)) {
        	roles = rolesStr.split(",");
        }
//        String[] roles = new String[2];
        return org.springframework.security.core.userdetails.User.withUsername(username)
        		.password(new BCryptPasswordEncoder().encode(password))
        		.disabled(!user.isConfirmed())
        		.roles(roles)
        		.build();
	}
	
//	@Override
//	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
//        User user = userRepository.findByUsername(username);
//        if(user == null) {
//        	throw new UsernameNotFoundException("User " + username + " doesn't exist");
//        }
//		//UserDetails userDetails = this.loadMockUser(username);
//        UserDetails userDetails = this.userDetailsCoverter(user);
//		log.info("UserDetailsService find "+username+"'s user details");
//		return userDetails;
//	}
	
	private UserDetails userDetailsCoverter(User user) {
		return new org.springframework.security.core.userdetails.User(user.getUsername(),
        		user.getPassword(), AuthorityUtils.commaSeparatedStringToAuthorityList(user.getUserType()));
	}
	
	//---------- start mock -----------
	@Autowired
    private PasswordEncoder passwordEncoder;
	
	private List<User> userList;
	
	/**
	 * mock fake data
	 */
	@PostConstruct
    public void initData() {
        String password = passwordEncoder.encode("123456");
        userList = new ArrayList<>();
        userList.add(new User(1L, "zuogan", password, "admin", "zuogan@cn.ibm.com", "1802708362", true));
        userList.add(new User(2L, "mike", password, "user", "mike@cn.ibm.com", "13299087765", true));
        userList.add(new User(3L, "tommy", password, "user", "tommy@cn.ibm.com", "13978652209", true));
    }

	private UserDetails loadMockUser(String username) {
		List<UserDetails> findUserList = userList.stream().filter(user -> user.getUsername().equals(username))
				.map(u -> userDetailsCoverter(u)).collect(Collectors.toList());
        if (!CollectionUtils.isEmpty(findUserList)) {
            return findUserList.get(0);
        } else {
            throw new UsernameNotFoundException("Username or password invalid");
        }
	}
	
	//---------- end mock -----------
}
