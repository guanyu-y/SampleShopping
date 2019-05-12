<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Welcome shoppinig!</title>
</head>
<body>

<jsp:include page="/menu.jsp" /><hr>
<h3>現在のカートの中身</h3>

<c:if test="${empty cart.items}">
現在、カートは空です
</c:if>
<hr><br>

<c:if test="${not empty cart.items}">

	<table style="text-align: center" border="1" align="center">

	<tr><td>商品番号</td><td>商品名</td><td>単価(税込)</td>
		<td>個数</td><td>小計</td><td>削除</td></tr>

	<c:forEach items="${cart.items}" var="item" varStatus="status">


	<tr>
		<td align="center">${item.value.code}</td>
		<td align="center">${item.value.name}</td>
		<td align="right">${item.value.currency}</td>
		<td align="right">${item.value.quantity}</td>
		<td align="right">${item.value.subTotalCurrency}</td>

		<td>

		<form action="/shopping/CartServlet?action=delete" method="post">
			<input type="hidden" name="item_code" value="${item.value.code}">
			<input type="submit" value="削除">
		</form>

		</td>

	</tr>

	</c:forEach>


	<tr><td align="right" colspan="6">総計:<b>${cart.totalCurrency}<b></td></tr>

	</table>

	<form style="text-align: center" action="/shopping/OrderServlet?action=input_customer" method="post">
		<br><br>
		<input  type="submit" value="注文する">
	</form>

</c:if>


</body>
</html>