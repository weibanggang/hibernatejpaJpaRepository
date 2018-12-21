package com.wbg;
import com.wbg.sjt.config.JavaConfig;
import com.wbg.sjt.service.RoleDao;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.util.Map;

public class Main {
    public static void main(String[] args) {
        ApplicationContext applicationContext = new AnnotationConfigApplicationContext(JavaConfig.class);
        RoleDao roleDao = applicationContext.getBean(RoleDao.class);
        System.out.println(roleDao.getRole());
        for (Map<String, Object> map : roleDao.getToList()) {
            System.out.println(map);
        }
        roleDao.create();
        System.out.println("----------------------");
        for (Map<String, Object> map : roleDao.getToList()) {
            System.out.println(map);
        }
    }
}
