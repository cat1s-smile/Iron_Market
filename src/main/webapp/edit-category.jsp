<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page contentType="text/html;charset=utf-8" %>
<fmt:requestEncoding value="UTF-8" />
<!DOCTYPE html>
<head>
    <link rel="stylesheet" type="text/css" href="styles/perform.css">
    <meta http-equiv="Content-Type" content="text/html" charset="UTF-8">
    <title>Редактирование категории</title>
</head>
<body>
<header>
    <div class="upper-container">
        <div class="header">Редактирование категории</div>
    </div>
    <div style="padding:5px; color:red;font-style:italic;">
        ${message}
    </div>
</header>
<form method="post">
    <div class="fields">
        <div class="field">
            <input type="hidden" value="${category.id}" name="idCategory" />
            <div class="property-name">Название</div>
            <label>
                <input name="name" value="${category.name}" class="input-field"/>
            </label>
        </div>
        <br>
        <input type="submit" class="button" value="Сохранить"/>
    </div>
</form>
</body>