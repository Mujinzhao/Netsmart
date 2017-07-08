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
<title>Excel任务界面</title>
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
    <script src="<%=basePath%>excelparser/js/excelist.js" type="text/javascript"></script>
</head>
<body>
	<br />
<div id="addmyform">
		<fieldset style="border:1px solid #AECAF0;">
			<legend>解析文件录入</legend>
				<table>
					<tr>
						<td width="7%" align="right" class="border_left"
							style="font-size: 12px;text-align:right;">选择文件</td>
							<td width="37%" class="border_left">
								<table cellSpacing="0" cellPadding="0"  border="0" style="font-size: 12px;">
									<s:file id="uploadify" name="uploadify" cssStyle="width:100px;"  />
								</table>
				            </td>
					</tr>
				</table>
		</fieldset>
		<div style="text-align:right;">
		    <input type="hidden" id="pageflag" name="pageflag" value="files"/>
		    <input type="hidden" id="id" name="id"/>
		    <input type="hidden" id="templateid" name="templateid" value="${templateId}"/>
		    <input type="hidden" id="templatetype" name="templatetype" value="${templatetype}"/>
		    <input type="hidden" id="excelname" name="excelname"/>
		    <input type="hidden" id="exceltype" name="exceltype"/>
		    <input type="hidden" id="excelsize" name="excelsize"/>
		    
		</div>
	</div>
	<!-- 搜索框 -->
	<form id="form" name="form">
		<fieldset style="border:1px solid #AECAF0;">
			<legend>查询信息</legend>
			<div class="searchbar">
				<table width="100%" cellpadding="0" cellspacing="0" border="0"
					bordercolor="#a0c6e5" style="border-collapse:collapse;" id="conditions">
					<tr>
						<td width="7%" height="24" class="border_left"
							style="font-size: 12px;text-align:right;">文件名称</td>
						<td width="24%" style="text-align:left;">
						   <input type="text" id="excelname" fortable="excelist" roletype="param" style="width:220px" name="excelname" />
						</td>
							<td width="7%" height="24" class="border_left"
							style="font-size: 12px;text-align:right;">文件类型</td>
						<td width="41%" style="text-align:left;">
						   <input type="text" id="exceltype" fortable="excelist" roletype="param" style="width:220px" name="exceltype" />
						</td>
						<td width="21%">
						 <a class="easyui-linkbutton" style="width:60px;height:24px;"  fortable="excelist" roletype="search" id="searchtask">查询</a>
                         <a class="easyui-linkbutton" style="width:60px;height:24px;" id="searchreset">重置</a>
						 <a href="javascript:void(0)" style="width:60px;height:24px;" class="easyui-linkbutton" onclick="xfileclose()">关闭</a>
						</td>
					</tr>
				</table>
			</div>
		</fieldset>
		<div>
			<mytable:mytable sqlKey="getExcelList" tableName="excelist"
				headCols="EXCEL_NAME:文件名称, 
					      EXCEL_TYPE:文件类型, 
					      EXCEL_FILE_SIZE:文件大小,
					      EXCEL_PATH:文件路径,
					      PARSERSTATUS:解析状态,
					      SAVETABLE:存储数据库,
					      PARSERNUM:入库条数,
					      CREATE_TIME:创建时间"
				pageSize="15"
				sqlParam='templateId:${templateId}'
				actionCol="delFile:[删除]">
			</mytable:mytable>
		</div>
	</form>
</body>
</html>
