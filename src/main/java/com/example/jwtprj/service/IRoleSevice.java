package com.example.jwtprj.service;

import com.example.jwtprj.model.Role;
import com.example.jwtprj.model.RoleName;

import java.util.Optional;

public interface IRoleSevice {
    Optional<Role> findByName(RoleName name);
}
