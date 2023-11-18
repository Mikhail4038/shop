package com.keiko.authservice.service;

import com.keiko.authservice.entity.User;
import lombok.NonNull;

import java.time.LocalDateTime;
import java.util.List;

public interface UserService {
    void save (@NonNull User user);

    User findByEmail (String email);

    void deleteByEmail (String email);

    void deleteAll (List<User> users);

    List<User> findNotConfirmRegistration (LocalDateTime dateTime);

}
