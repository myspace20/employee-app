package org.employee.employee_app.models;

import java.util.Comparator;

public class Employee<T> implements Comparable<Employee<T>>{

    T employeeId;
    String name;
    String department;
    double salary;
    double performanceRating;
    int yearsOfExperience;
    boolean isActive;


    public Employee(T employeeId, String name, String department, double salary, double performanceRating, int yearsOfExperience, boolean isActive){
        this.employeeId = employeeId;
        this.name = name;
        this.department = department;
        this.salary = salary;
        this.performanceRating = performanceRating;
        this.yearsOfExperience = yearsOfExperience;
        this.isActive = isActive;
    }


    @Override
    public String toString() {
        return "Employee {" +
                "ID = " + employeeId +
                ", Name = '" + name + '\'' +
                ", Department = '" + department + '\'' +
                ", Salary = " + salary +
                ", Performance Rating = " + performanceRating +
                ", Years of Experience = " + yearsOfExperience +
                ", Active = " + isActive +
                '}';
    }

    @Override
    public int compareTo(Employee<T> o) {
        return Integer.compare(o.yearsOfExperience, this.yearsOfExperience);
    }

    public String getDepartment() {
        return department;
    }

    public String getName() {
        return name;
    }

    public double getSalary(){
        return salary;
    }

    public double getPerformanceRating(){
        return performanceRating;
    }



    public static Comparator<Employee<?>> EmployeePerformanceComparator = new Comparator<>() {
        @Override
        public int compare(Employee<?> o1, Employee<?> o2) {
            return Double.compare(o2.getPerformanceRating(), o1.getPerformanceRating());
        }
    };


    public static Comparator<Employee<?>> EmployeeSalaryComparator = new Comparator<>() {
        @Override
        public int compare(Employee e1, Employee e2) {
            return Double.compare(e2.getSalary(), e1.getSalary());
        }
    };


    public void setSalary(double newSalary) {
        salary = newSalary;
    }


    public int  getYearsOfExperience() {
        return yearsOfExperience;
    }

    public boolean isActive() {
        return isActive;
    }

    public void setDepartment(String newValue) {
        department = newValue;
    }

    public void setName(String newValue){
        name = newValue;
    }

    public void setPerformanceRating(double newValue){
        performanceRating = newValue;
    }

    public void setYearsOfExperience(int newValue){
        yearsOfExperience = newValue;
    }

    public void setActive(boolean newValue){
        isActive = newValue;
    }

    public T getEmployeeId() {
        return employeeId;
    }
}

    
