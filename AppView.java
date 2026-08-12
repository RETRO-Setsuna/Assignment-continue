import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class AppView {

    private VBox view;

    private Button customerBtn;
    private Button staffBtn;
    private Button exitBtn;

    private AppController controller;
    private AppModel model;
    private Stage primaryStage;

    public AppView(AppController controller, AppModel model, Stage primaryStage) {
        this.controller = controller;
        this.model = model;
        this.primaryStage = primaryStage;

        createAndConfigurePane();
        createAndLayoutControls();
    }

    public Parent asParent() {
        return view;
    }

    private void createAndConfigurePane() {
        view = new VBox(15);
        view.setAlignment(Pos.CENTER);
    }

    private void createAndLayoutControls() {

        Label title = new Label("Welcome to HD Choco Shop!!!!");

        customerBtn = new Button("Customer");
        staffBtn = new Button("Staff");
        exitBtn = new Button("Exit");

        customerBtn.setOnAction(event -> showCustomerMenu());
        exitBtn.setOnAction(event -> primaryStage.close());

        view.getChildren().addAll(title, customerBtn, staffBtn, exitBtn);
    }

    private void showCustomerMenu() {

        Label title = new Label("Customer Sign In");

        TextField usernameField = new TextField();
        usernameField.setPromptText("Enter username");

        TextField passwordField = new TextField();
        passwordField.setPromptText("Enter password");

        HBox usernameRow = new HBox(10, new Label("Username:"), usernameField);
        HBox passwordRow = new HBox(10, new Label("Password:"), passwordField);

        usernameRow.setAlignment(Pos.CENTER);
        passwordRow.setAlignment(Pos.CENTER);

        Button loginBtn = new Button("Log In");
        Button signUpBtn = new Button("Sign Up");
        Button returnBtn = new Button("Return");

        HBox buttonRow = new HBox(10, loginBtn, signUpBtn);
        buttonRow.setAlignment(Pos.CENTER);

        Label messageLabel = new Label("");

        loginBtn.setOnAction(event -> {
            String username = usernameField.getText();
            String password = passwordField.getText();

            if (controller.signIn(username, password)) {
                showCustomerMainMenu();
            } else {
                messageLabel.setText("Wrong username or password");
            }
        });

        signUpBtn.setOnAction(event -> createSignUpForm());

        returnBtn.setOnAction(event -> showMainMenu());

        view.getChildren().clear();
        view.getChildren().addAll(title, usernameRow, passwordRow, buttonRow, messageLabel, returnBtn);
    }

    private void createSignUpForm() {

        Stage stage = new Stage();
        stage.initOwner(primaryStage);
        stage.initModality(Modality.APPLICATION_MODAL);

        TextField usernameField = new TextField();
        usernameField.setPromptText("Enter username");

        TextField passwordField = new TextField();
        passwordField.setPromptText("Enter password");

        TextField confirmField = new TextField();
        confirmField.setPromptText("Confirm password");

        HBox usernameRow = new HBox(5, new Label("Username:"), usernameField);
        HBox passwordRow = new HBox(5, new Label("Password:"), passwordField);
        HBox confirmRow = new HBox(5, new Label("Confirm Password:"), confirmField);

        usernameRow.setAlignment(Pos.CENTER);
        passwordRow.setAlignment(Pos.CENTER);
        confirmRow.setAlignment(Pos.CENTER);

        Label messageLabel = new Label("");

        Button createBtn = new Button("Create Account");
        Button cancelBtn = new Button("Cancel");

        createBtn.setOnAction(event -> {
            String username = usernameField.getText().trim();
            String password = passwordField.getText();
            String confirmPassword = confirmField.getText();

            if (username.isEmpty() || password.isEmpty() || confirmPassword.isEmpty()) {
                messageLabel.setText("Please complete all fields");
            } else if (password.length() < 8) {
                messageLabel.setText("Password must be at least 8 characters");
            } else if (!password.equals(confirmPassword)) {
                messageLabel.setText("Passwords do not match");
            } else if (controller.signUp(username, password)) {
                messageLabel.setText("Account created successfully");
            } else {
                messageLabel.setText("Sign up failed");
            }
        });

        cancelBtn.setOnAction(event -> stage.close());

        HBox buttonRow = new HBox(5, createBtn, cancelBtn);
        buttonRow.setAlignment(Pos.CENTER);

        VBox root = new VBox(5, usernameRow, passwordRow, confirmRow, messageLabel, buttonRow);
        root.setAlignment(Pos.CENTER);

        Scene scene = new Scene(root, 350, 180);

        stage.setScene(scene);
        stage.show();
    }

    private void showMainMenu() {

        Label title = new Label("Welcome to HD Choco Shop!!!!");

        customerBtn = new Button("Customer");
        staffBtn = new Button("Staff");
        exitBtn = new Button("Exit");

        customerBtn.setOnAction(event -> showCustomerMenu());
        exitBtn.setOnAction(event -> primaryStage.close());

        view.getChildren().clear();
        view.getChildren().addAll(title, customerBtn, staffBtn, exitBtn);
    }

    private void showCustomerMainMenu() {

        Label title = new Label("Customer Menu");

        Button searchBtn = new Button("Search");

        Button filterBtn = new Button("Filter");
        Button buildChocolateBtn = new Button("Build Chocolate");
        Button cartBtn = new Button("My Cart");
        Button logoutBtn = new Button("Log Out");

        HBox firstRow = new HBox(10, searchBtn);
        HBox secondRow = new HBox(10, filterBtn, buildChocolateBtn);
        HBox thirdRow = new HBox(10, cartBtn, logoutBtn);

        firstRow.setAlignment(Pos.CENTER);
        secondRow.setAlignment(Pos.CENTER);
        thirdRow.setAlignment(Pos.CENTER);

        logoutBtn.setOnAction(event -> showCustomerMenu());

        view.getChildren().clear();
        view.getChildren().addAll(title, firstRow, secondRow, thirdRow);
    }
}