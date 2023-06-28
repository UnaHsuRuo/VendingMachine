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
</style>
<script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
<script type="text/javascript">
    $(document).ready(function() {
    	$('#goodsID').change(function() {
    	    var goodsID = $(this).val();
    	if (goodsID !== '') {
				$.ajax({
				  url: 'BackendAction.do?action=getModifyGoods', // 指定要進行呼叫的位址
				  type: "GET", // 請求方式 POST/GET
				  data: {goodsID: goodsID}, // 傳送至 Server的請求資料(物件型式則為 Key/Value pairs)
				  dataType : 'JSON', // Server回傳的資料類型
				  success: function(response) { // 請求成功時執行函式
					$('#goodsPrice').val(response.goodsPrice);
	    	        $('#goodsQuantity').text(response.goodsQuantity);
				  },
				  error: function(error) { // 請求發生錯誤時執行函式
				  	alert("Ajax Error!");
				  }
				});
			}else{
				$('#goodsPrice').val('');
				$('#goodsQuantity').text('');
			}
    	    
    	});

        $('#modifyGoodsForm').submit(function(e) {
                e.preventDefault(); // 防止表單提交
                var form = $(this);
                var url = form.attr('action');

                $.ajax({
                    type: 'POST',
                    url: url,
                    data: new FormData(form[0]),
                    processData: false,
                    contentType: false,
                    success: function(response) {
                        var msg = response.msg;
                        var modifyGoodsID = response.modifyGoodsID;
                        var modifyMSG = response.modifyMSG;
                        $('#msg').text(msg);
                        $('#modifyGoodsID').text(modifyGoodsID);
                        $('#modifyMSG').text(modifyMSG);
                        $('#modifyGoodsForm')[0].reset(); // 清除表單
                        var modifyGoodsID = response.modifyGoodsID;
                        $('select#goodsID').val(modifyGoodsID); // 更新 selected option
                        $('#goodsPrice').val(response.goodsPrice); // 更新商品價格
                        $('#goodsQuantity').val(response.goodsQuantity); // 新增這行
                        
                    },
                    error: function(xhr, status, error) {
                        console.log(error);
                    }
                });
            });
    });
</script>
</head>
<div class="center">
<body>
	<%@ include file="Backend_Menu.jsp" %>
	<br/><br/>
	<h2>商品補貨作業</h2><br/>
	<div style="margin-left:25px;">
	<p style="color:blue;">
	<span id="msg">${msg}</span>
	<span id="modifyGoodsID">${modifyGoodsID}</span>
	<span id="modifyMSG">${modifyMSG}</span>
	</p>

	<form id="modifyGoodsForm" action="BackendAction.do?action=modifyGoods"
			enctype="multipart/form-data" method="post">
		<p>
			飲料名稱：
			<select id="goodsID" name="goodsID">
			<option value="">----- 請選擇 -----</option>
				<c:forEach items="${goods}" var="good">
				<option value="${good.goodsID}" <c:if test="${good.goodsID eq modifyGoodsID}">selected</c:if>>
						${good.goodsName}
					</option>
				</c:forEach>
			</select>
		</p>		
		<p>
			更改價格： 
			<input id="goodsPrice" name="goodsPrice" type="number" size="5" min="0" max="1000" value="${goodsPrice}"/>		
		</p>
		<p>
			商品庫存量： <span id="goodsQuantity">${goodsQuantity}</span>
		</p>
		<p>
			補貨數量：
			<input type="number" name="goodsQuantity" size="5" value="0" min="0" max="1000">
		</p>
		<p>
			商品狀態：
			<input type="radio" name="status" value="1" checked>上架
			<input type="radio" name="status" value="0">下架
		</p>
		<p>
			<input type="submit" value="修改">
		</p>
	</form>
	</div>
</body>
</div>
</html>
