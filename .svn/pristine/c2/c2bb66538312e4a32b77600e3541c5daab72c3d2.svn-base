$(function (){
	initable();
});
function initable(){
	myTables["parsefiled"].formatCol("emptyacqfield",function (coldata,rowdata){
		var emptyfiledhtml = "空";
        var emptyacqfield = rowdata.emptyacqfield;
        if(emptyacqfield == "1")
        {
        	emptyfiledhtml="非空";
        }
	    return emptyfiledhtml;
	});
}

function emptyFieldOp(value){
	// 获取选择ID 值
	var filedids = getcheckboxVal("parsefiled");
	if(filedids == null || filedids == "" ||filedids == undefined){
		alert("请先选定操作的字段！");
		return ;
	}
	var modifyurl = "emptyfiledState.action?emptyflag="+value+"&filedidarray="+filedids+"&sqlkey=updatemptyAcqfield";
	$.ajax({
			url:modifyurl,
			data:$(':input,:checkbox,:radio','#addform'),
			type:"POST",
			success:function(res){
				reloadmyTable("parsefiled");
			}
		});
}

function  emptyclose(){
	window.parent.closemptyField();
}
function reloadmyTable(obj){
	myTables[obj].refreshTab();
}
function getcheckboxVal(obj){
	var filedid = myTables[obj].getSelectVals();
	return filedid;
}
