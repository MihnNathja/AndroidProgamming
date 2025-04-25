package nathja.finalproject.databinding;

import androidx.databinding.BaseObservable;
import androidx.databinding.Bindable;

import java.io.Serializable;

public class User implements Serializable {
    private String firstName;
    private String lastName;

    // Constructor mặc định
    public User() {
    }

    // Constructor với tham số
    public User(String firstName, String lastName) {
        this.firstName = firstName;
        this.lastName = lastName;
    }
    // Getter cho firstName
    public String getFirstName() {
        return firstName;
    }

    // Setter cho firstName, có thông báo thay đổi dữ liệu
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    // Getter cho lastName
    public String getLastName() {
        return lastName;
    }

    // Setter cho lastName, có thông báo thay đổi dữ liệu
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }
}
