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
<title>事件采集配置</title>
	<link rel="stylesheet" type="text/css" href="<%=basePath%>easyui/themes/default/easyui.css"/>
	<link rel="stylesheet" type="text/css" href="<%=basePath%>easyui/themes/icon.css"/>
	<link rel="stylesheet" type="text/css" href="<%=basePath%>easyui/demo/demo.css"/>
	<link href="<%=basePath%>zTree_v3/vform/css/style.css" rel="stylesheet" type="text/css" />
	<link rel="stylesheet" type="text/css" href="<%=basePath%>common/mytable/css/style.css"></link>
	<script type="text/javascript" src="<%=basePath%>core/jquery-1.8.0.min.js"></script>
	<!-- <script type="text/javascript" src="<%=basePath%>easyui/src/jquery.tabs.js"></script> -->

 	<script src="<%=basePath%>zTree_v3/vform/Validform_v5.3.2.js" type="text/javascript"></script>
 	<script src="<%=basePath%>common/mytable/js/table.js" type="text/javascript"></script>
 	<script src="<%=basePath%>/gather/js/eventconfig.js" type="text/javascript"></script>
</head>
<body>
<div  id="addparam"> 
	  <fieldset style="border:1px solid #AECAF0;">
		<legend><h4>事件配置</h4></legend>
		<div id="checkparam">
							<table>
							  <tr style="height: 20px;">
								  <td style="text-align:right;">配置地址</td>
								  <td style="text-align:left;" rowspan="1" colspan="3">
								    <label id="taskurl" name="taskurl" >${taskurl}</label>
								    <a href="javascript:link_open();" style="color:blue;">打开查看</a></td> 
								   <td style="text-align:right;">组&nbsp;编&nbsp;号</td>
								   <td style="text-align:left;" colspan="2" rowspan="1">
								       <input type="hidden" id="id"     name="id" />
								       <label style="color:blue;" id="groupcode">${groupcode}</label>
								   </td> 
						       </tr>
								<tr style="height: 20px;">
								  <td width="81" style="text-align:right;">标签类型</td>
								  <td width="233" style="text-align:left;">
								  <select style="width:220px;height: 20px;" onchange="changetagtype(this.value)" name="tagtype" id="tagtype">
								    <option value="0"> 目标标签 </option>
								    <option value="1"> 容器标签 </option>
								    </select></td>
								  <td width="89" style="text-align:right;"><span style="text-align:left;">上级编号</span></td>
								  <td width="220" style="text-align:left;"><input type="text" name="partagcode" id="partagcode"
										style="width:60px;" disabled="disabled" value="0"/></td>
								  <td width="81" style="text-align:right;"><span style="text-align:left;">标签编号</span></td>
								  <td width="337" style="text-align:left;">
								  <input type="text" name="tagcode" id="tagcode" disabled="disabled" style="width:60px;" /></td>
							  </tr>
								<tr style="height: 20px;">
								  <td style="text-align:right;">标签名称</td>
								  <td style="text-align:left;"><select style="width:220px;height: 20px;"name="tagname" id="tagname">
								    <option value="input">input</option>
									<option value="button">button</option>
									<option value="option">option</option>
									<option value="select">select</option>
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
									<option value="span">span</option>
									<option value="img">img</option>
									<option value="iframe">iframe</option>
									<option value="form">form</option>
								    </select></td>
								  <td style="text-align:right;">属&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;性</td>
								  <td style="text-align:left;"><select style="width:220px;height: 20px;"name="tagattr" id="tagattr">
								        <option value="id">id</option>
										<option value="className">class</option>
										<option value="name">name</option>
										<option value="value">value</option>
										<option value="innerText">innerText</option>
										<option value="innerHTML">innerHtml</option>
										<option value="prov">prov</option>
										<option value="src">src</option>
								    </select></td>
								  <td style="text-align:right;">属&nbsp;性&nbsp;值</td>
								  <td style="text-align:left;"><input type="text" style="width:220px;" id="tagval" name="tagval"/></td>
							  </tr>
								<tr style="height: 20px;" id="eventcontor">
								  <td style="text-align:right;">事件类型</td>
								  <td style="text-align:left;"><select style="width:220px;height: 20px;"name="tagevent" id="tagevent">
								    <option value="">-请选择--</option>
									<option value="set">设置</option>
									<option value="click">点击</option>
									<option value="change">改变选项</option>
									<option value="timedefult">时间默认</option>
									<option value="validate">识别验证码</option>
									<option value="fillvalidate">填写验证码</option>
									<option value="getdata">循环获取数据</option>
									<option value="setdate">日期控件</option>
							      </select></td>
								  <td style="text-align:right;">事件参数</td>
								  <td style="text-align:left;"><input type="text" style="width:220px;" id="tageventval" name="tageventval"/></td>
								 <td height="21" style="text-align:right"> 是否采集 </td>
			                     <td  style="text-align:left">
			                         <input id="isspider" name="isspider" style="text-align:right;" type="checkbox" value="T" />
				                 </td>
				                 <td style="text-align:right">
				                 <input type="button" id="datasource" value="数据来源"/>
				                </td>
						      </tr>
					  </table>
				</div>
		  </fieldset>
          <div>
             <div style="text-align:right;">
                  <input type="button" id="previousstep"  value="上一步"/>
                  <input type="button" id="nextpaging"  value="下一步"/>
                  <input type="button" id="eventconfig"  value="事件配置"/>
                  <input type="button" id="clearcontor"  value="重置"/>
             </div>
          </div>
          <div id="hidparam">
 	        <input type="hidden" id="pageflag" name="pageflag"/>
			<input type="hidden" id="taskcode" name="taskcode" value="${taskcode}"/>
			<input type="hidden" id="taskname" name="taskname" value="${taskname}"/>
			<input type="hidden" id="taskurl" name="taskurl"  value="${taskurl}"/>
			<input type="hidden" id="lgurl" name="lgurl"  value="${loginurl}"/>
			<input type="hidden" name="loginflag" id="loginflag" value="${loginflag}" />
			<input type="hidden" name="groupcode" id="groupcode" value="${groupcode}" /> 
			<input type="hidden" id="flag"   name="flag" />
		  </div>
</div>
<div style="width:100%;height:100%;">
	<mytable:mytable sqlKey="getEventConfig" 
		    tableName="eventinfo"
			headCols="taskcode:任务编号,
				      gcode:组编号,
				      groupdesc:组描述,
				      tagcode:标签编号,
				      partagcode:上级编号,
				      tagname:标签名称,
				      tagattr:标签属性,
				      tagattrval:标签属性值,
				      tagtype:标签类型,
				      tagattr1val:事件类型,
				      tagattr1:事件参数"
			pageSize="15"
			colPK="id"
			sqlParam='taskcode:${taskcode},groupcode:${groupcode}'
			actionCol="delevent:[删除],modifyevent:[修改]">
	</mytable:mytable>
</div>
</body>
</html>