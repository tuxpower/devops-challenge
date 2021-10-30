package org.acme.domain.assembler;

import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;

import org.acme.api.database.entity.UserEntity;
import org.acme.domain.entity.User;

import static org.junit.Assert.assertEquals;

/**
 * Test class for {@link DomainAssembler}.
 */
@ExtendWith(MockitoExtension.class)
public class DomainAssemblerTest {
    
    private static final String USERNAME= "johndoe";
    
    private static final LocalDate DATE_OF_BIRTH = LocalDate.of(1978, 9, 2);
    
    @InjectMocks
    private DomainAssembler assembler;
    
    @Nested
    class AssembleUser {
        @Test
        void shouldAssembleToDatabaseUserEntity() {
            // data
            UserEntity expected = new UserEntity();
            expected.setUsername(USERNAME);
            expected.setDateOfBirth(DATE_OF_BIRTH);

            // action and verify
            assertEquals(expected, assembler.assembleUser(new User(USERNAME, DATE_OF_BIRTH)));
        }
        
        @Test
        void shouldAssembleToDomainUserEntity() {
            // data
            UserEntity databaseEntity = new UserEntity();
            databaseEntity.setUsername(USERNAME);
            databaseEntity.setDateOfBirth(DATE_OF_BIRTH);
            
            // expectation
            User expected = new User(USERNAME, DATE_OF_BIRTH);
            
            // action and verify
            assertEquals(expected, assembler.assembleUser(databaseEntity));
        }
    }

}
