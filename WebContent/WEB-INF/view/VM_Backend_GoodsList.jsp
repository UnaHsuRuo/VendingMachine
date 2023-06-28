<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Language" content="zh-tw">
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>販賣機-後臺</title>

<style>
div.center {text-align: center; }

ul.pagination {
    display: inline-block;
    padding: 0;
    margin: 0;
}

ul.pagination li {display: inline;}

ul.pagination li a {
    color: black;
    float: left;
    padding: 10px 15px;
    text-decoration: none;
    border-radius: 60px;
}

ul.pagination li a.active {
	background-color: #FFC0CB;
	color: white;
	border-radius: 60px;
}

ul.pagination li a:hover:not(.active) {background-color: #ddd;}


</style>
</head>

<body>
<div class="center">
	<%@ include file="Backend_Menu.jsp" %>
	<br/>
	<h2>商品列表</h2><br/>
	
		<table border="1" style="margin: 0 auto;">
			<form action="BackendAction.do" method="get">
				<input type="hidden" name="action" value="queryGoods" /> <input
					type="hidden" name="pageNo" value="1" />
				<tbody>
					<tr height="50" align="center">
						<td width="150"><b>商品編號</b></td>
						<td width="150"><b>商品名稱</b></td>
						<td width="100"><b>商品價格</b></td>
						<td width="100"><b>現有庫存</b></td>
						<td width="100"><b>商品狀態</b></td>
					</tr>
					<c:forEach items="${goods}" var="good" varStatus="loop">
						<c:if
							test="${loop.index ge (pageNo * 10)-10 and loop.index le (pageNo * 10)-1}">
							<tr height="30" align="center">
								<td>${good.goodsID}</td>
								<td>${good.goodsName}</td>
								<td>${good.goodsPrice}</td>
								<td>${good.goodsQuantity}</td>
								<c:if test="${good.status==1}">
									<td style="color: blue;">上架</td>
								</c:if>
								<c:if test="${good.status==0}">
									<td style="color: red;">下架</td>
								</c:if>
							</tr>
						</c:if>
					</c:forEach>
				</tbody>
		</table>
<br/>
			
			
				<ul class="pagination">
					<li>
					<c:if test="${pageNo > 1}">
					<a href="BackendAction.do?pageNo=${pageNo-1}&amp;action=queryGoods">
						上一頁 
					</a> 
					</c:if>
					
					<c:if test="${numPages%10!=0}">
					<c:forEach begin="${pageNo}" end="${pageNo+2}"
							var="currentPage" varStatus="loop">
					<c:if test="${currentPage <= (numPages/10)+1}">
						<a class="${currentPage == pageNo ? 'active' : ''}"
						    href="BackendAction.do?pageNo=${currentPage}&amp;action=queryGoods">
								${currentPage} 
							</a>
						</c:if>
					</c:forEach> 
					</c:if>
					
					<c:if test="${numPages%10==0}">
					<c:forEach begin="${pageNo}" end="${pageNo+2}"
							var="currentPage" varStatus="loop">
					<c:if test="${currentPage <= (numPages/10)}">
						<a class="${currentPage == pageNo ? 'active' : ''}"
							href="BackendAction.do?pageNo=${currentPage}&amp;action=queryGoods">
								${currentPage} </a>
					</c:if>
					</c:forEach>
					</c:if>
					
					<c:if test="${pageNo < (numPages/10)}">
 						<a href="BackendAction.do?pageNo=${pageNo+1}&amp;action=queryGoods">
							下一頁 
						</a>
					</c:if>
					</li>
				</ul>
				
</div>
</body>

</html>