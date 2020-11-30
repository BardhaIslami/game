package com.assignment.app.appcontroller.instructions;

import java.util.Optional;

public abstract class ChainingInstructions<O> {
	
	/*
     * Next command that will be called after current one is processed.
     */
    protected Optional<ChainingInstructions<O>> next = Optional.empty();

     // Execute the command or the instruction
    
    public abstract void execute(O info);

    // Add the next command

    public void setNext(ChainingInstructions<O> next) {
        this.next = Optional.of(next);
    }

    protected void next(O info) {
        next.ifPresent(i -> i.execute(info));
    }

}
