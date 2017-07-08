<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!-- 公共文件引用 -->
<%
 
	String path = request.getContextPath();
	String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<meta http-equiv="X-UA-Compatible" content="IE=Edge" />
<base href="<%=basePath%>"/>
<!-- 系统默认样式 -->
<link rel="stylesheet"   type="text/css" href="css/default.css" />
<!-- 引入jQuery -->
<script type="text/javascript" src="core/jquery-1.8.0.min.js"></script>

<!-- 引入EasyUI -->
<link rel="stylesheet" type="text/css" href="<%=basePath%>/easyui/themes/default/easyui.css"/>
<link rel="stylesheet" type="text/css" href="<%=basePath%>/easyui/themes/icon.css"/>
<link rel="stylesheet" type="text/css" href="<%=basePath%>/easyui/demo/demo.css"/>
<script type="text/javascript" src="<%=basePath%>/easyui/jquery.easyui.min.js"></script>
 
