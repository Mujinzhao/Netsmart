 var interval;
$(function (){
	 var tasktype = $("#timetype").val();
	 $("input[value="+tasktype+"]").attr("checked",true);
	 timetype(tasktype);
	 var mothed = $("#taskenabled").val();
	 //启动采集任务
	 if(mothed == "run"){
		 $("#taskdesc").html("<font color=\"green\">任务已启动</font><br/>");
	 }else if(mothed == "stop"){
		 $("#taskdesc").html("<font color=\"red\">任务已被暂停</font><br/>");
	 }else if(mothed == "kill"){
		 $("#taskdesc").html("<font color=\"red\">任务已被终止</font><br/>");
	 }
});
function timetype(tasktype){
	 var timestate = $("#timetype").val();
	 if(timestate != "" && timestate == tasktype){
		 $("#taskdesc").html("<font color=\"green\">任务已启动</font><br/>");
	 }else{
		 $("#taskdesc").html("任务暂未运行<br/>");
	 }
	 if(tasktype == "0"){
    	 $("#tasktypedesc").html("当前时间执行");
     }else if(tasktype == "1"){
    	 $("#tasktypedesc").html("每天凌晨23:59执行 ");
     }else if(tasktype == "2"){
    	 $("#tasktypedesc").html("每周六凌晨23:59执行");
     }else if(tasktype == "3"){
    	 $("#tasktypedesc").html("每月01号的23:59执行");
     }else{
    	 $("#tasktypedesc").html("");
     }
}
/**
 * 任务管理操作
 * @param mothed
 */
function  taskmanageop(mothed){
	if($("#isactive").val() == "T"){
		 var taskcode = $("#taskcode").val();
		 var tasktype = $("input[id=runtype]:checked").val();
		 // 采集任务管理操作 当前时间任务执行 启动、暂停、终止
	     if(tasktype == "0"){
	    	 //启动采集任务
	    	 if(mothed == "startspider"){
	    		 	$("#taskdesc").html("<font color=\"green\">任务已启动</font><br/>");
		    		realtime();
		    		ajaxPost(mothed+".action?taskcode="+taskcode);
	    	 }else if(mothed == "haltspider"){
	    		 	$("#taskdesc").html("<font color=\"red\">任务已被暂停</font><br/>");
	    		    ajaxPost(mothed+".action?taskcode="+taskcode);
	    			// 睡眠15秒后关闭定时任务
//	    			sleep(15000);
//	    			clearTimeout(interval);  
	    	 }else if(mothed == "killspider"){
	    		 	$("#taskdesc").html("<font color=\"red\">任务已被终止</font><br/>");
	    		    ajaxPost(mothed+".action?taskcode="+taskcode);
	    			// 睡眠15秒后关闭定时任务
//	    			sleep(15000);
//	    			clearTimeout(interval);  
	    	 }
	     }
	     //解析任务管理操作 其他时间设置任务 启动、暂停、终止
	     else{
	    	 if(tasktype != null && tasktype != undefined){
	    		 var parsesnum = $("#parsesnum").text();
	    		 var url = "timingstate.action?taskcode="+taskcode+"&opmothed="+mothed+"&runtype="+tasktype;
	    		 if(parsesnum == 0){
	    			 alert("该任务尚未配置解析模板，是否启动该任务!");
	    			//启动解析任务
					 ajaxPost(url);
	    		 }else{
					 ajaxPost(url);
	    		 }
	    		 if(mothed == "startspider"){
		    		 	$("#taskdesc").html("<font color=\"green\">任务已启动</font><br/>");
		    	 }else if(mothed == "haltspider"){
		    		 	$("#taskdesc").html("<font color=\"red\">任务已被暂停</font><br/>");
		    	 }else if(mothed == "killspider"){
		    		 	$("#taskdesc").html("<font color=\"red\">任务已被终止</font><br/>");
		    	 }
	    	 }else{
	    		 alert("任务执行条件选择！");
		    	 return ;
	    	 }
	     }
	}else{
		alert("该任务配置无效!");
		return ;
	}
}
function realtime(){
	interval =setInterval("realtimeShowLog('showlog.action')", 1000);
}
function  closeShowLog(interval){
	clearTimeout(interval);
}
/**
 * 提交后台方法
 */
function  ajaxPost(url){
	$.ajax({
		url:url,
		type:"POST",
		data:"",
		success:function(res){
		}
	});
}
/**
 * 同步任务操作日志信息
 * @param url
 */
function realtimeShowLog(url){
	$.ajax({
		url:url,
		type:"POST",
		data:"",
		success:function(res){
			var obj = document.getElementById("taskdesc");
			if(res =="" || res =="获取任务日志异常！"){
				// 睡眠15秒后关闭定时任务
//    			sleep(15000);
//    			clearTimeout(interval);  
			}else{
				//判断是否出现滚动条
				if(obj.scrollHeight>obj.clientHeight||obj.offsetHeight>obj.clientHeight){ 
					// 清空原来控件信息重新绑定数据
					 obj.innerHTML="";
			    } 
			}
			var html = obj.innerHTML;
			//再跟你想追加的代码加到一起插入div中
	    	obj.innerHTML = html + res;
			
		}
	});
}

function changeHight(){
	var beforeHeight = $("#taskdesc").scrollTop();
	$("#taskdesc").scrollTop($("#taskdesc").scrollTop()+20);
	var afterHeight = $("#taskdesc").scrollTop();
	if(beforeHeight == afterHeight){
	//alert("ok");
	}else{
	  setTimeout("changeHight()",5);
	}
}

