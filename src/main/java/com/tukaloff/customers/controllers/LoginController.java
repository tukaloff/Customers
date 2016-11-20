/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tukaloff.customers.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 *
 * @author tukal
 */
@Controller
public class LoginController {
    
    @RequestMapping({"/", "/login"})
    public String showLoginPage() {
        return "/login";
    }
    
    @RequestMapping("/user")
    public String showUserPage() {
        return "/user";
    }
}
