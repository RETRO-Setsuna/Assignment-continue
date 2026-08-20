import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

//1. retrives all class object needed from the different files and turns them into attributes for the AppModel to handle
public class AppModel {

    private Inventory inventory;
    private Shopping shopping;
    private boolean memberSignedIn;
    private ListOfUsers users;
    private final ObservableList<Chocolate> chocolates;

    public AppModel() {
        this.inventory = new Inventory();
        this.shopping = new Shopping();
        this.memberSignedIn = false;
        this.users = new ListOfUsers(
                new HashMap<Usernames, Passwords>(),
                new ArrayList<Users>());
        this.chocolates = FXCollections.observableArrayList();

    }

    // imports all needed methods that is used in the application to the app model
    public Inventory getInventory() {
        return inventory;
    }

    public Shopping getShopping() {
        return shopping;
    }

    public void addChocolate(Chocolate chocolate) {
        inventory.addChocolate(chocolate);
        chocolates.add(chocolate);
    }

    public boolean isMemberSignedIn() {
        return memberSignedIn;
    }

    // 5. it grabs the signin method from the class oobject oof sign in and check
    // its if it exist or not if it does exist than it would return true allowing
    // the user to move on, else it would return false
    public boolean signIn(String username, String password) {
        if (users.signIn(username, password)) {
            memberSignedIn = true;
            return true;
        }

        return false;
    }

    public void setMemberSignedIn(boolean memberSignedIn) {
        this.memberSignedIn = memberSignedIn;
    }

    public boolean signUp(String username, String password) {
        return users.signUp(username, password);
    }

    public List<Chocolate> filterByType(Types type) {
        return inventory.filterByType(type);
    }

    public List<Chocolate> filterBySize(Size size) {
        return inventory.filterBySize(size);
    }

    public List<Chocolate> filterBySweetness(Sweetness sweetness) {
        return inventory.filterBySweetness(sweetness);
    }

    // 10. buildChocolate utilises the chocolate building method in the chocolate
    // object class, with if statment it would modify the prices of the chocolate by
    // adding addition costs. Also can choose the quantity of it and after the
    // chocolate is created it would than be
    // aadded to the cart where the user can close the winder when done and visit
    // the cart
    public void buildChocolate(String name, Types type, Size size, Sweetness sweetness,
            Fillings filling, Toppings topping, int quantity) {

        double price = 10.00;

        if (size == Size.M) {
            price = price + 2.00;
        } else if (size == Size.L) {
            price = price + 4.00;
        } else if (size == Size.XL) {
            price = price + 6.00;
        }

        if (topping == Toppings.EXTRA_CHOCOLATE) {
            price = price + 2.00;
        }

        String productId = "CB-" + name;

        Chocolate chocolate = new Chocolate(
                productId, name, price, size, sweetness, type, filling, topping);

        for (int i = 0; i < quantity; i++) {
            shopping.addToCart(chocolate);
        }
    }

    public List<Chocolate> getCart() {
        return shopping.getCart();
    }

    public double calculateTotal() {
        return shopping.calculateTotal();
    }

    public void removeFromCart(Chocolate chocolate) {
        shopping.deleteFromCart(chocolate);
    }

    public void checkout(PaymentMethod paymentMethod, Delivery delivery) {
        shopping.checkout(memberSignedIn, paymentMethod, delivery);
    }

    public void addToCart(Chocolate chocolate) {
        shopping.addToCart(chocolate);
    }

    public List<Chocolate> getCurrentOrder() {
        return shopping.getCurrentOrder();
    }

    // returns the order status of the orders
    public OrderStatus getOrderStatus() {
        return shopping.getOrderStatus();
    }

    public double calculateFinalTotal() {
        double total = shopping.calculateTotal();

        if (memberSignedIn) {
            return total * 0.9;
        }

        return total;
    }

    public ObservableList<Chocolate> chocoProperties() {
        return this.chocolates;
    }

    public void updateChoco(Chocolate c, int index) {
        this.chocolates.set(index, c);
    }

    // removes a specific chocolate
    public void removeChoc(int index) {
        this.chocolates.remove(index);
    }

    // clears all chocolates in a list
    public void clearOrder() {
        shopping.clearOrder();
    }

    // gets access to change the status
    public void updateOrderStatus(OrderStatus status) {
        shopping.staffUpdateStatus(status);
    }

}