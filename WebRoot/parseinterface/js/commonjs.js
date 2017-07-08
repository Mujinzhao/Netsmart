 $(function(){
	  /** 
	   * 快捷方式 操作表字段上下移动
       */
	  $("#btnMoveUp,#btnMoveDown").click(function() {
   	   var $opt = $("#tabfiled2 option:selected:first");
   	   if (!$opt.length) return;   
   	   if (this.id == "btnMoveUp"){
   		   $opt.prev().before($opt);
   	   } 
   	   else{
   		   $opt.next().after($opt);   
   	   }
   	 });   
   	 //按Alt加上下鍵也可以移動   
   	 $("#tabfiled2").keydown(function(evt) {   
   	   if (!evt.altKey) return;   
   	   var k = evt.which;   
   	   if (k == 38) { $("#btnMoveUp").click(); return false; }   
   	   else if (k == 40) { $("#btnMoveDown").click(); return false; }   
   	 });   
 });

// 获取当前控件的总行数
function getTableLineNumer(){
	   var req = /共.*?(\d+).*?行/;
	   var countnum=0;
	   var countnumstr = $(".pagingdiv").text();
	   if(countnumstr != "" && countnumstr!= null &&countnumstr !=undefined){
		   var result= countnumstr.match(req); 
		   if(result !="null"){
			   countnum= result[1];
		   }
	   }
	   return countnum;
}
//却换验证方式
function checkparsertype(value){
	var checktype=null;
	if(value != null && value != "" && value !=undefined){
		checktype = value;
	}else{
	    checktype =  $("#checktype").val();
	}
	if(checktype == "0"){
		$("#parseurl1").hide();
		$("#parsepath2").show();
	}else{
		$("#parseurl1").show();
		$("#parsepath2").hide();
	}
}
//打开解析任务网址
function link_open(){
	window.open($("#testUrl").val());
	
}
// 打开创建表信息页面
function createtable_open(){
	window.parent.killTab("存储表创建"); 
	var tableurl = encodeURI("configpage.action");
	window.parent.addTab("存储表创建", tableurl);
}

/**
 * 提交后台方法
 */
function postAjax(url,data,obj){
	$.ajax({
		url:url,
		type:"POST",
		datatype:"html",
		data:data,//$(':input,:checkbox,:radio','#beforesuffix').serialize(),
		success:function(res){
			alert(res);
			refershData(obj);
		}
	});
}
/**
 * 根据改变数据库用户获取表信息
 * @param value
 */
function choosetab(value){
	if(value !=""){
	    var owner = $("#dbuser").val();
		$("#tabfiled").find("option").remove();
		var url ='parsetableselect.action?asstable='+value+'&owner='+owner+'&sqlkey=getPaserSaveTable&r='+Math.random();
		tableinfo(url, "dbtable");
	}
	else{
		$("#dbtable").find("option").remove();
		$("#tabfiled").find("option").remove();
	}
}
//表名称改变事件触发
function choosetabfiled(value){
	if(value !=""){
		var owner = $("#dbuser").val();
		$("#tabfiled2").find("option").remove();
		$("#filedstyle").show();
		var url ='parsefiledselect.action?asstable='+value+'&owner='+owner+'&sqlkey=getTableFiled&r='+Math.random();
		tablefiled(url, "tabfiled");
	}else{
		$("#tabfiled").find("option").remove();
	}
}
/**
 *  查询数据库表字段绑定
 * @param url 后台请求链接
 * @param obj 绑定控件ID
 */
function tablefiled(url,obj){
	//绑定字段列
	$("#"+obj).find("option").remove(); 
	$.ajax({
		url:url,
		type:"POST",
		dataType:"text",
		success:function(res){
           $(res).appendTo($("#"+obj));
		}
	});
}
function tableinfo(url,obj){
	//绑定字段列
	$("#"+obj).find("option").remove(); 
	$.ajax({
		url:url,
		type:"POST",
		dataType:"text",
		success:function(res){
           $(res).appendTo($("#"+obj));
		}
	});
}
//获取选中的ID信息
function getids(obj){
	var ids = myTables[obj].getSelectVals("id");
	return ids;
}


function emptyFieldOp(value){
	// 获取选择ID 值
	var filedids = getids("templateFiled");
	if(filedids == null || filedids == "" ||filedids == undefined){
		alert("请先选定操作的字段！");
		return ;
	}
	var modifyurl = "emptyfiledState.action?emptyflag="+value+"&filedidarray="+filedids+"&sqlkey=updatemptyCustomAcqfield";
	$.ajax({
			url:modifyurl,
			data:$(':input,:checkbox,:radio','#addform'),
			type:"POST",
			success:function(res){
				refershData("templateFiled");
			}
		});
}
//刷新页面
function refershData(obj){
	myTables[obj].refreshTab();
}

/******************** 设置自动左右移动*******************************/

function rightMove(){
	if($("#tabfiled").val() == ""
		|| null == $("#tabfiled").val()){
		alert("未选中需要右移字段！");
		return;
	}
	// 设置LeftFiled To RightFiled
	setLeftFiledToRightFiled();
}
function setLeftFiledToRightFiled(){
	var obj =  document.getElementById('tabfiled');
	var temp ="";
	$('#tabfiled option').each(function(i){
//		alert("第"+(i+1)+"个值为"+$(this).val());
		if(obj.options[i].selected){
			var row1 = $(this).val();
			var commdesc = $(this).text();
			if(!jsSelectIsExitItem(row1)){
				temp +="<option value='" + row1 + "'>" + commdesc + "</option>";
			}
		}
    });
    $(temp).appendTo($("#tabfiled2"));
}

function setRightSelectText(parsefiled){
	var filedvalue = parsefiled.split(",");
	var temp ="";
	for(var i=0;i < filedvalue.length;i++) 
    { 
		$('#tabfiled option').each(function(){
			if($(this).val()==filedvalue[i]){
				var row1 = $(this).val();
				var commdesc = $(this).text();
				if(!jsSelectIsExitItem(row1)){
					temp +="<option value='" + row1 + "'>" + commdesc + "</option>";
				}
			}
	    });
	}
    $(temp).appendTo($("#tabfiled2"));
}

function removeLeftSelect(){
	var j =0;
	var removeleftarry = new Array(); 
	var obj =  document.getElementById('tabfiled2');
	$('#tabfiled2 option').each(function(i){
		if(obj.options[i].selected){
//			obj.options.remove(i);   
			removeleftarry[j++]=obj.options[i]; 
		}
    });
	for(var i=0;i <removeleftarry.length;i++){ 
	    obj.removeChild(removeleftarry[i]); 
	} 
}
function getLeftSelectText(){
	var leftarry = new Array(); 
	var temp="∴";
	var tabtext="";
	$('#tabfiled2 option').each(function(i){
	    tabtext+=$(this).text()+temp;
    });
	tabtext= tabtext.substring(0,tabtext.length-1);
	leftarry.push(tabtext);
//	return tabtext;
	return leftarry;
}

function getLeftSelectArray(flag){
	var leftarry = new Array(); 
	var temp="∴";
	var tabval="";
	$('#tabfiled2 option').each(function(i){
		if(flag =="add"){
			 tabval +=  $(this).val()+"|"+$(this).text()+temp;
		}else{
			 tabval +=  $(this).val()+temp;
//			 leftarry.push(tabval);
		}
    });
	// 处理最后一位特殊字符串
	tabval = tabval.substring(0,tabval.length-1);
	leftarry.push(tabval);
	return leftarry;
}
function jsSelectIsExitItem(objItemValue) {        
   var isExit = false;    
   $('#tabfiled2 option').each(function(){
		if($(this).val()== objItemValue){
			 isExit = true;        
	         breank;
		}
    });
   return isExit;        
}
function leftMove(){
	if($("#tabfiled2").val() == ""
		|| null == $("#tabfiled2").val()){
		alert("未选中需要左移字段！");
		return;
	}
	removeLeftSelect();
}
/******************** 设置自动左右移动*******************************/
