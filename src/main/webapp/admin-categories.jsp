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
					<input id= "a-categories" class="button-menu" type="button" onclick="window.location.href = '/IronMarket_war_exploded/admin-categories';" value="Категории" style="background-color: #1220a7">
				</li>
			</ul>
		</div>
	</header>
	<section>
		<div class="container-table">
			<table class="table-categories">
				<tr>
					<th style="width: 1px">ID</th>
					<th style="width: 50px">Название</th>
					<th style="width: 150px"><p><a href='<c:url value="/createCategory" />' class="button-create">Create new</a></p></th>
				</tr>
				<c:forEach var="category" items="${categories}">
					<tr><td>${category.idCategory}</td>
						<td>${category.name}</td>
						<td>
							<a href='<c:url value="/editCategory?id=${category.idCategory}" />' class="button-edit">Edit</a> |
							<form method="post" action='<c:url value="/deleteCategory" />' style="display:inline;">
								<input type="hidden" name="id" value="${category.idCategory}">
								<input type="submit" value="Delete" class="button-delete" id="${category.idCategory}" ${category.countBoundProducts() != 0 ? "disabled" : ""}>
							</form>
						</td></tr>
				</c:forEach>
			</table>
		</div>
	</section>
</body>
</html>