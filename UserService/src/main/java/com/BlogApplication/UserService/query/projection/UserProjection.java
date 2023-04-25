package com.BlogApplication.UserService.query.projection;

import com.BlogApplication.UserService.command.repository.UserRepository;
import com.BlogApplication.UserService.core.entity.User;
import com.BlogApplication.UserService.core.payload.UserModel;
import com.BlogApplication.UserService.query.Query.GetUsersQuery;
import lombok.extern.slf4j.Slf4j;
import org.axonframework.queryhandling.QueryHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
@Slf4j
public class UserProjection {
  @Autowired
    private UserRepository repo;

    @QueryHandler
    public List<UserModel> handle(GetUsersQuery getUsersQuery){
      log.info("inside query handler");
      List<User> list_ent=  this.repo.findAll();
    List<UserModel> list_model= list_ent.stream().map(user -> new UserModel(user.getUserId(),user.getUsername(),user.getEmail(),user.getPassword(),user.getRoles())).collect(Collectors.toList());
    return list_model;
    }
}
