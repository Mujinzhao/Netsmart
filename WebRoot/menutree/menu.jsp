<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="/WEB-INF/mytable.tld" prefix="mytable"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<html>
<head>
<base href="<%=basePath%>" />
<title>模板数据录入</title>
<link rel="stylesheet"  href="<%=basePath%>/zTree_v3/css/demo.css" type="text/css" />
<link rel="stylesheet"  href="<%=basePath%>/zTree_v3/css/zTreeStyle/zTreeStyle.css" type="text/css" />
<script type="text/javascript" src="<%=basePath%>/core/jquery-1.8.0.min.js"></script>
<script type="text/javascript" src="<%=basePath%>/zTree_v3/js/jquery.ztree.core-3.5.js"></script>
<script type="text/javascript" src="<%=basePath%>/core/myutil.js"></script>
<script type="text/javascript" src="<%=basePath%>/menutree/js/menu.js"></script>
<style>
#treeDiv {
	background-color: #FFFFFF;
	height: 220px;
	width:220px;
	OVERFLOW-X: hidden;
	OVERFLOW-Y: auto;
	OVERFLOW: scroll;
}

#t_content {
	width: 730px;
	height: 100px;
	margin: 0px 0px 0px 0px;
	margin-bottom: 0px;
	border-bottom: 1px solid #C1DAD7;
	background-color: #FAFCFD
}

#button_div {
	height: 40px;
}

.hr_div {
	margin: 0px;
}
</style>
</head>
<body style="margin: 0px; padding: 0px">
	<div class="hr_div" style="background-color: #FAFCFD">
		<h3 align="center">资源详细信息</h3>
	</div>
	<div id="edit">
	    <br/>
		<table id="t_content" width="906"
			style="background-color: #FAFCFD;text-align: left;" cellpadding="0"
			cellspacing="0">
			<tr>
				<td style="text-align: right;" width="150">资源 ID：</td>
				<td width="578"><input type="text" id="resId" name="resId" value="" disabled="disabled" style="width:220px;" /></td>
			</tr>
			<tr id="tr_resParentid">
				<td style="text-align: right;">上级资源：</td>
				<td style="overflow-x: hidden">
				<input type="text" readonly id="resParentname" disabled="disabled" value="" style="width:220px;" onclick="showMenu();" />
				 <input type="hidden" id="resParentid" name="resParentid" value=""/>
				 <input id="opmothed" name="opmothed" type="hidden"/>
			     <input id="res_pkid" name="res_pkid" type="hidden"/>
				</td>

			</tr>
			<tr>
				<td style="text-align: right;">资源名称：</td>
				<td><input type="text" id="resClname" readonly="readonly" name="resClname"
					value="" style="width:220px;" /></td>
			</tr>
			<tr id="resPtype">
				<td style="text-align: right;">资源类型：</td>
				<td><select id="resPtype" name="resPtype" disabled="disabled"  readonly="readonly" onchange="change();" style="width:220px;">
						<option value="FOLDER">目录</option>
						<option value="MENU">功能菜单</option>
				</select></td>
			</tr>
			<tr id="res_URL" class="type">
				<td style="text-align: right;">资源URL：</td>
				<td><input type="text" id="resUrl" readonly="readonly" name="resUrl"
					value="" style="width:220px;" /></td>
			</tr>
			<tr>
				<td style="text-align: right;">资源 值：</td>
				<td><input type="text" id="resValue" readonly="readonly" name="resValue"
					value="" style="width:220px;" /></td>
			</tr>
			<tr>
				<td style="text-align: right;">搜索关键字：</td>
				<td><input type="text" id="resSelkeyword" readonly="readonly"
					name="resSelkeyword" value=""  style="width:220px;" /></td>
			</tr>
			<tr>
				<td style="text-align: right;">描述：</td>
				<td><input type="text" id="resDescription" readonly="readonly"
					name="resDescription" value="" style="width:220px;" /></td>
			</tr>
			<tr>
				<td style="text-align: right;">打开方式：</td>
				<td><select id="sltopentype" disabled="disabled" readonly="readonly">
						<option value="TABWINDOW">TAB页</option>
						<option value="NEWWINDOW">新窗口</option>
				</select> <input type="hidden" id="resOpenType" name="resOpenType"
					value="" />
			     </td>
			</tr>
		</table>
	</div>
	<div id="opbutton">
		<br /> <br />
		<table width="731" style="background-color: #FAFCFD" align="left"
			cellpadding="0" cellspacing="0">
			<tr>
				<td width="723" colspan="2" align="center"><input type="button"
					value="保存" id="save" class="_button" disabled="disabled"
					onclick="saveMenus();" /> <input type="button" value="取消"
					id="cancle" class="_button" disabled="disabled" onclick="reset();" />
				</td>
			</tr>
		</table>
	</div>
	<div id="treeDiv" class="treeDiv" style="display:none; position: absolute;">
		<ul id="menutree" class="ztree" style="margin-top:0; width:180px; height: 300px;"></ul>
	</div>
</body>
</html>
