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
<title>元搜索配置</title>
	<link rel="stylesheet" type="text/css" href="<%=basePath%>/easyui/themes/default/easyui.css"/>
	<link rel="stylesheet" type="text/css" href="<%=basePath%>/easyui/themes/icon.css"/>
	<link rel="stylesheet" type="text/css" href="<%=basePath%>/easyui/demo/demo.css"/>
	<link rel="stylesheet" type="text/css" href="<%=basePath%>uploadify/uploadify.css"></link>
	<script type="text/javascript" src="<%=basePath%>/core/jquery-1.8.0.min.js"></script>
	<script type="text/javascript" src="<%=basePath%>/easyui/jquery.easyui.min.js"></script>
	<link rel="stylesheet" type="text/css" href="<%=basePath%>common/mytable/css/style.css"></link>
 	<script src="<%=basePath%>uploadify/jquery.uploadify.js" type="text/javascript"></script>
	<script src="<%=basePath%>common/js/ajaxfileupload.js" type="text/javascript"></script>
	
	<script src="<%=basePath%>common/mytable/js/table.js" type="text/javascript"></script>
	<script src="<%=basePath%>parseinterface/js/commonjs.js" type="text/javascript"></script>
 	<script src="<%=basePath%>/parseinterface/js/matetag.js" type="text/javascript"></script>
</head>
 <body>
  <div id="tagrule">
     <table width="1071">
		<tr>
		  <td width="76" height="21"  style="text-align: right;">标签名称&nbsp;&nbsp;</td>
		  <td width="126"><select id="tagname0" name="tagname" style="width: 100px;">
		    <option value="table">table</option>
		    <option value="div">div</option>
		    <option value="ul">ul</option>
		    <option value="li">li</option>
		    <option value="a">a</option>
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
		    <option value="h2">h2</option>
		    </select></td>
		  <td width="77"  style="text-align: right;">标签属性&nbsp;&nbsp;</td>
		  <td width="109"  style="text-align: left;"><select id="tagattr0" name="tagattr" style="width: 100px;">
		    <option value="">--请选择--</option>
		    <option value="class" selected>class</option>
		    <option value="id">id</option>
		    <option value="style">style</option>
		    <option value="align">align</option>
		    <option value="frame">frame</option>
		    <option value="width">width</option>
		    <option value="height">height</option>
		    <option value="name">name</option>
		    <option value="value">value</option>
	      </select></td>
		  <td width="96"  style="text-align: right;">Tag属性值&nbsp;&nbsp;</td>
		  <td width="559"  style="text-align: left;"><input type="text" name="tagattrval" style="width: 180px;"
				id="tagattrval0" /></td>
       </tr>
		<tr>
		  <td height="21"  style="text-align: right;"><span style="width: 60px;text-align: right;">验证方式</span></td>
		  <td><input name="checktype" type="radio" id="checktype"
					onclick="checkparsertype(this.value)" value="0" checked="checked" />
文件
  <input type="radio" name="checktype" id="checktype" onclick="checkparsertype(this.value)" value="1" />
  网址</td>
       </tr>
		<tr>
		  <td height="21" style="width: 60px;text-align: right;" >验证地址</td>
		  <td colspan="10">
             <table id="parseurl1"  cellSpacing="0" cellPadding="0" border="0">
                <tr>
                       <td>
                       	<input type="text" name="testUrl" id="testUrl" datatype="url" style="width:420px;" value="${parseurl}" />
						<a href="javascript:link_open();" style="color:blue;">打开查看</a>
                       </td>
                    </tr>
					</table>
					<table id="parsepath2" cellSpacing="0" cellPadding="0" border="0">
                      <tr>
                       <td>
                       	<s:file id="parsefile" name="parsefile"/>
                       </td>
                      </tr>
					</table></td>
	   </tr>
		<tr>
		  <td height="21" style="width: 60px;text-align: right;" ><span style="text-align: right;">验证结果</span></td>
		  <td colspan="10"><textarea name="textarea" id="showURLValue" readonly="readonly"  cols="130" rows="12" row="row">${fieldStr}</textarea>
	      </td>
	   </tr>
	</table>
    <div>
    <div style="text-align:right;">
      <a class="easyui-linkbutton" style="width:100px;height:24px;"                   onclick="previousstep()">上一步</a>
      <a class="easyui-linkbutton" style="width:100px;height:24px;"                   onclick="checktagrule()">解析验证</a>
      <a class="easyui-linkbutton" style="width:100px;height:24px;" id="savetagrule"  onclick="addtagrule();">规则入库</a>
      <a class="easyui-linkbutton" style="width:100px;height:24px;"                   onclick="deltagrule();">删除规则</a>
      <a class="easyui-linkbutton" style="width:60px;height:24px;" id="centerBut"     onclick="cencerSuffix();">重置</a>

  </div>
        
   </div>
    <div id="hidparam">
	          <input type="hidden" id="taskcode" name="taskcode" value="${taskcode}" /> 
		      <input type="hidden"  id="asstable"      name="asstable" value="${asstable}"/>
		      <input type="hidden"  id="structuredid"  name="structuredid" value="${structuredid}"/>
		      <input type="hidden"  id="tablenamedesc" name="tablenamedesc" value="${tablename}"/>
		      <input type="hidden"  id="parseurl"      name="parseurl"    value="${parseurl}" />
		      <input type="hidden"  id="parsertype"    name="parsertype"  value="${parsertype}"/>
		      <input type="hidden"  id="tagpage"       name="tagpage" value="${tagpage}"/>
		      <input type="hidden"  id="encode"        name="encode" value="${encode}"/>
		      <input type="hidden" id="owner" name="owner" value="${owner}" />
		      <input type="hidden"  id="id0" name="id0"/>
    </div>
  </div>
	  <div  style="width:100%;height:100%;">
		<mytable:mytable sqlKey="getTagRuleParse" 
			    tableName="tagruleParse"
				headCols="tagcode:标签编号,
				          tagname:标签名称,
					      tagattr:标签属性,
					      tagattrval:标签属性值"
				pageSize="3"
				colPK="id"
				sqlParam='structuredid:${structuredid},taskcode:${taskcode},parsetype:${parsertype}'
				actionCol="modfiytagrule:[修改]"
				selectRowBtn="1">
		</mytable:mytable>
	</div>
  </body>
</html>
