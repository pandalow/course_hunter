package com.hunt.vo;

import com.hunt.enumerate.Role;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor

public class UserVO {
    private Long id;
    private String googleId;
    private String email;
    private String name;
    private String avatar;
    private Role role;
}
