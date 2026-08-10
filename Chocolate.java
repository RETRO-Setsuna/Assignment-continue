enum Size {
    S, M, L, XL;
}

enum Delivery {
    PICK_UP, DELIVERY;
}

enum Fillings {
    NONE, CARAMEL, NUTS, FRUITS;
}

enum Sweetness {
    ZERO(0), TWENTY_FIVE(25), FIFTY(50), SEVENTY_FIVE(75), HUNDRED(100);

    private int percentage;

    Sweetness(int percentage) {
        this.percentage = percentage;
    }

    public int getPercentage() {
        return percentage;
    }
}

enum Toppings {
    NONE, FRUITS, OREO, CANDY_POP, EXTRA_CHOCOLATE;
}

enum Types {
    WHITE_CHOCOLATE, DARK_CHOCOLATE, MILK_CHOCOLATE, COOKIE_AND_CREAM;
}

enum PaymentMethod {
    CASH, CARD, TRANSFER;
}

enum OrderStatus {
    NO_ORDER, PENDING, CONFIRMED, PREPARING, READY_FOR_PICKUP, OUT_FOR_DELIVERY, COMPLETE;

}

class Chocolate implements Discountable {
    private String productId;
    private String name;
    private double price;
    private Size size;
    private Sweetness sweetness;
    private Types type;
    private Fillings filling;
    private Toppings topping;

    Chocolate(String productId, String name, double price, Size size, Sweetness sweetness, Types type, Fillings filling,
            Toppings topping) {
        this.productId = productId;
        this.name = name;
        this.price = price;
        this.size = size;
        this.sweetness = sweetness;
        this.type = type;
        this.filling = filling;
        this.topping = topping;
    }

    public String getProductId() {
        return productId;
    }

    public String getName() {
        return name;
    }

    public Size getSize() {
        return size;
    }

    public Sweetness getSweetness() {
        return sweetness;
    }

    public Types getType() {
        return type;
    }

    public double getPrice() {
        return price;
    }

    public Toppings getToppings() {
        return topping;
    }

    public Fillings getFilling() {
        return filling;
    }

    void displayChocolate() {
        System.out.println("Type: " + type);
        System.out.println("Size: " + size);
        System.out.println("Sweetness: " + sweetness.getPercentage() + "%");
        System.out.println("Filling: " + filling);
        System.out.println("Topping: " + topping);
    }

    @Override
    public String toString() {
        return "Product Name : " + this.name + "\nProduct ID   : " + this.productId + "\nPrice        : $" + this.price;
    }

    @Override
    public double calculateDiscountPrice() {
        return getPrice() * 0.9;
    }
}
