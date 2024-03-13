package com.keiko.authservice.validation;

import com.keiko.authservice.request.RegistrationRequest;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import jakarta.validation.constraintvalidation.SupportedValidationTarget;
import jakarta.validation.constraintvalidation.ValidationTarget;
import org.springframework.stereotype.Component;

@Component
@SupportedValidationTarget (ValidationTarget.PARAMETERS)
public class PasswordMatchesValidator
        implements ConstraintValidator<PasswordMatches, Object[]> {

    @Override
    public boolean isValid (Object o[], ConstraintValidatorContext constraintValidatorContext) {
        final RegistrationRequest registrationRequest = (RegistrationRequest) o[0];
        final String presentedPassword = registrationRequest.getPassword ();
        final String passwordConfirm = registrationRequest.getPasswordConfirm ();
        return presentedPassword.equals (passwordConfirm);
    }
}
