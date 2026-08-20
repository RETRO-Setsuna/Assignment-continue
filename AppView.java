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

public class AppView {

    private VBox view;

    private Button customerBtn;
    private Button staffBtn;
    private Button exitBtn;

    private AppController controller;
    private AppModel model;
    private Stage primaryStage;
    private TableView<Chocolate> chocolateView;

    public TableView<Chocolate> chocoView;
    private Button addChocolate;
    private Button removeChocolate;
    private Button updateChocolate;
    private Button removeAllChocolate;
    private Button changeOrderStatus;
    private Button logOutBtn;

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
        staffBtn.setOnAction(event -> passcodePanel());
        exitBtn.setOnAction(event -> primaryStage.close());

        view.getChildren().clear();
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

    // Panel for the staff passcode
    private void PasscodePanel() {

        Stage stage = new Stage();
        stage.initOwner(primaryStage);
        stage.initModality(Modality.APPLICATION_MODAL);

        Label msgLabel = new Label("");

        TextField passcodField = new TextField();
        passcodField.setPromptText("Passcode");

        Button confirmBtn = new Button("Confirm");
        Button returnBtn = new Button("Return");

        HBox passcodeRow = new HBox(5, new Label("Passcode:"), passcodField);
        passcodeRow.setAlignment(Pos.CENTER);
        HBox buttonRow = new HBox(5, confirmBtn, returnBtn);
        buttonRow.setAlignment(Pos.CENTER);

        confirmBtn.setOnAction(event -> {
            String passcode = passcodField.getText().trim();

            // if the password is correct it would redirect you to the proper menu, else you
            // wouldnt be able to enter
            if (passcode.isEmpty()) {
                msgLabel.setText("please enter PassCode");
            } else if (passcode.equals("1234")) {
                staffPanel();
                stage.close();
            } else {
                msgLabel.setText("Wrong Passcode");
            }

        });
        returnBtn.setOnAction(event -> {
            stage.close();
            showMainMenu();
        });

        VBox root = new VBox(5, passcodeRow, buttonRow);
        root.setAlignment(Pos.CENTER);

        Scene scene = new Scene(root, 350, 180);

        stage.setScene(scene);
        stage.show();

    }

    // the actual staff panel
    private void staffPanel() {
        this.chocoView = new TableView<>();
        // clolumn for the chocolate name
        TableColumn<Chocolate, String> chocoName = new TableColumn<>("Chocolate Name");
        chocoName.setMinWidth(200.0);
        chocoName.setCellValueFactory(cellData -> cellData.getValue().nameProperty());
        // coloumn for the IDs
        TableColumn<Chocolate, String> chocoID = new TableColumn<>("Product ID");
        chocoID.setCellValueFactory(cellData -> cellData.getValue().productIDProperty());
        // COolumn for the price
        TableColumn<Chocolate, Double> chocoPrice = new TableColumn<>("Price");
        chocoPrice.setCellValueFactory(cellData -> cellData.getValue().priceProperty().asObject());
        // column for the size of chocolate
        TableColumn<Chocolate, Size> chocSize = new TableColumn<>("Chocolate Size");
        chocSize.setCellValueFactory(cellData -> cellData.getValue().sizeProperty());
        // coloumn for chocolate type
        TableColumn<Chocolate, Types> chocoType = new TableColumn<>("Chocolate Type");
        chocoType.setCellValueFactory(cellData -> cellData.getValue().typeProperty());
        // coloumn for the topping
        TableColumn<Chocolate, Toppings> chocotoppings = new TableColumn<>("Toppings");
        chocotoppings.setCellValueFactory(cellData -> cellData.getValue().toppingProperty());
        // coloumn for he sweetness of the chocolate
        TableColumn<Chocolate, Sweetness> chocosweetnesses = new TableColumn<>("Sweetness");
        chocosweetnesses.setCellValueFactory(cellData -> cellData.getValue().sweetProperty());
        // coloumn for the filling inside the chocolate

        TableColumn<Chocolate, Fillings> chocFill = new TableColumn<>("Fillings");
        chocFill.setCellValueFactory(cellData -> cellData.getValue().fillProperty());

        this.chocoView.getColumns().addAll(chocoName, chocoID, chocoPrice, chocSize, chocoType, chocoSweetnesses,
                chocFill, chocotoppings);
        this.chocoView.setItems(model.chocoProperties());

        this.addChcoclate = new Button("Add Chocolate");
        // when clicked it would be redirected to AddChocoPanel
        this.addChcoclate.setOnAction(event -> AddChocPanel());
        this.removeChocolate = new Button("Remove Chocolate");
        // the selected choco would be deleted
        this.removeChocolate.setOnAction(event -> {
            int index = this.chocoView.getSelectionModel().getSelectedIndex();
            if (index != -1) {
                this.controller.removeChoc(index);
            }
        });
        this.updateChocolate = new Button("Edit Chocolate");
        // when clicke the editingChocPanel
        this.updateChocolate.setOnAction(event -> {
            int i = this.chocoView.getSelectionModel().getSelectedIndex();
            if (i != -1) {
                editingChocPanel(i);
            }
        });
        this.changeOrderStatus = new Button("Update Customer Order Status");
        // when clicked it would open a staffOrderStatus
        this.changeOrderStatus.setOnAction(event -> staffOrderStatus());
        this.logOutBtn = new Button("Log Out");
        this.logOutBtn.setOnAction(event -> {
            view.getChildren().clear();
            createAndLayoutControls();
        });

        HBox buttonRow = new HBox(5, addChocolate, removeChocolate, updateChocolate, changeOrderStatus, logOutBtn);

        view.getChildren().clear();
        view.getChildren().addAll(this.chocoView, buttonRow);

    }

    private void addChocPanel() {
        Stage stage = new Stage();
        stage.initOwner(primaryStage);
        stage.initModality(Modality.APPLICATION_MODAL);

        TextField nameField = new TextField();
        TextField idField = new TextField();
        TextField priceField = new TextField();

        // Textsfields for the name ID and price
        nameField.setPromptText("Enter Chocolate Name");
        idField.setPromptText(" Enter Chocolate ID");
        priceField.setPromptText("Enter price");

        HBox nameRow = new HBox(5, new Label("Name:"), nameField);
        HBox idRow = new HBox(5, new Label("ID: "), idField);
        HBox priceRow = new HBox(5, new Label("Price:"), priceField);

        nameRow.setAlignment(Pos.CENTER);
        idRow.setAlignment(Pos.CENTER);
        priceRow.setAlignment(Pos.CENTER);

        // radio buttons for all avaliable options for size
        ToggleGroup sizeGroup = new ToggleGroup();
        RadioButton sBtn = new RadioButton("Small");
        RadioButton mBtn = new RadioButton("Medium");
        RadioButton lBtn = new RadioButton("Large");
        RadioButton xlBtn = new RadioButton("Extra Large");
        sBtn.setToggleGroup(sizeGroup);
        mBtn.setToggleGroup(sizeGroup);
        lBtn.setToggleGroup(sizeGroup);
        xlBtn.setToggleGroup(sizeGroup);

        // buttions for avaliable chocolate types
        ToggleGroup typeGroup = new ToggleGroup();
        RadioButton dkChocBtn = new RadioButton("Dark Chocolate");
        RadioButton mkChocBtn = new RadioButton("Milk Chocolate");
        RadioButton wtChocBtn = new RadioButton("White Chocolate");
        RadioButton cncChocBtn = new RadioButton("Cookies and Cream Chocolate");
        dkChocBtn.setToggleGroup(typeGroup);
        mkChocBtn.setToggleGroup(typeGroup);
        wtChocBtn.setToggleGroup(typeGroup);
        cncChocBtn.setToggleGroup(typeGroup);

        // button for choosethe sweetness of the chocolatte and rhe level of sugar
        ToggleGroup sweetnessesGroup = new ToggleGroup();
        RadioButton nasweetnessesBtn = new RadioButton("0%");
        RadioButton qusweetnessesBtn = new RadioButton("25%");
        RadioButton hfsweetnessesBtn = new RadioButton("50%");
        RadioButton mysweetnessesBtn = new RadioButton("75%");
        RadioButton orsweetnessesBtn = new RadioButton("100%");
        nasweetnessesBtn.setToggleGroup(sweetnessesGroup);
        qusweetnessesBtn.setToggleGroup(sweetnessesGroup);
        hfsweetnessesBtn.setToggleGroup(sweetnessesGroup);
        mysweetnessesBtn.setToggleGroup(sweetnessesGroup);
        orsweetnessesBtn.setToggleGroup(sweetnessesGroup);

        // selections of fillings
        ToggleGroup fillingGroup = new ToggleGroup();
        RadioButton nfFillBtn = new RadioButton("None");
        RadioButton clFillBtn = new RadioButton("Caramel");
        RadioButton nsFillBtn = new RadioButton("Nuts");
        RadioButton ftFillBtn = new RadioButton("Fruits");
        nfFillBtn.setToggleGroup(fillingGroup);
        clFillBtn.setToggleGroup(fillingGroup);
        nsFillBtn.setToggleGroup(fillingGroup);
        ftFillBtn.setToggleGroup(fillingGroup);

        // selection of fillings
        ToggleGroup toppingGroup = new ToggleGroup();
        RadioButton ntTopBtn = new RadioButton("None");
        RadioButton ftTopBtn = new RadioButton("Fruits");
        RadioButton ooTopBtn = new RadioButton("Oreo");
        RadioButton cpTopBtn = new RadioButton("Popping Candy");
        RadioButton ecTopBtn = new RadioButton("Extra Chocolate");
        ntTopBtn.setToggleGroup(toppingGroup);
        ftTopBtn.setToggleGroup(toppingGroup);
        ooTopBtn.setToggleGroup(toppingGroup);
        cpTopBtn.setToggleGroup(toppingGroup);
        ecTopBtn.setToggleGroup(toppingGroup);

        HBox sizeRow = new HBox(5, sBtn, mBtn, lBtn, xlBtn);
        HBox typeRow = new HBox(5, dkChocBtn, mkChocBtn, wtChocBtn, cncChocBtn);
        HBox sweetnessesRow = new HBox(5, nasweetnessesBtn, qusweetnessesBtn, hfsweetnessesBtn, mysweetnessesBtn,
                orsweetnessesBtn);
        HBox fillRow = new HBox(5, nfFillBtn, clFillBtn, nsFillBtn, ftFillBtn);
        HBox topRow = new HBox(5, ntTopBtn, ftTopBtn, ooTopBtn, cpTopBtn, ecTopBtn);
        sizeRow.setAlignment(Pos.CENTER);
        typeRow.setAlignment(Pos.CENTER);
        sweetnessesRow.setAlignment(Pos.CENTER);
        fillRow.setAlignment(Pos.CENTER);
        topRow.setAlignment(Pos.CENTER);

        // when submitted a new chjocolate object is than made based on the user's
        // selection
        Button submitButton = new Button("Submit");
        submitButton.setOnAction(event -> {
            String name = nameField.getText().trim();
            String id = idField.getText().trim();
            double price = Double.parseDouble(priceField.getText().trim());
            Size size;
            Types type;
            Sweetness sweetnesses;
            Fillings filling;
            Toppings toppings;

            if (sBtn.isSelected()) {
                size = Size.S;
            } else if (mBtn.isSelected()) {
                size = Size.M;
            } else if (lBtn.isSelected()) {
                size = Size.L;
            } else {
                size = Size.XL;
            }

            if (dkChocBtn.isSelected()) {
                type = Types.DARK_CHOCOLATE;
            } else if (mkChocBtn.isSelected()) {
                type = Types.MILK_CHOCOLATE;
            } else if (wtChocBtn.isSelected()) {
                type = Types.WHITE_CHOCOLATE;
            } else {
                type = Types.COOKIE_AND_CREAM;
            }

            if (nasweetnessesBtn.isSelected()) {
                sweetnesses = Sweetness.ZERO;
            } else if (qusweetnessesBtn.isSelected()) {
                sweetnesses = Sweetness.TWENTY_FIVE;
            } else if (hfsweetnessesBtn.isSelected()) {
                sweetnesses = Sweetness.FIFTY;
            } else if (mysweetnessesBtn.isSelected()) {
                sweetnesses = Sweetness.SEVENTY_FIVE;
            } else {
                sweetnesses = Sweetness.HUNDRED;
            }

            if (nfFillBtn.isSelected()) {
                filling = Fillings.NONE;
            } else if (clFillBtn.isSelected()) {
                filling = Fillings.CARAMEL;
            } else if (nsFillBtn.isSelected()) {
                filling = Fillings.NUTS;
            } else {
                filling = Fillings.FRUITS;
            }

            if (ntTopBtn.isSelected()) {
                toppings = Toppings.NONE;
            } else if (ftTopBtn.isSelected()) {
                toppings = Toppings.FRUITS;
            } else if (ooTopBtn.isSelected()) {
                toppings = Toppings.OREO;
            } else if (cpTopBtn.isSelected()) {
                toppings = Toppings.CANDY_POP;
            } else {
                toppings = Toppings.EXTRA_CHOCOLATE;
            }
            // when the chocolate is created the window automatically closes
            if (!name.isEmpty() && !id.isEmpty() && price != 0) {
                Chocolate c = new Chocolate(id, name, price, size, sweetnesses, type, filling, toppings);
                controller.addChocolate(c);
                stage.close();
            }

        });

        Button cancelBtn = new Button("Cancel");
        cancelBtn.setOnAction(event -> stage.close());

        HBox buttonRow = new HBox(5, submitButton, cancelBtn);
        buttonRow.setAlignment(Pos.CENTER);

        VBox root = new VBox(5, nameRow, idRow, priceRow, sizeRow, typeRow, sweetnessesRow, fillRow, topRow, buttonRow);
        root.setAlignment(Pos.CENTER);

        Scene testScene = new Scene(root, 500, 500);
        stage.setScene(testScene);
        stage.show();

    }

    private void editingChocPanel(int index) {
        Stage stage = new Stage();
        stage.initOwner(primaryStage);
        stage.initModality(Modality.APPLICATION_MODAL);

        // stores the old name, ID and prices andd creates new sections for new options
        // but does not changes them yet

        TextField nameField = new TextField();
        TextField idField = new TextField();
        TextField priceField = new TextField();

        String oldName = model.chocoProperties().get(index).getName();
        nameField.setPromptText("Enter new name");
        nameField.setText(oldName);

        String oldID = model.chocoProperties().get(index).getProductId();
        idField.setPromptText("Enter new ID");
        idField.setText(oldID);

        String oldPrice = String.valueOf(model.chocoProperties().get(index).getPrice());
        priceField.setPromptText("Enter new price");
        priceField.setText(oldPrice);

        HBox nameRow = new HBox(5, new Label("Name:"), nameField);
        nameRow.setAlignment(Pos.CENTER);
        HBox idRow = new HBox(5, new Label("ID: "), idField);
        idRow.setAlignment(Pos.CENTER);
        HBox priceRow = new HBox(5, new Label("Price:"), priceField);
        priceRow.setAlignment(Pos.CENTER);

        ToggleGroup sizeGroup = new ToggleGroup();
        RadioButton sBtn = new RadioButton("Small");
        RadioButton mBtn = new RadioButton("Medium");
        RadioButton lBtn = new RadioButton("Large");
        RadioButton xlBtn = new RadioButton("Extra Large");
        sBtn.setToggleGroup(sizeGroup);
        mBtn.setToggleGroup(sizeGroup);
        lBtn.setToggleGroup(sizeGroup);
        xlBtn.setToggleGroup(sizeGroup);

        ToggleGroup typeGroup = new ToggleGroup();
        RadioButton dkChocBtn = new RadioButton("Dark Chocolate");
        RadioButton mkChocBtn = new RadioButton("Milk Chocolate");
        RadioButton wtChocBtn = new RadioButton("White Chocolate");
        RadioButton cncChocBtn = new RadioButton("Cookies and Cream Chocolate");
        dkChocBtn.setToggleGroup(typeGroup);
        mkChocBtn.setToggleGroup(typeGroup);
        wtChocBtn.setToggleGroup(typeGroup);
        cncChocBtn.setToggleGroup(typeGroup);

        ToggleGroup sweetnessesGroup = new ToggleGroup();
        RadioButton nasweetnessesBtn = new RadioButton("0%");
        RadioButton qusweetnessesBtn = new RadioButton("25%");
        RadioButton hfsweetnessesBtn = new RadioButton("50%");
        RadioButton mysweetnessesBtn = new RadioButton("75%");
        RadioButton orsweetnessesBtn = new RadioButton("100%");
        nasweetnessesBtn.setToggleGroup(sweetnessesGroup);
        qusweetnessesBtn.setToggleGroup(sweetnessesGroup);
        hfsweetnessesBtn.setToggleGroup(sweetnessesGroup);
        mysweetnessesBtn.setToggleGroup(sweetnessesGroup);
        orsweetnessesBtn.setToggleGroup(sweetnessesGroup);

        ToggleGroup fillingGroup = new ToggleGroup();
        RadioButton nfFillBtn = new RadioButton("None");
        RadioButton clFillBtn = new RadioButton("Caramel");
        RadioButton nsFillBtn = new RadioButton("Nuts");
        RadioButton ftFillBtn = new RadioButton("Fruits");
        nfFillBtn.setToggleGroup(fillingGroup);
        clFillBtn.setToggleGroup(fillingGroup);
        nsFillBtn.setToggleGroup(fillingGroup);
        ftFillBtn.setToggleGroup(fillingGroup);

        ToggleGroup toppingGroup = new ToggleGroup();
        RadioButton ntTopBtn = new RadioButton("None");
        RadioButton ftTopBtn = new RadioButton("Fruits");
        RadioButton ooTopBtn = new RadioButton("Oreo");
        RadioButton cpTopBtn = new RadioButton("Popping Candy");
        RadioButton ecTopBtn = new RadioButton("Extra Chocolate");
        ntTopBtn.setToggleGroup(toppingGroup);
        ftTopBtn.setToggleGroup(toppingGroup);
        ooTopBtn.setToggleGroup(toppingGroup);
        cpTopBtn.setToggleGroup(toppingGroup);
        ecTopBtn.setToggleGroup(toppingGroup);

        HBox sizeRow = new HBox(5, sBtn, mBtn, lBtn, xlBtn);
        HBox typeRow = new HBox(5, dkChocBtn, mkChocBtn, wtChocBtn, cncChocBtn);
        HBox sweetnessesRow = new HBox(5, nasweetnessesBtn, qusweetnessesBtn, hfsweetnessesBtn, mysweetnessesBtn,
                orsweetnessesBtn);
        HBox fillRow = new HBox(5, nfFillBtn, clFillBtn, nsFillBtn, ftFillBtn);
        HBox topRow = new HBox(5, ntTopBtn, ftTopBtn, ooTopBtn, cpTopBtn, ecTopBtn);
        sizeRow.setAlignment(Pos.CENTER);
        typeRow.setAlignment(Pos.CENTER);
        sweetnessesRow.setAlignment(Pos.CENTER);
        fillRow.setAlignment(Pos.CENTER);
        topRow.setAlignment(Pos.CENTER);

        // ffills up the newly created classes for the option chosen by the user and
        // than compares the old and new chioces before updating the choclate to the new
        // specificatsions
        Button submitBtn = new Button("Submit");
        submitBtn.setOnAction(event -> {
            String name = nameField.getText().trim();
            String id = idField.getText().trim();
            double price = Double.parseDouble(priceField.getText().trim());

            Size oldSize = model.chocoProperties().get(index).getSize();
            Types oldTypes = model.chocoProperties().get(index).getType();
            Sweetness oldSweetness = model.chocoProperties().get(index).getSweetness();
            Fillings oldFillings = model.chocoProperties().get(index).getFilling();
            Toppings oldToppings = model.chocoProperties().get(index).getToppings();

            Size newSize;
            Types newTypes;
            Sweetness newSweetness;
            Fillings newFillings;
            Toppings newToppings;

            if (sBtn.isSelected()) {
                newSize = Size.S;
            } else if (mBtn.isSelected()) {
                newSize = Size.M;
            } else if (lBtn.isSelected()) {
                newSize = Size.L;
            } else {
                newSize = Size.XL;
            }

            if (dkChocBtn.isSelected()) {
                newTypes = Types.DARK_CHOCOLATE;
            } else if (mkChocBtn.isSelected()) {
                newTypes = Types.MILK_CHOCOLATE;
            } else if (wtChocBtn.isSelected()) {
                newTypes = Types.WHITE_CHOCOLATE;
            } else {
                newTypes = Types.COOKIE_AND_CREAM;
            }

            if (nasweetnessesBtn.isSelected()) {
                newSweetness = Sweetness.ZERO;
            } else if (qusweetnessesBtn.isSelected()) {
                newSweetness = Sweetness.TWENTY_FIVE;
            } else if (hfsweetnessesBtn.isSelected()) {
                newSweetness = Sweetness.FIFTY;
            } else if (mysweetnessesBtn.isSelected()) {
                newSweetness = Sweetness.SEVENTY_FIVE;
            } else {
                newSweetness = Sweetness.HUNDRED;
            }

            if (nfFillBtn.isSelected()) {
                newFillings = Fillings.NONE;
            } else if (clFillBtn.isSelected()) {
                newFillings = Fillings.CARAMEL;
            } else if (nsFillBtn.isSelected()) {
                newFillings = Fillings.NUTS;
            } else {
                newFillings = Fillings.FRUITS;
            }

            if (ntTopBtn.isSelected()) {
                newToppings = Toppings.NONE;
            } else if (ftTopBtn.isSelected()) {
                newToppings = Toppings.FRUITS;
            } else if (ooTopBtn.isSelected()) {
                newToppings = Toppings.OREO;
            } else if (cpTopBtn.isSelected()) {
                newToppings = Toppings.CANDY_POP;
            } else {
                newToppings = Toppings.EXTRA_CHOCOLATE;
            }

            boolean changeSize = oldSize != newSize;
            boolean changeType = oldTypes != newTypes;
            boolean changeSweetness = oldSweetness != newSweetness;
            boolean changeFilling = oldFillings != newFillings;
            boolean changeTopping = oldToppings != newToppings;
            boolean newNameIDAndPriceNotEmpty = !name.isEmpty() && !id.isEmpty() && price != 0;
            // also automatically closes thje window when finished
            if (changeSize || changeType || changeSweetness || changeFilling || changeTopping
                    || newNameIDAndPriceNotEmpty) {
                Chocolate c = new Chocolate(id, name, price, newSize, newSweetness, newTypes, newFillings,
                        newToppings);
                controller.updateChoco(c, index);
                stage.close();
            }

        });
        Button cancelBtn = new Button("Cancel");
        cancelBtn.setOnAction(event -> stage.close());

        HBox buttonRow = new HBox(5, submitBtn, cancelBtn);
        buttonRow.setAlignment(Pos.CENTER);

        VBox root = new VBox(5, nameRow, idRow, priceRow, sizeRow, typeRow, sweetnessesRow, fillRow, topRow, buttonRow);
        root.setAlignment(Pos.CENTER);

        Scene testScene = new Scene(root, 500, 500);
        stage.setScene(testScene);
        stage.show();
    }

    private void staffOrderStatus() {
        Stage stage = new Stage();
        stage.initOwner(primaryStage);
        stage.initModality(Modality.APPLICATION_MODAL);

        Label title = new Label("Customer Order Status");
        Label statusLabel = new Label("" + controller.getOrderStatus());
        Label messageLabel = new Label("");
        Button updateBtn = new Button("Update Status");
        Button closeBtn = new Button("Close");

        // new taboleview for the list of chocolates ordered by the user
        TableView<Chocolate> orderView = new TableView<>();

        // table shows only name ID price and Status
        TableColumn<Chocolate, String> nameCol = new TableColumn<>("Name");
        TableColumn<Chocolate, String> idCol = new TableColumn<>("Product ID");
        TableColumn<Chocolate, String> priceCol = new TableColumn<>("Price");
        TableColumn<Chocolate, OrderStatus> statusCol = new TableColumn<>("Status");

        nameCol.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getName()));
        idCol.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getProductId()));
        priceCol.setCellValueFactory(cellData -> new SimpleStringProperty("$ " + cellData.getValue().getPrice()));
        statusCol.setCellValueFactory(cellData -> new SimpleObjectProperty<>(controller.getOrderStatus()));

        orderView.getColumns().addAll(nameCol, idCol, priceCol, statusCol);
        ObservableList<Chocolate> orderList = FXCollections.observableArrayList();
        orderList.addAll(controller.getCurrentOrder());
        orderView.setItems(orderList);

        // new buttons for the users to update the status of the order
        ToggleGroup statusGroup = new ToggleGroup();
        RadioButton confirmedBtn = new RadioButton("Confirmed");
        RadioButton prepareBtn = new RadioButton("Preparing");
        RadioButton readyBtn = new RadioButton("Ready for Pickup");
        RadioButton deliveryBtn = new RadioButton("Out for Delivery");
        RadioButton completeBtn = new RadioButton("Complete");
        confirmedBtn.setToggleGroup(statusGroup);
        prepareBtn.setToggleGroup(statusGroup);
        readyBtn.setToggleGroup(statusGroup);
        deliveryBtn.setToggleGroup(statusGroup);
        completeBtn.setToggleGroup(statusGroup);

        updateBtn.setOnAction(event -> {
            OrderStatus newStat;

            if (confirmedBtn.isSelected()) {
                newStat = OrderStatus.CONFIRMED;
            } else if (prepareBtn.isSelected()) {
                newStat = OrderStatus.PREPARING;
            } else if (readyBtn.isSelected()) {
                newStat = OrderStatus.READY_FOR_PICKUP;
            } else if (deliveryBtn.isSelected()) {
                newStat = OrderStatus.OUT_FOR_DELIVERY;
            } else {
                newStat = OrderStatus.COMPLETE;
                controller.clearOrder();
                orderList.clear();

            }

            if (!(newStat == null)) {
                controller.updateOrderStatus(newStat);
                statusLabel.setText("" + controller.getOrderStatus());
                messageLabel.setText("Status Updated");
            }
            stage.close();

        });

        closeBtn.setOnAction(event -> stage.close());

        HBox buttonRow = new HBox(5, updateBtn, closeBtn);

        VBox root = new VBox(5, title, orderView, statusLabel, confirmedBtn, prepareBtn, readyBtn, deliveryBtn,
                completeBtn, messageLabel, buttonRow);
        root.setAlignment(Pos.CENTER);

        Scene scene = new Scene(root, 500, 500);
        stage.setScene(scene);
        stage.show();

    }

    private void showMainMenu() {
        createAndLayoutControls();
    }

    private void showCustomerMainMenu() {

        Label title = new Label("Customer Menu");

        Button searchBtn = new Button("Search");
        Button filterBtn = new Button("Filter");
        Button buildChocolateBtn = new Button("Build Chocolate");
        Button addCartBtn = new Button("Add to Cart");
        Button cartBtn = new Button("My Cart");
        Button showAllBtn = new Button("Show All");
        Button logoutBtn = new Button("Log Out");
        Button orderStatusBtn = new Button("Order Status");

        HBox menuRow = new HBox(10, searchBtn, filterBtn, buildChocolateBtn, addCartBtn, cartBtn, orderStatusBtn,
                showAllBtn);
        menuRow.setAlignment(Pos.CENTER);

        chocolateView = new TableView<>();
        chocolateView.setItems(model.chocoProperties());

        TableColumn<Chocolate, String> idCol = new TableColumn<>("ID");
        TableColumn<Chocolate, String> nameCol = new TableColumn<>("Name");
        TableColumn<Chocolate, String> priceCol = new TableColumn<>("Price");
        TableColumn<Chocolate, Types> typeCol = new TableColumn<>("Type");
        TableColumn<Chocolate, Size> sizeCol = new TableColumn<>("Size");
        TableColumn<Chocolate, String> sweetnessCol = new TableColumn<>("Sweetness");

        idCol.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getProductId()));
        nameCol.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getName()));
        priceCol.setCellValueFactory(cellData -> new SimpleStringProperty("$ " + cellData.getValue().getPrice()));
        typeCol.setCellValueFactory(cellData -> new SimpleObjectProperty<Types>(cellData.getValue().getType()));
        sizeCol.setCellValueFactory(cellData -> new SimpleObjectProperty<Size>(cellData.getValue().getSize()));
        sweetnessCol.setCellValueFactory(
                cellData -> new SimpleStringProperty(cellData.getValue().getSweetness().getPercentage() + "%"));

        chocolateView.getColumns().addAll(idCol, nameCol, priceCol, typeCol, sizeCol, sweetnessCol);

        logoutBtn.setOnAction(event -> {
            controller.setMemberSignedIn(false);
            showMainMenu();
        });
        searchBtn.setOnAction(event -> createSearchForm());
        showAllBtn.setOnAction(event -> chocolateView.setItems(model.chocoProperties()));
        filterBtn.setOnAction(event -> createFilterForm());
        buildChocolateBtn.setOnAction(event -> createBuildChocolateForm());
        cartBtn.setOnAction(event -> createCartForm());
        addCartBtn.setOnAction(event -> {
            Chocolate selectedChocolate = chocolateView.getSelectionModel().getSelectedItem();

            if (selectedChocolate != null) {
                controller.addToCart(selectedChocolate);
            }
        });
        orderStatusBtn.setOnAction(event -> createOrderStatusForm());

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

        Label addingLabel = new Label("Please enter the exact chocolate name");
        Label adding2Label = new Label("case-sensitive!! (ex) Dark Chocolate).");
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

                    createAddCartForm(chocolate);

                    stage.close();

                } else {
                    messageLabel.setText("Chocolate not found");
                }
            }
        });

        cancelBtn.setOnAction(event -> stage.close());

        HBox buttonRow = new HBox(5, searchBtn, cancelBtn);
        buttonRow.setAlignment(Pos.CENTER);

        VBox root = new VBox(5, addingLabel, adding2Label, searchRow, messageLabel, buttonRow);
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

    private void createBuildChocolateForm() {

        Stage stage = new Stage();
        stage.initOwner(primaryStage);
        stage.initModality(Modality.APPLICATION_MODAL);

        Label title = new Label("Build Your Chocolate");

        TextField nameField = new TextField();
        nameField.setPromptText("Enter chocolate name");

        HBox nameRow = new HBox(5, new Label("Chocolate Name:"), nameField);
        nameRow.setAlignment(Pos.CENTER);

        ToggleGroup typeGroup = new ToggleGroup();

        RadioButton whiteBtn = new RadioButton("White");
        whiteBtn.setToggleGroup(typeGroup);

        RadioButton darkBtn = new RadioButton("Dark");
        darkBtn.setToggleGroup(typeGroup);

        RadioButton milkBtn = new RadioButton("Milk");
        milkBtn.setToggleGroup(typeGroup);

        RadioButton cookieBtn = new RadioButton("Cookie and Cream");
        cookieBtn.setToggleGroup(typeGroup);

        HBox typeRow = new HBox(5, new Label("Type:"), whiteBtn, darkBtn, milkBtn, cookieBtn);
        typeRow.setAlignment(Pos.CENTER);

        ToggleGroup sizeGroup = new ToggleGroup();

        RadioButton smallBtn = new RadioButton("S");
        smallBtn.setToggleGroup(sizeGroup);

        RadioButton mediumBtn = new RadioButton("M");
        mediumBtn.setToggleGroup(sizeGroup);

        RadioButton largeBtn = new RadioButton("L");
        largeBtn.setToggleGroup(sizeGroup);

        RadioButton extraLargeBtn = new RadioButton("XL");
        extraLargeBtn.setToggleGroup(sizeGroup);

        HBox sizeRow = new HBox(5, new Label("Size:"), smallBtn, mediumBtn, largeBtn, extraLargeBtn);
        sizeRow.setAlignment(Pos.CENTER);

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

        HBox sweetnessRow = new HBox(5, new Label("Sweetness:"), zeroBtn, twentyFiveBtn, fiftyBtn, seventyFiveBtn,
                hundredBtn);
        sweetnessRow.setAlignment(Pos.CENTER);

        ToggleGroup fillingGroup = new ToggleGroup();

        RadioButton noFillingBtn = new RadioButton("None");
        noFillingBtn.setToggleGroup(fillingGroup);

        RadioButton caramelBtn = new RadioButton("Caramel");
        caramelBtn.setToggleGroup(fillingGroup);

        RadioButton nutsBtn = new RadioButton("Nuts");
        nutsBtn.setToggleGroup(fillingGroup);

        RadioButton fruitsBtn = new RadioButton("Fruits");
        fruitsBtn.setToggleGroup(fillingGroup);

        HBox fillingRow = new HBox(5, new Label("Filling:"), noFillingBtn, caramelBtn, nutsBtn, fruitsBtn);
        fillingRow.setAlignment(Pos.CENTER);

        ToggleGroup toppingGroup = new ToggleGroup();

        RadioButton noToppingBtn = new RadioButton("None");
        noToppingBtn.setToggleGroup(toppingGroup);

        RadioButton fruitToppingBtn = new RadioButton("Fruits");
        fruitToppingBtn.setToggleGroup(toppingGroup);

        RadioButton oreoBtn = new RadioButton("Oreo");
        oreoBtn.setToggleGroup(toppingGroup);

        RadioButton candyBtn = new RadioButton("Candy Pop");
        candyBtn.setToggleGroup(toppingGroup);

        RadioButton extraChocolateBtn = new RadioButton("Extra Chocolate");
        extraChocolateBtn.setToggleGroup(toppingGroup);

        HBox toppingRow = new HBox(5, new Label("Topping:"), noToppingBtn, fruitToppingBtn, oreoBtn, candyBtn,
                extraChocolateBtn);
        toppingRow.setAlignment(Pos.CENTER);

        Label messageLabel = new Label("");

        Button addBtn = new Button("Add to Cart");
        Button cancelBtn = new Button("Cancel");

        addBtn.setOnAction(event -> {

            String name = nameField.getText().trim();

            Types type = null;
            Size size = null;
            Sweetness sweetness = null;
            Fillings filling = null;
            Toppings topping = null;

            if (whiteBtn.isSelected()) {
                type = Types.WHITE_CHOCOLATE;
            } else if (darkBtn.isSelected()) {
                type = Types.DARK_CHOCOLATE;
            } else if (milkBtn.isSelected()) {
                type = Types.MILK_CHOCOLATE;
            } else if (cookieBtn.isSelected()) {
                type = Types.COOKIE_AND_CREAM;
            }

            if (smallBtn.isSelected()) {
                size = Size.S;
            } else if (mediumBtn.isSelected()) {
                size = Size.M;
            } else if (largeBtn.isSelected()) {
                size = Size.L;
            } else if (extraLargeBtn.isSelected()) {
                size = Size.XL;
            }

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

            if (noFillingBtn.isSelected()) {
                filling = Fillings.NONE;
            } else if (caramelBtn.isSelected()) {
                filling = Fillings.CARAMEL;
            } else if (nutsBtn.isSelected()) {
                filling = Fillings.NUTS;
            } else if (fruitsBtn.isSelected()) {
                filling = Fillings.FRUITS;
            }

            if (noToppingBtn.isSelected()) {
                topping = Toppings.NONE;
            } else if (fruitToppingBtn.isSelected()) {
                topping = Toppings.FRUITS;
            } else if (oreoBtn.isSelected()) {
                topping = Toppings.OREO;
            } else if (candyBtn.isSelected()) {
                topping = Toppings.CANDY_POP;
            } else if (extraChocolateBtn.isSelected()) {
                topping = Toppings.EXTRA_CHOCOLATE;
            }

            if (name.isEmpty() || type == null || size == null || sweetness == null
                    || filling == null || topping == null) {

                messageLabel.setText("Please complete all options");

            } else {
                controller.buildChocolate(name, type, size, sweetness, filling, topping);
                messageLabel.setText("Chocolate added to cart");
            }
        });

        cancelBtn.setOnAction(event -> stage.close());

        HBox buttonRow = new HBox(5, addBtn, cancelBtn);
        buttonRow.setAlignment(Pos.CENTER);

        VBox root = new VBox(10, title, nameRow, typeRow, sizeRow, sweetnessRow, fillingRow, toppingRow, messageLabel,
                buttonRow);
        root.setAlignment(Pos.CENTER);

        Scene scene = new Scene(root, 650, 350);

        stage.setScene(scene);
        stage.show();
    }

    private void createCheckoutForm(Stage cartStage) {

        Stage stage = new Stage();
        stage.initOwner(primaryStage);
        stage.initModality(Modality.APPLICATION_MODAL);

        Label title = new Label("Checkout");
        Label paymentLabel = new Label("Payment Method:");

        ToggleGroup paymentGroup = new ToggleGroup();

        RadioButton cashBtn = new RadioButton("Cash");
        cashBtn.setToggleGroup(paymentGroup);

        RadioButton cardBtn = new RadioButton("Card");
        cardBtn.setToggleGroup(paymentGroup);

        RadioButton transferBtn = new RadioButton("Bank Transfer");
        transferBtn.setToggleGroup(paymentGroup);

        HBox paymentRow = new HBox(10, cashBtn, cardBtn, transferBtn);
        paymentRow.setAlignment(Pos.CENTER);

        Label deliveryLabel = new Label("Delivery Method:");

        ToggleGroup deliveryGroup = new ToggleGroup();

        RadioButton pickupBtn = new RadioButton("Pick Up");
        pickupBtn.setToggleGroup(deliveryGroup);

        RadioButton deliveryBtn = new RadioButton("Delivery");
        deliveryBtn.setToggleGroup(deliveryGroup);

        HBox deliveryRow = new HBox(10, pickupBtn, deliveryBtn);
        deliveryRow.setAlignment(Pos.CENTER);

        Label messageLabel = new Label("");

        Button confirmBtn = new Button("Confirm");
        Button cancelBtn = new Button("Cancel");

        confirmBtn.setOnAction(event -> {
            PaymentMethod paymentMethod = null;
            Delivery delivery = null;

            if (cashBtn.isSelected()) {
                paymentMethod = PaymentMethod.CASH;
            } else if (cardBtn.isSelected()) {
                paymentMethod = PaymentMethod.CARD;
            } else if (transferBtn.isSelected()) {
                paymentMethod = PaymentMethod.TRANSFER;
            }

            if (pickupBtn.isSelected()) {
                delivery = Delivery.PICK_UP;
            } else if (deliveryBtn.isSelected()) {
                delivery = Delivery.DELIVERY;
            }

            if (paymentMethod == null || delivery == null) {
                messageLabel.setText("Please select payment and delivery method");
            } else {
                controller.checkout(paymentMethod, delivery);
                createCheckoutSuccessForm();
                stage.close();
                cartStage.close();
            }
        });

        cancelBtn.setOnAction(event -> stage.close());

        HBox buttonRow = new HBox(10, confirmBtn, cancelBtn);
        buttonRow.setAlignment(Pos.CENTER);

        VBox root = new VBox(10, title, paymentLabel, paymentRow, deliveryLabel, deliveryRow, messageLabel, buttonRow);
        root.setAlignment(Pos.CENTER);

        Scene scene = new Scene(root, 400, 300);
        stage.setScene(scene);
        stage.show();
    }

    private void createCartForm() {

        Stage stage = new Stage();
        stage.initOwner(primaryStage);
        stage.initModality(Modality.APPLICATION_MODAL);

        Label title = new Label("My Cart");
        Label totalLabel = new Label("Total: $ " + controller.calculateTotal());

        TableView<Chocolate> cartView = new TableView<>();

        TableColumn<Chocolate, String> nameCol = new TableColumn<>("Name");
        TableColumn<Chocolate, Types> typeCol = new TableColumn<>("Type");
        TableColumn<Chocolate, Size> sizeCol = new TableColumn<>("Size");
        TableColumn<Chocolate, String> priceCol = new TableColumn<>("Price");

        nameCol.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getName()));
        typeCol.setCellValueFactory(cellData -> new SimpleObjectProperty<Types>(cellData.getValue().getType()));
        sizeCol.setCellValueFactory(cellData -> new SimpleObjectProperty<Size>(cellData.getValue().getSize()));
        priceCol.setCellValueFactory(cellData -> new SimpleStringProperty("$ " + cellData.getValue().getPrice()));

        cartView.getColumns().addAll(nameCol, typeCol, sizeCol, priceCol);

        ObservableList<Chocolate> cartList = FXCollections.observableArrayList();
        cartList.addAll(controller.getCart());
        cartView.setItems(cartList);

        Label totalpLabel = new Label("Total: $ " + controller.calculateTotal());
        Label discountLabel = new Label("Member Discount: 10%");
        Label finalTotalLabel = new Label("Final Total: $ " + controller.calculateFinalTotal());

        Button removeBtn = new Button("Remove");
        Button checkoutBtn = new Button("Checkout");
        Button closeBtn = new Button("Close");

        removeBtn.setOnAction(event -> {
            Chocolate selectedChocolate = cartView.getSelectionModel().getSelectedItem();

            if (selectedChocolate != null) {
                controller.removeFromCart(selectedChocolate);
                cartList.remove(selectedChocolate);
                totalpLabel.setText("Total: $ " + controller.calculateTotal());
            }
        });

        checkoutBtn.setOnAction(event -> createCheckoutForm(stage));
        closeBtn.setOnAction(event -> stage.close());

        HBox buttonRow = new HBox(5, removeBtn, checkoutBtn, closeBtn);
        buttonRow.setAlignment(Pos.CENTER);

        VBox root = new VBox(10, title, cartView, totalpLabel, discountLabel, finalTotalLabel, buttonRow);

        Scene scene = new Scene(root, 500, 400);
        stage.setScene(scene);
        stage.show();
    }

    private void createCheckoutSuccessForm() {

        Stage stage = new Stage();
        stage.initOwner(primaryStage);
        stage.initModality(Modality.APPLICATION_MODAL);

        Label messageLabel = new Label("Checkout successful");

        Button closeBtn = new Button("Close");
        closeBtn.setOnAction(event -> stage.close());

        VBox root = new VBox(10, messageLabel, closeBtn);
        root.setAlignment(Pos.CENTER);

        Scene scene = new Scene(root, 250, 120);

        stage.setScene(scene);
        stage.show();
    }

    private void createAddCartForm(Chocolate chocolate) {

        Stage stage = new Stage();
        stage.initOwner(primaryStage);
        stage.initModality(Modality.APPLICATION_MODAL);

        Label messageLabel = new Label("Do you want to add this chocolate to your cart?");

        Button yesBtn = new Button("Yes");
        Button noBtn = new Button("No");

        yesBtn.setOnAction(event -> {
            controller.addToCart(chocolate);
            stage.close();
        });

        noBtn.setOnAction(event -> stage.close());

        HBox buttonRow = new HBox(10, yesBtn, noBtn);
        buttonRow.setAlignment(Pos.CENTER);

        VBox root = new VBox(10, messageLabel, buttonRow);
        root.setAlignment(Pos.CENTER);

        Scene scene = new Scene(root, 350, 120);
        stage.setScene(scene);
        stage.show();
    }

    private void createOrderStatusForm() {

        Stage stage = new Stage();
        stage.initOwner(primaryStage);
        stage.initModality(Modality.APPLICATION_MODAL);

        Label title = new Label("Order Status");

        TableView<Chocolate> orderView = new TableView<>();

        TableColumn<Chocolate, String> nameCol = new TableColumn<>("Name");
        TableColumn<Chocolate, Types> typeCol = new TableColumn<>("Type");
        TableColumn<Chocolate, Size> sizeCol = new TableColumn<>("Size");
        TableColumn<Chocolate, String> priceCol = new TableColumn<>("Price");

        nameCol.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getName()));
        typeCol.setCellValueFactory(cellData -> new SimpleObjectProperty<Types>(cellData.getValue().getType()));
        sizeCol.setCellValueFactory(cellData -> new SimpleObjectProperty<Size>(cellData.getValue().getSize()));
        priceCol.setCellValueFactory(cellData -> new SimpleStringProperty("$ " + cellData.getValue().getPrice()));

        orderView.getColumns().addAll(nameCol, typeCol, sizeCol, priceCol);

        ObservableList<Chocolate> orderList = FXCollections.observableArrayList();
        orderList.addAll(controller.getCurrentOrder());
        orderView.setItems(orderList);

        Label statusLabel = new Label("Status: " + controller.getOrderStatus());

        Button closeBtn = new Button("Close");
        closeBtn.setOnAction(event -> stage.close());

        VBox root = new VBox(10, title, orderView, statusLabel, closeBtn);
        root.setAlignment(Pos.CENTER);

        Scene scene = new Scene(root, 500, 400);
        stage.setScene(scene);
        stage.show();
    }

}