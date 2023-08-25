package com.project.fireflies.util.validator;

import com.project.fireflies.dto.UserCreateDto;
import com.project.fireflies.entity.User;
import com.project.fireflies.repository.UsersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import java.util.Optional;

@Component
public class UserValidator implements Validator {

    private final UsersRepository usersRepository;

    @Autowired
    public UserValidator(UsersRepository usersRepository) {
        this.usersRepository = usersRepository;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return UserCreateDto.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        UserCreateDto user = (UserCreateDto) target;
        Optional<User> maybeUser = usersRepository.findByUsername(user.getUsername());
        if (maybeUser.isPresent()) {
            errors.rejectValue("username", "", "This username already contained");
        }

        if (!user.getPassword().matches(".*[a-z].*")
            || !user.getPassword().matches(".*[A-Z].*")
            || !user.getPassword().matches(".*\\d.*")) {

            errors.rejectValue("password", "",
                    "Password must contain letter in lowercase and uppercase, and least digit");
        }
    }
}
