package com.BlogApplication.UserService.command.repository;

import com.BlogApplication.UserService.core.entity.ERole;
import com.BlogApplication.UserService.core.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepository extends JpaRepository<Role,Long> {
    public Role findByName(ERole name);

}
