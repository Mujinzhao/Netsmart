//var iframeObj;
$(function(){
	    // 初始化table 控件数据
	    initable();
	    //生成解析字段select 控件
		var url="parsefiledselect.action?sqlkey=getCustomFiled&"+$(':input,:checkbox,:radio','#addform').serialize();
		tablefiled(url, "parsefiled");
		
		//下一步操作
		$("#nextbut").click(function(){
			// 动态跳转界面
			var iframeObj =  window.parent.document.getElementById("parserconfig");
			var parseurl = "templateconfig.action?jumpage=excelparser&";
			iframeObj.src=parseurl+$(':input,:checkbox,:radio','#addform').serialize();
		});
});

function  initable(){
	myTables["templateFiled"].formatCol("emptyacqfield",function (coldata,rowdata){
		var emptyfiledhtml = "空";
        var emptyacqfield = rowdata.emptyacqfield;
        if(emptyacqfield == "1")
        {
        	emptyfiledhtml="非空";
        }
	    return emptyfiledhtml;
	});
}
function customfiled(){
	ajaxPost("customfiledval.action");
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

function ajaxPost(url){
	$.ajax({
		url:url,
		type:"POST",
		data:$(':input,:checkbox,:radio','#addform').serialize(),
		success:function(res){
		  refershData("templateFiled");
		  //$("#clearcontor").trigger("click");
		}
	});
}
