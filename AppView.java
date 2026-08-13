import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
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
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;

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
        filterBtn.setOnAction(event -> createFilterForm());

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

    private void createFilterForm() {

        Stage stage = new Stage();
        stage.initOwner(primaryStage);
        stage.initModality(Modality.APPLICATION_MODAL);

        Label title = new Label("Filter Chocolate");

        Button typeBtn = new Button("Filter by Type");
        Button sizeBtn = new Button("Filter by Size");
        Button sweetnessBtn = new Button("Filter by Sweetness");
        Button cancelBtn = new Button("Cancel");

        cancelBtn.setOnAction(event -> stage.close());
        typeBtn.setOnAction(event -> createTypeFilterForm(stage));
        sizeBtn.setOnAction(event -> createSizeFilterForm(stage));
        sweetnessBtn.setOnAction(event -> createSweetnessFilterForm(stage));

        VBox root = new VBox(10, title, typeBtn, sizeBtn, sweetnessBtn, cancelBtn);
        root.setAlignment(Pos.CENTER);

        Scene scene = new Scene(root, 300, 250);

        stage.setScene(scene);
        stage.show();
    }

    private void createTypeFilterForm(Stage filterStage) {

        Stage stage = new Stage();
        stage.initOwner(primaryStage);
        stage.initModality(Modality.APPLICATION_MODAL);

        Label title = new Label("Filter by Type");

        ToggleGroup typeGroup = new ToggleGroup();

        RadioButton whiteBtn = new RadioButton("White Chocolate");
        whiteBtn.setToggleGroup(typeGroup);

        RadioButton darkBtn = new RadioButton("Dark Chocolate");
        darkBtn.setToggleGroup(typeGroup);

        RadioButton milkBtn = new RadioButton("Milk Chocolate");
        milkBtn.setToggleGroup(typeGroup);

        RadioButton cookieBtn = new RadioButton("Cookie and Cream");
        cookieBtn.setToggleGroup(typeGroup);

        VBox typeRow = new VBox(5, whiteBtn, darkBtn, milkBtn, cookieBtn);
        typeRow.setAlignment(Pos.CENTER);

        Label messageLabel = new Label("");

        Button applyBtn = new Button("Apply");
        Button cancelBtn = new Button("Cancel");

        cancelBtn.setOnAction(event -> stage.close());
        applyBtn.setOnAction(event -> {

            Types type = null;

            if (whiteBtn.isSelected()) {
                type = Types.WHITE_CHOCOLATE;
            } else if (darkBtn.isSelected()) {
                type = Types.DARK_CHOCOLATE;
            } else if (milkBtn.isSelected()) {
                type = Types.MILK_CHOCOLATE;
            } else if (cookieBtn.isSelected()) {
                type = Types.COOKIE_AND_CREAM;
            }

            if (type == null) {
                messageLabel.setText("Please select a chocolate type");
            } else {
                ObservableList<Chocolate> filteredList = FXCollections.observableArrayList();
                filteredList.addAll(controller.filterByType(type));

                chocolateView.setItems(filteredList);

                stage.close();
                filterStage.close();
            }
        });
        HBox buttonRow = new HBox(5, applyBtn, cancelBtn);
        buttonRow.setAlignment(Pos.CENTER);

        VBox root = new VBox(10, title, typeRow, messageLabel, buttonRow);
        root.setAlignment(Pos.CENTER);

        Scene scene = new Scene(root, 300, 250);

        stage.setScene(scene);
        stage.show();
    }

    private void createSizeFilterForm(Stage filterStage) {

        Stage stage = new Stage();
        stage.initOwner(primaryStage);
        stage.initModality(Modality.APPLICATION_MODAL);

        Label title = new Label("Filter by Size");

        ToggleGroup sizeGroup = new ToggleGroup();

        RadioButton smallBtn = new RadioButton("S");
        smallBtn.setToggleGroup(sizeGroup);

        RadioButton mediumBtn = new RadioButton("M");
        mediumBtn.setToggleGroup(sizeGroup);

        RadioButton largeBtn = new RadioButton("L");
        largeBtn.setToggleGroup(sizeGroup);

        RadioButton extraLargeBtn = new RadioButton("XL");
        extraLargeBtn.setToggleGroup(sizeGroup);

        VBox sizeRow = new VBox(5, smallBtn, mediumBtn, largeBtn, extraLargeBtn);
        sizeRow.setAlignment(Pos.CENTER);

        Label messageLabel = new Label("");

        Button applyBtn = new Button("Apply");
        Button cancelBtn = new Button("Cancel");

        applyBtn.setOnAction(event -> {

            Size size = null;

            if (smallBtn.isSelected()) {
                size = Size.S;
            } else if (mediumBtn.isSelected()) {
                size = Size.M;
            } else if (largeBtn.isSelected()) {
                size = Size.L;
            } else if (extraLargeBtn.isSelected()) {
                size = Size.XL;
            }

            if (size == null) {
                messageLabel.setText("Please select a size");
            } else {
                ObservableList<Chocolate> filteredList = FXCollections.observableArrayList();
                filteredList.addAll(controller.filterBySize(size));

                chocolateView.setItems(filteredList);

                stage.close();
                filterStage.close();
            }
        });

        cancelBtn.setOnAction(event -> stage.close());

        HBox buttonRow = new HBox(5, applyBtn, cancelBtn);
        buttonRow.setAlignment(Pos.CENTER);

        VBox root = new VBox(10, title, sizeRow, messageLabel, buttonRow);
        root.setAlignment(Pos.CENTER);

        Scene scene = new Scene(root, 300, 250);

        stage.setScene(scene);
        stage.show();
    }

    private void createSweetnessFilterForm(Stage filterStage) {

        Stage stage = new Stage();
        stage.initOwner(primaryStage);
        stage.initModality(Modality.APPLICATION_MODAL);

        Label title = new Label("Filter by Sweetness");

        ToggleGroup sweetnessGroup = new ToggleGroup();

        RadioButton zeroBtn = new RadioButton("0%");
        zeroBtn.setToggleGroup(sweetnessGroup);

        RadioButton twentyFiveBtn = new RadioButton("25%");
        twentyFiveBtn.setToggleGroup(sweetnessGroup);

        RadioButton fiftyBtn = new RadioButton("50%");
        fiftyBtn.setToggleGroup(sweetnessGroup);

        RadioButton seventyFiveBtn = new RadioButton("75%");
        seventyFiveBtn.setToggleGroup(sweetnessGroup);

        RadioButton hundredBtn = new RadioButton("100%");
        hundredBtn.setToggleGroup(sweetnessGroup);

        VBox sweetnessRow = new VBox(5, zeroBtn, twentyFiveBtn, fiftyBtn, seventyFiveBtn, hundredBtn);
        sweetnessRow.setAlignment(Pos.CENTER);

        Label messageLabel = new Label("");

        Button applyBtn = new Button("Apply");
        Button cancelBtn = new Button("Cancel");

        applyBtn.setOnAction(event -> {

            Sweetness sweetness = null;

            if (zeroBtn.isSelected()) {
                sweetness = Sweetness.ZERO;
            } else if (twentyFiveBtn.isSelected()) {
                sweetness = Sweetness.TWENTY_FIVE;
            } else if (fiftyBtn.isSelected()) {
                sweetness = Sweetness.FIFTY;
            } else if (seventyFiveBtn.isSelected()) {
                sweetness = Sweetness.SEVENTY_FIVE;
            } else if (hundredBtn.isSelected()) {
                sweetness = Sweetness.HUNDRED;
            }

            if (sweetness == null) {
                messageLabel.setText("Please select sweetness");
            } else {
                ObservableList<Chocolate> filteredList = FXCollections.observableArrayList();
                filteredList.addAll(controller.filterBySweetness(sweetness));

                chocolateView.setItems(filteredList);

                stage.close();
                filterStage.close();
            }
        });

        cancelBtn.setOnAction(event -> stage.close());

        HBox buttonRow = new HBox(5, applyBtn, cancelBtn);
        buttonRow.setAlignment(Pos.CENTER);

        VBox root = new VBox(10, title, sweetnessRow, messageLabel, buttonRow);
        root.setAlignment(Pos.CENTER);

        Scene scene = new Scene(root, 300, 280);

        stage.setScene(scene);
        stage.show();
    }
}
