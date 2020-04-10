<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page contentType="text/html;charset=utf-8" %>
<!DOCTYPE html>
<html>
<head>
    <link rel="stylesheet" type="text/css" href="styles/cart.css">
    <title>История</title>
</head>
<body>

<header>
    <div class="upper-container">
        <div style="display: inline" class="basket-name">История заказов</div>
        <a href='<c:url value="cart" />' style="display: inline" class="button-basket">Корзина</a>
        <a href='<c:url value="user" />' style="display: inline" class="button-basket">Назад в магазин</a>
        <div class="line"></div>
    </div>
</header>

<section>
    <div class="container">
        <div class="list-text">Совершенные заказы</div>
        <div class="hist-container-products">
            <ul class="products">
                <c:forEach var="order" items="${orders}">
                    <li>
                        <div class="container-product">
                            <div class="product">
                                <p>id заказа: ${order.orderID}</p>
                                <c:forEach var = "item" items="${order.content}">
                                    <p> ${item}</p>
                                </c:forEach>
                                <p>Стоимость: ${order.totalCost}р.</p>
                                <p></p>
                            </div>
                        </div>
                    </li>
                </c:forEach>
            </ul>
        </div>
    </div>
</section>
</body>
</html>