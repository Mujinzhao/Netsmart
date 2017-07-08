function getCarData() {
	return [ {
		Name : "",
		Type : "",
		Nullable : 'no',
		Default : "",
		Comments : ""
	} ];
}
$(function() {
	var $example = $("#example1");
	var $parent = $example.parent();
	$example.handsontable({
		data : getCarData(),
		rowHeaders : true,
		colHeaders : [ "Name", "Type", "Nullable", "Default", "Comments" ],
		columnSorting : true,
		columns : [
				{
					data : "Name"
				},
				{
					data : "Type",
					type : 'autocomplete',
					source : ["varchar2()","char()","number", "date", "long",
					          "nvarchar2()", "blob", "cblob"
							 ],
					strict : false
				}, {
					data : "Nullable",
					type : "checkbox",
					checkedTemplate : 'yes',
					uncheckedTemplate : 'no'
				}, {
					data : "Default"
				}, {
					data : "Comments"
				} ],
		manualColumnResize : true,
		minSpareRows : 0,
		autoWrapRow : true,
		contextMenu: true,
		persistentState : true
	});
	var handsontable = $example.data('handsontable');
	$parent
			.find('button[name=Apply]')
			.click(
					function() {
						alert(JSON.stringify({
							"data" : handsontable.getData()
						}));
						$.ajax({
									url : "", //"json/save.json",
									data : JSON.stringify({
										"data" : handsontable.getData()
									}), //returns all cells' data
									dataType : 'json',
									type : 'POST',
									success : function(res) {
										if (res.result === 'ok') {
										//	$console.text('Data saved');
										} else {
										//	$console.text('Save error');
										}
									},
									error : function() {
//										$console
//												.text('Save error. POST method is not allowed on GitHub Pages. '
//														+ 'Run this example on your own server to see the success message.');
									}
								});
					});
	$parent.find('button[name=ViewSQL]').click(function() {
		$("#sqlview").show();
		$("#sqlshow").html("-- Create table \n <br/>"+
				"create table PARSER_FILE_LIST \n <br/>" +
				"(\n <br/>" +
				" FILE_PKID        VARCHAR2(30),\n <br/>" +
				" TEMPLATE_ID      VARCHAR2(30),\n <br/>" +
				" FILE_FNAME       VARCHAR2(200)\n <br/>" +
				")");
	});
});

function checkname(value){
	if(value != null && value!=""){
		$("#Apply").attr("disabled",false);
		$("#Query").attr("disabled",false);
		$("#ViewSQL").attr("disabled",false);
	}else{
		$("#Apply").attr("disabled",true);
		$("#Query").attr("disabled",true);
		$("#ViewSQL").attr("disabled",true);
	}
}
