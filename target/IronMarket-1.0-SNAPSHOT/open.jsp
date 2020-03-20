<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page contentType="text/html;charset=utf-8" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>О товаре</title>
    <style>
        body{
            padding: 0;
            margin: 0;
            font-family: Calibri, sans-serif;
            background-color: #edeef0;
        }
        .container{
            width: 50%;
            margin-left: 25%;
            margin-right: 25%;
            height: 500px;
        }
        h3, p{
            text-align: center;
        }
        .button {
            margin-top: 10px;
            margin-left: 310px;
            width: 150px;
            color: #fff; /* цвет текста */
            text-decoration: none; /* убирать подчёркивание у ссылок */
            user-select: none; /* убирать выделение текста */
            background: rgb(52, 95, 167); /* фон кнопки */
            padding: .7em 1.5em; /* отступ от текста */
            outline: none; /* убирать контур в Mozilla */
        }
        .button:hover {
            background: rgb(61, 78, 167);
        } /* при наведении курсора мышки */
        .button:active {
            background: rgb(18, 32, 167);
        } /* при нажатии */
    </style>
</head>
<body>
<div class="container">
    <h3>${product.name}</h3>
    <p>entities.main.Product code: ${product.idProduct}</p>
    <p>entities.main.Category: ${product.idCategory}</p>
    <p>Description: ${product.description}</p>
    <p>Amount on store: ${product.amount}</p>
    <p>Price: ${product.price}</p>
    <form method="get" action='<c:url value="/user" />' style="display:inline;">
        <input type="submit" value="Назад" class="button">
    </form>
    <form method="post" action='<c:url value="/buy" />' style="display:inline;">
        <input type="hidden" name="id" value="${product.idProduct}">
        <input type="submit" value="В корзину" class="button">
    </form>
</div>
</body>
</html>