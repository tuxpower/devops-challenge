package org.acme.domain.entity;

import java.time.LocalDate;

import javax.validation.constraints.NotNull;

import org.eclipse.microprofile.openapi.annotations.media.Schema;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * User entity.
 * 
 * Holds the base information of a user.
 */
@Schema(name = "User",  description = "User entity")
public class User {
    
    /**
     * User date of birth property name.
     */
    public static final String DATE_OF_BIRTH = "dateOfBirth";
    
    /**
     * Username.
     */
    @NotNull
    private String username;
    
    /**
     * Date of Birth.
     */
    @NotNull
    @JsonProperty(User.DATE_OF_BIRTH)
    @Schema(name = User.DATE_OF_BIRTH, description = "The user date of birth", required = true)
    private final LocalDate dateOfBirth;
    
    /**
     * User constructor.
     * 
     * @param dateOfBirth date of birth of the User
     */
    @JsonCreator
    public User(@JsonProperty(User.DATE_OF_BIRTH) final LocalDate dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }
    
    /**
     * User constructor.
     * 
     * @param username      username of the User
     * @param dateOfBirth   date of birth of the User
     */
    public User(final String username, final LocalDate dateOfBirth) {
        this.username = username;
        this.dateOfBirth = dateOfBirth;
    }  
    
    /**
     * Private constructor to create an object with a Builder.
     * 
     * @param builder User builder.
     */
    private User(final Builder builder) {
        this.username = builder.username;
        this.dateOfBirth = builder.dateOfBirth;
    }

    /**
     * Get the username of the User.
     * 
     * @return username
     */
    public String getUsername() {
        return username;
    }

    /**
     * Get the date of birth of the User.
     * 
     * @return date of birth
     */
    public LocalDate getDateOfBirth() {
        return dateOfBirth;
    }
    
    /**
     * Private constructor to create an object with a Builder.
     * 
     * @return created builder
     */
    public static Builder builder() {
        return new Builder();
    }
    
    /**
     * Builder to build {@link User}.
     */
    public static final class Builder {
        private String username;
        private LocalDate dateOfBirth;
        
        private Builder() {}
        
        /**
         * Adds username.
         * 
         * @param username the username to add
         * @return the {@link User.Builder}
         */
        public Builder withUsername(final String username) {
            this.username = username;
            return this;
        }
        
        /**
         * Adds birthday.
         * 
         * @param dateOfBirth the birthday to add
         * @return the {@link User.Builder}
         */
        public Builder withDateOfBirth(final LocalDate dateOfBirth) {
            this.dateOfBirth = dateOfBirth;
            return this;
        }
        
        /**
         * Return instance.
         * 
         * @return {@link User}
         */
        public User build() {
            return new User(this);
        }
    }

}
