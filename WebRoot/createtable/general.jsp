<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<html>
<head>
    <title>General</title>
    <link rel="stylesheet" type="text/css" href="<%=basePath%>easyui/themes/default/easyui.css"/>
	<link rel="stylesheet" type="text/css" href="<%=basePath%>easyui/themes/icon.css"/>
	<link rel="stylesheet" type="text/css" href="<%=basePath%>easyui/demo/demo.css"/>
	
	<script type="text/javascript" src="<%=basePath%>/createtable/js/general.js"></script>
</head>
<body>
	 <div>
		     <table style="width: 680px; height: 20px;">
		         <tr>
		           <td width="76" style="text-align: right;">Owner</td>
		           <td width="180"><%--
		           <select id="dbuser" name="dbuser" style="width: 180px;">
		             <option></option>
		             <option value="DATACVG">DATACVG</option>
		             <option value="HAIER">HAIER</option>
		             <option value="IMSDATACVG">IMSDATACVG</option>
	               </select>
	               --%>
	                <select id="dbuser" name="dbuser" style="width: 180px;">${userselect}</select>
	               </td>
                   <td width="202" style="text-align: right;"> Name</td>
		           <td width="200"><input  type="text" id="tablename" onblur="showapplysql()"
		 		 	                    style="ime-mode:disabled;width:180px;" name="tablename"/></td>
	           </tr>
	         </table>
	</div>
    <div style="width:680px; height:50%">
   	  <fieldset style="border:1px solid #AECAF0;width:680px; height:50%">
		<legend>Storage</legend>
			<table width="680">
			  <tr>
			    <td width="74" style="text-align: right">Tablespace</td>
			     <td>
			       <select id="tablespace" name="tablespace" onchange="choosespace()" style="width: 180px;">${spaceselect}</select>
			     </td>
			      <%--<option></option>
			      <option value="BS_DATA">BS_DATA</option>
			      <option value="DT_DATA">DT_DATA</option>
			      <option value="HAIER_DATA">HAIER_DATA</option>
			      <option value="NBXT_DATA">NBXT_DATA</option>
		        </select></td>--%>
			    <td width="14">&nbsp;</td>
			    <td width="182" style="text-align: right">Initial Extent</td>
			    <td width="90"><input type="text" id="initialextent" 
			     onkeyup="if(!(/^-?\d+\.?\d{0,1}$/.test(this.value))&&this.value!=''){this.value='';}"
			     onafterpaste="if(!(/^-?\d+\.?\d{0,1}$/.test(this.value))&& this.value!=''){this.value='';}"
		 		 	                    style="ime-mode:disabled;width:90px;" /></td>
			    <td width="119"><select id="extentype" name="extenttype" style="width: 90px;">
			      <option>Bytes</option>
			      <option selected="selected">KB</option>
			      <option>MB</option>
		        </select></td>
		      </tr>
			  <tr>
			    <td style="text-align: right">%Free</td>
			    <td>
			     <input id="free" onkeyup="if(!(/^-?\d+\.?\d{0,1}$/.test(this.value))&&this.value!=''){this.value='';}"
		 		 	                    name="free"
		 		 	                    onafterpaste="if(!(/^-?\d+\.?\d{0,1}$/.test(this.value))&& this.value!=''){this.value='';}"
		 		 	                    style="ime-mode:disabled;width:90px;" /> 
		 		 	 
			    </td>
			    <td>&nbsp;</td>
			    <td style="text-align: right"> Next Extent</td>
			    <td><input type="text" id="nextextent"  
			     onkeyup="if(!(/^-?\d+\.?\d{0,1}$/.test(this.value))&&this.value!=''){this.value='';}"
			     onafterpaste="if(!(/^-?\d+\.?\d{0,1}$/.test(this.value))&& this.value!=''){this.value='';}"
		 		 	                    style="ime-mode:disabled;width:90px;" /></td>
			    <td><select id="nextextentype" name="nextextentype" style="width: 90px;">
			      <option  selected="selected">Bytes</option>
			      <option>KB</option>
			      <option>MB</option>
		        </select></td>
		      </tr>
			  <tr>
			    <td style="text-align: right">%User</td>
			    <td><input type="text" id="userl" 
			      onkeyup="if(!(/^-?\d+\.?\d{0,1}$/.test(this.value))&&this.value!=''){this.value='';}"
			      onafterpaste="if(!(/^-?\d+\.?\d{0,1}$/.test(this.value))&& this.value!=''){this.value='';}"
		 		 	                    style="ime-mode:disabled;width:90px;" /></td>
			    <td>&nbsp;</td>
			    <td style="text-align: right">%Increase</td>
			    <td><input type="text" id="increase" 
			     onkeyup="if(!(/^-?\d+\.?\d{0,1}$/.test(this.value))&&this.value!=''){this.value='';}"
			     onafterpaste="if(!(/^-?\d+\.?\d{0,1}$/.test(this.value))&& this.value!=''){this.value='';}"
		 		 	                    style="ime-mode:disabled;width:90px;" /></td>
			    <td>&nbsp;</td>
		      </tr>
			  <tr>
			    <td style="text-align: right">Ini Trans</td>
			    <td><input type="text" id="initrans" 
			      onkeyup="if(!(/^-?\d+\.?\d{0,1}$/.test(this.value))&&this.value!=''){this.value='';}"
			      onafterpaste="if(!(/^-?\d+\.?\d{0,1}$/.test(this.value))&& this.value!=''){this.value='';}"
		 		 	                    style="ime-mode:disabled;width:90px;" /></td>
			    <td>&nbsp;</td>
			    <td style="text-align: right">Min Extents </td>
			    <td><input type="text"  id="minextents" 
			     onkeyup="if(!(/^-?\d+\.?\d{0,1}$/.test(this.value))&&this.value!=''){this.value='';}"
			     onafterpaste="if(!(/^-?\d+\.?\d{0,1}$/.test(this.value))&& this.value!=''){this.value='';}"
		 		 	                    style="ime-mode:disabled;width:90px;" /></td>
			    <td>&nbsp;</td>
		      </tr>
			  <tr>
			    <td style="text-align: right">Max Trans</td>
			    <td><input type="text" id="maxtrans" 
			     onkeyup="if(!(/^-?\d+\.?\d{0,1}$/.test(this.value))&&this.value!=''){this.value='';}"
			     onafterpaste="if(!(/^-?\d+\.?\d{0,1}$/.test(this.value))&& this.value!=''){this.value='';}"
		 		 	                    style="ime-mode:disabled;width:90px;" /></td>
			    <td>&nbsp;</td>
			    <td style="text-align: right">&nbsp;Max Extents</td>
			    <td><input type="text" id="maxextents"  
			      onkeyup="if(!(/^-?\d+\.?\d{0,1}$/.test(this.value))&&this.value!=''){this.value='';}"
			      onafterpaste="if(!(/^-?\d+\.?\d{0,1}$/.test(this.value))&& this.value!=''){this.value='';}"
		 		 	                     style="ime-mode:disabled;width:90px;" /></td>
			    <td><input type="checkbox" id="unlimited" name="unlimited"/>Unlimited</td>
		      </tr>
	    </table>
   	  </fieldset>
    </div>
    <br/>
     <div> &nbsp; &nbsp;Comments
       <input type="text" id="comments" style="width:  610px;"/></div>
</body>
</html>