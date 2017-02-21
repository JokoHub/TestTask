package com.vlad.controller;

import com.vlad.AppConstant;
import com.vlad.security.jwt.TokenUtils;
import com.vlad.model.AuthenticationRequest;
import com.vlad.model.AuthenticationResponse;
import com.vlad.model.SpringSecurityUser;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by Fedyunkin_Vladislav on 2/9/2017.
 */
@RestController
@RequestMapping("/auth")
@Api(value = "/auth", description = "Controller that generates tokens for future authorization. Requires valid login and pass")
public class AuthenticationController {
    private Logger log = LoggerFactory.getLogger(AuthenticationController.class.getName());
    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private TokenUtils tokenUtils;

    @Autowired
    private UserDetailsService userDetailsService;

    @ApiOperation(value = "Generates Token", notes = "Generates Token for further login")
    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public ResponseEntity<?> login(@RequestBody AuthenticationRequest authenticationRequest) throws AuthenticationException{
        Authentication authentication = this.authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        authenticationRequest.getLogin(),
                        authenticationRequest.getPassword()
                )
        );
        SecurityContextHolder.getContext().setAuthentication(authentication);

        UserDetails userDetails = this.userDetailsService.loadUserByUsername(authenticationRequest.getLogin());
        String token = this.tokenUtils.generateToken(userDetails);
        return ResponseEntity.ok(new AuthenticationResponse(token));
    }

    @ApiOperation(value = "Removes token", notes = "Removes token from current user")
    @RequestMapping(value = "/logout", method = RequestMethod.POST)
    public ResponseEntity<?> logout(@RequestBody AuthenticationRequest request){
        Authentication authentication = this.authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getLogin(),
                        request.getPassword()
                )
        );
        SecurityContextHolder.getContext().getAuthentication().setAuthenticated(false);
        return new ResponseEntity<String>("User Successfuly logged out", HttpStatus.OK);
    }

    @RequestMapping(value = "/refresh", method = RequestMethod.GET)
    @ApiOperation("refresh current page")
    public ResponseEntity<?> authenticationRequest(HttpServletRequest request){
        String token = request.getHeader(AppConstant.TOKEN_HEADER);
        String login = this.tokenUtils.getLoginFromToken(token);
        SpringSecurityUser user = (SpringSecurityUser) this.userDetailsService.loadUserByUsername(login);
        if (this.tokenUtils.canTokenBeRefreshed(token, user.getLastPasswordReset())) {
            String refreshedToken = this.tokenUtils.refreshToken(token);
            return ResponseEntity.ok(new AuthenticationResponse(refreshedToken));
        } else {
            return ResponseEntity.badRequest().body(null);
        }
    }
}
