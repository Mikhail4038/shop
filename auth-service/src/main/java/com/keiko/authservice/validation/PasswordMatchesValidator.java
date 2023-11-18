package com.keiko.authservice.validation;

import com.keiko.authservice.entity.User;
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
        final User user = (User) o[0];
        final String presentedPassword = user.getPassword ();
        final String passwordConfirm = user.getPasswordConfirm ();
        return presentedPassword.equals (passwordConfirm);
    }
}
