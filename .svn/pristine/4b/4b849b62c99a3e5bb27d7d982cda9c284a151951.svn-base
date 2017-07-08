// 自定义 维护接口及列格式化行数据
var iframeObj;
$(function (){
	// 初始化表格控件
	initable();
	//生成解析字段select 控件
	var url="parsefiledselect.action?sqlkey=getTableFiledByStructuredid&"+$(':input,:checkbox,:radio','#tableparam').serialize();
	tablefiled(url, "parsefiled");
	if($("#tagpage").val()== "2"){
		// 出现上一步操作按钮
		$("#previousstep").show();
	}else{
		$("#previousstep").hide();
	}
	
	//重置操作
	$("#currtparser").click(function(){
		 $("#saveparser").find("span [class=l-btn-text]").html("提交");
		 $("#currtparser").find("span [class=l-btn-text]").html("重置");
		 $("#delparser").show();
		 $("#id").val("");
		 //$("#parsefiled").attr("value","");
		 $("#parsefiled option").eq(0).attr('selected', 'true');
		 $("#tagindex").val("0");
	});
	// 删除操作
	$("#delparser").click(function(){
		// 获取选中的checkbox值
		var delids = getids("tagparserInfo");
		if(null == delids || delids == "" || delids == undefined){
			alert("请先选择你要删除的信息！");
			return;
		}
		// 将id参数拼装'xxx','xxx'形式传入后台操作
		if (delids.indexOf(",") != -1) {
	    	//taskurl = taskurl.replaceAll("&", "$");
			delids=delids.replace(/\,/g,"','"); 
	    }
		var delurl = "delparserinfo.action?id='"+delids+"'";
		postAjax(delurl);
		
	});
});

function initable(){
	myTables.tagparserInfo.clickRow=function (clickval,rowdata){
		if(clickval == "modfiyparse"){
			 //修改前后缀规则信息
			 $("#saveparser").find("span [class=l-btn-text]").html("修改规则");
			 $("#currtparser").find("span [class=l-btn-text]").html("取消修改");
			 // 删除按钮隐藏
			 $("#delparser").hide();
			 editdata(rowdata);
		}else if(clickval == "delparse"){
			var delurl = "delparserinfo.action?id='"+rowdata.id+"'";
			postAjax(delurl);
		}
    };
}

// 上一步  前后缀解析界面
function previousstep(){
	var parseifram = window.parent.document.getElementById("parserconfig");
	$("#parsertype").val("0");
	var parseurl = "parserpage.action?";
	parseifram.src=parseurl+$(':input,:checkbox,:radio','#hidparam').serialize();
}
//下一步  标签解析界面
function nextstep(){
	var parseifram = window.parent.document.getElementById("parserconfig");
	$("#parsertype").val("3");
	var parseurl = "parserpage.action?";
	parseifram.src=parseurl+$(':input,:checkbox,:radio','#hidparam').serialize();
}
function savetagInfo(){
	var tagindex = getTableLineNumer();
	var parserbut = $("#saveparser").find("span [class=l-btn-text]").html();
	if(parserbut=="提交"){
		// 校验添加的字段是否已存在数据库中
	    var checkfiled = myTables["tagparserInfo"].checkColumnData("acqfield",$("#parsefiled").val());
	    if(checkfiled){
	  	  alert("解析字段已添加！");
	  	  return ;
	    }
	    $("#tagindex").val("");
	    tagindex++;
	    $("#tagindex").val(tagindex);
		postAjax("tagparserinfo.action");
		
	}else{
		postAjax("updateparserinfo.action");
	}
	// 获取当前字段选中下标值
	var index = $('#parsefiled option:selected').index();
	var customop =  $('#customop option:selected').val();
	//设置自动选中字段 根据option控件下标
	$('#parsefiled option:eq('+(index+1)+')').attr('selected','selected');
	// 设置字段下标值
	if(customop == "1"){
		var filedindex =1;
		if(index>0){
			filedindex=(index+1);
		}
		$("#acqfieldval").val(filedindex);
	}
	
}
//修改数据绑定
function editdata(rowdata){
	$("#id").val(rowdata.id);
	$("#parsefiled").attr("value",rowdata.acqfield);
	$("#tagindex").val(rowdata.fieldindex);
}
/**
 * 提交后台方法
 */
function postAjax(url){
	$.ajax({
		url:url,
		type:"POST",
	//	datatype:"html",
		data:$(':input,:checkbox,:radio','#tableparam').serialize(),
		success:function(res){
		    refershData("tagparserInfo");
		}
	});
	
}

/**
 *  查询数据库表字段绑定
 * @param url 后台请求链接
 * @param obj 绑定控件ID
 */
function tablefiled(url,obj){
	//绑定字段列
	$("#"+obj).find("option").remove(); 
	$.ajax({
		url:url,
		type:"POST",
		dataType:"text",
		success:function(res){
           $(res).appendTo($("#"+obj));
		}
	});
}