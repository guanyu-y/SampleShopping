<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>商品一覧</title>
</head>
<body>
<jsp:include page="/menu.jsp" /><hr>
<h3>商品一覧</h3>

<c:if test="${empty cart.items}">
現在、カートは空です
</c:if>
<hr>

<c:forEach items="${items}" var="item">

	<form action="/shopping/CartServlet?action=add" method="post">
		<input type="hidden" name="item_code" value="${item.code}">

	<table border="0">
	<tr><td rowspan="4"><img src="${item.url}" alt="${item.name}" title="${item.name}" width="150"></td>
		<td align="left">　　商品番号:<b>${item.code}</b></td></tr>
	<tr><td align="left">　　商品名:<b>${item.name}</b></td></tr>
	<tr><td align="left">　　価格(税込):<b>${item.currency}</b></td></tr>
	<tr><td align="left">　　個数:<select name="quantity">
		<option value="1">1
		<option value="2">2
		<option value="3">3
		<option value="4">4
		<option value="5">5
		</select>
		個 <input type="submit" value="カートに追加"></td></tr>
	</table>
	<hr>
	</form>
</c:forEach>


</body>
</html>