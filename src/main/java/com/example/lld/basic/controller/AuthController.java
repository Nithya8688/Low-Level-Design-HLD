    package com.example.lld.basic.controller;

    import com.example.lld.basic.dto.TokenResponse;
    import com.example.lld.basic.entity.User;
    import com.example.lld.basic.repository.UserRepository;
    import com.example.lld.config.JwtUtil;
    import io.jsonwebtoken.Claims;
    import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
    import org.springframework.security.crypto.password.PasswordEncoder;
    import org.springframework.web.bind.annotation.PostMapping;
    import org.springframework.web.bind.annotation.RequestMapping;
    import org.springframework.web.bind.annotation.RequestParam;
    import org.springframework.web.bind.annotation.RestController;

    @RestController
    @RequestMapping("/auth")
    public class AuthController {

        private final UserRepository userRepository;
        private final PasswordEncoder passwordEncoder;

        public AuthController(UserRepository userRepository,
                              PasswordEncoder passwordEncoder) {
            this.userRepository = userRepository;
            this.passwordEncoder = passwordEncoder;
        }

        @PostMapping("/login")
        public TokenResponse login(@RequestParam String username,
                                   @RequestParam String password) {

            User user = userRepository.findByUsername(username)
                    .orElseThrow(() -> new RuntimeException("User not found"));

            System.out.println(new BCryptPasswordEncoder().encode("admin"));

            if (!passwordEncoder.matches(password, user.getPassword())) {
                throw new RuntimeException("Invalid credentials");
            }

            String accessToken = JwtUtil.generateAccessToken(
                    user.getUsername(), user.getRole());

            String refreshToken = JwtUtil.generateRefreshToken(
                    user.getUsername());

            return new TokenResponse(accessToken, refreshToken);
        }
        @PostMapping("/refresh")
        public TokenResponse refresh(@RequestParam String refreshToken) {

            Claims claims = JwtUtil.parseToken(refreshToken);
            String username = claims.getSubject();

            User user = userRepository.findByUsername(username)
                    .orElseThrow(() -> new RuntimeException("User not found"));

            String newAccessToken =
                    JwtUtil.generateAccessToken(username, user.getRole());

            return new TokenResponse(newAccessToken, refreshToken);
        }
    }


