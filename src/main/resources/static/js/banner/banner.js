;var Banner = {
		Attr:{
			bannerModalID:'#bannerModal',
			formID:'#bannerForm',
			tableID:'#bannerTable',
			editType:null,
			moduleName:null,
			searchFormID:'#searchForm'
		},
		Fn:{
			init:function(){
				Banner.Attr.moduleName = decodeURI(Common.Fn.obtainVariableFromRequestUrl('moduleName'));
				
				$('#title').append(Banner.Attr.moduleName);
				
				Banner.Fn.loadTable();
				Banner.Fn.ModalClass.Fn.init();
				Banner.Fn.FormClass.Fn.init();
				
			},
			loadTable:function(){
				//先销毁表格
				$(Banner.Attr.tableID).bootstrapTable("destroy");
				$(Banner.Attr.tableID).bootstrapTable({
					         height: 500,
					         theadClasses: 'thead-blue',//这里设置表头样式
					         classes: 'table table-bordered table-striped table-lg',
					         escape:false, //转义html标签
					         //searchAlign: 'right',
					         //search: true,   //显示搜索框
					         //strictSearch:false,
					         showHeader: true,     //是否显示列头
					         url: Config.requestContextPath+'/banner/selectBanners',
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
					         }, {
					                 field: 'bannerOrder',
					                 align:'center',
					                 title: '序号',
					                 formatter:function(value , row , index){
					                	 return index+1;
					                 }
					         }, {
					                 field: 'bannerName',
					                 align:'center',
					                 title: '名称'
					         }, {
					                 field: 'bannerImg',
					                 title: '图片',
					                 align:'center',
					                 formatter:function(value , row , index){
							        	 return "<img class='img-responsive img-thumbnail' height=100px width=90px src ="+value+"/>";
							         },
					         }, {
					                 field: 'targetUrl',
					                 title: '跳转地址',
					                 align:'center',
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
						
						$(document).off('click','#search').on('click','#search',function(){
						    var $btn = $(this).button('loading');
						    
						    var data = $(Banner.Attr.searchFormID).serializeJson();
						    if(data.beginTime)data.beginTime = data.beginTime+' 00:00:00';
						    if(data.endTime)data.endTime = data.endTime+' 23:59:59';
						    $.ajax({
								type:'POST',
								url:Config.requestContextPath+'/banner/selectBanners',
								data:JSON.stringify(data),
								dataType:'JSON',
								contentType:'application/json',
								success:function(result,status,xhr){
									if(result.success){
										Common.Fn.alert('加载成功',function(){
											 $(Banner.Attr.tableID).bootstrapTable('load',result.data);
											 
											 $(Banner.Attr.searchFormID)[0].reset();
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
						
						//$('#uploadBannerImg').change(function(v2){
						$(document).off('change','#uploadBannerImg').on('change','#uploadBannerImg',function(){
							//v2.preventDefault();
							//v2.stopPropagation();
							var files = this.files[0];
							Common.Fn.uploadFiles(files,'banner',
								function(){
								$(Banner.Attr.formID).data('bootstrapValidator').updateStatus("uploadBannerImg","NOT_VALIDATED",null);//在之前重置某个验证字段验证规则
								$(Banner.Attr.formID).data('bootstrapValidator').validateField('uploadBannerImg');//触发指定字段的验证
                                return $(Banner.Attr.formID).data('bootstrapValidator').isValidField("uploadBannerImg");
							},function(result){
								if(result.success){
									$('#bannerImg').val(result.data);
									Common.Fn.alert(result.alertMessage+',上传路径是['+result.data+']');
								}else{
									Common.Fn.alert(result.alertMessage);
								}
							});
							return false;
						});
						
						$(Banner.Attr.formID).bootstrapValidator({
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
						    	 bannerName: {
						          message: '名称验证失败',
						          validators: {
							            notEmpty: {
							              message: '名称不能为空'
							            },
							            stringLength: {  //长度限制
							              min: 1,
							              max: 18,
							              message: '长度必须在1到18位之间'
							            },/*
							            regexp: { //正则表达式
							              regexp: /^[a-zA-Z0-9_]+$/,
							              message: '用户名只能包含大写、小写、数字和下划线'
							            },*/
							            different: {  //比较
							              field: 'targetUrl', //需要进行比较的input name值
							              message: '名称不能与跳转地址相同'
							            },/*
							            identical: {  //比较是否相同
							              field: 'password',  //需要进行比较的input name值
							              message: '两次密码不一致'
							            }*/
							          }
						        },
						        targetUrl: {
						          validators: {
						            notEmpty: {
						              message: '跳转地址不能为空'
						            },/*
						            emailAddress: {
						              message: '邮箱地址格式有误'
						            }*/
						          }
						        },
						        uploadBannerImg:{
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
					
					saveOrUpdateBanner:function(){
					
						if(Banner.Attr.editType===2 && $('#bannerImg').val()) {
							$(Banner.Attr.formID).bootstrapValidator('removeField','uploadBannerImg');
						}
						
						$(Banner.Attr.formID).data("bootstrapValidator").validate();
						
						if($(Banner.Attr.formID).data('bootstrapValidator').isValid()){
							
							var data = $(Banner.Attr.formID).serializeJson();
							$.ajax({
								type:'POST',
								url:Config.requestContextPath+'/banner/saveOrUpdateBanner',
								data:JSON.stringify(data),
								dataType:'JSON',
								contentType:'application/json',
								success:function(result,status,xhr){
									if(result.success){
										Common.Fn.alert(result.alertMessage,function(){
											$(Banner.Attr.formID).data('bootstrapValidator').resetForm(true);
											$(Banner.Attr.bannerModalID).modal('hide');
											//重新加载数据
											$(Banner.Attr.tableID).bootstrapTable('refresh');
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
					},
					
				}
			},
			selectBanner:function(){
				var rows = $(Banner.Attr.tableID).bootstrapTable('getAllSelections');
				if(rows && rows.length===1){
					$.ajax({
						type:'POST',
						url:Config.requestContextPath+'/banner/selectBanner',
						data:JSON.stringify({id:rows[0].id}),
						dataType:'JSON',
						contentType:'application/json',
						success:function(result,status,xhr){
							if(result.success){
								$(Banner.Attr.formID).data('bootstrapValidator').resetForm(true);
								$(Banner.Attr.bannerModalID).modal({
									  keyboard: true
								});
								$('#bannerName').val(result.data.bannerName);
								$('#targetUrl').val(result.data.targetUrl);
								$('#bannerImg').val(result.data.bannerImg);
								$('#id').val(result.data.id);
								/*
								var imgDatabase = '';
								if(result.data.bannerImg){
									imgDatabase = result.data.bannerImg.split(',');
								}
								$("#uploadBannerImg").fileinput({
						            'uploadUrl': Config.requestContextPath+'/file/uploadFiles',
						            language: 'zh', 
						            uploadAsync:false,
						            overwriteInitial: false,
						            initialPreviewAsData: true,
						            initialPreview: imgDatabase,
						            uploadExtraData:function (previewId, index) {
						            	return{'uploadPath':'banner'};
						            }
						        }).on("filebatchuploadsuccess", function(event, data) {
					                $('#bannerImg').val(data.response.data.join(','));
					            }).on('fileerror', function(event, data, msg) {  
					            	 Common.Fn.alert('-上传失败-'+msg);
					            });*/
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
			
			deleteBanner:function(){
				var rows = $(Banner.Attr.tableID).bootstrapTable('getAllSelections');
				if(rows && rows.length>=1){
					Common.Fn.confirm('确定删除么？',function(){
						var ids = new Array();
						
						$.each(rows,function(i,row){
							ids.push(row.id);
						});
						
						$.ajax({
							type:'POST',
							url:Config.requestContextPath+'/banner/deleteBanners',
							data:JSON.stringify({ids:ids.join(',')}),
							dataType:'JSON',
							contentType:'application/json',
							success:function(result,status,xhr){
								if(result.success){
									//重新加载数据
									$(Banner.Attr.tableID).bootstrapTable('refresh');
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
						$(Banner.Attr.formID).data('bootstrapValidator').resetForm(true);
						$(Banner.Attr.formID).data('bootstrapValidator').destroy();
						$('#id').val('');
						$('#bannerImg').val('');
						Banner.Fn.FormClass.Fn.init();
						//$(Banner.Attr.formID).data('bootstrapValidator',null);
						Banner.Attr.editType = oprateType;
						switch (oprateType) {
						case 2:
							//修改
							Banner.Fn.selectBanner();
							break;

						default:
							//新增
							
							$(Banner.Attr.bannerModalID).modal({
								  keyboard: true
							});
						/*
							$("#uploadBannerImg").fileinput({
					            'uploadUrl': Config.requestContextPath+'/file/uploadFiles',
					            language: 'zh', 
					            uploadAsync:false,
					            overwriteInitial: false,
					            initialPreviewAsData: true,
					            uploadExtraData:function (previewId, index) {
					            	return{'uploadPath':'banner'};
					            }
					        }).on("filebatchuploadsuccess", function(event, data) {
				                $('#bannerImg').val(data.response.data.join(','));
				            }).on('fileerror', function(event, data, msg) {  
				            	 Common.Fn.alert('-上传失败-'+msg);
				            });*/
							break;
						}
						
					}
				},
				
					
				
			}
			
		}
};

$(function(){
	Banner.Fn.init();
});