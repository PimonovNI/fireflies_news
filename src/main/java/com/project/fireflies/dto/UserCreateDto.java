package com.project.fireflies.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.*;
import org.springframework.web.multipart.MultipartFile;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class UserCreateDto {

    @NotEmpty(message = "Enter your username")
    @Size(min = 2, max = 128, message = "Username must be greater 2 and less 128")
    private String username;

    @NotEmpty(message = "Enter your password")
    @Size(min = 5, max = 128, message = "Password must be greater 5 and less 128")
    private String password;

    @Email(message = "Illegal format")
    @NotEmpty(message = "Enter your email")
    private String email;

    private MultipartFile photo;
}
