/**
 * jquery mytable
 * 
 * 
 * Copyright (c) 2014 By ma qianli [ma.ql@qq.com]
 * 
 */

//访问地址

var reqURL="paging";
//导出地址
var expURL="expdoc";

//自定义属性,指向表格
var param_for="fortable";
//自定义属性,参数的作用search(查询),param(参数),export(导出等等)
var param_type="roletype";
//查询
var type_search="search";
//参数
var type_param="param";
//导出
var type_export="export";
var myTablesArr=[];
//
var myTables={};

//绑定各种事件
var bindTable=function (tabid){
	
	var $tab=$("#"+tabid);
	
	var colarr=[];
	//最大行数限制
	var maxRows=100;
	//首次是否初始化前
	var isInitPre=false;
	//首次是否初始化
	var isInit=false;
	var rowarrs={};
	//格式化列数组
	var fmtFuns=[];
	$tab.fmtFuns=fmtFuns;
	
	//默认异步请求数据
	$tab.async=true;
	
	var options= {
			beforeSearch:function (){
				
			},
			refreshBefor:function(befor){
				if(befor)
				this.refreshBefor=befor;
			},
			//callback
			clickRow:function(clickval,rowdata){

			},
			// 根据传入的字段 获取列数据是否存在
			checkColumnData:function (colname,value){
				var checkflag = false;
				colname=colname.toLowerCase();
				// 获取列下标
				var colindex=$tab.find("tr").find("[cname="+colname+"]").index();
				//当前列不存在
			    if(colindex<0){
				   return;
			    }
				$tab.find("tr").find("td:eq("+colindex+")").each(function (){
					var colvalue=$(this).attr("title");
					if(colvalue == undefined){
						colvalue =$(this).text();
					}
					if(colvalue == value){
						 checkflag=true;
						 return false;
					}
			    });
				return checkflag;
				
			},
			
			// 获取点击行数下标值
			getTabIndex:function (colname,value){
//				var colindex=$tab.find("tr").find("[id="+idstr+"]").index();
				var trindex=0;
				colname=colname.toLowerCase();
				// 获取列下标
				var colindex=$tab.find("tr").find("[cname="+colname+"]").index();
				//当前列不存在
			    if(colindex<0){
				   return;
			    }
				$tab.find("tr").find("td:eq("+colindex+")").each(function (i){
					var colvalue=$(this).attr("title");
					if(colvalue == undefined){
						colvalue =$(this).text();
					}
					if(colvalue == value){
//						 alert("第"+i+"行"); 
						 trindex= i;
						 return false;
					}
			    });
				return trindex;
			},
			//获取tab相关参数
			 getTabParam:function(){
				
				 var inputPageSize=parseInt($tab.find("#txtpagesize").val());
				 inputPageSize=(inputPageSize>maxRows)?maxRows:inputPageSize;
				 inputPageSize=inputPageSize?inputPageSize:10;
				 //设置每页显示行数
				 $tab.find("#hid_pageSize").val(inputPageSize);
				 
				 //替换参数
				this.replaceParam();
				
				var params=  $("["+param_for+"="+$tab.attr("id")+"]["+param_type+"="+type_param+"]").serialize().replace(/&/g, ",").replace(/=/g,":");

				if(params!=""){
					$tab.find("#hid_dynamicParam").val(params);
					var sqlparam=$tab.find("#hid_sqlParam").val();
					if(sqlparam){
						//如果存在替换
						$tab.find("#hid_sqlParam").val(sqlparam+","+params);
					}else{
						$tab.find("#hid_sqlParam").val(params);
					}
				}
				
				return $tab.find("input").serialize();
				
			},
			//替换参数
			replaceParam:function (){
				
				var dyparams=  $("["+param_for+"="+$tab.attr("id")+"]["+param_type+"="+type_param+"]");
				var param= $tab.find("#hid_sqlParam");
				var curobj=this;
				if(param.val()!=""){
					var arrs= param.val().split(",");
					 
					for(var i=0;i<arrs.length;i++){
						var key =arrs[i].split(":")[0];
						
						dyparams.each(function (){ 
							
							 if($(this).attr("name")==key){
								 
								 curobj.removeParam(key);
								
								return false;
									
							}
							 
						});
					}
	
					 
				}
			 
				return param.val();
				
			},
			//分页查询
			paingSearch:function(){
				$tab.tabMask();
				$.ajax({
					url:reqURL,
					type:"POST",
					data:this.getTabParam()+"&r="+Math.random(),
					async:$tab.async,
					success:function (res){
						$tab.tabUnMask();
						if(res!=""){
							$tab.show();
							 
							$tab.find(".dynamic").html($(res).find(".dynamic").html());
							if(!isInitPre){
								$tab.initPre();
							}
							//首次未初始化,则执行
							if(!isInit){
								$tab.init();
							}
							//是否存在格式化函数
							if($tab.fmtFuns.length>0){
								
								//alert($tab.fmtFuns.length);
								for ( var i = 0; i < $tab.fmtFuns.length; i++) {
									var obj=$tab.fmtFuns[i];
									//var var_obj={"cname":colname,"formatfun":fromatfun};
									$tab.formatCol(obj.cname,obj.formatfun);
								}
							}
							$tab.setMaskHeight();
						}else{
							$tab.hide();
						}
					},
					error:function (XMLHttpRequest, textStatus, errorThrown){
						$tab.tabUnMask();
						alert("请求发生错误！"+textStatus);
						
					}
					
				});
				//$tab.tabUnMask();
				$tab.refreshBefor();
			},
		 
			//刷新重载
			refreshTab: function (async){
				//$tab.refreshBefor();
				if(async){
					$tab.async=async;
				}
				
				//$tab.find("#hid_pageIndex").val("1");
				$tab.find("#hid_sortCol").val("");
				$tab.find("#hid_sortVal").val("");
				this.paingSearch();
				$tab.async=true;
			},
			//添加参数
			getParam: function(key){

				var param= $tab.find("#hid_sqlParam");
				
				if(param.val()!=""){
					var arrs= param.val().split(',');
					for(var i=0;i<arrs.length;i++){
						if(arrs[i].indexOf(key+":")>=0){
							return arrs[i].replace(key+":","");
						}
					}
				}
			},
			//移除参数
			removeParam: function(key){

				var param= $tab.find("#hid_sqlParam");
				 
				if(param.val()!=""){
				
					var arrs= param.val().split(',');
					for(var i=0;i<arrs.length;i++){
						if(arrs[i].indexOf(key+":")==0){
						 
							var parstr=param.val().replace(","+arrs[i],"").replace(arrs[i],"");
							param.val(parstr);
							return parstr;
						
						}
					}
				}
				return param.val();
			},
			//增加参数
			setParam:function (key,value){

				var param= $tab.find("#hid_sqlParam");
				
				if(param.val()==""){
					param.val(key+":"+value);
				}else{
					//如果存在替换
					if(param.val().indexOf(key+":")>=0){
						var arrs= param.val().split(',');
						for(var i=0;i<arrs.length;i++){
							if(arrs[i].indexOf(key+":")==0){
								arrs[i]=key+":"+value;
								break;
							}
						}
					 
						//var repstr=param.val().replace(eval("/"+key+":\\w*/ig\\"),key+":"+value);	 
					
						param.val(arrs.join(","));
					
						
					}else{
						param.val(param.val()+","+key+":"+value);
					}
				}
				
			},
			//获取列显示值
			getHeadCol:function (key){
				
				var param= $tab.find("#hid_headCols");
				
				if(param.val()!=""){
					var arrs= param.val().split(',');
					for(var i=0;i<arrs.length;i++){
						if(arrs[i].indexOf(key+":")>=0){
							return arrs[i].replace(key+":","");
						}
					}
				}
				 
				
			},
			//设置头显示列
			setHeadCol:function (key,value){
				
				var param= $tab.find("#hid_headCols");
				 
				//如果存在替换
				if(param.val().indexOf(key+":")>=0){
					var repstr=param.val().replace(eval("/"+key+":\\w*[\u4e00-\u9fa5]*/ig"),key+":"+value);
				
					param.val(repstr);
				} 
				 
				
			},
			hideCol:function (key){
				var param= $tab.find("#hid_headCols");
				//如果存在替换
				if(param.val().indexOf(key+":")>=0){
					var repstr=param.val().replace(eval("/"+key+":\\w*,?/ig"),"").replace(/,+$/,"");
					param.val(repstr);
				} 
				 
			},
			expDocs:function(){
				 
				this.getTabParam();
				$tab.wrap("<form method='post' target='_blank' action=\""+expURL+"\"></form>");
				$("form:first").submit();
				$tab.find("#hid_expDoc").val("");
				$("form:first").remove();
				 
	
			},
			//导出数据
			expDocsGet:function(){
				
				var params=this.getTabParam()+"&r="+Math.random();
				if(params&&params.length>1500){
					this.expDocs();
				}else{
					$tab.find("#hid_expDoc").val("");
					window.location.href=expURL+"?"+params;
				}
				
			},
			//获取选择项value
			getSelectVals:function (colname){
				
				var checkvals="";
				if(colname){
					
					$tab.find("tr:gt(0)").each(function (){
						 
						if($(this).find("input:checked").attr("checked")){
							var rowd=$tab.getRowData($(this));
							checkvals+=rowd[colname]+",";
						}
						
					});
					
				}else{
					
					$tab.find("tr:gt(0)").each(function (){
						 
						if($(this).find("input:checked").attr("checked")){
							
							checkvals+=$(this).attr($("#hid_colPK").val())+",";
						}
						
					});
				}
				
				if(checkvals.length>0&&checkvals.indexOf(",")>0){
					checkvals=checkvals.substring(0,checkvals.length-1);
				}
				
		
				return checkvals;
			},
			//获取选择项value
			getSelectValsStr:function (colname){
				
				var checkvals="";
				if(colname){
					
					$tab.find("tr:gt(0)").each(function (){
						 
						if($(this).find("input:checked").attr("checked")){
							var rowd=$tab.getRowData($(this));
							checkvals+="'"+rowd[colname]+"'"+",";
							 
						}
						
					});
					
				}else{
					$tab.find("tr:gt(0)").each(function (){
						 
						if($(this).find("input:checked").attr("checked")){
							
							checkvals+="'"+$(this).attr($("#hid_colPK").val())+"'"+",";
						}
						
					});
				}
				
				
				
				if(checkvals.length>0&&checkvals.indexOf(",")>0){
					checkvals=checkvals.substring(0,checkvals.length-1);
				}
				
		
				return checkvals;
				
			},
			//获取选择项value 返回 数组
			getSelectValsArr:function (colname){
				
				var checkvals=[];
				if(colname){
					
					$tab.find("tr:gt(0)").each(function (){
						 
						if($(this).find("input:checked").attr("checked")){
							var rowd=$tab.getRowData($(this));
							checkvals.push(rowd[colname]);
						}
						
					});
					
				}else{
					
					$tab.find("tr:gt(0)").each(function (){
						 
						if($(this).find("input:checked").attr("checked")){
							
							checkvals.push($(this).attr($("#hid_colPK").val()));
						}
						
					});
				}
			 
				return checkvals;
			},
			//获取选择行对象集合
			getSelectedRows:function (){
				var rows = [];
				$tab.find("tr:gt(0)").each(function (){
					 
					if($(this).find("input:checked").attr("checked")){
						//checkvals+=$(this).find(":hidden").val()+",";
						rows.push($tab.getRowData($(this)));
					}
					
				});
				if(rows.length==0){
					return;
				}else if(rows.length==1){
					return rows[0];
				}
				return rows;
				
			},
	        ///////////索湘云添加
			//获取选择项value
			getSelectValsum:function (colname){
				
				var checkvals=0;
				if(colname){
					
					$tab.find("tr:gt(0)").each(function (){
						if($(this).find("input:checked").attr("checked")){
							var rowd=$tab.getRowData($(this));
						    checkvals+=Math.round(rowd[colname] * Math.pow(10, 2)) / Math.pow(10, 2);
						}
						
					});
				}
				return checkvals;
			},
			
			//格式化列
			formatCol:function (colname,fromatfun){
				
				colname=colname.toLowerCase();
				var colindex=$tab.find("tr").find("[cname="+colname+"]").index();
				//格式化没找到或不存在
			    if(colindex<0){
				   return;
			    }
				
				$tab.find("tr").find("td:eq("+colindex+")").each(function (){
					
					var formattxt= fromatfun($(this).text(),$tab.getRowData($(this).parent()));
					 if(formattxt){
						 $(this).html(formattxt);
					 }
				});
				
				var isexists=false;
			 
				for ( var i = 0; i < $tab.fmtFuns.length; i++) {
					if($tab.fmtFuns[i].cname==colname){
						isexists=true;
						break;
					}
				}
			 
				if(!isexists){
					var var_obj={"cname":colname,"formatfun":fromatfun};
				 	$tab.fmtFuns.push(var_obj);
				}
			},
			initTabMask:function (){
				 
				//$tab.height($(".dynamic").outerHeight());
				//alert($(".dynamic").outerHeight());
				//异步加载显示提示
				$tab.append("<div class=\"table_mask\"   ><div class=\"mask-background\"   ></div><div class=\"mask-msg mask-loading\" style=\"display: block; margin-left: -48px; margin-top: -23px;\">Loading...</div></div>");
			},
			//显示加载遮罩
			tabMask:function (){
				//alert("xxx");
				$tab.height($(".dynamic").outerHeight());
				$tab.find(".table_mask").show();
				//alert($tab.find(".table_mask").html());
				
			},
			//取消显示加载遮罩
			tabUnMask:function (){
				$tab.find(".table_mask").hide();
			},
			setMaskHeight:function (){
				$tab.height($(".dynamic").outerHeight());
			}
	};

	for(var v in options){
		$tab[v]=options[v];
	}
	
	$tab.rows=[];
	//获取行数据
	$tab.getRowData=function (trobj){
		
		var $tr = trobj;
		var rowdata=[];
		/*for ( var i = 0; i < colarr.length; i++) {
			
			var tdval=$tr.find("td:eq("+colarr[i].index+")").html().replace(/(&nbsp;)/g,"");
			rowdata[i]="\""+colarr[i].value+"\":\""+tdval+"\"";	
		}
		
		var rowleng=rowdata.length;
		$tr.find("input:hidden").each(function (i){
			
			rowdata[rowleng+i]="\""+$(this).attr("cname")+"\":\""+$(this).val()+"\"";
		}); 
	 	//alert("{"+rowdata.join(",")+"}");*/
		 
		rowdata=$.parseJSON($tr.attr("rowdata"));
		
		return rowdata;
		
	};
	$tab.initPre=function (){
		
			var j=0;
			$tab.find("tr:first th").each(function (i){
				
				if($(this).attr("cname")){
					var obj= new Object();
					obj.index=i;
					obj.value=$(this).attr("cname");
					colarr.push(obj);
					//alert(colarr[j].index);
					j++;
				}
				
				
			});
			//初始化数据
			/*$tab.find("tr:gt(0)").each(function (i){
				
				$tab.rows.push($tab.getRowData($(this)));
				
			});*/
			//存在初始化
			if(j>0){
				isInitPre=true;
			}
		
	};
	
	$tab.init=function (){
		
			
			//分页事件
			$tab.find(".pagingdiv a").live("click",function (){
				if($(this).attr("value")!="0"){
					
					$tab.find("#hid_pageIndex").val($(this).attr("value"));
					//分页操作
					$tab.paingSearch();
				}
				
			});
		
			//全选事件
			$tab.find("#"+tabid+"_checkall").live("click",function (){
				 
				if($(this).attr("checked")){
					$tab.find("table input[type=checkbox]").attr("checked",true);
				}else{
					$tab.find("table input[type=checkbox]").attr("checked",false);
				}
				
			});
			
			//查找有查询的button
			$("["+param_for+"="+tabid+"]["+param_type+"="+type_search+"]").live("click",function (){
		 
				$tab.beforeSearch();
				$tab.refreshTab();
				
			});
			
			//刷新
			$tab.find("#pagereload").live("click",function (){
				$tab.refreshTab();
				
			});
			//查找有导出的button
			$tab.find("["+param_type+"="+type_export+"]").live("click",function (){
				
			  // var params= $tab.find("#hid_sqlParam").val();
			 
			   $tab.find("#hid_expDoc").val($(this).attr("val"));
			   
			 //  $tab.expDocs();
			   $tab.expDocsGet();
				
			});
			
			//排序
			$tab.find("th").live("click",function (){
				
				var $thspan=$(this).find("span");
				if($thspan.attr("val")){

					var sortcol=$tab.find("#hid_sortCol").val();
					var sortval=$tab.find("#hid_sortVal").val();
					// alert(sortcol+" "+$(this).attr("val"));
					if($thspan.attr("val")==sortcol){
						 if(sortval=="asc"){
							 $tab.find("#hid_sortVal").val("desc");
							
						 }else{
							 $tab.find("#hid_sortVal").val("asc");
						 }
					 
					}else{
						 $tab.find("#hid_sortCol").val($thspan.attr("val"));
						
						 if(sortval=="asc"){
			
							 $tab.find("#hid_sortVal").val("desc");
						 }else if(sortval=="desc"){
		
							 $tab.find("#hid_sortVal").val("asc");
						 }else{
	
							 $tab.find("#hid_sortVal").val("desc");
						 }
					}
			 
					$tab.paingSearch();
				}
				
			}); 
			
			//row link  click
			$tab.find(".actionlink").live("click",function (){
				
				var $tr = $(this).parent().parent();
			
				var rowdata=$tab.getRowData($tr);
				
				var clickval=$(this).attr("val");
				 
				$tab.clickRow(clickval,rowdata);
				
			});
			
			//回车事件
			$tab.find("#txtpagesize").live("keypress",function (e){
				
	                var key = window.event ? e.keyCode : e.which;
	                if (key.toString() == "13") {
	                	$tab.refreshTab();
	                }
			});
			
			isInit=true;
			
			//初始化遮罩
			$tab.initTabMask();
			
	};
	$tab.initPre();
	//初始化
	$tab.init();
	
 
	//eval("myTables."+tabid+"=$tab");
	//加载图片
	
	return $tab;
};

 
$(function (){

	$(".maindiv").each(function (){
//		alert($(this).attr("id"));
		myTables[$(this).attr("id")]=bindTable($(this).attr("id"));
		
	});


}); 

 

