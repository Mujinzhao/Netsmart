$(function(){
	bindTabEvent();
    bindTabMenuEvent();
});

//绑定tab的双击事件、右键事件  
function bindTabEvent(){  
	  /*为选项卡绑定右键*/
	  $(".tabs li").live('contextmenu',function(e){
	  /* 选中当前触发事件的选项卡 */
	  var subtitle =$(this).text();
	  $('#tabs').tabs('select',subtitle);
	  //显示快捷菜单
	  $('#tab_menu').menu('show', {
	          left: e.pageX,
	          top: e.pageY
	      });
	      return false;
	  });
 }  
/************ 实现方法****************/
//绑定tab右键菜单事件  
function bindTabMenuEvent() {  
     //刷新
	 $('#tab_menu-tabrefresh').click(function () {
		  var url = $(".tabs-panels .panel").eq($('.tabs-selected').index()).find("iframe").attr("src"); 
		  $(".tabs-panels .panel").eq($('.tabs-selected').index()).find("iframe").attr("src", url); 
	 });  
	 // 新窗口打开
	 $('#tab_menu-openFrame').click(function () { 
		  var url = $(".tabs-panels .panel").eq($('.tabs-selected').index()).find("iframe").attr("src");  
		  window.open(url);  
	 });  
    //关闭当前  
	 $('#tab_menu-tabclose').click(function () { 
		var currtab_title = $('.tabs-selected .tabs-inner span').text();    
        $('#tabs').tabs('close', currtab_title);  
    });  
    //全部关闭  
	 $('#tab_menu-tabcloseall').click(function () {  
        $('.tabs-inner span').each(function(i, n) {  
            if ($(this).parent().next().is('.tabs-close')) {  
                var t = $(n).text();  
                $('#tabs').tabs('close', t);  
            }  
        });  
    });  
    //关闭除当前之外的TAB  
	 $('#tab_menu-tabcloseother').click(function () {  
		var currtab_title = $('.tabs-selected .tabs-inner span').text();    
		$('.tabs-inner span').each(function (i, n) {  
			 if ($(this).parent().next().is('.tabs-close')) {  
                var t = $(n).text();
                if (t != currtab_title)  
                    $('#tabs').tabs('close', t);  
            }  
        });  
    });  
    //关闭当前右侧的TAB  
	 $('#tab_menu-tabcloseright').click(function () {   
        var nextall = $('.tabs-selected').nextAll();  
        if (nextall.length == 0) {  
            alert('已经是最后一个了');  
            return false;  
        }  
        nextall.each(function(i, n) {  
            if ($('a.tabs-close', $(n)).length > 0) {  
                var t = $('a:eq(0) span', $(n)).text();  
                $('#tabs').tabs('close', t);  
            }  
        });  
        return false;  
    });  
    //关闭当前左侧的TAB  
	 $('#tab_menu-tabcloseleft').click(function () {  
        var prevall = $('.tabs-selected').prevAll();  
        if (prevall.length == 1) {  
            alert('已经是第一个了');  
            return false;  
        }  
        prevall.each(function(i, n) {  
            if ($('a.tabs-close', $(n)).length > 0) {  
                var t = $('a:eq(0) span', $(n)).text();  
                $('#tabs').tabs('close', t);  
            }  
        });  
        return false;  
    });  
} 

/*进度条  
function progress() {  
  var win = $.messager.progress({  
       title: '请稍等',  
       msg: '正在加载数据...'  
   });  
   setTimeout(function () {  
       $.messager.progress('close');  
   }, 3000); 
}*/