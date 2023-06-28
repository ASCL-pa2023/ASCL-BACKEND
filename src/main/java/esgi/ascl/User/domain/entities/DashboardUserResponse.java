package esgi.ascl.User.domain.entities;

import esgi.ascl.license.infrastructure.web.response.LicenseResponse;

public class DashboardUserResponse {
    public Long id;
    public String firstname;
    public String lastname;
    public Role role;
    public Integer nbFollowers;
    public Integer nbNews;
    public LicenseResponse license;


    public DashboardUserResponse setId(Long id) {
        this.id = id;
        return this;
    }

    public DashboardUserResponse setFirstname(String firstname) {
        this.firstname = firstname;
        return this;
    }

    public DashboardUserResponse setLastname(String lastname) {
        this.lastname = lastname;
        return this;
    }

    public DashboardUserResponse setRole(Role role) {
        this.role = role;
        return this;
    }

    public DashboardUserResponse setNbFollowers(Integer nbFollowers) {
        this.nbFollowers = nbFollowers;
        return this;
    }

    public DashboardUserResponse setNbNews(Integer nbNews) {
        this.nbNews = nbNews;
        return this;
    }

    public DashboardUserResponse setLicense(LicenseResponse license) {
        this.license = license;
        return this;
    }
}
