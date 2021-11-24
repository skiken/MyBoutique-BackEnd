package com.trainting.MyBoutique.web.auth;
import com.trainting.MyBoutique.dto.CustomerDto;
import com.trainting.MyBoutique.repository.CustomerRepository;
import com.trainting.MyBoutique.security.UserService;
import com.trainting.MyBoutique.security.jwt.JwtUtil;
import com.trainting.MyBoutique.security.payload.request.AuthenticationRequest;
import com.trainting.MyBoutique.security.payload.response.AuthenticationResponse;
import com.trainting.MyBoutique.security.payload.response.MessageResponse;
import com.trainting.MyBoutique.services.CustomerService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@CrossOrigin(origins = "*", maxAge = 3600)
@Controller
@RequiredArgsConstructor
@RequestMapping("/api"+"/auth")
public class AuthenticationEndpoint {

    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    CustomerRepository customerRepository;
    @Autowired
    CustomerService customerService;

    @Autowired
    UserService userService;

    @Autowired
    JwtUtil jwtUtil;

    @PostMapping("/signin")
    public ResponseEntity<?> authentication(@RequestBody AuthenticationRequest authenticationRequest)throws Exception {
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(authenticationRequest.getUsername(), authenticationRequest.getPassword())
            );
        }
        catch (BadCredentialsException e) {
            throw new Exception("Incorrect username or password", e);
        }


        final UserDetails userDetails = userService
                .loadUserByUsername(authenticationRequest.getUsername());

        final String jwt = jwtUtil.generateToken(userDetails);
        List<String> role = userDetails.getAuthorities().stream()
                .map(item -> item.getAuthority())
                .collect(Collectors.toList());

        return ResponseEntity.ok(new AuthenticationResponse(jwt,userDetails.getUsername(),role));
    }


    @PostMapping("/signup")
    public ResponseEntity<?> registerUser(@Valid @RequestBody CustomerDto customerDto) throws Exception {
        Boolean verif=true;
        if (customerRepository.existsByUsername(customerDto.getUsername())) {
            verif=false;
            return ResponseEntity
                    .badRequest()
                    .body(new MessageResponse("Username is already taken !"));
        }

        if (customerRepository.existsByEmail(customerDto.getEmail())) {
            verif=false;
            return ResponseEntity
                    .badRequest()
                    .body(new MessageResponse("Email is already in use !"));
        }

        if (verif)
            customerService.create(customerDto);

        return ResponseEntity.ok(new MessageResponse("User registered successfully!"));
    }


}
