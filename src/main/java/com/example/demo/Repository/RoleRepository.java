package com.example.demo.Repository;


import com.example.demo.Entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Set;

public interface RoleRepository extends JpaRepository<Role,Integer> {
    Set<Role> findByAuthority(String authority);
}
