import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

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

    public Chocolate searchChocolate(String name) {
        return inventory.searchChocolateByName(name);
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

    public void buildChocolate(String name, Types type, Size size, Sweetness sweetness,
            Fillings filling, Toppings topping) {

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

        Chocolate chocolate = new CustomChocolate(
                productId, name, price, size, sweetness, type, filling, topping);

        shopping.addToCart(chocolate);
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

    public void removeChoc(int index) {
        this.chocolates.remove(index);
    }

    public void removeAll() {
        for (int i = chocolates.size() - 1; i >= 0; i--) {
            this.chocolates.remove(i);
        }
    }

    public void clearOrder() {
        shopping.clearOrder();
    }

    public void updateOrderStatus(OrderStatus status) {
        shopping.staffUpdateStatus(status);
    }

}