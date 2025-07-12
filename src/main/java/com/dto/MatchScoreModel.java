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
    String advantage;
    List<Integer> games;
    List<Integer> sets;
}
