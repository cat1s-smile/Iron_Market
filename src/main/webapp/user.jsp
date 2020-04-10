<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page contentType="text/html;charset=utf-8" %>
<!DOCTYPE html>
<html>
<head>
    <link rel="stylesheet" type="text/css" href="styles/user.css">
    <link rel="stylesheet" type="text/css" href="styles/popup.css">
    <title>Магазин комплектующих компьютеров</title>
</head>
<body>

<header>
    <div class="upper-container">
        <div class="name-shop">Магазин</div>
        <form action='<c:url value="/user" />' style="display: inline">
            <input type="hidden" value="${catID}" name="catID"/>
            <input type="hidden" value="${cart}" name="cart"/>
            <input type="text" name="search" class="search" placeholder="Поиск товара" value=${search}>
            <input type="submit" class="button-search" value="Поиск">
        </form>
        <form action='<c:url value="/cart" />' style="display: inline; margin-left: 40px">
            <input type="submit" class="button-basket" style="display: inline" value="Корзина">
        </form>
        <div class="text-count-products">Товаров:</div>
        <div class="count-products">${cart}</div>
    </div>
</header>

<section>
    <div class="lower-container">

        <div class="lower-container-categories">
            <div class="text-category">Категории</div>
            <ul class="categories" id="categories">
                <li>
                    <%--                    <a href='<c:url value="/user" />' type="button" class="button-rawCategory-all">Все</a>--%>
                    <form method="get" action='<c:url value="/user" />'>
                        <input class="button-category" type="submit" value="Все"
                               id="-1"
                        ${catID == -1 ? "style=\"background-color: #3c87b5;\"" : ""}
                        >
                    </form>
                </li>
                <c:forEach var="category" items="${categories}">
                    <li>
                        <form method="get" action='<c:url value="/user" />'>
                            <input type="hidden" value="${category.id}" name="catID"/>
                            <input class="button-category" type="submit" value="${category.name}"
                                   id="${category.id}"
                                ${category.id == catID ? "style=\"background-color: #3c87b5;\"" : ""}
                            >
                        </form>
                    </li>
                </c:forEach>
            </ul>
        </div>

        <div class="lower-container-products">
            <div class="text-center">Товары</div>
            <ul class="products">
                <c:forEach var="product" items="${products}">
                    <li>
                        <div class="product">
                            <p>${product.name}</p>
                            <p>Цена: ${product.price}</p>
                            <p>Наличие: ${product.amount}</p>
                            <div class="product-buttons">
                                <form method="get" action='<c:url value="/user" />' style="display:inline;">
                                    <input type="hidden" name="id" value="${product.id}">
                                    <input type="hidden" value="${catID}" name="catID"/>
                                    <input type="hidden" value="${cart}" name="cart"/>
                                    <input type="hidden" value="${search}" name="search"/>
                                    <input class="button-product" id="${product.id}" type="submit"
                                           value="О товаре">
                                        <%--                                    onsubmit="document.getElementById('overlay').style.display = 'block' ">--%>
                                </form>
                                <form method="post" action='<c:url value="/buy" />' style="display:inline;">
                                    <input type="hidden" name="buyID" value="${product.id}">
                                    <input type="hidden" value="${catID}" name="catID"/>
                                    <input type="hidden" value="${cart}" name="cart"/>
                                    <input type="hidden" value="${search}" name="search"/>
                                    <input class="button-product" type="submit" value="Добавить в корзину"
                                        ${product.status == 0 ? "disabled" : ""}>
                                </form>
                            </div>
                        </div>
                    </li>
                </c:forEach>
            </ul>
        </div>

        <div class="overlay" id="overlay" ${overlay == 1 ? "style=\"display: block;\"" : "style=\"display: none;\""}>
            <div class="popup">
                <div class="popup-header">
                    <h2>${product.name}</h2>
                    <div class="popup-close" onclick="document.getElementById('overlay').style.display = 'none' ">
                        &times;
                    </div>
                </div>
                <p class="popup-text">Id товара: ${product.id}</p>
                <p class="popup-text">Категория: ${product.categoryName}</p>
                <p class="popup-text">Описание: ${product.description}</p>
                <p class="popup-text">В наличии: ${product.amount}</p>
                <p class="popup-text">Цена: ${product.price}</p>
            </div>
        </div>
    </div>
</section>
</body>
</html>