package com.keiko.authservice.service.impl;

import com.keiko.authservice.entity.User;
import com.keiko.authservice.exception.model.UserNotFoundException;
import com.keiko.authservice.repository.UserRepository;
import com.keiko.authservice.service.UserService;
import lombok.NonNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class DefaultUserService implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public void save (@NonNull User user) {
        userRepository.save (user);
    }

    @Override
    public User findByEmail (String email) {
        return userRepository.findByEmail (email).orElseThrow (
                () -> new UserNotFoundException (String.format ("User with email: %s not found", email)));
    }

    @Override
    public void deleteByEmail (String email) {
        userRepository.deleteByEmail (email);
    }

    @Override
    public void deleteAll (List<User> users) {
        userRepository.deleteAll (users);
    }

    @Override
    public List<User> findNotConfirmRegistration (LocalDateTime dateTime) {
        List<User> users = userRepository.findByEnabledIsAndVerificationToken_expiryDateLessThan (false, dateTime);
        return users;
    }
}
