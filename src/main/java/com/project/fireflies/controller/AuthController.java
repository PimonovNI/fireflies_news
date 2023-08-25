package com.project.fireflies.controller;

import com.project.fireflies.dto.UserCreateDto;
import com.project.fireflies.service.AuthService;
import com.project.fireflies.util.validator.UserValidator;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/auth")
public class AuthController {

    private final AuthService authService;
    private final UserValidator userValidator;

    @Autowired
    public AuthController(AuthService authService, UserValidator userValidator) {
        this.authService = authService;
        this.userValidator = userValidator;
    }

    @GetMapping("/login")
    public String showLoginPage() {
        return "/auth/login";
    }

    @GetMapping("/reg")
    public String showRegistrationPage(Model model) {
        model.addAttribute("user", new UserCreateDto());
        return "/auth/reg";
    }

    @PostMapping("/reg")
    public String registrationNewUser(@ModelAttribute("user") @Valid UserCreateDto userCreateDto, BindingResult bindingResult) {
        userValidator.validate(userCreateDto, bindingResult);
        if (bindingResult.hasErrors()) {
            return "/auth/reg";
        }

        authService.registration(userCreateDto);
        return "redirect:/auth/login";
    }

    @GetMapping("/activation/{code}")
    public String activation(@PathVariable("code") String code) {
        boolean res = authService.activation(code);
        return "redirect:/auth/login"
                + (res ? "" : "?error");
    }
}
