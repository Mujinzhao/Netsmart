<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib uri="/WEB-INF/mytable.tld" prefix="mytable"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>解析模板规则配置页面</title>
<base href="<%=basePath%>"/>
	<link rel="stylesheet" type="text/css" href="<%=basePath%>/easyui/themes/default/easyui.css"/>
	<link rel="stylesheet" type="text/css" href="<%=basePath%>/easyui/themes/icon.css"/>
	<link rel="stylesheet" type="text/css" href="<%=basePath%>/easyui/demo/demo.css"/>
	<link rel="stylesheet" type="text/css" href="<%=basePath%>common/mytable/css/style.css"></link>
	<script type="text/javascript" src="<%=basePath%>/core/jquery-1.8.0.min.js"></script>
	<script type="text/javascript" src="<%=basePath%>/easyui/jquery.easyui.min.js"></script>
	<script src="<%=basePath%>common/mytable/js/table.js" type="text/javascript"></script>
	<script type="text/javascript" src="<%=basePath%>/excelparser/js/excelparser.js"></script>
<style type="text/css">
#divadd {
	padding: 5px;
}

#divadd td {
	font-family: "微软雅黑", "宋体", Arial, sans-serif;
	font-size: 14px;
}

.td1 {
	width: 110px;
	padding-right: 5px;
	padding-top: 5px;
	text-align: left;
}
</style>
</head>

<body>
<div id="addform">
<fieldset style="border:1px solid #AECAF0;">
	<legend>解析规则操作</legend>
	  <table width="1045">
     <tr>
        <td width="69" style="text-align:left;">sheet名称</td>
        <td width="255" id="sheetname">${sheetname}</td>
     </tr>
     <tr>
       <td style="text-align:right;">开始行</td>
       <td><input id="parsesrownum"
					name="parsesrownum" style="width:255px;" /></td>
       <td width="72" id="paserstr" style="text-align:left; display: none;">验证信息</td>
       <td width="647" rowspan="5">
          <textarea style="display: none;" name="paserdata" cols="88" rows="9" id="paserdata"></textarea>
	   </td>
     </tr>
     <tr>
       <td style="text-align:right;">结束行</td>
       <td><input id="parseerownum"
					name="parseerownum" style="width:255px;" /></td>
       <td style="text-align:left;">&nbsp;</td>
       </tr>
     <tr>
       <td style="text-align:right;">开始列</td>
       <td><input id="parsescellnum"
					name="parsescellnum" style="width:255px;" /></td>
       <td style="text-align:left;">&nbsp;</td>
       </tr>
     <tr>
       <td style="text-align:right;">结束列</td>
       <td><input id="parseecellnum"
					name="parseecellnum" style="width:255px;" /></td>
       <td style="text-align:left;">&nbsp;</td>
       </tr>
     <tr>
       <td style="text-align:right;">列行转</td>
       <td><input id="parserowtocell" name="parserowtocell" style="width:255px;" /></td>
       </tr>  
      </table>
     
      <div style="text-align:right;"><a href="javascript:void(0)" class="easyui-linkbutton" style="width:90px;height:24px;" id="previousstep">上一步</a>
         <a href="javascript:void(0)" class="easyui-linkbutton" style="width:90px;height:24px;" id="parserrule">验证解析规则</a>
         <a href="javascript:void(0)" class="easyui-linkbutton" style="width:90px;height:24px;" id="btnadd">保存解析规则</a> 
         <a href="javascript:void(0)" class="easyui-linkbutton" style="width:90px;height:24px;" id="btncancel">重置</a>
        
      </div>
  </fieldset>
      <input type="hidden"  id="asstable"      name="asstable" value="${asstable}"/>
      <input type="hidden"  id="templateid"    name="templateid" value="${templateId}"/>
      <input type="hidden"  id="structuredid"  name="structuredid" value="${structuredid}"/>
      <input type="hidden" id="owner" name="owner" value="${owner}" />
      <input type="hidden" name="sheetname" id="sheetname" value="${sheetname}" />
			
      <input type="hidden" id="structid" name="structid"/>
</div>
<br/>
<div>
<!--  	 DBTABLENAME:数据存储表, -->
<mytable:mytable sqlKey="getExcelStructure" tableName="excelstructure"
		headCols="sheetname:sheet名称,sheetable:sheet列名,
		    parsertype:解析接口,
		    parsesrownum:开始行号,parseerownum:结束行号,
		    parsescellnum:开始列号,parseecellnum:结束列号,
		    parserowtocell:列转行"
		colPK="ID" sqlParam='templateid:${templateId},structuredid:${structuredid}' pageSize="5"
		actionCol="checkexcelparser:校验,updatexcelparser:修改,delexcelparser:删除">
</mytable:mytable>
</div>
</body>
</html>
