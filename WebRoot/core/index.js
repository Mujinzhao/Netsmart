var curMenu = null, zTree_Menu = null, rMenu = null;
var treeURL = "menutree.action";
var setting = {
	view : {
		showLine : false,
		showIcon : true,
		selectedMulti : false,
		dblClickExpand : false,
		addDiyDom : addDiyDom
	},
	data : {
		simpleData : {
			enable : true
		}
	},
	callback : {
		beforeClick : beforeClick
	}
};

$(function() {
	InitLeftTree();
	$('body').layout();
});
function InitLeftdbtableTree(obj, treeflag) {
	// 后台数据加载
	var jsondata = mygetJson(treeURL, {
		loadsql : "true",
		sqlkey : "queryResTree",
		treeflag : $("#" + treeflag).val()
	//"industry|website"
	});
	var treeObj = $("#" + obj);
	$.fn.zTree.init(treeObj, setting, jsondata);
	zTree_Menu = $.fn.zTree.getZTreeObj(obj);

	treeObj.hover(function() {
		if (!treeObj.hasClass("showIcon")) {
			treeObj.addClass("showIcon");
		}
	}, function() {
		treeObj.removeClass("showIcon");
	});
}

function InitLeftTree() {
	// 后台数据加载
	var jsondata = mygetJson(treeURL, {
		loadsql : "true",
		sqlkey : "queryResTree",
		treeflag : "industry|website"
	});
	var treeObj = $("#treeWebsite");
	$.fn.zTree.init(treeObj, setting, jsondata);
	zTree_Menu = $.fn.zTree.getZTreeObj("treeWebsite");

	treeObj.hover(function() {
		if (!treeObj.hasClass("showIcon")) {
			treeObj.addClass("showIcon");
		}
	}, function() {
		treeObj.removeClass("showIcon");
	});
}

function beforeClick(treeId, treeNode) {
	// 第一层级，点击无效
	if (treeNode.level == 0) {
		var zTree = $.fn.zTree.getZTreeObj("treeWebsite");
		zTree.expandNode(treeNode);
		return false;
	} else {
		// 无URL 不跳转
		if (treeNode.res_url == "" || treeNode.res_url == null) {
			var zTree = $.fn.zTree.getZTreeObj("treeWebsite");
			zTree.expandNode(treeNode);
			return false;
		} else {
			// 获取调整的页面名称，URL
			var tabTitle = treeNode.res_clname;
			var url = treeNode.res_url;
			addTab(tabTitle, url);
			return true;
		}
	}
	return true;
}

function addDiyDom(treeId, treeNode) {
	var spaceWidth = 3;
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
function addTab(subtitle, href) {
	if (!$("#tabs").tabs('exists', subtitle)) {
		$("#tabs").tabs('add', {
			title : subtitle,
			content : createFrame(href),
			closable : true
		});
	} else {
		$("#tabs").tabs('select', subtitle);
		$("#tab_menu-tabrefresh").trigger("click");
	}
	//$(".layout-button-left").trigger("click");  
}
function killTab(subtitle) {
	$('#tabs').tabs('close', subtitle);
}
function createFrame(url) {
	var content = '<iframe  id="mainFrame" scrolling="no" frameborder="0"  src="'
			+ url
			+ '" style="margin:0px;overflow:scroll;overflow-x:hidden;overflow-y:auto;width:100%;height:120%;"></iframe>';
	return content;
}