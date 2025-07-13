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

        return new PlayerDto(player.getName());
    }
    public static Player toPlayer(PlayerDto playerDto) {
        if (playerDto == null) {
            return null;
        }

        return new Player(playerDto.getName());
    }
    public static MatchDto toMatchDto(Match match) {
        if (match == null) {
            return null;
        }

        return new MatchDto(toPlayerDto(match.getPlayer1()), toPlayerDto(match.getPlayer2()), toPlayerDto(match.getWinner()));
    }
    public static Match toMatch(MatchDto matchDto) {
        if (matchDto == null) {
            return null;
        }

        return new Match(toPlayer(matchDto.getFirstPlayer()), toPlayer(matchDto.getSecondPlayer()), toPlayer(matchDto.getWinner()));
    }
}
