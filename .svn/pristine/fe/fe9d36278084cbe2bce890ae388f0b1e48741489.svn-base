<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags" %>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<html>
	<head>
	<title>采集任务组</title>
	<link rel="stylesheet" type="text/css" href="<%=basePath%>/easyui/themes/default/easyui.css"/>
	<link rel="stylesheet" type="text/css" href="<%=basePath%>/easyui/themes/icon.css"/>
	<link rel="stylesheet" type="text/css" href="<%=basePath%>/easyui/demo/demo.css"/>
	<script type="text/javascript" src="<%=basePath%>core/jquery-1.8.0.min.js"></script>
	<script type="text/javascript" src="<%=basePath%>easyui/jquery.easyui.min.js"></script>
	<script type="text/javascript" src="<%=basePath%>gather/js/gathergroup.js"></script>
</head>
 <body>
 	<div style="margin:5px 0;width:100%;height:520px;" >
 	   <div id="hidparam">
 	        <input type="hidden" id="pageflag" name="pageflag" value="${tasktype}"/>
			<input type="hidden" id="taskcode" name="taskcode" value="${taskcode}"/>
			<input type="hidden" id="taskname" name="taskname" value="${taskname}"/>
			<input type="hidden" id="tasklogin" name="tasklogin" value="${tasklogin}"/>
			<input type="hidden" id="taskurl" name="taskurl" value="${taskurl}"/>
		</div>
			<div id="tabspage"  class="easyui-tabs" data-options="fit:true,plain:true">
			     <div id="listpage" title="采集配置" style="padding:0px;" >
				     <iframe id="pageconfig" name="mainFrame" scrolling="no" frameborder="0"  src="" style="margin:0px;width:100%;height:100%;"></iframe>
				 </div>
			</div>
	</div>
</body>
</html>