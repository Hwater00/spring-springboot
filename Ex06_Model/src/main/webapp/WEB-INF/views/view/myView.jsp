<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  User: KIMHYESOO
  Date: 2023-08-20
  Time: 오후 10:29
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri=" http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>Title</title>
</head>
<body>
    ${ObjectTest}
    ${lists}
<c:forEach var="mylist"items="${lists}">
    ${mylist}
</c:forEach>
<br>
당신의 이름은 ${name}입니다.
</body>
</html>
