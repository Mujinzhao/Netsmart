var iframeObj;
$(function (){
	// 动态跳转界面
	iframeObj =  window.document.getElementById("parserconfig");
	var parseurl = $("#jumpurl").val();//"parserpage.action?";
	iframeObj.src=parseurl+$(':input,:checkbox,:radio','#hidparam').serialize();
});
