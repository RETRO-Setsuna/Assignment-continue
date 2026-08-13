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
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class AppView {

    private VBox view;

    private Button customerBtn;
    private Button staffBtn;
    private Button exitBtn;

    private AppController controller;
    private AppModel model;
    private Stage primaryStage;
    private TableView<Chocolate> chocolateView;

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
        Button showAllBtn = new Button("Show All");

        HBox menuRow = new HBox(10, searchBtn, filterBtn, buildChocolateBtn, cartBtn, showAllBtn);
        menuRow.setAlignment(Pos.CENTER);

        chocolateView = new TableView<>();
        chocolateView.setItems(model.chocolatesProperty());

        TableColumn<Chocolate, String> idCol = new TableColumn<>("ID");
        TableColumn<Chocolate, String> nameCol = new TableColumn<>("Name");
        TableColumn<Chocolate, Types> typeCol = new TableColumn<>("Type");
        TableColumn<Chocolate, Size> sizeCol = new TableColumn<>("Size");

        idCol.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getProductId()));
        nameCol.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getName()));
        typeCol.setCellValueFactory(cellData -> new SimpleObjectProperty<Types>(cellData.getValue().getType()));
        sizeCol.setCellValueFactory(cellData -> new SimpleObjectProperty<Size>(cellData.getValue().getSize()));

        chocolateView.getColumns().addAll(idCol, nameCol, typeCol, sizeCol);

        logoutBtn.setOnAction(event -> showCustomerMenu());
        searchBtn.setOnAction(event -> createSearchForm());
        showAllBtn.setOnAction(event -> chocolateView.setItems(model.chocolatesProperty()));

        view.getChildren().clear();
        view.getChildren().addAll(title, menuRow, chocolateView, logoutBtn);
    }

    private void createSearchForm() {

        Stage stage = new Stage();
        stage.initOwner(primaryStage);
        stage.initModality(Modality.APPLICATION_MODAL);

        TextField searchField = new TextField();
        searchField.setPromptText("Enter chocolate name");

        HBox searchRow = new HBox(5, new Label("Chocolate Name:"), searchField);
        searchRow.setAlignment(Pos.CENTER);

        Label addingLabel = new Label("Please enter the exact Chocolate name. ex) Dark Chocolate.");
        Label messageLabel = new Label("");

        Button searchBtn = new Button("Search");
        Button cancelBtn = new Button("Cancel");

        searchBtn.setOnAction(event -> {
            String name = searchField.getText().trim();

            if (name.isEmpty()) {
                messageLabel.setText("Please enter a Chocolate name");
            } else {
                Chocolate chocolate = controller.searchChocolate(name);

                if (chocolate != null) {

                    ObservableList<Chocolate> searchResult = FXCollections.observableArrayList();
                    searchResult.add(chocolate);

                    chocolateView.setItems(searchResult);

                    stage.close();

                } else {
                    messageLabel.setText("Chocolate not found");
                }
            }
        });

        cancelBtn.setOnAction(event -> stage.close());

        HBox buttonRow = new HBox(5, searchBtn, cancelBtn);
        buttonRow.setAlignment(Pos.CENTER);

        VBox root = new VBox(5, addingLabel, searchRow, messageLabel, buttonRow);
        root.setAlignment(Pos.CENTER);

        Scene scene = new Scene(root, 350, 150);

        stage.setScene(scene);
        stage.show();
    }
}
