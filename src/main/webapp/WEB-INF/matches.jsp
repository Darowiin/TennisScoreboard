<%@ page import="java.util.List" %>
<%@ page import="com.dto.MatchDto" %>
<%@ page contentType="text/html;charset=UTF-8"%>
<html>
<head>
    <title>Завершенные матчи</title>
</head>
<body>
<%
    List<MatchDto> matches = (List<MatchDto>) request.getAttribute("matches");;
    if (matches == null || matches.isEmpty()) {
        String message = request.getAttribute("message").toString();
%>
<h1><%= message%></h1>
<%
    } else {
%>
        <table>
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
    }
%>


</body>
</html>
