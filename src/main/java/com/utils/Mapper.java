package com.utils;

import com.dto.PlayerDto;
import com.dto.MatchDto;
import com.models.Player;
import com.models.Match;

public class Mapper {
    public static PlayerDto toPlayerDto(Player player) {
        if (player == null) {
            return null;
        }

        PlayerDto playerDto = new PlayerDto();
        playerDto.setName(player.getName());

        return playerDto;
    }
    public static Player toPlayer(PlayerDto playerDto) {
        if (playerDto == null) {
            return null;
        }

        Player player = new Player();
        player.setName(playerDto.getName());

        return player;
    }
    public static MatchDto toMatchDto(Match match) {
        if (match == null) {
            return null;
        }

        MatchDto matchDto = new MatchDto();
        matchDto.setFirstPlayer(toPlayerDto(match.getPlayer1()));
        matchDto.setSecondPlayer(toPlayerDto(match.getPlayer2()));
        matchDto.setWinner(toPlayerDto(match.getWinner()));

        return matchDto;
    }
    public static Match toMatch(MatchDto matchDto) {
        if (matchDto == null) {
            return null;
        }

        Match match = new Match();
        match.setPlayer1(toPlayer(matchDto.getFirstPlayer()));
        match.setPlayer2(toPlayer(matchDto.getSecondPlayer()));
        match.setWinner(toPlayer(matchDto.getWinner()));

        return match;
    }
}
