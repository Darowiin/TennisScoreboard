package com.controllers;

import com.services.FinishedMatchesService;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet("/matches")
public class MatchesController extends HttpServlet {
    FinishedMatchesService finishedMatchesService = new FinishedMatchesService();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        int page = req.getParameter("page") == null ? 1 : Integer.parseInt(req.getParameter("page"));
        int matchCount = finishedMatchesService.getTotalMatchesCount();
        var matches = finishedMatchesService.getMatchesByPage(page, 10);

        int maxPages = (int) Math.ceil((double) matchCount / 10);

        req.setAttribute("maxPages", maxPages);
        req.setAttribute("matches", matches);

        if (matches.isEmpty()) {
            req.setAttribute("message", "Нет завершённых матчей");
        }

        req.getRequestDispatcher("/WEB-INF/matches.jsp").forward(req, resp);
    }
}
