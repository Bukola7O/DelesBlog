package com.example.delesblog.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserDto {
    private String password;
    private String username;
    private String firstName;
    private String lastName;
    private String email;
}
