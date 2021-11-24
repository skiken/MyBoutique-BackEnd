package com.trainting.MyBoutique;

import java.time.LocalTime;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class rest {
     
    @RequestMapping(path = "/time", method = RequestMethod.GET)
    public String sayHelloWorld(){
        return "Hello World @ " + LocalTime.now();
    }
 
}