package nathja.finalproject.baitapview.multipleViewTypeRecylerView;

import java.io.Serializable;

public class UserModel implements Serializable {
    private String name;
    private String address;

    public String getName() {
        return name;
    }

    public UserModel(String name, String address) {
        this.name = name;
        this.address = address;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}