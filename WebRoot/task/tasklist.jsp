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
<title>解析任务维护界面</title>
<base href="<%=basePath%>" />
<link rel="stylesheet" type="text/css" href="<%=basePath%>/easyui/themes/default/easyui.css"/>
	<link rel="stylesheet" type="text/css" href="<%=basePath%>/easyui/themes/icon.css"/>
	<link rel="stylesheet" type="text/css" href="<%=basePath%>/easyui/demo/demo.css"/>
	<link href="<%=basePath%>zTree_v3/vform/css/style.css" rel="stylesheet" type="text/css" />
	<script type="text/javascript" src="<%=basePath%>/core/jquery-1.8.0.min.js"></script>
	<script type="text/javascript" src="<%=basePath%>/easyui/jquery.easyui.min.js"></script>
	<link rel="stylesheet" type="text/css" href="<%=basePath%>common/mytable/css/style.css"></link>
	
	<script src="<%=basePath%>zTree_v3/vform/Validform_v5.3.2.js" type="text/javascript"></script>
	<script src="<%=basePath%>common/mytable/js/table.js" type="text/javascript"></script>
	
	
<script src="<%=basePath%>task/js/tasklist.js" type="text/javascript"></script>
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
			<legend>任务添加</legend>
			<div id="gathercheck">
				<table width="100%">
					<tr>
						<td height="30" align="right" class="border_left"
							style="font-size: 12px;">采集类型</td>
						<td width="21%" class="border_right"><select
							style="width:220px;height: 20px;" name="tasktype"
							id="tasktype">
								<option value="0" selected="selected">列表采集任务</option>
								<option value="1">模拟采集任务</option>
								<option value="2">元搜索采集任务</option>
								<option value="3">当前源采集任务</option>
						</select>
						</td>
						<td width="5%" class="border_right">下载方式</td>
						<td width="10%" style="text-align:left;"><select
							style="width:100px;height: 20px;" name="selectype" id="selectype">
								<option value="0">普通解析</option>
								<option value="1">JS解析</option>
						</select>
						</td>
						<td width="5%" style="text-align:right;">采集页数</td>
						<td colspan="3" style="text-align:left;"><input type="text"
							name="totalpage" id="totalpage" style="width:80px;" />
							(为空，默认采集全部!)</td>
					</tr>
					<tr>
						<td style="text-align:right;">任务名称</td>
						<td style="text-align:left;"><input style="width:220px;"
							type="text" datatype="*" id="taskname" name="taskname" />
						</td>
						<td class="border_right">
						  <span style="text-align:left;">激活任务</span>
						</td>
						<td style="text-align:left;">
						    <input id="isactive" name="isactive" type="checkbox" value="T" />
						    <span style="text-align:left;">&nbsp;是否采集</span>
						    <input id="isspider" name="isspider" type="checkbox" value="T" />
						</td>
						<td width="5%" style="text-align:right;">网页编码</td>
						<td width="10%"><select style="width:88px;" name="encodeurl"
							id="encodeurl">
								<option value="GBK">GBK</option>
								<option value="UTF-8">UTF-8</option>
								<option value="GB2312">GB2312</option>
						</select>
						</td>
						<td width="5%"><span class="border_right"><span style="text-align:left;">是否排重</span></span></td>
						<td width="6%"><span class="border_right"><span style="text-align:left;">
						  <input id="remark" name="remark" type="checkbox" value="T" />
						</span></span></td>
					</tr>
					<tr>
						<td width="5%" style="text-align:right;"><span
							class="border_left" style="font-size: 12px;">采集地址</span>
						</td>
						<td colspan="8" style="text-align:left;"><span
							class="border_right"><span class="border_left"
								style="font-size: 12px;"> <input name="taskurl"
									type="text" id="taskurl" datatype="url" style="width:375px;" />
							</span>
						</span>
						</td>
					  <td width="6%"><a class="easyui-linkbutton" style="width:100px;height:24px;" id="sub">保存</a></td>
						<td width="15%"><input type="hidden" id="taskcode" name="taskcode" />
                          <input type="hidden" id="sitecode" name="sitecode" value="${websiteid}" /><a class="easyui-linkbutton" style="width:100px;height:24px;" id="reset">重置</a>
						</td>
					</tr>
				</table>
			</div>
		</fieldset>
		<div style="text-align:right;"></div>
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
						<td width="6%" height="24" class="border_left"
							style="font-size: 12px;text-align:right;">任务名称					    </td>
						<td width="72%" style="text-align:left;">
						   <input type="text" id="taskname2" fortable="tasklist" roletype="param" style="width:220px" name="taskname2" />
						</td>
						<td width="22%">
						 <a class="easyui-linkbutton" style="width:100px;height:24px;"  fortable="tasklist" roletype="search" id="searchtask">查询</a>
                         <a class="easyui-linkbutton" style="width:100px;height:24px;" id="searchreset">重置</a>
						</td>
					</tr>
				</table>
			</div>
		</fieldset>
		<br/>
		<div>
		<!--  encodeurl:编码, validspider:[启动采集],haltspider:[暂停采集],killspider:[终止采集] 
		eventcount:事件配置数,  pagecount:页面配置数, parsercount:解析结构数
		-->
			<mytable:mytable sqlKey="getTaskList" tableName="tasklist"
				headCols="taskcode:任务编号,
			          tasktype:采集类型,
			          taskname:任务名称, 
				      taskurl:url地址, 
				      isactive:是否有效 ,
				      isspider:当前URL采集"
				pageSize="15"
				sqlParam="sitecode:${websiteid}"
				actionCol="taskmanage:[任务管理],gathergroup:[采集任务配置],taskgrouppage:[解析任务配置],deltask:[删除],modfiytask:[修改]">
			</mytable:mytable>
		</div>
	</form>
</body>
</html>
