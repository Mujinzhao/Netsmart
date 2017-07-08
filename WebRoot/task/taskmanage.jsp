<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<html>
<head>
<title>任务管理界面</title>
<base href="<%=basePath%>" />
<link rel="stylesheet" type="text/css" href="<%=basePath%>/easyui/themes/default/easyui.css"/>
<link rel="stylesheet" type="text/css" href="<%=basePath%>/easyui/themes/icon.css"/>
<script type="text/javascript" src="<%=basePath%>/core/jquery-1.8.0.min.js"></script>
<script type="text/javascript" src="<%=basePath%>/easyui/jquery.easyui.min.js"></script>
<script src="<%=basePath%>task/js/taskmanage.js" type="text/javascript"></script>
<style type="text/css">
body {
	margin-left: 0px;
	margin-top: 0px;
	margin-right: 0px;
	margin-bottom: 0px;
	font-size: 14px;
}
</style>
</head>
<body>
	<br />
	<div id="taskmanage">
		<fieldset style="border:1px solid #AECAF0;">
			<legend><h4>任务基本信息</h4></legend>
			<div id="basicinfomation">
				   <div>任务名称:   <label>${taskname}</label></div>
				   <div>采集类型:   <label id="spidertype">${spidertype}</label></div>
				   <div>事件配置数: <label id="eventnum">${eventnum}</label></div>
				   <div>页面配置数: <label id="pagenum">${pagenum}</label></div>
				   <div>分页配置数: <label id="pagingnum">${pagingnum}</label></div>
				   <div>解析结构数: <label id="parsesnum">${parsesnum}</label></div>
				   <input type="hidden" id="taskcode" name="taskcode" value="${taskcode}"/>
				   <input type="hidden" id="isactive" name="isactive" value="${isactive}"/>
				   <input type="hidden" id="timetype" name="timetype" value="${timetype}"/>
				   <input type="hidden" id="taskenabled" name="taskenabled" value="${taskenabled}"/>
				   
			</div>
		</fieldset>
		<fieldset style="border:1px solid #AECAF0;">
			<legend><h4>任务执行条件</h4></legend>
			<div id="conditions">
			<!-- 
			  <div>任务类型:采集<input type="radio" id="tasktype" name="tasktype"  value="0"/>
			                               解析<input type="radio" id="tasktype" name="tasktype"  value="1"/>
		      </div>
		      &nbsp; &nbsp;&nbsp; &nbsp;&nbsp; &nbsp; 
		       -->
		      <div>
			       <input type="radio" id="runtype" name="runtype" onclick="timetype(0);" value="0"/><span style="text-align:left">执行一次</span>
			       <input type="radio" id="runtype" name="runtype" onclick="timetype(1);" value="1"/><span style="text-align:left">每天执行</span>
			       <input type="radio" id="runtype" name=runtype   onclick="timetype(2);" value="2"/><span style="text-align:left">每周执行</span>
			       <input type="radio" id="runtype" name="runtype" onclick="timetype(3);" value="3"/><span style="text-align:left">每月执行</span>
			       <!-- <span style="text-align:left">自定义</span><input type="radio" id="runtype" name="runtype"  value="4"/>
		           -->  
		      </div>
		      <br/>
		      <div id="tasktypedesc"  style="text-align:left;font-size:14px;color: red;">
		      </div>
			</div>
			
		</fieldset>
		
		<fieldset style="border:1px solid #AECAF0;">
			<legend><h4>任务运行状态</h4></legend>
			<div id="taskstate">
				 <div id="taskdesc"  style='text-align:left;overflow-y:auto; overflow-x:auto; width:100%; height:220px;'>
				      该任务暂未运行<br/>
                </div>
			</div>
		</fieldset>
	</div>
	<div>
		<a id="startspider"class="easyui-linkbutton" onclick="taskmanageop('startspider');" style="width:60px;height:24px;">启动</a>
		<a id="haltspider" class="easyui-linkbutton" onclick="taskmanageop('haltspider');" style="width:60px;height:24px;">暂停</a>
		<a id="killspider" class="easyui-linkbutton" onclick="taskmanageop('killspider');" style="width:60px;height:24px;">终止</a>
	</div>
</body>
</html>
