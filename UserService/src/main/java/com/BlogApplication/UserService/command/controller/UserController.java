package com.BlogApplication.UserService.command.controller;
import com.BlogApplication.UserService.command.commands.CreateUserCommand;
import com.BlogApplication.UserService.command.commands.DeleteUserCommand;
import com.BlogApplication.UserService.command.payload.RegisterRequest;
import com.BlogApplication.UserService.command.repository.RoleRepository;
import com.BlogApplication.UserService.command.repository.UserRepository;
import com.BlogApplication.UserService.core.config.JwtUtill;
import com.BlogApplication.UserService.core.entity.UserDetails;
import com.BlogApplication.UserService.core.service.UserDetailsServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.UUID;

@RestController
@Slf4j
@RequestMapping("/user/command")
public class UserController {
    private CommandGateway commandGateway;
    @Autowired
private RoleRepository repository;
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private UserDetailsServiceImpl userDetailService;
    @Autowired
    private JwtUtill jwtUtill;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private RestTemplate restTemplate;



    public UserController(CommandGateway commandGateway) {
        this.commandGateway = commandGateway;
    }

    @PostMapping("/register")
    public ResponseEntity<?> createUser(@RequestBody RegisterRequest registerRequest){
        log.info("controller:create user");
        CreateUserCommand createUserCommand= CreateUserCommand.builder()
                .userId(UUID.randomUUID())
                .username(registerRequest.getUsername())
                .password(registerRequest.getPassword())
                .email(registerRequest.getEmail())
                .roles(registerRequest.getRoles())
                .build();
        return ResponseEntity.ok(this.commandGateway.sendAndWait(createUserCommand));
//        return ResponseEntity.ok(createUserCommand);


    }
    @DeleteMapping("/delete/{username}")
    public ResponseEntity<?> deleteUser(@PathVariable("username") String username) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        UserDetails userDetails = (UserDetails) auth.getPrincipal();
        String currentUsername = userDetails.getUsername();
        if (auth != null && (auth.getAuthorities().stream().anyMatch(a -> ((GrantedAuthority) a).getAuthority().equals("ROLE_ADMIN") || currentUsername.equals(username)))) {
            DeleteUserCommand deleteUserCommand=new DeleteUserCommand(UUID.randomUUID(),"username");
            return this.commandGateway.sendAndWait(deleteUserCommand);
        } else {
            return ResponseEntity.ok("user not deleted");//throw exception
        }
    }

//    @PutMapping
//    public void updateUser(UserModel userModel){
//
//    }
//        @PostMapping("/test")
//        public void testingreq(){
//log.info("line1");
//log.error("error1");
//
//        }
//    @PostMapping("/restore")
//    public Role create(){
//        Role role1=new Role(1,ERole.ROLE_USER);
//        Role role2=new Role(2,ERole.ROLE_ADMIN);
//repo.save(role1);
//repo.save(role2);
//       return role1 ;
//    }
}

