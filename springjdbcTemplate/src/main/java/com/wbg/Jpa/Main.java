package com.wbg.Jpa;
import com.wbg.Jpa.config.JavaConfig;
import com.wbg.Jpa.entity.Role;
import com.wbg.Jpa.dao.RoleService;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class Main {
    public static void main(String[] args) {
        ApplicationContext applicationContext = new AnnotationConfigApplicationContext(JavaConfig.class);
        RoleService roleDao = applicationContext.getBean(RoleService.class);
        for (Role role : roleDao.listAll()) {
            System.out.println(role);
        }
    }
}
