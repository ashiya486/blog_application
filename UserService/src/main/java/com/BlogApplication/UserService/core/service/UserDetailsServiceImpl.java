package com.BlogApplication.UserService.core.service;

import com.BlogApplication.UserService.command.repository.UserRepository;
import com.BlogApplication.UserService.core.entity.User;
import com.BlogApplication.UserService.core.entity.UserDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {
    @Autowired
    private UserRepository userRepository;

    @Override
//    @Trasactional ???
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username);

        return UserDetails.build(user);
    }
}
