
//同步获取json数据
var mygetJson=function (url,jsondata){
	var resjson;
	$.ajax({
		url:url||"loadParam.action",
		type:"POST",
		data:jsondata||{},
		dataType:"json",
		async:false,
		success:function (res){
			resjson=res;
		},
		error:function(err){
			alert("加载失败");
		}
	});
	return resjson;
};
//同步请求数据
var myajax=function(url,jsondata){
	
	$.ajax({
		url:url,
		type:"POST",
		data:jsondata||{},
		async:false,
		success:function (res){
			
			return res;
			
		},
		error:function(err){
			alert("加载失败");
		}
	});
};

//下拉框初始化
var myComboxSetting = function(option){
	
	var ops="";
	ops+="<option value=\"\" ></option>";
	if(option.jsondata){
		for(var i=0;i<option.jsondata.length;i++){
			
			if(option.jsondata[i].key==option.initval||option.jsondata[i].value==option.initval){
				ops+="<option value=\""+option.jsondata[i].key+"\" selected=\"selected\">"+option.jsondata[i].value+"</option>";
			}else{
				ops+="<option value=\""+option.jsondata[i].key+"\"  >"+option.jsondata[i].value+"</option>";
			}
			
		} 
	}
	$("#"+option.id).html(ops);
	
};

//tree 的初始化设置
var myTreeSetting = function (option){
	
var settree={
		loadtype:"tree",
		csswidth:undefined,
		check: {
			enable: false,
			chkboxType:{ "Y" : "s", "N" : "" }
		},
		treeclick:function (event, treeId, treeNode,clickFlag){
			
		},
	 
		data: {
			key: {
				name: "res_clname"
			},
			simpleData: {
				enable: true,
				idKey: "res_id",
				pIdKey: "res_parentid",
				rootPId: 0
			}
		}, 
		view: {
			showIcon: false,
		    selectedMulti: false,
		    showLine:true,
			dblClickExpand: false,
			expandSpeed: ""
	 
			
		},
		callback:{
			beforeClick: function(treeId, treeNode) {

			},
			onClick:function (event, treeId, treeNode,clickFlag){
				var zTree = $.fn.zTree.getZTreeObj(treeId);
				zTree.setting.treeclick(event, treeId, treeNode,clickFlag);
				if(!treeNode.isParent){
					//子节点点击api
					/*if((typeof treeNotParentNodeClick)!='undefined'){
						treeNotParentNodeClick(event, treeId, treeNode);
					}*/
				}else{
					
					//zTree.expandNode(treeNode);
					//alert(typeof treeParentNodeClick);
					//父节点点击api
					/*if((typeof treeParentNodeClick)!='undefined'){
						
						treeParentNodeClick(event, treeId, treeNode);
					}*/
				}
				
				
			}
		}
		
};
if(option){
	settree.loadtype=option.type||settree.loadtype;
	settree.csswidth=option.width;
	settree.view.showIcon=option.showIcon||settree.view.showIcon;
	settree.check.enable=option.check||settree.check.enable;
	settree.treeclick=option.treeclick||settree.treeclick;
	settree.data.key.name=option.name||settree.data.key.name;
	settree.data.simpleData.idKey=option.id||settree.data.simpleData.idKey;
	settree.data.simpleData.pIdKey=option.pid||settree.data.simpleData.pIdKey;
}

return settree;


};
//默认初始化树
var myTreeInit=function (id,jsondata,setting){
 
 
	if(setting){
		
		if(setting.loadtype=="tree"){
			return myTreeInitSetting(id,jsondata,setting);
			
		}else if(setting.loadtype=="combotree"){
			
			return myComboTreeInit(id,jsondata,setting);
		}
		
	}else{
		return myTreeInitSetting(id,jsondata,myTreeSetting());
	}
	 
};

//初始化树带设置参数
var myTreeInitSetting=function (id,jsondata,setting){
	
	$("#"+id).attr("class","ztree");
	
	return $.fn.zTree.init($("#"+id), setting, jsondata);
	
};
//下拉树
var myComboTreeInit=function (id,jsondata,setting){
	
	 
	var treewidth=(setting.csswidth)?setting.csswidth:$("#"+id).css("width");
	
	$("#"+id).css("width",treewidth); 
	var treeid=id+"_tree";
	var divtreeid=id+"_divtree";
	var divheight=jsondata.length>=30?"height:500px;":"";
	
	//创建树
	var treehtml="<div id='"+divtreeid+"' style='"+divheight+"padding-bottom:10px;background:#fff;border: 1px solid #C1DAD7;display:none;position: absolute;width:"+treewidth+";overflow-y:auto;margin-top:0;'><ul id='"+treeid+"'  class='ztree'></ul></div>";
	$("body").append(treehtml);
	//定位树
	var cityObj = $("#"+id);
	var cityOffset = $("#"+id).offset();
	$("#"+divtreeid).css({left:cityOffset.left + "px", top:cityOffset.top + cityObj.outerHeight() + "px"}).slideDown("fast");

	$("#"+divtreeid).hide();
	//绑定事件
	$("#"+id).bind("click",function(){
		
		if($("#"+divtreeid).css("display")=="none"){
			$("#"+divtreeid).show();
		}else{
			$("#"+divtreeid).hide();
		}
	});
	
	$("#"+divtreeid).mouseover(function (){
		$("#"+divtreeid).show();
	}).mouseleave(function (){
		$("#"+divtreeid).hide();
	});
	
	
	//绑定数据
	$("#"+treeid).attr("class","ztree");
	

	return myTreeInitSetting(treeid,jsondata,setting);

};

 


 
 