<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page contentType="text/html;charset=utf-8" %>
<!DOCTYPE >
<html>
<head>
    <link rel="stylesheet" type="text/css" href="styles/import.css">
    <title>Изменение параметров</title>
</head>
<body>

<h2>Выберите необходимое действие?</h2>

<form method="post" action='<c:url value="/chooseAction" />'>
    <p>
        <label>
            <input type="radio" name="option" checked value="a1">
        </label>Изменение статуса товаров в магазине<Br>
        <label>
            <input type="radio" name="option" value="a2">
        </label>Изменение параметров товаров<Br>

    <input type="submit" class="button" value="Продолжить">
</form>
<a href='<c:url value="/admin" />' style="display: inline">Вернуться</a>

</body>
</html>
