import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class AppModel {

    private Inventory inventory;
    private Shopping shopping;
    private boolean memberSignedIn;
    private ListOfUsers users;

    private ObservableList<Chocolate> chocolates;

    public AppModel() {
        this.inventory = new Inventory();
        this.shopping = new Shopping();
        this.memberSignedIn = false;

        this.users = new ListOfUsers(
                new HashMap<Usernames, Passwords>(),
                new ArrayList<Users>());

        this.chocolates = FXCollections.observableArrayList();
    }

    public Inventory getInventory() {
        return inventory;
    }

    public Shopping getShopping() {
        return shopping;
    }

    public ObservableList<Chocolate> chocolatesProperty() {
        return chocolates;
    }

    public void addChocolate(Chocolate chocolate) {
        inventory.addChocolate(chocolate);
        chocolates.add(chocolate);
    }

    public boolean isMemberSignedIn() {
        return memberSignedIn;
    }

    public boolean signIn(String username, String password) {
        if (users.signIn(username, password)) {
            memberSignedIn = true;
            return true;
        }

        return false;
    }

    public void setMemberSignedIn(boolean memberSignedIn) {
        this.memberSignedIn = memberSignedIn;
    }

    public boolean signUp(String username, String password) {
        return users.signUp(username, password);
    }

    public Chocolate searchChocolate(String name) {
        return inventory.searchChocolateByName(name);
    }

    public List<Chocolate> filterByType(Types type) {
        return inventory.filterByType(type);
    }

    public List<Chocolate> filterBySize(Size size) {
        return inventory.filterBySize(size);
    }

    public List<Chocolate> filterBySweetness(Sweetness sweetness) {
        return inventory.filterBySweetness(sweetness);
    }
}