package com.chenu.patel.hospitalManagement.dto;

import com.chenu.patel.hospitalManagement.entity.type.RoleType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashSet;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SignupRequestDto {
    private String username;
    private String password;
    private String email;
    private Set<RoleType> role = new HashSet<>(); // not recomended take role from user;
}
