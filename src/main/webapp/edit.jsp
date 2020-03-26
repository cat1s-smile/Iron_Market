<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page contentType="text/html;charset=utf-8" %>
<!DOCTYPE html>
<head>
    <link rel="stylesheet" type="text/css" href="perform.css">
    <meta charset="UTF-8">
    <title>Редактирование товара</title>
</head>
<body>
<header>
    <div class="upper-container">
        <div class="header">Редактирование товара</div>
    </div>
</header>
<form method="post">
    <section>
        <div class="fields">
            <div class="field">
                <input type="hidden" value="${product.id}" name="id">
                <div class="property-name">Название</div>
                <label>
                    <input name="name" value="${product.name}" class="input-field">
                </label>
            </div>
            <div class="field">
                <div class="property-name">Цена</div>
                <label>
                    <input name="price" value="${product.price}" type="number" min="100" class="input-field">
                </label>
            </div>
            <div class="field">
                <div class="property-name">Количество</div>
                <label>
                    <input name="amount" value="${product.amount}" type="number" min="0" class="input-field">
                </label>
            </div>
            <div class="field">
                <div class="property-name">Описание</div>
                <label>
                    <input name="description" value="${product.description}" class="input-field">
                </label>
            </div>
            <div class="field">
                <div class="property-name">ID Категории</div>
                <label>
                    <input name="idCategory" value="${product.category}" type="number" min="1" class="input-field">
                </label>
            </div>
            <br>
            <input type="submit" class="button" value="Сохранить"/>
        </div>
    </section>
</form>
</body>