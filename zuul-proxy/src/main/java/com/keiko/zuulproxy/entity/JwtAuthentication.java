package com.keiko.zuulproxy.entity;

import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;
import java.util.Set;

@Getter
@Setter
public class JwtAuthentication implements Authentication {

    private String email;
    private String name;
    private Set<Role> roles;
    private boolean authenticated;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities () {
        return roles;
    }

    @Override
    public Object getCredentials () {
        return null;
    }

    @Override
    public Object getDetails () {
        return null;
    }

    @Override
    public Object getPrincipal () {
        return email;
    }

    @Override
    public boolean isAuthenticated () {
        return authenticated;
    }

    @Override
    public void setAuthenticated (boolean isAuthenticated) throws IllegalArgumentException {
        this.authenticated = isAuthenticated;
    }

    @Override
    public String getName () {
        return name;
    }
}
