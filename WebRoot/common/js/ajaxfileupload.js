/**
 * 上传控件样式指定
 * @param id
 * @param url
 * @param ansys
 */
function  fileuploadcss(id,url,buttondesc,autoflag,filetype,filetypedsc,parsfiled){
		$('#'+id).uploadify({
	        uploader: url,// 服务器端处理文件存储地址
	        swf: 'uploadify/uploadify.swf',    // 上传使用的 Flash
	        width: 400,                          // 按钮的宽度
	        height: 16,                         // 按钮的高度
	        buttonText: buttondesc,                 // 按钮上的文字
	        fileObjName: id,
	        // 两个配套使用
	        fileTypeExts: filetype,             // 扩展名
	        fileTypeDesc: "请选择"+filetypedsc+"文件",     // 文件说明
	        auto: autoflag,                // 选择之后，自动开始上传
	        multi: false,               // 是否支持同时上传多个文件
	        method:'post',
	        removeCompleted : true,   
	        removeCompleted : true,   
	        fileSizeLimit: 0,//单个文件大小，0为无限制，可接受KB,MB,GB等单位
	        onUploadSuccess: function (file, data, response){
//	        	alert(file);
//	        	alert(data);
	        	//parsfiled.val(data);
	        	//Validform_checktip Validform_wrong parsefile
	        	//Validform_checktip Validform_right
	        	// $(".parsefile").removeClass("Validform_checktip Validform_wrong").addClass("Validform_checktip Validform_right");//checking;
	        	 $(".uploadify-button-text").html(data);
	        },
			onUploadError:function (file, errorCode, errorMsg, errorString){
	            alert(errorCode);
				alert(errorString);
			}
	});
}
function templateuploadcss(id,url,buttondesc,autoflag,filetype,filetypedsc,parsfiled){
       $('#'+id).uploadify({
	        uploader: url,// 服务器端处理文件存储地址
	        swf: 'uploadify/uploadify.swf',    // 上传使用的 Flash
	        width: 400,                          // 按钮的宽度
	        height: 16,                         // 按钮的高度
	        buttonText: buttondesc,                 // 按钮上的文字
	        fileObjName: id,
	        // 两个配套使用
	        fileTypeExts: filetype,             // 扩展名
	        fileTypeDesc: "请选择"+filetypedsc+"文件",     // 文件说明
	        auto: autoflag,                // 选择之后，自动开始上传
	        multi: false,               // 是否支持同时上传多个文件
	        method:'post',
	        queueSizeLimit: 1,// 允许多文件上传的时候，同时上传文件的个数
	        removeTimeout : 1,//成功后移除 处理框  
	        removeCompleted : true,   
	        removeCompleted : true,   
	       // fileSizeLimit: 0,//单个文件大小，0为无限制，可接受KB,MB,GB等单位
	        fileSizeLimit:1000000*1024,   // 设置上传文件大小
	        onUploadSuccess: function (file, data, response){
	        	// 上传成功后 将文件信息存入数据中
	        	saveFileInfo(file);
	        },
//            onCancel: function(file){
//            	alert('The file'+ file.name + 'was cancelled.');
//	        },
//	        //文件被移除出队列时触发,返回file参数
//	        onClearQueue: function(queueItemCount){
//	        	alert(queueItemCount+'file(s) were removed frome the queue');
//	        },
			onUploadError:function (file, errorCode, errorMsg, errorString){
	            alert(errorCode);
				alert(errorString);
			}
     });
}
