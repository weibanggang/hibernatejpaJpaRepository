package com.wbg.Jpa.dao;

import com.wbg.Jpa.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleDao  extends JpaRepository<Role,Integer> {

}
