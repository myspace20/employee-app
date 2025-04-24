package org.employee.employee_app.controllers;

import javafx.beans.property.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.SortedList;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.util.Callback;

import javafx.scene.layout.GridPane;
import org.employee.employee_app.validators.UserInputValidation;
import org.employee.employee_app.models.Employee;
import org.employee.employee_app.models.EmployeeDB;
import org.employee.employee_app.models.EmployeeID;


import java.util.Comparator;
import java.util.List;
import java.util.Optional;

public class DashboardController {

    @FXML
    public Button addEmployee;

    @FXML
    private ChoiceBox<String> filter;


    @FXML
    private TableColumn<Employee<EmployeeID>,String> nameColumn;

    @FXML
    private TableColumn<Employee<EmployeeID>,String> empIdColumn;

    @FXML
    private TableColumn<Employee<EmployeeID>,String> departmentColumn;

    @FXML
    private TableColumn<Employee<EmployeeID>,Double> salaryColumn;

    @FXML
    private TableColumn<Employee<EmployeeID>,Double> ratingColumn;

    @FXML
    private TableColumn<Employee<EmployeeID>,Integer> years_xpColumn;

    @FXML
    private TableColumn<Employee<EmployeeID>,Boolean> activeColumn;

    @FXML
    private TableView<Employee<EmployeeID>> tableView;


    @FXML
    private TableColumn<Employee, Void> actionColumn;


    @FXML
    private  TextField ratingAmount;

    @FXML
    private TextField percentageValue;


    @FXML
    private  TextField minSalary;

    @FXML
    private TextField maxSalary;

    @FXML
    private TextField nameDepartmentSearch;

    @FXML
    private Label deptAverage;

    @FXML
    private TextField departmentName;

    @FXML
    private TextField ratingGet;

    private final EmployeeDB<EmployeeID,Employee<EmployeeID>> employeeDB = new EmployeeDB<>();



    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    @FXML
    protected void loadEmployees() {
        try {
            List<Employee<EmployeeID>> employees = employeeDB.getAllEmployees();
            ObservableList<Employee<EmployeeID>> observableEmployees = FXCollections.observableArrayList(employees);
            SortedList<Employee<EmployeeID>> sortedEmployees = new SortedList<>(observableEmployees);
            sortedEmployees.setComparator(Comparator.naturalOrder());
            tableView.setItems(sortedEmployees);
        } catch (Exception e) {
        showAlert("Failed to load employees", e.getMessage());        }
    }


    @FXML
    private void onGetEmployeesByRating(){
        try{
            String ratingFiled = ratingGet.getText().trim();
            UserInputValidation.validateInputField(ratingFiled, "Rating");
            double rating = Double.parseDouble(ratingFiled);
            List<Employee<EmployeeID>> employees = employeeDB.getEmployeesByRating(rating);
            ObservableList<Employee<EmployeeID>> observableEmployees = FXCollections.observableArrayList(employees);
            tableView.setItems(observableEmployees);
        }catch (Exception e){
            showAlert("An error occured", e.getMessage());
        }

    }


    @FXML
    private void onCalculateDepAverageSalary(){
        try {
            String department = departmentName.getText();
            UserInputValidation.validateInputField(department, "Department");
            double depAverage = employeeDB.getAverageSalaryByDepartment(department);
            deptAverage.setText(String.valueOf(depAverage));
        }catch (Exception e){
            showAlert("An error occured", e.getMessage());
        }

    }


    @FXML
    private void onGetBySalaryRange(){
        try {
            String max = maxSalary.getText().trim();
            String min = minSalary.getText().trim();
            UserInputValidation.validateInputField(min, "Minimum salary");
            UserInputValidation.validateInputField(max, "Maximum salary");
            double minimumSalary = Double.parseDouble(min);
            double maximumSalary = Double.parseDouble(max);
            UserInputValidation.validateGetEmployeeSalaryInRangeInput(minimumSalary, maximumSalary);
            List<Employee<EmployeeID>> employee = employeeDB.getEmployeesInSalaryRange(minimumSalary,maximumSalary);
            ObservableList<Employee<EmployeeID>> observableEmployee = FXCollections.observableArrayList(employee);
            tableView.setItems(observableEmployee);
        }catch (Exception e){
            showAlert("An error occured", e.getMessage());
        }
    }


    @FXML
    private void onSalaryRaise(){
        try{
            String ratingInput = ratingAmount.getText();
            String percentInput = percentageValue.getText();
            UserInputValidation.validateInputField(ratingInput, "Performance rating");
            UserInputValidation.validateInputField(percentInput, "Percentage");
            double rating = Double.parseDouble(ratingInput);
            double percentage = Double.parseDouble(percentInput);
            UserInputValidation.validateRaiseByPercentInput(rating, percentage);
            employeeDB.salaryRaiseByRatePercent(rating, percentage);
            loadEmployees();
            tableView.refresh();
        }catch (Exception e){
            showAlert("An error occured", e.getMessage());
        }

    }


    @FXML
    private void onSearchByName(){
        try {
            String name = nameDepartmentSearch.getText().trim();
            UserInputValidation.validateInputField(name, "Name");
            List<Employee<EmployeeID>> employee = employeeDB.getEmployeesByName(name);
            ObservableList<Employee<EmployeeID>> observableEmployee = FXCollections.observableArrayList(employee);
            tableView.setItems(observableEmployee);
        }catch (Exception e) {
            showAlert("An error occured", e.getMessage());
        }
    }

    @FXML
    private void onSearchByDepartment(){
        try{
            String department = nameDepartmentSearch.getText().trim();
            UserInputValidation.validateInputField(department, "Department");
            List<Employee<EmployeeID>> employee = employeeDB.getEmployeesByDepartment(department);
            ObservableList<Employee<EmployeeID>> observableEmployee = FXCollections.observableArrayList(employee);
            tableView.setItems(observableEmployee);
        }catch (Exception e){
            showAlert("An error occured", e.getMessage());
        }

    }



    @FXML
    private void showAddEmployeeDialog() {
        Dialog<Employee<EmployeeID>> dialog = new Dialog<>();
        dialog.setTitle("Add New Employee");
        dialog.setHeaderText("Enter new employee details:");

        ButtonType addButtonType = new ButtonType("Add", ButtonBar.ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().addAll(addButtonType, ButtonType.CANCEL);

        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(20, 150, 10, 10));

        TextField nameField = new TextField();
        nameField.setPromptText("Name");

        TextField departmentField = new TextField();
        departmentField.setPromptText("Department");

        TextField salaryField = new TextField();
        salaryField.setPromptText("Salary");

        TextField ratingField = new TextField();
        ratingField.setPromptText("Performance Rating");

        TextField experienceField = new TextField();
        experienceField.setPromptText("Years of Experience");

        CheckBox activeCheckBox = new CheckBox("Is Active");

        grid.add(new Label("Name:"), 0, 1);
        grid.add(nameField, 1, 1);
        grid.add(new Label("Department:"), 0, 2);
        grid.add(departmentField, 1, 2);
        grid.add(new Label("Salary:"), 0, 3);
        grid.add(salaryField, 1, 3);
        grid.add(new Label("Performance Rating:"), 0, 4);
        grid.add(ratingField, 1, 4);
        grid.add(new Label("Experience:"), 0, 5);
        grid.add(experienceField, 1, 5);
        grid.add(activeCheckBox, 1, 6);

        dialog.getDialogPane().setContent(grid);

        dialog.setResultConverter(dialogButton -> {
            if (dialogButton == addButtonType) {
                try {
                    String name = nameField.getText();
                    String department = departmentField.getText();
                    String Salary = salaryField.getText();
                    String PerformanceRating = ratingField.getText();
                    String YearsOfExperience = experienceField.getText();
                    boolean isActive = activeCheckBox.isSelected();


                    UserInputValidation.validateInputField(Salary, "Salary");
                    UserInputValidation.validateInputField(PerformanceRating, "Performance");
                    UserInputValidation.validateInputField(YearsOfExperience, "Years of experience");

                    double salary = Double.parseDouble(Salary);
                    double performanceRating = Double.parseDouble(PerformanceRating);
                    int yearsOfExperience = Integer.parseInt(YearsOfExperience);

                    UserInputValidation.validateCreateUserInput(new EmployeeID(),name,department,salary,performanceRating,yearsOfExperience);

                    return new Employee<>(new EmployeeID(), name, department, salary, performanceRating, yearsOfExperience, isActive);
                } catch (Exception e) {
                    showAlert("An error occurred", e.getMessage());
                }
            }
            return null;
        });

        Optional<Employee<EmployeeID>> result = dialog.showAndWait();

        result.ifPresent(employee -> {
            employeeDB.addEmployee(employee);
            loadEmployees();
        });
    }



    private void addActionButtonsToTable() {
        Callback<TableColumn<Employee, Void>, TableCell<Employee, Void>> cellFactory = new Callback<>() {
            @Override
            public TableCell<Employee, Void> call(final TableColumn<Employee, Void> param) {
                return new TableCell<>() {

                    private final Button updateButton = new Button("Update");
                    private final Button deleteButton = new Button("Delete");

                    {
                        updateButton.setOnAction(event -> {
                            Employee<EmployeeID> employee = getTableView().getItems().get(getIndex());
                            openUpdateDialog(employee);
                        });

                        deleteButton.setOnAction(event -> {
                            Employee<EmployeeID> employee = getTableView().getItems().get(getIndex());
                            showDeleteConfirmation(employee); // Show Delete Confirmation
                        });

                        updateButton.setStyle("-fx-background-color: #0066cc; -fx-text-fill: white; -fx-font-weight: bold;");
                        deleteButton.setStyle("-fx-background-color: red; -fx-text-fill: white; -fx-font-weight: bold;");
                    }

                    @Override
                    public void updateItem(Void item, boolean empty) {
                        super.updateItem(item, empty);
                        if (empty) {
                            setGraphic(null);
                        } else {
                            HBox hbox = new HBox(10, updateButton, deleteButton);
                            setGraphic(hbox);
                        }
                    }
                };
            }
        };

        actionColumn.setCellFactory(cellFactory);
    }

    private void openUpdateDialog(Employee<EmployeeID> employee) {
            Dialog<Employee<EmployeeID>> dialog = new Dialog<>();
            dialog.setTitle("Update Employee");

            String originalName = employee.getName();
            String originalDepartment = employee.getDepartment();
            double originalSalary = employee.getSalary();
            double originalPerformanceRating = employee.getPerformanceRating();
            int originalYearsOfExperience = employee.getYearsOfExperience();
            boolean originalIsActive = employee.isActive();

            TextField nameField = new TextField(originalName);
            TextField departmentField = new TextField(originalDepartment);
            TextField salaryField = new TextField(String.valueOf(originalSalary));
            TextField performanceField = new TextField(String.valueOf(originalPerformanceRating));
            TextField experienceField = new TextField(String.valueOf(originalYearsOfExperience));
            CheckBox activeCheckBox = new CheckBox("Active");
            activeCheckBox.setSelected(originalIsActive);

            VBox content = new VBox(10,
                    new Label("Name:"), nameField,
                    new Label("Department:"), departmentField,
                    new Label("Salary:"), salaryField,
                    new Label("Performance Rating:"), performanceField,
                    new Label("Years of Experience:"), experienceField,
                    activeCheckBox
            );
            content.setPadding(new Insets(10));
            dialog.getDialogPane().setContent(content);

            ButtonType updateButtonType = new ButtonType("Update", ButtonBar.ButtonData.OK_DONE);
            dialog.getDialogPane().getButtonTypes().addAll(updateButtonType, ButtonType.CANCEL);

            dialog.setResultConverter(dialogButton -> {
                if (dialogButton == updateButtonType) {
                    try {


                        String newName = nameField.getText();
                        String newDepartment = departmentField.getText();
                        String Salary = salaryField.getText();
                        String PerformanceRating = performanceField.getText();
                        String YearsOfExperience = experienceField.getText();
                        boolean newIsActive = activeCheckBox.isSelected();

                        UserInputValidation.validateInputField(newName, "Name");
                        UserInputValidation.validateInputField(newDepartment, "Department");
                        UserInputValidation.validateInputField(Salary, "Salary");
                        UserInputValidation.validateInputField(PerformanceRating, "Performance");
                        UserInputValidation.validateInputField(YearsOfExperience, "Years of experience");

                        double newSalary = Double.parseDouble(Salary);
                        double newPerformanceRating = Double.parseDouble(PerformanceRating);
                        int newYearsOfExperience = Integer.parseInt(YearsOfExperience);


                        if (!newName.equals(originalName)) {
                                employee.setName(newName);
                                employeeDB.updateEmployeeDetails(employee.getEmployeeId(), "name", newName);
                        }
                        if (!newDepartment.equals(originalDepartment)) {
                                employee.setDepartment(newDepartment);
                                employeeDB.updateEmployeeDetails(employee.getEmployeeId(), "department", newDepartment);
                        }
                        if (newSalary != originalSalary) {
                                UserInputValidation.validateForZeroValues(newSalary, "Salary");
                                employee.setSalary(newSalary);
                                employeeDB.updateEmployeeDetails(employee.getEmployeeId(), "salary", newSalary);
                        }
                        if (newPerformanceRating != originalPerformanceRating) {
                                UserInputValidation.validateForZeroValues(newPerformanceRating, "Performance rating");
                                employee.setPerformanceRating(newPerformanceRating);
                                employeeDB.updateEmployeeDetails(employee.getEmployeeId(), "performance", newPerformanceRating);
                        }
                        if (newYearsOfExperience != originalYearsOfExperience) {
                                UserInputValidation.validateForZeroValues(newYearsOfExperience, "Years of experience");
                                employee.setYearsOfExperience(newYearsOfExperience);
                                employeeDB.updateEmployeeDetails(employee.getEmployeeId(), "experience", newYearsOfExperience);
                        }
                        if (newIsActive != originalIsActive) {
                                employee.setActive(newIsActive);
                                employeeDB.updateEmployeeDetails(employee.getEmployeeId(), "active", newIsActive);
                        }
                        return employee;
                    }catch (Exception e){
                        showAlert("Invalid update data", e.getMessage());

                    }
                }
                return null;
            });

            dialog.showAndWait();
            loadEmployees();

    }


    private void showDeleteConfirmation(Employee<EmployeeID> employee) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirm Delete");
        alert.setHeaderText("Are you sure you want to delete this employee?");
        alert.setContentText("Employee: " + employee.getName());

        Optional<ButtonType> result = alert.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK) {
            employeeDB.removeEmployee((EmployeeID) employee.getEmployeeId());
            loadEmployees();
        }
    }



    @FXML
    private void initialize(){

        filter.setItems(FXCollections.observableArrayList(
                "Top 5 employees", "Performance rating", "Salary (Highest first)"
        ));

        empIdColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getEmployeeId().id));
        nameColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getName()));
        departmentColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getDepartment()));
        salaryColumn.setCellValueFactory(cellData -> new SimpleDoubleProperty(cellData.getValue().getSalary()).asObject());
        ratingColumn.setCellValueFactory(cellData -> new SimpleDoubleProperty(cellData.getValue().getPerformanceRating()).asObject());
        years_xpColumn.setCellValueFactory(cellData -> new SimpleIntegerProperty(cellData.getValue().getYearsOfExperience()).asObject());
        activeColumn.setCellValueFactory(cellData -> new SimpleBooleanProperty(cellData.getValue().isActive()).asObject());

        addActionButtonsToTable();
        loadEmployees();

    }



    @FXML
    private void onSortingCriteria() {
        filter.setOnAction(e -> {

            String selected = filter.getValue();

            if (selected.equals("Top 5 employees")) {
                List<Employee<EmployeeID>> topFive = employeeDB.getTopFiveHighestPaidEmployees();
                ObservableList<Employee<EmployeeID>> observableTopFive = FXCollections.observableArrayList(topFive);
                tableView.setItems(observableTopFive);
            } else if (selected.equals("Performance rating")) {
                List<Employee<EmployeeID>> employees = employeeDB.sortEmployeesByRating();
                ObservableList<Employee<EmployeeID>> observableEmployees = FXCollections.observableArrayList(employees);
                tableView.setItems(observableEmployees);
                tableView.refresh();
            } else if (selected.equals("Salary (Highest first)")) {
                List<Employee<EmployeeID>> employees = employeeDB.sortEmployeesSalaryHighestFirst();
                ObservableList<Employee<EmployeeID>> observableEmployees = FXCollections.observableArrayList(employees);
                tableView.setItems(observableEmployees);
            } else {
                loadEmployees();
            }
        });
    }


}