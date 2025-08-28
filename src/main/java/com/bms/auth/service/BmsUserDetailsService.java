package com.bms.auth.service;

import com.bms.auth.model.BmsUser;
import com.bms.auth.repository.BmsUserRepo;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class BmsUserDetailsService implements UserDetailsService {

    private final BmsUserRepo repo;

    public BmsUserDetailsService(BmsUserRepo repo) {
        this.repo = repo;
    }
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        BmsUser user = repo.findByEmail(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));

        BmsUser bmsUser = new BmsUser();
        bmsUser.setEmail(user.getEmail());
        bmsUser.setPassword(user.getPassword());
        bmsUser.setRole(user.getRole());
        return bmsUser;
    }
}
