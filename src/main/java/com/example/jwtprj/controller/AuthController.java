package com.example.jwtprj.controller;

import com.example.jwtprj.dto.request.SignInForm;
import com.example.jwtprj.dto.request.SignUpForm;
import com.example.jwtprj.dto.response.JwtResponse;
import com.example.jwtprj.dto.response.ResponeMessage;
import com.example.jwtprj.model.Role;
import com.example.jwtprj.model.RoleName;
import com.example.jwtprj.model.User;
import com.example.jwtprj.security.jwt.JwtProvider;
import com.example.jwtprj.security.userprincal.UserPrinciple;
import com.example.jwtprj.service.impl.RoleServiceImpl;
import com.example.jwtprj.service.impl.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.HashSet;
import java.util.Set;


@RestController
@RequestMapping("/api/auth")
public class AuthController {
    @Autowired
    UserServiceImpl userService;
    @Autowired
    RoleServiceImpl roleService;
    @Autowired
    PasswordEncoder passwordEncoder;
    @Autowired
    AuthenticationManager authenticationManager;
    @Autowired
    JwtProvider  jwtProvider;
    @PostMapping("/signup")
    public ResponseEntity<?> register(@Valid @RequestBody SignUpForm signUpForm){
        if(userService.existsByUsername(signUpForm.getUsername())){
            return new ResponseEntity<>(new ResponeMessage("The username existed! Please try again!"), HttpStatus.OK);
        }
        if(userService.existsByEmail(signUpForm.getEmail())){
            return new ResponseEntity<>(new ResponeMessage("The email existed!Please try again"),HttpStatus.OK);
        }
        User user = new User(signUpForm.getName(),signUpForm.getUsername(),signUpForm.getEmail(),passwordEncoder.encode(signUpForm.getPassword()));
        Set<String> strRoles = signUpForm.getRoles();
        Set<Role> roles = new HashSet<>();
        strRoles.forEach(role->{
            switch (role){
                case "ADMIN":
                    Role aminRole = roleService.findByName(RoleName.ADMIN).orElseThrow(
                            ()-> new RuntimeException("Role not found")
                    );
                    roles.add(aminRole);
                    break;
                case "PM":
                    Role pmRole = roleService.findByName(RoleName.PM).orElseThrow(() -> new RuntimeException("Role not found"));
                    roles.add(pmRole);
                    break;
                default:
                    Role userRole = roleService.findByName(RoleName.USER).orElseThrow(()->new RuntimeException("Role not found"));
                    roles.add(userRole);

            }


        });
        user.setRoles(roles);
        userService.save(user);
        return new ResponseEntity<>(new ResponeMessage("Create user success!"),HttpStatus.OK );

    }
    @PostMapping("/signin")
    public ResponseEntity<?> login(@Valid @RequestBody SignInForm signInForm){
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(signInForm.getUsername(),signInForm.getPassword())
        );
        SecurityContextHolder.getContext().setAuthentication(authentication);
        String token = jwtProvider.createToken(authentication);
        UserPrinciple userPrinciple = (UserPrinciple) authentication.getPrincipal();
        return ResponseEntity.ok(new JwtResponse(token,userPrinciple.getName(),userPrinciple.getAuthorities()));
    }

}
