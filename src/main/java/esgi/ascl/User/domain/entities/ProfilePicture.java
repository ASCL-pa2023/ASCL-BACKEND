package esgi.ascl.User.domain.entities;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;

import java.util.Date;

@Entity
@Table(name = "profile_picture")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class ProfilePicture {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    private String filename;
    private Date creationDate;

    public Long getId() {
        return id;
    }

    public User getUser() {
        return user;
    }

    public ProfilePicture setUser(User user) {
        this.user = user;
        return this;
    }

    public String getFilename() {
        return filename;
    }

    public ProfilePicture setFilename(String filename) {
        this.filename = filename;
        return this;
    }

    public Date getCreationDate() {
        return creationDate;
    }

    public ProfilePicture setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
        return this;
    }
}
