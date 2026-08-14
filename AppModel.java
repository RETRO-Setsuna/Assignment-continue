public class AppModel {

    private Inventory inventory;
    private Shopping shopping;
    private boolean memberSignedIn;

    public AppModel() {
        this.inventory = new Inventory();
        this.shopping = new Shopping();
        this.memberSignedIn = false;
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

    public void setMemberSignedIn(boolean memberSignedIn) {
        this.memberSignedIn = memberSignedIn;
    }
}