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
<title>解析任务添加</title>
<link rel="stylesheet" type="text/css" href="<%=basePath%>/easyui/themes/default/easyui.css"/>
<link rel="stylesheet" type="text/css" href="<%=basePath%>/easyui/themes/icon.css"/>
<link rel="stylesheet" type="text/css" href="<%=basePath%>/easyui/demo/demo.css"/>
<link rel="stylesheet" type="text/css" href="<%=basePath%>common/mytable/css/style.css"></link>
<script type="text/javascript" src="core/jquery-1.8.0.min.js"></script>
<script src="<%=basePath%>common/mytable/js/table.js" type="text/javascript"></script>
<script type="text/javascript" src="<%=basePath%>/easyui/jquery.easyui.min.js"></script>
<script type="text/javascript" src="<%=basePath%>/easyui/src/jquery.tabs.js"></script>
<script src="<%=basePath%>parseinterface/js/commonjs.js" type="text/javascript"></script>
<script type="text/javascript" src="<%=basePath%>/task/js/taskgroup.js"></script>
</head>
<body>
	<form id="myform" name="myform">
		<fieldset style="border:1px solid #AECAF0;">
		  <legend>解析任务添加</legend>
			
			<table>
		    <tr>
		      <td width="95" style="text-align:right">任务名称：</td>
		      <td colspan="3"><input type="text" id="taskname"
						disabled="disabled" name="taskname" style="width:225px;" value="${taskname}" /></td>
	        </tr>
	        <tr>
		      <td  style="text-align:right">数据库用户：</td>
		      <td colspan="3">
		         <select id="dbuser" name="owner" onchange="choosetab(this.value)"  style="width: 228px;">${userselect}</select>
		      </td>
		      <td width="79" style="text-align:right"><input type="hidden" name="owner" id="owner"/>
              <span style="text-align:left">
              <a href="javascript:createtable_open();" style="color:blue;"></a></span>存储表：</td>
		      <td colspan="3">
		      <select id="dbtable" onchange="choosetabfiled(this.value)" name="dbtable" style="width:150px;">${tableTree}</select>
		      <span style="text-align:left"><a href="javascript:createtable_open();" style="color:blue;">数据表创建
		      
		      </a></span></td>
		      </tr>
		    <tr>
		      <td style="text-align:right">多&nbsp;结&nbsp;构：</td>
		      <td width="23" ><input type="checkbox" id="morestruts" name="morestruts" value="1" /></td>
		      <td width="75" style="text-align:left">文件编号：</td>
		      <td width="124"><select id="parsercode" name="parsercode" style="width:122px;">${filecodeTree}</select></td>
		      <td style="text-align:right">解析类型：</td>
	          <td width="152"  style="text-align:left"><select id="parsertype" name="parsertype" style="width:150px;">
	              <option value="0">前后缀解析</option>
	              <option value="1">标签解析</option>
	              <option value="2">综合解析</option>
	              <option value="4">元搜索解析</option>
              </select></td>
	          <td width="56" style="text-align:left">列转行：</td>
	          <td width="20" style="text-align:right"><input type="checkbox" id="rowtocell" name="rowtocell" value="true" /></td>
	          </tr>
		    <tr>
		      <td height="23" style="text-align:right">表&nbsp;字&nbsp;段：</td>
		      <td colspan="3" rowspan="7"><select 
						id="tabfiled" style="width: 230px; height: 155px;"
						multiple="multiple">
	          </select></td>
		      <td>&nbsp;</td>
		      <td colspan="3" rowspan="7"><select 
						id="tabfiled2" style="width: 235px; height: 155px;"
						multiple="multiple">
	          </select></td>
		      <td width="197"><input type="button" value="▲" id="btnMoveUp"
						title="快速鍵: alt+向上" /></td>
		    
	        </tr>
		    <tr>
		      <td height="24">&nbsp;</td>
		      <td style="text-align:center"><input type="button" id="rightmove"
						name="rightmove" onclick="rightMove();" size="50"
						style="width: 65px; height: 22px;" value="&gt;&gt;" /></td>
		      <td><input type="button" value="▼" id="btnMoveDown"
						title="快速鍵: alt+向下" /></td>
		     
	        </tr>
		    <tr>
		      <td height="21" style="text-align:right">&nbsp;</td>
		      
	        </tr>
		    <tr>
		      <td height="24">&nbsp;</td>
		      <td style="text-align:center"><input type="button" id="lefmove" name="lefmove"
						onclick="leftMove();" size="50" style="width: 65px; height: 22px;"
						value="&lt;&lt;" /></td>
		     
	        </tr>
		
		    <tr>
		      <td height="21">&nbsp;</td>
		      <td>&nbsp;</td>
		      
		      </tr>
		    <tr>
		      <td height="21">&nbsp;</td>
		   
		      <td>
		        <input type="hidden" id="structuredid" name="structuredid"/>
		        <input type="hidden" id="oldtkname" name="oldtkname" value="${taskname}" />
		        <input type="hidden" id="taskid" name="taskid"  value="${taskid}"/></td>
		      <td width="197" style="text-align:center"></td>
		      <td width="73" style="text-align:left">
		            <a class="easyui-linkbutton" style="width:90px;height:24px;" id="saveBut" name="saveBut">保存</a>
		      </td>
		      <td width="48" style="text-align:left">
		            <a class="easyui-linkbutton" style="width:90px;height:24px;" id="centerBut" name="centerBut">重置</a>
		      </td>
		    
	        </tr>
	      </table>
		</fieldset>
	</form>
	<div>
		<mytable:mytable sqlKey="getparserInfo" 
		    tableName="parsetask"
			headCols="structuredesc:任务名称, 
				      asstablename:表名称,
				      asstable:存储表,
				      morestructured:多结构,
				      parsertype:解析类型"
			pageSize="15" 
			sqlParam="taskid:${taskid}"
			actionCol="parserstructure:[解析模板],parsercheck:[解析验证],emptyfiled:[非空字段指定],delstructure:[删除],modfiystructure:[修改]">
		</mytable:mytable>
	</div>
	<div id="w" class="easyui-dialog" scrolling="yes" title="非空字段指定" data-options="iconCls:'icon-save',closed:true" style="overflow-y:auto; overflow-x:auto;width:100%;height:120%;">
			<iframe id="emptfield"  name="mainFrame" scrolling="no" frameborder="0"  src="" style="width:100%;height:100%;"></iframe>
	</div>
</body>
</html>