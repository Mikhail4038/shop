package com.keiko.userservice.constants;

/**
 * This class includes all the API end points
 */
public class WebResourceKeyConstants {

    //common
    public static final String SAVE = "/save";
    public static final String FETCH_BY = "/fetchBy";
    public static final String FETCH_ALL = "/fetchAll";
    public static final String DELETE = "/delete";


    // user
    public static final String USER_BASE = "/user";
    public static final String FIND_USER_BY_EMAIL = "/findByEmail";
    public static final String DELETE_USER_BY_EMAIL = "/deleteByEmail";
    public static final String FIND_USERS_BY_ROLE = "/findByRole";
    public static final String ADD_ROLES = "/addRoles";
    public static final String DELETE_ROLES = "/deleteRoles";

    // role
    public static final String ROLE_BASE = "/role";
    public static final String FIND_ROLE_BY_NAME = "/findByName";
    public static final String GET_USER_ROLES = "/getUserRoles";
}
