package org.acme.domain.service;

import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import org.acme.domain.assembler.DomainAssembler;
import org.acme.domain.entity.User;
import org.acme.api.database.entity.UserEntity;
import org.acme.api.database.repository.UserRepository;

import java.time.LocalDate;
import java.util.stream.Stream;

import javax.validation.Validation;
import javax.validation.executable.ExecutableValidator;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.only;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

/**
 * Unit test for {@link UserCrud}.
 */
@ExtendWith(MockitoExtension.class)
public class UserCrudTest {
    
    @InjectMocks
    private UserCrud userCrud;

    @Mock
    private UserRepository repository;

    @Mock
    private DomainAssembler assembler;

    private static ExecutableValidator validator = Validation.buildDefaultValidatorFactory().getValidator()
            .forExecutables();
    
    @Nested
    class GetUser {
        
        @Test
        void shouldReadUser() {
            // data
            String username = "johndoe";
            User user = new User("johndoe", LocalDate.of(1978, 9, 2));
            UserEntity userEntity = new UserEntity();
            
            //expectation
            doReturn(Stream.of(userEntity)).when(repository).stream("username", username);
            doReturn(user).when(assembler).assembleUser(userEntity);
            
            // action
            userCrud.read(username).toString();
            
            // verification
            verify(assembler, only()).assembleUser(userEntity);
            
        }
        
        @Test
        void shouldThrowConstraintViolationExceptionWhenUsernameIsNull() throws NoSuchMethodException, SecurityException {
            assertFalse(validator.validateParameters(userCrud, UserCrud.class.getDeclaredMethod("read", String.class),
                    new Object[] { null }).isEmpty());
        }
    }
    
    @Nested
    class PutUser {
        
        @Test
        void shouldPutUser() {
            // data
            UserEntity userEntity = new UserEntity();
            
            // action
            repository.persist(userEntity);
            verify(repository, times(1)).persist(any(UserEntity.class));
            verify(assembler, never()).assembleUser(any(), any());
        }
        
        @Test
        void shouldThrowConstraintViolationExceptionWhenUsernameIsNull() throws NoSuchMethodException, SecurityException {
            assertFalse(validator.validateParameters(userCrud, UserCrud.class.getDeclaredMethod("persist", String.class, User.class),
                    new Object[] { null, new User("johndoe", LocalDate.of(1978, 9, 2)) }).isEmpty());
        }
        
        @Test
        void shouldThrowConstraintViolationExceptionWhenDateOfBirthIsNull() throws NoSuchMethodException, SecurityException {
            assertFalse(validator.validateParameters(userCrud, UserCrud.class.getDeclaredMethod("persist", String.class, User.class),
                    new Object[] { "johndoe", null }).isEmpty());
        }
    }

}
