<%@ page import="java.util.UUID" %>
<%@ page contentType="text/html;charset=UTF-8" %>
<!DOCTYPE html>
<html lang="ru">
<head>
  <meta charset="UTF-8">
  <title>Страница матча</title>
</head>
<body>

<%
  Object scoreObj = request.getAttribute("matchScoreModel");
  UUID matchUuid = (UUID) request.getAttribute("matchUuid");
  if (scoreObj != null) {
    com.dto.MatchScoreModel score = (com.dto.MatchScoreModel) scoreObj;

%>
<table>
  <thead>
  <tr>
    <th scope="col">Players</th>
    <th scope="col">Score</th>
    <th scope="col">Games</th>
    <th scope="col">Sets</th>
  </tr>
  </thead>
  <tbody>
  <tr>
    <th scope="row"> <%= score.getMatchDto().getFirstPlayer().getName() %></th>
    <td><%= score.getFirstPlayerScore()%></td>
    <td><%= score.getGames().get(0)%></td>
    <td><%= score.getSets().get(0)%></td>
    <td>
      <form method="post" action="/match-score?uuid=<%= matchUuid %>">
        <input type="hidden" name="winnerId" value="1">
        <button type="submit">Добавить очко</button>
      </form>
  </tr>
  <tr>
    <th scope="row"><%= score.getMatchDto().getSecondPlayer().getName() %></th>
    <td><%= score.getSecondPlayerScore()%></td>
    <td><%= score.getGames().get(1)%></td>
    <td><%= score.getSets().get(1)%></td>
    <td>
      <form method="post" action="/match-score?uuid=<%= matchUuid %>">
        <input type="hidden" name="winnerId" value="2">
        <button type="submit">Добавить очко</button>
      </form>
  </tr>
  </tbody>
</table>
<%
  }
%>

</body>
</html>
