package com.keiko.zuulproxy.util;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.impl.DefaultClaims;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class TestData {
    private static final String USER_EMAIL = "admin@gmail.com";
    private static final String USER_NAME = "admin";
    private static final Set<String> USER_ROLES = new HashSet<> (Arrays.asList (("ADMIN")));

    public static Claims createTestClaims () {
        Claims claims = new DefaultClaims ();
        claims.setSubject (USER_EMAIL);
        claims.put ("name", USER_NAME);
        //claims.put ("roles", USER_ROLES);
        return claims;
    }
}
