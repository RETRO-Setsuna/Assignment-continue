import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
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

        Label title = new Label("HD Choco Shop");

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

        Label title = new Label("CUSTOMER");

        Button signUpBtn = new Button("Sign Up");
        Button signInBtn = new Button("Sign In");

        Button searchBtn = new Button("Search");
        Button sortBtn = new Button("Sort");

        Button filterBtn = new Button("Filter");
        Button buildChocolateBtn = new Button("Build Chocolate");

        Button cartBtn = new Button("My Cart");
        Button returnBtn = new Button("Return");

        signUpBtn.setPrefSize(180, 50);
        signInBtn.setPrefSize(180, 50);
        searchBtn.setPrefSize(180, 50);
        sortBtn.setPrefSize(180, 50);
        filterBtn.setPrefSize(180, 50);
        buildChocolateBtn.setPrefSize(180, 50);
        cartBtn.setPrefSize(180, 50);
        returnBtn.setPrefSize(180, 50);

        HBox accountRow = new HBox(20, signUpBtn, signInBtn);
        HBox searchRow = new HBox(20, searchBtn, sortBtn);
        HBox chocolateRow = new HBox(20, filterBtn, buildChocolateBtn);
        HBox cartRow = new HBox(20, cartBtn, returnBtn);

        accountRow.setAlignment(Pos.CENTER);
        searchRow.setAlignment(Pos.CENTER);
        chocolateRow.setAlignment(Pos.CENTER);
        cartRow.setAlignment(Pos.CENTER);

        view.getChildren().clear();

        view.getChildren().addAll(title, accountRow, searchRow, chocolateRow, cartRow);
    }

    private void showStaffMenu() {

        Label title = new Label("Staff");

    }

}
