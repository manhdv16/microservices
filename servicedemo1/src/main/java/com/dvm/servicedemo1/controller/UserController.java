package com.dvm.servicedemo1.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user")
public class UserController {

        @RequestMapping("/get")
        public String getUser() {
            return "User details from service 1";
        }
}
