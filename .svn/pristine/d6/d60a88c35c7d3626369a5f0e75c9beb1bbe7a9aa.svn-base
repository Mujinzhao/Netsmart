$(function(){
	   // 初始化table 控件数据
	   initable();
	   //初始化上传控件
	  templateuploadcss("uploadify","uploadfile.action","解析模板上传",false,"*.xls;*.xlsx","xls xlsx",null);
      $("#reset").click(function(){
	    	$("#templateid").val("");
	    	$("#templatetype").val("");
	        $("#otemplatetype").val("");
	    	$("#stauts").attr("checked",false);
	    	$("#uploadexcel").find("span [class=l-btn-text]").html("上传");
			$("#reset").find("span [class=l-btn-text]").html("重置");
      });
});

function  uploadtemplate(){
	var templateType = $("#templatetype").val();
	if(templateType == null || templateType ==""){
		alert("模板类型未填写！");
		return;
	}
	var uploadexcelbut = $("#uploadexcel").find("span [class=l-btn-text]").html();
	if(uploadexcelbut == "上传"){
		$("#uploadify").uploadify("settings", "formData",{
			'templatetype':templateType,
			'pageflag':$("#pageflag").val()
			});
		$("#uploadify").uploadify("upload", "*");
	}else{
		ajaxPost("modfiytemplate.action");
	}
	
}

function  initable(){
	myTables.templatelist.clickRow=function (clickval,rowdata){
		if(clickval == "templatexml"){
			var url = clickval+".action?templateid="+rowdata.id+"&templatetype="+rowdata.template_type;
			url = encodeURI(encodeURI(url));
			ajaxPost(url);
		}
		if(clickval == "templatestruct"){
			window.parent.killTab("Excel模板维护");
			var sturl = "templatestruct.action?templateid="+rowdata.id
			+"&templatePath="+rowdata.template_path
			+"\\"+rowdata.template_name+rowdata.template_filetype;
			sturl = encodeURI(encodeURI(sturl));
			window.parent.addTab("Excel模板维护", sturl);
		}
		if(clickval == "modfiytemplate"){
			 $("#uploadexcel").find("span [class=l-btn-text]").html("修改模板");
			 $("#reset").find("span [class=l-btn-text]").html("取消修改");
			 setModfiyEvent(rowdata);
		}else if(clickval == "deltemplate"){
			var delurl = clickval+".action?structid="+rowdata.id;
			ajaxPost(delurl);
		}
		// 解析文件维护
		else if(clickval == "fileop"){
			xlsFiles("filepage.action?templateid="+rowdata.id+"&templatetype="+rowdata.template_type);
		}
    };
}

function xlsFiles(excelurl){
	// 设置iframe src值
	$("#excelfiles").attr("src",excelurl);
	$('#xlsfiles').window('open');
}
function closexlsFiles(){
	$('#xlsfiles').window('close');
}
function setModfiyEvent(file){
	$("#templateid").val(file.id);
	$("#templatetype").val(file.template_type);
	$("#otemplatetype").val(file.template_type);
	if(file.status == "F")
	  $("#stauts").attr("checked",false);
	else
	  $("#stauts").attr("checked",true);
}
function  saveFileInfo(file){
//	alert( 'id: ' + file.id
//	+ ' - 索引: ' + file.index
//	+ ' - 文件名: ' + file.name
//	+ ' - 文件大小: ' + file.size
//	+ ' - 类型: ' + file.type
//	+ ' - 创建日期: ' + file.creationdate
//	+ ' - 修改日期: ' + file.modificationdate
//	+ ' - 文件状态: ' + file.filestatus);
	$("#templatename").val(file.name);
	$("#templatefiletype").val(file.type);
	$("#templatesize").val(file.size);
	var uploadurl = "uploadtemplate.action";
	ajaxPost(uploadurl);
	
}
function ajaxPost(url){
	$.ajax({
		url:url,
		type:"POST",
		data:$(':input,:checkbox,:radio','#addmyform').serialize(),
		success:function(res){
			if(res ==1){
				alert("XML模板创建成功");
				return;
			}else if(res == "wring"){
				alert("模板类型数据库中已存在！");
				return ;
			}
			refershData();
		  
		  //$("#clearcontor").trigger("click");
		}
	});
}
	// 刷新页面
function refershData(){
	myTables["templatelist"].refreshTab();
}