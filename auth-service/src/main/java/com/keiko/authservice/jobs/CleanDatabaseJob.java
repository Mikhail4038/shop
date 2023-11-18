package com.keiko.authservice.jobs;

import com.keiko.authservice.entity.User;
import com.keiko.authservice.service.UserService;
import com.keiko.authservice.service.VerificationTokenService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;

@Component
public class CleanDatabaseJob {

    @Autowired
    private VerificationTokenService verificationTokenService;

    @Autowired
    private UserService userService;

    @Scheduled (cron = "0 0 0 1 * *")
    @Transactional
    public void deleteExpiredVerificationToken () {
        LocalDateTime now = LocalDateTime.now ();

        List<User> users = userService.findNotConfirmRegistration (now);
        verificationTokenService.deleteExpiredToken (now);
        userService.deleteAll (users);
    }
}
