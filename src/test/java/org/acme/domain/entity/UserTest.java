package org.acme.domain.entity;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertFalse;

import java.time.LocalDate;

import javax.validation.Validation;
import javax.validation.Validator;

/**
 * Test class for {@link User}.
 */
@ExtendWith(MockitoExtension.class)
public class UserTest {

    private static Validator validator = Validation.buildDefaultValidatorFactory().getValidator();
    
    @Test
    public void shouldHaveNonNullUsername() {
        assertFalse(validator.validate(new User(null, LocalDate.of(1978, 9, 2))).isEmpty());
    }
    
    @Test
    public void shouldHaveNonNullDateOfBirth() {
        assertFalse(validator.validate(new User("johndoe", null)).isEmpty());
    }
}
