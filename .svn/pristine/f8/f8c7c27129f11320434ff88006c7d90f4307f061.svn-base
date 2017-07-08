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
<title>解析字段操作</title>
	<link rel="stylesheet" type="text/css" href="<%=basePath%>/easyui/themes/default/easyui.css"/>
	<link rel="stylesheet" type="text/css" href="<%=basePath%>/easyui/themes/icon.css"/>
	<link rel="stylesheet" type="text/css" href="<%=basePath%>/easyui/demo/demo.css"/>
	<script type="text/javascript" src="<%=basePath%>/core/jquery-1.8.0.min.js"></script>
	<script type="text/javascript" src="<%=basePath%>/easyui/jquery.easyui.min.js"></script>
	<link rel="stylesheet" type="text/css" href="<%=basePath%>common/mytable/css/style.css"></link>
	<script src="<%=basePath%>common/mytable/js/table.js" type="text/javascript"></script>
	<script src="<%=basePath%>parseinterface/js/commonjs.js" type="text/javascript"></script>
	<script src="<%=basePath%>parseinterface/js/tagparse.js" type="text/javascript"></script>
</head>
<body>
	<div id="tableparam">
	<fieldset style="border:1px solid #AECAF0;">
	    <legend>解析规则值操作</legend>
		<table>
			<tr>
			    <td width="83" style="text-align: right;">解析字段</td>
				<td width="133">
				    <select style="width:120px;"name="parsefiled" id="parsefiled">
					</select>
				</td>
			    <td  style="text-align: right;">操作类型</td>
			    <td><span style="text-align:left">
		        <select id="customop" name="customop" style="width:150px;">
		          <option value="0">字段值</option>
		          <option value="1" selected>字段下标</option>
	            </select>
	          </span></td>
				<td style="text-align: right;">字段值</td>
				<td  style="text-align:right">
				   <input type="text" name="acqfieldval" style="width:160px;" id="acqfieldval" />
				</td>
				 <td width="46"><a id="saveparser" class="easyui-linkbutton" style="width:60px;height:24px;" onclick="savetagInfo()">提交</a></td>
				 <td width="39"><a id="delparser" class="easyui-linkbutton" style="width:60px;height:24px;" onclick="">删除</a></td>
				 <td width="54"><a id="currtparser" class="easyui-linkbutton" style="width:60px;height:24px;" onclick="">重置</a></td>
				 <td width="59" id="previousstep"><a class="easyui-linkbutton" style="width:90px;height:24px;" onclick="previousstep()">上一步</a></td>
				 <td width="54" id="nextstep"><a class="easyui-linkbutton" style="width:90px;height:24px;" onclick="nextstep()">下一步</a></td>
			</tr>
		</table>
		<div id="hidparam">
		      <input type="hidden" id="taskcode" name="taskcode" value="${taskcode}" /> 
		      <input type="hidden"  id="asstable"     name="asstable" value="${asstable}"/>
		      <input type="hidden"  id="structuredid" name="structuredid" value="${structuredid}"/>
		      <input type="hidden"  id="tablenamedesc" name="tablenamedesc" value="${tablename}"/>
		      <input type="hidden"  id="parseurl"     name="parseurl"    value="${parseurl}" />
		      <input type="hidden"  id="parsertype"   name="parsertype"  value="${parsertype}"/>
		      <input type="hidden"  id="tagpage"       name="tagpage" value="${tagpage}"/>
		      <input type="hidden"  id="encode"    name="encode" value="${encode}"/>
		      <input type="hidden" id="owner" name="owner" value="${owner}" />
		      <input type="hidden" id="id" name="id"/>
	    </div>
	</fieldset>
	</div>
	<br />
	<div id="tagindexsql" style="width:100%;height:100%;">
		<mytable:mytable sqlKey="getParserTagParse" tableName="tagparserInfo"
			headCols="acqfield:解析字段,
				      acqfieldesc:字段描述,
					  fieldindex:字段下标"
			pageSize="15" colPK="id"
			sqlParam='structuredid:${structuredid}'
			actionCol="delparse:[删除],modfiyparse:[修改]"
			selectRowBtn="1">
		</mytable:mytable>
	</div>
<!--
	<div id="tableparam">
		<table>
			<tr style="height: 20px;">
				<td width="91" style="text-align: right;">数据存储</td>
				<td width="231"><input type="text" name="asstable"
					style="width:200px;" id="asstable" disabled="disabled"
					value="${tablename}" /> <font color="red">*</font>
				</td>
				<td  style="text-align: right;">操作类型</td>
			    <td><span style="text-align:left">
		        <select id="customop" name="customop" style="width:150px;">
		          <option value="0">字段值</option>
		          <option value="1" selected>字段下标</option>
	            </select>
	          </span></td>
				<td width="83" style="text-align: right;">解析字段</td>
				<td width="133">
				    <select style="width:120px;"name="parsefiled" id="parsefiled">
					</select>
				</td>
				<td width="84" style="text-align: right;">字段下标</td>
				<td width="87"><input type="text" name="tagindex"
					style="width:60px;" id="tagindex" value="0" />
                <%-- <input type="radio"
					name="tag" id="tag" value="0" onclick="clickchoose(this.value)" />
					标签字段 <input type="radio" name="tag" id="tag2" value="1"
					onclick="clickchoose(this.value)" /> 标签规则 --%></td>
				        <td width="46"><a id="saveparser" class="easyui-linkbutton" style="width:60px;height:24px;" onclick="savetagInfo()">提交</a></td>
					    <td width="39"><a id="delparser" class="easyui-linkbutton" style="width:60px;height:24px;" onclick="">删除</a></td>
					    <td width="54"><a id="currtparser" class="easyui-linkbutton" style="width:60px;height:24px;" onclick="">取消</a></td>
					    					    <td width="59" id="previousstep"><a class="easyui-linkbutton" style="width:90px;height:24px;" onclick="previousstep()">上一步</a></td>
					    <td width="54" id="nextstep"><a class="easyui-linkbutton" style="width:90px;height:24px;" onclick="nextstep()">下一步</a></td>
			</tr>
		</table>
		<div id="hidparam">
		      <input type="hidden" id="taskcode" name="taskcode" value="${taskcode}" /> 
		      <input type="hidden"  id="asstable"     name="asstable" value="${asstable}"/>
		      <input type="hidden"  id="structuredid" name="structuredid" value="${structuredid}"/>
		      <input type="hidden"  id="tablenamedesc" name="tablenamedesc" value="${tablename}"/>
		      <input type="hidden"  id="parseurl"     name="parseurl"    value="${parseurl}" />
		      <input type="hidden"  id="parsertype"   name="parsertype"  value="${parsertype}"/>
		      <input type="hidden"  id="tagpage"       name="tagpage" value="${tagpage}"/>
		      <input type="hidden"  id="encode"    name="encode" value="${encode}"/>
		      <input type="hidden" id="owner" name="owner" value="${owner}" />
		      <input type="hidden" id="id" name="id"/>
	    </div>
    </div>
	<div id="tagindexsql" style="width:100%;height:100%;">
		<mytable:mytable sqlKey="getParserTagParse" tableName="tagparserInfo"
			headCols="acqfield:解析字段,
				      acqfieldesc:字段描述,
					  fieldindex:字段下标"
			pageSize="15" colPK="id"
			sqlParam='structuredid:${structuredid}'
			actionCol="delparse:[删除],modfiyparse:[修改]"
			selectRowBtn="1">
		</mytable:mytable>
	</div>
	-->
</body>
</html>