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
<title>普通采集配置</title>
	<link rel="stylesheet" type="text/css" href="<%=basePath%>/easyui/themes/default/easyui.css"/>
	<link rel="stylesheet" type="text/css" href="<%=basePath%>/easyui/themes/icon.css"/>
	<link rel="stylesheet" type="text/css" href="<%=basePath%>/easyui/demo/demo.css"/>
	<script type="text/javascript" src="<%=basePath%>/core/jquery-1.8.0.min.js"></script>
	<script type="text/javascript" src="<%=basePath%>/easyui/jquery.easyui.min.js"></script>
	<link rel="stylesheet" type="text/css" href="<%=basePath%>common/mytable/css/style.css"></link>
	<script src="<%=basePath%>common/mytable/js/table.js" type="text/javascript"></script>
	<link href="<%=basePath%>zTree_v3/vform/css/style.css" rel="stylesheet" type="text/css" />
 	<script src="<%=basePath%>zTree_v3/vform/Validform_v5.3.2.js" type="text/javascript"></script>
</head>
<body>
<div> 

				
							<table width="1071">
								<tr>
								  <td style="text-align:right;">任务URL</td>
								  <td colspan="3" style="text-align:left;"><input type="text" name="gatherurl" id="gatherurl" datatype="url" 
										style="width:500px;" /></td>
								  <td style="text-align:right;">网页编码</td>
								  <td style="text-align:left;"><select style="width:85px;" name="encodeurl" id="encodeurl">
								    <option value="GBK">GBK</option>
								    <option  value="UTF-8">UTF-8</option>
								    <option value="GB2312">GB2312</option>
								    <option value="GBK">IOS8859-1</option>
							      </select></td>
							  </tr>
								<tr>
								  <td width="82" style="text-align:right;">区域前缀</td>
								  <td width="234" style="text-align:left;"><input type="text" style="width:230px;" id="gathername" name="gathername"/></td>
								  <td width="77" style="text-align:right;">区域前缀</td>
								  <td width="235" style="text-align:left;"><input type="text" style="width:230px;" id="gathername2" name="gathername2"/></td>
								  <td width="102" style="text-align:right;">不包含字符串</td>
								  <td width="313" style="text-align:left;"><input type="text" style="width:230px;" id="gathername6" name="gathername6"/></td>
						      </tr>
								<tr>
								  <td style="text-align:right;">NEXT规则</td>
								  <td style="text-align:left;"><input type="text" style="width:230px;" id="nextrule" name="nextrule"/></td>
								  <td style="text-align:right;">NEXT前缀</td>
								  <td style="text-align:left;"><input type="text" style="width:230px; id="nextprifix" name="nextprifix"/></td>
								  <td style="text-align:right;">NEXT后缀</td>
								  <td style="text-align:left;"><input type="text" style="width:230px;" id="nextsuffix" name="nextsuffix"/></td>
							  </tr>
								<tr>
								  <td style="text-align:right;">符合的URL</td>
								  <td colspan="5"><textarea name="showvalue" id="showvalue" style="resize:none" cols="125" rows="9" row="row">${fieldStr}</textarea></td>
							  </tr>
					  </table>
</div>
</body>
</html>