;var Product = {
		Attr:{
			productModalID:'#productModal',
			formID:'#productForm',
			tableID:'#productTable',
			editType:null,
			searchFormID:'#searchForm',
			categoryType:null,
			moduleName:null
		},
		Fn:{
			init:function(){
				Product.Attr.categoryType = Common.Fn.obtainVariableFromRequestUrl('categoryType');
				Product.Attr.moduleName = decodeURI(Common.Fn.obtainVariableFromRequestUrl('moduleName'));
				$('#title').append(Product.Attr.moduleName);
				Product.Fn.loadTable(Product.Attr.categoryType);
				
				Product.Fn.ModalClass.Fn.init();
				Product.Fn.FormClass.Fn.init();
				
			},
			loadTable:function(){
				
				var columns = [{
		             checkbox: true
		         }, {
		                 field: 'id',
		                 align:'center',
		                 title: '序号',
		                 formatter:function(value , row , index){
		                	 return index+1;
		                 }
		         }, {
		                 field: 'productName',
		                 align:'center',
		                 title: '名称'
		         }, {
	                 field: 'productIntegralPrice',
	                 title: '积分额',
	                 align:'center',
		         }, {
	                 field: 'productIntroduction',
	                 title: '简介',
	                 align:'center',
	              },  {
		                 field: 'productRepertoryNum',
		                 title: '库存',
		                 align:'center',
		          },  {
		                 field: 'categoryName',
		                 title: '品类',
		                 align:'center',
		           }, {
		                 field: 'productName',
		                 title: '商品图片',
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
		         },{
	                 field: 'describeImg',
	                 title: '描述图片',
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
	         }, {
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
		         }];
                
                 //先销毁表格
				$(Product.Attr.tableID).bootstrapTable("destroy");
				$(Product.Attr.tableID).bootstrapTable({
					         height: 500,
					         theadClasses: 'thead-blue',//这里设置表头样式
					         classes: 'table table-bordered table-striped table-lg',
					         escape:false, //转义html标签
					         //searchAlign: 'right',
					         //search: true,   //显示搜索框
					         //strictSearch:false,
					         showHeader: true,     //是否显示列头
					         url: Config.requestContextPath+'/product/selectProducts',
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
					         pageSize: 2,                       //每页的记录行数（*）
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
					         columns: columns,
					         responseHandler:function(result){
					        	 if(result.success){
										return result.data;
									}else{
										alert(result.alertMessage);
									}
					         },
					        
					         queryParams:function(params) {
					        	 console.info(params);
					        	 console.info('---------------------------------------------');
					             params.pageNumber = params.offset+1;
					             params.pageSize = params.limit;
					             params.categoryType = Product.Attr.categoryType
					             params.platForm = 1;
					             console.info(params);
					            return params;
					         },
					         onLoadError: function (status, jqXHR) {
					             if(jqXHR && jqXHR.responseJSON && jqXHR.responseJSON.message){
					            	 Common.Fn.alert(jqXHR.responseJSON.message);
					             }else{
					            	 Common.Fn.alert('系统异常');
					             }
					          }
					     });
			},
			FormClass:{
				Fn:{
					init:function(){
						
						$.ajax({
							type:'POST',
							url:Config.requestContextPath+'/category/selectCategorys',
							data:JSON.stringify({categoryType:Product.Attr.categoryType,usePagenation:false}),
							dataType:'JSON',
							contentType:'application/json',
							success:function(result,status,xhr){
								if(result.success){
									var html = '<option>--请选择--</option>';
									$.each(result.data,function(i,every){
										html+='<option value='+every.id+'>'+every.categoryName+'</option>';
									});
									$('#productCategoryId').html(html);
									$("#productCategoryId").selectpicker('refresh');

								}else{
									Common.Fn.alert(result.alertMessage+'【商品类型】');
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
						
						
						$(document).off('click','#search').on('click','#search',function(){
						    var $btn = $(this).button('loading');
						    
						    var data = $(Product.Attr.searchFormID).serializeJson();
						    if(data.beginTime)data.beginTime = data.beginTime+' 00:00:00';
						    if(data.endTime)data.endTime = data.endTime+' 23:59:59';
						    data.platForm = 1;
						    data.categoryType = Product.Attr.categoryType;
						    $.ajax({
								type:'POST',
								url:Config.requestContextPath+'/product/selectProducts',
								data:JSON.stringify(data),
								dataType:'JSON',
								contentType:'application/json',
								success:function(result,status,xhr){
									if(result.success){
										Common.Fn.alert('加载成功',function(){
											 $(Product.Attr.tableID).bootstrapTable('load',result.data);
											 
											 $(Product.Attr.searchFormID)[0].reset();
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
						
						
						$(Product.Attr.formID).bootstrapValidator({
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
						      },
						      fields: {
						    	  productName: {
							          message: '商品名称验证失败',
							          validators: {
								            notEmpty: {
								              message: '商品名称不能为空'
								            },
								            stringLength: {  //长度限制
								              min: 1,
								              max: 30,
								              message: '长度必须在1到30位之间'
								            }
								          }
						          },
						          productIntegralPrice: {
							          message: '积分额验证失败',
							          validators: {
								            notEmpty: {
								              message: '积分额不能为空'
								            },
								            stringLength: { 
								              min: 1,
								              max: 10,
								              message: '长度必须在1到10位之间'
								            }
								        }
						          },
						          productRepertoryNum: {
							          message: '库存验证失败',
							          validators: {
								            notEmpty: {
								              message: '库存不能为空'
								            },
								            stringLength: { 
									              min: 1,
									              max: 10,
									              message: '长度必须在1到10位之间'
									         }
								        }
						          },
						          productIntroduction: {
							          message: '商品简介验证失败',
							          validators: {
								            notEmpty: {
								              message: '商品简介不能为空'
								            }
								        }
						          },
						         
						          productCategoryId: {
						        	  selector: '#productCategoryId',
							          message: '品类验证失败',
							          validators: {
								            notEmpty: {
								              message: '品类不能为空'
								            }
								        }
						          },
						        uploadProductImg:{
						        	validators: {
						        		file:{
						        			extension: 'png,jpg,jpeg',
					                        type: 'image/png,image/jpg,image/jpeg',
					                        message: '此文件不是图片'
						        		},
							            notEmpty: {
							              message: '文件不能为空'
							            }
							          }
						        }
						      }
						    });
						/*****************************************************************************/
					},
					
					saveOrUpdateProduct:function(){
						
						if($('#productImage').val()){
							if(Product.Attr.editType===2 && $('#productImage').val()) {
								$(Product.Attr.formID).bootstrapValidator('removeField','uploadProductImg');
							}
							
							$(Product.Attr.formID).data("bootstrapValidator").validate();
							
							if($(Product.Attr.formID).data('bootstrapValidator').isValid()){

								var data = $(Product.Attr.formID).serializeJson();
								
								$.ajax({
									type:'POST',
									url:Config.requestContextPath+'/product/saveOrUpdateProduct',
									data:JSON.stringify(data),
									dataType:'JSON', 
									contentType:'application/json',
									success:function(result,status,xhr){
										if(result.success){
											Common.Fn.alert(result.alertMessage,function(){
												$(Product.Attr.formID).data('bootstrapValidator').resetForm(true);
												$(Product.Attr.productModalID).modal('hide');
												//重新加载数据
												$(Product.Attr.tableID).bootstrapTable('refresh');
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
						}else{
							Common.Fn.alert('请先点击上传图片按钮');
						}
					},
					
				}
			},
			
			selectProduct:function(){
				var rows = $(Product.Attr.tableID).bootstrapTable('getAllSelections');
				if(rows && rows.length===1){
					$.ajax({
						type:'POST',
						url:Config.requestContextPath+'/product/selectProduct',
						data:JSON.stringify({id:rows[0].id}),
						dataType:'JSON',
						contentType:'application/json',
						success:function(result,status,xhr){
							if(result.success){
								$(Product.Attr.formID).data('bootstrapValidator').resetForm(true);
								$(Product.Attr.productModalID).modal({
									  keyboard: true
								});
								
								
								$('#productCategoryId').val(result.data.productCategoryId).trigger("change");
								
								$('#productName').val(result.data.productName);
								$('#productIntegralPrice').val(result.data.productIntegralPrice);
								$('#productIntroduction').val(result.data.productIntroduction);
								$('#id').val(result.data.id);
								$('#productRepertoryNum').val(result.data.productRepertoryNum);
								$('#describeImg').val(result.data.describeImg);
								$('#productImg').val(result.data.productImg);
								var productImgDatabase = '';
								if(result.data.newsImage){
									productImgDatabase = result.data.productImg.split(',');
								}
								
								$("#uploadProductImg").fileinput({
							            'uploadUrl': Config.requestContextPath+'/file/uploadFiles',
							            language: 'zh', 
							            uploadAsync:false,
							            overwriteInitial: false,
							            initialPreviewAsData: true,
							            initialPreview: productImgDatabase,
							            uploadExtraData:function (previewId, index) {
							            	return{'uploadPath':'product'};
							            }
							        }).on("filebatchuploadsuccess", function(event, data) {
						                $('#productImg').val(data.response.data.join(','));
						            }).on('fileerror', function(event, data, msg) {  
						            	 Common.Fn.alert('-上传失败-'+msg);
						            });
								
								
								var productDescribeImgDatabase = '';
								if(result.data.newsImage){
									productDescribeImgDatabase = result.data.describeImg.split(',');
								}
								$("#uploadProductDescribeImg").fileinput({
						            'uploadUrl': Config.requestContextPath+'/file/uploadFiles',
						            language: 'zh', 
						            uploadAsync:false,
						            overwriteInitial: false,
						            initialPreviewAsData: true,
						            initialPreview: productDescribeImgDatabase,
						            uploadExtraData:function (previewId, index) {
						            	return{'uploadPath':'product/describe'};
						            }
						        }).on("filebatchuploadsuccess", function(event, data) {
					                $('#describeImg').val(data.response.data.join(','));
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
			
			deleteProduct:function(){
				var rows = $(Product.Attr.tableID).bootstrapTable('getAllSelections');
				if(rows && rows.length>=1){
					Common.Fn.confirm('确定删除么？',function(){
						var ids = new Array();
						
						$.each(rows,function(i,row){
							ids.push(row.id);
						});
						
						$.ajax({
							type:'POST',
							url:Config.requestContextPath+'/product/deleteProducts',
							data:JSON.stringify({ids:ids.join(',')}),
							dataType:'JSON',
							contentType:'application/json',
							success:function(result,status,xhr){
								if(result.success){
									//重新加载数据
									$(Product.Attr.tableID).bootstrapTable('refresh');
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
						$(Product.Attr.formID).data('bootstrapValidator').resetForm(true);
						$(Product.Attr.formID).data('bootstrapValidator').destroy();
						$("#uploadProductImg").fileinput('destroy');
						$("#uploadProductDescribeImg").fileinput('destroy');
						$('#id').val('');
						$('#productImg').val('');
						$('#describeImg').val('');
						Product.Fn.FormClass.Fn.init();
						Product.Attr.editType = oprateType;
						switch (oprateType) {
						case 2:
							//修改
							Product.Fn.selectProduct();
							break;

						default:
							//新增
							
							$(Product.Attr.productModalID).modal({
								  keyboard: true
							});
						
							$("#uploadProductImg").fileinput({
					            'uploadUrl': Config.requestContextPath+'/file/uploadFiles',
					            language: 'zh', 
					            uploadAsync:false,
					            overwriteInitial: false,
					            initialPreviewAsData: true,
					            uploadExtraData:function (previewId, index) {
					            	return{'uploadPath':'product'};
					            }
					        }).on("filebatchuploadsuccess", function(event, data) {
				                $('#productImg').val(data.response.data.join(','));
				            }).on('fileerror', function(event, data, msg) {  
				            	 Common.Fn.alert('-上传失败-'+msg);
				            });
							
							$("#uploadProductDescribeImg").fileinput({
					            'uploadUrl': Config.requestContextPath+'/file/uploadFiles',
					            language: 'zh', 
					            uploadAsync:false,
					            overwriteInitial: false,
					            initialPreviewAsData: true,
					            uploadExtraData:function (previewId, index) {
					            	return{'uploadPath':'product/describe'};
					            }
					        }).on("filebatchuploadsuccess", function(event, data) {
				                $('#describeImg').val(data.response.data.join(','));
				            }).on('fileerror', function(event, data, msg) {  
				            	 Common.Fn.alert('-上传失败-'+msg);
				            });
							break;
						}
						
					}
				},
				
				
			}
		}
};

$(function(){
	Product.Fn.init();
});