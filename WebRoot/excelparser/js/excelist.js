$(function(){
	   // 初始化table 控件数据
	   initable();
	   //初始化上传控件
	   //绑定上传控件样式
	   templateuploadcss("uploadify","uploadfile.action?templatetype="+encodeURI($("#templatetype").val()),"解析文件上传",true,"*.xls;*.xlsx","xls xlsx",null);

});

function  initable(){
	myTables.excelist.clickRow=function (clickval,rowdata){
		if(clickval == "delFile"){
			var delurl = clickval+".action?id="+rowdata.excelid;
			ajaxPost(delurl);
		}
    };
}
function xfileclose(){
	window.parent.closexlsFiles();
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
	$("#excelname").val(file.name);
	$("#exceltype").val(file.type);
	$("#excelsize").val(file.size);
	var uploadurl = "uploadFiles.action";
	ajaxPost(uploadurl);
	
}
function ajaxPost(url){
	$.ajax({
		url:url,
		type:"POST",
		data:$(':input,:checkbox,:radio','#addmyform').serialize(),
		success:function(res){
		  refershData();
		  //$("#clearcontor").trigger("click");
		}
	});
}
	// 刷新页面
function refershData(){
	myTables["excelist"].refreshTab();
}