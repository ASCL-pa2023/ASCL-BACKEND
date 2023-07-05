package esgi.ascl.tournament.infrastructure.web.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TournamentRequest {
    @JsonProperty("location")
    private String location;
    @JsonProperty("start_date")
    private String start_date;
    @JsonProperty("end_date")
    private String end_date;
    @JsonProperty("deadline_inscription_date")
    private String deadline_inscription_date;
    @JsonProperty("tournament_type")
    private String tournamentType;
    @JsonProperty("places_number")
    private int places_number;
    @JsonProperty("description")
    private String description;
    @JsonProperty("status")
    private String status;
}
