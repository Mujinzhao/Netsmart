//浏览器兼容性
try { document.execCommand("BackgroundImageCache", false, true); } catch (e) { }
$(document).ready(function() {
    if ($.browser.msie) {
        var v = parseInt($.browser.version);
        $(document.body).addClass("ie ie" + v);
    }
    
    //event 兼容
    var Sys = {};
    var ua = navigator.userAgent.toLowerCase();
    var s;  
    (s = ua.match(/msie ([\d.]+)/)) ? Sys.ie = s[1] :  
    (s = ua.match(/firefox\/([\d.]+)/)) ? Sys.firefox = s[1] :  
    (s = ua.match(/chrome\/([\d.]+)/)) ? Sys.chrome = s[1] :  
    (s = ua.match(/opera.([\d.]+)/)) ? Sys.opera = s[1] :  
    (s = ua.match(/version\/([\d.]+).*safari/)) ? Sys.safari = s[1] : 0;      
    
//    if (window.ActiveXObject)
//        Sys.ie = ua.match(/msie ([\d.]+)/)[1]
//    else if (document.getBoxObjectFor)
//        Sys.firefox = ua.match(/firefox\/([\d.]+)/)[1]
//    else if (window.MessageEvent && !document.getBoxObjectFor)
//        Sys.chrome = ua.match(/chrome\/([\d.]+)/)[1]
//    else if (window.opera)
//        Sys.opera = ua.match(/opera.([\d.]+)/)[1]
//    else if (window.openDatabase)
//        Sys.safari = ua.match(/version\/([\d.]+)/)[1];
	
});

var operating = 0;   //默认不在操作中

//点击左边的 Tree ，右边显示 Panel
function addTab(title, href){
	if( operating == 0 ){
	   	var tt = $('#tt');
	   	if (tt.tabs('exists', title)){
	    	tt.tabs('select', title);
	    	refreshTab({tabTitle:title,url:href});
	   	} else {
	    	if (href){
		    	var content = '<iframe scrolling="auto" frameborder="0"  src="'+href+'" style="margin:0px;overflow:scroll;overflow-x:hidden;overflow-y:auto;width:100%;height:100%;"></iframe>';
	    	} else {
		    	var content = '未实现';
	    	}
	    	tt.tabs('add',{
		    	title:title,
		    	closable:true,
		    	content:content
	    	});
	   	}
   	}
}

//关闭 Tab （根据 title 标识）
function killTab(title){
	var tt = $('#main-center');
	
	if (tt.tabs('exists', title)){
		tt.tabs('close',title);
	}
}

/**     
 * 刷新tab 
 * @cfg  
 *example: {tabTitle:'tabTitle',url:'refreshUrl'} 
 *如果tabTitle为空，则默认刷新当前选中的tab 
 *如果url为空，则默认以原来的url进行reload 
 */  
function refreshTab(cfg){  
    var refresh_tab = cfg.tabTitle?$('#main-center').tabs('getTab',cfg.tabTitle):$('#main-center').tabs('getSelected');  
    if(refresh_tab && refresh_tab.find('iframe').length > 0){
   		var _refresh_ifram = refresh_tab.find('iframe')[0];
    	var refresh_url = cfg.url?cfg.url:_refresh_ifram.src;
    	//_refresh_ifram.src = refresh_url;  
    	_refresh_ifram.contentWindow.location.href=refresh_url;
    }  
}

//Map实现
function Map() {
 var struct = function(key, value) {
  this.key = key;      
  this.value = value;      
 }      
       
 var put = function(key, value){      
  for (var i = 0; i < this.arr.length; i++) {      
   if ( this.arr[i].key === key ) {      
    this.arr[i].value = value;      
    return;      
   }      
  }      
   this.arr[this.arr.length] = new struct(key, value);      
 }      
       
 var get = function(key) {      
  for (var i = 0; i < this.arr.length; i++) {      
   if ( this.arr[i].key === key ) {      
     return this.arr[i].value;      
   }      
  }      
  return null;      
 }      
       
 var remove = function(key) {      
  var v;      
  for (var i = 0; i < this.arr.length; i++) {      
   v = this.arr.pop();      
   if ( v.key === key ) {      
    continue;      
   }      
   this.arr.unshift(v);      
  }      
 }      
       
 var size = function() {      
  return this.arr.length;      
 }      
       
 var isEmpty = function() {      
  return this.arr.length <= 0;      
 }      
     
 this.arr = new Array();      
 this.get = get;      
 this.put = put;      
 this.remove = remove;      
 this.size = size;      
 this.isEmpty = isEmpty;      
}


/**
 * json对象转字符串形式
 */
 function json2str(o) {
 	var arr = [];
 	var fmt = function(s) {
 	if (typeof s == 'object' && s != null) return json2str(s);
 		return /^(string|number)$/.test(typeof s) ? "'" + s + "'" : s;
 	}
	for (var i in o) arr.push("'" + i + "':" + fmt(o[i]));
		return '{' + arr.join(',') + '}';

}

/* event 兼容调用 IE ，FF，Chrome*/
function loadEvent() {  
 if (!document.all) {  
  window.constructor.prototype  
    ._defineGetter_(
      "event",  
      function() {  
       var func = arguments.callee.caller;  
       while (func != null) {  
        var arg0 = func.arguments[0];  
        if (arg0  
          && (arg0.constructor == Event || arg0.constructor == MouseEvent)) {  
         return arg0;  
        }  
        func = func.caller;  
       }  
       return null;  
      });  
 }  
}

//用enter 键填充信息
function fillCusInfo(_inputMark,e){
	var key = window.event ? e.keyCode : e.which;

	if( key == 13 ){
		enterCustomerDetail(_inputMark);
	}
}

//日期格式化
function formatDate(date){
	var month = date.getMonth()+1;
	if( "" != date ){
		if( date.getMonth() +1 < 10 ){
			month = '0' + (date.getMonth() +1);
		}
		var day = date.getDate();
		if( date.getDate() < 10 ){
			day = '0' + date.getDate();
		}
		return date.getFullYear()+'-'+month+'-'+day;
	}else{
		return "";
	}
}

$.extend($.fn.datagrid.defaults.editors, {
    readOnlyText: {
        init: function(container, options){
            var input = $('<input type="text" disabled class="datagrid-editable-input">').appendTo(container);
            return input;
        },
        getValue: function(target){
            return $(target).val();
        },
        setValue: function(target, value){
            $(target).val(value);
        },
        resize: function(target, width){
            var input = $(target);
            if ($.boxModel == true){
                input.width(width - (input.outerWidth() - input.width()));
            } else {
                input.width(width);
            }
        }
    }
});

$.extend($.fn.datagrid.defaults.editors, {
    floatText: {
        init: function(container, options){
            var input = $('<input type="text" onblur="floatCheck(this)" class="datagrid-editable-input">').appendTo(container);
            return input;
        },
        getValue: function(target){
            return $(target).val();
        },
        setValue: function(target, value){
            $(target).val(value);
        },
        resize: function(target, width){
            var input = $(target);
            if ($.boxModel == true){
                input.width(width - (input.outerWidth() - input.width()));
            } else {
                input.width(width);
            }
        }
    }
});

function floatCheck (target) {
	var text = target.value;
    var regex = /^(-?\d+)(\.\d+)?$/;
    if (!regex.test(text)){
    	$.messager.alert('输入错误','您输入的信息有误，请输入数字类型!','warning',function(){
    		$(target).val("0");
    		$(target).focus();
    	});
        return false;
    }
    return true;
}

$.extend($.fn.datagrid.defaults.editors, {
    positiveText: {
        init: function(container, options){
            var input = $('<input type="text" onblur="numberCheck(this)" class="datagrid-editable-input">').appendTo(container);
            return input;
        },
        getValue: function(target){
            return $(target).val();
        },
        setValue: function(target, value){
            $(target).val(value);
        },
        resize: function(target, width){
            var input = $(target);
            if ($.boxModel == true){
                input.width(width - (input.outerWidth() - input.width()));
            } else {
                input.width(width);
            }
        }
    }
});

function numberCheck (target) {
	var text = target.value;
    var regex = /^\d+(\.\d+)?$/;    //^((-\d+(\.\d+)?)|(0+(\.0+)?))$              负浮点 
    if (!regex.test(text)){
    	$.messager.alert('输入错误','您输入的信息有误，请输入正数!','warning',function(){
    		$(target).val("0");
    		$(target).focus();
    	});
        return false;
    }
    return true;
}

function fixWidth(percent){
	return document.body.clientWidth * percent ; //这里可以自己调整
}

//实现上一条，下一条 功能， 并设置其可用性
function showOrderByPreNext(tableName,type){
	var tblName = '#' + tableName ;
	
	var selectRow =$(tblName).datagrid("getSelected");
	var index = $(tblName).datagrid("getRowIndex",selectRow);
	var totalRows = $(tblName).datagrid('getRows').length;
	
	if( 'next' == type ){
		if( index == totalRows - 2 ){
			$('#btnNext').linkbutton('disable');
			$('#btnNext').attr('disabled',true);
		}
	}else if( 'previous' == type ){
		if( index == 1 ){
			$('#btnPrevious').linkbutton('disable');
			$('#btnPrevious').attr('disabled',true);
		}
	}
	
	$(tblName).datagrid('unselectRow',index);
	if( 'next' == type ){
		$(tblName).datagrid('selectRow',parseInt(index)+1);  //指定行选中
		
		$('#btnPrevious').linkbutton('enable');
		$('#btnPrevious').attr('disabled',false);
	}else{
		$(tblName).datagrid('selectRow',parseInt(index)-1);
		
		$('#btnNext').linkbutton('enable');
		$('#btnNext').attr('disabled',false);
	}
	var currentRow =$(tblName).datagrid("getSelected");
	
	return currentRow;
	
}

function loadSaleBaseCondition(){
	$("#beginDate").datebox({formatter:formatDate});
	$("#endDate").datebox({formatter:formatDate});	
	$('#beginDate').datebox('disable');
	$("#endDate").datebox('disable');	
	
	var $make = $("#ck_makeDate");  
	var $orderId = $("#ck_orderId");
	var $cusNo = $("#ck_cusNo");	
	
	//判断 checkbox 是否选择，如果选择，则相应的控件设置为可用
	$make.click(function(){  
		if($make.is(":checked")){       //采用 jQuery 的方式来判断  			
			$('#beginDate').datebox('enable');
			$('#endDate').datebox('enable');
		}else{
			$('#beginDate').datebox('disable');
			$("#endDate").datebox('disable');
		}
	});

	$orderId.click(function(){
		if( $orderId.is(":checked")){
			$("#s-orderId").attr('disabled',false);
		}else{
			$("#s-orderId").attr('disabled',true);
		}
	});
	
	$cusNo.click(function(){
		if( $cusNo.is(":checked") ){
			$("#s-cusNo").attr('disabled',false);
			$("#img_cus").removeClass('hidden');
			
		}else{
			$("#s-cusNo").attr('disabled',true);
			$("#img_cus").addClass('hidden');
		}
	});
}

//验证当前输入的数量大于 0
$.extend($.fn.validatebox.defaults.rules, {
    compareValues: {
        validator: function (value, param) {
        	return /^[1-9][0-9]*$/.test(value);
        },
        
        message:' 请输入大于 0 的数！ '

    }

});

function loadingDate(){
	$("<div class=\"datagrid-mask\"></div>").css({display:"block",width:"100%",height:$(window).height()}).appendTo("body");
	$("<div class=\"datagrid-mask-msg\"></div>").html("正在处理，请稍候。。。").appendTo("body").css({display:"block",left:($(document.body).outerWidth(true) - 190) / 2,top:($(window).height() - 45) / 2});

}

function loaded(){
	$(document.body).find("div.datagrid-mask-msg").remove();
	$(document.body).find("div.datagrid-mask").remove(); 
}