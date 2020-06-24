package com.sn.ex.zuulproxy.service;


import com.sn.ex.zuulproxy.entities.AppUser;
import org.springframework.stereotype.Service;

@Service
public interface AppUserService {

    AppUser findByEmail(String email);
    AppUser findByUsername(String username);
    AppUser findById(String Id);

    Boolean isAdminByEmail(String email);
    Boolean isAdminByToken(String authorization);

    Boolean save(AppUser user);
    AppUser update(String username, AppUser user);

    Boolean validateUserByToken(String authorization);
    AppUser getUserFromToken(String authorization);

}
