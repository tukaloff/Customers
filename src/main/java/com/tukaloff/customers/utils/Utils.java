/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tukaloff.customers.utils;

import java.util.Base64;


/**
 *
 * @author tukal
 */
public class Utils {
    
    
    public static String getAccessToken(String s1, String s2) {
        String keySource = s1 + System.currentTimeMillis() + s2;
        byte[] tokenByte = Base64.getEncoder().encode(keySource.getBytes());
        return new String(tokenByte);
    }
}
