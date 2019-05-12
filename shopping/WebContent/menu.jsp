<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<a href="/shopping/ShowItemServlet?action=top">トップページ</a>
<br>
カテゴリ:
<c:forEach items="${categories}" var="category">
<a href="/shopping/ShowItemServlet?action=list&code=${category.code}">${category.name}</a>
</c:forEach>
[ <a href="/shopping/CartServlet?action=show">カートを見る</a> ]
