package employee;

import org.employee.employee_app.exceptions.EmployeeNotFound;
import org.employee.employee_app.models.Employee;
import org.employee.employee_app.models.EmployeeDB;
import org.employee.employee_app.models.EmployeeID;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;


public class EmployeeTest {

    private static EmployeeDB<EmployeeID> employeeDatabase;

    @BeforeAll
    public static void setUp(){
        employeeDatabase = new EmployeeDB<>();
    }


    @Test
    public void AddEmployeeTest() throws EmployeeNotFound {
        EmployeeID employeeID1 = new EmployeeID();
        Employee<EmployeeID> employee1 = new Employee<>(employeeID1,"Roger","IT",2000,4,3,true);
        employeeDatabase.addEmployee(employee1);
        Optional<Employee<EmployeeID>> retrievedEmployee = employeeDatabase.retrieveEmployeeById(employeeID1);
        assertEquals(employee1, retrievedEmployee.get());
    }



}
