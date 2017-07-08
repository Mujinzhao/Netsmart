//window.onload=function (){
//	
//};
$(function (){
	//初始化 table 控件样式
	initablecss();
	// 保存、修改点击事件
	$("#sub").click(function(){
		var saveurl = null;
		var buttonstr = $("#sub").find("span [class=l-btn-text]").html();
		if(buttonstr == "保存"){
			var checkflag = checktasktype();
			if(!checkflag.check()){
				return;
			}
			 // 验校当前输入采集地址是否存在
			 var checkurl = myTables["tasklist"].checkColumnData("taskurl",$("#taskurl").val());
	         if(checkurl){
		    	  $(".border_left").find(".Validform_right").removeClass("Validform_checktip Validform_right").addClass("Validform_checktip Validform_wrong");//checking;
		    	  $(".border_left").find(".Validform_wrong").html("采集地址已经存在！");
		    	  return;
	         }
	         // 判断当前是否选中
	         saveurl = "optask.action";
		}else{
			 saveurl = "modfiytask.action";
		}
		 //是否激活
     	if($("#isactive").attr("checked")=="checked"){
     		 $("#isactive").attr("value","T");
     	}
     	//是否采集
     	if($("#isspider").attr("checked")=="checked"){
     		 $("#isspider").attr("value","T");
     	}
    	//是否排重
     	if($("#remark").attr("checked")=="checked"){
     		 $("#remark").attr("value","T");
     	}
     	
//		alert($(':input,:checkbox,:radio','#addmyform').serialize());
		ajaxPost(saveurl);
	});
	$("#reset").click(function(){
		clearTask();
	});
});

// 初始化 table 控件样式
function initablecss(){
	myTables.tasklist.clickRow=function (clickval,rowdata){
		if(clickval == "gathergroup"){
			window.parent.killTab("采集任务配置");
			var pagefalg=rowdata.tasktype;
			// 处理采集URL &问题
			var taskurl=rowdata.taskurl;
		    if (taskurl.indexOf("&") != -1) {
		    	taskurl = taskurl.replace("&", "$");
		    }
			var gatherurl = encodeURI(encodeURI("taskname="+rowdata.taskname
					+"&tasktype="+pagefalg
					+"&taskcode="+rowdata.taskcode
					+"&tasklogin="+rowdata.tasklogin
					+"&taskurl="+taskurl));
			window.parent.addTab("采集任务配置", clickval+".action?" + gatherurl);
		}
		else if(clickval == "taskgrouppage"){
			window.parent.killTab("解析任务配置"); 
			var taskurl = encodeURI(encodeURI("taskname="+rowdata.taskname+"&taskid="+rowdata.taskcode));
			window.parent.addTab("解析任务配置", clickval+".action?" + taskurl);
		}
		else if(clickval == "modfiytask"){
//			document.getElementById("tasktype").disabled=true;
			 //修改前后缀规则信息
			 $("#sub").find("span [class=l-btn-text]").html("修改任务");
			 $("#reset").find("span [class=l-btn-text]").html("取消修改");
			 //填充修改数据
			 setModifyTask(rowdata);
		}else if(clickval == "deltask"){
			if(rowdata.eventcount >0 || rowdata.pagecount >0){
				alert("该任务存在采集模板信息！");
				return;
			}
			var delurl = clickval+".action?taskcode="+rowdata.taskcode;
			ajaxPost(delurl);
		}else{
		        var manageurl = "taskname="+rowdata.taskname+"&taskcode="+rowdata.taskcode
		                        +"&spidertype="+rowdata.tasktype+"&isactive="+rowdata.isactive
		                        +"&eventnum="+rowdata.eventcount+"&pagenum="+rowdata.itagcount
		                        +"&pagingnum="+rowdata.pagecount+"&parsesnum="+rowdata.parsercount
		                        +"&timetype="+rowdata.timetype+"&taskenabled="+rowdata.taskenabled;
		        manageurl = encodeURI(encodeURI(manageurl));
//		        alert(manageurl);
		        window.parent.killTab("任务管理");
				window.parent.addTab("任务管理", "taskmanage.action?"+manageurl);
		
			//ajaxPost(clickval+".action?taskcode="+rowdata.taskcode);
		}
//		else if(clickval == "validspider"){
//			ajaxPost("runspidertasks.action?taskcode="+rowdata.taskcode);
//		}else if(clickval == "haltspider"){//spiderTasksHalt
//		    ajaxPost("spiderTasksHalt.action?taskcode="+rowdata.taskcode);
//		}else if(clickval == "killspider"){
//		    ajaxPost("spiderTasksKill.action?taskcode="+rowdata.taskcode);
//		}else if(clickval == "restartspider"){
//			  ajaxPost("spiderTasksKill.action?taskcode="+rowdata.taskcode);
//		}
	};
	// 采集类型
	myTables["tasklist"].formatCol("tasktype",function (coldata,rowdata){
		var tasktypehtml = "";
        var tasktype = rowdata.tasktype;
        if(tasktype == "0")
        {
        	tasktypehtml="列表采集";
        }
        if(tasktype == "1")
        {
        	tasktypehtml="模拟采集";
        }
        if(tasktype == "2")
        {
        	tasktypehtml="元搜索采集";
        }
        if(tasktype == "3")
        {
        	tasktypehtml="当前源采集";
        }
	    return tasktypehtml;
	});
	// 任务有效标识
	myTables["tasklist"].formatCol("isactive",function (coldata,rowdata){
		var isactivehtml="";
	    var isactive = rowdata.isactive;
	    if(isactive == "T") {
	    	isactivehtml="<label style='color:green;'>有效</label>";
	    } else{
	    	isactivehtml="<label style='color:red;'>无效</label>";
	    }
	    return isactivehtml;
	});
	//任务采集标识
	myTables["tasklist"].formatCol("isspider",function (coldata,rowdata){
		var isspiderhtml="";
	    var isspider = rowdata.isspider;
	    if(isspider == "T") {
	    	isspiderhtml="<label style='color:green;'>是</label>";
	    } else{
	    	isspiderhtml="<label style='color:red;'>否</label>";
	    }
	    return isspiderhtml;
	});
}
/**
 * 修改填充数据
 * @param obj
 */
function setModifyTask(obj){
	//任务编号
	$("#taskcode").val(obj.taskcode);
	//采集类型
	$("#tasktype").attr("value",obj.tasktype);
	//下载方式
	$("#selectype").attr("value",obj.jsparse);
	//采集页数
	$("#totalpage").val(obj.totalpage);
	//任务名称
	$("#taskname").val(obj.taskname);
	//是否激活
	if(obj.isactive == "T")
	    $("#isactive").attr("checked",true);
	else
		$("#isactive").attr("checked",false);
	
	 $("#isactive").attr("value",obj.isactive);
	//是否采集
	if(obj.isspider == "T")
	    $("#isspider").attr("checked",true);
	else
	    $("#isspider").attr("checked",false);
	
	
	//是否排重
 	if(obj.remark == "T"){
 	   $("#remark").attr("checked",true);
 	}else{
 	   $("#remark").attr("checked",false);
 	}
 	
	 $("#isspider").attr("value",obj.isspider);
	//网页编码
	$("#encodeurl").attr("value",obj.encodeurl);
	//采集地址
	$("#taskurl").val(obj.taskurl);
}
/**
 * 添加任务时校验
 */
function checktasktype(){
		var gathercheck=$("#gathercheck").Validform({tiptype:3});
		return gathercheck;
}
/**
 * 提交后台方法
 */
function ajaxPost(url){
	$.ajax({
		url:url,
		type:"POST",
		data:$(':input,:checkbox,:radio','#addmyform').serialize(),
		success:function(res){
			// 清空控件信息
			refershData();
			clearTask();
		}
	});
}
//清空控件信息
function clearTask (){
	
  $(':input','#addmyform').not(':button, :submit, :reset, :hidden')  .val('')  .removeAttr('checked')  .removeAttr('selected') .removeAttr('class') .removeAttr('nullmsg');
  $("#sub").find("span [class=l-btn-text]").html("保存");
  $("#reset").find("span [class=l-btn-text]").html("重置");
  
  $("#taskcode").val("");
}
function refershData(){
	myTables["tasklist"].refreshTab();
}