import java.util.Collections;
import java.util.Comparator;
import java.util.List;

class SortingByAlphabet {

    private static final Comparator<Chocolate> comparator = Comparator.comparing(Chocolate::getName)
            .thenComparing(Chocolate::getPrice);

    public void sortChocolate(List<Chocolate> chocolates) {
        Collections.sort(chocolates, comparator);

        for (Chocolate c : chocolates) {
            System.out.println(c);
            System.out.println();
        }
    }

    @Override
    public String toString() {
        return "Sorting method: Alphabetical order";
    }
}

class SortingByPrice {

    private static final Comparator<Chocolate> comparator = Comparator.comparing(Chocolate::getPrice)
            .thenComparing(Chocolate::getType);

    public void sortChocolate(List<Chocolate> chocolates) {
        Collections.sort(chocolates, comparator);

        for (Chocolate c : chocolates) {
            System.out.println(c);
            System.out.println();
        }
    }

    @Override
    public String toString() {
        return "Sorting method: Price order";
    }
}