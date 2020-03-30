<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page contentType="text/html;charset=utf-8" %>
<!DOCTYPE >
<html>
<head>
    <link rel="stylesheet" type="text/css" href="import.css">
    <title>Upload files</title>
</head>
<body>

<h1>Файл загружен</h1>
<form method="post" action='<c:url value="/import" />'>
    <p><b>Что делать с возможными дубликатами?</b></p>
    <p>
        <label>
            <input type="radio" name="option" value="a1" checked>
        </label>Пропустить<Br>
        <label>
            <input type="radio" name="option" value="a2">
        </label>Перезаписать<Br>
        <label>
            <input type="radio" name="option" value="a3">
        </label>Запись с дубликатами<Br>
        <input type="hidden" name="file" value="${file}">
    <input type="submit" class="button" value="Загрузить в базу">
</form>
<p> ${preview}Preview</p>
</body>
</html>
