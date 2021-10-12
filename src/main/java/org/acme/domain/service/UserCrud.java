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
    
    /**
     * Create user related information.
     * 
     * @param username the username of the user
     * 
     * @param user the user date of birth
     */
    @Transactional
    public void persist(
            @NotNull
            final String username,
            
            @NotNull
            final User user) {
        
        UserEntity userToPersist = assembler.assembleUser(username, user);
        
        repository.persist(userToPersist); 
    }
    
    /**
     * Retrieves user related information.
     * 
     * @param username the username of the user
     * 
     * @return String the information about the user birthday
     */
   public String read(
            @NotNull
            final String username) {
        User user = repository.stream("username", username)
                .findFirst()
                .map(assembler::assembleUser)
                .orElseThrow(NotFoundException::new);
        
        return checkBirthday(user);
    }
    
    private String checkBirthday(final User user) {
        StringBuilder message = new StringBuilder();
        LocalDate dateOfBirth = user.getDateOfBirth();
        LocalDate today = LocalDate.now();
        
        int todayMonth = today.getMonthValue();
        int todayDayOfMounth = today.getDayOfMonth();
        
        /**
         * Check first if today is user birthday else calculate N days
         */
        if (dateOfBirth.getMonthValue() == todayMonth && dateOfBirth.getDayOfMonth() == todayDayOfMounth) {
            message.append("{\"message\": \"Hello, " + user.getUsername() + "! Happy Birthday!\"}");
        } else {
            message.append("{\"message\": \"Hello, " + user.getUsername() + "! Your birthday is in "  
                    + nDays(dateOfBirth, today) + " day(s)\"}");
        }
        
        return message.toString();
    }
    
    private long nDays(final LocalDate dateOfBirth, final LocalDate today) {
        LocalDate nextBirthday;
        
        /**
         * Check if birthday is still this year, else next year.
         * 
         */
        if (today.getMonthValue() < dateOfBirth.getMonthValue()
                || (today.getMonthValue() == dateOfBirth.getMonthValue() 
                && today.getDayOfMonth() < dateOfBirth.getDayOfMonth())) {
            nextBirthday = LocalDate.of(today.getYear(), dateOfBirth.getMonthValue(), dateOfBirth.getDayOfMonth());
            
        } else {
            nextBirthday = LocalDate.of(today.plusYears(1).getYear(), dateOfBirth.getMonthValue(), dateOfBirth.getDayOfMonth());
        }
        
        return ChronoUnit.DAYS.between(today, nextBirthday);
    }

}
