var treeURL="menutree.action";
var zTree_Menu;
var setting = {
        			view: {
        				dblClickExpand: false
        			},
        			data: {
        				simpleData: {
        					enable: true
        				}
        			},
        			callback: {
        				beforeClick: beforeClick
        				//onClick: onClick
        			}
};
$(function() {
	loadTree();
});
function loadTree(){
	var jsondata = mygetJson(treeURL, {
		loadsql : "true",
		sqlkey : "queryResTree",
		treeflag : "industry|website"
	});
	$.fn.zTree.init($("#menutree"), setting, jsondata);
	zTree_Menu = $.fn.zTree.getZTreeObj("menutree");
	zTree_Menu.expandAll(true); 
	return zTree_Menu;
}

function beforeClick(treeId, treeNode) {
	var resParentname = $("#resParentname");
	resParentname.attr("value", treeNode.res_clname);
	//$("#resParentid").val(treeNode.res_clname);
	var resParentid = $("#resParentid");
	resParentid.attr("value", treeNode.res_id);
}

function showMenu() {
	var cityObj = $("#resParentname");
	var cityOffset = $("#resParentname").offset();
	$("#treeDiv").css({left:cityOffset.left + "px", top:cityOffset.top + cityObj.outerHeight() + "px"}).slideDown("fast");
	$("body").bind("mousedown", onBodyDown);
}
function hideMenu() {
	$("#treeDiv").fadeOut("fast");
	$("body").unbind("mousedown", onBodyDown);
}
function onBodyDown(event) {
	if (!(event.target.id == "menuBtn" || event.target.id == "treeDiv" || $(event.target).parents("#treeDiv").length>0)) {
		hideMenu();
	}
}
function selectNodeType(value){
	var node = zTree_Menu.getNodeByParam("res_id", value, null);
	zTree_Menu.selectNode(node);
	$("#resParentname").val(node.res_clname);
}
/**
 * 修改数值绑定 
 * @param obj
 */
function  bindInputvalue(obj){
	
	  $("#res_pkid").val(obj.res_pkid);
	  $("#resId").val(obj.res_id);
	 // alert(obj.res_parentid);
	  $("#resParentid").val(obj.res_parentid);
	  $("#resClname").val(obj.res_clname);
	  //$("#resType").attr("value",obj.res_showtype);
	  $("#resPtype option[value='"+obj.res_showtype+"']").attr("selected", true); 

	  $("#resUrl").val(obj.res_url);
	  $("#resValue").val(obj.res_value);
	  $("#resSelkeyword").val(obj.res_selkeyword);
	  $("#resDescription").val(obj.res_description);
	 // $("#sltopentype").attr("value",obj.res_opentype);
	  $("#sltopentype option[value='"+obj.res_opentype+"']").attr("selected", true); 
}

function saveMenus(){
	//alert($("#edit").find("*").serialize());
	var url=null;
	if($("#opmothed").val()=="add"){
		 url = "saveMenus.action?resId="+$("#resId").val();
	}else if($("#opmothed").val()=="modfiy"){
		 url = "modfiyMenus.action?res_pkid="+$("#res_pkid").val();
	}
	postAjax(url);
}

function reset(){
	$("#edit input").attr("readonly",true);
	$("#edit input").attr("disabled",true);
	$("#edit select").attr("disabled",true);
	$("#opbutton input").attr("disabled",true);
	$("#res_pkid").val("");
	$("#resId").val("");
	//$("#edit input").attr("value","");
	//$("#edit select").attr("value","");
}
function  treeclickButton(flag){
	if(flag=="add"){
		$("#opmothed").val(flag);
		// 显示树控件
		$("#tr_resParentid").show();
		// 释放 readonly  属性
		$("#edit input").attr("readonly",false);
		$("#edit input").attr("disabled",false);
		$("#edit select").attr("disabled",false);
		$("#opbutton input").attr("disabled",false);
		$("#resParentid").attr("value",$("#resId").val());
		selectNodeType($("#resId").val());
		// 清空resID  重新生成
		$("#res_pkid").val("");
		$("#resId").val(window.parent.ran(16));
		$("#resClname").val("");
	}else if(flag=="modfiy"){
		$("#opmothed").val(flag);
		// 释放 readonly  属性
		$("#edit input").attr("readonly",false);
		$("#edit input").attr("disabled",false);
		$("#edit select").attr("disabled",false);
		$("#opbutton input").attr("disabled",false);
		selectNodeType(iframeObj.find("#resParentid").val());
		
	}else{
		$("#opmothed").val(flag);
		var delurl = "delMenus.action?resId="+$("#resId").val();
		postAjax(delurl);
	}
}
//后台方法提交相应
function  postAjax(saveurl){
	//alert($(":input,:checkbox,:radio,#edit").serialize());
	$.ajax({
			url:saveurl,
			type:"POST",
			data:$(":input,:checkbox,:radio,#edit").serialize(),
			success:function(res){
				 window.parent.initTree();
				 loadTree();
			}
		});
}