package com.project.fireflies.service;

import com.project.fireflies.entity.User;
import com.project.fireflies.entity.enums.Status;
import com.project.fireflies.repository.UsersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserDetailsService implements org.springframework.security.core.userdetails.UserDetailsService {

    private final UsersRepository usersRepository;

    @Autowired
    public UserDetailsService(UsersRepository usersRepository) {
        this.usersRepository = usersRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<User> user = usersRepository.findByUsername(username);
        if (user.isEmpty()) {
            throw new UsernameNotFoundException("Illegal username");
        }

        if (user.get().getStatus() == Status.VERIFICATION) {
            throw new UsernameNotFoundException("You must verification your email");
        }

        return new com.project.fireflies.entity.UserDetails(user.get());
    }
}
