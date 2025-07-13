<%@ page import="java.util.List" %>
<%@ page import="com.dto.MatchDto" %>
<%@ page contentType="text/html;charset=UTF-8"%>
<html lang="ru">
<head>
    <meta charset="UTF-8">
    <title>Завершенные матчи</title>
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link rel="stylesheet" href="/css/style.css">
</head>
<body>
<nav>
    <p>Tennis Scoreboard</p>
    <div class="nav-links">
        <a href="main">Главная</a>
        <a href="matches">Матчи</a>
    </div>
</nav>
<%
    List<MatchDto> matches = (List<MatchDto>) request.getAttribute("matches");
    String nameParam = request.getParameter("name") != null ? request.getParameter("name") : "";
    if (matches == null || matches.isEmpty()) {
        String message = request.getAttribute("message").toString();
%>
<div class="card-container matches-container">
    <div class="filter-form">
        <form action="matches" method="get">
            <div style="display: flex; flex-direction: column;">
                <label for="name" class="filter-label">Имя</label>
                <input name="name" id="name" value="<%= nameParam %>" class="filter-input">
            </div>
            <button type="submit" class="filter-button">Фильтровать</button>
        </form>
    </div>
    <h1><%= message %></h1>
</div>
<%
} else {
    int pageNumber = request.getParameter("page") == null ? 1 : Integer.parseInt(request.getParameter("page"));
    int maxPages = (request.getAttribute("maxPages") != null) ? (int) request.getAttribute("maxPages") : 1;
%>
<div class="card-container matches-container">
    <div class="filter-form">
        <form action="matches" method="get">
            <div style="display: flex; flex-direction: column;">
                <label for="name" class="filter-label">Имя</label>
                <input name="name" id="name" value="<%= nameParam %>" class="filter-input">
            </div>
            <button type="submit" class="filter-button">Фильтровать</button>
        </form>
    </div>

    <h1>Завершённые матчи</h1>
    <table class="matches-table">
        <tr>
            <th>First Player</th>
            <th>Second Player</th>
            <th>Winner</th>
        </tr>
        <%
            for (MatchDto match : matches) {
        %>
        <tr>
            <td><%= match.getFirstPlayer().getName() %></td>
            <td><%= match.getSecondPlayer().getName() %></td>
            <td><%= match.getWinner().getName() %></td>
        </tr>
        <%
            }
        %>
    </table>
    <div class="pagination">
        <% if (pageNumber > 1) { %>
            <a href="matches?page=<%= pageNumber - 1 %>&name=<%= nameParam %>" title="Назад">&laquo;</a>
        <% } else { %>
            <span style="opacity:0.5;">&laquo;</span>
        <% } %>

        <% if (pageNumber == 1) { %>
            <span><b>1</b></span>
        <% } else { %>
            <a href="matches?page=1&name=<%= nameParam %>">1</a>
        <% } %>

        <% if (pageNumber > 3) { %>
            <span>...</span>
        <% } %>

        <% int start = Math.max(2, pageNumber - 1); %>
        <% int end = Math.min(maxPages - 1, pageNumber + 1); %>
        <% for (int i = start; i <= end; i++) {
            if (i == 1 || i == maxPages) continue;
        %>
            <% if (i == pageNumber) { %>
                <span><b><%= i %></b></span>
            <% } else { %>
                <a href="matches?page=<%= i %>&name=<%= nameParam %>"><%= i %></a>
            <% } %>
        <% } %>

        <% if (pageNumber < maxPages - 2) { %>
            <span>...</span>
        <% } %>

        <% if (maxPages > 1) {
            if (pageNumber == maxPages) { %>
                <span><b><%= maxPages %></b></span>
            <% } else { %>
                <a href="matches?page=<%= maxPages %>&name=<%= nameParam %>"><%= maxPages %></a>
            <% }
        } %>

        <% if (pageNumber < maxPages) { %>
            <a href="matches?page=<%= pageNumber + 1 %>&name=<%= nameParam %>" title="Вперёд">&raquo;</a>
        <% } else { %>
            <span style="opacity:0.5;">&raquo;</span>
        <% } %>
    </div>
</div>
<%
    }
%>
</body>
</html>