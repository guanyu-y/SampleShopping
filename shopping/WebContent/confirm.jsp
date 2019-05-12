<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Welcome shopping!</title>
<style type="text/css">
h4 {text-align: center}
</style>
</head>
<body>

<jsp:include page="/menu.jsp" /><hr>
<h3>下記の内容で注文を行いますか？</h3>
<hr>
<h4>ご注文商品</h4>
<c:if test="${not empty cart.items}">

	<table style="text-align: center" border="1" align="center">
	<tr><td>商品番号</td><td>商品名</td><td>単価(税込)</td>
		<td>個数</td><td>小計</td></tr>


	<c:forEach items="${cart.items}" var="item">

	<tr>
		<td align="center">${item.value.code}</td>
		<td align="center">${item.value.name}</td>
		<td align="right">${item.value.currency}</td>
		<td align="right">${item.value.quantity}</td>
		<td align="right">${item.value.subTotalCurrency}</td>

	</tr>

	</c:forEach>

	<tr><td align="right" colspan="6">総計:<b>${cart.totalCurrency}</b></td></tr>
	</table>

	<h4>お客様情報</h4>

	<form action="/shopping/OrderServlet?action=order" method="post">
		<table border="1" align="center">
		<tr>
		<td>お名前</td><td width="173">${customer.name}</td>
		</tr>
		<tr>
		<td>住所</td><td>${customer.address}</td>
		</tr>
		<tr>
		<td>電話番号</td><td>${customer.tel}</td>
		</tr>
		<tr>
		<td>e-mail</td><td>${customer.email}</td>
		</tr>
		</table>
		<br>
		<center>
		<input type="submit" value="この内容で注文">
		</center>
	</form>

</c:if>

</body>
</html>