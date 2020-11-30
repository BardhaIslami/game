package com.assignment.app.maingame.gamemoves.playinglogic.validation;

import com.assignment.app.maingame.validation.Validator;

	// Interface to create customized with validators.

public interface CustomizedValidators<T> {
	
	// Add validators to current object

    void addValidator(Validator<T> validator);

}
