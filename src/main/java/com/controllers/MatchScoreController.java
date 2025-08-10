package com.controllers;

import com.dto.MatchDto;
import com.dto.MatchScoreModel;
import com.services.MatchScoreCalculationService;
import com.services.OngoingMatchesService;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.UUID;

@WebServlet("/match-score")
public class MatchScoreController extends HttpServlet {
    OngoingMatchesService ongoingMatchesService = new OngoingMatchesService();
    MatchScoreCalculationService matchScoreCalculationService = new MatchScoreCalculationService();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        UUID matchId = UUID.fromString(req.getParameter("uuid"));
        MatchScoreModel matchScoreModel = ongoingMatchesService.getMatchScore(matchId);

        if (matchScoreModel == null) {
            resp.sendError(HttpServletResponse.SC_NOT_FOUND, "Match not found");
            return;
        }
        req.setAttribute("matchScoreModel", matchScoreModel);
        req.setAttribute("matchUuid", matchId);
        ServletContext servletContext = getServletContext();
        RequestDispatcher requestDispatcher = servletContext.getRequestDispatcher("/WEB-INF/match-score.jsp");
        requestDispatcher.forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        UUID matchId = UUID.fromString(req.getParameter("uuid"));
        MatchScoreModel matchScoreModel = ongoingMatchesService.getMatchScore(matchId);
        MatchDto matchDto = matchScoreModel.getMatchDto();

        int winnerId = Integer.parseInt(req.getParameter("winnerId"));
        if (winnerId == 1) {
            matchScoreCalculationService.incrementPlayerScore(matchScoreModel, matchDto.getFirstPlayer());
        } else {
            matchScoreCalculationService.incrementPlayerScore(matchScoreModel, matchDto.getSecondPlayer());
        }
        if (matchScoreModel.getMatchDto().getWinner() != null) {
            ongoingMatchesService.endMatch(matchScoreModel, matchId);
            resp.sendRedirect("/main");
        } else {
            resp.sendRedirect("match-score?uuid=" + matchId);
        }
    }
}
