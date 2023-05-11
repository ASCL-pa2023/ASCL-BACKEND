package esgi.ascl.tournament.infrastructure.web.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TournamentRequest {
    private String location;
    private Date start_date;
    private Date end_date;
    private Date deadline_inscription_date;
    private esgi.ascl.tournament.infrastructure.web.request.TournamentTypeRequest tournamentType;
}
