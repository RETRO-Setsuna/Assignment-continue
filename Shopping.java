import java.util.ArrayList;
import java.util.List;

class Shopping {
    private List<Chocolate> cart;
    private OrderStatus orderStatus;
    private List<Chocolate> currentOrder;

    Shopping() {
        cart = new ArrayList<Chocolate>();
        orderStatus = OrderStatus.NO_ORDER;
        currentOrder = new ArrayList<Chocolate>();
    }

    void addToCart(Chocolate chocolate) {
        cart.add(chocolate);
        System.out.println(chocolate.getName() + " added to cart.");
    }

    void deleteFromCart(Chocolate chocolate) {
        cart.remove(chocolate);
        System.out.println(chocolate.getName() + " has been removed from your cart");
    }

    void displayCart() {
        System.out.println();
        System.out.println("My Cart");
        System.out.println();

        if (cart.isEmpty()) {
            System.out.println("Your cart is empty.");
        } else {
            for (Chocolate chocolate : cart) {
                System.out.println(chocolate);
            }

            System.out.println("Total: $" + calculateTotal());
        }
    }

    void checkout(boolean memberSignedIn, PaymentMethod paymentMethod, Delivery delivery) {

        if (cart.isEmpty()) {
            return;
        }

        double originalTotal = calculateTotal();
        double total = originalTotal;
        double discount = 0;

        if (memberSignedIn) {
            total = 0;

            for (Chocolate chocolate : cart) {
                total = total + chocolate.calculateDiscountPrice();
            }

            discount = originalTotal - total;
        }

        orderStatus = OrderStatus.PENDING;

        currentOrder.clear();

        for (Chocolate chocolate : cart) {
            currentOrder.add(chocolate);
        }

        cart.clear();
    }

    void viewOrderStatus() {
        if (orderStatus == OrderStatus.NO_ORDER) {
            System.out.println("No order has been placed.");
            return;
        }

        System.out.println();
        System.out.println("Order Details");
        System.out.println();

        for (Chocolate chocolate : currentOrder) {
            System.out.println(chocolate);
            chocolate.displayChocolate();
            System.out.println();
        }

        System.out.println("Current Order Status: " + orderStatus);
    }

    double calculateTotal() {
        double total = 0;

        for (Chocolate chocolate : cart) {
            total = total + chocolate.getPrice();
        }

        return total;
    }

    public void staffUpdateStatus(OrderStatus newStatus) {
        if (orderStatus == OrderStatus.NO_ORDER) {
            System.out.println("No order has been placed.");
            return;
        }

        orderStatus = newStatus;
        System.out.println("Order status updated to: " + orderStatus);
    }

    public List<Chocolate> getCart() {
        return cart;
    }

    public List<Chocolate> getCurrentOrder() {
        return currentOrder;
    }

    public OrderStatus getOrderStatus() {
        return orderStatus;
    }

    @Override
    public String toString() {
        return "Cart items: " + cart.size() + ", Order status: " + orderStatus;
    }

}
