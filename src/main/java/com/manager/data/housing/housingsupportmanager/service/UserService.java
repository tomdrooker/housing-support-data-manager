package com.manager.data.housing.housingsupportmanager.service;

import com.manager.data.housing.housingsupportmanager.model.User;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface UserService extends UserDetailsService {

    User findByUserName(String username);

}
