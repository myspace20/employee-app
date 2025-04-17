package org.employee.employee_app;

import java.util.Random;

public class EmployeeID {

    public final String id;

    public  EmployeeID(){
        Random rand = new Random();
        StringBuilder card = new StringBuilder("A");
        for (int i = 0; i < 7; i++)
        {
            int n = rand.nextInt(10);
            card.append(Integer.toString(n));
        }

        this.id =  card.toString();
    }

    public String getId() {
        return id;
    }

    @Override
    public String toString() {
        return id;
    }
}
