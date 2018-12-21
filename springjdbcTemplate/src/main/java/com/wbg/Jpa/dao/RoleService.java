package com.wbg.Jpa.dao;

import com.wbg.Jpa.entity.Role;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class RoleService {
    @Autowired
    private RoleDao roleDao;

    public List<Role> listAll() {
        List<Role> list = roleDao.findAll();
        return list;
    }


}
