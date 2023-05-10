package com.BlogApplication.UserService.command.event;

import com.BlogApplication.UserService.command.repository.RoleRepository;
import com.BlogApplication.UserService.command.repository.UserRepository;
import com.BlogApplication.UserService.core.entity.ERole;
import com.BlogApplication.UserService.core.entity.Role;
import com.BlogApplication.UserService.core.entity.User;
import com.BlogApplication.UserService.core.events.UserCreatedEvent;
import com.BlogApplication.UserService.core.events.UserDeletedEvent;
import lombok.extern.slf4j.Slf4j;
import org.axonframework.eventhandling.EventHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.Set;

@Component
@Slf4j
public class UserEventsHandler {
    Logger logger= LoggerFactory.getLogger(UserEventsHandler.class);
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private RoleRepository roleRepository;
    @Autowired
    private BCryptPasswordEncoder passwordEncoder;
    @Autowired
    KafkaTemplate<String,Object> kafkaTemplate;
    @EventHandler
    public void on(UserCreatedEvent userCreatedEvent){
        log.info("event handler create"+userCreatedEvent.getId().toString()+userCreatedEvent.getRoles().toString());
        User user=new User();
        Set<String> strRoles=userCreatedEvent.getRoles();
        Set<Role> roles=new HashSet<>();
        user.setUserId(userCreatedEvent.getId());
        user.setUsername(userCreatedEvent.getUsername());
        user.setEmail(userCreatedEvent.getEmail());
        user.setPassword(passwordEncoder.encode(userCreatedEvent.getPassword()));

        if(strRoles==null){
            Role userRole=roleRepository.findByName(ERole.ROLE_USER);
            log.info("check roles user"+userRole.getName().toString());
            roles.add(userRole);

        }else{
            strRoles.forEach(role ->{
                switch (role){
                    case "admin":
                        Role adminRole=roleRepository.findByName(ERole.ROLE_ADMIN);
                        log.info("role"+adminRole.getName().toString());
                        roles.add(adminRole);
                        break;
                    default:
                        Role userRole=roleRepository.findByName(ERole.ROLE_USER);
                        log.info("role"+userRole.getName().toString());
                        roles.add(userRole);
                }
            });
        }
        log.info("persisting user"+user.getUserId().toString());
        user.setRoles(roles);
        userRepository.save(user);

        log.info("publishing event");
        this.kafkaTemplate.send("UserEventsTopic",userCreatedEvent);
    }
    @EventHandler
    public void on(UserDeletedEvent userDeletedEvent){
        try{
            if(userRepository.existsUserByUsername(userDeletedEvent.getUsername())){
                userRepository.deleteByUsername(userDeletedEvent.getUsername());
                this.kafkaTemplate.send("UserEventsTopic",userDeletedEvent);
            }
        }catch (Exception e){e.printStackTrace();}

    }
}
