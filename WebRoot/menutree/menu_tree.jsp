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
        <title>菜单维护</title>
        <link rel="stylesheet" type="text/css" href="../easyui/themes/default/easyui.css"/>
        <link rel="stylesheet" type="text/css" href="../easyui/themes/icon.css"/>
        <link rel="stylesheet" type="text/css" href="../easyui/demo/demo.css"/>
   
		
        <link rel="stylesheet" href="../zTree_v3/css/demo.css" type="text/css"/>
	    <link rel="stylesheet" href="../zTree_v3/css/zTreeStyle/zTreeStyle.css" type="text/css"/>
        
        <script type="text/javascript" src="../core/jquery-1.8.0.min.js"></script>
        <script type="text/javascript" src="../easyui/jquery.easyui.min.js"></script>
	    <script type="text/javascript" src="../zTree_v3/js/jquery.ztree.core-3.5.js"></script>
        <script src="../core/myutil.js"></script>
        <script src="../menutree/js/menutree.js"></script>
                <style type="text/css">

#t_content {
	width: 730px;
	height: 100px;
	margin: 0px 0px 0px 0px;
	margin-bottom: 0px;
	border-bottom: 1px solid #C1DAD7;
	background-color: #FAFCFD
}
#button_div {
	height: 40px;
}
.hr_div {
	margin: 0px;
}

.ztree li ul{ margin:0; padding:0}
.ztree li {line-height:30px;}
.ztree li a {width:200px;height:30px;padding-top: 0px;}
.ztree li a:hover {text-decoration:none; background-color: #E7E7E7;}
.ztree li a span.button.switch {visibility:hidden}
.ztree.showIcon li a span.button.switch {visibility:visible}
.ztree li a.curSelectedNode {background-color:#D4D4D4;border:0;height:30px;}
.ztree li span {line-height:30px;}
.ztree li span.button {margin-top: -7px;}
.ztree li span.button.switch {width: 16px;height: 16px;}

.ztree li a.level0 span {font-size: 150%;font-weight: bold;}
.ztree li span.button {background-image:url("../left_menuForOutLook.png"); *background-image:url("../left_menuForOutLook.gif")}
.ztree li span.button.switch.level0 {width: 20px; height:20px}
.ztree li span.button.switch.level1 {width: 20px; height:20px}
.ztree li span.button.noline_open {background-position: 0 0;}
.ztree li span.button.noline_close {background-position: -18px 0;}
.ztree li span.button.noline_open.level0 {background-position: 0 -18px;}
.ztree li span.button.noline_close.level0 {background-position: -18px -18px;}
</style>
</head>

    <body style="margin: 0px; padding: 0px">
    <br/>
     <div class="easyui-panel" style="text-align:center; width:100%;height:480px;">
		<div class="easyui-layout" data-options="fit:true">
			<div data-options="region:'west',split:true" style="width:26%;padding:15px">
			   <div class="hr_div"  style="background-color: #FAFCFD;width:256px;">
							<h2 align="left"><p>
							   &nbsp;&nbsp;&nbsp;资源树&nbsp;&nbsp;&nbsp;<input type="text" title="针对资源树节点名称检索"  id="key" class="empty" />
							</p>
							</h2>
				</div>
				<div style="background-color: #FAFCFD;overflow-x: hidden;width:256px;height:369px;" >
							     <input id="dbtable" type="hidden" value="dbtable|dbtable"/>
                                 <ul id="treeDemo" class="ztree"></ul>
				</div>
				<div style="background-color: #FAFCFD">
                   <table id="t_button">
									<tr>
										<td>
											<input type="button" value="新增 "
												id="add_button" class="_button" disabled="disabled"
												style="width: 70px;" />
										</td>
										<td>
											<input type="button"
												value="删除" id="delete_button"
												class="_button" disabled="disabled" style="width: 70px;" />
										</td>
										<td>
											<input type="button"
												value="修改" id="edit_button"
												class="_button" disabled="disabled" style="width: 70px;" />
										</td>
									</tr>
								</table>
                   </div>
			</div>
		    <div data-options="region:'center'" style="width:100px;padding:10px">
			           <iframe id="parampage" name="mainFrame" scrolling="no" frameborder="0"  src="<%=basePath%>/menutree/menu.jsp" style="margin:0px;width:100%;height:100%;"></iframe>
			</div>
		</div>
	</div>
	</body>
</html>
