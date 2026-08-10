import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.HashMap;

class Inventory {

    private List<Chocolate> chocolates;
    private Map<String, Chocolate> chocolateMap;

    Inventory() {
        chocolates = new ArrayList<Chocolate>();
        chocolateMap = new HashMap<String, Chocolate>();
    }

    void addChocolate(Chocolate c) {
        chocolates.add(c);
        chocolateMap.put(c.getProductId(), c);
    }

    void addChocolate(String id, String name, double price, Size size, Sweetness sweetness, Types type,Fillings filling, Toppings topping) {

        Chocolate c = new NormalChocolate(id, name, price, size, sweetness, type,
                filling, topping);

        chocolates.add(c);
        chocolateMap.put(c.getProductId(), c);
    }

    boolean productIdExists(String productId) {
        return chocolateMap.containsKey(productId);
    }

    boolean chocolateNameExists(String name) {
        for (Chocolate chocolate : chocolates) {
            if (chocolate.getName().equals(name)) {
                return true;
            }
        }

        return false;
    }

    void displayChocolate() {

        if (chocolates.isEmpty()) {
            System.out.println("No chocolates in inventory.");
            return;
        }

        for (Chocolate choco : chocolates) {
            System.out.println(choco);
            System.out.println();
        }
    }

    Chocolate searchChocolateById(String productId) {
        return chocolateMap.get(productId);
    }

    Chocolate searchChocolateByName(String name) {

        for (Chocolate chocolate : chocolates) {

            if (chocolate.getName().equals(name)) {
                return chocolate;
            }
        }

        return null;
    }

    List<Chocolate> getChocolates() {
        return chocolates;
    }

    void removeChocolate(String productId) {

        Chocolate removedChocolate = chocolateMap.remove(productId);

        if (removedChocolate != null) {
            chocolates.remove(removedChocolate);

            System.out.println(removedChocolate.getName()
                    + " has been removed from the inventory.");
        } else {
            System.out.println("Chocolate not found.");
        }
    }

    List<Chocolate> filterByType(Types type) {
        List<Chocolate> filteredChocolate = new ArrayList<>();

        for (Chocolate chocolate : chocolates) {
            if (chocolate.getType() == type) {
                filteredChocolate.add(chocolate);
            }
        }

        return filteredChocolate;
    }

    List<Chocolate> filterBySize(Size size) {
        List<Chocolate> filteredChocolate = new ArrayList<>();

        for (Chocolate chocolate : chocolates) {
            if (chocolate.getSize() == size) {
                filteredChocolate.add(chocolate);
            }
        }

        return filteredChocolate;
    }

    List<Chocolate> filterBySweetness(Sweetness sweetness) {
        List<Chocolate> filteredChocolate = new ArrayList<>();

        for (Chocolate chocolate : chocolates) {
            if (chocolate.getSweetness() == sweetness) {
                filteredChocolate.add(chocolate);
            }
        }

        return filteredChocolate;
    }

    @Override
    public String toString() {
        return "Inventory contains " + chocolates.size() + " chocolates";
    }
}
