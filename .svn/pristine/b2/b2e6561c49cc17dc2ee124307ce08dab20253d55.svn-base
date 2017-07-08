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
	<title>解析任务组</title>
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
		      <input type="hidden"  id="taskcode" name="taskcode" value="${taskcode}"/>
		      <input type="hidden"  id="structuredid"  name="structuredid" value="${structuredid}"/>
		      <input type="hidden"  id="tablenamedesc" name="tablenamedesc" value="${tablename}"/>
		      <input type="hidden"  id="parseurl"      name="parseurl"  value="${parseurl}" />
		      <input type="hidden"  id="parsertype"    name="parsertype" value="${parsertype}"/>
		      <input type="hidden"  id="encode"    name="encode" value="${encode}"/>
		      <input type="hidden"  id="tag"  name="tag" value="${tag}"/>
		      <input type="hidden" id="owner" name="owner" value="${owner}" />
		      <input type="hidden" id="jumpurl" name="jumpurl" value="parserpage.action?" />
		</div>
			<div id="tabspage"  class="easyui-tabs" data-options="fit:true,plain:true">
			     <div id="listpage" title="解析配置" style="padding:0px;" >
				     <iframe id="parserconfig" name="mainFrame" scrolling="auto" frameborder="0"  src="" style="margin:0px;width:100%;height:100%;"></iframe>
				 </div>
			</div>
	</div>
 	<%--<div style="margin:5px 0;width:100%;height:550px;" >
			<div id="tabspage"  class="easyui-tabs" data-options="fit:true,plain:true">
			     <div id="listpage" title="解析配置" style="padding:0px;" >
				     <iframe id="parserconfig" name="mainFrame" scrolling="no" frameborder="0"  src="" style="margin:0px;width:100%;height:100%;"></iframe>
				 </div>
			</div>
		    <div id="hidparam">
		      <input type="hidden"  id="asstable"      name="asstable" value="${asstable}"/>
		      <input type="hidden"  id="taskcode" name="taskcode" value="${taskcode}"/>
		      <input type="hidden"  id="structuredid"  name="structuredid" value="${structuredid}"/>
		      <input type="hidden"  id="tablenamedesc" name="tablenamedesc" value="${tablename}"/>
		      <input type="hidden"  id="parseurl"      name="parseurl"  value="${parseurl}" />
		      <input type="hidden"  id="parsertype"    name="parsertype" value="${parsertype}"/>
		      <input type="hidden"  id="encode"    name="encode" value="${encode}"/>
		      <input type="hidden"  id="tag"  name="tag" value="${tag}"/>
            </div>
	</div>
--%></body>
</html>