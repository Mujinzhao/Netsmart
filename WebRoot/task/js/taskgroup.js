var showflag = 0;
var $winfiled;
$(function (){
	   // 初始化表格控件数据
	   initable();
       // 提交解析任务
       $("#saveBut").bind("click",function (){
    	  // alert($("#myform").find("*").serialize());
    	   var tablename = $("#dbtable").val();
    	   var tabledesc = $("select[id=dbtable]").find("option:selected").text();
    	   var leftarrayval = getLeftSelectArray("add");
    	   var paserbut =  $("#saveBut").find("span [class=l-btn-text]").html();
    	   if(paserbut=="保存"){
    		   //验证多结构信息
    		   if(leftarrayval !="" && leftarrayval!=null && leftarrayval != undefined){
	    			    var morestrutscheck = $("#morestruts").attr("checked");
	    			    // 检测当前控件总行数
    			    	var tablenumber = getTableLineNumer();
	    			    if(morestrutscheck != undefined && morestrutscheck == "checked"){
	    			    	var checkfiled = myTables["parsetask"].checkColumnData("morestructured","是");
	 	    			    if(tablenumber >=1 && !checkfiled ){
	 	    				   alert("同一解析任务不能包含两种解析模板！");
	 	    				   return ;
	 	    			    }
	    			    }else{
	    			    	//alert(tablenumber);
	 	    			    if(tablenumber >= 1){
	 	    				   alert("同一解析任务不能包含两种解析模板！");
	 	    				   return ;
	 	    			    }
	    			    }
	    			   $("#taskname").attr("disabled",false);
    	    		   var saveurl = encodeURI(encodeURI('saveParsertask.action?filedarray='+leftarrayval+'&tablearray='+tablename+'|'+tabledesc+'&r='+Math.random()));
    	    	       //保存解析任务
    	    		   postAjax(saveurl);
    	    		   $("#taskname").attr("disabled",true);
    	       }
    	   }else{
//    		   if (){
//    			   alert("存在解析模板，无法修改！")
//    		   }
    		   var modfiyarray = getLeftSelectArray("modify");
    		   var modifyurl = encodeURI(encodeURI('modifyParsertask.action?modfiyarray='+modfiyarray+'&filedarray='+leftarrayval+'&tablearray='+tablename+'|'+tabledesc+'&r='+Math.random()));
    	       //保存解析任务
    		   postAjax(modifyurl);
    	   }
       });
       // 取消修改
       $("#centerBut").bind("click",function (){
    	  $("#saveBut").find("span [class=l-btn-text]").html("保存");
		  $("#centerBut").find("span [class=l-btn-text]").html("重置");
//		  $('#myform') .not(':button, :submit, :reset, :hidden')  .val('')  .removeAttr('checked')  .removeAttr('selected') .removeAttr('class');
		  $("#id").val("");
		  $("#dbtable").val("");
		  $("#structuredid").val("");
		  $("#parsercode").val("");
		  $("#parsertype").attr("value","0");
		  $("#dbtable").attr("disabled",false);
		  $("#morestruts").attr("checked",false);
		  $("#rowtocell").attr("checked",false);
		  $("#taskname").attr("disabled",true);
		  $("#taskname").val($("#oldtkname").val());
		  // 清除当前用户
		  $("#dbuser").val("");
		  $("#dbtable").find("option").remove();
		  $("#tabfiled").find("option").remove(); 
		  $("#tabfiled2").find("option").remove(); 
       });
    	 // 多结构点击事件
    	 $("#morestruts").click(function(){
    		    var morestrutscheck = $("#morestruts").attr("checked");
			    if(morestrutscheck != undefined && morestrutscheck == "checked"){
    		          $("#taskname").attr("disabled",false);
			    }else{
			    	  $("#taskname").attr("disabled",true);
			    }
    	 });
});

function  initable(){
	myTables.parsetask.clickRow=function (clickval,rowdata){
		if(clickval == "parserstructure"){
			window.parent.killTab("解析模板配置");
			var parsertype=rowdata.parsertype;
			// 处理采集URL &问题
			var taskurl=rowdata.taskurl;
		    if (taskurl.indexOf("&") != -1) {
		    	taskurl=taskurl.replace(/\&/g,"$"); 
		    }
		    var tempparseurl = encodeURI(encodeURI("parserconfig.action?parsertype="+parsertype+"&taskcode="+rowdata.taskcode+"&tablenamedesc="+rowdata.asstablename+"&owner="+rowdata.owner+"&asstable="+rowdata.asstable+"&structuredid="+rowdata.structuredid+"&parseurl="+taskurl+"&encode="+rowdata.encodeurl));
		    window.parent.addTab("解析模板配置", tempparseurl);
		}else if(clickval == "parsercheck"){
			postAjax("checkparser.action?taskcode="+rowdata.parserfilecode);
		}
		// 非空字段指定
		else if(clickval == "emptyfiled"){
			var taskurl = encodeURI(encodeURI("emptyfiled.action?structuredid="+rowdata.structuredid+"&asstable="+rowdata.asstable+"&owner="+rowdata.owner));
			emptyField(taskurl);
		}else if(clickval == "modfiystructure"){
			 $("#saveBut").find("span [class=l-btn-text]").html("修改任务");
			 $("#centerBut").find("span [class=l-btn-text]").html("取消修改");
		     //修改解析规则信息
			 editdata(rowdata);
			
		}else if(clickval == "delstructure"){
			var delurl = "delParsertask.action?structuredid="+rowdata.structuredid;
			var paserbut =  $("#saveBut").find("span [class=l-btn-text]").html();
			if(paserbut=="保存"){
				postAjax(delurl);
			}else{
				alert("当前数据处于编辑状态，无法删除！");
			}
		}
	};
	myTables["parsetask"].formatCol("morestructured",function (coldata,rowdata){
		var morestructuredhtml = "否";
        var morestructured = rowdata.morestructured;
        if(morestructured == "1")
        {
        	morestructuredhtml="是";
        }
	    return morestructuredhtml;
	});
	myTables["parsetask"].formatCol("parsertype",function (coldata,rowdata){
		var parsertypehtml = "前后缀解析";
        var parsertype = rowdata.parsertype;
        if(parsertype == "1")
        {
        	parsertypehtml="标签解析";
        }else if(parsertype == "2")
        {
        	parsertypehtml="综合解析";
        }
        else if(parsertype == "4")
        {
        	parsertypehtml="元搜索解析";
        }
	    return parsertypehtml;
	});
   
	// 首次绑定table 信息
	
	if($("#dbuser").val()!= null && $("#dbuser").val()!=""){
		
		choosetab($("#dbuser").val());
	}
}

// 后台方法提交相应
function  postAjax(saveurl){
//	alert($("#myform").find("*").serialize());
	$.ajax({
			url:saveurl,
			type:"POST",
			data:$("#myform").find("*").serialize(),
			success:function(res){
				$("#centerBut").trigger("click");
				reloadmyTable("parsetask");
			}
		});
}

function emptyField(taskurl){
	// 设置iframe src值
	$("#emptfield").attr("src",taskurl);
	$('#w').window('open');
}
function closemptyField(){
	$('#w').window('close');
}
function reloadmyTable(obj){
	myTables[obj].refreshTab();
}

// 修改字段绑定
function editdata(rowdata){
	$("#taskname").attr("disabled",false);
	$("#taskname").val(rowdata.structuredesc);
	 //根据数据库用户获取表信息
	if(rowdata.owner!= null && rowdata.owner !=""){
		$("#dbuser").val(rowdata.owner);
	}
	//选中当前表信息
	var url ='parsetableselect.action?asstable='+rowdata.asstable+'&owner='+$("#dbuser").val()+'&sqlkey=getPaserSaveTable&r='+Math.random();
	tableinfo(url, "dbtable");
	
	$("#structuredid").val(rowdata.structuredid);
	if(rowdata.morestructured == "1"){
		$("#morestruts").attr("checked",true);
	}else{
		$("#morestruts").attr("checked",false);
	}
	if(rowdata.celltorow == "true"){
		$("#rowtocell").attr("checked",true);
	}else{
		$("#rowtocell").attr("checked",false);
	}
	
	$("#taskname").attr("disabled",false);
//	else{
//		$("#taskname").attr("disabled",true);
//	}
	// 解析类型
	$("#parsertype").attr("value",rowdata.parsertype);

	// 解析文件编号
	$("#parsercode").val(rowdata.parserfilecode);

	// 加载表字段数据
	choosetabfiled(rowdata.asstable);
	
	// 绑定修改字段
	var url ='parsefiledselect.action?asstable='+rowdata.asstable+'&structuredid='+rowdata.structuredid+'&owner='+$("#dbuser").val()+'&sqlkey=getTableFiledByStructuredid&r='+Math.random();
	tablefiled(url, "tabfiled2");
	
}

//去除字符串的首尾的空格   
function trim(str){   
return str.replace(/(^\s*)|(\s*$)/g, "");   
}   