<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page contentType="text/html;charset=utf-8" %>
<!DOCTYPE html>
<html>
<head>
    <link rel="stylesheet" type="text/css" href="user3.css">
    <link rel="stylesheet" type="text/css" href="popup.css">
    <title>Магазин комплектующих компьютеров</title>
</head>
<body>

<header>
    <div class="upper-container">
        <div class="name-shop">Магазин</div>
        <form action='<c:url value="/search" />' style="display: inline">
            <input type="hidden" value="${catID}" name="catID"/>
            <input type="hidden" value="${cart}" name="cart"/>
            <input type="text" name="search" class="search" placeholder="Поиск товара" value=${req} >
            <input type="submit" class="button-search" value="Поиск">
        </form>
        <%--        <a href='<c:url value="cart" />' class="button-basket">Корзина</a>--%>
        <input id="cart" class="button-basket" type="button" onclick="window.location.href = '/IronMarket_war_exploded/cart';"
               value="Корзина">
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
                    <%--                    <a href='<c:url value="/user" />' type="button" class="button-category-all">Все</a>--%>
                    <form method="get" action='<c:url value="/user" />'>
                        <input class="button-category" type="submit" value="Все"
                               id="-1"
                        ${catID == -1 ? "style=\"background-color: #3c87b5;\"" : ""}
                        >
                    </form>
                </li>
                <c:forEach var="category" items="${categories}">
                    <li>
                        <form method="get" action='<c:url value="/category" />'>
                            <input type="hidden" value="${category.idCategory}" name="id"/>
                            <input class="button-category" type="submit" value="${category.name}"
                                   id="${category.idCategory}"
                                ${category.idCategory == catID ? "style=\"background-color: #3c87b5;\"" : ""}
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
                                    <input type="hidden" name="id" value="${product.idProduct}">
                                    <input class="button-product" id="${product.idProduct}" type="submit" value="О товаре">
<%--                                    onsubmit="document.getElementById('overlay').style.display = 'block' ">--%>
                                </form>

                                <form method="post" action='<c:url value="/buy" />' style="display:inline;">
                                    <input type="hidden" name="id" value="${product.idProduct}">
                                    <input class="button-product" type="submit" value="Добавить в корзину"}>
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
                    <div class="popup-close" onclick="document.getElementById('overlay').style.display = 'none' ">&times;</div>
                </div>
                <p class="popup-text">Product code: ${product.idProduct}</p>
                <p class="popup-text">Category: ${product.idCategory}</p>
                <p class="popup-text">Description: ${product.description}</p>
                <p class="popup-text">Amount on store: ${product.amount}</p>
                <p class="popup-text">Price: ${product.price}</p>
            </div>
        </div>
    </div>
</section>
</body>
</html>