<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page contentType="text/html;charset=UTF-8" %>
<html>
<head>
    <title>Результат розрахунків</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/css/bootstrap.min.css" rel="stylesheet"
          integrity="sha384-EVSTQN3/azprG1Anm3QDgpJLIm9Nao0Yz1ztcQTwFspd3yD65VohhpuuCOmLASjC" crossorigin="anonymous">
    <link rel="stylesheet" href="design.css">
</head>
<body>
<jsp:useBean id="solution" scope="request" type="com.delivery.Solution"/>

<div>
    <h1>Результат розрахунків</h1>
</div>

<div class="container start-20 end-20 mt-5">
    <div class="row">
        <div class="col-6 d-flex justify-content-center">
            <div class="w-100 d-flex justify-content-center" id="left-side">
                <div class="w-75 mt-4 mb-4">
                    <h3>Найкращий маршрут:</h3>
                    <ol class="fs-4">
                        <c:forEach var="city" items="${solution.bestRoute}">
                            <li>${city}</li>
                        </c:forEach>
                    </ol>
                </div>
            </div>
        </div>
        <div class="col-6 d-flex justify-content-center">
            <div class="w-100 d-flex justify-content-center" id="right-side">
                <div class="w-75 mt-5 mb-4 d-flex justify-content-center" id="results">
                    <ul class="list-unstyled" id="last-ul">
                        <li class="mb-4">
                            <h5>Довжина маршруту: ${solution.bestTourLength} км</h5>
                        </li>
                        <li class="mb-4">
                            <h5>Витрати на пальне: <fmt:formatNumber type="number" value="${solution.fuelExpenses}"
                                                                     maxFractionDigits="2"/> грн</h5>
                        </li>
<%--                        <li class="mb-4">--%>
<%--                            <h5>Швидкість виконання алгоритму: ${solution.algorithmExecutionTime} мс</h5>--%>
<%--                        </li>--%>
                    </ul>
                </div>
            </div>
        </div>
    </div>
    <div class="d-flex justify-content-center mt-5">
        <a class="return" href="${pageContext.request.contextPath}/app">Повернутися до вибору міст</a>
    </div>
</div>
</body>
</html>
