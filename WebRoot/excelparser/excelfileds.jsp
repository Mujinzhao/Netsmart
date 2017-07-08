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
	<script type="text/javascript" src="<%=basePath%>/core/jquery-1.8.0.min.js"></script>
	<script type="text/javascript" src="<%=basePath%>/easyui/jquery.easyui.min.js"></script>
	<script src="<%=basePath%>common/mytable/js/table.js" type="text/javascript"></script>
	<script src="<%=basePath%>parseinterface/js/commonjs.js" type="text/javascript"></script>
	<script type="text/javascript" src="<%=basePath%>/excelparser/js/excelfiled.js"></script>
</head>
<body>
	<div id="addform">
	<fieldset style="border:1px solid #AECAF0;">
	    <legend>解析规则值操作</legend>
		<table>
			<tr>
				<td  style="text-align: right;">解析字段</td>
				<td><input type="hidden" name="asstable"
					id="asstable" value="${asstable}" />
					 <select style="width:120px;" name="parsefiled" id="parsefiled"></select>
			    </td>
			    <td  style="text-align: right;">操作类型</td>
			    <td><span style="text-align:left">
		        <select id="customop" name="customop" style="width:150px;">
		          <option value="0">字段值</option>
		          <option value="1" selected>字段下标</option>
		          <option value="2">字段正确性</option>
	            </select>
	          </span></td>
				<td style="text-align: right;">字段值</td>
				<td  style="text-align:right"><input type="text"
					name="acqfieldval" style="width:160px;" id="acqfieldval" />
				</td>
				<td width="58" style="text-align:right"><a
					href="javascript:void(0)" class="easyui-linkbutton"
					style="width:70px;height:24px;" onclick="customfiled()">提交</a>
				</td>
				<td width="38" style="text-align:right"><a
					href="javascript:void(0)" class="easyui-linkbutton"
					style="width:90px;height:24px;" onclick="emptyFieldOp(1);">指定非空</a>
				</td>
				<td width="38" style="text-align:right"><a
					href="javascript:void(0)" class="easyui-linkbutton"
					style="width:90px;height:24px;" onclick="emptyFieldOp(0);">撤销非空</a>
				</td>
				<td width="38" style="text-align:right"><a
					href="javascript:void(0)" class="easyui-linkbutton"
					style="width:70px;height:24px;" id="nextbut">下一步</a>
				</td>
			</tr>
		</table>
		<div>
			<input type="hidden" name="owner" id="owner" value="${owner}" />
			<input type="hidden" id="templateid" name="templateid" value="${templateId}"/>
			<input type="hidden" name="structuredid" id="structuredid" value="${structuredid}" />
			<input type="hidden" name="sheetname" id="sheetname" value="${sheetname}" />
			<input type="hidden" name="sqlkey"       id="sqlkey" value="updatemptyAcqfield" />
		</div>
	</fieldset>
	</div>
	<br />
	<div style="width:100%;height:100%;">
		<mytable:mytable sqlKey="getTemplateFiled" tableName="templateFiled"
			headCols="acqfieldesc:字段描述,
				      acqfield:存储字段,
				      asstablename:存储表名称,
				      emptyacqfield:允许空值,
				      acqfieldvalue:自定义字段值,
				      filedindex:字段下标"
			pageSize="8" 
			colPK="id"
			sqlParam='structuredid:${structuredid},asstable:${asstable},owner:${owner}'
			selectRowBtn="1">
		</mytable:mytable>
	</div>
</body>
</html>