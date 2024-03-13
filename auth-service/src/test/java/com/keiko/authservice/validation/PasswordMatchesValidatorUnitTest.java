package com.keiko.authservice.validation;

import com.keiko.authservice.request.RegistrationRequest;
import jakarta.validation.ConstraintValidatorContext;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static com.keiko.authservice.util.TestData.testRegistrationRequest;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

@ExtendWith (MockitoExtension.class)
public class PasswordMatchesValidatorUnitTest {
    private static final String INCORRECT_PASSWORD_CONFIRM = "25922";

    @Mock
    private ConstraintValidatorContext constraintValidatorContext;

    @InjectMocks
    private static PasswordMatchesValidator passwordMatchesValidator;
    private static RegistrationRequest registrationRequest;

    @BeforeAll
    static void setUp () {
        registrationRequest = testRegistrationRequest ();
        passwordMatchesValidator = new PasswordMatchesValidator ();
    }

    @Test
    void should_successfully_password_matches () {
        registrationRequest.setPasswordConfirm (registrationRequest.getPassword ());
        Object[] objects = {registrationRequest};
        boolean isMatch
                = passwordMatchesValidator.isValid (objects, constraintValidatorContext);
        assertTrue (isMatch);
    }

    @Test
    void should_unSuccessfully_password_not_matches () {
        registrationRequest.setPasswordConfirm (INCORRECT_PASSWORD_CONFIRM);
        Object[] objects = {registrationRequest};
        boolean isMatch
                = passwordMatchesValidator.isValid (objects, constraintValidatorContext);
        assertFalse (isMatch);
    }
}
