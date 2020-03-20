<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page contentType="text/html;charset=utf-8" %>
<!DOCTYPE html>
<html>
<head>
    <link rel="stylesheet" type="text/css" href="cart.css">
    <link rel="stylesheet" type="text/css" href="popup.css">
    <title>Корзина</title>
</head>
<body>

<header>
    <div class="upper-container">
        <div style="display: inline; margin-left: 10px" class="basket-name">Корзина</div>
        <a href='<c:url value="history" />' style="display: inline" class="button-basket">История заказов</a>
        <a href='<c:url value="user" />' style="display: inline" class="button-basket">Назад в магазин</a>
        <div class="line"></div>
    </div>
</header>

<section>
    <div class="container">
        <div class="list-text">Выбранные товары:</div>
        <div class="container-products">
            <ul class="products">
                <c:forEach var="cartItem" items="${cart}">
                    <li>
                        <div class="cart-product" ${cartItem.status == 0 ? "style=\"border: 2px solid red;\"" : ""}>
                            <p>${cartItem.product.name}</p>
                            <p>Цена: ${cartItem.product.price}</p>
                            <p>Наличие: ${cartItem.product.status == 0 ? " НЕ ДОСТУПНО" : cartItem.product.amount}</p>
                            <p style="display: inline; margin-left: 5px">Выбрано: ${cartItem.number}</p>
                            <div class="buttons-count">
                                <form method="post" action='<c:url value="/remove_from_cart" />'
                                      style="display:inline;">
                                    <input type="hidden" name="id" value="${cartItem.product.idProduct}">
                                    <input type="hidden" name="todo" value="-">
                                    <input type="submit" value="-"
                                           style="display:inline; height: 15px; padding-top: 0;">
                                </form>
                                <form method="post" action='<c:url value="/remove_from_cart" />'
                                      style="display:inline;">
                                    <input type="hidden" name="id" value="${cartItem.product.idProduct}">
                                    <input type="hidden" name="todo" value="+">
                                    <input type="submit" value="+" style="display:inline;height: 15px; padding-top: 0">
                                </form>
                            </div>
                            <form method="post" action='<c:url value="/remove_from_cart" />'>
                                <input type="hidden" name="id" value="${cartItem.product.idProduct}">
                                <input type="hidden" name="todo" value="remove">
                                <input type="submit" class="delete" value="Удалить" style="display:inline;">
                            </form>
                        </div>
                    </li>
                </c:forEach>
            </ul>
        </div>
        <div class="container-info">
            <div class="info-total-order">
                <div class="text-total">Сумма заказа</div>
                <div class="price-total">${cost}</div>
            </div>
            <form method="post" action='<c:url value="/confirm_order" />'>
                <input type="submit" value="Оформить" class="confirm" ${correct == 0 ? "disabled" : ""}>
            </form>
        </div>

<%--        <div class="overlay">--%>
<%--            <div class="popup">--%>
<%--                <div class="popup-header">--%>
<%--                    <h2>Ошибка оформления</h2>--%>
<%--                    <div class="popup-close">&times;</div>--%>
<%--                </div>--%>
<%--                <h2>Недостаточно товаров:</h2>--%>
<%--                <ul class="products-lack">--%>

<%--                </ul>--%>
<%--            </div>--%>
<%--        </div>--%>
    </div>
</section>
</body>
</html>