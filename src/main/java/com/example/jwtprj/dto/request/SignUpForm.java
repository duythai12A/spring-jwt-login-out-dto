package com.example.jwtprj.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class SignUpForm {
    private String name;
    private String username;
    private String email;
    private String password;
    private Set<String> roles;

}
