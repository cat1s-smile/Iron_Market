<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page contentType="text/html;charset=utf-8" %>
<!DOCTYPE html>
<head>
    <link rel="stylesheet" type="text/css" href="admin.css">
    <meta charset="UTF-8">
    <script type="text/javascript" src="checkboxes.js"></script>
    <title>Магазин комплектующих компьютеров</title>
</head>
<body>
<section>
    <div class="container-table">
        <form method="post" action='<c:url value="/changesPreview" />'>
            <table class="table-product">
                <tr>
                    <th><input type="checkbox" class="check" name="checkAllB" onclick="checkAll(this)"/></th>
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
                        <td ${product.status == 0 ? "style=\"background: #dfdfdf;\"" : ""}>
                            <input type="checkbox" class="check" name="checkedId" value="${product.id}"
                                   onclick="checkOne(this)"
                            <c:forEach var="checkedProduct" items="${checkedProducts}">
                                ${product.id == checkedProduct.id ? "checked" : ""}
                            </c:forEach>>
                        </td>
                        <td ${product.status == 0 ? "style=\"background: #dfdfdf;\"" : ""}>${product.id}</td>
                        <td ${product.status == 0 ? "style=\"background: #dfdfdf;\"" : ""}>${product.name}</td>
                        <td ${product.status == 0 ? "style=\"background: #dfdfdf;\"" : ""}>${product.price}</td>
                        <td ${product.status == 0 ? "style=\"background: #dfdfdf;\"" : ""}>${product.amount}</td>
                        <td ${product.status == 0 ? "style=\"background: #dfdfdf;\"" : ""}>${product.description}</td>
                        <td ${product.status == 0 ? "style=\"background: #dfdfdf;\"" : ""}>${product.categoryName}</td>
                        <td ${product.status == 0 ? "style=\"background: #dfdfdf;\"" : ""}>${product.status}</td>
                        <td  ${product.status == 0 ? "style=\"background: #dfdfdf;\"" : ""}></td>
                    </tr>
                </c:forEach>
            </table>
                <input type="hidden" name="mode" value="${mode}">
                <input type="submit" class="button-confirm" value="Продолжить">
            </form>
    </div>
</section>
</body>