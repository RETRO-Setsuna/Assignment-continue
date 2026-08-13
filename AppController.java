public class AppController {
    private AppModel model;

    public AppController(AppModel model) {
        this.model = model;
    }

    public boolean signIn(String username, String password) {
        return model.signIn(username, password);
    }

    public boolean signUp(String username, String password) {
        return model.signUp(username, password);
    }

    public Chocolate searchChocolate(String name) {
        return model.searchChocolate(name);
    }
}