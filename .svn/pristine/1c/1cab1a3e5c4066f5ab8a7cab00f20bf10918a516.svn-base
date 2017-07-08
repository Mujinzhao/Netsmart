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
<title>Excel解析任务界面</title>
<base href="<%=basePath%>" />
<link rel="stylesheet" type="text/css" href="<%=basePath%>/easyui/themes/default/easyui.css"/>
	<link rel="stylesheet" type="text/css" href="<%=basePath%>/easyui/themes/icon.css"/>
	<link rel="stylesheet" type="text/css" href="<%=basePath%>/easyui/demo/demo.css"/>
	<link rel="stylesheet" type="text/css" href="<%=basePath%>uploadify/uploadify.css"></link>
	<script type="text/javascript" src="<%=basePath%>/core/jquery-1.8.0.min.js"></script>
	<script type="text/javascript" src="<%=basePath%>/easyui/jquery.easyui.min.js"></script>
	<link rel="stylesheet" type="text/css" href="<%=basePath%>common/mytable/css/style.css"></link>
	<script src="<%=basePath%>uploadify/jquery.uploadify.js" type="text/javascript"></script>
	<script src="<%=basePath%>common/js/ajaxfileupload.js" type="text/javascript"></script>
	
	<script src="<%=basePath%>common/mytable/js/table.js" type="text/javascript"></script>
    <script src="<%=basePath%>excelparser/js/excelmanage.js" type="text/javascript"></script>
<style type="text/css">
body {
	margin-left: 0px;
	margin-top: 0px;
	margin-right: 0px;
	margin-bottom: 0px;
	font-size: 12px;
}

.searchBar {
	height: 30px;
	padding: 7px 0 3px;
	text-align: center;
}
</style>
</head>
<body>
	<br />
	<div id="addmyform">
		<fieldset style="border:1px solid #AECAF0;">
			<legend>解析模板录入</legend>
				<table width="100%">
					<tr>
						<td width="7%" align="right" class="border_left"
							style="font-size: 12px;text-align:right;">选择模板</td>
							<td width="37%" class="border_left">
								<table cellSpacing="0" cellPadding="0"  border="0" style="font-size: 12px;">
									<s:file id="uploadify" name="uploadify" cssStyle="width:100px;"  />
								</table>
				            </td>
				            <td width="6%" class="border_right">模板类型</td>
				            <td width="18%"  class="border_left"> 
				                <input type="text" id="templatetype" name="templatetype"/>
				            </td>
				            <td width="6%" class="border_right">激活模板</td>
				            <td width="5%" class="border_left"> 
				                <input type="checkbox" id="stauts" name="stauts" value="T"/>
				            </td>
						<td width="21%">
                          <a class="easyui-linkbutton" style="width:60px;height:24px;" onclick="uploadtemplate()" id="uploadexcel">上传</a>
                          <a class="easyui-linkbutton" style="width:60px;height:24px;" id="reset">重置</a>
						</td>
					</tr>
				</table>
		</fieldset>
		<div style="text-align:right;">
		    <input type="hidden" id="pageflag" name="pageflag" value="template"/>
		    <input type="hidden" id="otemplatetype" name="otemplatetype"/>
		    <input type="hidden" id="templatename" name="templatename"/>
		    <input type="hidden" id="templatefiletype" name="templatefiletype"/>
		    <input type="hidden" id="templatesize" name="templatesize"/>
		    <input type="hidden" id="templateid" name="templateid"/>
		</div>
	</div>
	<!-- 搜索框 -->
	<form id="form" name="form">
		<fieldset style="border:1px solid #AECAF0;">
			<legend>查询信息</legend>
			<div class="searchbar">

				<table width="100%" cellpadding="0" cellspacing="0" border="0"
					bordercolor="#a0c6e5" style="border-collapse:collapse;"
					id="conditions">
					<tr>
						<td width="7%" height="24" class="border_left"
							style="font-size: 12px;text-align:right;">模板名称</td>
						<td width="24%" style="text-align:left;">
						   <input type="text" id="templatename" fortable="templatelist" roletype="param" style="width:220px" name="templatename" />
						</td>
							<td width="7%" height="24" class="border_left"
							style="font-size: 12px;text-align:right;">模板类型</td>
						<td width="41%" style="text-align:left;">
						   <input type="text" id="templatetype" fortable="templatelist" roletype="param" style="width:220px" name="templatetype" />
						</td>
						<td width="21%">
						 <a class="easyui-linkbutton" style="width:60px;height:24px;"  fortable="templatelist" roletype="search" id="searchtask">查询</a>
                         <a class="easyui-linkbutton" style="width:60px;height:24px;" id="searchreset">重置</a>
						</td>
					</tr>
				</table>
			</div>
			</fieldset>
			<br/>
		<div>
		<!-- ,
					      fileparsnum:解析文件数 -->
			<mytable:mytable sqlKey="getExcelTemplateList" tableName="templatelist"
				headCols="template_type:模板类型, 
					      template_name:模板名称, 
					      template_size:模板大小,
					      create_time:创建时间,
					      structernum:解析结构数,
					      filetotalnum:文件总数"
				pageSize="15"
				actionCol="templatestruct:[模板维护],templatexml:[XML模板],fileop:[文件维护],deltemplate:[删除],modfiytemplate:[修改]">
			</mytable:mytable>
		</div>
		
	</form>
	<div id="xlsfiles" class="easyui-dialog" scrolling="yes" title="解析文件维护" data-options="iconCls:'icon-save',closed:true" style="overflow-y:auto; overflow-x:auto;width:100%;height:200%;">
			<iframe id="excelfiles"  name="mainFrame" scrolling="no" frameborder="0"  src="" style="width:100%;height:100%;"></iframe>
	</div>
</body>
</html>
