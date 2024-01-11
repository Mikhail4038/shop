package com.keiko.authservice.constants;

/**
 * This class includes all the API end points
 */
public class WebResourceKeyConstants {
    public static final String AUTH_BASE = "/auth";
    public static final String REGISTRATION_USER = "/registration";
    public static final String CONFIRM_REGISTRATION = "/confirmRegistration";
    public static final String LOGIN_USER = "/login";
    public static final String BLOCK_USER = "/block";
    public static final String GENERATE_NEW_ACCESS_TOKEN = "/accessToken";
    public static final String GENERATE_NEW_REFRESH_TOKEN = "/refreshToken";

    // notification
    public static final String EMAIL_NOTIFICATION_BASE = "/email";
    public static final String SIMPLE_EMAIL = "/simple";
}
