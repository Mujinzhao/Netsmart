var iframeObj;
var suffixdiv=null;
var regextr=null;
$(function (){
	// 初始化表格控件
	initable();
	//绑定上传控件样式
	fileuploadcss("parsefile","uploadparsfile.action?encode="+$("#encode").val(),"解析上传文件",true,"*.html;*htm","html htm",$("#showvalue"));
	// 下步操作按钮展现
	if($("#tagpage").val()=="2" || $("#tagpage").val()=="4"){
		$("#nexttagindex").show();
	}else{
		$("#nexttagindex").hide();
	}
	//切换验证方式
	checkparsertype();
	
	parsertype = $('input[id="parserstatus"]').filter(':checked').val();
	suffixdiv=$("#suffixcheck").Validform({tiptype:3});
	regextr=$("#regexcheck").Validform({tiptype:3});
//	if(parsertype=="0")
//	{
//		suffixdiv=$("#suffixcheck").Validform({tiptype:3});
//	}
//	else{
//		regextr=$("#regexcheck").Validform({tiptype:3});
//		console.info(regextr);
//	}
});

function initable(){
	myTables.suffixInfo.clickRow=function (clickval,rowdata){
	if(clickval == "modfiysuffix"){
		 //修改前后缀规则信息
		 $("#savesuffix").find("span [class=l-btn-text]").html("修改规则");
		 $("#centerBut").find("span [class=l-btn-text]").html("取消修改");
		 editdata(rowdata);
	}else if(clickval == "delsuffix"){
		var delurl = "delsuffix.action?id="+rowdata.id;
		var suffixbut = $("#savesuffix").find("span [class=l-btn-text]").html();
		if(suffixbut=="规则入库"){
			postAjax(delurl,$(':input,:checkbox,:radio','#divsuffix').serialize(),"suffixInfo");
		}else{
			alert("当前数据处于编辑状态，无法删除！");
			return;
		}
	}
};
}

//绑定修改值
function editdata(obj){
	$("#id").val(obj.id);
	$("#parsefiled").attr("value",obj.acqfield);
	$("#prefixrule").val(obj.ruleprefix);
	$("#extractregex").val(obj.extractregex);
	$("#suffixrule").val(obj.rulesuffix);
	$("#outRegex").val(obj.excludregex);
}
// 下一步点击事件
function nextsetp(){
	var parseifram = window.parent.document.getElementById("parserconfig");
	if($("#tagpage").val()=="2"){
	   $("#parsertype").val("1");
	}if($("#tagpage").val()=="4"){
	   $("#parsertype").val("5");
	}
	var parseurl = "parserpage.action?";
	parseifram.src=parseurl+$(':input,:checkbox,:radio','#hidparam').serialize();
}
// 保存规则信息
function saveSuffix(){
	var parsertype = $('input[id="parserstatus"]').filter(':checked').val();
	if(parsertype=="1"){
		//清空前后缀规则信息
		$("#prefixrule").val("");
		$("#suffixrule").val("");
		if(!regextr.check()){
			return;
		}
	}else{
		if(!suffixdiv.check()){
			return;
		}
	}
	var url=null;
	var suffixbut= $("#savesuffix").find("span [class=l-btn-text]").html();
	
	if(suffixbut == "规则入库"){
		// 校验解析字段是否配置
		var parsefiled=$("#parsefiled").val();
	    var checkfiled = myTables["suffixInfo"].checkColumnData("acqfield",parsefiled);
	    if(checkfiled){
		   alert("该字段解析已配置！");
		   return ;
	    }
	    url ="savesuffix.action";
	}else{
		url ="modifysuffix.action";
	}
	postAjax(url,$(':input,:checkbox,:radio','#divsuffix').serialize(),"suffixInfo");
}

// 验证规则信息
function checkSuffix(){
	var parsertype = $('input[id="parserstatus"]').filter(':checked').val();
	if(parsertype=="1"){
		//清空前后缀规则信息
		$("#prefixrule").val("");
		$("#suffixrule").val("");
		if(!regextr.check()){
			return;
		}
	}else{
		$("#extractregex").val("");
//		regextr.resetForm();
		//清空绑定验证状态
		//$("#prefixrule").attr("disabled",false);
		//$("#suffixrule").attr("disabled",false);
		if(!suffixdiv.check()){
			$(".Validform_right").removeClass('Validform_right').removeClass('Validform_wrong').html(''); //有效
			return;
		}
	}
	var url ="validsuffix.action";
//	alert("haha");
	parseSuffix(url);

}
// 提交后台方法
function parseSuffix(url){
	$("#showvalue").text("请稍后...");
	$.ajax( {
			url : url,
			type : 'POST',
			async : true,
			dataType:"text",
			data:$(':input,:checkbox,:radio','#divsuffix').serialize(),
			timeout : 200000,
			success : function(data) {
				$("#showvalue").text(data);
//				  var odiv=document.createElement("div");    
				/*  var otextear=document.getElementById("");
//				  odiv.innerHTML=data;
				  otextear.innerText=data;*/
//				  otextear.appendChild(odiv); 
			}
	  });
}

//重置
function cencerSuffix(){
	$("#id").val("");
	$("#prefixrule").val("");
	$("#extractregex").val("");
	$("#suffixrule").val("");
	$("#outRegex").val("");
	$("#showvalue").val("");
	
	$("#savesuffix").find("span [class=l-btn-text]").html("规则入库");
	$("#centerBut").find("span [class=l-btn-text]").html("重置");
	
	// 清除校验提示信息
//	$(".Validform_right").removeClass('Validform_right').removeClass('Validform_wrong').html(''); //有效
//	$(".Validform_wrong").removeClass('Validform_wrong').removeClass('Validform_right').html(''); //有效
}
