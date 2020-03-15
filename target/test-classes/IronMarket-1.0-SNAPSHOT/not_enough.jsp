<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page contentType="text/html;charset=utf-8" %>
<!DOCTYPE html>
<head>
    <meta charset="UTF-8">
    <title>Ошибка</title>
    <style>
        body{
            padding: 0;
            margin: 0;
            font-family: Calibri, sans-serif;
            background-color: #edeef0;
        }
        .upper-container{
            width: 100%;
            height: 50px;
            background-color: #AFCDE7;
        }
        .header{
            display: inline;
            text-align: left;
            font-size: 30px;
        }
        .button {
            margin-left: 70px;
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
<header>
    <div class="upper-container">
        <div class="header">В данный момент на складе недостаточно выбранных товаров</div>
        <a href="cart" class="button">Назад</a>
    </div>
</header>
</body>