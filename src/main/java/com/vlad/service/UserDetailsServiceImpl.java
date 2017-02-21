package com.vlad.service;

import com.vlad.exceptions.SQLFailException;
import com.vlad.model.SpringSecurityUser;
import com.vlad.service.UserService.UserService;
import com.vlad.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

/**
 * Created by Fedyunkin_Vladislav on 2/9/2017.
 */
@Service("userDetailsService")
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private UserService userService;

    public UserDetails loadUserByUsername(String login) throws UsernameNotFoundException {
        User user = null;
        try {
            user = userService.getUserByLogin(login);
            user.setAuthorities("ROLE_ADMIN");
        } catch (SQLFailException e) {
            e.printStackTrace();
        }

        if (user == null){
            throw new UsernameNotFoundException(String.format("No appUser found with login '%s'.", login));
        } else {
            SpringSecurityUser springSecurityUser = new SpringSecurityUser(user.getId(), user.getLogin(), user.getPassword(), null, null, AuthorityUtils.commaSeparatedStringToAuthorityList(user.getAuthorities()));
            return springSecurityUser ;
        }
    }
}
