<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page contentType="text/html;charset=utf-8" %>
<!DOCTYPE html>
<head>
    <link rel="stylesheet" type="text/css" href="admin.css">
    <meta charset="UTF-8">
    <title>Магазин комплектующих компьютеров</title>
</head>
<body>
<section>
    <div class="container-table">
        <form method="post" action='<c:url value="/changesPreview" />'>
            <table class="table-product">
                <tr>
                    <th>ID</th>
                    <th>Наименование</th>
                    <th>Цена</th>
                    <th>Наличие</th>
                    <th style="width: 300px">Описание</th>
                    <th style="width: 10px">Категория</th>
                    <th>Статус</th>
                </tr>
                <c:forEach var="product" items="${products}">
                    <tr>
                        <td ${product.status == 0 ? "style=\"background: #dfdfdf;\"" : ""}>${product.idProduct}</td>
                        <td ${product.status == 0 ? "style=\"background: #dfdfdf;\"" : ""}>${product.name}</td>
                        <td ${product.status == 0 ? "style=\"background: #dfdfdf;\"" : ""}>${product.price}</td>
                        <td ${product.status == 0 ? "style=\"background: #dfdfdf;\"" : ""}>${product.amount}</td>
                        <td ${product.status == 0 ? "style=\"background: #dfdfdf;\"" : ""}>${product.description}</td>
                        <td ${product.status == 0 ? "style=\"background: #dfdfdf;\"" : ""}>${product.idCategory}</td>
                        <td ${product.status == 0 ? "style=\"background: #dfdfdf;\"" : ""}>${product.status}</td>
                        <td  ${product.status == 0 ? "style=\"background: #dfdfdf;\"" : ""}></td>
                    </tr>
                    <input type="hidden" name="checkedId" value="${product.idProduct}">
                </c:forEach>
            </table>
            <input type="hidden" name="mode" value="${mode}">
            <input type="submit" class="button" value="Продолжить">
        </form>
        <form method="post" action='<c:url value="/changesPreview" />'>
            <c:forEach var="product" items="${products}">
                <input type="hidden" name="checkedId" value="${product.idProduct}">
            </c:forEach>
            <input type="hidden" name="mode" value="back">
            <input type="submit" class="button" value="Назад">
        </form>
    </div>
</section>
</body>