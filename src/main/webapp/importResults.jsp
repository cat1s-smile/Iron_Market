<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page contentType="text/html;charset=utf-8" %>
<!DOCTYPE >
<html>
<head>
    <title>Upload files</title>
</head>
<body>

<h3>Upload has been done successfully!</h3>
<a href="preview.html">Preview</a>
<form method="post" action='<c:url value="/import" />'>
    <p><b>Что делать с возможными дубликатами?</b></p>
    <p>
        <label>
            <input type="radio" name="option" value="a1">
        </label>IgnoreDuplicates<Br>
        <label>
            <input type="radio" name="option" value="a2">
        </label>OverwriteDuplicates<Br>
        <label>
            <input type="radio" name="option" value="a3">
        </label>WithDuplicates<Br>
        <input type="hidden" name="file" value="${file}">
        <input type="hidden" name="xsl" value="${xsl}">
    <p><input type="submit" value="Загрузить в базу"></p>
</form>

</body>
</html>
