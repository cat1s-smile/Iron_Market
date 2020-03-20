<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page contentType="text/html;charset=utf-8" %>
<!DOCTYPE >
<html>
<head>
    <title>Экспорт</title>
</head>
<body>



<h2>Критерий экспорта</h2>

<form method="post" action='<c:url value="/export" />'>
    <p>
        <label>
            <input type="radio" name="option" value="a1">
        </label>AllProducts<Br>
        <label>
            <input type="radio" name="option" value="a2">
        </label>ProductsOnSale<Br>
        <label>
            <input type="radio" name="option" value="a3">
        </label>ArchivedProducts<Br>
        <input type="hidden" name="xml" value="${xml}">
    <p><input type="submit" value="Сохранить из базы"></p>
</form>

</body>
</html>
