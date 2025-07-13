package com.services;

import com.dao.MatchDaoImpl;
import com.dto.MatchDto;
import com.models.Match;
import com.utils.Mapper;

import java.util.ArrayList;
import java.util.List;

public class FinishedMatchesService {
    private static final MatchDaoImpl matchDao = new MatchDaoImpl();

    private List<MatchDto> getMatches() {
        List<MatchDto> matches = new ArrayList<>();
        if (matchDao.getAll().isPresent()) {
            for (Match match : matchDao.getAll().get()) {
                matches.add(Mapper.toMatchDto(match));
            }
        }
        else {
            matches = new ArrayList<>();
        }
        return matches;
    }

    public List<MatchDto> getMatchesByPage(int page, int size, String filter) {
        List<MatchDto> matches = getMatches();
        if (filter != null && !filter.isEmpty()) {
            matches.removeIf(match -> !match.getFirstPlayer().getName().toLowerCase().contains(filter.toLowerCase()) &&
                                      !match.getSecondPlayer().getName().toLowerCase().contains(filter.toLowerCase()));
        }
        if (matches == null || matches.isEmpty()) {
            return new ArrayList<>();
        }

        int fromIndex = (page - 1) * size;
        int toIndex = Math.min(fromIndex + size, matches.size());

        if (fromIndex >= matches.size() || fromIndex < 0) {
            throw new IndexOutOfBoundsException("Page index out of bounds");
        }

        return matches.subList(fromIndex, toIndex);
    }

    public int getTotalMatchesCount() {
        return matchDao.getAll().map(List::size).orElse(0);
    }

    public int getFilteredMatchesCount(String filter) {
        List<MatchDto> matches = getMatches();
        if (filter != null && !filter.isEmpty()) {
            matches.removeIf(match -> !match.getFirstPlayer().getName().toLowerCase().contains(filter.toLowerCase()) &&
                                      !match.getSecondPlayer().getName().toLowerCase().contains(filter.toLowerCase()));
        }
        return matches.size();
    }
}
