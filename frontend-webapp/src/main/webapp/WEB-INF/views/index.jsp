<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<html ng-app="angularHelloApp">
    <head>
        <script src="https://ajax.googleapis.com/ajax/libs/angularjs/1.4.3/angular.min.js"></script>
        <script src="js/angular-hello.js"></script>
        <title></title>
    </head>
    <body>
        <div ng-controller="HelloController">
            <img src="images/Lumpy_Space.png"/>
            <br>
            {{noOfHellos}} Hellos received!
            <br>

            <!-- csrt support -->
            <form action="<c:url value="/logout"/>" method="post" id="logoutForm">
                <input type="hidden"
                    name="${_csrf.parameterName}"
                    value="${_csrf.token}" />
            </form>

            <script>
                function formSubmit() {
                    document.getElementById("logoutForm").submit();
                }
            </script>

            <c:if test="${pageContext.request.userPrincipal.name != null}">
                    Welcome : ${pageContext.request.userPrincipal.name} | <a ng-href="#here" onClick="formSubmit()">Logout</a>
            </c:if>
        </div>
    </body>
</html>