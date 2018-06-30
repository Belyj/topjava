<%@ page import="java.util.List" %>
<%@ page import="ru.javawebinar.topjava.model.MealWithExceedUI" %><%--
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
        List<MealWithExceedUI> meals = (List<MealWithExceedUI>) request.getAttribute("meals");
        for (MealWithExceedUI m : meals) {%>
    <tr bgcolor="<%=m.getExceedColor()%>">
        <td>
            <%=m.getDateTime()%>
        </td>
        <td>
            <%=m.getDescription()%>
        </td>
        <td>
            <%=m.getCalories()%>
        </td>
    </tr>
    <%}%>
</table>
</body>
</html>
