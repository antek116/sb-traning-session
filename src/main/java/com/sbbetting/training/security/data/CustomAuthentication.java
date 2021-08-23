package com.sbbetting.training.security.data;

import com.sbbetting.training.database.model.User;
import com.sbbetting.training.database.model.UserDetails;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.Collection;
import java.util.Collections;

public class CustomAuthentication implements Authentication {

    private final User user;

    private boolean isAuthenticated = true;

    public CustomAuthentication(User user) {
        this.user = user;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Collections.singletonList(new SimpleGrantedAuthority("USER_ROLE"));
    }

    @Override
    public Object getCredentials() {
        return null;
    }

    @Override
    public UserDetails getDetails() {
        return user.getUserDetails();
    }

    @Override
    public User getPrincipal() {
        return user;
    }

    @Override
    public boolean isAuthenticated() {
        return isAuthenticated;
    }

    @Override
    public void setAuthenticated(boolean authenticated) throws IllegalArgumentException {
        this.isAuthenticated = authenticated;
    }

    @Override
    public String getName() {
        return user.getEmail();
    }
}
