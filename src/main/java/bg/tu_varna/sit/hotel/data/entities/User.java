package bg.tu_varna.sit.hotel.data.entities;

import bg.tu_varna.sit.hotel.business.UserService;
import bg.tu_varna.sit.hotel.data.access.Connection;
import bg.tu_varna.sit.hotel.data.repositories.implementations.HotelRepositoryImpl;
import bg.tu_varna.sit.hotel.data.repositories.implementations.UserRepositoryImpl;
import bg.tu_varna.sit.hotel.presentation.models.UserModel;
import org.hibernate.Session;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "users")
public class User implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "id", nullable = false, length = 10)
    private String id;//EGN

    @Column(name = "first_name", nullable = false)
    private String firstName;

    @Column(name = "last_name", nullable = false)
    private String lastName;

    @Column(name = "phone", nullable = false, unique = true, length = 10)
    private String phone;

    @Column(name = "username", nullable = false, unique = true)
    private String username;

    @Column(name = "email", nullable = false, unique = true)
    private String email;

    @Column(name = "password", nullable = false)
    private String password;

    @Column(name = "hash", nullable = false)
    private String hash;

    @Column(name = "role", nullable = false)
    private String role;

    @Column(name = "created_at", nullable = false)
    private Timestamp createdAt;

    @Column(name = "last_login", nullable = false)
    private Timestamp lastLogin;

    @Column(name = "status", nullable = false)
    private String status;


    @ManyToMany(cascade = {CascadeType.PERSIST})
    @JoinTable(name = "hotels_users",
            joinColumns = @JoinColumn(name = "user_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "hotel_id", referencedColumnName = "id"))
    List<Hotel> hotels = new ArrayList<>();//

    public void setHotels(List<Hotel> hotels) {this.hotels = hotels;}

    public List<Hotel> getHotels() {return hotels;}


    public String getHash() {
        return hash;
    }

    public void setHash(String hash) {
        this.hash = hash;
    }

    public Timestamp getLastLogin() {
        return lastLogin;
    }

    public void setLastLogin(Timestamp lastLogin) {
        this.lastLogin = lastLogin;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Timestamp getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Timestamp createdAt) {
        this.createdAt = createdAt;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "User{" +
                "id='" + id + '\'' +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", phone='" + phone + '\'' +
                ", username='" + username + '\'' +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", hash='" + hash + '\'' +
                ", role='" + role + '\'' +
                ", createdAt=" + createdAt +
                ", lastLogin=" + lastLogin +
                ", status='" + status + '\'' +
                //", hotels=" + hotels +
                '}';
    }


    public UserModel toModel() {
        UserModel userModel = new UserModel();
        userModel.setId(this.id);
        userModel.setFirstName(this.firstName);
        userModel.setLastName(this.lastName);
        userModel.setPhone(this.phone);
        userModel.setUsername(this.username);
        userModel.setEmail(this.email);
        userModel.setPassword(this.password);
        userModel.setHash(this.hash);
        userModel.setRole(this.role);
        userModel.setCreatedAt(this.createdAt);
        userModel.setLastLogin(this.lastLogin);
        userModel.setStatus(this.status);
        userModel.setHotels(this.hotels);
        return userModel;
    }
}