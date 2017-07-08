var iframeObj;
var ciframeObj;
// choose tablspace 
// 选择表空间触发对应空间
function chooseTablespace(){
	iframeObj = $("#general").contents();
	iframeObj.find("#initialextent").val("64");
	iframeObj.find("#minextents").val("1");
	iframeObj.find("input[id=unlimited]").attr("checked",true);
}

function showapplysql(){
	iframeObj = $("#general").contents();
	var tablename =  iframeObj.find("#tablename").val();
	if(tablename == "" || tablename == null){
		$('#applystate').removeAttr('href');//去掉a标签中的href属性 
		$('#applystate').removeAttr('onclick');//去掉a标签中的onclick事件 
	}else{
		$("#applystate").attr("onclick","applysql();");
	}
}
/**
 * sql语句执行  将组装的sql 语句存入资源文件执行
 */
function applysql(){
	  var content = $("#sqlinfo").html();
	  content = content.replace(new RegExp(/&nbsp;/g),' ');//将SQL语句中所有&nbsp;替换成' '
	  content = delHtmlTag(content);
//	  content = content.replaceAll("&nbsp;"," ");
	  // 去除空格
	  var tablename = iframeObj.find("#tablename").val();
//	  alert(tablename);
	  if(tablename == "" || tablename == null){
		  alert("请填写创建的表名!");
		  return ;
	  }
	  //alert(content);
	  // ajax 提交数据后台处理
	  var url ="runsql.action?tablecontent="+content+"&filename="+tablename;
	  url = encodeURI(encodeURI(url));
	  $.ajax({
			url:url,
			type: 'POST',
	        dataType:"text",
			success:function (data){
				if(data=="success"){
					alert("数据表创建成功!");
				}else{
					alert(data);
				}
			}
		});
}

/**
 * 组装创建表的sql
 * 包含三部分  
 * 创建表名
 * 所属表空间
 * 表字段描述
 */
function viewsql(){
    iframeObj = $("#general").contents();
    ciframeObj =  document.getElementById('columnsFiled').contentWindow;
    var ownername = iframeObj.find("#dbuser option:selected").val();
    var tempstr='create table ';
    var filedarray = '';
    var filedstr='';
    // 获取表名称值
    var tablename = iframeObj.find("#tablename").val();
    if(tablename !=null && tablename !=""){
    	// 表名称设置
    	tablename = ownername+'.'+tablename;
    	tempstr+=fontparam(tablename);
    	// 表字段设置
        var  filedandcomment = ciframeObj.getfieldInfo(fontparam(tablename));
        if(filedandcomment != null && filedandcomment != ""){
    	   filedarray =  filedandcomment.split("|");
       	   tempstr +=filedarray[0];
       	   filedstr =filedarray[1];
        }
    }else{
    	$("#applysql").attr("disabled",true);
    }
    // 表空间参数设置
    var tablespace = iframeObj.find("#tablespace").val();
    if(tablespace !=null && tablespace !=""){
    	tempstr+='<br/>tablespace '+fontparam(tablespace)+'<br/>';
    	var free =  iframeObj.find("#free").val();//obj.document.getElementById('free').value;
    	if(free !=null && free !=""){
    		tempstr+='&nbsp;pctfree '+fontparam(free)+'<br/>';
    	}else{
    		tempstr+='&nbsp; pctfree '+fontparam(10)+'<br/>';
    	}
        var userl = iframeObj.find("#userl").val();//obj.document.getElementById('userl').value;
        if(userl !=null && userl !=""){
    		tempstr+='&nbsp; pctused '+fontparam(userl)+'<br/>';
    	}
    	var initrans = iframeObj.find("#initrans").val();//obj.document.getElementById('initrans').value;
	    if(initrans !=null && initrans !=""){
    		tempstr+='&nbsp; initrans '+fontparam(initrans)+'<br/>';
    	}else{
    		tempstr+='&nbsp; initrans '+fontparam(1)+'<br/>';
    	}
	    var maxtrans = iframeObj.find("#maxtrans").val();// obj.document.getElementById('maxtrans').value;
    	if(maxtrans !=null && maxtrans !=""){
    		tempstr+='&nbsp; maxtrans '+fontparam(maxtrans)+'<br/>';
    	}else{
    		tempstr+='&nbsp; maxtrans '+fontparam(255)+'<br/>';
    	}
    	tempstr+=' storage<br/>('+'<br/>';
    	
    	var initialextent = iframeObj.find("#initialextent").val();//obj.document.getElementById('initialextent').value;
    	if(initialextent !=null && initialextent !=""){
    		tempstr+=' &nbsp;&nbsp;initial '+fontparam(initialextent)+'K<br/>';
    	}
    	var nextextent = iframeObj.find("#nextextent").val();// obj.document.getElementById('nextextent').value;
    	if(nextextent !=null && nextextent !=""){
    		tempstr+=' &nbsp;&nbsp;next '+fontparam(nextextent)+'<br/>';
    	}
    	var minextents = iframeObj.find("#minextents").val();//obj.document.getElementById('minextents').value;
    	if(minextents !=null && minextents !=""){
    		tempstr+=' &nbsp;&nbsp;minextents '+fontparam(minextents)+'<br/>';
    	}
 	    var maxextents =  iframeObj.find("#maxextents").val();//obj.document.getElementById('maxextents').value;
 	    if(maxextents !=null && maxextents !=""){
    		tempstr+=' &nbsp;&nbsp;maxextents '+fontparam(maxextents)+'<br/>';
    	}else{
    		tempstr+='&nbsp;&nbsp;maxextents unlimited<br/>';
    	}
 	    var increase = iframeObj.find("#increase").val();//obj.document.getElementById('increase').value;
 	    if(increase !=null && increase !=""){
    		tempstr+=' &nbsp;&nbsp; pctincrease '+fontparam(increase)+'<br/>';
    	}
 		tempstr+=');<br/>';
 		$("#applysql").attr("disabled",false);
    }else{
    	tempstr+='<br/>; ';
    }
    // 表描述
	var tablecommens = iframeObj.find("#comments").val();//obj.document.getElementById('increase').value;
	if(tablecommens !=null && tablecommens !="")
	{
		tempstr+="comment on table "+tablename+" is '"+tablecommens+"' ;<br/>";
	}
	// 表字段别名设置
	tempstr += filedstr;
    showandhide(tempstr);
} 
/**
 * 关键字颜色
 * @param value
 * @returns
 */
function fontparam(value){
	var fontparam = '<font color=\"lightblue;\">'+value+'</font>';
	return fontparam;
}
/**
 * viewsql 显示跟隐藏的却换
 */
function showandhide(tempstr){
	    var showflag = $("#showflag").val();
	    if(showflag == null || showflag == "" || showflag=="tabs"){
	    	 $("#tablepage").hide();
	    	 $("#showsql").show();
	    	 $("#showflag").val("showsql");
	    	 
	    	 $("#sqlinfo").text("");
	    	 $("#sqlinfo").html(tempstr);
	    }else{
	    	 $("#tablepage").show();
	    	 $("#showsql").hide();
	    	 $("#showflag").val("tabs");
	    }
}
//去掉所有的html标记
function delHtmlTag(str){
	  return str.replace(/<[^>]+>/g,"");
}
