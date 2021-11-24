package com.trainting.MyBoutique.security.payload.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;

import java.io.Serializable;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AuthenticationResponse implements Serializable {

    /**
     *
     */
    private static final long serialVersionUID = 1L;
    private String jwt;
    private String username;
    private List<String> roles;
}
