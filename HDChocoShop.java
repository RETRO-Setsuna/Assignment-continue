import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

class HDChocoShop {
    private Inventory inventory;
    private Shopping shopping;
    private StaffManager staffManager;
    private boolean memberSignedIn;
    private ListOfUsers users;

    HDChocoShop() {
        inventory = new Inventory();
        shopping = new Shopping();

        memberSignedIn = false;

        users = new ListOfUsers(new HashMap<Usernames, Passwords>(), new ArrayList<>());

        staffManager = new StaffManager(inventory, shopping);
    }

    void staffAccess() {
        staffManager.staffAccess();
    }

    Inventory getInventory() {
        return inventory;
    }

    void membershipSignUp() {

        System.out.println();
        System.out.println("Membership Sign Up");
        System.out.println();

        System.out.print("Enter your name: ");
        String name = In.nextLine();

        if (users.userExists(name)) {
            System.out.println("This username already exists.");
            return;
        }

        System.out.print("Enter your password: ");
        String password = In.nextLine();

        users.signUp(name, password);

        System.out.println("Membership sign up successful.");
    }

    void membershipSignIn() {
        System.out.println();
        System.out.println("Membership Sign In");
        System.out.println();

        System.out.print("Enter your name: ");
        String name = In.nextLine();

        System.out.print("Enter your password: ");
        String password = In.nextLine();

        if (users.signIn(name, password)) {
            memberSignedIn = true;
            System.out.println("Welcome " + name + "!");
            System.out.println("Members can receive 10% discount!");
        } else {
            System.out.println("Wrong password or Username, Please try again");
        }
    }

    void searchChocolate() {
        System.out.println();
        System.out.println("Search Chocolate");
        System.out.println();

        System.out.print("Enter chocolate name to search: ");
        System.out.println("Search is case-sensitive.");
        System.out.println("Please enter the exact name (ex) Dark Chocolate).");

        String name = In.nextLine();

        Chocolate foundChocolate = inventory.searchChocolateByName(name);

        if (foundChocolate != null) {
            System.out.println("Chocolate found:");
            System.out.println(foundChocolate);

            System.out.println();
            System.out.println("Would you like to add this chocolate to your cart?");
            System.out.println("1. Yes");
            System.out.println("2. No");
            System.out.println();
            System.out.print("Select an option: ");

            int choice = In.nextInt();

            if (choice == 1) {
                shopping.addToCart(foundChocolate);
            } else if (choice == 2) {
                System.out.println("Chocolate was not added to the cart.");
            } else {
                System.out.println("Invalid option.");
            }
        }
    }

    void buildChocolate() {
        System.out.println();
        System.out.println("Build Your Chocolate");
        System.out.println();

        System.out.print("Enter chocolate name: ");
        System.out.println();
        String name = In.nextLine();

        Types type = ChocolateSelector.chooseType();
        Size size = ChocolateSelector.chooseSize();
        Sweetness sweetness = ChocolateSelector.chooseSweetness();
        Fillings filling = ChocolateSelector.chooseFilling();
        Toppings topping = ChocolateSelector.chooseTopping();

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

        String productName = "CUSTOM-" + name;

        Chocolate customChocolate = new CustomChocolate(productName, name, price, size, sweetness, type, filling,
                topping);

        System.out.println();
        System.out.println("Your chocolate has been created.");
        customChocolate.displayChocolate();
        System.out.println("Price: $" + customChocolate.getPrice());

        shopping.addToCart(customChocolate);
    }

    void sortChocolate() {
        SortingByAlphabet alphabetSorter = new SortingByAlphabet();
        SortingByPrice price = new SortingByPrice();

        System.out.println();
        System.out.println("Sort Chocolate");
        System.out.println();
        System.out.println("1. Alphabetical");
        System.out.println("2. Price");
        System.out.println("3. Return");
        System.out.println();
        System.out.print("Select an option: ");

        int choice = In.nextInt();

        if (choice == 1) {
            alphabetSorter.sortChocolate(inventory.getChocolates());
        } else if (choice == 2) {
            price.sortChocolate(inventory.getChocolates());
        } else if (choice == 3) {
            return;
        } else {
            System.out.println("Invalid option.");
        }
    }

    void viewCart() {
        shopping.displayCart();
    }

    void viewOrderStatus() {
        shopping.viewOrderStatus();
    }

    void checkout() {

        shopping.checkout(memberSignedIn);
    }

    void deleteFromCart() {
        shopping.deleteFromCart();
    }

    void cartMenu() {
        boolean running = true;

        while (running) {
            System.out.println();
            System.out.println("My Cart Menu");
            System.out.println();
            System.out.println("1. View Cart");
            System.out.println("2. Delete From Cart");
            System.out.println("3. Checkout");
            System.out.println("4. View Order Status");
            System.out.println("5. Return");
            System.out.println();
            System.out.print("Select an option: ");

            int choice = In.nextInt();

            if (choice == 1) {
                viewCart();
            } else if (choice == 2) {
                deleteFromCart();
            } else if (choice == 3) {
                checkout();
            } else if (choice == 4) {
                viewOrderStatus();
            } else if (choice == 5) {
                running = false;
            } else {
                System.out.println("Invalid option.");
            }
        }
    }

    void filterChocolate() {
        System.out.println();
        System.out.println("1. Filter by Type");
        System.out.println("2. Filter by Size");
        System.out.println("3. Filter by Sweetness");
        System.out.println("4. Return");
        System.out.println();
        System.out.print("Select an option: ");

        int choice = In.nextInt();

        if (choice == 1) {
            filterChocolateByType();
        } else if (choice == 2) {
            filterChocolateBySize();
        } else if (choice == 3) {
            filterChocolateBySweetness();
        } else if (choice == 4) {
            return;
        } else {
            System.out.println("Invalid option.");
        }
    }

    void displayFilteredChocolate(List<Chocolate> filteredChocolate) {
        System.out.println();

        if (filteredChocolate.isEmpty()) {
            System.out.println("No chocolates found.");
            return;
        }

        for (Chocolate chocolate : filteredChocolate) {
            System.out.println(chocolate);
            System.out.println();
        }
    }

    void filterChocolateByType() {
        System.out.println();
        System.out.println("Filter by Type");
        System.out.println();
        System.out.println("1. White Chocolate");
        System.out.println("2. Dark Chocolate");
        System.out.println("3. Milk Chocolate");
        System.out.println("4. Cookie and Cream");
        System.out.println("5. Return");
        System.out.println();
        System.out.print("Select an option: ");

        int choice = In.nextInt();
        Types type;

        if (choice == 1) {
            type = Types.WHITE_CHOCOLATE;
        } else if (choice == 2) {
            type = Types.DARK_CHOCOLATE;
        } else if (choice == 3) {
            type = Types.MILK_CHOCOLATE;
        } else if (choice == 4) {
            type = Types.COOKIE_AND_CREAM;
        } else if (choice == 5) {
            return;
        } else {
            System.out.println("Invalid option.");
            return;
        }

        displayFilteredChocolate(inventory.filterByType(type));
    }

    void filterChocolateBySize() {
        System.out.println();
        System.out.println("Filter by Size");
        System.out.println();
        System.out.println("1. Small");
        System.out.println("2. Medium");
        System.out.println("3. Large");
        System.out.println("4. Extra Large");
        System.out.println("5. Return");
        System.out.println();
        System.out.print("Select an option: ");

        int choice = In.nextInt();
        Size size;

        if (choice == 1) {
            size = Size.S;
        } else if (choice == 2) {
            size = Size.M;
        } else if (choice == 3) {
            size = Size.L;
        } else if (choice == 4) {
            size = Size.XL;
        } else if (choice == 5) {
            return;
        } else {
            System.out.println("Invalid option.");
            return;
        }

        displayFilteredChocolate(inventory.filterBySize(size));
    }

    void filterChocolateBySweetness() {
        System.out.println();
        System.out.println("Filter by Sweetness");
        System.out.println();
        System.out.println("1. 0%");
        System.out.println("2. 25%");
        System.out.println("3. 50%");
        System.out.println("4. 75%");
        System.out.println("5. 100%");
        System.out.println("6. Return");
        System.out.println();
        System.out.print("Select an option: ");

        int choice = In.nextInt();
        Sweetness sweetness;

        if (choice == 1) {
            sweetness = Sweetness.ZERO;
        } else if (choice == 2) {
            sweetness = Sweetness.TWENTY_FIVE;
        } else if (choice == 3) {
            sweetness = Sweetness.FIFTY;
        } else if (choice == 4) {
            sweetness = Sweetness.SEVENTY_FIVE;
        } else if (choice == 5) {
            sweetness = Sweetness.HUNDRED;
        } else if (choice == 6) {
            return;
        } else {
            System.out.println("Invalid option.");
            return;
        }

        displayFilteredChocolate(inventory.filterBySweetness(sweetness));
    }

    @Override
    public String toString() {
        return "HD Choco Shop";
    }
}