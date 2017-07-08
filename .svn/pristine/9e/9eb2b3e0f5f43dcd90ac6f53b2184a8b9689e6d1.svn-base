$(function(){
	   //采集类型标识
	    var gatherflag = $("#pageflag").val();
	    // 列表采集
	    if(gatherflag == "0"){
//	          $("#listpage").attr("title","列表采集");
	        //  $("#pageconfig").attr("src","gatherpage.action?"+$("#hidparam").find("*").serialize());
	         // $("#pageconfig").attr("src","gather/groupconfig.jsp");
	    }
	    //模拟采集
	    if(gatherflag == "1"){
//	    	   $("#listpage").attr("title","组信息添加");
	       //  $("#pageconfig").attr("src","gather/eventconfig.jsp");
	    }
	    //元搜索采集
	     if(gatherflag == "2"){
//	    	 $("#listpage").attr("title","元搜索采集");
	        // $("#pageconfig").attr("src","yuansearch.jsp");
	    }
	     $("#pageconfig").attr("src","gatherpage.action?"+$(':input,:checkbox,:radio','#hidparam').serialize());
});