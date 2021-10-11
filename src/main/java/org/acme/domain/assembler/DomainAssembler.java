package org.acme.domain.assembler;

import javax.enterprise.context.ApplicationScoped;
import javax.validation.constraints.NotNull;

import org.acme.api.database.entity.UserEntity;
import org.acme.domain.entity.User;

@ApplicationScoped
public class DomainAssembler {
    
    /**
     * Assembles domain entity user to a database entity.
     * 
     * @param username the username of the user
     * @param origin the domain entity user
     * 
     * @return {@link UserEntity}
     */
    public UserEntity assembleUser(@NotNull final String username, @NotNull final User origin) {
        UserEntity destiny = new UserEntity();
        destiny.setUsername(username);
        destiny.setDateOfBirth(origin.getDateOfBirth());
        return destiny;
    }
    
    /**
     * Assembles database entity user to a domain entity.
     * 
     * @param origin the database entity user
     * 
     * @return an assembled domain entity user
     */
    public User assembleUser(@NotNull final UserEntity origin) {
        return new User(origin.getUsername(), origin.getDateOfBirth());
    }

}
