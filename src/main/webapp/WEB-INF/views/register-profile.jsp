<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<html>

<head>
    <meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
    <title>Profile Registration Form</title>

<style>

    .error {
        color: #ff0000;
    }
</style>

<link href="<c:url value='/static/css/bootstrap.css' />"  rel="stylesheet" type="text/css"></link>
    <link href="<c:url value='/static/css/app.css' />" rel="stylesheet" type="text/css"></link>
</head>

<body>

    <h2>Registration Form</h2>

    <form:form method="POST" modelAttribute="profileVO" enctype="multipart/form-data">
        <form:input type="hidden" path="id" id="id"/>
        <table>
            <tr>
                <td><label for="name">First Name: </label> </td>
                <td><form:input path="firstName" id="firstName"/></td>
                <td><form:errors path="firstName" cssClass="error"/></td>

                <td><label for="name">Last Name: </label> </td>
                <td><form:input path="lastName" id="lastName"/></td>
                <td><form:errors path="lastName" cssClass="error"/></td>

            </tr>

            <tr>
                <td><label for="birthDate">Birth Date: </label> </td>
                <td><form:input path="birthDate" id="birthDate"/></td>
                <td><form:errors path="birthDate" cssClass="error"/></td>
            </tr>

            <tr>
                <td><label for="sex">Sex: </label> </td>
                <td><form:input path="sex" id="sex"/></td>
                <td><form:errors path="sex" cssClass="error"/></td>
            </tr>

            <tr>
                <td><label for="emailAddress">EMAIL ADDRESS: </label> </td>
                <td><form:input path="emailAddress" id="ssn"/></td>
                <td><form:errors path="emailAddress" cssClass="error"/></td>
            </tr>

            <tr>
                <td><label for="photo">Portrait Photo: </label></td>
                <td><form:input type="file" path="photo.file" id="photo"/></td>
            </tr>
            <tr>
                <td colspan="3">
                    <c:choose>
                        <c:when test="${edit}">
                            <input type="submit" value="Update"/>
                        </c:when>
                        <c:otherwise>
                            <input type="submit" value="Register"/>
                        </c:otherwise>
                    </c:choose>
                </td>
            </tr>
        </table>
    </form:form>
    <br/>
    <br/>
    Go back to <a href="<c:url value='/list' />">List of All Profiles</a>
</body>
</html>