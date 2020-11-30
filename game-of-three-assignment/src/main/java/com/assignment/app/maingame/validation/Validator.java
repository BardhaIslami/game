package com.assignment.app.maingame.validation;

import java.util.List;

import com.assignment.app.maingame.exceptions.ConfirmationException;

/* Interface to create validators that can be used
 * by classes that implement CheckObjectValidation
 */

public interface Validator<T> {
	
	 // Validates object
     
    boolean validate(T object);

    // Throws runtime exception if not valid

    void validOrNot(T object) throws ConfirmationException;

    // Get all validation messages 
     
    List<String> getValidationMsg();

}
