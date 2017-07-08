var curMenu = null, zTree_Menu = null,rMenu=null;
var treeURL="menutree.action";
var  iframeObj;
var lastValue = "", nodeList = [], fontCss = {};
var setting = {
		key: {
			title: "res_clname"
		},
		view : {
			showLine : false,
			showIcon : true,
			selectedMulti : false,
			dblClickExpand : true,
			addDiyDom : addDiyDom,
			fontCss: getFontCss
		},
		data : {
			simpleData : {
				enable : true
			}
		},
		callback : {
			beforeClick : beforeClick
			//onRightClick: OnRightClick
		}
		
	};
    
	function getFontCss(treeId, treeNode) {
		return (!!treeNode.highlight) ? {color:"red", "font-weight":"bold"} : {color:"#333", "font-weight":"normal"};
	}
	function addDiyDom(treeId, treeNode) {
		var spaceWidth = 5;
		var switchObj = $("#" + treeNode.tId + "_switch"), icoObj = $("#"
				+ treeNode.tId + "_ico");
		switchObj.remove();
		icoObj.before(switchObj);

		if (treeNode.level > 1) {
			var spaceStr = "<span style='display: inline-block;width:"
					+ (spaceWidth * treeNode.level) + "px'></span>";
			switchObj.before(spaceStr);
		}
	}
	function beforeClick(treeId, treeNode) {
		// 根节点
		iframeObj = $("#parampage").contents();
		if (treeNode.level == 0) {
			iframeObj.find("#tr_resParentid").hide();
			
			$("#delete_button").attr("disabled",true);
			//释放新增按钮、修改按钮
			$("#add_button").attr("disabled",false);
			$("#edit_button").attr("disabled",false);
			treeNode.res_parentid=treeNode.level;
			//return false;
		}else{
			 $("#add_button").attr("disabled",false);
			 $("#delete_button").attr("disabled",false);
			 $("#edit_button").attr("disabled",false);
			 iframeObj.find("#tr_resParentid").show();
			 $("#parampage")[0].contentWindow.selectNodeType(treeNode.res_parentid);
		}
		  var zTree = $.fn.zTree.getZTreeObj("treeDemo");
		  zTree.expandNode(treeNode);
		  // 动态选择tree 父级节点数据
		  //iframemothed.selectNodeType(treeNode.template_resparentid);
		  iframeObj.find("#edit input").attr("readonly",true);
		  iframeObj.find("#edit select").attr("disabled",true);
		  iframeObj.find("#opbutton input").attr("disabled",true);
		  $("#parampage")[0].contentWindow.bindInputvalue(treeNode);
		return true;
	}
var key;
$(function() {
	initTree();
	
	key = $("#key");
	key.bind("focus", focusKey).bind("blur", blurKey).bind("propertychange", searchNode).bind("input", searchNode);
	$("#searchkey").click(function(){
		searchNode("");
	});
	$('body').layout();
	// 功能树新增事件
	$("#add_button").click(function(){
		//新增按钮、修改按钮
		$("#delete_button").attr("disabled",true);
		$("#add_button").attr("disabled",true);
		$("#edit_button").attr("disabled",true);
		 $("#parampage")[0].contentWindow.treeclickButton("add");
	});
	//功能树修改事件
	$("#edit_button").click(function(){
		//alert("sssssssssss" +$("#resParentid").val());
		$("#parampage")[0].contentWindow.treeclickButton("modfiy");
	});
	//删除事件
	$("#delete_button").click(function(){
		$("#parampage")[0].contentWindow.treeclickButton("del");
	});
});
function ran(m) {
	m = m > 16 ? 16 : m;
	var num = Math.random().toString();
	if (num.substr(num.length - m, 1) === '0') {
		return rand(m);
	}
	return num.substring(num.length - m);
} 
function  initTree(){
	// 后台数据加载
	var jsondata = mygetJson(treeURL, {
		loadsql : "true",
		sqlkey : "queryResTree",
		treeflag : "industry|website"
	});
	var treeObj = $("#treeDemo");
	$.fn.zTree.init(treeObj, setting, jsondata);
	zTree_Menu = $.fn.zTree.getZTreeObj("treeDemo");
	zTree_Menu.expandAll(true); 
	treeObj.hover(function() {
		if (!treeObj.hasClass("showIcon")) {
			treeObj.addClass("showIcon");
		}
	}, function() {
		treeObj.removeClass("showIcon");
	});
	return zTree_Menu;
}

function searchNode(e) {
	var zTree = $.fn.zTree.getZTreeObj("treeDemo");
	var value = $.trim(key.get(0).value);
	if (value === ""){
		updateNodes(false);
	}else{
		nodeList = zTree.getNodesByParamFuzzy("res_clname",value);
		updateNodes(true);
	}
}
function focusKey(e) {
	if (key.hasClass("empty")) {
		key.removeClass("empty");
	}
}
function blurKey(e) {
	if (key.get(0).value === "") {
		key.addClass("empty");
	}
}
function updateNodes(highlight) {
	var zTree = $.fn.zTree.getZTreeObj("treeDemo");
	for( var i=0, l=nodeList.length; i<l; i++) {
		nodeList[i].highlight = highlight;
		zTree.updateNode(nodeList[i]);
	}
}
