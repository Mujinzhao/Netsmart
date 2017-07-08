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
<title>组信息添加</title>
	<link rel="stylesheet" type="text/css" href="<%=basePath%>easyui/themes/default/easyui.css"/>
	<link rel="stylesheet" type="text/css" href="<%=basePath%>easyui/themes/icon.css"/>
	<link rel="stylesheet" type="text/css" href="<%=basePath%>easyui/demo/demo.css"/>
	<link href="<%=basePath%>zTree_v3/vform/css/style.css" rel="stylesheet" type="text/css" />
	<link rel="stylesheet" type="text/css" href="<%=basePath%>common/mytable/css/style.css"></link>
	<script type="text/javascript" src="<%=basePath%>core/jquery-1.8.0.min.js"></script>
	<script type="text/javascript" src="<%=basePath%>easyui/jquery.easyui.min.js"></script>

	<script src="<%=basePath%>common/mytable/js/table.js" type="text/javascript"></script>
    <script src="<%=basePath%>zTree_v3/vform/Validform_v5.3.2.js" type="text/javascript"></script>
    <script src="<%=basePath%>gather/js/groupconfig.js" type="text/javascript"></script>
</head>
<body>
<div id="hidparam"> 
	     <fieldset style="border:1px solid #AECAF0;">
			<legend><h4>组信息添加</h4></legend>
			<table>
				<tr style="height: 20px;">
				  <td width="67" height="21" style="text-align:right">所属站点</td>
				  <td colspan="3"  style="text-align:left">
				     <input type="text" style="width:220px;" id="taskname" name="taskname" disabled="disabled" value="${taskname}"/>
				     <input type="hidden" id="taskcode" name="taskcode" value="${taskcode}"/></td>
				  <td width="73"  style="text-align:right">采集地址</td>
				  <td width="671"  style="text-align:left">
				     <input type="text" name="taskurl" id="taskurl" value="${taskurl}" style="width:375px;" disabled="disabled" /></td>
			  </tr>
			 <tr>
			   <td height="21" style="text-align:right"> 是否登录 </td>
			     <td width="126"  style="text-align:left">
			        <input id="islogin" name="islogin" style="text-align:right;" type="checkbox" value="T" />
		       </td>
			     <td width="69"  style="text-align:left"><span style="text-align:right">是否采集</span></td>
			     <td width="35"  style="text-align:left"><input id="isspider" name="isspider" style="text-align:right;" type="checkbox" value="T" /></td>
			      <td height="21" style="text-align:right">执行顺序</td>
			        <td  style="text-align:left"><input id="runsort" name="runsort" style="text-align:left;width:220px;" type="text" value="1" /></td>
			  </tr>
			 
				<tr style="height: 20px;">
				  <td height="21" style="text-align:right">组&nbsp;描&nbsp;述</td>
				  <td colspan="3" id="groupdesccheck" style="text-align:left">
				       <input type="text" style="width:220px;" datatype="*" id="groupdesc" name="groupdesc"/></td>
				  <td  style="text-align:right">登录网址</td>
				  <td  style="text-align:left">
				         <input type="text" datatype="url"  name="loginurl" id="loginurl" style="width:375px;" />
				         <input id="loginflag" name="loginflag" type="hidden" value="${loginflag}" />
				         <input id="lgurl" name="lgurl" type="hidden" value="${loginurl}" />
				   </td>
			  </tr>
	  </table>
	</fieldset>
	  <div style="text-align:right;">
                              <input type="button" style="display:none;" id="nextpaging"  value="下一步"/>
                              <input type="hidden" id="pageflag" name="pageflag" value="3"/>
                              <input type="hidden" id="id" name="id"/>
                              <input type="hidden" id="groupcode" name="groupcode" value="${groupcode}"/>
                              <input type="button" id="addgroup"  value="新建任务组"/>
                              <input type="button" id="rester"  value="重置"/>
	 </div>
</div>

<div style="width:100%;height:100%;">
	<mytable:mytable sqlKey="getGroup" 
		    tableName="groupinfo"
			headCols="taskcode:任务编号,
				      groupdesc:组描述,
				      groupcode:组编号,
				      runsort:执行顺序,
				      islogin:登录标识,
				      isspider:采集标识,
				      eventcount:事件配置数,
				      pagecount:页面配置数"
			pageSize="15"
			colPK="id"
			selectRowBtn="1"
			sqlParam='taskcode:${taskcode}'
			actionCol="delgroup:[删除],modfiygroup:[修改],nextpaging:[下一步]">
	</mytable:mytable>
</div>
</body>
</html>