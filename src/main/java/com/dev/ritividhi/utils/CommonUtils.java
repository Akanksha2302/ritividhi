package com.dev.ritividhi.utils;


public class CommonUtils {

    public static String extractBearerToken(String authorization) {
        if (authorization == null || !authorization.startsWith("Bearer ")) {
            return null;
        }
        return authorization.split(" ")[1];
    }
}