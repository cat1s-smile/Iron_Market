<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page contentType="text/html;charset=utf-8" %>
<head>
    <link rel="stylesheet" type="text/css" href="styles/index.css">
    <title>Вход</title>
</head>
<body>
<header>
    <div class="upper-container">
        <div class="text">Выберите роль для входа</div>
        <div class="buttons">
            <form action='<c:url value="user" />'>
                <input type="submit" class="user" value="Я новенький">
            </form>
            <form action='<c:url value="admin" />'>
                <input type="hidden" name="tab" value="products">
                <input type="submit" class="admin" value="Я уже смешарик">
            </form>
        </div>
    </div>
</header>
</body>