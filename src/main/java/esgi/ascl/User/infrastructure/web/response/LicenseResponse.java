package esgi.ascl.User.infrastructure.web.response;

import esgi.ascl.User.domain.entities.User;

import java.util.Date;

public class LicenseResponse {
    private Long id;
    private UserResponse userResponse;
    private Long number;
    private Date expirationDate;
    private Date creationDate;

    public Long getId() {
        return id;
    }

    public LicenseResponse setId(Long id) {
        this.id = id;
        return this;
    }

    public UserResponse getUser() {
        return userResponse;
    }

    public LicenseResponse setUser(UserResponse userResponse) {
        this.userResponse = userResponse;
        return this;
    }

    public Long getNumber() {
        return number;
    }

    public LicenseResponse setNumber(Long number) {
        this.number = number;
        return this;
    }

    public Date getExpirationDate() {
        return expirationDate;
    }

    public LicenseResponse setExpirationDate(Date expirationDate) {
        this.expirationDate = expirationDate;
        return this;
    }

    public Date getCreationDate() {
        return creationDate;
    }

    public LicenseResponse setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
        return this;
    }

}
