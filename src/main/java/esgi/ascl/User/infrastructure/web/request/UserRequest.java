package esgi.ascl.User.infrastructure.web.request;

import java.util.Date;

public class UserRequest {
    private Long id;
    private String email;
    private String phone;
    private String firstname;
    private String lastname;
    private String bio;
    private Date birthday;
    private String profilePicture;


    public Long getId() {
        return id;
    }

    public UserRequest setId(Long id) {
        this.id = id;
        return this;
    }

    public String getEmail() {
        return email;
    }

    public UserRequest setEmail(String email) {
        this.email = email;
        return this;
    }

    public String getPhone() {
        return phone;
    }

    public UserRequest setPhone(String phone) {
        this.phone = phone;
        return this;
    }

    public String getFirstname() {
        return firstname;
    }

    public UserRequest setFirstname(String firstname) {
        this.firstname = firstname;
        return this;
    }

    public String getLastname() {
        return lastname;
    }

    public UserRequest setLastname(String lastname) {
        this.lastname = lastname;
        return this;
    }

    public String getBio() {
        return bio;
    }

    public UserRequest setBio(String bio) {
        this.bio = bio;
        return this;
    }

    public Date getBirthday() {
        return birthday;
    }

    public UserRequest setBirthday(Date birthday) {
        this.birthday = birthday;
        return this;
    }

    public String getProfilePicture() {
        return profilePicture;
    }

    public UserRequest setProfilePicture(String profilePicture) {
        this.profilePicture = profilePicture;
        return this;
    }

    @Override
    public String toString() {
        return "UserRequest{" +
                "id=" + id +
                ", email='" + email + '\'' +
                ", phone='" + phone + '\'' +
                ", firstname='" + firstname + '\'' +
                ", lastname='" + lastname + '\'' +
                ", birthday=" + birthday +
                ", profilePicture='" + profilePicture + '\'' +
                '}';
    }

}
