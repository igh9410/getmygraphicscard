package com.GetMyGraphicsCard.subscriptionservice.model;

import com.GetMyGraphicsCard.subscriptionservice.entity.Subscription;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;


public class SecurityUser implements UserDetails {

    private final Subscription subscription;

    public SecurityUser(Subscription subscription) {
        this.subscription = subscription;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(() -> subscription.getRole().name());
    }

    @Override
    public String getPassword() {
        return subscription.getPassword();
    }

    @Override
    public String getUsername() {
        return subscription.getEmail();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
