<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
    <title>Profile Envrollement</title>

    <style>
        tr:first-child{
            font-weight: bold;
            background-color: #C6C9C4;
        }
    </style>

</head>


<body>
    <h2>List of Profiles</h2>
    <table>
        <tr>
            <td>FIRST NAME</td><td>LAST NAME</td><td>BIRTH DATE</td><td>SEX</td><td>EMAIL ADDRESS</td><td>PHOTO</td><td></td><td></td>
        </tr>
        <c:forEach items="${profileVOList}" var="profileVO">
            <tr>
            <td>${profileVO.firstName}</td>
            <td>${profileVO.lastName}</td>
            <td>${profileVO.birthDate}</td>
            <td>${profileVO.sex}</td>
            <td>${profileVO.emailAddress}</td>
            <td><img alt="image" height="150" width="150" src="data:image/jpeg;base64,${profileVO.base64Encoded}" /></td>
            <td><a href="<c:url value='/edit-${profileVO.id}-profile' />">Edit</a></td>
            <td><a href="<c:url value='/delete-${profileVO.id}-profile' />">delete</a></td>
            </tr>
        </c:forEach>
    </table>
    <br/>
    <a href="<c:url value='/new' />">Add New Profile</a>

</body>
</html>