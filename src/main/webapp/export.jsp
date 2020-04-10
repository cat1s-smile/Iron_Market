<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page contentType="text/html;charset=utf-8" %>
<!DOCTYPE >
<html>
<head>
    <link rel="stylesheet" type="text/css" href="styles/import.css">
    <title>Экспорт</title>
</head>
<body>



<h2>Критерий экспорта</h2>

<form method="post" action='<c:url value="/export" />'>
    <p>
        <label>
            <input type="radio" name="option" value="a1" checked>
        </label>AllProducts<Br>
        <label>
            <input type="radio" name="option" value="a2">
        </label>ProductsOnSale<Br>
        <label>
            <input type="radio" name="option" value="a3">
        </label>ArchivedProducts<Br>
        <input type="hidden" name="file" value="${file}">
        <input type="submit" class="button" value="Сохранить из базы">
</form>
<a href='<c:url value="/admin" />' style="display: inline">Вернуться</a>

</body>
</html>
