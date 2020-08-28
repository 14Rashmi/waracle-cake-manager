<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<!DOCTYPE html>
<html xmlns:th="http://www.thymeleaf.org" language="en">
<head>
    <title>Display the list of cakes</title>
    	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />

    	<link rel="stylesheet" type="text/css" href="https://stackpath.bootstrapcdn.com/twitter-bootstrap/2.3.2/css/bootstrap-combined.min.css">
    	<script src="//ajax.googleapis.com/ajax/libs/jquery/1.8.3/jquery.min.js"></script>
    	<script src="//netdna.bootstrapcdn.com/twitter-bootstrap/2.2.1/js/bootstrap.min.js"></script>
</head>
<body>
     <a href="<c:url value="/logout" />">Logout</a>
     </br>
     <h5>
     		<c:out value="${message}" />
     </h5>
	<h1>Cakes</h1>
	<table style="width:70%">
        <tr>
            <th>Name</th>
            <th>Description</th>
            <th>Click below to open the image <br /> in the new tab</th>
        </tr>
        <c:forEach items="${cakes}" var="cake">
            <tr>
                <td style="text-align:center">${cake.title}</td>
                <td style="text-align:center">${cake.desc}</td>
                <td style="text-align:center"><img src="${cake.image}" height="70" width="70"></td>
            </tr>
        </c:forEach>
    </table>
	<br />

	<br />
	<h1>Add a new Cake</h1>
    	    <form:form method="POST" action="/addCake" modelAttribute="cakeModel">
                <table>
                    <tr>
                        <td><form:label path="title">Title</form:label></td>
                        <td><form:input path="title" required="true"/></td>
                    </tr>
                    <tr>
                        <td><form:label path="desc">Description</form:label></td>
                        <td><form:input path="desc" required="true"/></td>
                    </tr>
                    <tr>
                        <td><form:label path="image">Image URL</form:label></td>
                        <td><form:input path="image" type="url" required="true"/></td>
                    </tr>
                    <tr>
                        <td><input type="submit" value="Submit"/></td>
                    </tr>
                </table>
            </form:form>
</body>
	<script>
		.right {
            text-align: right;
            float: right;
        }
	</script>
</html>