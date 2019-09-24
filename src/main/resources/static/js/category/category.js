;var Category = {
		Attr:{
			categoryModalID:'#categoryModal',
			formID:'#categoryForm',
			tableID:'#categoryTable',
			editType:null,
			searchFormID:'#searchForm',
			categoryType:null,
			moduleName:null
		},
		Fn:{
			init:function(){
				Category.Attr.categoryType = Common.Fn.obtainVariableFromRequestUrl('categoryType');
				Category.Attr.moduleName = decodeURI(Common.Fn.obtainVariableFromRequestUrl('moduleName'));
				
				Category.Fn.loadTable(Category.Attr.categoryType);
				Category.Fn.ModalClass.Fn.init();
				Category.Fn.FormClass.Fn.init();
				
				
			    $('.categoryType').each(function(i,each){
			    	$(this).after('<span class="label label-warning">提醒：'+Category.Attr.categoryType+'</span>');
			    	$(this).val(Category.Attr.categoryType);
			    });
			    
			    $('#title').append(Category.Attr.moduleName);
			},
			loadTable:function(){
				//先销毁表格
				$(Category.Attr.tableID).bootstrapTable("destroy");
				$(Category.Attr.tableID).bootstrapTable({
					         height: 500,
					         theadClasses: 'thead-blue',//这里设置表头样式
					         classes: 'table table-bordered table-striped table-lg',
					         escape:false, //转义html标签
					         //searchAlign: 'right',
					         //search: true,   //显示搜索框
					         //strictSearch:false,
					         showHeader: true,     //是否显示列头
					         url: Config.requestContextPath+'/category/selectCategorys',
					         method: 'post',                      //请求方式（*）
					         //showFullscreen:true, //显示全屏按钮
					         showRefresh: true,                  //是否显示刷新按钮
					         showColumns: true,                  //显示所有的列控制
					         showExport:true,
					         minimumCountColumns:10,//当列的数量大于等于3时 才显示列选择按钮
					        // buttonsAlign: "left",//按钮对齐方式
					         toolbar: '#toolbar',//指定工具栏
					         toolbarAlign: 'left',//工具栏对齐方式
					         locale: 'zh-CN', //中文支持
					         showLoading: true,
					        // striped: true,                      //是否显示行间隔色
					         pagination: true,                   //是否显示分页（*）
					         pageNumber: 1,                       //初始化加载第一页，默认第一页
					         pageSize: 15,                       //每页的记录行数（*）
					         pageList: '[2,5,15,20]',        //可供选择的每页的行数
					         sidePagination: 'server',           //分页方式：client客户端分页，server服务端分页（*
					         showPaginationSwitch:true,
					         paginationHAlign: 'left',
					         clickToSelect: true,                //是否启用点击选中行
					         //maintainSelected :true, //3,开启分页保持选择状态，就是用户点击下一页再次返回上一页
					         //uniqueId: 'id',                     //每一行的唯一标识，一般为主键列
					        // paginationLoop: false,             //是否无限循环
					         totalField:'total',
					         dataField:'list',
					         columns: [{
					             checkbox: true
					         },
					         {
					                 field: 'id',
					                 align:'center',
					                 title: '序号',
					                 formatter:function(value , row , index){
					                	 return index+1;
					                 }
					         },
					         {
					                 field: 'categoryName',
					                 align:'center',
					                 title: '类型名称'
					         },
					         {
				                 field: 'categoryType',
				                 align:'center',
				                 title: '业务类型'
				             }, 
				             {
				                 field: 'categoryImg',
				                 title: '图片',
				                 align:'center',
				                 formatter:function(value , row , index){
				                	 var html =  '';
				                	 if(value){
				                		 var urls = value.split(',');
					                	 for(var i=0;i<urls.length;i++){
					                		 html += "<img class='img-responsive img-thumbnail' height=100px width=90px src ="+urls[i]+"/>";
					                	 }
				                	 }
				                	
						        	 return html;
						         },
				            }, 
					         {
					        	 field:'createTime',
					        	 title:'创建时间',
					        	 align:'center',
					        	 
					         },{
					        	 field:'updateTime',
					        	 title:'更新时间',
					        	 align:'center',
					        	 
					         },
					         {
				                 field: 'valid',
				                 title: '是否有效',
				                 align:'center',
				                 formatter:function(value , row , index){
						        	 return value===1?'有效':'无效';
						         }
					         }],
					         responseHandler:function(result){
					        	 if(result.success){
					        		 
										return result.data;
									}else{
										alert(result.alertMessage);
									}
					         },
					        
					         queryParams:function(params) {
					             params.pageNumber = params.offset+1;
					             params.pageSize = params.limit;
					             params.categoryType = Category.Attr.categoryType
					            return params;
					         },
					         onLoadError: function (status, jqXHR) {
					             if(jqXHR && jqXHR.responseJSON && jqXHR.responseJSON.message){
					            	 Common.Fn.alert(jqXHR.responseJSON.message);
					             }else{
					            	 Common.Fn.alert('系统异常');
					             }
					          },
					          onLoadSuccess: function (data) {  
					           }
					     });
			},
			FormClass:{
				Fn:{
					init:function(){
						
						$(document).off('click','#search').on('click','#search',function(){
						    var $btn = $(this).button('loading');
						    
						    var data = $(Category.Attr.searchFormID).serializeJson();
						    if(data.beginTime)data.beginTime = data.beginTime+' 00:00:00';
						    if(data.endTime)data.endTime = data.endTime+' 23:59:59';
						    $.ajax({
								type:'POST',
								url:Config.requestContextPath+'/category/selectCategorys',
								data:JSON.stringify(data),
								timeout:2000,
								dataType:'JSON',
								contentType:'application/json',
								success:function(result,status,xhr){
									if(result.success){
										Common.Fn.alert('加载成功',function(){
											 $(Category.Attr.tableID).bootstrapTable('load',result.data);
											 
											 $(Category.Attr.searchFormID)[0].reset();
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
						    
						    $btn.button('reset');
						  });
						
						$(Category.Attr.formID).bootstrapValidator({
						      // 默认的提示消息
						      message: '输入值无效',
						      // 表单框里右侧的icon
						      feedbackIcons: {
					        　　　　　　　　valid: 'glyphicon glyphicon-ok',
					        　　　　　　　　invalid: 'glyphicon glyphicon-remove',
					        　　　　　　　　validating: 'glyphicon glyphicon-refresh'
						      },
						      submitHandler: function (validator, form, submitButton) {
						        // 表单提交成功时会调用此方法
						        // validator: 表单验证实例对象
						        // form  jq对象  指定表单对象
						        // submitButton  jq对象  指定提交按钮的对象
						    	  alert('-----');
						      },
						      fields: {
						    	 categoryName: {
						          message: '类型名称验证失败',
						          validators: {
							            notEmpty: {
							              message: '类型名称不能为空'
							            },
							            stringLength: {  //长度限制
							              min: 1,
							              max: 18,
							              message: '长度必须在1到18位之间'
							            }							           
							          }
						        },
						        categoryType: {
						          validators: {
						            notEmpty: {
						              message: '业务类型不能为空'
						            }
						          }
						        }
						      }
						    });
						/*****************************************************************************/
					},
					
					saveOrUpdateCategory:function(){
						
						//if( $('#categoryImg').val()){
							$(Category.Attr.formID).data("bootstrapValidator").validate();
							
							if($(Category.Attr.formID).data('bootstrapValidator').isValid()){
								
								var data = $(Category.Attr.formID).serializeJson();
								$.ajax({
									type:'POST',
									url:Config.requestContextPath+'/category/saveOrUpdateCategory',
									data:JSON.stringify(data),
									timeout:2000,
									dataType:'JSON',
									contentType:'application/json',
									success:function(result,status,xhr){
										if(result.success){
											Common.Fn.alert(result.alertMessage,function(){
												$(Category.Attr.formID).data('bootstrapValidator').resetForm(true);
												$(Category.Attr.categoryModalID).modal('hide');
												//重新加载数据
												$(Category.Attr.tableID).bootstrapTable('refresh');
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
							}else{
								Common.Fn.alert('录入数据存在异常');
							}
						/*}else{
							Common.Fn.alert('请先上传图片');
						}*/
					
					},
					
				}
			},
			selectCategory:function(){
				var rows = $(Category.Attr.tableID).bootstrapTable('getAllSelections');
				if(rows && rows.length===1){
					$.ajax({
						type:'POST',
						url:Config.requestContextPath+'/category/selectCategory',
						data:JSON.stringify({id:rows[0].id}),
						timeout:5000,
						dataType:'JSON',
						contentType:'application/json',
						success:function(result,status,xhr){
							if(result.success){
								$(Category.Attr.formID).data('bootstrapValidator').resetForm(true);
								$(Category.Attr.categoryModalID).modal({
									  keyboard: true
								});
								$('#categoryName').val(result.data.categoryName);
								$('#categoryType').val(result.data.categoryType);
								$('#id').val(result.data.id);
								
								$('#categoryImg').val(result.data.categoryImg);
								
								var imgDatabase = '';
								if(result.data.categoryImg){
									imgDatabase=result.data.categoryImg.split(',');
								}
								$("#uploadCategoryImg").fileinput({
						            'uploadUrl': Config.requestContextPath+'/file/commonUploadFiles',
						            language: 'zh', 
						            uploadAsync:false,
						            overwriteInitial: false,
						            initialPreviewAsData: true,
						            initialPreview: imgDatabase,
						            uploadExtraData:function (previewId, index) {
						            	return{'uploadPath':'category'};
						            }
						        }).on("filebatchuploadsuccess", function(event, data) {
					                $('#categoryImg').val(data.response.data.join(','));
					            }).on('fileerror', function(event, data, msg) {  
					            	 Common.Fn.alert('-上传失败-'+msg);
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
				}else{
					Common.Fn.alert('请选择一条数据再修改');
				}
			},
			
			deleteCategory:function(){
				var rows = $(Category.Attr.tableID).bootstrapTable('getAllSelections');
				if(rows && rows.length>=1){
					Common.Fn.confirm('确定删除么？',function(){
						var ids = new Array();
						
						$.each(rows,function(i,row){
							ids.push(row.id);
						});
						
						$.ajax({
							type:'POST',
							url:Config.requestContextPath+'/category/deleteCategorys',
							data:JSON.stringify({ids:ids.join(',')}),
							timeout:5000,
							dataType:'JSON',
							contentType:'application/json',
							success:function(result,status,xhr){
								if(result.success){
									//重新加载数据
									$(Category.Attr.tableID).bootstrapTable('refresh');
									Common.Fn.alert('删除成功，已删除【'+result.data+'】条数据');
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
					},function(){});
				}else{
					Common.Fn.alert('请选择需要删除的信息行');
				}
				
			},
			
			ModalClass:{
				Fn:{
					init:function(){
					},
					openModal:function(oprateType){
						$(Category.Attr.formID).data('bootstrapValidator').resetForm(true);
						$(Category.Attr.formID).data('bootstrapValidator').destroy();
						$('#id').val('');
						Category.Fn.FormClass.Fn.init();
						Category.Attr.editType = oprateType;
						
						$("#uploadCategoryImg").fileinput('destroy');
						$('#categoryImg').val('');
						switch (oprateType) {
						case 2:
							//修改
							Category.Fn.selectCategory();
							break;

						default:
							//新增
							
							$('#categoryType').val(Category.Attr.categoryType);
							$(Category.Attr.categoryModalID).modal({
								  keyboard: true
							});
							
							$("#uploadCategoryImg").fileinput({
					            'uploadUrl': Config.requestContextPath+'/file/commonUploadFiles',
					            language: 'zh', 
					            uploadAsync:false,
					            overwriteInitial: false,
					            initialPreviewAsData: true,
					            uploadExtraData:function (previewId, index) {
					            	return{'uploadPath':'category'};
					            }
					        }).on("filebatchuploadsuccess", function(event, data) {
				                $('#categoryImg').val(data.response.data.join(','));
				            }).on('fileerror', function(event, data, msg) {  
				            	 Common.Fn.alert('-上传失败-'+msg);
				            });
							break;
						}
						
					}
				}	
			}
			
		}
};

$(function(){
	Category.Fn.init();
});