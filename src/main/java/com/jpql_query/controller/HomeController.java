package com.jpql_query.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HomeController {

    Logger logger= LoggerFactory.getLogger(HomeController.class);
    @RequestMapping("/home")
    public String home(){
        logger.trace("home controller access");
        return "Hello from spring boot";
    }
}
