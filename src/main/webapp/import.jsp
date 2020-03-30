<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page contentType="text/html;charset=utf-8" %>
<!DOCTYPE >
<html>
<head>
    <link rel="stylesheet" type="text/css" href="import.css">
    <title>Импорт</title>
</head>
<body>

<div style="padding:5px; color:red;font-style:italic;">
    ${errorMessage}
</div>

<h2>Загрузка файла</h2>

<form method="post" action='<c:url value="/uploadFile" />'
      enctype="multipart/form-data">
    <p>Выберите файл:</p>
    <div class="files">
        <input type="file" name="file"  />
    </div>
    <br>
    <input type="submit" class="button" value="Загрузить" />
</form>

</body>
</html>
