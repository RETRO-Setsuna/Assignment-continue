import java.util.List;

public class AppController {
    private AppModel model;

    public AppController(AppModel model) {
        this.model = model;
    }

    public boolean signIn(String username, String password) {
        return model.signIn(username, password);
    }

    public boolean signUp(String username, String password) {
        return model.signUp(username, password);
    }

    public Chocolate searchChocolate(String name) {
        return model.searchChocolate(name);
    }

    public List<Chocolate> filterByType(Types type) {
        return model.filterByType(type);
    }

    public List<Chocolate> filterBySize(Size size) {
        return model.filterBySize(size);
    }

    public List<Chocolate> filterBySweetness(Sweetness sweetness) {
        return model.filterBySweetness(sweetness);
    }

    public void buildChocolate(String name, Types type, Size size, Sweetness sweetness,
            Fillings filling, Toppings topping) {
        model.buildChocolate(name, type, size, sweetness, filling, topping);
    }

    public List<Chocolate> getCart() {
        return model.getCart();
    }

    public double calculateTotal() {
        return model.calculateTotal();
    }

    public void removeFromCart(Chocolate chocolate) {
        model.removeFromCart(chocolate);
    }

    public void checkout(PaymentMethod paymentMethod, Delivery delivery) {
        model.checkout(paymentMethod, delivery);
    }

    public void addToCart(Chocolate chocolate) {
        model.addToCart(chocolate);
    }

    public List<Chocolate> getCurrentOrder() {
        return model.getCurrentOrder();
    }

    public OrderStatus getOrderStatus() {
        return model.getOrderStatus();
    }
}