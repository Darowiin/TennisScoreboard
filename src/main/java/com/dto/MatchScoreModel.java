package com.dto;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.experimental.FieldDefaults;

import java.util.List;

@Data
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class MatchScoreModel {
    MatchDto matchDto;
    int firstPlayerScore;
    int secondPlayerScore;
    Integer tieBreakScoreFirst;
    Integer tieBreakScoreSecond;
    String advantage;
    List<Integer> games;
    List<Integer> sets;

    public String getFirstPlayerScoreDisplay() {
        if ("1".equals(advantage)) {
            return "AD";
        }
        if (firstPlayerScore == 0) return "0";
        if (firstPlayerScore == 1) return "15";
        if (firstPlayerScore == 2) return "30";
        if (firstPlayerScore == 3) return "40";
        return String.valueOf(firstPlayerScore);
    }

    public String getSecondPlayerScoreDisplay() {
        if ("2".equals(advantage)) {
            return "AD";
        }
        if (secondPlayerScore == 0) return "0";
        if (secondPlayerScore == 1) return "15";
        if (secondPlayerScore == 2) return "30";
        if (secondPlayerScore == 3) return "40";
        return String.valueOf(secondPlayerScore);
    }
}
