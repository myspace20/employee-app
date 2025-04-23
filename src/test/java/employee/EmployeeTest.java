package employee;

import org.employee.employee_app.models.EmployeeDB;
import org.employee.employee_app.models.EmployeeID;
import org.junit.jupiter.api.BeforeAll;


public class EmployeeTest {

    private static EmployeeDB<EmployeeID> employeeDatabase;

    @BeforeAll
    public static void setUp(){
        employeeDatabase = new EmployeeDB<>();
    }



}
