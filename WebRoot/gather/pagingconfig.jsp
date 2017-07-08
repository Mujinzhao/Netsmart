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
<title>分页配置</title>
	<link rel="stylesheet" type="text/css" href="<%=basePath%>/easyui/themes/default/easyui.css"/>
	<link rel="stylesheet" type="text/css" href="<%=basePath%>/easyui/themes/icon.css"/>
	<link rel="stylesheet" type="text/css" href="<%=basePath%>/easyui/demo/demo.css"/>
	<script type="text/javascript" src="<%=basePath%>/core/jquery-1.8.0.min.js"></script>
	<script type="text/javascript" src="<%=basePath%>/easyui/jquery.easyui.min.js"></script>
	<link rel="stylesheet" type="text/css" href="<%=basePath%>common/mytable/css/style.css"></link>
	<script src="<%=basePath%>common/mytable/js/table.js" type="text/javascript"></script>
	<script src="<%=basePath%>/gather/js/pagingconfig.js" type="text/javascript"></script>
</head>
<body>
<div id="addpagingparam"> 
  <fieldset style="border:1px solid #AECAF0;">
	<legend><h4>分页配置</h4></legend>
	 <table>
				<tr style="height: 20px;">
				  <td width="81" style="text-align:right;">标签类型</td>
				  <td width="220" style="text-align:left;"><select style="width:220px;height: 20px;"name="tagtype" id="tagtype">
				    <option> 目标标签 </option>
				    <option> 容器标签 </option>
				    </select></td>
				  <td width="80" style="text-align:right;">上级编号</td>
				  <td width="248" style="text-align:left;"><input type="text" disabled="disabled" value="0" name="partagcode" id="partagcode"
						style="width:72px;" /></td>
				  <td width="68" style="text-align:right;"><span style="text-align:left;">标签编号</span></td>
				  <td width="324" style="text-align:left;">
				    <input type="text" name="tagcode" id="tagcode" disabled="disabled"
						style="width:72px;" />
				  </td>
			  </tr>
				<tr style="height: 20px;">
				  <td style="text-align:right;">标签名称</td>
				  <td style="text-align:left;"><select style="width:220px;height: 20px;"name="tagname" id="tagname">
					<option value="a">a</option>
					<option value="div">div</option>
					<option value="ul">ul</option>
					<option value="li">li</option>
					<option value="span">span</option>
					<option value="img">img</option>
					<option value="iframe">iframe</option>
				    </select></td>
				  <td style="text-align:right;">属&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;性</td>
				  <td style="text-align:left;"><select style="width:220px;height: 20px;" name="tagattr" id="tagattr">
				    <option value="innerText">innerText</option>
					<option value="id">id</option>
					<option value="className">class</option>
					<option value="name">name</option>
					<option value="value">value</option>
					<option value="innerHTML">innerHtml</option>
				    </select></td>
				  <td style="text-align:right;">属&nbsp;性&nbsp;值</td>
				  <td style="text-align:left;"><input name="tagval" type="text" id="tagval" style="width:220px;" value="/下一页/"/></td>
			  </tr>
	  </table>
   </fieldset>

<div id="hidparam">
 	        <input type="hidden" id="pageflag" name="pageflag" value="${pageflag}"/>
			<input type="hidden" id="taskcode" name="taskcode" value="${taskcode}"/>
			<input type="hidden" id="taskname" name="taskname" value="${taskname}"/>
			<input type="hidden" id="taskurl"  name="taskurl"  value="${taskurl}"/>
			<input type="hidden" name="groupcode" id="groupcode" value="${groupcode}" /> 
			<input type="hidden" id="flag" name="flag" value="${flag}"/>
			<input type="hidden" id="id" name="id"/>
</div>
</div>
<div>
     <div style="text-align:right;">
          <input type="button" id="previousstep"  value="上一步"/>
          <input type="button" id="complete"      value="完成"/>
          <input type="button" id="savepaging"    value="保存"/>
          <input type="button" id="clearcontor"   value="重置"/>
     </div>
</div>
<div style="width:100%;height:100%;">
	<mytable:mytable sqlKey="getPageingconfg" 
		    tableName="pageingconfg"
		    headCols="taskcode:任务编号,
			      tagcode:标签编号,
			      partagcode:上级编号,
			      tagname:标签名称,
			      tagattr:标签属性,
			      tagattrval:标签属性值,
			      tagtype:标签类型"
			pageSize="15"
			colPK="id"
			selectRowBtn="1"
			sqlParam='taskcode:${taskcode},gcode:${groupcode}'
			actionCol="delpaging:[删除],modfiypaging:[修改]">
	</mytable:mytable>
</div>
</body>
</html>
