package esgi.ascl.User.Entitie;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.Getter;

import java.util.Date;

@Getter
@Entity
@Table(name = "users")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", updatable = false)
    private int id;

    @Column(name = "email", unique = true)
    private String email;

    @Column(name = "phone", unique = true)
    private int phone;

    @Column(name = "firstname")
    private String firstname;

    @Column(name = "lastname")
    private String lastname;

    @Column(name = "password")
    private String password;//TODO: class Password ?

    @Column(name = "license")
    private String license;

    @Column(name = "birthday")
    private Date birthday;

    public User setBirthday(Date birthday) {
        this.birthday = birthday;
        return this;
    }

    public User setPhone(int phone) {
        this.phone = phone;
        return this;
    }

    public User setLicense(String license) {
        this.license = license;
        return this;
    }

    public User setId(int id) {
        this.id = id;
        return this;
    }

    public User setEmail(String email) {
        this.email = email;
        return this;
    }

    public User setFirstname(String firstname) {
        this.firstname = firstname;
        return this;
    }

    public User setLastname(String lastname) {
        this.lastname = lastname;
        return this;
    }

    public User setPassword(String password) {
        this.password = password;
        return this;
    }
}
