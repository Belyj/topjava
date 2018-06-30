<%@ page import="ru.javawebinar.topjava.model.MealWithExceed" %>
<%@ page import="java.util.List" %>
<%@ page import="java.util.Map" %><%--
  Created by IntelliJ IDEA.
  User: a.beloglazov
  Date: 30.06.2018
  Time: 17:13
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Meals</title>
</head>
<body>
<h3><a href="index.html">Home</a></h3>
<h2>Meals</h2>

<table width="400">
    <thead>
    <th>Date / Time</th>
    <th>Description</th>
    <th>Calories</th>
    </thead>
    <%
        Map<String, MealWithExceed> meals = (Map<String, MealWithExceed>) request.getAttribute("meals");
        for (Map.Entry<String, MealWithExceed> m : meals.entrySet()) {%>
    <tr bgcolor="aqua">
        <td><%=m.getKey()%></td>
        <td><%=m.getValue().getDescription()%></td>
        <td><%=m.getValue().getCalories()%>
        <td><%=m.getValue().isExceed()%></td>
    </tr>
    <%}%>
</table>
</body>
</html>
