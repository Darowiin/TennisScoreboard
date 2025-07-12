package com.services;

import com.dto.MatchScoreModel;
import com.dto.PlayerDto;

import java.util.ArrayList;
import java.util.List;

public class MatchScoreCalculationService {
    private final List<Integer> scores = new ArrayList<>(List.of(0, 15, 30, 40));

    public void incrementPlayerScore(MatchScoreModel matchScore, PlayerDto playerDto) {
        PlayerDto firstPlayer = matchScore.getMatchDto().getFirstPlayer();
        PlayerDto secondPlayer = matchScore.getMatchDto().getSecondPlayer();
        if (matchScore.getFirstPlayerScore() == 40 && matchScore.getSecondPlayerScore() == 40) {
            if (matchScore.getAdvantage() == null) {
                matchScore.setAdvantage(playerDto.getName());
            } else if (matchScore.getAdvantage().equals(playerDto.getName())) {
                if (playerDto.equals(firstPlayer)) {
                    updateGamesAndSets(matchScore, 0);
                } else {
                    updateGamesAndSets(matchScore, 1);
                }
            } else {
                matchScore.setAdvantage(null);
            }
        } else if ((matchScore.getFirstPlayerScore() == 40 && playerDto.equals(firstPlayer)) ||
                (matchScore.getSecondPlayerScore() == 40 && playerDto.equals(secondPlayer))) {
            if (playerDto.equals(firstPlayer)) {
                updateGamesAndSets(matchScore, 0);
            } else {
                updateGamesAndSets(matchScore, 1);
            }
        } else {
            int currentScore = playerDto.equals(firstPlayer) ?
                matchScore.getFirstPlayerScore() : matchScore.getSecondPlayerScore();
            currentScore = scores.indexOf(currentScore) + 1;
            if (currentScore < scores.size()) {
                if (playerDto.equals(firstPlayer)) {
                    matchScore.setFirstPlayerScore(scores.get(currentScore));
                } else {
                    matchScore.setSecondPlayerScore(scores.get(currentScore));
                }
            }
        }
    }

    private void updateGamesAndSets(MatchScoreModel matchScore, int index) {
        List<Integer> games = matchScore.getGames();
        List<Integer> sets = matchScore.getSets();

        matchScore.setAdvantage(null);
        matchScore.setFirstPlayerScore(0);
        matchScore.setSecondPlayerScore(0);

        games.set(index, games.get(index) + 1);

        if (games.get(0) >= 6 || games.get(1) >= 6) {
            if (games.get(0) - games.get(1) >= 2 || games.get(1) - games.get(0) >= 2) {
                if (games.get(0) > games.get(1)) {
                    sets.set(0, sets.get(0) + 1);
                } else {
                    sets.set(1, sets.get(1) + 1);
                }
                games.set(0, 0);
                games.set(1, 0);
            }
        }
        if (sets.contains(2)) {
            matchScore.getMatchDto().setWinner(
                sets.get(0) == 2 ? matchScore.getMatchDto().getFirstPlayer() : matchScore.getMatchDto().getSecondPlayer()
            );
        }
    }
}
