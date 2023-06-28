<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link href="./css/VendingMachine.css" rel="stylesheet">
<title>販賣機</title>
</head>
<script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
<script>

</script>

<body align="center">
   <table width="1000" height="400" align="center">
		
		<td colspan="2" align="right">
			<form action="FrontendAction.do" method="get">
				<input type="hidden" name="action" value="searchGoods" /> 
				<input type="hidden" name="pageNo" value="1" /> 
				<input type="text" name="searchKeyword" id="searchKeyword" value="${searchKeyword}" />&nbsp
				<button class="btn btn-secondary btn-sm" type="submit">商品搜尋</button>
			</form>
		</td>
		<tr><tr><tr><tr><tr><tr><tr><tr><tr><tr>
		
			<td width="400" height="200" align="center">
				<form action="FrontendAction.do" method="post">
					<input type="hidden" name="action" value="buyGoods" /> 
					<input type="hidden" name="pageNo" value="1" /> 
					<input type="hidden" name="customerID" value="${sessionScope.member.identificationNo}"/>
					<img border="0" src="DrinksImage/coffee.jpg" width="200" height="200">
					<h1>歡迎光臨，${sessionScope.member.customerName}</h1>
					<a class="btn btn-warning"
						href="BackendAction.do?action=queryGoods&amp;pageNo=1"
						align="left"> 後臺頁面 </a>&nbsp; 
					<a class="btn btn-warning"
						href="LoginAction.do?action=logout" align="left"> 登出 </a> 
						<br /><br /> 
					<font face="微軟正黑體" size="4"> 
					<b>投入:</b> <input type="number" name="inputMoney" max="100000" min="0" size="5"
						value="0"> <b>元</b> &nbsp 
					<input class="btn btn-secondary btn-sm" type="submit" value="送出">
						<br /><br />
					</font>
					<c:if test="${not empty sessionScope.buyGoodsRtn}">
						<div
							style="border-width: 3px; border-style: dashed; border-color: #FFAC55; padding: 5px; width: 300px;">
							<p style="color: blue;">~~~~~~~ 消費明細 ~~~~~~~</p>
							<p style="margin: 10px;">投入金額：${buyGoodsRtn.inputMoney}</p>
							<p style="margin: 10px;">購買金額：${buyGoodsRtn.sumOrderAmount}</p>
							<p style="margin: 10px;">找零金額：${buyGoodsRtn.change}</p>
							<c:if test="${not empty sessionScope.goodsOrders}">
								<font style="margin: 10px;"> 
								<c:forEach var="goodsOrder"
										items="${goodsOrders}">
	                            ${goodsOrder.key.goodsName} ${goodsOrder.key.goodsPrice} * ${goodsOrder.value}
	                            </br>
								</c:forEach>
								</font>
							</c:if>
						</div>
					</c:if>
			</td>

			<td width="600" height="400">
				<table border="1" style="border-collapse: collapse">
					<c:forEach var="good" items="${goods}" begin="${pageNo*6-6}" end="${pageNo*6-1}" varStatus="status">
						<c:if test="${status.first}">
							<tr>
						</c:if>
						<c:if test="${status.count eq 4}">
							</tr>
							<tr>
						</c:if>
						<td width="300">
						<font face="微軟正黑體" size="5"> ${good.goodsName} </font> <br /> 
						<font face="微軟正黑體" size="4" style="color: gray;"> ${good.goodsPrice} 元/罐 </font> <br />
						<img border="0" src=" DrinksImage/${good.goodsImageName}" width="150" height="150"> <br /> 
						<font face="微軟正黑體" size="3"> 
						<input type="hidden" name="goodsIDs" value="${good.goodsID}">
						<!-- 設定最多不能買大於庫存數量 --> 
						購買<input type="number" name="buyQuantitys_${good.goodsID}" min="0" max="30" size="5" value="0">罐 
						<br><br><button name="addToCart" value="${good.goodsID}">加入購物車</button>
						<!-- 顯示庫存數量 --> <br>
						<p style="color: red;">(庫存 ${good.goodsQuantity} 罐)</p>
						</font>
						</td>
						<c:if test="${status.last}">
							</tr>
						</c:if>
					</c:forEach>
				</table>
			</td>
		</tr><tr><tr><tr><tr><tr><tr>
			<td colspan="2" align="right">

				<ul class="pagination">
					<li>
					
					<c:if test="${pageNo > 1}">
						<a
							href="FrontendAction.do?pageNo=${pageNo-1}&amp;action=searchGoods&amp;searchKeyword=${searchKeyword}">
							上一頁 </a>
					</c:if> 
					
					<c:if test="${numPages%6!=0}">
					<c:forEach begin="${pageNo}" end="${pageNo+2}"
							var="currentPage" varStatus="loop">
					<c:if test="${currentPage <= (numPages/6)+1}">
						<a class="${currentPage == pageNo ? 'active' : ''}"
							href="FrontendAction.do?pageNo=${currentPage}&amp;action=searchGoods&amp;searchKeyword=${searchKeyword}">
								${currentPage} </a>
					</c:if>
					</c:forEach>
					</c:if>
					
					<c:if test="${numPages%6==0}">
					<c:forEach begin="${pageNo}" end="${pageNo+2}"
							var="currentPage" varStatus="loop">
					<c:if test="${currentPage <= (numPages/6)}">
						<a class="${currentPage == pageNo ? 'active' : ''}"
							href="FrontendAction.do?pageNo=${currentPage}&amp;action=searchGoods&amp;searchKeyword=${searchKeyword}">
								${currentPage} </a>
					</c:if>
					</c:forEach>
					</c:if>
					
					<c:if test="${pageNo < (numPages/6)}">
						<a href="FrontendAction.do?pageNo=${pageNo+1}&amp;action=searchGoods&amp;searchKeyword=${searchKeyword}">
							下一頁 </a>
					</c:if>
					
					</li>
				</ul>

				</form> 
				<% session.removeAttribute("buyGoodsRtn"); %>
			</td>
		</tr>
	</table>
</body>

</html>
