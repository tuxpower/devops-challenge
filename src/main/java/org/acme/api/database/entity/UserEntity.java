package org.acme.api.database.entity;

import java.time.LocalDate;
import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import io.quarkus.hibernate.orm.panache.PanacheEntityBase;

/**
 * 
 * User Entity.
 */
@Entity(name = "user")
@Table(schema = "public")
public class UserEntity extends PanacheEntityBase {
    
    @Id
    @Column(nullable = false, name = "username")
    private String username;

    @Column(nullable = false, name = "date_of_birth")
    private LocalDate dateOfBirth;

    /**
     * Get the username.
     * 
     * @return username
     */
    public String getUsername() {
        return username;
    }

    /**
     * Set the username.
     * 
     * @param username
     */
    public void setUsername(final String username) {
        this.username = username;
    }

    /**
     * Get the Date of Birth.
     * 
     * @return dateOfBirth
     */
    public LocalDate getDateOfBirth() {
        return dateOfBirth;
    }

    /**
     * Set the Date of Birth.
     * 
     * @param dateOfBirth
     */
    public void setDateOfBirth(final LocalDate dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }
    
    @Override
    public boolean equals(final Object other) {
        if (this == other) {
            return true;
        }
        if (other == null || !getClass().equals(other.getClass())) {
            return false;
        }

        UserEntity castOther = (UserEntity) other;

        return Objects.equals(username, castOther.username) && Objects.equals(dateOfBirth, castOther.dateOfBirth);
    }

    @Override
    public int hashCode() {
        return Objects.hash(username, dateOfBirth);
    }

}
