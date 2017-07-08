// 自定义 维护接口及列格式化行数据
//window.onload=function (){
//	myTables.tagruleParse.clickRow=function (clickval,rowdata){
//		if(clickval == "modfiytagrule"){
//			 //修改前后缀规则信息
//			 $("#savetagrule").find("span [class=l-btn-text]").html("修改规则");
//			 $("#centerBut").find("span [class=l-btn-text]").html("取消修改");
//			 editdata(rowdata);
//		}else if(clickval == "delsuffix"){
//			var delurl = "delsuffix.action?id="+rowdata.id;
//			var suffixbut = $("#savesuffix").find("span [class=l-btn-text]").html();
//			if(suffixbut=="规则入库"){
//				postAjax(delurl,$(':input,:checkbox,:radio','#beforesuffix').serialize(),"suffixInfo");
//			}else{
//				alert("当前数据处于编辑状态，无法删除！");
//			}
//		}
//    };
//};
var iframeObj;
$(function (){
	//初始化表格控件
	initable();
	//绑定上传控件样式
	fileuploadcss("parsefile","uploadparsfile.action?encode="+$("#encode").val(),"解析上传文件",true,"*.html;*htm","html htm",null);
	checkparsertype();
});
function initable(){
	myTables.tagruleParse.clickRow=function (clickval,rowdata){
		if(clickval == "modfiytagrule"){
			 //修改前后缀规则信息
			 $("#savetagrule").find("span [class=l-btn-text]").html("修改规则");
			 $("#centerBut").find("span [class=l-btn-text]").html("取消修改");
			 editdata(rowdata);
		}else if(clickval == "delsuffix"){
			var delurl = "delsuffix.action?id="+rowdata.id;
			var suffixbut = $("#savesuffix").find("span [class=l-btn-text]").html();
			if(suffixbut=="规则入库"){
				postAjax(delurl,$(':input,:checkbox,:radio','#beforesuffix').serialize(),"suffixInfo");
			}else{
				alert("当前数据处于编辑状态，无法删除！");
			}
		}
    };
}

// 上一步  前后缀解析界面
function previousstep(){
	var parseifram = window.parent.document.getElementById("parserconfig");
	$("#parsertype").val("1");
	var parseurl = "parserpage.action?";
	parseifram.src=parseurl+$(':input,:checkbox,:radio','#hidparam').serialize();
}

//解析验证
function checktagrule(){
	var ruleparam = postruleparam();
	var url = "checktagparser.action?"+ruleparam;
	//alert(url);
	postAjaxCheck(url);
}
// 添加标签规则
function  addtagrule(){
	var ruleparam = postruleparam();
	var suffixbut = $("#savetagrule").find("span [class=l-btn-text]").html();
	var url ="";
	if(suffixbut=="规则入库"){
		 url = "savetagparser.action?"+ruleparam;
	}else{
		 url = "updatetagparser.action?"+ruleparam;
	}
	postAjax(url,$(':input,:checkbox,:radio','#tagrule').serialize(),"tagruleParse");
}
// 删除标签规则
function deltagrule(){
	// 获取选中的checkbox值
	var delids = getids("tagruleParse");
	if(null == delids || delids == "" || delids == undefined){
		alert("请先选择你要删除的信息！");
		return;
	}
	// 将id参数拼装'xxx','xxx'形式传入后台操作
	if (delids.indexOf(",") != -1) {
		delids=delids.replace(/\,/g,"','"); 
    }
	var delurl = "deltagparsser.action?id='"+delids+"'";
	postAjax(delurl,$(':input,:checkbox,:radio','#tagrule').serialize(),"tagruleParse");
}
// 修改数据填充
function editdata(rowdata){

	var trindex = myTables["tagruleParse"].getTabIndex("tagname",rowdata.tagname);
	$("#id"+trindex).val(rowdata.id);
	$("#tagname"+trindex).val(rowdata.tagname);
	$("#tagattr"+trindex).val(rowdata.tagattr);
	$("#tagattrval"+trindex).val(rowdata.tagattrval);
	$("#startindex"+trindex).val(rowdata.bindex);
	$("#endindex"+trindex).val(rowdata.eindex);
	if(trindex==1){
		$("#rowcellnum").val(rowdata.glrowcellnum);
	}
}
//重置
function cencerSuffix(){
	 $("#savetagrule").find("span [class=l-btn-text]").html("规则入库");
	 $("#centerBut").find("span [class=l-btn-text]").html("重置");
	 var index = myTables["tagruleParse"].find("tr").length;
	 for(var i=0;i<=index;i++){
		    $("#id"+i).val("");
			$("#tagname"+i).attr("value","");
			$("#tagattr"+i).attr("value","");
			$("#tagattrval"+i).val("");
			$("#startindex"+i).val("");
			$("#endindex"+i).val("");
	 }
	$("#rowcellnum").val("");
	$("#showURLValue").val("");
}
//提交后台参数
function postruleparam(){
	var tagrule= "'"+$("#tagname0").val()+"'$"+ $("#tagattr0").val()+"'$'"+ $("#tagattrval0").val()+"'$'"+ $("#startindex0").val()+"'$'"+ $("#endindex0").val()+"'$''$'"+$("#id0").val()+"'";
	var tagrule1="'"+$("#tagname1").val()+"'$"+$("#tagattr1").val()+"'$'"+$("#tagattrval1").val()+"'$'"+$("#startindex1").val()+"'$'"+$("#endindex1").val()+"'$'"+$("#rowcellnum").val()+"'$'"+$("#id1").val()+"'";
	var tagrule2="'"+$("#tagname2").val()+"'$"+$("#tagattr2").val()+"'$'"+$("#tagattrval2").val()+"'$'"+$("#startindex2").val()+"'$'"+$("#endindex2").val()+"'$''$'"+$("#id2").val()+"'";
	return "tabparam="+tagrule+"&trparam="+tagrule1+"&tdparam="+tagrule2+"&r="+Math.random();
}
/**
 * 提交后台方法
 */
function postAjaxCheck(url){
	$("#showURLValue").html("请稍后...");
	$.ajax({
		url:url,
		type:"POST",
	//	datatype:"html",
		data:$(':input,:checkbox,:radio','#tagrule').serialize(),
		success:function(res){
			$("#showURLValue").html(res);
		}
	});
}
