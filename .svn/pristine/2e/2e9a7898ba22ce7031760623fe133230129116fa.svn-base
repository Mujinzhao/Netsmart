<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" 
			+ request.getServerPort()
			+ path + "/";
%>
<html>
<head>
<title>Create Table</title>
	<link rel="stylesheet" type="text/css" href="<%=basePath%>easyui/themes/default/easyui.css"/>
	<link rel="stylesheet" type="text/css" href="<%=basePath%>easyui/themes/icon.css"/>
	<link rel="stylesheet" type="text/css" href="<%=basePath%>easyui/demo/demo.css"/>
	
	<script type="text/javascript" src="<%=basePath%>core/jquery-1.8.0.min.js"></script>
	<script type="text/javascript" src="<%=basePath%>easyui/jquery.easyui.min.js"></script>
	<script type="text/javascript" src="<%=basePath%>createtable/js/tablegroup.js"></script>
</head>
<body>
	<div style="margin:10px 0;width:100%;height:420px;" >
			<div id="tablepage"  class="easyui-tabs" data-options="fit:true,plain:true">
			    <input type="hidden" id="showflag"/>
				<div title="General" style="padding:10px;">
				   <iframe id="general" name="mainFrame" scrolling="no" frameborder="0"  src="generalpage.action" style="width:100%;height:100%;"></iframe>
				</div>
				<div title="Columns" style="padding:10px;">
	          	   <iframe id="columnsFiled" name="mainFrame" scrolling="yes" frameborder="0"  src="createtable/options.html" style="width:100%;height:100%;"></iframe>
			 	</div>
			</div>
			
			<div id="showsql" style="display: none;">
				<div id="sqlinfo" contenteditable="true"  style='overflow-y:auto; overflow-x:auto; width:100%; height:400px;'>
                </div>
			</div>
	</div>
	<table style="width:100%;height:20px;" >
			  <tr>
			    <td width="70">
			    <span  id="Apply">
			       <a href="#" id="applystate"  class="easyui-linkbutton" onclick="applysql()">Apply</a>
			    </span>
			    </td>
			    <!--
			    <td width="77">
			     <span  id="Refresh">
			        <a href="#" class="easyui-linkbutton" style="text-align: right" onclick="">Refresh</a>
			     </span>
			    </td>
			    
			    <td width="62">
			      <span  id="Query">
			             <a href="#" class="easyui-linkbutton" onclick="">Query...</a>
			     </span>
			    </td>-->
			    <td width="764">
			    </td>
               <td width="47" rowspan="7"> <a href="#" class="easyui-linkbutton" onclick="viewsql()">ViewSQL</a></td>
		      </tr>
	  </table>
</body>
</html>
