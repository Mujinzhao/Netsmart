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
<title>EXCEL解析任务添加</title>
<link rel="stylesheet" type="text/css" href="<%=basePath%>/easyui/themes/default/easyui.css"/>
<link rel="stylesheet" type="text/css" href="<%=basePath%>/easyui/themes/icon.css"/>
<link rel="stylesheet" type="text/css" href="<%=basePath%>/easyui/demo/demo.css"/>
<link rel="stylesheet" type="text/css" href="<%=basePath%>common/mytable/css/style.css"></link>
<script type="text/javascript" src="core/jquery-1.8.0.min.js"></script>
<script src="<%=basePath%>common/mytable/js/table.js" type="text/javascript"></script>
<script type="text/javascript" src="<%=basePath%>/easyui/jquery.easyui.min.js"></script>
<script type="text/javascript" src="<%=basePath%>/easyui/src/jquery.tabs.js"></script>
<script src="<%=basePath%>parseinterface/js/commonjs.js" type="text/javascript"></script>
<script src="<%=basePath%>excelparser/js/excelstrunct.js" type="text/javascript"></script>
</head>
<body>
	<div id="myform">
		<fieldset style="border:1px solid #AECAF0;">
		  <legend>EXCEL解析任务添加</legend>
		  <table width="989">
	        <tr>
	          <td width="83" height="21" style="text-align:right">SHEET名称</td>
	          <td colspan="2"><select id="sheetname" name="sheetname" style="width:228px;">${sheetOption}</select></td>
	          <td width="82" style="text-align:right">SHEET列名</td>
	          <td colspan="7">
	          <input style="width:227px;" type="text" id="sheetable" name="sheetable"/>
	          <input type="hidden" id="templateid" name="templateid" value="${templateId}"/>
	          <input type="hidden" id="structuredid" name="structuredid"/>
	          </td>
            </tr>
	        <tr>
	          <td  style="text-align:right">存储用户</td>
	          <td colspan="2"><select id="dbuser" name="owner" onchange="choosetab(this.value)"  style="width: 228px;">
	            ${userselect}
              </select></td>
	          <td style="text-align:right">存储表</td>
	          <td  style="text-align:left"><select id="dbtable" onchange="choosetabfiled(this.value)" name="dbtable" style="width:150px;">
	            ${tableTree}
              </select></td>
	          <td width="80"  style="text-align:left"><a href="javascript:createtable_open();" style="color:blue;">数据表创建</a></td>      
            </tr>
	        <tr>
		      <td  style="text-align:right">解析类型</td>
		      <td width="211"><span style="text-align:left">
		        <select id="parsertype" name="parsertype" style="width:150px;">
		          <option value="0">普通解析</option>
		          <option value="1">列转行解析</option>
	          </select>
		      列转行:</span></td>
		      <td width="20"><span style="text-align:left">
		        <input type="checkbox" id="rowtocell" name="rowtocell" value="true" />
		      </span></td>
		      <td style="text-align:right">是否有效</td>
		      <td width="154"  style="text-align:left"><input type="checkbox" id="isactive" name="isactive" value="T" /></td>
	        </tr>
		    
		    <tr>
		      <td height="23" style="text-align:right">表&nbsp;字&nbsp;段</td>
		      <td colspan="2" rowspan="8"><select 
						id="tabfiled" style="width: 235px; height: 100px;"
						multiple="multiple">
	          </select></td>
		      <td style="text-align:center"><span style="text-align:center">
		        <input type="button" id="rightmove"
						name="rightmove" onclick="rightMove();" size="50"
						style="width: 65px; height: 22px;" value="&gt;&gt;" />
		      </span></td>
		      <td colspan="2" rowspan="8"><select 
						id="tabfiled2" style="width: 235px; height: 100px;"
						multiple="multiple">
	          </select></td>
		      <td width="269"><input type="button" value="▲" id="btnMoveUp"
						title="快速鍵: alt+向上" /></td>
		    
	        </tr>
		    <tr>
		      <td height="24">&nbsp;</td>
		      <td style="text-align:center">&nbsp;</td>
		      <td><input type="button" value="▼" id="btnMoveDown"
						title="快速鍵: alt+向下" /></td>
	        </tr>
		    <tr>
		      <td>&nbsp;</td>
		      <td>&nbsp;</td>
		      
	        </tr>
		    <tr>
		      <td>&nbsp;</td>
		      <td  style="text-align:center"><span  style="text-align:center">
		        <input type="button" id="lefmove" name="lefmove"
						onclick="leftMove();" size="50" style="width: 65px; height: 22px;"
						value="&lt;&lt;" />
		      </span></td>
		      <td style="text-align:right">
		                <a class="easyui-linkbutton" style="width:90px;height:24px;" id="saveBut" name="saveBut">保存</a>
		      </td>
              <td width="54" style="text-align:left">
                        <a class="easyui-linkbutton" style="width:90px;height:24px;" id="centerBut" name="centerBut">重置</a>
              </td>
	        </tr>
	      </table>
		</fieldset>
	</div>
    <br/>
	<div>
		<mytable:mytable sqlKey="getTemplateStruct" 
		    tableName="exceconfig"
			headCols="sheetname:SHEET名称, 
				      sheetable:SHEET列表,
				      owner:用户,
				      asstablename:存储表,
				      parsertype:解析类型,
				      isactive:是否有效"
			pageSize="8" 
			sqlParam="templateid:${templateId}"
			actionCol="tanchong:填充下一条,parserstructure:[模板结构],delstructure:[删除],modfiystructure:[修改]">
		</mytable:mytable>
	</div>
</body>
</html>