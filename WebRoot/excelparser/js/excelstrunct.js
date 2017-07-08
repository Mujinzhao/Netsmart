$(function(){
	   // 初始化table 控件数据
	   initable();
	   // 首次绑定table 信息
	   if($("#dbuser").val()!= null && $("#dbuser").val()!=""){
			choosetab($("#dbuser").val());
	   }
	   // 提交解析任务
       $("#saveBut").bind("click",function (){
    	   var tablename = $("#dbtable").val();
    	   var tabledesc = $("select[id=dbtable]").find("option:selected").text();
    	   var paserbut =  $("#saveBut").find("span [class=l-btn-text]").html();
    	   var leftarrayval = getLeftSelectArray("add");
    	   if(paserbut=="保存"){
    		   var saveurl = encodeURI('savestructure.action?filedarray='+leftarrayval+'&tablearray='+tablename+'|'+tabledesc+'&r='+Math.random());
    	       //保存解析任务
    		   var data = $(':input,:checkbox,:radio','#myform').serialize();
    		   var obj = "exceconfig";
    		   //保存解析任务
    		   postAjax(saveurl,data,obj);
    	   }else{
    		   var modfiyarray = getLeftSelectArray("modify");
    		   var modifyurl = encodeURI('updatestructure.action?modfiyarray='+modfiyarray+'&filedarray='+leftarrayval+'&tablearray='+tablename+'|'+tabledesc+'&r='+Math.random());
    		   //保存解析任务
    		   var data = $(':input,:checkbox,:radio','#myform').serialize();
    		   var obj = "exceconfig";
    		   postAjax(modifyurl,data,obj);
    		   $("#centerBut").trigger("click");
    	   }
       });
       // 重置操作
       $("#centerBut").bind("click",function (){
    	  $("#saveBut").find("span [class=l-btn-text]").html("保存");
		  $("#centerBut").find("span [class=l-btn-text]").html("重置");
		  $("#id").val("");
		  $("#dbtable").val("");
		  $("#structuredid").val("");
		  $("#sheetname").val("");
		  $("#sheetable").val("");
		  $("#parsertype").attr("value","0");
		  $("#rowtocell").attr("checked",false);
		  $("#isactive").attr("checked",false);
		  // 清除当前用户
		  $("#dbuser").val("");
		  $("#tabfiled").find("option").remove(); 
		  $("#tabfiled2").find("option").remove(); 
       });
});

function  initable(){
	myTables.exceconfig.clickRow=function (clickval,rowdata){
		if(clickval=="tanchong"){
			var url = encodeURI(clickval+".action?" +
					"structuredid="+rowdata.structcode+
					"&asstable="+rowdata.asstable+
					"&owner="+rowdata.owner+
					"&templateid="+$("#templateid").val());
			if(window.confirm("确定填充下一条信息？")){
				ajaxPost(url);
			}
		}
		if(clickval=="parserstructure"){
			window.parent.killTab("Excel模板配置");
			var url = encodeURI("templateconfig.action?jumpage=excelconfig" +
					"&structuredid="+rowdata.structcode+
					"&asstable="+rowdata.asstable+
					"&owner="+rowdata.owner+
					"&sheetname="+rowdata.sheetname+
					"&templateid="+$("#templateid").val());
			window.parent.addTab("Excel模板配置", url);
		}
		if(clickval == "delstructure"){
			var delurl = clickval+".action?structuredid="+rowdata.structcode+"&asstable="+rowdata.asstable+"&owner="+rowdata.owner;
			var paserbut =  $("#saveBut").find("span [class=l-btn-text]").html();
			if(paserbut=="保存"){
				if(window.confirm("确定删除信息？")){
					ajaxPost(delurl);
				}
			}else{
				alert("不能删除，信息处理编辑状态！");
			}
		}
		// 修改结构信息
		if(clickval == "modfiystructure"){
			 $("#saveBut").find("span [class=l-btn-text]").html("修改结构");
			 $("#centerBut").find("span [class=l-btn-text]").html("取消修改");
		     //修改解析规则信息
			 editdata(rowdata);
		}
    };
    myTables["exceconfig"].formatCol("parsertype",function (coldata,rowdata){
		var parsertypehtml = "前后缀解析";
        var parsertype = rowdata.parsertype;
        if(parsertype == "0")
        {
        	parsertypehtml="普通解析";
        }else if(parsertype == "1")
        {
        	parsertypehtml="列传行解析";
        }
	    return parsertypehtml;
	});
    myTables["exceconfig"].formatCol("isactive",function (coldata,rowdata){
		var isactivehtml = "否";
        var isactive = rowdata.isactive;
        if(isactive == "T")
        {
        	isactivehtml="是";
        }
	    return isactivehtml;
	});
}
//修改字段绑定
function editdata(rowdata){
	$("#sheetname").val(rowdata.sheetname);
	$("#sheetable").val(rowdata.sheetable);
	$("#structuredid").val(rowdata.structcode);
	 //根据数据库用户获取表信息
	if(rowdata.owner!= null && rowdata.owner !=""){
		$("#dbuser").val(rowdata.owner);
	}
	//选中当前表信息
	var url ='parsetableselect.action?asstable='+rowdata.asstable+'&owner='+$("#dbuser").val()+'&sqlkey=getPaserSaveTable&r='+Math.random();
	tableinfo(url, "dbtable");

	if(rowdata.celltorow == "true"){
		$("#rowtocell").attr("checked",true);
	}else{
		$("#rowtocell").attr("checked",false);
	}
	if(rowdata.isactive == "T"){
		$("#isactive").attr("checked",true);
	}else{
		$("#isactive").attr("checked",false);
	}
	
	// 解析类型
	$("#parsertype").attr("value",rowdata.parsertype);

	// 加载表字段数据
	choosetabfiled(rowdata.asstable);
	// 绑定修改字段
	var url ='parsefiledselect.action?asstable='+rowdata.asstable+'&structuredid='+rowdata.structcode+'&owner='+$("#dbuser").val()+'&sqlkey=getCustomFiled&r='+Math.random();
	tablefiled(url, "tabfiled2");
} 
function ajaxPost(url){
	$.ajax({
		url:url,
		type:"POST",
		data:$(':input,:checkbox,:radio','#myform').serialize(),
		success:function(res){
		   refershData("exceconfig");
		}
	});
}
