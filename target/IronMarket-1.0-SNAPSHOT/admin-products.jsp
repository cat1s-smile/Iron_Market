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
			<ul class="entities">
				<li class="menu-elem">
<%--					<a href='<c:url value="/admin-products" />' type="button">Товары</a>--%>
					<input id= "a-products" class="button-menu" type="button" onclick="window.location.href = '/IronMarket_war_exploded/admin-products';" value="Товары" style="background-color: #1220a7">
				</li>
				<li class="menu-elem">
<%--					<a href='<c:url value="/admin-categories" />' type="button">Категории</a>--%>
					<input id= "a-categories" class="button-menu" type="button" onclick="window.location.href = '/IronMarket_war_exploded/admin-categories';" value="Категории">
				</li>
			</ul>
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
					<tr ${product.status == 0 ? "style=\"border: 2px solid red;\"" : ""}>
						<td>${product.idProduct}</td>
						<td>${product.name}</td>
						<td>${product.price}</td>
						<td>${product.amount}</td>
						<td>${product.description}</td>
						<td>${product.getCategory(product.idCategory)}</td>
						<td>${product.status}</td>
						<td>
							<a href='<c:url value="/edit?id=${product.idProduct}" />' class="button-edit">Edit</a> |
							<form method="post" action='<c:url value="/archive" />' style="display:inline;">
								<input type="hidden" name="id" value="${product.idProduct}">
								<input type="submit" class="button-delete" value=${product.status == 1 ? "Archive" : "DeArchive"}>
							</form>
							<form method="post" action='<c:url value="/delete" />' style="display:inline;">
								<input type="hidden" name="id" value="${product.idProduct}">
								<input type="submit" value="Delete" class="button-delete">
							</form>
						</td></tr>
				</c:forEach>
			</table>
		</div>
	</section>
</body>