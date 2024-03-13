package com.keiko.userservice.constants;

/**
 * This class includes all the API end points
 */
public class WebResourceKeyConstants {

    // user
    public static final String IS_EXISTS_USER = "/isExists";
    public static final String FIND_USER_BY_EMAIL = "/findByEmail";
    public static final String FIND_USERS_BY_ROLE = "/findByRole";
    public static final String FIND_NOT_ENABLED_USERS = "/findNotEnabled";
    public static final String DELETE_USER_BY_EMAIL = "/deleteByEmail";
    public static final String DELETE_ALL = "/deleteAll";
    public static final String DELETE_ROLES = "/deleteRoles";
    public static final String ADD_ROLES = "/addRoles";

    // role
    public static final String FIND_ROLE_BY_NAME = "/findByName";
    public static final String GET_USER_ROLES = "/getUserRoles";
}
