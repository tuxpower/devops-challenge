package org.acme.domain.validator;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.Payload;

/**
 * 
 * Annotation to validate the date of birth of the User.
 * 
 */
@Documented
@Target({ElementType.METHOD, ElementType.FIELD, ElementType.ANNOTATION_TYPE,
  ElementType.CONSTRUCTOR, ElementType.PARAMETER, ElementType.TYPE_USE})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = { DateOfBirthValidator.class })
public @interface DateOfBirth {
    
    /**
     * Default message.
     * 
     * @return default message
     */
    String message() default "Date of birth not valid.";

    /**
     * Default groups.
     * 
     * @return default groups.
     */
    Class<?>[] groups() default {};

    /**
     * Default payload.
     * 
     * @return default payload.
     */
    Class<? extends Payload>[] payload() default {};

}
