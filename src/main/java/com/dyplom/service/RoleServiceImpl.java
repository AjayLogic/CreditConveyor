package com.dyplom.service;

import com.dyplom.entity.Role;
import com.dyplom.repository.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("roleService")
public class RoleServiceImpl implements RoleService {

    @Autowired
    RoleRepository roleRepository;


    @Override
    public Role findByRole(String role) {
        return roleRepository.findByRole(role);
    }
}
