package esgi.ascl.tournament.infrastructure.web.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TournamentTypeResponse {
    private Long id;
    private String name;
}
