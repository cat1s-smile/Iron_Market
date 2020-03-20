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
			<div class="entities">
				<form action='<c:url value="admin" />' style="display: inline">
					<input type="hidden" name="tab" value="products">
					<input type="submit" class="button-menu" value="Товары">
				</form>
				<form action='<c:url value="admin" />' style="display: inline">
					<input type="hidden" name="tab" value="categories">
					<input type="submit" class="button-menu" value="Категории" style="background-color: #1220a7">
				</form>
				<form action='<c:url value="admin" />' style="display: inline">
					<input type="hidden" name="tab" value="import-export">
					<input type="submit" class="button-menu" value="Импорт/Экспорт">
				</form>
			</div>
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
								<input type="submit" value="Delete" class="button-delete" id="${category.idCategory}" ${doLinkedProductsExists != 0 ? "disabled" : ""}>
							</form>
						</td></tr>
				</c:forEach>
			</table>
		</div>
	</section>
</body>
</html>