class ChocolateSelector {

    static Types chooseType() {

        Types type = null;

        while (type == null) {
            System.out.println();
            System.out.println("Choose Chocolate Type");
            System.out.println();
            System.out.println("1. White Chocolate");
            System.out.println("2. Dark Chocolate");
            System.out.println("3. Milk Chocolate");
            System.out.println("4. Cookie and Cream");
            System.out.println();
            System.out.print("Select an option: ");

            int choice = In.nextInt();

            if (choice == 1) {
                type = Types.WHITE_CHOCOLATE;
            } else if (choice == 2) {
                type = Types.DARK_CHOCOLATE;
            } else if (choice == 3) {
                type = Types.MILK_CHOCOLATE;
            } else if (choice == 4) {
                type = Types.COOKIE_AND_CREAM;
            } else {
                System.out.println("Invalid option. Please choose again.");
            }
        }

        return type;
    }

    static Size chooseSize() {

        Size size = null;

        while (size == null) {
            System.out.println();
            System.out.println("Choose Size");
            System.out.println();
            System.out.println("1. Small");
            System.out.println("2. Medium");
            System.out.println("3. Large");
            System.out.println("4. Extra Large");
            System.out.println();
            System.out.print("Select an option: ");

            int sizeChoice = In.nextInt();

            if (sizeChoice == 1) {
                size = Size.S;
            } else if (sizeChoice == 2) {
                size = Size.M;
            } else if (sizeChoice == 3) {
                size = Size.L;
            } else if (sizeChoice == 4) {
                size = Size.XL;
            } else {
                System.out.println("Invalid option. Please choose again.");
            }
        }

        return size;
    }

    static Sweetness chooseSweetness() {

        Sweetness sweetness = null;

        while (sweetness == null) {
            System.out.println();
            System.out.println("Choose Sweetness");
            System.out.println();
            System.out.println("1. 0%");
            System.out.println("2. 25%");
            System.out.println("3. 50%");
            System.out.println("4. 75%");
            System.out.println("5. 100%");
            System.out.println();
            System.out.print("Select an option: ");

            int sweetnessChoice = In.nextInt();

            if (sweetnessChoice == 1) {
                sweetness = Sweetness.ZERO;
            } else if (sweetnessChoice == 2) {
                sweetness = Sweetness.TWENTY_FIVE;
            } else if (sweetnessChoice == 3) {
                sweetness = Sweetness.FIFTY;
            } else if (sweetnessChoice == 4) {
                sweetness = Sweetness.SEVENTY_FIVE;
            } else if (sweetnessChoice == 5) {
                sweetness = Sweetness.HUNDRED;
            } else {
                System.out.println("Invalid option. Please choose again.");
            }
        }

        return sweetness;
    }

    static Fillings chooseFilling() {

        Fillings filling = null;

        while (filling == null) {
            System.out.println();
            System.out.println("Choose Filling");
            System.out.println();
            System.out.println("1. None");
            System.out.println("2. Caramel");
            System.out.println("3. Nuts");
            System.out.println("4. Fruits");
            System.out.println();
            System.out.print("Select an option: ");

            int fillingChoice = In.nextInt();

            if (fillingChoice == 1) {
                filling = Fillings.NONE;
            } else if (fillingChoice == 2) {
                filling = Fillings.CARAMEL;
            } else if (fillingChoice == 3) {
                filling = Fillings.NUTS;
            } else if (fillingChoice == 4) {
                filling = Fillings.FRUITS;
            } else {
                System.out.println("Invalid option. Please choose again.");
            }
        }

        return filling;
    }

    static Toppings chooseTopping() {

        Toppings topping = null;

        while (topping == null) {
            System.out.println();
            System.out.println("Choose Topping");
            System.out.println();
            System.out.println("1. None");
            System.out.println("2. Fruits");
            System.out.println("3. Oreo");
            System.out.println("4. Candy Pop");
            System.out.println("5. Extra Chocolate");
            System.out.println();
            System.out.print("Select an option: ");

            int toppingsChoice = In.nextInt();

            if (toppingsChoice == 1) {
                topping = Toppings.NONE;
            } else if (toppingsChoice == 2) {
                topping = Toppings.FRUITS;
            } else if (toppingsChoice == 3) {
                topping = Toppings.OREO;
            } else if (toppingsChoice == 4) {
                topping = Toppings.CANDY_POP;
            } else if (toppingsChoice == 5) {
                topping = Toppings.EXTRA_CHOCOLATE;
            } else {
                System.out.println("Invalid option. Please choose again.");
            }
        }

        return topping;
    }

    @Override
    public String toString() {
        return "Chocolate Selector";
    }
}
