package esgi.ascl.tournament.infrastructure.web.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TournamentResponse {
    @JsonProperty("id")
    private Long id;
    @JsonProperty("location")
    private String location;
    @JsonProperty("start_date")
    private Date start_date;
    @JsonProperty("end_date")
    private Date end_date;
    @JsonProperty("deadline_inscription_date")
    private Date deadline_inscription_date;
    @JsonProperty("tournament_type")
    private TournamentTypeResponse tournamentType;
    @JsonProperty("places_number")
    private int places_number;
}
