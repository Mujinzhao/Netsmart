$(function(){
	   // 初始化table 控件数据
	   initable();
	   if($("#lgurl").val() != null && $("#lgurl").val() != ""
			   && $("#lgurl").val() != undefined){
		   $("#taskurl").text($("#lgurl").val());
	   }
	
	   var iframeObj =  window.parent.document.getElementById("pageconfig");
	   //数据来源
	   $("#dayasource").click(function(){
		   
	   })
	   // 下一步
	   $("#nextpaging").click(function(){
		    $("#pageflag").val("0");
		   $("#flag").val("event");
		   iframeObj.src ="gatherpage.action?"+$("#hidparam").find("*").serialize();
	   });
	   // 上一步
	   $("#previousstep").click(function(){
		   //iframeObj.src = "gather/groupconfig.jsp";
		   $("#pageflag").val("1");
		   //alert($("#hidparam").find("*").serialize());
		   iframeObj.src ="gatherpage.action?"+$("#hidparam").find("*").serialize();
	   });
	   $("#eventconfig").click(function(){
		   var opurl="";
		   $("#partagcode").attr("disabled",false);
		   $("#tagcode").attr("disabled",false);
		   if($("#eventconfig").val()=="事件配置"){
			   // 释放置灰按钮
			   settagcode();
			   opurl="addevent.action";
			 
		   }else{
			   opurl="modifyevent.action";
		   }
		   ajaxPost(opurl);
		   $("#partagcode").attr("disabled",true);
		   $("#tagcode").attr("disabled",true);
	   });
	   $("#clearcontor").click(function(){
		   //iframeObj.src = "gather/groupconfig.jsp";
		   // 清空标签信息值
		   $(':input','#checkparam') .not(':button, :submit, :reset, :hidden').val('').removeAttr('checked')  .removeAttr('selected'); 
		   $("#partagcode").val("0");
		   $("#eventconfig").attr("value","事件配置");
		   $("#clearcontor").attr("value","重置");
		   
		   changetagtype("0");
		   //alert($("#addparam").find("*").serialize());
	   });
	   
});
//修改和删除按钮
function  initable(){
	myTables.eventinfo.clickRow=function (clickval,rowdata){
		if(clickval == "modifyevent"){
			 $("#eventconfig").attr("value","确认修改");
			 $("#clearcontor").attr("value","取消修改");
			 setModfiyEvent(rowdata);
		}else if(clickval == "delevent"){
			var delurl = clickval+".action?id="+rowdata.id;
			ajaxPost(delurl);
		}
    };
    
    myTables["eventinfo"].formatCol("tagattr1val",function (coldata,rowdata){
    	var eventvalhtml=null;
        var eventval = rowdata.tagattr1val;
        if(eventval == "set")
        {
        	eventvalhtml="设置";
        }
        else if(eventval == "click")
        {
        	eventvalhtml="点击";
        }
        else if(eventval == "change")
        {
        	eventvalhtml="改变选项";
        }
        else if(eventval == "timedefult")
        {
        	eventvalhtml="时间默认";
        }
        else if(eventval=="validate")
    	{
        	eventvalhtml="识别验证码";
    	}
        else if(eventval=="fillvalidate")
    	{
        	eventvalhtml="填写验证码";
    	}
	    return eventvalhtml;
	});
	
	myTables["eventinfo"].formatCol("tagtype",function (coldata,rowdata){
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
	   if($("#tagtype").val() == "1")
       {
		   $("#partagcode").val(countnum-1);
       }
	   $("#tagcode").val(countnum); 
}

function link_open(){
	window.open($("#taskurl").text());
	//window.parent.parent.addTab($("#taskname").val(), "gcf:view-source:"+$("#taskurl").val());
}
// 标签类型修改触发事件
function changetagtype(value){
	if(value == "0"){
		$("#eventcontor").show();
	}else{
		$(':input','#eventcontor').val('') .removeAttr('checked')  .removeAttr('selected');
		$("#eventcontor").hide();
	}
}
function setModfiyEvent(obj){
	//事件ID
	$("#id").val(obj.id);
	//父级编号
	$("#partagcode").attr("value",obj.partagcode);
	//标签编号
	$("#tagcode").attr("value",obj.tagcode);
	//标签名称
	$("#tagname").attr("value",obj.tagname);
	//标签属性
	$("#tagattr").attr("value",obj.tagattr);
	//属性值
	$("#tagval").attr("value",obj.tagattrval);
	//事件类型
	$("#tagevent").attr("value",obj.tagattr1val);
	//事件类型值
	$("#tageventval").attr("value",obj.tagattr1);
}
function ajaxPost(url){
	//alert($(':input,:checkbox,:radio','#addparam').serialize());
	$.ajax({
		url:url,
		type:"POST",
		data:$(':input,:checkbox,:radio','#addparam').serialize(),//$("#addparam").find("*").serialize(),
		success:function(res){
		  refershData();
		  $("#clearcontor").trigger("click");
		}
	});
}
	// 刷新页面
function refershData(){
	myTables["eventinfo"].refreshTab();
}