module org.employee.employee_app {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires org.kordamp.bootstrapfx.core;
    requires java.desktop;
    requires java.logging;

    opens org.employee.employee_app to javafx.fxml;
    exports org.employee.employee_app;
    exports org.employee.employee_app.models;
    opens org.employee.employee_app.models to javafx.fxml;
    exports org.employee.employee_app.controllers;
    opens org.employee.employee_app.controllers to javafx.fxml;
    exports org.employee.employee_app.validators;
    opens org.employee.employee_app.validators to javafx.fxml;
}