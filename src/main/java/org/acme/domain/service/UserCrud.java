package org.acme.domain.service;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

import javax.enterprise.context.Dependent;
import javax.inject.Inject;
import javax.transaction.Transactional;
import javax.validation.constraints.NotNull;
import javax.ws.rs.NotFoundException;

import org.acme.api.database.entity.UserEntity;
import org.acme.domain.assembler.DomainAssembler;
import org.acme.domain.entity.User;
import org.json.JSONObject;
import org.acme.api.database.repository.UserRepository;

/**
 * User service.
 */
@Dependent
public class UserCrud {
    
    @Inject
    DomainAssembler assembler;
    
    @Inject
    UserRepository repository;
    
    private int todayMonth = LocalDate.now().getMonthValue();
    private int todayDayOfMounth = LocalDate.now().getDayOfMonth();
    
    /**
     * Create user related information.
     * 
     * 
     * @param user the user date of birth
     * 
     * @return User {@link User}
     */
    @Transactional
    public User persist(

            @NotNull
            final User user) {
        
        UserEntity userToPersist = assembler.assembleUser(user);
        
        repository.persist(userToPersist);
        return assembler.assembleUser(userToPersist); 
    }
    
    /**
     * Retrieves user related information.
     * 
     * @param username the username of the user
     * @return {@link User}
     */
    public User read(
            @NotNull
            final String username) {
        return repository.stream("username", username)
                .findFirst()
                .map(assembler::assembleUser)
                .orElseThrow(NotFoundException::new);
    }
    
    /**
     * Retrieves user birthday related information.
     * 
     * @param username the username of the user
     * 
     * @return String the information about the user birthday
     */
   public JSONObject checkBirthday(
            @NotNull
            final String username) {
        User user = repository.stream("username", username)
                .findFirst()
                .map(assembler::assembleUser)
                .orElseThrow(NotFoundException::new);
        
        return checkBirthday(user);
    }
    
    private JSONObject checkBirthday(final User user) {
        LocalDate dateOfBirth = user.getDateOfBirth();  
        
        String hello = "Hello, " + user.getUsername() + "!";
        
        return new JSONObject()
                .put("message", isBirtyhdayToday(user.getDateOfBirth()) ? hello + " Happy Birthday!" 
                        : hello + " Your birthday is in " + nDays(dateOfBirth) + " day(s)");
    }
    
    private boolean isBirtyhdayToday(final LocalDate dateOfBirth) {
        return dateOfBirth.getMonthValue() == todayMonth && dateOfBirth.getDayOfMonth() == todayDayOfMounth;
    }
    
    private long nDays(final LocalDate dateOfBirth) {
        LocalDate nextBirthday;
        int birthdayYear; 
        
        /**
         * Check if birthday is still this year, else next year.
         * 
         */
        birthdayYear = (birthDayAlreadyHappenedThisYear(dateOfBirth)) ? LocalDate.now().plusYears(1).getYear()
                : LocalDate.now().getYear();
        
        /**
         * Set the next birthday
         */
        nextBirthday = LocalDate.of(birthdayYear, dateOfBirth.getMonthValue(), dateOfBirth.getDayOfMonth());
        
        return ChronoUnit.DAYS.between(LocalDate.now(), nextBirthday);
    }
    
    private boolean birthDayAlreadyHappenedThisYear(final LocalDate dateOfBirth) {
        return LocalDate.now().isAfter(
                LocalDate.now().withMonth(dateOfBirth.getMonthValue()).withDayOfMonth(dateOfBirth.getDayOfMonth()));
    }

}
