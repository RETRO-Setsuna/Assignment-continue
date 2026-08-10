class StaffManager {
    private Inventory inventory;
    private Shopping shopping;

    StaffManager(Inventory inventory, Shopping shopping) {
        this.inventory = inventory;
        this.shopping = shopping;

    }

    void staffAccess() {
        System.out.print("Enter staff PIN: ");
        int pin = In.nextInt();

        if (pin == 1234) {
            staffInventoryMenu();
        } else {
            System.out.println("Incorrect staff PIN.");
        }
    }

    void staffInventoryMenu() {
        boolean running = true;
        while (running) {
            System.out.println();
            System.out.println("Staff Inventory Menu");
            System.out.println();
            System.out.println("1. Display Inventory");
            System.out.println("2. Add Chocolate");
            System.out.println("3. Remove Chocolate");
            System.out.println("4. Search Chocolate");
            System.out.println("5. Change Customer order status");
            System.out.println("6. Return");
            System.out.println();
            System.out.print("Select an option: ");

            int staffSelect = In.nextInt();

            while (staffSelect < 1 || staffSelect > 6) {
                System.out.println("Invalid option. Please select from 1 to 6.");
                System.out.print("Select an option: ");

                staffSelect = In.nextInt();
            }
            if (staffSelect == 1) {
                inventory.displayChocolate();

            } else if (staffSelect == 2) {
                addChocolate();

            } else if (staffSelect == 3) {
                removeChocolate();

            } else if (staffSelect == 4) {
                searchChocolate();

            } else if (staffSelect == 5) {
                System.out.println();
                System.out.println("Update Order Status");
                System.out.println();

                System.out.println();
                System.out.println("1. Confirmed");
                System.out.println("2. Preparing");
                System.out.println("3. Ready for Pickup");
                System.out.println("4. Out for Delivery");
                System.out.println("5. Complete");
                System.out.println("6. Return");
                System.out.println();
                System.out.print("Select an option: ");

                int choice = In.nextInt();

                while (choice < 1 || choice > 6) {
                    System.out.println("Invalid option. Please select from 1 to 6.");
                    System.out.print("Select an option: ");
                    choice = In.nextInt();
                }
                if (choice == 1) {
                    shopping.staffUpdateStatus(OrderStatus.CONFIRMED);
                } else if (choice == 2) {
                    shopping.staffUpdateStatus(OrderStatus.PREPARING);
                } else if (choice == 3) {
                    shopping.staffUpdateStatus(OrderStatus.READY_FOR_PICKUP);
                } else if (choice == 4) {
                    shopping.staffUpdateStatus(OrderStatus.OUT_FOR_DELIVERY);
                } else if (choice == 5) {
                    shopping.staffUpdateStatus(OrderStatus.COMPLETE);
                } else if (choice == 6) {
                    continue;
                }

            } else if (staffSelect == 6) {
                running = false;

            }
        }

    }

    void searchChocolate() {
        System.out.println("Search Chocolate");
        System.out.println();

        System.out.print("Enter Product ID to search: ");
        String productId = In.nextLine();

        Chocolate foundChocolate = inventory.searchChocolateById(productId);

        if (foundChocolate != null) {
            System.out.println("Chocolate found:");
            System.out.println(foundChocolate);
        } else {
            System.out.println("Chocolate not found.");
        }
    }

    void addChocolate() {
        System.out.println();
        System.out.println("Add Chocolate");
        System.out.println();

        String id;

        while (true) {
            System.out.print("Enter Product ID: ");
            id = In.nextLine();

            if (id.isEmpty()) {
                System.out.println("Product ID cannot be empty.");
            } else if (inventory.productIdExists(id)) {
                System.out.println("Product ID already exists.");
            } else {
                break;
            }
        }

        String name;

        while (true) {
            System.out.print("Enter chocolate name: ");
            name = In.nextLine();

            if (name.isEmpty()) {
                System.out.println("Chocolate name cannot be empty.");
            } else if (inventory.chocolateNameExists(name)) {
                System.out.println("Chocolate name already exists.");
            } else {
                break;
            }
        }

        double price = 0;

        while (price <= 0) {
            System.out.print("Enter price: ");
            price = In.nextDouble();

            if (price <= 0) {
                System.out.println("Price must be greater than 0.");
            }
        }

        Types type = ChocolateSelector.chooseType();
        Size size = ChocolateSelector.chooseSize();
        Sweetness sweetness = ChocolateSelector.chooseSweetness();
        Fillings filling = ChocolateSelector.chooseFilling();
        Toppings topping = ChocolateSelector.chooseTopping();

        inventory.addChocolate(
                id, name, price, size, sweetness, type, filling, topping);

        System.out.println();
        System.out.println("Chocolate added successfully.");
    }

    void removeChocolate() {
        System.out.println("Remove Chocolate");
        System.out.println();

        inventory.displayChocolate();

        System.out.print("Enter Product ID to remove: ");
        String productId = In.nextLine();

        inventory.removeChocolate(productId);
    }

    @Override
    public String toString() {
        return "Staff Manager";
    }
}
