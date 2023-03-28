package com.getmygraphicscard.subscriptionservice.service;

import com.getmygraphicscard.subscriptionservice.model.SecurityUser;
import com.getmygraphicscard.subscriptionservice.entity.Subscription;
import com.getmygraphicscard.subscriptionservice.repository.SubscriptionRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;



@Service
@AllArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {

    private final SubscriptionRepository subscriptionRepository;

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Subscription user = subscriptionRepository.findByEmail(username).orElseThrow(() -> new UsernameNotFoundException("User not found"));
        return new SecurityUser(user);
    }
}
