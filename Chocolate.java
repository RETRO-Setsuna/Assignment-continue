import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;

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

    private final SimpleStringProperty chocName;
    private final SimpleStringProperty chocID;
    private final SimpleObjectProperty<Sweetness> chocSweet;
    private final SimpleObjectProperty<Types> chocType;
    private final SimpleObjectProperty<Toppings> chocTops;
    private final SimpleObjectProperty<Fillings> chocFill;
    private final SimpleObjectProperty<Size> chocSize;
    private final SimpleDoubleProperty chocPrice;

    Chocolate(String productId, String name, double price, Size size, Sweetness sweetness, Types type, Fillings filling,
            Toppings topping) {
        this.chocID = new SimpleStringProperty(productId);
        this.chocName = new SimpleStringProperty(name);
        this.chocPrice = new SimpleDoubleProperty(price);
        this.chocSize = new SimpleObjectProperty<>(size);
        this.chocSweet = new SimpleObjectProperty<>(sweetness);
        this.chocType = new SimpleObjectProperty<>(type);
        this.chocFill = new SimpleObjectProperty<>(filling);
        this.chocTops = new SimpleObjectProperty<>(topping);
    }

    public String getProductId() {
        return chocID.get();
    }

    public void setProductID(String newID) {
        this.chocID.set(newID);
    }

    public SimpleStringProperty productIDProperty() {
        return chocID;
    }

    public String getName() {
        return chocName.get();
    }

    public void setName(String newName) {
        this.chocName.set(newName);
    }

    public SimpleStringProperty nameProperty() {
        return chocName;
    }

    public Size getSize() {
        return chocSize.get();
    }

    public void setSize(Size newSize) {
        this.chocSize.set(newSize);
    }

    public SimpleObjectProperty<Size> sizeProperty() {
        return chocSize;
    }

    public Sweetness getSweetness() {
        return chocSweet.get();
    }

    public void setSweetness(Sweetness newSweet) {
        this.chocSweet.set(newSweet);
    }

    public SimpleObjectProperty<Sweetness> sweetProperty() {
        return chocSweet;
    }

    public Types getType() {
        return chocType.get();
    }

    public void setType(Types newType) {
        this.chocType.set(newType);
    }

    public SimpleObjectProperty<Types> typeProperty() {
        return chocType;
    }

    public double getPrice() {
        return chocPrice.get();
    }

    public void setPrice(double newPrice) {
        this.chocPrice.set(newPrice);
    }

    public SimpleDoubleProperty priceProperty() {
        return chocPrice;
    }

    public Toppings getToppings() {
        return chocTops.get();
    }

    public void setTopping(Toppings newTop) {
        this.chocTops.set(newTop);
    }

    public SimpleObjectProperty<Toppings> toppingProperty() {
        return chocTops;
    }

    public Fillings getFilling() {
        return chocFill.get();
    }

    public void newFIlling(Fillings newFIll) {
        this.chocFill.set(newFIll);
    }

    public SimpleObjectProperty<Fillings> fillProperty() {
        return chocFill;
    }

    void displayChocolate() {
        System.out.println("Type: " + chocType);
        System.out.println("Size: " + chocSize);
        System.out.println("Sweetness: " + chocSweet.getValue().getPercentage() + "%");
        System.out.println("Filling: " + chocFill);
        System.out.println("Topping: " + chocTops);
    }

    @Override
    public String toString() {
        return "Product Name : " + this.chocName + "\nProduct ID   : " + this.chocID + "\nPrice        : $"
                + this.chocPrice;
    }

    @Override
    public double calculateDiscountPrice() {
        return getPrice() * 0.9;
    }

}
