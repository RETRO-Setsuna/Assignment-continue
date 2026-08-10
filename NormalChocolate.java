class NormalChocolate extends Chocolate {

    NormalChocolate(String id, String name, double price, Size size,
            Sweetness sweetness, Types type,
            Fillings filling, Toppings topping) {

        super(id, name, price, size, sweetness, type, filling, topping);
    }

    @Override
    public String toString() {
        return super.toString() + "\nChocolate Category: Normal Chocolate";
    }
}