<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page contentType="text/html;charset=utf-8" %>
<!DOCTYPE html>
<head>
    <link rel="stylesheet" type="text/css" href="perform.css">
    <meta charset="UTF-8">
    <title>Добавление товара</title>
</head>
<body>
<header>
    <div class="upper-container">
        <div class="header">Добавление товара</div>
    </div>
</header>
<form method="post">
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
                <div class="property-name">ID категории</div>
                <label>
                    <input name="idCategory" class="input-field">
                </label>
            </div>
            <br>
            <input type="submit" class="button" value="Сохранить"/>
        </div>
    </section>
</form>
</body>
