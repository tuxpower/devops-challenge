package org.acme.domain.validator;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.acme.domain.entity.User;

public class UsernameValidator implements ConstraintValidator<Username, User> {

    @Override
    public boolean isValid(final User user, final ConstraintValidatorContext context) {
        boolean valid = false;
        if (user.getUsername() == null) {
            valid = false;
        } else {
            Pattern pattern = Pattern.compile("^[A-Za-z]*$", Pattern.CASE_INSENSITIVE);
            Matcher matcher = pattern.matcher(user.getUsername());
            valid = matcher.find();
        }
        return valid;
    }

}
