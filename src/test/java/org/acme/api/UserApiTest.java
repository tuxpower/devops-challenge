package org.acme.api;

import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import java.time.LocalDate;

import javax.validation.ConstraintViolationException;
import javax.validation.Validation;
import javax.validation.executable.ExecutableValidator;
import javax.ws.rs.NotFoundException;
import javax.ws.rs.core.Response;

import org.acme.domain.entity.User;
import org.acme.domain.service.UserCrud;

import static org.mockito.Mockito.doReturn;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.doThrow;

/**
 * Test class for {@link UserApi}.
 */
@ExtendWith(MockitoExtension.class)
public class UserApiTest {
    
    @InjectMocks
    private UserApi api;
    
    @Mock
    private UserCrud userCrud;
    
    private static ExecutableValidator validator = Validation.buildDefaultValidatorFactory().getValidator().forExecutables();
    
    @Nested
    class GetUser {
        @Test
        void shouldReturnSuccess() {
            // data
            User expected = User.builder()
                    .withUsername("johndoe")
                    .withDateOfBirth(LocalDate.of(1978, 9, 2))
                    .build();
            
            // expectation
            doReturn(expected).when(userCrud).read("johndoe");
            
            // action
            Response result = api.getUser("johndoe");
            
            // verification
            assertEquals(expected, result.getEntity());
        }
        
        @Test
        void shouldHaveValidUsername() throws NoSuchMethodException, SecurityException {
            assertFalse(validator.validateParameters(api, UserApi.class.getDeclaredMethod("getUser", String.class), new Object[]
                    {"johndoe1"}).isEmpty());
        }
        
        @Test
        void shouldReturnResponseWithStatusResourceNotFound() {
            doThrow(NotFoundException.class).when(userCrud).read("inexistentuser");
            
            assertThrows(NotFoundException.class, () -> api.getUser("inexistentuser"));
        }
        
        @Test
        void shouldReturnResponseWithBadRequestIfUsernameNotProvided() {       
            doThrow(ConstraintViolationException.class).when(userCrud).read("johndoe");
            
            assertThrows(ConstraintViolationException.class, () -> api.getUser("johndoe"));
        }
    }
    
    @Nested
    class PostUser {
        @Test
        void shouldReturnSuccess() {
            // data
            User user = new User("johndoe", LocalDate.of(1978, 9, 2));
            User expected = User.builder()
                    .withUsername("johndoe")
                    .withDateOfBirth(LocalDate.of(1978, 9, 2))
                    .build();
            
            // expectation
            doReturn(expected).when(userCrud).persist(user);
            
            // action
            Response result = api.postUser(user);
            
            // verification
            assertEquals(201, result.getStatus());
            assertEquals(expected, result.getEntity());
        }
        
        @Test
        void shouldHaveValidUsername() throws NoSuchMethodException, SecurityException {
            assertFalse(validator.validateParameters(api, UserApi.class.getDeclaredMethod("postUser", User.class), new Object[]
                    {new User("johndoe1", LocalDate.of(1978, 9, 2))}).isEmpty());
        }
        
        @Test
        void shouldHaveBirthdayBeforeToday() throws NoSuchMethodException, SecurityException {
            assertFalse(validator.validateParameters(api, UserApi.class.getDeclaredMethod("postUser", User.class), new Object[]
                    {new User("johndoe", LocalDate.now().plusDays(1))}).isEmpty());
        }
        
        @Test
        void shouldReturnResponseWithBadRequestIfUsernameNotProvided() {
            User user = User.builder().withDateOfBirth(LocalDate.of(1978, 9, 2)).build();
            
            doThrow(ConstraintViolationException.class).when(userCrud).persist(user);
            
            assertThrows(ConstraintViolationException.class, () -> api.postUser(user));
        }
        
        @Test
        void shouldReturnResponseWithBadRequestIfDateOfBirthNotProvided() {
            User user = User.builder().withUsername("johndoe").build();
            
            doThrow(ConstraintViolationException.class).when(userCrud).persist(user);
            
            assertThrows(ConstraintViolationException.class, () -> api.postUser(user));
        }
    }

}
