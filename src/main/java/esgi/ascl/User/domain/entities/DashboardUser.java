package esgi.ascl.User.domain.entities;

public class DashboardUser {
    public Long id;
    public String firstname;
    public String lastname;
    public Role role;
    public Integer nbFollowers;
    public License license;


    public DashboardUser(Long id, String firstname, String lastname, Role role, Integer nbFollowers, License license) {
        this.id = id;
        this.firstname = firstname;
        this.lastname = lastname;
        this.role = role;
        this.nbFollowers = nbFollowers;
        this.license = license;
    }


    public Long getId() {
        return id;
    }

    public DashboardUser setId(Long id) {
        this.id = id;
        return this;
    }

    public String getFirstname() {
        return firstname;
    }

    public DashboardUser setFirstname(String firstname) {
        this.firstname = firstname;
        return this;
    }

    public String getLastname() {
        return lastname;
    }

    public DashboardUser setLastname(String lastname) {
        this.lastname = lastname;
        return this;
    }

    public Role getRole() {
        return role;
    }

    public DashboardUser setRole(Role role) {
        this.role = role;
        return this;
    }

    public Integer getNbFollowers() {
        return nbFollowers;
    }

    public DashboardUser setNbFollowers(Integer nbFollowers) {
        this.nbFollowers = nbFollowers;
        return this;
    }

    public License getLicense() {
        return license;
    }

    public DashboardUser setLicense(License license) {
        this.license = license;
        return this;
    }
}
