<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page contentType="text/html;charset=utf-8" %>
<!DOCTYPE html>
<html>
<head>
	<link rel="stylesheet" type="text/css" href="admin.css">
	<meta charset="UTF-8">
	<title>Магазин комплектующих компьютеров</title>
</head>
<body>
	<header>
		<div class="upper-container">
			<div class="name-admin">Администраторская панель</div>
			<ul class="entities">
				<li class="menu-elem">
					<%--					<a href='<c:url value="/admin-products" />' type="button">Товары</a>--%>
					<input id= "a-products" class="button-menu" type="button" onclick="window.location.href = '/IronMarket_war_exploded/admin-products';" value="Товары">
				</li>
				<li class="menu-elem">
					<%--					<a href='<c:url value="/admin-categories" />' type="button">Категории</a>--%>
					<input id= "a-categories" class="button-menu" type="button" onclick="window.location.href = '/IronMarket_war_exploded/admin-categories';" value="Категории">
				</li>
				<li class="menu-elem">
					<%--					<a href='<c:url value="/admin-orders" />' type="button">Заказы</a>--%>
					<input id= "a-orders" class="button-menu" type="button" onclick="window.location.href = '/IronMarket_war_exploded/admin-orders';" value="Заказы" style="background-color: #1220a7">
				</li>
			</ul>
		</div>
	</header>
	<section>
		<div class="container-table">			
			<table class="table-orders">
				<tr>
					<th>ID</th>
					<th>Пользователь</th>
					<th>Статус</th>
				</tr>
				<c:forEach var="order" items="${orders}">
					<tr><td>${order.idOrder}</td>
						<td>${order.idUser}</td>
						<td>${order.status}</td>
						</tr>
				</c:forEach>
			</table>
		</div>
	</section>
</body>
</html>