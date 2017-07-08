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
<title>页面采集配置</title>
	<link rel="stylesheet" type="text/css" href="<%=basePath%>/easyui/themes/default/easyui.css"/>
	<link rel="stylesheet" type="text/css" href="<%=basePath%>/easyui/themes/icon.css"/>
	<link rel="stylesheet" type="text/css" href="<%=basePath%>/easyui/demo/demo.css"/>
	<script type="text/javascript" src="<%=basePath%>/core/jquery-1.8.0.min.js"></script>
	<script type="text/javascript" src="<%=basePath%>/easyui/jquery.easyui.min.js"></script>
	<link rel="stylesheet" type="text/css" href="<%=basePath%>common/mytable/css/style.css"></link>
	<script src="<%=basePath%>common/mytable/js/table.js" type="text/javascript"></script>
	<link href="<%=basePath%>zTree_v3/vform/css/style.css" rel="stylesheet" type="text/css" />
 	<script src="<%=basePath%>zTree_v3/vform/Validform_v5.3.2.js" type="text/javascript"></script>
 	<script src="<%=basePath%>/gather/js/pageconfig.js" type="text/javascript"></script>
</head>
<body>
<div id="addpageparam"> 
  <fieldset style="border:1px solid #AECAF0;">
		<legend><h4>页面配置</h4></legend>
	<table>
	       <tr style="height: 20px;">
			  <td style="text-align:right;">采集地址</td>
			  <td style="text-align:left;" rowspan="1" colspan="3">
			    <label id="taskurl" name="taskurl" >${taskurl}</label>
			    <a href="javascript:link_open();" style="color:blue;">打开查看</a></td> 
			</tr>
			<tr style="height: 20px;">
			  <td width="81" style="text-align:right;">标签类型</td>
			  <td width="228" style="text-align:left;"><select style="width:220px;height: 20px;" onchange="changetagtype(this.value)"  name="tagtype" id="tagtype">
			    <option value="0"> 目标标签 </option>
			    <option value="1"> 容器标签 </option>
			    </select></td>
			  <td width="83" style="text-align:right;">上级编号</td>
			  <td width="362" style="text-align:left;"><input type="text" name="partagcode" id="partagcode"
			      value="0"
					style="width:72px;" />
		      <span style="text-align:right;">&nbsp;&nbsp;</span></td>
			  <td width="67" style="text-align:right;"><span style="text-align:left;">标签编号</span></td>
			  <td width="220" style="text-align:left;">
			    <input type="text" name="tagcode" id="tagcode" 
					style="width:72px;" />
			  </td>
		  </tr>
			<tr style="height: 20px;">
			  <td style="text-align:right;">标签名称</td>
			  <td style="text-align:left;"><select style="width:220px;height: 20px;"name="tagname" id="tagname">
			   	    <option value="div">div</option>
					<option value="ul">ul</option>
					<option value="li">li</option>			
					<option value="a">a</option>
					<option value="table">table</option>
					<option value="tr">tr</option>
					<option value="td">td</option>
					<option value="dl">dl</option>
					<option value="dt">dt</option>
					<option value="dd">dd</option>
					<option value="input">input</option>
					<option value="span">span</option>
					<option value="button">button</option>
					<option value="img">img</option>
					<option value="iframe">iframe</option>
					<option value="select">select</option>
					<option value="option">option</option>
					<option value="form">form</option>
					<option value="strong">strong</option>
					<option value="embed">embed</option>
			    </select></td>
			  <td style="text-align:right;">属&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;性</td>
			  <td style="text-align:left;"><select style="width:220px;height: 20px;"name="tagattr" id="tagattr">
			        <option value=""></option>
					<option value="id">id</option>
					<option value="className">class</option>
					<option value="name">name</option>
					<option value="value">value</option>
					<option value="innerText">innerText</option>
					<option value="innerHTML">innerHtml</option>
			    </select></td>
			  <td style="text-align:right;">属&nbsp;性&nbsp;值</td>
			  <td style="text-align:left;"><input type="text" style="width:220px;" id="tagval" name="tagval"/></td>
		  </tr>
			
			<tr style="height: 20px;" id="pagecontor">
			  <td style="text-align:right;">取值属性</td>
			  <td style="text-align:left;"><select style="width:220px;height: 20px;"name="targetval" id="targetval">
					<option value=""></option>
			  		<option value="href">href</option>
					<option value="src">src</option>
			  		<option value="id">id</option>
			  		<option value="action">action</option>
					<option value="target">target</option>
					<option value="innerText">innerText</option>
					<option value="innerHTML">innerHtml</option>
					<option value="onclick">onclick</option>
			    </select></td>
			  <td style="text-align:right;">检测关键词</td>
			  <td style="text-align:left;"><input type="text" style="width:220px;" id="keyword" name="keyword"/>								    <span style="text-align:left;">(引导标签配置)</span>
              </td>
			  <td style="text-align:right;">钻&nbsp;&nbsp;&nbsp;&nbsp;取</td>
			  <td style="text-align:left;">
			  <input name="isdrill" type="checkbox" id="isdrill" value="T" /> 
			    采集 <input name="isspider" type="checkbox" id="isspider" value="T" /></td>
	      </tr>
  </table>
  </fieldset>
   <div id="hidparam">
 	        <input type="hidden" id="pageflag" name="pageflag"/>
			<input type="hidden" id="taskcode" name="taskcode" value="${taskcode}"/>
			<input type="hidden" id="taskname" name="taskname" value="${taskname}"/>
			<input type="hidden" id="taskurl" name="taskurl"  value="${taskurl}"/>
			<input type="hidden" id="lgurl" name="lgurl"  value="${loginurl}"/>
			<input type="hidden" name="loginflag" id="loginflag" value="${loginflag}" />
			<input type="hidden" name="groupcode" id="groupcode" value="${groupcode}" /> 
			<input type="hidden" id="flag" name="flag"  value="${flag}" />
			<input type="hidden" id="id" name="id"/>
			
	</div>
</div>
<div>
     <div style="text-align:right;">
          <input type="button" style="display:none;" id="previousstep"  value="上一步"/>
          <input type="button" id="nextpaging"  value="下一步"/>
          <input type="button" id="extractingtags"  value="检测"/>
          <input type="button" id="savepageconfig"  value="保存"/>
          <input type="button" id="clearcontor"  value="重置"/>
          
     </div>
</div>
<div style="width:100%;height:100%;">
	<mytable:mytable sqlKey="getPageconfg" 
		    tableName="itertaginfo"
	    	headCols="taskcode:任务编号,
			      tagcode:标签编号,
			      partagcode:上级编号,
			      tagname:标签名称,
			      tagattr:标签属性,
			      tagattrval:标签属性值,
			      tagtype:标签类型,
			      targetattr:取值属性,
			      isdrill:钻取,
			      isgather:采集"		
			pageSize="15"
			colPK="id"
			sqlParam='taskcode:${taskcode},groupcode:${groupcode}'
			actionCol="delpage:[删除],modfiypage:[修改]">
	</mytable:mytable>
</div>
</body>
</html>
