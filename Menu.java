class Menu {
    private HDChocoShop shop;

    Menu(HDChocoShop shop) {
        this.shop = shop;
    }

    void runMenu() {

        boolean running = true;

        while (running) {

            System.out.println();
            System.out.println("Welcome to HD Choco Shop!!!!!!");
            System.out.println();
            System.out.println("1. Customer");
            System.out.println("2. Staff");
            System.out.println("3. Exit");
            System.out.println();
            System.out.print("Select an option: ");

            int choice = In.nextInt();
            while (choice < 1 || choice > 3) {
                System.out.println("Invalid option. Please select from 1 to 3.");
                System.out.print("Select an option: ");
                choice = In.nextInt();
            }
            if (choice == 1) {
                customerMenu();
            } else if (choice == 2) {
                shop.staffAccess();
            } else if (choice == 3) {
                System.out.println("Thank you for visiting HD Choco Shop!");
                running = false;
            }
        }
    }

    void customerMenu() {

        boolean running = true;

        while (running) {

            System.out.println();
            System.out.println();
            System.out.println("1. Membership Sign Up");
            System.out.println("2. Membership Sign In");
            System.out.println("3. Search Chocolate");
            System.out.println("4. Sort Chocolate");
            System.out.println("5. Filter Chocolate");
            System.out.println("6. Build Your Chocolate");
            System.out.println("7. My Cart");
            System.out.println("8. Return");
            System.out.println();
            System.out.print("Select an option: ");

            int choice = In.nextInt();

            while (choice < 1 || choice > 8) {
                System.out.println("Invalid option. Please select from 1 to 8.");
                System.out.print("Select an option: ");
                choice = In.nextInt();
            }

            if (choice == 1) {
                shop.membershipSignUp();
            } else if (choice == 2) {
                shop.membershipSignIn();
            } else if (choice == 3) {
                shop.searchChocolate();
            } else if (choice == 4) {
                shop.sortChocolate();
            } else if (choice == 5) {
                shop.filterChocolate();
            } else if (choice == 6) {
                shop.buildChocolate();
            } else if (choice == 7) {
                shop.cartMenu();
            } else if (choice == 8) {
                running = false;
            }
        }
    }

    @Override
    public String toString() {
        return "HD Choco Shop Menu";
    }
}
