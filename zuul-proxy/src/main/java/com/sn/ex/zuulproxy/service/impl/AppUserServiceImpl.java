package com.sn.ex.zuulproxy.service.impl;


import com.sn.ex.zuulproxy.entities.AppUser;
import com.sn.ex.zuulproxy.repository.AppUserRepository;
import com.sn.ex.zuulproxy.service.AppUserService;
import com.sn.ex.zuulproxy.service.JwtUtilService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

@Slf4j
@Service
public class AppUserServiceImpl implements AppUserService {

    @PersistenceContext
    private EntityManager entityManager;

    @Autowired
    private AppUserRepository appUserRepository;

    @Autowired
    private PasswordEncoder bcryptEncoder;

    @Autowired
    private JwtUtilService jwtTokenUtil;


    @Override
    public AppUser findByEmail(String email) {
        Query query = entityManager.createNativeQuery("SELECT * FROM appuser  WHERE email = :email", AppUser.class);
        query.setParameter("email", email);
        AppUser appUser = (AppUser) query.getSingleResult();
        return appUser;
    }

    @Override
    public AppUser findByUsername(String username) {
        return this.findById(username);
    }

    @Override
    public AppUser findById(String Id) {
        if (appUserRepository.existsById(Id)) {
            AppUser appUser = appUserRepository.findById(Id).get();
            return appUser;
        } else {
            return null;
        }
    }


    @Override
    public AppUser update(String username, AppUser appUser) {
        AppUser user = appUserRepository.findByUsername(username);
        if (user == null) {
            log.info(" Could not update user info. Username not found : {}", username);
            return null;
        }
        if (StringUtils.isNotEmpty(appUser.getPassword())) {
            user.setPassword(bcryptEncoder.encode(appUser.getPassword()));
        }
        if (StringUtils.isNotEmpty(appUser.getFirstName())) {
            user.setFirstName(appUser.getFirstName());
        }
        if (StringUtils.isNotEmpty(appUser.getLastName())) {
            user.setLastName(appUser.getLastName());
        }
        if (StringUtils.isNotEmpty(appUser.getEmail())) {
            user.setEmail(appUser.getEmail());
        }
        if (StringUtils.isNotEmpty(appUser.getMobilePhone())) {
            user.setMobilePhone(appUser.getMobilePhone());
        }
        if (user.getIsAdmin() != null && !user.getIsAdmin().equals(appUser.getIsAdmin())) {
            user.setIsAdmin(appUser.getIsAdmin());
        }
        if (user.getIsDeleted() != null && !user.getIsDeleted().equals(appUser.getIsDeleted())) {
            user.setIsDeleted(appUser.getIsDeleted());
        }
        AppUser newUser = appUserRepository.save(user);
        if (newUser != null) {
            log.info(" Update user info successfully.");
            return newUser;
        }
        return null;
    }


    @Override
    public Boolean validateUserByToken(String authorization) {
        String jwtToken = null;
        if (authorization != null && authorization.startsWith("Bearer ")) {
            jwtToken = authorization.substring(7);
            log.info("validateUserByToken - jwtToken = {}", jwtToken);
            String userName = jwtTokenUtil.extractUsername(jwtToken);
            AppUser appUser = appUserRepository.findByUsername(userName);
            if (appUser != null && !appUser.getIsDeleted()) {
                return true;
            } else {
                return false;
            }

        } else {
            return false;
        }
    }

    @Override
    public Boolean isAdminByToken(String authorization) {
        String jwtToken = null;
        if (StringUtils.isNotEmpty(authorization) && authorization.startsWith("Bearer ")) {
            jwtToken = authorization.substring(7);

            String userName = jwtTokenUtil.extractUsername(jwtToken);
            AppUser appUser = appUserRepository.findByUsername(userName);
            if (appUser != null && !appUser.getIsDeleted()) {
                return appUser.getIsAdmin();
            }
        }
        return false;
    }

    @Override
    public AppUser getUserFromToken(String authorization) {
        String jwtToken = null;
        if (StringUtils.isNotEmpty(authorization) && authorization.startsWith("Bearer ")) {
            jwtToken = authorization.substring(7);
            String userName = jwtTokenUtil.extractUsername(jwtToken);
            AppUser appUser = appUserRepository.findByUsername(userName);
            if (appUser != null && !appUser.getIsDeleted()) {
                return appUser;
            }
        }
        return null;
    }


    @Override
    public Boolean save(AppUser user) {
        if (appUserRepository.findByUsername(user.getUsername()) != null) {
            log.info("Could not save User {}. User is existed.", user);
            return false;
        }
        AppUser newUser = user;
        newUser.setPassword(bcryptEncoder.encode(user.getPassword()));

        try {
            AppUser saveUser = appUserRepository.save(newUser);
            log.info(" Save User {} successfully.", saveUser);
            return true;
        } catch (Exception e ) {
            log.info("Could not save User {}", user);
            return false;
        }
    }

    @Override
    public Boolean isAdminByEmail(String email) {
        AppUser appUsers = this.findByEmail(email);
        return appUsers.getIsAdmin();
    }

}
