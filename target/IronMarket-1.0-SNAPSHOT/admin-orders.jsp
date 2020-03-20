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
					<input type="submit" class="button-menu" value="Товары" style="background-color: #1220a7">
				</form>
				<form action='<c:url value="admin" />' style="display: inline">
					<input type="hidden" name="tab" value="categories">
					<input type="submit" class="button-menu" value="Категории">
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