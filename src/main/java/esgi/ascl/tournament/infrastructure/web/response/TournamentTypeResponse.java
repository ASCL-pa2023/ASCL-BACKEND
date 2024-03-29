package esgi.ascl.tournament.infrastructure.web.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TournamentTypeResponse {
    @JsonProperty("id")
    private Long id;
    @JsonProperty("name")
    private String name;
}
