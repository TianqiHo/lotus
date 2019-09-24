;
var Common = {
		Fn:{
			strToBinary : function (str){
			    var result = [];
			    var list = str.split('');
			    for(var i=0;i<list.length;i++){
			        if(i != 0){
			            result.push(' ');
			        }
			        var item = list[i];
			        var binaryStr = item.charCodeAt();
			        result.push(binaryStr);
			    }  
			    return result;
			},
			
			obtainVariableFromRequestUrl:function(variable){
			       var query = window.location.search.substring(1);
			       var vars = query.split("&");
			       for (var i=0;i<vars.length;i++) {
			               var pair = vars[i].split("=");
			               if(pair[0] == variable){return pair[1];}
			       }
			       return(false);
			},
			uploadFiles:function(files,uploadPath,validFn,callbackFn){
				 var upload = function(){
					   var formFile = new FormData();  
		               formFile.append('uploadNewsImg', files);
		               formFile.append('uploadPath',uploadPath);
		 
		               $.ajax({
		                   url: Config.requestContextPath+'/file/uploadFiles',
		                   data: formFile,
		                   type: 'POST',
		                   //dataType: 'json',
		                   cache: false,//上传文件无需缓存
		                   processData: false,//用于对data参数进行序列化处理 这里必须false
		                   contentType: false, //必须
		                   success: function (result) {
		                       if(callbackFn && typeof callbackFn == 'function'){
		                    	   callbackFn(result);
		                       }else{
		                    	   Common.Fn.alert('callbackFn无效');
		                       }
		                       
		                   },
		               })
				   }
				   if(validFn && typeof validFn=='function'){
					   if(validFn()){
						   upload();
					   }
					}else if(validFn && typeof validFn=='boolean'){
						upload();
					}else{
						 Common.Fn.alert('函数调用不合法');
						 return false;
					}
	               
			},
			confirm:function(content,okFn,cancelFn){
                     $.confirm({
                         title: '提示',
                         content: content,
                         autoClose: 'cancelAction|9000',
                         escapeKey: 'cancelAction',
                         buttons: {
                             confirm: {
                                 btnClass: 'btn-red',
                                 text: '确定删除',
                                 action: function(){
                                	 if(okFn && typeof okFn==='function'){
                                		 okFn();
                                	 }
                                 }
                             },
                             cancelAction: {
                                 text: '取消',
                                 action: function(){
									 if(cancelFn && typeof cancelFn==='function'){
										 cancelFn();        		 
									  }
                                    
                                 }
                             }
                         }
                     });

			},
			alert:function(content,okFn){
				$.alert({
                    title: '提示',
                    content: '<div class="alert alert-default" role="alert" style="text-align:center"><h3>'+content+'</h3></div>',//'<p style="text-align:center">'+content+'</p>',
                    icon: 'fa fa-rocket',
                    animation: 'scale',
                    closeAnimation: 'scale',
                    buttons: {
                        okay: {
                            text: '确定 ',
                            btnClass: 'btn-blue',
                            action: function(){
                               if(okFn && typeof okFn=='function')okFn();
                            }
                        }
                    }
                });
			},
			activeNavbar:function(){
				
				
				/*$('#navbars > .nav-item').click(function(){
					
					$('#navbars > .active').each(function(z,act){
						
						if($(act).hasClass('dropdown-menu')){
							$(act).attr({class:'nav-item dropdown'});
						}else{
							$(act).attr({class:'nav-item'});
						}
						
					});
					
					
						if($(this).hasClass('dropdown-menu')){
							$(this).attr({class:'nav-item dropdown active'});
						}else{
							$(this).attr({class:'nav-item active'});
						}
					
				});*/
				
			}
		}
};

(function($) {
	    $.fn.serializeJson = function() {
	        var serializeObj = {};
	        var array = this.serializeArray();
	        var str = this.serialize();
	        $(array).each(function() {
	            if(serializeObj[this.name]) {
	                if($.isArray(serializeObj[this.name])) {
	                    serializeObj[this.name].push(this.value);
	                }else{
	                    serializeObj[this.name] = [serializeObj[this.name], this.value];
	                }
	            }else{
	                serializeObj[this.name] = this.value;
	            }
	        });
	        return serializeObj;
	    };
})(jQuery);

$(function(){
	//Common.Fn.activeNavbar();
});