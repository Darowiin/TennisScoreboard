package com.services;

import com.dao.MatchDaoImpl;
import com.dao.PlayerDaoImpl;
import com.dto.MatchDto;
import com.dto.MatchScoreModel;
import com.dto.PlayerDto;
import com.models.Match;
import com.models.Player;
import com.utils.Mapper;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Getter
@Setter
public class OngoingMatchesService {
    private static Map<UUID, MatchScoreModel> ongoingMatches = new HashMap<>();
    private final MatchDaoImpl matchDao = new MatchDaoImpl();
    private final PlayerDaoImpl playerDao = new PlayerDaoImpl();

    public UUID addMatch(String firstPlayerName, String secondPlayerName) {
        PlayerDto firstPlayer;
        PlayerDto secondPlayer;

        var firstOpt = playerDao.getByName(firstPlayerName);
        var secondOpt = playerDao.getByName(secondPlayerName);

        firstPlayer = firstOpt.map(Mapper::toPlayerDto).orElse(new PlayerDto(firstPlayerName));
        secondPlayer = secondOpt.map(Mapper::toPlayerDto).orElse(new PlayerDto(secondPlayerName));

        MatchDto matchDto = new MatchDto(firstPlayer, secondPlayer);


        UUID matchId = UUID.randomUUID();
        ongoingMatches.put(matchId, new MatchScoreModel(matchDto,0, 0, null,
                new ArrayList<>(List.of(0, 0)), new ArrayList<>(List.of(0, 0))));

        return matchId;
    }
    public MatchScoreModel getMatchScore(UUID matchId) {
        return ongoingMatches.get(matchId);
    }
    public void endMatch(MatchScoreModel matchScore, UUID matchId) {
        MatchDto matchDto = matchScore.getMatchDto();
        Player player1 = playerDao.getByName(matchDto.getFirstPlayer().getName())
                .orElseGet(() -> {
                    playerDao.save(Mapper.toPlayer(matchDto.getFirstPlayer()));
                    return playerDao.getByName(matchDto.getFirstPlayer().getName()).orElseThrow();
                });

        Player player2 = playerDao.getByName(matchDto.getSecondPlayer().getName())
                .orElseGet(() -> {
                    playerDao.save(Mapper.toPlayer(matchDto.getSecondPlayer()));
                    return playerDao.getByName(matchDto.getSecondPlayer().getName()).orElseThrow();
                });

        Match match = new Match(player1, player2);
        match.setWinner(matchDto.getWinner().getName().equals(player1.getName()) ? player1 : player2);
        matchDao.save(match);
        ongoingMatches.remove(matchId);
    }
}
