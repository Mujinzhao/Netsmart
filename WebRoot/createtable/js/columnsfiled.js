//表字段头列信息
var $container;
var instance;
var getColHeaders =["Name","Type","Nullable","Default","Comments"];
var filedtype=["varchar2()","char()","number", "date", "long","nvarchar2()", "blob", "cblob"];
//头列初始数据信息
var getfiledData =[{
	Name : "",
	Type : "",
	Nullable : true,
	Default : "",
	Comments : ""} 
];
//数据格式化字段信息
var getfiledcolumns=[
  {
     data : "Name"
  },
  {
	  data: "Type",
      type: 'autocomplete',
      source:filedtype,// ["varchar2()","char()","number", "date", "long","nvarchar2()", "blob", "cblob"]
   }, {
		data : "Nullable",
		type : "checkbox"
	}, {
		data : "Default"
	}, {
		data : "Comments"
	} 
];
var row1 = null,row2 = null;
$(function() {
    $container = $("#example1");
    $container.handsontable({
      data: getfiledData,
      colHeaders: true,
      rowHeaders : true,
      colHeaders : getColHeaders,
      columnSorting : true,
      columns:getfiledcolumns,
      manualColumnResize : true,
	  minSpareRows :1,
	  autoWrapRow : true,
	 // contextMenu: true,
	  persistentState : true,
	  afterSelectionEnd: function(x1, y1, x2, y2){

	        //add this because of bug
	       if( (x1 <= x2 && y1 < y2) || (x1 < x2 && y1 <= y2) || (x1 == x2 && y1 == y2)) {
	            row1 = x1;
	            if(x1 == 0)
	                row2 = parseInt(x2 + 1);
	              else
	                row2 = x2;
	        }
	        else if( (x1 >= x2 && y1 > y2) || (x1 > x2 && y1 >= y2)) {
	            row1 = x2;
	            if(x2 == 0)
	                row2 = parseInt(x1 + 1);
	              else
	                row2 = x1;
	        }
	        else if(x1 < x2 && y1 > y2) {
	            row1 = x1;
	            row2 = x2;
	        }
	        else if(x1 > x2 && y1 < y2) {
	            row1 = x2;
	            row2 = x1;
	        }
	    }
    });
    instance = $container.handsontable('getInstance');
    editRows();
});
function editRows() {
    $('#addbottom').on('click', function () {
	    if(row1 != null){
	        if(row2 != null || row2 != row1 ){
	            instance.alter('insert_row', row1, row2);
	            instance.isSelected(row1,row2);
	        }
	        else{
	            instance.alter('insert_row', row1);
	            instance.isSelected(row1);
	        }
	        row1 = null,row2 = null;
	    }else{
	        alert('Please select a cell...');   
	    }
    });
    
    $('#deletebutton').on('click', function () {
	    if(row1 != null){
	        if(row2 != null){
	        	instance.alter('remove_row', row2);
	        	instance.isSelected((row2-1));
	        }else{
	        	instance.alter('remove_row', row1);
	        	instance.isSelected((row1-1));
	        }
	        row1 = null,row2 = null;
	    }else{
	        alert('Please select a cell...');   
	    }
    });

}
/**
 * 表字段组装
 * @param tablename
 * @returns {String}
 */
function getfieldInfo(tablename){
	var checkflag= false;
	var $example = $("#example1");
	var handsontable = $example.data('handsontable');

//	var jsonObj =JSON.stringify({"data" : handsontable.getData()});
	var jsonObj = handsontable.getData();
//	alert(JSON.stringify({"data" : handsontable.getData()}));
	var index =jsonObj.length;
//	var filedname = jsonObj[0].Name;
//	alert(filedname);
	var tempstr = '<br/> ( <br/>';
	var filedstr='';
	var commentstr =' comment on column ';
	var commentdiv='';
//	alert(index);
	for(var i=0;i<index;i++){
		
		var filedname = jsonObj[i].Name;
		if(filedname !=null && filedname !="" && filedname !="null"){
			filedstr += filedname +" ";
			checkflag= true;
		}
//		alert(filedname);
		var filedtype = jsonObj[i].Type;  
		if(filedtype !=null && filedtype !=""  && filedtype !="null"){
			filedstr += fontparam("green",filedtype) +" ";
			checkflag= true;
		}
//		alert(filedtype);
		var fileddefault = jsonObj[i].Default;  
		if(fileddefault !=null && fileddefault !="" && fileddefault !="null"){
			filedstr +=" default '"+fontparam("lightblue;",fileddefault)+"'";
		}
		var filednullable = jsonObj[i].Nullable;
//		alert("ｓｓｄｓ" +filednullable);
		if((filednullable == "null" || filednullable == false || filednullable == null)
				&& filedname !=null && filedname !="" && filedname !="null"){
			filedstr += fontparam("green"," not null");
		}
		var filedcomments = jsonObj[i].Comments;
		if((index-2) == i){
			i=index;
			filedstr += "<br/>";
		//	filedstr += filedname +' '+filedtype +' default '+ fileddefault +filednullable+" <br/>" ;
		}else{
			//filedstr += filedname +' '+filedtype +' '+ filednullable+",<br/>" ;
			filedstr += ",<br/>";
		}
		commentdiv+=commentstr + tablename+"."+ filedname +" is '"+ filedcomments +"';<br/>";
	}
//	alert(tempstr + filedstr +')<br/>|'+commentdiv);
//	alert(tempstr + filedstr +')<br/>');
	if(checkflag){
		return tempstr + filedstr +')|'+commentdiv;
	}
	return "";
	
//	return JSON.stringify({"data" : handsontable.getData()});
}

/**
 * 字段类型颜色
 * @param value
 * @returns
 */
function fontparam(color,value){
	var fontparam = '<font color=\"'+color+'";\">'+value+'</font>';
	return fontparam;
}
