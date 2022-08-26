package com.example.jwtprj.service.impl;

import com.example.jwtprj.model.Role;
import com.example.jwtprj.model.RoleName;
import com.example.jwtprj.repository.IRoleRepository;
import com.example.jwtprj.service.IRoleSevice;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class RoleServiceImpl implements IRoleSevice {

    @Autowired
    IRoleRepository roleRepository;
    @Override
    public Optional<Role> findByName(RoleName name) {
        return roleRepository.findByName(name);
    }
}
