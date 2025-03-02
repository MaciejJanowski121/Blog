package org.example.blog.controller;

import org.example.blog.model.User;
import org.example.blog.service.AuthService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import java.util.Map;

@RestController
@RequestMapping("/auth")
public class AuthController {
    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/register")
    public ResponseEntity<Object> register(@RequestBody User user, HttpServletResponse response) {
        String token = authService.register(user);

        // Ustaw HttpOnly Cookie
        Cookie cookie = new Cookie("token", token);
        cookie.setHttpOnly(true);
        cookie.setSecure(false); // Użyj true na produkcji z HTTPS
        cookie.setPath("/");
        cookie.setMaxAge(24 * 60 * 60); // 1 dzień
        response.addCookie(cookie);

        return ResponseEntity.ok().body(Map.of("message", "Registration successful"));
    }

    @PostMapping("/login")
    public ResponseEntity<Map<String, String>> login(@RequestBody User user, HttpServletResponse response) {
        String token = authService.login(user.getUsername(), user.getPassword());

        // Ustaw HttpOnly Cookie
        Cookie cookie = new Cookie("token", token);
        cookie.setHttpOnly(true);
        cookie.setSecure(false); // Użyj true na produkcji z HTTPS
        cookie.setPath("/");
        cookie.setMaxAge(24 * 60 * 60); // 1 dzień
        response.addCookie(cookie);

        return ResponseEntity.ok(Map.of("message", "Login successful"));
    }

    @PostMapping("/logout")
    public ResponseEntity<Map<String, String>> logout(HttpServletResponse response) {
        // Usuń HttpOnly Cookie
        Cookie cookie = new Cookie("token", null);
        cookie.setHttpOnly(true);
        cookie.setSecure(false);
        cookie.setPath("/");
        cookie.setMaxAge(0); // Usunięcie cookie
        response.addCookie(cookie);

        return ResponseEntity.ok(Map.of("message", "Logout successful"));
    }

    @GetMapping("/check-auth")
    public ResponseEntity<?> checkAuth() {
        // Jeśli użytkownik jest zalogowany, zwraca status 200 OK
        return ResponseEntity.ok(Map.of("status", "authenticated"));
    }
}