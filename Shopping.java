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

    void deleteFromCart() {
        if (cart.isEmpty()) {
            System.out.println("Your cart is empty.");
            return;
        }

        displayCart();

        System.out.println();
        System.out.print("Enter chocolate you want to delete name: ");
        String name = In.nextLine();

        for (int i = 0; i < cart.size(); i++) {
            if (cart.get(i).getName().equals(name)) {
                deleteFromCart(cart.get(i));
                return;
            }
        }

        System.out.println("Chocolate not found.");
    }

    void checkout(boolean memberSignedIn) {
        if (cart.isEmpty()) {
            System.out.println("Your cart is empty.");
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

        PaymentMethod paymentMethod = choosePaymentMethod();
        Delivery delivery = chooseDelivery();

        orderStatus = OrderStatus.PENDING;

        System.out.println();
        System.out.println("Checkout Summary");

        if (memberSignedIn) {
            System.out.println("Original Price: $ " + originalTotal);
            System.out.println("Member Discount (10%): $ " + discount);
            System.out.println("Total Price: $ " + total);

        } else {
            System.out.println("Membership Discount: Not Applied");
            System.out.println("Sign in as a member next time to receive a 10% discount.");
            System.out.println("Total Price: $ " + total);

        }

        System.out.println();
        System.out.println("Payment Method: " + paymentMethod);
        System.out.println("Delivery Option: " + delivery);

        System.out.println();
        System.out.println("Order placed successfully.");

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

    PaymentMethod choosePaymentMethod() {
        while (true) {

            System.out.println();
            System.out.println("Choose Payment Method");
            System.out.println();
            System.out.println("1. Cash");
            System.out.println("2. Card");
            System.out.println("3. Transfer");
            System.out.println();
            System.out.print("Select an option: ");

            int choice = In.nextInt();

            if (choice == 1) {
                return PaymentMethod.CASH;
            } else if (choice == 2) {
                return PaymentMethod.CARD;
            } else if (choice == 3) {
                return PaymentMethod.TRANSFER;
            } else {
                System.out.println("Invalid option. Please select again.");
            }
        }
    }

    Delivery chooseDelivery() {
        while (true) {

            System.out.println();
            System.out.println("Choose Delivery Option");
            System.out.println();
            System.out.println("1. Pick Up");
            System.out.println("2. Delivery");
            System.out.println();
            System.out.print("Select an option: ");

            int choice = In.nextInt();

            if (choice == 1) {
                return Delivery.PICK_UP;
            } else if (choice == 2) {
                return Delivery.DELIVERY;
            } else {
                System.out.println("Invalid option. Please choose again.");
            }
        }
    }

    void staffUpdateStatus(OrderStatus newStatus) {
        if (orderStatus == OrderStatus.NO_ORDER) {
            System.out.println("No order has been placed.");
            return;
        }

        orderStatus = newStatus;
        System.out.println("Order status updated to: " + orderStatus);
    }

    @Override
    public String toString() {
        return "Cart items: " + cart.size() + ", Order status: " + orderStatus;
    }
}
