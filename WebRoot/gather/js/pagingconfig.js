//window.onload=function (){};
/**
 * 分页操作js
 */
$(function(){
	 //初始化table 控件
     initable();
	 var iframeObj =  window.parent.document.getElementById("pageconfig");
	       // 完成，跳转至任务管理页
		   $("#complete").click(function(){
			   iframeObj.src ="http://localhost:8080/NetSmart/task/taskmanage.jsp";
				window.parent.killTab("任务管理");
				window.parent.addTab("任务管理", "taskmanage.action");
			   window.parent.addTab("任务管理", "taskmanage.jsp");
		   });
		   // 上一步
		   $("#previousstep").click(function(){
			   //pageflag 页面标识  0页面配置  1组信息添加界面 2元搜索 3模拟采集，4分页配置
//			   $("#pageflag").val("0");
			   iframeObj.src ="gatherpage.action?"+$("#hidparam").find("*").serialize();
		   });
		   // 保存分页配置
		   $("#savepaging").click(function(){
			   // 释放置灰按钮
			   $("#partagcode").attr("disabled",false);
			   $("#tagcode").attr("disabled",false);
			   var url=null;
			   if( $("#savepaging").val()=="保存"){
				   settagcode();
				   url="addpaging.action";
			   }else{
				   url="modfiypaging.action";
			   }
			   ajaxPost(url);
			   // 置灰按钮
			   $("#partagcode").attr("disabled",true);
			   $("#tagcode").attr("disabled",true);
			   //alert($(':input,:checkbox,:radio','#addpageparam').serialize());
		   });
		   
		   //重置
		   $("#clearcontor").click(function(){
			   $("#savepaging").attr("value","保存");
			   $("#clearcontor").attr("value","重置");
			   // 清空标签信息值
			   $(':input,:checkbox,:radio','#addpagingparam').not(':button, :submit, :reset, :hidden').val('').removeAttr('checked')  .removeAttr('selected'); 
		   });
	   
});
function initable(){
	myTables.pageingconfg.clickRow=function (clickval,rowdata){
		if(clickval == "modfiypaging"){
			 $("#savepaging").attr("value","确认修改");
			 $("#clearcontor").attr("value","取消修改");
			 setModifyVal(rowdata);
			 
		}else if(clickval == "delpaging"){
			var delurl = clickval+".action?id="+rowdata.id;
			ajaxPost(delurl);
		}
    };
	
	myTables["pageingconfg"].formatCol("tagtype",function (coldata,rowdata){
		var tagtypehtml = "";
        var tagtype = rowdata.tagtype;
        if(tagtype == "0")
        {
        	tagtypehtml="目标标签";
        }
        if(tagtype == "1")
        {
        	tagtypehtml="容器标签";
        }
	    return tagtypehtml;
	});
}
function  setModifyVal(obj){
	$("#id").val(obj.id);
	$("#tagtype").attr("value",obj.tagtype);
	$("#partagcode").val(obj.partagcode);
	$("#tagcode").val(obj.tagcode);
	$("#tagname").attr("value",obj.tagname);
	$("#tagattr").attr("value",obj.tagattr);
	$("#tagval").attr("value",obj.tagattrval);
}
// 设置父级标签及标签编号
function settagcode(){
	  var req = /共.*?(\d+).*?行/;
	   var countnum=1;
	   var countnumstr = $(".pagingdiv").text();
	   if(countnumstr != "" && countnumstr!= null &&countnumstr !=undefined){
		   var result= countnumstr.match(req); 
		   if(result !="null"){
			   countnum= result[1];
			   countnum++;
		   }
	   }
	   $("#partagcode").val(countnum-1);
	   $("#tagcode").val(countnum); 
}

function link_open(){
	window.open($("#taskurl").text());
}
function ajaxPost(url){
	$.ajax({
		url:url,
		type:"POST",
		data:$("#addpagingparam").find("*").serialize(),
		success:function(res){
		  refershData();
		}
	});
}
	// 刷新页面
function refershData(){
	myTables["pageingconfg"].refreshTab();
}