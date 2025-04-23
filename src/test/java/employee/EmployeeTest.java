package employee;

import org.employee.employee_app.exceptions.EmployeeNotFound;
import org.employee.employee_app.exceptions.InvalidDepartment;
import org.employee.employee_app.models.Employee;
import org.employee.employee_app.models.EmployeeDB;
import org.employee.employee_app.models.EmployeeID;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.List;
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


    @Test
    public void getEmployeesByDepartment() throws InvalidDepartment{
        String department = "IT";
        EmployeeID employeeID1 = new EmployeeID();
        EmployeeID employeeID2 = new EmployeeID();
        EmployeeID employeeID3 = new EmployeeID();
        Employee<EmployeeID> employee1 = new Employee<>(employeeID1,"Roger",department,2000,4,3,true);
        Employee<EmployeeID> employee2 = new Employee<>(employeeID2,"Roger",department,2000,4,3,true);
        Employee<EmployeeID> employee3 = new Employee<>(employeeID3,"Roger",department,2000,4,3,true);
        employeeDatabase.addEmployee(employee1);
        employeeDatabase.addEmployee(employee2);
        employeeDatabase.addEmployee(employee3);

        List <Employee<EmployeeID>> employees = employeeDatabase.getEmployeesByDepartment(department);

        for(Employee<EmployeeID> employee: employees){
            assertEquals(department, employee.getDepartment());
        }



    }



}
