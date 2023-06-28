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
<script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
<meta name="viewport" content="width=device-width, initial-scale=1.0">
  <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css">
  <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.4.1/jquery.min.js"></script>
  <script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.14.7/umd/popper.min.js"></script>
  <script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.3.1/js/bootstrap.min.js"></script>
<script>

function addCartGoods(goodsID,buyQuantityIdx){
	var buyQuantity = document.getElementsByName("buyQuantitys")[buyQuantityIdx].value;
	$.ajax({
		url:'MemberAction.do?action=addCartGoods',
		type:'POST',
		data:{goodsID,buyQuantity},
		dataType:'JSON',
		success:function(jsonObject){
			if(jsonObject.message != null){
				alert(jsonObject.message);
			}else{
				alert('已將商品加入購物車!'+
						  '\n\n商品名稱:'+jsonObject.goodsName+
						  '\n購買數量:'+jsonObject.buyQuantity+'罐'+
						  '\n購買單價:NT$'+jsonObject.goodsPrice+'/罐'
						  );
			}
				// 刷新頁面
				location.reload();
			}
			
			});
		}
		
function clearCartGoods(){
	console.log("clear cart goods");
	$.ajax({
		url:'MemberAction.do?action=clearCartGoods',
		type:'POST',
		success:function(){
			$('#close').click();
			// 刷新頁面
			location.reload();
			}
			});
		}



</script>
</head>
<body align="center">
	<table width="1000" height="400" align="center">
		<tr>
			<td colspan="2" align="right">
				<form action="FrontendAction.do" method="get">
					<input type="hidden" name="action" value="searchGoods" /> 
					<input type="hidden" name="pageNo" value="1" /> 
					<input type="text" name="searchKeyword" id="searchKeyword" value="${searchKeyword}" />&nbsp;
					<button class="btn btn-secondary btn-sm" type="submit">商品搜尋</button>
					<button type="button" class="btn btn-secondary btn-sm" data-toggle="modal" data-target="#cartModal">
   						 購物車
  					</button>
				</form>
			</td>
		</tr>
		<tr>
			<td width="400" height="200" align="center">
			<form action="FrontendAction.do" method="post">
					<input type="hidden" name="action" value="buyGoods" /> 
					<input type="hidden" name="pageNo" value="1" /> 
					<input type="hidden" name="customerID" value="${sessionScope.member.identificationNo}"/>
					<img border="0" src="DrinksImage/coffee.jpg" width="200" height="200">
					<h1>歡迎光臨，${sessionScope.member.customerName}</h1>
					<a class="btn btn-warning" href="BackendAction.do?action=queryGoods&amp;pageNo=1" align="left">後臺頁面</a>&nbsp;
					<a class="btn btn-warning" href="LoginAction.do?action=logout" align="left">登出</a>
					<br /><br />
					<font face="微軟正黑體" size="4"> 
						<b>投入:</b> 
						<input type="number" name="inputMoney" max="100000" min="0" size="5" value="0"> <b>元</b> &nbsp; 
						<button type="submit" class="btn btn-secondary btn-sm">送出</button>	<br /><br />
					</font> </form>
					<c:if test="${not empty sessionScope.buyGoodsRtn}">
						<div
							style="border-width: 3px; border-style: dashed; border-color: #FFAC55; padding: 5px; width: 300px;">
							<p style="color: blue;">~~~~~~~ 消費明細 ~~~~~~~</p>
							<p style="margin: 10px;">投入金額：${buyGoodsRtn.inputMoney}</p>
							<p style="margin: 10px;">購買金額：${buyGoodsRtn.buyAmount}</p>
							<p style="margin: 10px;">找零金額：${buyGoodsRtn.change}</p>
							<c:if test="${not empty sessionScope.buyGoodsRtn}">
								<font style="margin: 10px;"> 
								<c:forEach var="shoppingCartGoods" items="${buyGoodsRtn.shoppingCartGoods}">
	                            ${shoppingCartGoods.key.goodsName} ${shoppingCartGoods.key.goodsPrice} * ${shoppingCartGoods.value}
	                            </br>
								</c:forEach>
								<% 
									session.removeAttribute("buyGoodsRtn");
									session.removeAttribute("shoppingCartGoods");
								%>
								</font>
							</c:if>
						</div>
					</c:if>
			</td>
			
			<td width="900" height="400">
				<table border="1" style="border-collapse: collapse">
					<c:forEach var="good" items="${goods}" varStatus="status">
						<c:if test="${status.index eq 0 or status.index eq 3}">
							<tr>
						</c:if>
						<td align="center" width="300">
							<font face="微軟正黑體" size="4">${good.goodsName}</font><br /> 
							<font face="微軟正黑體" size="3" style="color: gray;">${good.goodsPrice} 元/罐</font><br />
							<img border="0" src="DrinksImage/${good.goodsImageName}" width="125" height="125"><br /> 
							<font face="微軟正黑體" size="3"> 
								<input type="hidden" name="goodsIDs" value="${good.goodsID}">
								<!-- 設定最多不能買大於庫存數量 --> 
								購買<input type="number" name="buyQuantitys" min="0" max="30" size="5" value="0">罐 
								<br /><br />
								<button value="${good.goodsID}" class="btn btn-secondary btn-sm" onClick="addCartGoods(${good.goodsID},${status.index})">加入購物車</button>
								<!-- 顯示庫存數量 --> <br />
								<p style="color: red;">(庫存 ${good.goodsQuantity} 罐)</p>
							</font>
						</td>
						<c:if test="${status.index eq 2 or status.index eq 5}">
							</tr>
						</c:if>
					</c:forEach>
				</table>
			</td>
			
		</tr>
		<tr>
			<td colspan="2" align="right">
				<ul class="pagination">
					<li>
						<c:if test="${pageNo > 1}">
							<a href="FrontendAction.do?pageNo=${pageNo-1}&amp;action=searchGoods&amp;searchKeyword=${searchKeyword}">上一頁</a>
						</c:if> 
						<c:if test="${numPages%6!=0}">
							<c:forEach begin="${pageNo}" end="${pageNo+2}" var="currentPage" varStatus="loop">
								<c:if test="${currentPage <= (numPages/6)+1}">
									<a class="${currentPage == pageNo ? 'active' : ''}" href="FrontendAction.do?pageNo=${currentPage}&amp;action=searchGoods&amp;searchKeyword=${searchKeyword}">${currentPage}</a>
								</c:if>
							</c:forEach>
						</c:if>
						<c:if test="${numPages%6==0}">
							<c:forEach begin="${pageNo}" end="${pageNo+2}" var="currentPage" varStatus="loop">
								<c:if test="${currentPage <= (numPages/6)}">
									<a class="${currentPage == pageNo ? 'active' : ''}" href="FrontendAction.do?pageNo=${currentPage}&amp;action=searchGoods&amp;searchKeyword=${searchKeyword}">${currentPage}</a>
								</c:if>
							</c:forEach>
						</c:if>
						<c:if test="${pageNo < (numPages/6)}">
							<a href="FrontendAction.do?pageNo=${pageNo+1}&amp;action=searchGoods&amp;searchKeyword=${searchKeyword}">下一頁</a>
						</c:if>
					</li>
				</ul>
			</td>
		</tr>
	</table>

<div class="modal fade" id="cartModal" tabindex="-1" role="dialog" aria-labelledby="cartModalLabel"
  aria-hidden="true">
  <div class="modal-dialog modal-dialog-centered modal-lg" role="document">
    <div class="modal-content">
      <div class="modal-header">
        <h5 class="modal-title" id="cartModalLabel">購物車</h5>
        <button type="button" class="close" data-dismiss="modal" aria-label="Close">
          <span aria-hidden="true">&times;</span>
        </button>
      </div>
      <div class="modal-body">
      	<div class="cartItems">
 		<c:forEach var="entry" items="${sessionScope.shoppingCartGoods}">
 					<c:set var="goods" value="${entry.key}" />
            <c:set var="quantity" value="${entry.value}" />
            <div class="cartItem">
              <h6>${goods.goodsName}</h6>
              <img src="DrinksImage/${goods.goodsImageName}" alt="${goods.goodsName}" width="100" height="100">
              <p>購買數量: ${quantity}</p>
              <p>商品價格: NT$${goods.goodsPrice}/罐</p>
            </div>
			</c:forEach>
		</div>
      </div>
      <div class="modal-footer">
      	<button type="button" class="btn btn-danger" onclick="clearCartGoods()">清空購物車</button>
        <button id="close" type="button" class="btn btn-secondary" data-dismiss="modal">Close</button>
      </div>
    </div>
  </div>
</div>


</body>
</html>
