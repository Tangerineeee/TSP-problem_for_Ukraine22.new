<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <title>Алгоритм мурашиних колоній для розв'язання задачі комівояжера</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/css/bootstrap.min.css" rel="stylesheet"
          integrity="sha384-EVSTQN3/azprG1Anm3QDgpJLIm9Nao0Yz1ztcQTwFspd3yD65VohhpuuCOmLASjC" crossorigin="anonymous">
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/js/bootstrap.bundle.min.js"
            integrity="sha384-MrcW6ZMFYlzcLA8Nl+NtUVF0sA7MsXsP1UyJoMp4YLEuNSfAP+JcXn/tWtIaxVXM"
            crossorigin="anonymous"></script>
    <script src="cities_list_functions.js"></script>
    <link rel="stylesheet" href="form.css">
    <%--    <link rel="stylesheet" href="new-form.less">--%>
    <link rel="stylesheet" href="design.css">
</head>

<body>
<jsp:useBean id="citiesList" scope="request" type="java.util.List"/>

<div>
    <h1>Задача Комівояжера</h1>
</div>

<div class="container start-20 end-20 mt-5">
    <form method="get" onsubmit="return validateNumberOfCities()" action="${pageContext.request.contextPath}/app">
        <div class="row">
            <div class="col-6 d-flex justify-content-center">
                <div class="w-100 d-flex justify-content-center" id="left-side">
                    <div class="w-75 mt-4 mb-4">
                        <div class="form-outline mb-3">
                            <label for="fuel-cost" class="form-label">Введіть ціну пального (грн/л):</label>
                            <input id="fuel-cost" name="fuel-cost" type="number" min="1" class="form-control" required>
                        </div>

                        <div class="form-outline mb-3">
                            <label for="fuel-consumption" class="form-label">Введіть розхід пального (л/100 км):</label>
                            <input id="fuel-consumption" name="fuel-consumption" type="number" min="1"
                                   class="form-control"
                                   required>
                        </div>

                        <div class="form-outline mb-3">
                            <label for="city" class="form-label">Оберіть міста:</label>
                            <div class="row gx-0 justify-content-start">
                                <div class="col-10">
                                    <select class="form-select" id="city" aria-label="Select cities">
                                        <c:forEach var="city" items="${citiesList}">
                                            <option value="${city}">${city}</option>
                                        </c:forEach>
                                    </select>
                                </div>
                                <div class="col-2">
                                    <button type="button" onclick="addCity()" class="add-button">
                                    </button>
                                </div>
                            </div>
                        </div>
                        <input type="hidden" name="citiesList" id="citiesList">
                    </div>
                </div>
            </div>
            <div class="col-6 d-flex justify-content-center">
                <div class="w-100 d-flex justify-content-center" id="right-side">
                    <div class="w-50 mt-4 mb-4">
                        <div id="emptyList">
                            <h4>Ще не обрано жодного міста</h4>
                        </div>
                        <div id="chosenCities" class="hide">
                            <h4>Список обраних міст</h4>
                            <ol id="chosenCitiesList" class="full-list"></ol>
                        </div>
                    </div>
                </div>
            </div>
            <div class="d-flex justify-content-center mt-5">
                <input type="submit" class="submit" id="submit-button" value="Розрахувати"/>
            </div>
        </div>
    </form>
</div>
</body>
</html>
