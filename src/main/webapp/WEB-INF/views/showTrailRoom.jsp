<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">

    <style>
        tr:first-child{
            font-weight: bold;
            background-color: #C6C9C4;
        }
    </style>

</head>


<body>
    <h2>List of Trail Apparels</h2>
    <table>
        <tr>
            <td>NAME</td><td>Profile Photo</td><td>New Arrival</td><td></td>
        </tr>
        <c:forEach items="${trailRoomVOList}" var="trailRoomVO">
            <tr>
            <td>${trailRoomVO.employeeName}</td>
            <td><img alt="cobined_image" height="150" width="150" src="data:image/jpeg;base64,${trailRoomVO.base64EncodedForProfileTrailPhoto}" /></td>
            <td colspan="3">
                    <c:choose>
                        <c:when test="${trailRoomVO.emailSent}">
                            Email Sent
                        </c:when>
                        <c:otherwise>
                            <a href="<c:url value='/delete-${employee.ssn}-sendEmail' />">sendEmail</a>
                        </c:otherwise>
                    </c:choose>
                </td>
            </tr>
        </c:forEach>
    </table>

</body>
</html>
