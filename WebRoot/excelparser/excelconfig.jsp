<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib uri="/WEB-INF/mytable.tld" prefix="mytable"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<html>
	<head>
	<title>Excel模板解析任务组</title>
	<link rel="stylesheet" type="text/css" href="<%=basePath%>/easyui/themes/default/easyui.css"/>
	<link rel="stylesheet" type="text/css" href="<%=basePath%>/easyui/themes/icon.css"/>
	<link rel="stylesheet" type="text/css" href="<%=basePath%>/easyui/demo/demo.css"/>
	<link rel="stylesheet" type="text/css" href="<%=basePath%>common/mytable/css/style.css"></link>
	<script type="text/javascript" src="<%=basePath%>/core/jquery-1.8.0.min.js"></script>
	<script type="text/javascript" src="<%=basePath%>/easyui/jquery.easyui.min.js"></script>
	<script src="<%=basePath%>common/mytable/js/table.js" type="text/javascript"></script>
	<script src="<%=basePath%>parseinterface/js/parsergroup.js" type="text/javascript"></script>
</head>
 <body>
 <div style="margin:5px 0;width:100%;height:520px;" >
 	   <div id="hidparam">
 	          <input type="hidden"  id="asstable"      name="asstable" value="${asstable}"/>
		      <input type="hidden"  id="templateid"    name="templateid" value="${templateId}"/>
		      <input type="hidden"  id="structuredid"  name="structuredid" value="${structuredid}"/>
		      <input type="hidden" id="owner" name="owner" value="${owner}" />
		      <input type="hidden" name="sheetname" id="sheetname" value="${sheetname}" />
		      <input type="hidden" id="jumpurl" name="jumpurl" value="templateconfig.action?jumpage=excelfileds&" />
		</div>
			<div id="tabspage"  class="easyui-tabs" data-options="fit:true,plain:true">
			     <div id="listpage" title="Excel解析模板配置" style="padding:0px;" >
				     <iframe id="parserconfig" name="mainFrame" scrolling="auto" frameborder="0"  src="" style="margin:0px;width:100%;height:100%;"></iframe>
				 </div>
			</div>
	</div>
 </body>
</html>