<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  User: a.beloglazov
  Date: 30.06.2018
  Time: 17:13
  To change this template use File | Settings | File Templates.
--%>
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

    RequestDispatcher rd = request.getRequestDispatcher(dispatcherUrl);
    rd.forward(request, response);

    <c:forEach items="${meals}" var="m" >
        <tr bgcolor="${m.getExceedColor()}">
            <td>${m.getDateTime()}</td>
            <td>${m.getDescription()}</td>
            <td>${m.getCalories()}</td>
        </tr>
    </c:forEach>
</table>
</body>
</html>
