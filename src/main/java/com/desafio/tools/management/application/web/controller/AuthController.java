package com.desafio.tools.management.application.web.controller;

import com.desafio.tools.management.application.config.JwtUtil;
import com.desafio.tools.management.application.web.entities.requests.AuthRequest;
import com.desafio.tools.management.application.web.entities.responses.AuthResponse;
import com.desafio.tools.management.domain.services.impl.AuthService;
import com.desafio.tools.management.domain.services.impl.UserDetailsServiceImpl;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@CrossOrigin
@Api(value="AuthController", description="Operations for authentication")
public class AuthController {
    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private UserDetailsServiceImpl userDetailsService;

    @Autowired
    private AuthService authService;

    @PostMapping
    @ApiOperation(value = "Authenticate User", response = AuthResponse.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successful auth"),
            @ApiResponse(code = 401, message = "Invalid credentials")
    })
    public ResponseEntity<AuthResponse> login(@RequestBody AuthRequest authenticationRequest)  {
        try {
            authService.authenticate(authenticationRequest.getUsername(), authenticationRequest.getPassword());

            final UserDetails userDetails = userDetailsService.loadUserByUsername(authenticationRequest.getUsername());

            final String token = jwtUtil.generateToken(userDetails.getUsername());

            return ResponseEntity.ok(
                new AuthResponse(token)
            );
        } catch (DisabledException ex) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                    new AuthResponse(ex)
            );
        } catch (BadCredentialsException ex) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(
                    new AuthResponse(ex)
            );
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(
                    new AuthResponse(ex)
            );
        }
    }
}
