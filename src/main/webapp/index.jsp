<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page contentType="text/html;charset=utf-8" %>
<head>
  <link rel="stylesheet" type="text/css" href="index2.css">
  <title>Вход</title>
</head>
<body>
<header>
  <div class="upper-container">
    <div class="text">Выберите роль для входа</div>
    <div class="buttons">
      <a href='<c:url value="user" />' class="user">Я новенький</a>
      <a href='<c:url value="admin-products" />' class="admin">Я уже смешарик</a>
    </div>
  </div>
</header>
</body>