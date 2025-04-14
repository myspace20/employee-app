module org.employee.employee_app {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires org.kordamp.bootstrapfx.core;

    opens org.employee.employee_app to javafx.fxml;
    exports org.employee.employee_app;
}