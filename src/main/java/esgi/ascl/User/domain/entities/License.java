package esgi.ascl.User.domain.entities;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import java.util.Date;


@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "license")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class License {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", updatable = false)
    private Long id;

    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @Column(name = "number", unique = true)
    private Long number;

    @Column(name = "expiration_date")
    private Date expirationDate;

    @Column(name = "created_at")
    private Date createdAt;


    public Long getId() {
        return id;
    }

    public License setId(Long id) {
        this.id = id;
        return this;
    }

    public User getUser() {
        return user;
    }

    public License setUser(User user) {
        this.user = user;
        return this;
    }

    public Long getNumber() {
        return number;
    }

    public License setNumber(Long number) {
        this.number = number;
        return this;
    }

    public Date getExpirationDate() {
        return expirationDate;
    }

    public License setExpirationDate(Date expirationDate) {
        this.expirationDate = expirationDate;
        return this;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public License setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
        return this;
    }
}
