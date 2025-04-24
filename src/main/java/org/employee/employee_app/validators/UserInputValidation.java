package org.employee.employee_app.validators;

import org.employee.employee_app.exceptions.InvalidSalary;
import org.employee.employee_app.exceptions.InvalidUserInput;

import java.util.logging.Logger;

public class UserInputValidation {


    public static <T> void validateCreateUserInput(T employeeId, String name, String department, double salary, double performanceRating, int yearsOfExperience) throws InvalidSalary {
      validateInputField(name,"Name");

      validateInputField(department, "Department");

      UserInputValidation.validDateSalary(salary, "Salary");
      UserInputValidation.validateForZeroValues(performanceRating, "Performance rating");
      UserInputValidation.validateForZeroValues(yearsOfExperience, "Years of experience");

    }

    public static void validDateSalary(Double salary, String field) throws  InvalidSalary{
        if (isNullOrNegativeOrZero(salary)){
            throw new InvalidSalary("Invalid salary value for, " + field + "please try again");
        }
    }


    public static  void validateGetEmployeeSalaryInRangeInput(Double min, Double max) throws InvalidSalary {
      validDateSalary(min, "Minimum salary");
      validDateSalary(max, "Maximum salary");
    }

    public static void validateRaiseByPercentInput(Double rating, Double percent){
        if(isNullOrNegativeOrZero(percent)){
            throw new InvalidUserInput("Range values must greater than 0");
        }
        if(isNullOrNegativeOrZero(rating)){
            throw new InvalidUserInput("Rating values must greater than 0");
        }
    }

    public static void validateInputField(String value, String field){
        if(isEmptyOrNullString(value)){
            throw new InvalidUserInput(field + " must not be empty");
        }

    }

    public static  void validateForZeroValues(Double value, String field){
        if (isNullOrNegativeOrZero(value)){
            throw new InvalidUserInput(field + " must be valid");
        }
    }

    public static  void validateForZeroValues(Integer value, String field){
        if (isNullOrNegativeOrZero(value)){
            throw new InvalidUserInput(field + " must be valid");
        }
    }


    static boolean isNullOrNegativeOrZero(Double value){
        return value == null || value <= 0;
    }

    static boolean isNullOrNegativeOrZero(Integer value){
        return value == null || value <= 0;
    }


    static  boolean isEmptyOrNullString(String value){
        return value == null || value.isEmpty();
    }

}
