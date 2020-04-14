<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page contentType="text/html;charset=utf-8" %>
<!DOCTYPE html>
<head>
    <link rel="stylesheet" type="text/css" href="styles/perform.css">
    <meta charset="UTF-8">
    <title>Добавление категории</title>
</head>
<body>
<header>
    <div class="upper-container">
        <div class="header">Добавление категории</div>
    </div>
    <div style="padding:5px; color:red;font-style:italic;">
        ${message}
    </div>
</header>
<form method="post">
    <div class="fields">
        <div class="field">
            <div class="property-name">Название</div>
            <label>
                <input type="text" name="name" class="input-field">
            </label>
        </div>
        <br>
        <input type="submit" class="button" value="Сохранить"/>
    </div>
</form>
</body>