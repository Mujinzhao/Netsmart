//var iframeObj;
$(function(){
	    // 初始化table 控件数据
	    initable();
		//上一步操作
		$("#previousstep").click(function(){
			// 动态跳转界面
			var iframeObj =  window.parent.document.getElementById("parserconfig");
			var parseurl = "templateconfig.action?jumpage=excelfileds&";
			iframeObj.src=parseurl+$(':input,:checkbox,:radio','#addform').serialize();
		});
		//验证解析规则
		$("#parserrule").click(function(){
				$.ajax({
					url:"checkexcelparser.action",
					type:"POST",
					data:$(':input,:checkbox,:radio','#addform').serialize(),
					success:function(res){
						$("#paserstr").show();
						$("#paserdata").show();
						$("#paserdata").attr("value",res);
					}
				});
			 
		});
		
		// 保存模板信息
		$("#btnadd").click(function(){
			// 获取保存按钮文本信息
			 var paserbut =  $("#btnadd").find("span [class=l-btn-text]").html();
			 var url ="";
			 if(paserbut=="保存解析规则"){
				 //添加
				 url="savexcelparser.action";
			 }else{
				 //修改
				 url="updatexcelparser.action";
			 }
			 ajaxPost(url);
		});
		// 重置
		$("#btncancel").click(function(){
			cleardata();
		});
});

function  initable(){
	myTables.excelstructure.clickRow=function (clickval,rowdata){
		//校验
		if(clickval == "checkexcelparser"){
		}
		if(clickval == "delexcelparser"){
			var delurl = clickval+".action?structid="+rowdata.id;
			var paserbut =  $("#btnadd").find("span [class=l-btn-text]").html();
			if(paserbut=="保存解析规则"){
				if(window.confirm("确定删除信息？")){
					ajaxPost(delurl);
				}
			}else{
				alert("不能删除，信息处理编辑状态！");
			}
		}
		// 修改结构信息
		if(clickval == "updatexcelparser"){
			 $("#btnadd").find("span [class=l-btn-text]").html("修改结构");
			 $("#btncancel").find("span [class=l-btn-text]").html("取消修改");
		     //修改解析规则信息
			 editdata(rowdata);
		}
    };
	 myTables["excelstructure"].formatCol("parsertype",function (coldata,rowdata){
			var parsertypehtml = "前后缀解析";
	        var parsertype = rowdata.parsertype;
	        if(parsertype == "0")
	        {
	        	parsertypehtml="普通解析";
	        }else if(parsertype == "1")
	        {
	        	parsertypehtml="列传行解析";
	        }
		    return parsertypehtml;
		});
}
function  cleardata(){
	$("#btnadd").find("span [class=l-btn-text]").html("保存解析规则");
	$("#btncancel").find("span [class=l-btn-text]").html("重置");
	$("#structid").val("");
	$("#parsesrownum").val("");
	$("#parseerownum").val("");
	$("#parsescellnum").val("");
	$("#parseecellnum").val("");
	$("#parserowtocell").val("");
	$("#paserstr").hide();
	$("#paserdata").hide();
	$("#paserdata").attr("value","");
}
function  editdata(rowdata){
	$("#structid").val(rowdata.id);
	$("#parsesrownum").val(rowdata.parsesrownum);
	$("#parseerownum").val(rowdata.parseerownum);
	$("#parsescellnum").val(rowdata.parsescellnum);
	$("#parseecellnum").val(rowdata.parseecellnum);
	$("#parserowtocell").val(rowdata.parserowtocell);
}

function ajaxPost(url){
	$.ajax({
		url:url,
		type:"POST",
		data:$(':input,:checkbox,:radio','#addform').serialize(),
		success:function(res){
		  refershData("excelstructure");
		}
	});
}
//刷新页面
function refershData(obj){
	myTables[obj].refreshTab();
}