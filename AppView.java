import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.scene.layout.HBox;

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

        customerBtn.setPrefSize(180, 50);
        staffBtn.setPrefSize(180, 50);
        exitBtn.setPrefSize(180, 50);

        customerBtn.setOnAction(event -> showCustomerMenu());
        exitBtn.setOnAction(event -> primaryStage.close());

        view.getChildren().addAll(title, customerBtn, staffBtn, exitBtn);
    }

    private void showCustomerMenu() {

        Label title = new Label("Customer Sign In");

        Label usernameLabel = new Label("Username:");
        TextField usernameField = new TextField();

        Label passwordLabel = new Label("Password:");
        TextField passwordField = new TextField();

        Button loginBtn = new Button("Log In");
        Button signUpBtn = new Button("Sign Up");
        Button returnBtn = new Button("Return");

        usernameField.setPrefWidth(200);
        passwordField.setPrefWidth(200);

        loginBtn.setPrefSize(100, 30);
        signUpBtn.setPrefSize(100, 30);
        returnBtn.setPrefSize(100, 30);

        HBox usernameRow = new HBox(10, usernameLabel, usernameField);
        HBox passwordRow = new HBox(10, passwordLabel, passwordField);
        HBox buttonRow = new HBox(10, loginBtn, signUpBtn);

        usernameRow.setAlignment(Pos.CENTER);
        passwordRow.setAlignment(Pos.CENTER);
        buttonRow.setAlignment(Pos.CENTER);

        view.getChildren().clear();

        view.getChildren().addAll(
                title,
                usernameRow,
                passwordRow,
                buttonRow,
                returnBtn);
    }

}
