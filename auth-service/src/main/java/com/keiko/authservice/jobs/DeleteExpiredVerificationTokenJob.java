package com.keiko.authservice.jobs;

import com.keiko.authservice.entity.VerificationToken;
import com.keiko.authservice.service.VerificationTokenService;
import com.keiko.authservice.service.resources.UserService;
import com.keiko.commonservice.entity.resource.user.User;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class DeleteExpiredVerificationTokenJob {

    @Autowired
    private VerificationTokenService verificationTokenService;

    @Autowired
    private UserService userService;

    @Scheduled (cron = "0 0 0 1 * *")
    @Transactional
    public void delete () {
        LocalDateTime now = LocalDateTime.now ();

        List<User> users = userService.findNotEnabledUsers ();
        List<VerificationToken> expiredTokens = verificationTokenService.findExpiredTokens ();

        List<String> emails = expiredTokens.stream ()
                .map (VerificationToken::getEmail)
                .collect (Collectors.toList ());

        for (String email : emails) {
            users.stream ()
                    .filter (user -> user.getEmail ().equals (email))
                    .findFirst ()
                    .ifPresent (user -> userService.deleteByEmail (email));
        }
        verificationTokenService.deleteAll (expiredTokens);
    }
}
