package com.dto;

import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

@Data
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class MatchDto {
    PlayerDto firstPlayer;
    PlayerDto secondPlayer;
    PlayerDto winner;

    public MatchDto(PlayerDto firstPlayer, PlayerDto secondPlayer, PlayerDto winner) {
        this.firstPlayer = firstPlayer;
        this.secondPlayer = secondPlayer;
        this.winner = winner;
    }
}
