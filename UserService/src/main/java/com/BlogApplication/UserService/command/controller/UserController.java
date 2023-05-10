package com.BlogApplication.UserService.command.controller;
import com.BlogApplication.UserService.command.commands.CreateUserCommand;
import com.BlogApplication.UserService.command.commands.DeleteUserCommand;
import com.BlogApplication.UserService.command.payload.RegisterRequest;
import com.BlogApplication.UserService.command.repository.RoleRepository;
import com.BlogApplication.UserService.command.repository.UserRepository;
import com.BlogApplication.UserService.core.config.JwtUtill;
import com.BlogApplication.UserService.core.entity.ERole;
import com.BlogApplication.UserService.core.entity.Role;
import com.BlogApplication.UserService.core.entity.UserDetails;
import com.BlogApplication.UserService.core.exceptions.BadRequestException;
import com.BlogApplication.UserService.core.service.UserDetailsServiceImpl;
import com.netflix.discovery.converters.Auto;
import lombok.extern.slf4j.Slf4j;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.UUID;

@RestController
@Slf4j
@RequestMapping("/user")
public class UserController {
    private CommandGateway commandGateway;
    @Autowired
    private RoleRepository repo;

    public UserController(CommandGateway commandGateway) {
        this.commandGateway = commandGateway;
    }

    @PostMapping("/register")
    public ResponseEntity<?> createUser(@Valid @RequestBody RegisterRequest registerRequest){
        log.info("controller:create user");
        CreateUserCommand createUserCommand= CreateUserCommand.builder()
                .userId(UUID.randomUUID())
                .username(registerRequest.getUsername())
                .password(registerRequest.getPassword())
                .email(registerRequest.getEmail())
                .roles(registerRequest.getRoles())
                .build();
       return ResponseEntity.ok(this.commandGateway.sendAndWait(createUserCommand));



    }
    @DeleteMapping("/delete/{username}")
    public ResponseEntity<?> deleteUser(@PathVariable("username") String username) {
        log.info("controller:delete user");
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        UserDetails userDetails = (UserDetails) auth.getPrincipal();
        String currentUsername = userDetails.getUsername();
        if (auth != null && (auth.getAuthorities().stream().anyMatch(a -> ((GrantedAuthority) a).getAuthority().equals("ROLE_ADMIN") || currentUsername.equals(username)))) {
            DeleteUserCommand deleteUserCommand=new DeleteUserCommand(UUID.randomUUID(),"username");
            return this.commandGateway.sendAndWait(deleteUserCommand);
        } else {
            throw new BadRequestException("unable to delete user either user does not exist or you do not have authority to delete the user");
        }
    }
    @PostMapping("/restore")
    public void restore(){
        Role role1=new Role(1, ERole.ROLE_USER);
        Role role2=new Role(2,ERole.ROLE_ADMIN);
        repo.save(role1);
        repo.save(role2);

            }


}

