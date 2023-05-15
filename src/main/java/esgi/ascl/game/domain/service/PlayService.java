package esgi.ascl.game.domain.service;

import esgi.ascl.game.domain.entities.Game;
import esgi.ascl.game.domain.entities.Play;
import esgi.ascl.game.domain.entities.Team;
import esgi.ascl.game.infra.repository.PlayRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PlayService {
    private final PlayRepository playRepository;

    public PlayService(PlayRepository playRepository) {
        this.playRepository = playRepository;
    }

    public Play playGame(Game game, Team team){
        var play = new Play()
                .setGame(game)
                .setTeam(team);
        return playRepository.save(play);
    }

    public List<Play> getPlaysByGameId(Long gameId){
        return playRepository.findAllByGameId(gameId);
    }
}
