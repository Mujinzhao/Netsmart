$(function(){
	// 初始化table 控件
	 initable();
	 var iframeObj =  window.parent.document.getElementById("pageconfig");
	 //下一步
	 $("#nextpaging").click(function(){
		 $("#hidparam input").attr("disabled",false);
	      iframeObj.src ="gatherpage.action?"+$("#hidparam").find("*").serialize();
	      $("#hidparam input").attr("disabled",true);
	 });
	 $("#addkeywords").click(function (){
		 $("#taskname").attr("disabled",false);
		 if($("#addkeywords").val()=="关键词录入"){
			 ajaxPost("savekeywords.action");
		 }else{
			 ajaxPost("updatekeywords.action");
		 }
		 $("#taskname").attr("disabled",true);
	 });
     $("#rester").click(function (){
    	 $("#id").val("");
    	 $("#keyword").val("");
    	 $("#isspider").attr("checked",false);
    	 $("#addkeywords").attr("value","关键词录入");
		 $("#rester").attr("value","重置");
	 });
});

function initable(){
	myTables.searchkeywords.clickRow=function (clickval,rowdata){
		if(clickval == "modfiykeywords"){
			 $("#addkeywords").attr("value","修改关键词");
			 $("#rester").attr("value","取消修改");
			 // 填充修改绑定值
			 if(rowdata.isspider == "T"){
				$("#isspider").attr("checked",true);
			 }else{
				$("#isspider").attr("checked",false);
			 }
			 setModfiyVal(rowdata);
		}else if(clickval == "delkeywords"){
			if($("#addkeywords").val() == "修改关键词"){
				alert("该信息处于编辑状态不能删除！");
				return;
			}
			var delurl = clickval+".action?id="+rowdata.id;
			ajaxPost(delurl);
		}
    };
}
/**
 * 关键词修改信息填充
 * @param rowdata
 */
function  setModfiyVal(rowdata){
	$("#id").val(rowdata.id);
	$("#keyword").val(rowdata.keywords);
}
//提交后台处理
function ajaxPost(url){
	$.ajax({
		url:url,
		type:"POST",
		data:$(':input','#hidparam').serialize(),
		success:function(res){
		  if(res == "waring"){
			  alert("操作关键词数据库中已存在!");
			  return;
		  }if(res == "success"){
			  refershData();
			  $("#rester").trigger("click");
		  }
          if(res == "error"){
        	  alert("操作异常!");
			  return null;
		  }
		}
	});
}
// 刷新页面
function refershData(){
	myTables["searchkeywords"].refreshTab();
}