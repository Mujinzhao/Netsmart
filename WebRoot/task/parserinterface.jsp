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
 <link rel="stylesheet" type="text/css" href="<%=basePath%>common/mytable/css/style.css"></link>
<script type="text/javascript" src="core/jquery-1.8.0.min.js"></script>
<script src="<%=basePath%>common/mytable/js/table.js" type="text/javascript"></script>
<script type="text/javascript" src="<%=basePath%>easyui/src/jquery.tabs.js"></script>
<script type="text/javascript" src="<%=basePath%>/task/js/taskgroup.js"></script>
</head>
<body>
	<form name="myform">
		<fieldset style="border: 1px solid #AECAF0; text-align: center;">
		  <legend>解析任务添加</legend>
		  <table width="1072" height="177">
		    <tr>
		      <td style="text-align:right">任务名称：</td>
		      <td width="220"><input type="text" id="taskname"
						disabled="disabled" name="taskname" style="width:220px;" value="${taskname}" /></td>
		      <td width="90" style="text-align:right">存储数据表：</td>
		      <td width="220"><select id="dbtable"
						onchange="choosetabfiled()" name="dbtable" style="width:220px;"> 
							${tableTree}
		        </select></td>
	        </tr>
		    <tr>
		      <td width="83"  style="text-align:right">多结构：</td>
		      <td colspan="3"><input type="checkbox" id="morestruts"
						name="morestruts" /><font color="red">*同一解析任务对应多个解析模板</font></td>
              <td width="83"  style="text-align:right">表创建：</td>
		      <td colspan="3"><label id="createtable" name="createtable" ><a href="javascript:createtable_open();" style="color:blue;">数据表创建</a></label>
								    
						</td>
	        </tr>
		    <tr>
		      <td>&nbsp;</td>
		      <td rowspan="6"><select name="tabfiled"
						id="tabfiled" style="width: 220px; height: 145px;"
						multiple="multiple">
	          </select></td>
		      <td>&nbsp;</td>
		      <td rowspan="6"><select name="tabfiled2"
						id="tabfiled2" style="width: 220px; height: 145px;"
						multiple="multiple">
	          </select></td>
		      <td width="79"><input type="button" value="▲" id="btnMoveUp"
						title="快速鍵: alt+向上" /></td>
		    
	        </tr>
		    <tr>
		      <td>&nbsp;</td>
		      <td style="text-align:center"><input type="button" id="rightmove"
						name="rightmove" onclick="rightMove();" size="50"
						style="width: 65px; height: 22px;" value="&gt;&gt;" /></td>
		      <td><input type="button" value="▼" id="btnMoveDown"
						title="快速鍵: alt+向下" /></td>
		     
	        </tr>
		    <tr>
		      <td style="text-align:right">表字段：</td>
		      
	        </tr>
		    <tr>
		      <td>&nbsp;</td>
		      <td style="text-align:center"><input type="button" id="lefmove" name="lefmove"
						onclick="leftMove();" size="50" style="width: 65px; height: 22px;"
						value="&lt;&lt;" /></td>
		     
	        </tr>
		
		    <tr>
		      <td>&nbsp;</td>
		   
		      <td>
		        <input type="hidden" id="taskid" name="taskid" value="${taskid}" />
		      </td>
		      <td width="79" style="text-align:center">
		        <input type="button"
						id="saveBut" name="saveBut" value="提交" />
		       </td>
		      <td width="352" style="text-align:left">
		       
		        <input type="button"
						id="centerBut" name="centerBut" value="重置" />
		      </td>
		    
	        </tr>
	      </table>
		</fieldset>
	</form>
</body>
</html>