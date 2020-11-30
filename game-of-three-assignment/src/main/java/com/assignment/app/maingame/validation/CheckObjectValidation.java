package com.assignment.app.maingame.validation;

    //Define classes that can be validated with a Validator

public interface CheckObjectValidation<T> {
	
	// Validate current object
	
    boolean validate(Validator<T> validator);

    void validOrNot(Validator<T> validator);

}
