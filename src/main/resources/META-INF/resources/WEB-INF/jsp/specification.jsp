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
        <title>Specification</title>
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
		<h1>Specification</h1>

        <div id="accordion">
            <c:forEach items="${schema.fields}" var="field">
                <h3>${field.name}</h3>
                <div>
                    <div class="select-type" data-type="direct" data-field="${field.name}">
                        <h3>Direct</h3>
                        <label for="src-${field.name}">Source:</label>
                        <input id="src-${field.name}" onchange="setDirectSource('${field.name}', this);" />
                        <div class="btn btn-danger floatBottom" onclick="setNone('${field.name}');">reset</div>
                    </div>
                    <div class="select-type" data-type="mapping" data-field="${field.name}">
                        <h3>Mapping</h3>
                        <div id="mapping-${field.name}"></div>
                        <div class="btn btn-success" onclick="addMapping('${field.name}')">+</div>
                        <div class="btn btn-danger floatBottom" onclick="setNone('${field.name}');">reset</div>
                    </div>
                    <div class="select-type" data-type="default" data-field="${field.name}">
                        <h3>Default</h3>
                        <label for="value-${field.name}">Value:</label>
                        <input id="value-${field.name}" onchange="setDefaultValue('${field.name}', this);">
                        <div class="btn btn-danger floatBottom" onclick="setNone('${field.name}');">reset</div>
                    </div>
                    <div class="select-type" data-type="none" data-field="${field.name}">
                        <fieldset>
                            <legend>Mapping ausw&auml;hlen</legend>
                            <label for="radio-${field.name}-1" onClick="setDirect('${field.name}');">Direkt</label>
                            <input class="select" type="radio" name="radio-1" id="radio-${field.name}-1">
                            <label for="radio-${field.name}-2" onClick="setMapping('${field.name}');">Abbildung</label>
                            <input class="select" type="radio" name="radio-1" id="radio-${field.name}-2">
                            <label for="radio-${field.name}-3" onClick="setDefault('${field.name}');">Defaultwert</label>
                            <input class="select" type="radio" name="radio-1" id="radio-${field.name}-3">
                         </fieldset>
                    </div>
                </div>
            </c:forEach>
        </div>

        <button type="button" class="btn btn-primary controls" onclick="sendState();">Save</button>
        <button type="button" class="btn btn-success controls" onclick="sendState();">Validate</button>
	</body>
</html>