;

Home = {
		Attribute:{},
		
		Init:function(){
			//
			Home.Fn.obtainSystemMenu(1);
		},
		
		Fn:{
			obtainUserInfo:function(){
				
			},
			
			obtainSystemMenu:function(drawingType){
				$.ajax({
					type:'POST',
					url:Config.requestContextPath+'/systemMenu/selectSystemMenus',
					//data:JSON.stringify(data),
					dataType:'JSON',
					contentType:'application/json',
					success:function(result,status,xhr){
						if(result.success){
							Common.Fn.alert('加载成功',function(){
								console.info(result.data);
								
								if(drawingType === 1){
									for(var i = 0; i<result.data.length; i++){
										var systemMenu = result.data[i];
										Home.Fn.drawingParentSystemMenu(systemMenu.name,systemMenu.childSystemMenus);
									}
									
								}else if(drawingType === 2){
									Home.Fn.drawingChildSystemMenu(result.data);
								}

							});
							
						}else{
							Common.Fn.alert(result.alertMessage);
						}
					},
					error:function(xhr,status,error){
						 if(xhr && xhr.responseJSON && xhr.responseJSON.message){
			            	 Common.Fn.alert(xhr.responseJSON.message);
			             }else{
			            	 Common.Fn.alert('系统异常');
			             }
					}
				});
			},
			
			drawingParentSystemMenu:function(parentMenuName,parentMenuId){
				var html = '<li class="treeview"><a href="javascript:void(0)" onclick="Home.Fn.drawingChildSystemMenu('+parentMenuId+')" id='+parentMenuId+'><i class="fa fa-link"></i><span>'+parentMenuName+'</span><span class="pull-right-container"><i class="fa fa-angle-left pull-right"></i></span></a></li>';
				$('#systemMenu').append(html);
			},
			
			drawingChildSystemMenu:function(childSystemMenus){
				var html = '<ul class="treeview-menu">';
				for(var i = 0;i<childSystemMenus.length;i++){
					var childSystemMenu = childSystemMenus[i];
					html += '<li><a href="'+childSystemMenu.resourceUrl+'">'+childSystemMenu.name+'</a></li>';
				}
				html += '</ul>';
			}
			
		}
};