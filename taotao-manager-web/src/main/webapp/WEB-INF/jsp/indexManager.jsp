<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    
    <div>
    	<a href="javascript:void(0)" class="easyui-linkbutton" onclick="importAll()">一键导入商品数据到索引库</a>
    </div>
<script type="text/javascript">
	
	// 通过ajax 发送请求
	function importAll(){
		$.ajax({
			url:"/index/importAll",
			type:"GET",
			success:function(data){
				//TaotaoResult
				if(data.status == 200){
					alert("导入成功");
				}else{
					alert("导入失败");	
				}
			}
		});
	}
	//方法二
	/* function importAll(){
		$.post("/index/importAll", null, function(data){
			if(data.status==200){
				$.messager.alert("导入成功");
			}else{
				$.messager.alert("导入失败");
			}
		});
	} */
</script>