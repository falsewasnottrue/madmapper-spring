<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %><%
response.setHeader("Cache-Control","no-cache");
response.setHeader("Pragma","no-cache");
response.setHeader("Expires","0");
%>

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>

<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="form"   uri="http://www.springframework.org/tags/form" %>

<html>
    <head>
        <meta charset="utf-8">
        <title>Specifications</title>
        <link rel="stylesheet" href="css/bootstrap.min.css">
        <link rel="stylesheet" href="//code.jquery.com/ui/1.12.1/themes/base/jquery-ui.css">
        <link rel="stylesheet" href="css/madmapper.css">

        <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.1.1/jquery.min.js"></script>
        <script src="https://code.jquery.com/ui/1.12.1/jquery-ui.min.js"
         integrity="sha256-VazP97ZCwtekAsvgPBSUwPFKdrwD3unUfSGVYrahUqU="
         crossorigin="anonymous"></script>
        <script src="js/jquery.tabledit.min.js"></script>
    </head>
	<body>
    	<script src="js/specification.js"></script>
		<h1>Specifications</h1>

        <table class="table">
            <c:forEach items="${specifications}" var="specName">
                <tr>
                    <td><a href="/specification/${specName}">specName</a></td>
                    <td><a href="/generate/${specName}">generate</a></td>
                </tr>
            </c:forEach>
        </table>
	</body>
</html>