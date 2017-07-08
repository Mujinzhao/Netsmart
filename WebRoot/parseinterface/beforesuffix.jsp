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
 	<link rel="stylesheet" type="text/css" href="<%=basePath%>easyui/themes/default/easyui.css"/>
	<link rel="stylesheet" type="text/css" href="<%=basePath%>easyui/themes/icon.css"/>
	<link rel="stylesheet" type="text/css" href="<%=basePath%>easyui/demo/demo.css"/> 
	<link rel="stylesheet" type="text/css" href="<%=basePath%>common/mytable/css/style.css"></link>
	<link rel="stylesheet" type="text/css" href="<%=basePath%>uploadify/uploadify.css"></link>
	<link href="<%=basePath%>zTree_v3/vform/css/style.css" rel="stylesheet" type="text/css" />
	<script type="text/javascript" src="<%=basePath%>core/jquery-1.8.0.min.js"></script>
	<script src="<%=basePath%>common/mytable/js/table.js" type="text/javascript"></script>
	<script type="text/javascript" src="<%=basePath%>easyui/jquery.easyui.min.js"></script>
	<script type="text/javascript" src="<%=basePath%>easyui/src/jquery.tabs.js"></script>

 	<script src="<%=basePath%>zTree_v3/vform/Validform_v5.3.2.js" type="text/javascript"></script>
 	<script src="<%=basePath%>uploadify/jquery.uploadify.js" type="text/javascript"></script>
	<script src="<%=basePath%>common/js/ajaxfileupload.js" type="text/javascript"></script>
	
	<script src="<%=basePath%>parseinterface/js/commonjs.js" type="text/javascript"></script>
    <script src="<%=basePath%>parseinterface/js/beforesuffix.js" type="text/javascript"></script>
</head>
<body>
	<div id="divsuffix">
		<table width="1130">
			<tr style="height: 20px;">
			  <td align="center">验证方式</td>
			  <td><input name="checktype" type="radio" id="checktype"
					onclick="checkparsertype(this.value)" value="0" checked="checked" />
			    本地文件
			    <input type="radio" name="checktype" id="checktype"
					onclick="checkparsertype(this.value)" value="1" />
			    网址</td>
			  <td><span style="text-align: right;">验证地址</span></td>
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
					</table>
					<!-- <div  style="text-align: left;" class="Validform_checktip Validform_wrong parsefile"></div> -->
              </td>
		  </tr>
		  <tr>
		      <td align="center"><span style="text-align: right;">解析方式</span></td>
			   <td><input type="radio" id="parserstatus" name="parserstatus" value="0" checked="checked" />
			        前后缀
			    <input type="radio" name="parserstatus" id="parserstatus"  value="1" />
			         正则</td>
			  <td><span style="text-align: right;">验证结果 </span></td>
			  <td colspan="10" rowspan="7">
              <textarea  name="showvalue" id="showvalue" style="resize:none" rows="12" cols="70" readonly="readonly"></textarea>
              </td>
		  </tr>
			<tr style="height: 20px;">
			  <td align="center"><span style="text-align: right;">数据存储</span></td>
			  <td><input type="text" style="width:220px;" id="datatable"
					name="datatable" disabled="disabled" value="${tablename}" /></td>
			
		  </tr>
			<tr style="height: 20px;">
			  <td align="center"><span style="text-align: right;">解析字段</span></td>
			  <td><select style="width:220px;" name="parsefiled"
					id="parsefiled"> ${fieldselect} </select></td>
			  <td>&nbsp;</td>
		  </tr>
		  <tr style="height: 20px;" id="suffixcheck">
			<!--nullmsg="规则前缀不能为空"-->
			  <td width="86" align="center"><span style="text-align: left;">规则前缀</span></td>
			  <td width="351"><input type="text" name="prefixrule" datatype="*" 
					 id="prefixrule" style="width:220px;" />
					</td>
			  <td>&nbsp;</td>
		  </tr>
			<tr style="height: 20px;">
			  <td align="center"><span style="text-align: right;">规则后缀</span></td>
			  <td><input type="text" name="suffixrule" datatype="*"
					id="suffixrule" style="width:220px;" />
			    </td>
			  <td>&nbsp;</td>
		  </tr>
			<tr style="height: 20px;" id="regexcheck">
			  <td align="center">正则表达式</td>
			  <td><input type="text"
					name="extractregex" id="extractregex" datatype="*" style="width:220px;" /></td>
			  <td>&nbsp;</td>
		  </tr>
			<tr style="height: 20px;">
			  <td align="center"><span style="text-align: right;">排除内容</span></td>
			  <td><input type="text" name="outRegex" id="outRegex"
					style="width:220px;" /></td>
			  <td>&nbsp;</td>
		  </tr>
		</table>
		<div>
			<div style="text-align:right;">
				<a class="easyui-linkbutton" id="nexttagindex"
					style="width:90px;height:24spx;" onclick="nextsetp();">下一步</a> <a
					class="easyui-linkbutton" style="width:100px;height:24spx;"
					onclick="checkSuffix();">验证解析信息</a> <a class="easyui-linkbutton"
					style="width:100px;height:24px;" id="savesuffix"
					onclick="saveSuffix();">规则入库</a> <a class="easyui-linkbutton"
					style="width:60px;height:24px;" id="centerBut"
					onclick="cencerSuffix();">重置</a>
		  </div>
	  </div>
	  <div id="hidparam">
			    <input type="hidden" id="asstable" name="asstable" value="${asstable}" />
			    <input type="hidden" id="taskcode" name="taskcode" value="${taskcode}" /> 
				<input type="hidden" id="structuredid" name="structuredid" value="${structuredid}" />
				<input type="hidden" id="tablenamedesc" name="tablenamedesc" value="${tablename}" /> 
				<input type="hidden" id="parseurl" name="parseurl" value="${parseurl}" /> 
				<input type="hidden" id="parsertype" name="parsertype" value="${parsertype}" />
				<input type="hidden" id="tagpage" name="tagpage" value="${tagpage}" />
				<input type="hidden" id="id" name="id" />
				<input type="hidden" id="encode" name="encode" value="${encode}" />
				<input type="hidden" id="owner" name="owner" value="${owner}" />
		</div>
</div>
	<div style="width:100%;height:100%;">
		<mytable:mytable sqlKey="getParserBeforesuffix"
			tableName="suffixInfo"
			headCols="acqfield:解析字段,
			          acqfieldesc:字段描述,
				      prefixrule:规则前缀 ,
				      suffixrule:规则后缀 ,
				      outregex:排除内容,
				      exregex:正则表达式"
			pageSize="5" colPK="id"
			sqlParam='structuredid:${structuredid},asstable:${asstable},parsetype:${parsertype}'
			actionCol="delsuffix:[删除],modfiysuffix:[修改]">
		</mytable:mytable>
	</div>
</body>
</html>