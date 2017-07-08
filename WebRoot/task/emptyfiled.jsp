<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib uri="/WEB-INF/mytable.tld" prefix="mytable"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<html>
<head>
<title>非空字段指定</title>
<link rel="stylesheet" type="text/css" href="<%=basePath%>/easyui/themes/default/easyui.css"/>
<link rel="stylesheet" type="text/css" href="<%=basePath%>/easyui/themes/icon.css"/>
<link rel="stylesheet" type="text/css" href="<%=basePath%>/easyui/demo/demo.css"/>
<link rel="stylesheet" type="text/css" href="<%=basePath%>common/mytable/css/style.css"></link>
<script type="text/javascript" src="<%=basePath%>core/jquery-1.8.0.min.js"></script>
<script src="<%=basePath%>common/mytable/js/table.js" type="text/javascript"></script>
<script type="text/javascript" src="<%=basePath%>/easyui/jquery.easyui.min.js"></script>
<script type="text/javascript" src="<%=basePath%>/task/js/parsefiled.js"></script>
</head>
<body>
<div id="addform" >
				   <a href="javascript:void(0)" class="easyui-linkbutton" style="width:90px;height:24px;" onclick="emptyFieldOp(1);">指定非空</a>
			       <a href="javascript:void(0)" class="easyui-linkbutton" style="width:90px;height:24px;" onclick="emptyFieldOp(0);">撤销非空</a>
			       <a href="javascript:void(0)" class="easyui-linkbutton" style="width:70px;height:24px;" onclick="emptyclose()">关闭</a>
				   <input type="hidden" name="displaytype"  id="displaytype" value="${displaytype}" /> 
				   <input type="hidden" name="structuredid" id="structuredid" value="${structuredid}" /> 
				   <input type="hidden" name="asstable"     id="asstable" value="${asstable}" /> 
				   <input type="hidden" name="owner"        id="owner" value="${owner}" /> 
				   <input type="hidden" name="sqlkey"       id="sqlkey" value="updatemptyAcqfield" />
				   
	</div>
	<br/>
	<div style="width:100%;height:100%;">
	<mytable:mytable sqlKey="getEmptFiled" 
		    tableName="parsefiled"
			headCols="acqfield:存储字段,
				      acqfieldesc:字段名称,
				      asstable:存储表,
				      asstablename:存储表名称,
				      emptyacqfield:允许空值"
			pageSize="8"
			colPK="id"
			sqlParam='structuredid:${structuredid},asstable:${asstable},owner:${owner}'
			selectRowBtn="1">
	</mytable:mytable>
	</div>
</body>
</html>