<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Test jQuery</title>
</head>
<body>
<div align="center">
	<h2>Hello jQuery!!</h2>
	请输入要查询的商品编号：<input type="text" id="seckill_id" /><br/>
	<button id="search">查询</button>
	<br/>
	<p id="result"></p>
</div>
<script src="http://apps.bdimg.com/libs/jquery/1.11.1/jquery.js"></script>
<script>
$(document).ready(function(){
	$("#search").click(function(){
		var seckill_id = $("#seckill_id").val();
		$.ajax({
			type:"GET",
			url:seckill_id + "/detailJson",
			dataType:"json",
			success:function(data){
				if(data.success){
					$("#result").html(data.data.commodityName);
				}else{
					$("#result").html(data.error);
				}
			}
		});
	});
});
</script>
</body>
</html>