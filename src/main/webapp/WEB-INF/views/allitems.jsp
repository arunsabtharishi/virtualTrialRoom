<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
    <title>University Enrollments</title>

    <style>
        tr:first-child{
            font-weight: bold;
            background-color: #C6C9C4;
        }
    </style>

</head>


<body>
    <h2>List of Items</h2>
    <table>
        <tr>
            <td>NAME</td><td>Photo</td>
        </tr>
        <c:forEach items="${itemVOList}" var="itemVO">
            <tr>
            <td><a href="<c:url value='/inventory/edit-${itemVO.id}-item' />">${itemVO.name}</a></td>
            <td><a href="<c:url value='/inventory/view-${itemVO.id}-photo' />"><img alt="image" height="150" width="150" src="data:image/jpeg;base64,${itemVO.base64Encoded}" /></a></td>
            <td><a href="<c:url value='/inventory/delete-${itemVO.id}-item' />">delete</a></td>
            </tr>
        </c:forEach>
    </table>
    <br/>
    <a href="<c:url value='/inventory/new' />">Add New Item</a>

</body>
</html>