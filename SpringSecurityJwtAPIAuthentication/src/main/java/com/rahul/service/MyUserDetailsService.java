package com.rahul.service;

import com.rahul.entities.MyUser;
import com.rahul.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class MyUserDetailsService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<MyUser> myUser = userRepository.findByUserName(username);
        if (myUser.isPresent()){
            var userObj=myUser.get();
            return User.builder()
                    .username(userObj.getUserName())
                    .password(userObj.getPassword())
                    .roles(getRoles(userObj))
                    .build();

        }else {
            throw  new UsernameNotFoundException("No Such UserName Found");
        }
    }
    private String[] getRoles(MyUser userObj) {
        if (userObj==null){
            return  new String[]{"USER"};
        }else {
            return userObj.getRoles().split(",");
        }
    }
}
