package com.sn.ex.zuulproxy.repository;

import com.sn.ex.zuulproxy.entities.AppUser;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AppUserRepository extends JpaRepository<AppUser, String> {
    AppUser findByUsername(String username);
}
