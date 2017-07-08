<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@page import="java.util.*"%>
<%@page import="java.text.SimpleDateFormat"%>
<%@ page language="java" pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path + "/";
	SimpleDateFormat sdf = new SimpleDateFormat("yyyy年MM月dd日 E a h:mm ", Locale.CHINESE);
	Calendar cl = new GregorianCalendar();
	String day = sdf.format(cl.getTime());
%>
<html>
<head>
<title>采集平台配置信息</title>
<link rel="stylesheet" type="text/css"
	href="easyui/themes/default/easyui.css" />
<link rel="stylesheet" type="text/css" href="easyui/themes/icon.css" />
<link rel="stylesheet" type="text/css" href="easyui/demo/demo.css" />

<script type="text/javascript" src="core/jquery-1.8.0.min.js"></script>
<script type="text/javascript" src="easyui/jquery.easyui.min.js"></script>
<link rel="stylesheet" href="zTree_v3/css/demo.css" type="text/css" />
<link rel="stylesheet" href="zTree_v3/css/zTreeStyle/zTreeStyle.css"
	type="text/css" />
<script type="text/javascript"
	src="zTree_v3/js/jquery.ztree.core-3.5.js"></script>
<script type="text/javascript"
	src="zTree_v3/js/jquery.ztree.exedit-3.5.js"></script>
<script src="core/myutil.js"></script>
<script src="core/rightclickmenu.js"></script>
<script type="text/javascript" src="easyui/src/jquery.tabs.js"></script>
<script src="core/index.js"></script>
<style type="text/css">
.ztree * {
	font-size: 10pt;
	font-family: "Microsoft Yahei", Verdana, Simsun, "Segoe UI Web Light",
		"Segoe UI Light", "Segoe UI Web Regular", "Segoe UI",
		"Segoe UI Symbol", "Helvetica Neue", Arial
}

.ztree li ul {
	margin: 0;
	padding: 0
}

.ztree li {
	line-height: 30px;
}

.ztree li a {
	width: 200px;
	height: 30px;
	padding-top: 0px;
}

.ztree li a:hover {
	text-decoration: none;
	background-color: #E7E7E7;
}

.ztree li a span.button.switch {
	visibility: hidden
}

.ztree.showIcon li a span.button.switch {
	visibility: visible
}

.ztree li a.curSelectedNode {
	background-color: #D4D4D4;
	border: 0;
	height: 30px;
}

.ztree li span {
	line-height: 30px;
}

.ztree li span.button {
	margin-top: -7px;
}

.ztree li span.button.switch {
	width: 16px;
	height: 16px;
}

.ztree li a.level0 span {
	font-size: 150%;
	font-weight: bold;
}

.ztree li span.button {
	background-image: url("./left_menuForOutLook.png");
	*background-image: url("./left_menuForOutLook.gif")
}

.ztree li span.button.switch.level0 {
	width: 20px;
	height: 20px
}

.ztree li span.button.switch.level1 {
	width: 20px;
	height: 20px
}

.ztree li span.button.noline_open {
	background-position: 0 0;
}

.ztree li span.button.noline_close {
	background-position: -18px 0;
}

.ztree li span.button.noline_open.level0 {
	background-position: 0 -18px;
}

.ztree li span.button.noline_close.level0 {
	background-position: -18px -18px;
}
</style>
</head>
<body class="easyui-layout" style="overflow-y: hidden" scroll="no">
	<noscript>
		<div
			style="position: absolute; z-index: 100000; height: 2046px; top: 0px; left: 0px;
            width: 100%; background: white; text-align: center;">
			<img src="images/noscript.gif" alt='抱歉，请开启脚本支持！' />
		</div>
	</noscript>
	<div region="north" split="true"
		style="overflow: hidden; height: 50px; background: #D2E0F2 repeat-x center 50%;
        line-height: 20px;">
		<div style="cursor: pointer; text-align: left;font-weight: bold;">欢迎使用
			NetsSmart ver 1.0</div>
		<div style="cursor: pointer; text-align: right;font-weight: bold;"><%=day%></div>
	</div>
	<!-- <div region="south" style="height: 20px; background: #D2E0F2;">
        <div style="text-align: center; font-weight: bold;">
            NetsSmart ver 1.0</div>
    </div>-->
	<div region="west" split="true" title="导航菜单"
		style="width: 210px;overflow:hidden;" icon="icon-redo">
		<div id="menu" class="easyui-accordion" fit="true" border="false">
			<div title="网站模板配置"
				style="overflow:auto; width:16%;padding: 10px;overflow-x:hidden;background-color: transparent;"
				icon="icon-edit">
				<div title="网站配置">
					<!--   <ul>
                        <li>
                            <div>
                                <a target="mainFrame" href="task/tasklist.jsp">网站设置</a>
                            </div>
                        </li>
                    </ul> -->
					<input id="website" type="hidden" value="industry|excel" />
					<ul id="treeWebsite" class="ztree"></ul>
				</div>
				<!--  <div class="zTreeDemoBackground left">
			         <ul id="treeDemo" class="ztree"></ul>
			     </div> -->
			</div>
			<!-- <div title="采集数据展现" style="padding: 10px;" icon="icon-redo">
                <div title="采集数据展现">
                    <ul>
                        <li>
                            <div>
                                <a target="mainFrame" href="createtable/nestedtabs.jsp">Excel模板解析</a>
                            </div>
                        </li>
                        <li>
                            <div>
                            createtable/tablegroup.jsp
                                <a id="createframe" target="mainFrame" href="configpage.action">创建表信息</a>
                            </div>
                        </li>
                    </ul>
                    <input id="dbtable" type="hidden" value="dbtable|dbtable"/>
                    <ul id="treedbtable" class="ztree"></ul>
                </div>
            </div> -->
			<div title="关于" icon="icon-help">
				<h4>NetsSmart Ver 1.0</h4>
			</div>
		</div>
	</div>
	<!--   <div region="center" id="mainPanle" style="background: #eee;overflow:hidden;">
    <div id="tabs" class="easyui-tabs" data-options="tools:'#tab-tools'"  fit="true" border="false">
            <div title="主页" style="padding: 20px;" id="home">
                <h1>
                    Welcome...</h1>
            </div>
	</div> -->

	<div data-options="region:'center',border:false">
		<div id="tabs" class="easyui-tabs" data-options="fit:true">
			<div title="首页" data-options="closable:false"
				style="overflow: hidden; background:#fff">
				<iframe scrolling="auto" frameborder="0" src=""
					style="width: 100%; height: 100%;"></iframe>
			</div>
		</div>
	</div>
	<div id="tab_menu" class="easyui-menu" style="width: 150px;">
		<div id="tab_menu-tabrefresh">刷新当前</div>
		<div id="tab_menu-openFrame">在新的窗体打开</div>
		<div id="tab_menu-tabcloseall">关闭所有</div>
		<div id="tab_menu-tabcloseother">关闭其他</div>
		<div class="menu-sep"></div>
		<div id="tab_menu-tabcloseright">关闭右边</div>
		<div id="tab_menu-tabcloseleft">关闭左边</div>
		<div id="tab_menu-tabclose">关闭当前</div>
		<div id="menu" class="easyui-menu" style="width: 150px;"></div>
	</div>
</body>
</html>