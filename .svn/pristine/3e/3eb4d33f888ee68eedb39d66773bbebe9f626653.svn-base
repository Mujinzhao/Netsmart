 var data = [
              {id: 1, name: "Ted", isActive: true, color: "orange", date: "2008-01-01"},
              {id: 2, name: "John", isActive: false, color: "black", date: null},
              {id: 3, name: "Al", isActive: true, color: "red", date: null},
              {id: 4, name: "Ben", isActive: false, color: "blue", date: null}
		     ];
var datacolumns = [
    {data: "id", type: 'text'},
    //'text' is default, you don't actually have to declare it
    {data: "name", renderer: yellowRenderer},
    //use default 'text' cell type but overwrite its renderer with yellowRenderer
    {data: "isActive", type: 'checkbox',},
    {data: "date", type: 'date'},
    {data: "color",
      type: 'autocomplete',
      source: ["yellow", "red", "orange", "green", "blue", "gray", "black", "white"]
    }
];
		            
var getfiledData =[{
	Name : "",
	Type : "",
	Nullable : true,
	Default : "",
	Comments : ""} 
];

var getfiledcolumns=[
  {
     data : "Name"
  },
  {
	  data: "Type",
      type: 'autocomplete',
      source: ["varchar2()","char()","number", "date", "long","nvarchar2()", "blob", "cblob"
				 ]
   }, {
		data : "Nullable",
		type : "checkbox"
	}, {
		data : "Default"
	}, {
		data : "Comments"
	} 
];
var yellowRenderer = function (instance, td, row, col, prop, value, cellProperties) {
  Handsontable.renderers.TextRenderer.apply(this, arguments);
  $(td).css({
    background: 'yellow'
  });
};
		
var greenRenderer = function (instance, td, row, col, prop, value, cellProperties) {
  Handsontable.renderers.TextRenderer.apply(this, arguments);
  $(td).css({
    background: 'none'
  });
};

$(function() {
    var $container = $("#example1");
    $container.handsontable({
      data: getfiledData,
      colHeaders: true,
      rowHeaders : true,
      colHeaders : [ "Name", "Type", "Nullable", "Default", "Comments" ],
      columnSorting : true,
      columns:getfiledcolumns,
      manualColumnResize : true,
	  minSpareRows :1,
	  autoWrapRow : true,
	  contextMenu: true,
	  persistentState : true
    });
});
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
	var index =jsonObj.length;
//	var filedname = jsonObj[0].Name;
//	alert(filedname);
	var tempstr = '<br/> ( <br/>';
	var filedstr='';
	var commentstr =' comment on column ';
	var commentdiv='';
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
		if(filednullable == "no"){
			filedstr += " not null";
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
