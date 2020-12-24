package vangthao.app.weatherapplication.model.users;

import java.io.Serializable;
import java.util.concurrent.atomic.AtomicInteger;

public class User implements Serializable {
    private static final AtomicInteger count = new AtomicInteger(0);
    private int id;
    private  String userName;
    private String email;

    public User() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public User(int id, String userName, String email) {
        this.id = count.incrementAndGet();
        this.userName = userName;
        this.email = email;
    }

    public String getUserName() {
        return userName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

}
