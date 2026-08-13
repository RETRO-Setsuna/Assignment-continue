import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class AppMain extends Application {

        @Override
        public void start(Stage primaryStage) {

                AppModel model = new AppModel();

                model.addChocolate(
                                new NormalChocolate("N001", "Milk Chocolate", 10.00, Size.M, Sweetness.FIFTY,
                                                Types.MILK_CHOCOLATE, Fillings.CARAMEL, Toppings.OREO));

                model.addChocolate(
                                new NormalChocolate("N002", "Dark Chocolate", 11.50, Size.S, Sweetness.TWENTY_FIVE,
                                                Types.DARK_CHOCOLATE, Fillings.NONE, Toppings.NONE));

                model.addChocolate(
                                new NormalChocolate("N003", "White Chocolate", 12.00, Size.L, Sweetness.HUNDRED,
                                                Types.WHITE_CHOCOLATE, Fillings.FRUITS, Toppings.CANDY_POP));

                model.addChocolate(
                                new NormalChocolate("N004", "Cookies & Cream", 13.50, Size.XL, Sweetness.SEVENTY_FIVE,
                                                Types.COOKIE_AND_CREAM, Fillings.NUTS, Toppings.EXTRA_CHOCOLATE));

                model.addChocolate(
                                new NormalChocolate("N005", "Caramel Milk", 11.00, Size.M, Sweetness.FIFTY,
                                                Types.MILK_CHOCOLATE, Fillings.CARAMEL, Toppings.NONE));

                model.addChocolate(
                                new NormalChocolate("N006", "Classic Dark", 9.50, Size.S, Sweetness.ZERO,
                                                Types.DARK_CHOCOLATE, Fillings.NONE, Toppings.NONE));

                model.addChocolate(
                                new CustomChocolate("C001", "Dark Nut Chocolate", 12.00, Size.L, Sweetness.TWENTY_FIVE,
                                                Types.DARK_CHOCOLATE, Fillings.NUTS, Toppings.EXTRA_CHOCOLATE));

                model.addChocolate(
                                new CustomChocolate("C002", "Sweet White Dream", 14.00, Size.XL, Sweetness.HUNDRED,
                                                Types.WHITE_CHOCOLATE, Fillings.CARAMEL, Toppings.OREO));

                model.addChocolate(
                                new CustomChocolate("C003", "Berry Milk", 11.50, Size.M, Sweetness.FIFTY,
                                                Types.MILK_CHOCOLATE, Fillings.FRUITS, Toppings.FRUITS));

                model.addChocolate(
                                new CustomChocolate("C004", "Dark Zero", 10.50, Size.S, Sweetness.ZERO,
                                                Types.DARK_CHOCOLATE, Fillings.NONE, Toppings.NONE));

                model.addChocolate(
                                new CustomChocolate("C005", "Cookies Party", 15.00, Size.XL, Sweetness.SEVENTY_FIVE,
                                                Types.COOKIE_AND_CREAM, Fillings.CARAMEL, Toppings.CANDY_POP));

                model.addChocolate(
                                new CustomChocolate("C006", "Nut Explosion", 13.00, Size.L, Sweetness.FIFTY,
                                                Types.MILK_CHOCOLATE, Fillings.NUTS, Toppings.EXTRA_CHOCOLATE));

                AppController controller = new AppController(model);
                AppView view = new AppView(controller, model, primaryStage);

                Scene scene = new Scene(view.asParent(), 600, 500);

                primaryStage.setTitle("HD Choco Shop");
                primaryStage.setScene(scene);
                primaryStage.show();
        }

       

        public static void main(String[] args) {
                launch(args);
        }
}