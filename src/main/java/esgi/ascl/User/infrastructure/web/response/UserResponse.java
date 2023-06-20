package esgi.ascl.User.infrastructure.web.response;

import esgi.ascl.User.domain.entities.Role;

import java.util.Date;

public class UserResponse {
    public Long id;
    public String email;
    public String phone;
    public String firstname;
    public String lastname;
    public String bio;
    public Date birthday;
    public Role role;


    public Long getId() {
        return id;
    }

    public UserResponse setId(Long id) {
        this.id = id;
        return this;
    }

    public String getEmail() {
        return email;
    }

    public UserResponse setEmail(String email) {
        this.email = email;
        return this;
    }

    public String getPhone() {
        return phone;
    }

    public UserResponse setPhone(String phone) {
        this.phone = phone;
        return this;
    }

    public String getFirstname() {
        return firstname;
    }

    public UserResponse setFirstname(String firstname) {
        this.firstname = firstname;
        return this;
    }

    public String getLastname() {
        return lastname;
    }

    public UserResponse setLastname(String lastname) {
        this.lastname = lastname;
        return this;
    }

    public String getBio() {
        return bio;
    }

    public UserResponse setBio(String bio) {
        this.bio = bio;
        return this;
    }

    public Date getBirthday() {
        return birthday;
    }

    public UserResponse setBirthday(Date birthday) {
        this.birthday = birthday;
        return this;
    }

    public Role getRole() {
        return role;
    }

    public UserResponse setRole(Role role) {
        this.role = role;
        return this;
    }
}
