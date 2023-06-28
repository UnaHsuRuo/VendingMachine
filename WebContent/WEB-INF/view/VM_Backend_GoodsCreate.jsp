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
div.center {
	text-align: center;
}
</style>
<script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
<script>
        $(document).ready(function() {
            $('#createGoodsForm').submit(function(e) {
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
                        var createMsg = response.createMsg;

                        $('#msg').text(msg);
                        $('#createMsg').text(createMsg);
                        $('#createGoodsForm')[0].reset(); // 清除表單
                    },
                    error: function(xhr, status, error) {
                        console.log(error);
                    }
                });
            });
            <% session.removeAttribute("msg"); %>
			<% session.removeAttribute("createMsg"); %>
        });
    </script>
</head>
<body>
	<div class="center">
		<%@ include file="Backend_Menu.jsp"%>
		<br /> <br />
		<h2>商品新增上架</h2>
		<br />
		<div style="margin-left: 25px;">
			<p style="color: blue;">
				<span id="msg">${msg}</span> <span id="createMsg">${createMsg}</span>
			</p>
			
			<form id="createGoodsForm"
				action="BackendAction.do?action=createGoods"
				enctype="multipart/form-data" method="post">
				<p>
					飲料名稱： <input type="text" name="goodsName" size="10" />
				</p>
				<p>
					設定價格： <input type="number" name="goodsPrice" size="5" value="0"
						min="0" max="1000" />
				</p>
				<p>
					初始數量： <input type="number" name="goodsQuantity" size="5" value="0"
						min="0" max="1000" />
				</p>
				<p>
					商品圖片： <input type="file" name="goodsImage" />
				</p>
				<p>
					商品狀態： <select name="status">
						<option value="1">上架</option>
						<option value="0">下架</option>
					</select>
				</p>
				<p>
					<input type="submit" value="新增">
				</p>
			</form>
		</div>
	</div>
	</form>
	</div>
</body>
</div>
</html>
