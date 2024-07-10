package locator_item.v1.security.auth;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

import jakarta.validation.Valid;

import lombok.RequiredArgsConstructor;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import locator_item.v1.security.jwt.JwtAuthenticationResponse;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
@Tag(name = "Authentication")
public class AuthController {

    private final AuthenticationService authenticationService;

    @Operation(summary = "User registration")
    @PostMapping("/register")
    public JwtAuthenticationResponse signUp(@RequestBody @Valid AuthRegisterRequest request) {
        return authenticationService.register(request);
    }

    @Operation(summary = "User authorization")
    @PostMapping("/login")
    public JwtAuthenticationResponse signIn(@RequestBody @Valid AuthLoginRequest request) {
        return authenticationService.login(request);
    }
}

