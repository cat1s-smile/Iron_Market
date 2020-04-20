<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page contentType="text/html;charset=utf-8" %>
<!DOCTYPE html>
<head>
    <link rel="stylesheet" type="text/css" href="styles/perform.css">
    <meta charset="UTF-8">
    <title>Добавление товара</title>
</head>
<body>
<header>
    <div class="upper-container">
        <div class="header">Добавление товара</div>
    </div>
    <div style="padding:5px; color:red;font-style:italic;">
        ${message}
    </div>
</header>
<form method="post" action='<c:url value="/create" />'>
    <section>
        <div class="fields">
            <div class="field" id="first-field">
                <div class="property-name">Название</div>
                <label>
                    <input type="text" name="name" class="input-field">
                </label>
            </div>
            <div class="field">
                <div class="property-name">Цена</div>
                <label>
                    <input type="number" name="price" class="input-field" min="100">
                </label>
            </div>
            <div class="field">
                <div class="property-name">Количество</div>
                <label>
                    <input type="number" name="amount" class="input-field" min="0">
                </label>
            </div>
            <div class="field">
                <div class="property-name">Описание</div>
                <label>
                    <input type="text" name="description" class="input-field">
                </label>
            </div>
            <div class="field">
                <div class="property-name">Категория</div>
                <label>
                    <select name="category" required class="input-field">
                        <c:forEach var="category" items="${categories}" >
                            <option>${category.name}</option>
                        </c:forEach>
                    </select>
                </label>
            </div>
            <br>
            <input type="submit" class="button" value="Сохранить"/>
        </div>
        <p>
        <a href='<c:url value="/admin" />' style="display: inline">Вернуться</a>
        </p>
    </section>
</form>
</body>
