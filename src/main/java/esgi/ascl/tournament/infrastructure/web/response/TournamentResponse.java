package esgi.ascl.tournament.infrastructure.web.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

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
    private LocalDateTime start_date;
    @JsonProperty("end_date")
    private LocalDateTime end_date;
    @JsonProperty("deadline_inscription_date")
    private LocalDateTime deadline_inscription_date;
    @JsonProperty("tournament_type")
    private String tournamentType;
    @JsonProperty("places_number")
    private int places_number;
    @JsonProperty("winner_id")
    private Long winner_id;
    @JsonProperty("is_started")
    private boolean is_started;
    @JsonProperty("description")
    private String description;
    @JsonProperty("status")
    private String status;
}
