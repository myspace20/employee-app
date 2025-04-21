package org.employee.employee_app.exceptions;

public class FailedUpdateOperation extends Exception{
    public FailedUpdateOperation(String message){
        super(message);
    }
}
