import java.util.ArrayList;
import java.util.HashMap;

public class AppModel {

    private Inventory inventory;
    private Shopping shopping;
    private boolean memberSignedIn;
    private ListOfUsers users;

    public AppModel() {
        this.inventory = new Inventory();
        this.shopping = new Shopping();
        this.memberSignedIn = false;
        this.users = new ListOfUsers(
                new HashMap<Usernames, Passwords>(),
                new ArrayList<Users>());
    }

    public Inventory getInventory() {
        return inventory;
    }

    public Shopping getShopping() {
        return shopping;
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
}