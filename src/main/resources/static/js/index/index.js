;var Index = {
		
		Attributes:{},
		Fn:{			
			login : function(){
				var user = $('#loginForm').serializeJson();
				if(!user.userName){
					Common.Fn.alert('用户名为空');
					return false;
				}
				if(!user.passWord){
					Common.Fn.alert('密码为空');
					return false;
				}
				
				
				$.ajax({
					type:'POST',
					url:Config.requestContextPath+'/user/login',
					data:JSON.stringify(user),
					dataType:'JSON',
					contentType:'application/json',
					success:function(result,status,xhr){
						if(result.success){
							document.cookie = 'LOTUS=' + result.data +";path=/";
							window.location.href = 'pages/home/home.html';
						}else{
							 Common.Fn.alert(result.alertMessage+'['+xhr.responseJSON.realMessage+']');
						}
						
					},
					error:function(xhr,status,error){
						 if(xhr && xhr.responseJSON && xhr.responseJSON.message){
			            	 Common.Fn.alert(xhr.responseJSON.message+'['+xhr.responseJSON.realMessage+']');
			             }else{
			            	 Common.Fn.alert('系统异常');
			             }
					}
				});
			}
	
		}
};

$(function(){

});