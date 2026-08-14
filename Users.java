
import java.util.List;
import java.util.Map;

interface Requirement {
    boolean satisfaction(String value);
}

class LogInCredentials {
    protected String userTypes;

    LogInCredentials(String userTypes) {
        this.userTypes = userTypes;
    }

    public String getUserTypes() {
        return this.userTypes;
    }

    public void setUserTypes(String userTypes) {
        this.userTypes = userTypes;
    }

    public String toString() {
        return userTypes;
    }

}

class Passwords extends LogInCredentials implements Requirement {
    static final int minimumLength = 8;

    Passwords(String userTypes) {
        super(userTypes);
    }

    @Override
    public boolean satisfaction(String value) {
        if (value.length() >= minimumLength) {
            return true;
        } else {
            return false;
        }
    }

    public boolean passwordChecker() {
        if (!satisfaction(userTypes)) {
            System.out.println("Password must be at least 8 characters");
            return false;
        } else {
            return true;
        }
    }

    @Override
    public String toString() {
        return super.toString();
    }

}

class Usernames extends LogInCredentials {

    Usernames(String userTypes) {
        super(userTypes);
    }

    @Override
    public String toString() {
        return super.toString();
    }
}

class Users {
    private Usernames username;
    private Passwords password;

    Users(Usernames username, Passwords password) {
        this.username = username;
        this.password = password;
    }

    public Usernames getName() {
        return username;
    }

    public Passwords getPass() {
        return password;
    }

    public String toString() {
        return "Name: " + username + "    Password: " + password;
    }
}

class ListOfUsers {
    private Map<Usernames, Passwords> users;
    private List<Users> listUsers;

    ListOfUsers(Map<Usernames, Passwords> users, List<Users> listOfUsers) {
        this.users = users;
        this.listUsers = listOfUsers;
    }

    public void signUp(String userNameInput, String passwordInput) {
        Usernames username = new Usernames(userNameInput);
        Passwords password = new Passwords(passwordInput);

        boolean passwordInvalid = true;

        while (passwordInvalid == true) {
            try {
                if (password.passwordChecker()) {
                    break;
                } else {
                    System.out.print("Please try again: ");
                    password.setUserTypes(In.nextLine());
                }
            } catch (Exception e) {
                System.out.println("Error: " + e.getMessage() + "Make sure password is 8 character long");
                passwordInvalid = false;
            }
        }
        users.put(username, password);
        listUsers.add(new Users(username, password));
        System.out.println("Your account has been created");

    }

    public boolean signIn(String accountName, String accountPassword) {

        for (Users u : listUsers) {
            if (accountName.equals(u.getName().getUserTypes()) && accountPassword.equals(u.getPass().getUserTypes())) {

                return true;
            }
        }

        return false;
    }

    public boolean userExists(String username) {
        for (Users user : listUsers) {
            if (username.equals(user.getName().getUserTypes())) {
                return true;
            }
        }
        return false;
    }

    @Override
    public String toString() {
        return "Number of registered users: " + listUsers.size();
    }
}