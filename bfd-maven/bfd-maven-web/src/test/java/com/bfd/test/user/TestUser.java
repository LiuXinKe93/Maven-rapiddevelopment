package com.bfd.test.user;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractJUnit4SpringContextTests;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.bfd.bean.User;
import com.bfd.service.UserService;
import com.bfd.serviceImpl.UserServiceImpl;

@RunWith(SpringJUnit4ClassRunner.class)  
@ContextConfiguration(locations = "classpath:applicationContext.xml")  
public class TestUser extends AbstractJUnit4SpringContextTests {  
  
    @Autowired  
    UserService userService;  
  
    @Test  
    public void testSave() {  
        userService.save(new User(1,"xinkeliu",25));  
    }  
  
}  