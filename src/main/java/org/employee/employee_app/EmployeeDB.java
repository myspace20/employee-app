package org.employee.employee_app;

import java.util.*;
import java.util.stream.Collectors;

public class EmployeeDB<T, E> {

    private final Map<T,Employee<T>> employeeDB = new HashMap<>();

    public void addEmployee(Employee<T> e){
        employeeDB.put(e.employeeId, e);
    }


    public ArrayList<Employee<T>> getAllEmployees(){

        return  new ArrayList<>(employeeDB.values());
    }

    public void removeEmployee(T employeeId){
        employeeDB.remove(employeeId);
    }

    public void updateEmployeeDetails(T employeeId, String field, Object newValue) throws Exception {
        if (employeeId == null){
            throw new IllegalArgumentException("Employee Id is required");
        }
        if (field == null || field.isEmpty()){
            throw new IllegalArgumentException("Employee field is required");
        }
        if(newValue == null){
            throw new IllegalArgumentException("Employee update value must not be null");
        }
        Employee<T> currentEmployee = employeeDB.get(employeeId);
        if (currentEmployee == null) {
            throw new Exception("Employee does not exist");
        }

        Employee<T> updatedEmployee = getTEmployee(field, newValue, currentEmployee);

        boolean replaced = employeeDB.replace(employeeId, currentEmployee, updatedEmployee);
        if (!replaced) {
            throw new Exception("Employee update failed");
        }
    }

    private static <T> Employee<T> getTEmployee(String field, Object newValue, Employee<T> currentEmployee) {
        Employee<T> updatedEmployee = new Employee<>(
                currentEmployee.getEmployeeId(),
                currentEmployee.getName(),
                currentEmployee.getDepartment(),
                currentEmployee.getSalary(),
                currentEmployee.getPerformanceRating(),
                currentEmployee.getYearsOfExperience(),
                currentEmployee.isActive()
        );

        switch (field.trim().toLowerCase()) {
            case "name" -> updatedEmployee.setName((String) newValue);
            case "department" -> updatedEmployee.setDepartment((String) newValue);
            case "salary" -> updatedEmployee.setSalary((Double) newValue);
            case "performance" -> updatedEmployee.setPerformanceRating((Double) newValue);
            case "experience" -> updatedEmployee.setYearsOfExperience((Integer) newValue);
            case "active" -> updatedEmployee.setActive((Boolean) newValue);
            default -> {
                throw new IllegalArgumentException("Invalid employee data field");
            }
        }
        return updatedEmployee;
    }


    public List<Employee<T>> getEmployeesByDepartment(String department){
        if(department == null || department.isEmpty()){
            throw new IllegalArgumentException("Invalid input: Department must not be null");
        }
       return employeeDB.values().stream()
                .filter(emp -> emp.getDepartment().equals(department))
                .toList();
    }

    public List<Employee<T>> getEmployeesByName(String name){
        if(name == null || name.isEmpty()){
            throw new IllegalArgumentException("Invalid input: Name must not be null");
        }
        return employeeDB.values().stream()
                .filter(emp -> emp.getName().equals(name))
                .toList();
    }


    public List<Employee<T>> getEmployeesInSalaryRange(double min, double max){
        if(max <= 0){
            throw new IllegalArgumentException("Maximum must greater than 0");
        }
        if(min <= 0 ){
            throw new IllegalArgumentException("Minimum must greater than 0");
        }
        return employeeDB.values().stream()
                .filter(emp ->emp.getSalary() >= min && emp.getSalary() <= max)
                .toList();
    }

    public List<Employee<T>> getEmployeesByRating(double rating){
        if(rating <= 0){
            throw new IllegalArgumentException("Rating value must greater than 0");
        }
        return employeeDB.values().stream()
                .filter(emp ->emp.getPerformanceRating() >=rating).toList();
    }


    public void salaryRaiseByRatePercent(double rating, double percent){
        if(rating <= 0){
            throw new IllegalArgumentException("Range values must greater than 0");
        }
        if(percent <= 0){
            throw new IllegalArgumentException("Range values must greater than 0");
        }
        employeeDB.values().stream()
                .filter(e -> e.getPerformanceRating() >= rating)
                .forEach(e -> e.setSalary(e.getSalary() * (1 + percent / 100.0)));
    }


    public List<Employee<T>> getTopFiveHighestPaidEmployees(){

        return employeeDB.values().stream().sorted(Employee.EmployeeSalaryComparator).limit(5).toList();
    }


    public List<Employee<T>> sortEmployeesByRating(){
        return employeeDB.values().stream().sorted(Employee.EmployeePerformanceComparator).toList();
    }


    public List<Employee<T>> sortEmployeesSalaryHighestFirst(){
        return employeeDB.values().stream().sorted(Employee.EmployeeSalaryComparator).toList();
    }



    public double getAverageSalaryByDepartment(String department) {
        if(department == null || department.isEmpty()){
            throw new IllegalArgumentException("Invalid input: Department name must not be null");
        }
        return employeeDB.values().stream()
                .filter(e -> e.getDepartment().equalsIgnoreCase(department))
                .mapToDouble(Employee::getSalary)
                .average()
                .orElse(0.0);
    }

    public void displayEmployeesStream() {
        System.out.printf("%-10s %-20s %-15s %-10s %-18s %-10s %-10s%n",
                "ID", "Name", "Department", "Salary", "Performance Rating", "Years", "Active");
        System.out.println("-------------------------------------------------------------------------------------------");

        employeeDB.values().stream()
                .map(e -> String.format("%-10s %-20s %-15s %-10.2f %-18.1f %-10d %-10s",
                        e.getEmployeeId(), e.getName(), e.getDepartment(), e.getSalary(),
                        e.getPerformanceRating(), e.getYearsOfExperience(), e.isActive()))
                .forEach(System.out::println);
    }

    public void displayEmployeesForEach() {
        System.out.printf("%-10s %-20s %-15s %-10s %-18s %-10s %-10s%n",
                "ID", "Name", "Department", "Salary", "Performance Rating", "Years", "Active");
        System.out.println("-------------------------------------------------------------------------------------------");

        for (Employee<T> e : employeeDB.values()) {
            System.out.printf("%-10s %-20s %-15s %-10.2f %-18.1f %-10d %-10s%n",
                    e.getEmployeeId(), e.getName(), e.getDepartment(), e.getSalary(),
                    e.getPerformanceRating(), e.getYearsOfExperience(), e.isActive());
        }
    }


    public  void displayDepartmentSalaryAnalytics() {
        System.out.println("DEPARTMENT-BASED SALARY ANALYTICS");
        System.out.println("--------------------------------------------------------------------------");

        Map<String, List<Employee>> grouped = employeeDB.values().stream()
                .collect(Collectors.groupingBy(Employee::getDepartment));

        for (Map.Entry<String, List<Employee>> entry : grouped.entrySet()) {
            String dept = entry.getKey();
            List<Employee> employees = entry.getValue();

            double totalSalary = employees.stream().mapToDouble(Employee::getSalary).sum();
            double avgSalary = employees.stream().mapToDouble(Employee::getSalary).average().orElse(0);
            double maxSalary = employees.stream().mapToDouble(Employee::getSalary).max().orElse(0);
            double minSalary = employees.stream().mapToDouble(Employee::getSalary).min().orElse(0);

            System.out.printf(" %-15s | Total: $%-10.2f | Avg: $%-10.2f | Max: $%-10.2f | Min: $%-10.2f%n",
                    dept, totalSalary, avgSalary, maxSalary, minSalary);
        }
    }





}
