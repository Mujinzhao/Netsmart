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
<title>搜索关键词添加</title>
	<link rel="stylesheet" type="text/css" href="<%=basePath%>easyui/themes/default/easyui.css"/>
	<link rel="stylesheet" type="text/css" href="<%=basePath%>easyui/themes/icon.css"/>
	<link rel="stylesheet" type="text/css" href="<%=basePath%>easyui/demo/demo.css"/>
	<link href="<%=basePath%>zTree_v3/vform/css/style.css" rel="stylesheet" type="text/css" />
	<link rel="stylesheet" type="text/css" href="<%=basePath%>common/mytable/css/style.css"></link>
	<script type="text/javascript" src="<%=basePath%>core/jquery-1.8.0.min.js"></script>
	<script type="text/javascript" src="<%=basePath%>easyui/jquery.easyui.min.js"></script>

	<script src="<%=basePath%>common/mytable/js/table.js" type="text/javascript"></script>
    <script src="<%=basePath%>zTree_v3/vform/Validform_v5.3.2.js" type="text/javascript"></script>
    <script src="<%=basePath%>gather/js/yuansearch.js" type="text/javascript"></script>
</head>
<body>

<div id="hidparam"> 
	     <fieldset style="border:1px solid #AECAF0;">
			<legend><h4>关键词添加</h4></legend>
			<table>
			    <tr>
			      <td width="78"  style="text-align:right">URL地址</td>
			      <td> <label id="taskurl">${taskurl}</label> </td>
			      <td height="21" style="text-align:right"> 是否采集 </td>
			      <td  style="text-align:left">
			        <input id="isspider" name="isspider" style="text-align:right;" type="checkbox" value="T" />
				  </td>
			    </tr>
				<tr style="height: 20px;">
				  <td width="64" height="21" style="text-align:right">引擎名称</td>
				  <td width="379"  style="text-align:left">
				     <input type="text" style="width:220px;" id="taskname" name="taskname" disabled="disabled" value="${taskname}"/>
				     <input type="hidden" id="taskcode" name="taskcode" value="${taskcode}"/></td>
			      <td height="21" style="text-align:right">搜索关键词</td>
				  <td style="text-align:left" id="groupdesccheck">
				       <input type="text" style="width:320px;" datatype="*" id="keyword" name="keyword"/></td>
			  </tr>
	  </table>
	</fieldset>
	  <div style="text-align:right;">
          <input type="button" id="nextpaging"  value="下一步"/>
          <input type="hidden" id="pageflag" name="pageflag" value="5"/>
          <input type="hidden" id="id" name="id"/>
          <input type="button" id="addkeywords"  value="关键词录入"/>
          <input type="button" id="rester"  value="重置"/>
	 </div>
</div>

<div style="width:100%;height:100%;">
	<mytable:mytable sqlKey="getSearchkeywords" 
		    tableName="searchkeywords"
			headCols="taskcode:任务编号,
				      engineinterface:引擎接口,
				      keywords:搜索关键词,
				      pagecount:页面配置数"
			pageSize="15"
			colPK="id"
			selectRowBtn="1"
			sqlParam='taskcode:${taskcode}'
			actionCol="delkeywords:[删除],modfiykeywords:[修改]">
	</mytable:mytable>
</div>
</body>
</html>