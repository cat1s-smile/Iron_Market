<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page contentType="text/html;charset=utf-8" %>
<!DOCTYPE html>
<head>
    <link rel="stylesheet" type="text/css" href="admin.css">
    <meta charset="UTF-8">
    <title>Магазин комплектующих компьютеров</title>
</head>
<body>
<header>
    <div class="upper-container">
        <div class="name-admin">Администраторская панель</div>
        <div class="entities">
            <form action='<c:url value="admin" />' style="display: inline">
                <input type="hidden" name="tab" value="products">
                <input type="submit" class="button-menu" value="Товары" style="background-color: #1220a7">
            </form>
            <form action='<c:url value="admin" />' style="display: inline">
                <input type="hidden" name="tab" value="categories">
                <input type="submit" class="button-menu" value="Категории">
            </form>
            <form action='<c:url value="admin" />' style="display: inline">
                <input type="hidden" name="tab" value="import">
                <input type="submit" class="button-menu" value="Импорт">
            </form>
            <form action='<c:url value="admin" />' style="display: inline">
                <input type="hidden" name="tab" value="export">
                <input type="submit" class="button-menu" value="Экспорт">
            </form>
        </div>
    </div>
</header>
<section>
    <div class="container-table">
        <table class="table-product">
            <tr>
                <th>ID</th>
                <th>Наименование</th>
                <th>Цена</th>
                <th>Наличие</th>
                <th style="width: 300px">Описание</th>
                <th style="width: 10px">Категория</th>
                <th>Статус</th>
                <th style="width: 150px">
                    <p><a href='<c:url value="/create" />' class="button-create">Create new</a></p>
                </th>
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
                    <td  ${product.status == 0 ? "style=\"background: #dfdfdf;\"" : ""}>
                        <a href='<c:url value="/edit?id=${product.idProduct}" />' class="button-edit">Edit</a> |
                        <form method="post" action='<c:url value="/archive" />' style="display:inline;">
                            <input type="hidden" name="id" value="${product.idProduct}">
                            <input type="submit" class="button-delete"
                                   value=${product.status == 1 ? "Archive" : "DeArchive"}>
                        </form>
                        <form method="post" action='<c:url value="/delete" />' style="display:inline;">
                            <input type="hidden" name="id" value="${product.idProduct}">
                            <input type="submit" value="Delete" class="button-delete">
                        </form>
                    </td>
                </tr>
            </c:forEach>
        </table>
    </div>
</section>
</body>