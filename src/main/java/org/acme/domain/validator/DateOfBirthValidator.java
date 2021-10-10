package org.acme.domain.validator;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.acme.domain.entity.User;

public class DateOfBirthValidator implements ConstraintValidator<DateOfBirth, User> {

    @Override
    public boolean isValid(final User user, final ConstraintValidatorContext context) {
        LocalDate today = LocalDate.now();
        return ChronoUnit.DAYS.between(user.getDateOfBirth(), today) > 0;
    }

}
