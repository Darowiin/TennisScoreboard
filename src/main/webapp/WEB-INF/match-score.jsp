<%@ page import="java.util.UUID" %>
<%@ page contentType="text/html;charset=UTF-8" %>
<!DOCTYPE html>
<html lang="ru">
<head>
  <meta charset="UTF-8">
  <title>Страница матча</title>
  <meta name="viewport" content="width=device-width, initial-scale=1.0">
  <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
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
  Object scoreObj = request.getAttribute("matchScoreModel");
  UUID matchUuid = (UUID) request.getAttribute("matchUuid");
  if (scoreObj != null) {
    com.dto.MatchScoreModel score = (com.dto.MatchScoreModel) scoreObj;
    boolean isTieBreak = false;
    Integer tb1 = null;
    Integer tb2 = null;
    tb1 = score.getTieBreakScoreFirst();
    tb2 = score.getTieBreakScoreSecond();
    isTieBreak = tb1 != null && tb2 != null;
%>
<div class="card-container match-score-container">
  <h1>Счёт матча</h1>
  <table class="match-score-table">
    <thead>
    <tr>
      <th>Игрок</th>
      <th>Очки</th>
      <th>Геймы</th>
      <th>Сеты</th>
      <th>Действие</th>
    </tr>
    </thead>
    <tbody>
    <tr>
      <td><%= score.getMatchDto().getFirstPlayer().getName() %></td>
      <td><%= score.getFirstPlayerScoreDisplay()%></td>
      <td><%= score.getGames().get(0)%></td>
      <td><%= score.getSets().get(0)%></td>
      <td>
        <form method="post" action="${pageContext.request.contextPath}/match-score?uuid=<%= matchUuid %>">
          <input type="hidden" name="uuid" value="<%= matchUuid %>">
          <input type="hidden" name="winnerId" value="1">
          <button type="submit">Добавить очко</button>
        </form>
      </td>
    </tr>
    <tr>
      <td><%= score.getMatchDto().getSecondPlayer().getName() %></td>
      <td><%= score.getSecondPlayerScoreDisplay()%></td>
      <td><%= score.getGames().get(1)%></td>
      <td><%= score.getSets().get(1)%></td>
      <td>
        <form method="post" action="${pageContext.request.contextPath}/match-score?uuid=<%= matchUuid %>">
          <input type="hidden" name="uuid" value="<%= matchUuid %>">
          <input type="hidden" name="winnerId" value="2">
          <button type="submit">Добавить очко</button>
        </form>
      </td>
    </tr>
    <% if (isTieBreak) { %>
    <tr class="tie-break-row">
      <td colspan="5">
        <div class="tie-break-info">
          <h3>Тай-брейк</h3>
          <p><%= score.getMatchDto().getFirstPlayer().getName() %>: <%= tb1 %></p>
          <p><%= score.getMatchDto().getSecondPlayer().getName() %>: <%= tb2 %></p>
        </div>
      </td>
    </tr>
    <% } %>
    </tbody>
  </table>
</div>
<%
  }
%>
</body>
</html>