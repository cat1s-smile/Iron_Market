<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page contentType="text/html;charset=utf-8" %>
<!DOCTYPE >
<html>
<head>
    <link rel="stylesheet" type="text/css">
    <title>Изменение параметров</title>
</head>
<body>

<form method="post" action='<c:url value="/chooseAction" />'>
    <p><b>Выберите необходимое действие?</b></p>
    <p>
        <label>
            <input type="radio" name="option" value="a1">
        </label>Архивация<Br>
        <label>
            <input type="radio" name="option" value="a2">
        </label>Изменение параметров<Br>

    <input type="submit" class="button" value="Продолжить">
</form>

</body>
</html>
