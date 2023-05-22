package esgi.ascl.Tournament.domain.Entitie;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import esgi.ascl.User.domain.entities.User;
import jakarta.persistence.*;

@Entity
@Table(name = "partner_candidacy")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class PartnerCandidacy {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @JsonIgnore
    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;


    @JsonIgnore
    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "survey_id")
    private Survey survey;

    public Long getId() {
        return id;
    }

    public PartnerCandidacy setId(Long id) {
        this.id = id;
        return this;
    }

    public User getUser() {
        return user;
    }

    public PartnerCandidacy setUser(User user) {
        this.user = user;
        return this;
    }

    public Survey getSurvey() {
        return survey;
    }

    public PartnerCandidacy setSurvey(Survey survey) {
        this.survey = survey;
        return this;
    }

}
