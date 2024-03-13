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

    // user
    public static final String IS_EXISTS_USER = "/isExists";
    public static final String FIND_USER_BY_EMAIL = "/findByEmail";
    public static final String FIND_NOT_ENABLED_USERS = "/findNotEnabled";
    public static final String DELETE_USER_BY_EMAIL = "/deleteByEmail";
    public static final String DELETE_ALL = "/deleteAll";
}
