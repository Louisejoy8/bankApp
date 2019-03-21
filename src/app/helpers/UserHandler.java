package app.helpers;

import app.Entities.User;

public class UserHandler {
    private static UserHandler instance = new UserHandler();
    private User user;

    private UserHandler() {

    }

    public static UserHandler getInstance() {
        return instance;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
