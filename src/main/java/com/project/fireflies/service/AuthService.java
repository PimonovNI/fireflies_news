package com.project.fireflies.service;

import com.project.fireflies.dto.UserCreateDto;
import com.project.fireflies.entity.User;
import com.project.fireflies.entity.enums.Role;
import com.project.fireflies.entity.enums.Status;
import com.project.fireflies.repository.UsersRepository;
import com.project.fireflies.util.FileUploadUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.sql.SQLOutput;
import java.time.Instant;
import java.util.Optional;
import java.util.UUID;

@Service
@Transactional(readOnly = true)
public class AuthService {

    @Value("${upload.path.img.avatars}")
    private String pathImgAvatars;

    @Value("${spring.site.url}")
    private String siteDomain;

    private final UsersRepository usersRepository;
    private final PasswordEncoder passwordEncoder;
    private final MailService mailService;

    @Autowired
    public AuthService(UsersRepository usersRepository, PasswordEncoder passwordEncoder, MailService mailService) {
        this.usersRepository = usersRepository;
        this.passwordEncoder = passwordEncoder;
        this.mailService = mailService;
    }

    @Transactional
    public boolean registration(UserCreateDto dto) {
        User user = mapFrom(dto);

        if (dto.getPhoto() != null) {
            try {
                String photoName = createFile(dto.getPhoto());
                user.setPhoto(photoName);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }

        String activationCode = UUID.randomUUID().toString();
        user.setActivationCode(activationCode);
        String message = String.format("Hello, %s. Welcome to your social network. " +
                        "Please verify your email, just touch following link:\n" +
                        "http://%s/auth/activation/%s",
                user.getUsername(), siteDomain, activationCode);
        mailService.send(user.getEmail(), "Verify your email in Fireflies News", message);

        usersRepository.insert(user);
        return true;
    }

    @Transactional
    public boolean activation(String code) {
        Optional<User> user = usersRepository.findByActivationCode(code);
        if (user.isEmpty()) {
            return false;
        }

        user.get().setStatus(Status.ACTIVATED);
        user.get().setActivationCode(null);
        usersRepository.save(user.get());

        return true;
    }

    private String createFile(MultipartFile file) throws IOException {
        String fileName = UUID.randomUUID() + "." + file.getOriginalFilename();
        FileUploadUtils.save(pathImgAvatars, fileName, file);
        return fileName;
    }

    private User mapFrom(UserCreateDto dto) {
        return User.builder()
                .username(dto.getUsername())
                .password(passwordEncoder.encode(dto.getPassword()))
                .email(dto.getEmail())
                .createdAt(Instant.now())
                .role(Role.USER)
                .status(Status.VERIFICATION)
                .build();
    }
}
