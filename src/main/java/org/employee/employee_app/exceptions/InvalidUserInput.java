package org.employee.employee_app.exceptions;

public class InvalidUserInput extends IllegalArgumentException {
    public InvalidUserInput(String message){
        super(message);
    }
}
