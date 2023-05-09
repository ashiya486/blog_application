package com.BlogApplication.UserService.query.controller;

import com.BlogApplication.UserService.core.config.JwtUtill;
import com.BlogApplication.UserService.core.exceptions.BadRequestException;
import com.BlogApplication.UserService.core.exceptions.RestTemplateException;
import com.BlogApplication.UserService.core.service.UserDetailsServiceImpl;
import com.BlogApplication.UserService.query.Query.GetUsersQuery;
import com.BlogApplication.UserService.core.payload.UserModel;
import com.BlogApplication.UserService.query.payload.AuthenticationResponse;
import com.BlogApplication.UserService.query.payload.LoginRequest;
import lombok.extern.slf4j.Slf4j;
import org.axonframework.messaging.responsetypes.ResponseTypes;
import org.axonframework.queryhandling.QueryGateway;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@RestController
@Slf4j
@RequestMapping("/user")
public class UserQueryController {
    public UserQueryController(QueryGateway queryGateway) {
        this.queryGateway = queryGateway;
    }

    private QueryGateway queryGateway;
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private UserDetailsServiceImpl userDetailsService;
    @Autowired
    private JwtUtill jwtUtill;
    @Autowired
    private RestTemplate restTemplate;

    @GetMapping
    public ResponseEntity<?> getUsers() {
        GetUsersQuery getUserQuery = new GetUsersQuery();
        log.info("controller");
       List<UserModel> list=queryGateway.query(getUserQuery, ResponseTypes.multipleInstancesOf(UserModel.class)).join();
       return ResponseEntity.ok(list);
    }
    @PostMapping("/authenticate")
    public ResponseEntity<?>authenticateUser(@RequestBody LoginRequest loginRequest){
        try{
            Authentication authentication=authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginRequest.getUsername(),loginRequest.getPassword()));
        }
        catch(Exception e){throw new BadRequestException("invalid credentials");}
        log.info("generating token");
        final UserDetails userDetails=userDetailsService.loadUserByUsername(loginRequest.getUsername());
        final String jwt=jwtUtill.generateToken(userDetails);
        return ResponseEntity.ok(new AuthenticationResponse(jwt));
    }
    @GetMapping("/validate")
    public ResponseEntity<?> validateUser(){
        log.info("user validated");
    return ResponseEntity.status(HttpStatus.ACCEPTED).build();

    }
    @GetMapping("/blogs")
    public ResponseEntity<?>getBlogsByUser(){
    com.BlogApplication.UserService.core.entity.UserDetails userDetails=(com.BlogApplication.UserService.core.entity.UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    ResponseEntity<?> response=restTemplate.exchange("http://BLOG_SERVICE/blogs/user/"+userDetails.getUsername(), HttpMethod.GET,null,List.class);
        log.info("response recieved");
    if(response.getStatusCode()==HttpStatus.OK){
        return response;
    }else{throw new RestTemplateException(response.getStatusCode(),response.toString());
    }
}
}
