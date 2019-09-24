;var News = {
		Attr:{
			newsModalID:'#newsModal',
			formID:'#newsForm',
			tableID:'#newsTable',
			editType:null,
			searchFormID:'#searchForm',
			categoryType:null,
			moduleName:null
		},
		Fn:{
			init:function(){
				News.Attr.categoryType = Common.Fn.obtainVariableFromRequestUrl('categoryType');
				News.Attr.moduleName = decodeURI(Common.Fn.obtainVariableFromRequestUrl('moduleName'));
				$('#title').append(News.Attr.moduleName);
				News.Fn.loadTable(News.Attr.categoryType);
				
				News.Fn.ModalClass.Fn.init();
				News.Fn.FormClass.Fn.init();
				
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
		                 field: 'newsTitle',
		                 align:'center',
		                 title: '标题'
		         }, {
	                 field: 'newsAuthor',
	                 title: '作者',
	                 align:'center',
		         }, {
	                 field: 'newsFrom',
	                 title: '来源',
	                 align:'center',
	              },  {
		                 field: 'newsTag',
		                 title: '标签',
		                 align:'center',
		          },  {
		                 field: 'categoryNames',
		                 title: '资讯类型',
		                 align:'center',
		           }, {
		                 field: 'newsImage',
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
		         }, {
		        	 visible: false,
		        	 field:'newsContent1',
		        	 title:'内容',
		        	 align:'center'
			         }];
                if(News.Attr.categoryType!='NEWS_TYPE'){
					columns.push( {
		                 field: 'status',
		                 title: '是否审核',
		                 align:'center',
		                 formatter:function(value , row , index){
		                	 var res = '';
		                	 switch (value) {
								case 1:
									res = '未审核';
									break;
								case 2:
									res = '通过';
									break;
								case 3:
									res = '未通过';
									break;
								default:
									res = '未审核';
									break;
								}
				        	 return res;
				         }
			         });
				}
                
                 //先销毁表格
				$(News.Attr.tableID).bootstrapTable("destroy");
				$(News.Attr.tableID).bootstrapTable({
					         height: 500,
					         theadClasses: 'thead-blue',//这里设置表头样式
					         classes: 'table table-bordered table-striped table-lg',
					         escape:false, //转义html标签
					         //searchAlign: 'right',
					         //search: true,   //显示搜索框
					         //strictSearch:false,
					         showHeader: true,     //是否显示列头
					         url: Config.requestContextPath+'/news/selectNewss',
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
					         columns: columns,
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
					             params.categoryType = News.Attr.categoryType
					             params.platForm = 1;
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
							data:JSON.stringify({categoryType:News.Attr.categoryType,usePagenation:false}),
							timeout:2000,
							dataType:'JSON',
							contentType:'application/json',
							success:function(result,status,xhr){
								if(result.success){
									var html = '<option>--请选择--</option>';
									$.each(result.data,function(i,every){
										html+='<option value='+every.id+'>'+every.categoryName+'</option>';
									});
									$('#categorys').html(html);
									$("#categorys").selectpicker('refresh');

								}else{
									Common.Fn.alert(result.alertMessage+'【资讯类型】');
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
						    
						    var data = $(News.Attr.searchFormID).serializeJson();
						    if(data.beginTime)data.beginTime = data.beginTime+' 00:00:00';
						    if(data.endTime)data.endTime = data.endTime+' 23:59:59';
						    data.platForm = 1;
						    data.categoryType = News.Attr.categoryType;
						    $.ajax({
								type:'POST',
								url:Config.requestContextPath+'/news/selectNewss',
								data:JSON.stringify(data),
								timeout:2000,
								dataType:'JSON',
								contentType:'application/json',
								success:function(result,status,xhr){
									if(result.success){
										Common.Fn.alert('加载成功',function(){
											 $(News.Attr.tableID).bootstrapTable('load',result.data);
											 
											 $(News.Attr.searchFormID)[0].reset();
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
						
						//$('#uploadNewsImg').change(function(v2){
						/*$(document).off('change','#uploadNewsImg').on('change','#uploadNewsImg',function(){
							//v2.preventDefault();
							var files = this.files[0];
							Common.Fn.uploadFiles(files,'news',
								function(){
								$(News.Attr.formID).data('bootstrapValidator').updateStatus("uploadNewsImg","NOT_VALIDATED",null);//在之前重置某个验证字段验证规则
								$(News.Attr.formID).data('bootstrapValidator').validateField('uploadNewsImg');//触发指定字段的验证
                                return $(News.Attr.formID).data('bootstrapValidator').isValidField("uploadNewsImg");
							},function(result){
								if(result.success){
									$('#newsImage').val(result.data);
									Common.Fn.alert(result.alertMessage+',上传路径是['+result.data+']');
								}else{
									Common.Fn.alert(result.alertMessage);
								}
							});
						});*/
						
						$(News.Attr.formID).bootstrapValidator({
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
						    	  newsTitle: {
							          message: '标题验证失败',
							          validators: {
								            notEmpty: {
								              message: '标题不能为空'
								            },
								            stringLength: {  //长度限制
								              min: 1,
								              max: 30,
								              message: '长度必须在1到30位之间'
								            }
								          }
						          },
						          newsAuthor: {
							          message: '作者验证失败',
							          validators: {
								            notEmpty: {
								              message: '作者不能为空'
								            },
								            stringLength: { 
								              min: 1,
								              max: 18,
								              message: '长度必须在1到1830位之间'
								            }
								        }
						          },
						          newsFrom: {
							          message: '来源验证失败',
							          validators: {
								            notEmpty: {
								              message: '来源不能为空'
								            }
								        }
						          },
						          newsTag: {
							          message: '标签验证失败',
							          validators: {
								            notEmpty: {
								              message: '标签不能为空'
								            }
								        }
						          },
						          newsContent1: {
							          message: '内容验证失败',
							          validators: {
								            notEmpty: {
								              message: '内容不能为空'
								            }
								        }
						          },
						          categorys: {
						        	  selector: '#categorys',
							          message: '内容验证失败',
							          validators: {
								            notEmpty: {
								              message: '内容不能为空'
								            }
								        }
						          },
						        uploadNewsImg:{
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
					
					saveOrUpdateNews:function(){
						
						if($('#newsImage').val()){
							if(News.Attr.editType===2 && $('#newsImage').val()) {
								$(News.Attr.formID).bootstrapValidator('removeField','uploadNewsImg');
							}
							
							$(News.Attr.formID).data("bootstrapValidator").validate();
							
							if($(News.Attr.formID).data('bootstrapValidator').isValid()){

								var data = $(News.Attr.formID).serializeJson();
								if(data.categorys){
									 var teampCategorys = data.categorys;
									 data['categorysTemp']= teampCategorys;
									 var arr = new Array();

									 if(typeof teampCategorys ==='object'){
										 for(var i = 0;i<teampCategorys.length;i++){
												var categoryJson = {categoryId:teampCategorys[i],id:null};
												arr.push(categoryJson);
										 }
									 }else{
										 var categoryJson = {categoryId:teampCategorys,id:null};
										 arr.push(categoryJson);
									 }
									
									
									 data.categorys = arr;
								}
								
								$.ajax({
									type:'POST',
									url:Config.requestContextPath+'/news/saveOrUpdateNews',
									data:JSON.stringify(data),
									dataType:'JSON', 
									contentType:'application/json',
									success:function(result,status,xhr){
										if(result.success){
											Common.Fn.alert(result.alertMessage,function(){
												$(News.Attr.formID).data('bootstrapValidator').resetForm(true);
												$(News.Attr.newsModalID).modal('hide');
												//重新加载数据
												$(News.Attr.tableID).bootstrapTable('refresh');
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
			
			selectNews:function(){
				var rows = $(News.Attr.tableID).bootstrapTable('getAllSelections');
				if(rows && rows.length===1){
					$.ajax({
						type:'POST',
						url:Config.requestContextPath+'/news/selectNews',
						data:JSON.stringify({id:rows[0].id}),
						timeout:5000,
						dataType:'JSON',
						contentType:'application/json',
						success:function(result,status,xhr){
							if(result.success){
								$(News.Attr.formID).data('bootstrapValidator').resetForm(true);
								$(News.Attr.newsModalID).modal({
									  keyboard: true
								});
								
								var arr = new Array();
								$.each(result.data.categorys,function(i,category){
									arr.push(category.categoryId);
								});
								$('#categorys').val(arr).trigger("change");
								
								$('#newsTitle').val(result.data.newsTitle);
								$('#newsAuthor').val(result.data.newsAuthor);
								$('#newsFrom').val(result.data.newsFrom);
								$('#id').val(result.data.id);
								$('#newsTag').val(result.data.newsTag);
								$('#newsContent1').val(result.data.newsContent1);
								$('#newsImage').val(result.data.newsImage);
								var imgDatabase = '';
								if(result.data.newsImage){
									imgDatabase=result.data.newsImage.split(',');
								}
								
								$("#uploadNewsImg").fileinput({
							            'uploadUrl': Config.requestContextPath+'/file/uploadFiles',
							            language: 'zh', 
							            uploadAsync:false,
							            overwriteInitial: false,
							            initialPreviewAsData: true,
							            initialPreview: imgDatabase,
							            uploadExtraData:function (previewId, index) {
							            	return{'uploadPath':'news'};
							            }
							        }).on("filebatchuploadsuccess", function(event, data) {
						                $('#newsImage').val(data.response.data.join(','));
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
			
			deleteNews:function(){
				var rows = $(News.Attr.tableID).bootstrapTable('getAllSelections');
				if(rows && rows.length>=1){
					Common.Fn.confirm('确定删除么？',function(){
						var ids = new Array();
						
						$.each(rows,function(i,row){
							ids.push(row.id);
						});
						
						$.ajax({
							type:'POST',
							url:Config.requestContextPath+'/news/deleteNewss',
							data:JSON.stringify({ids:ids.join(',')}),
							timeout:5000,
							dataType:'JSON',
							contentType:'application/json',
							success:function(result,status,xhr){
								if(result.success){
									//重新加载数据
									$(News.Attr.tableID).bootstrapTable('refresh');
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
						$(News.Attr.formID).data('bootstrapValidator').resetForm(true);
						$(News.Attr.formID).data('bootstrapValidator').destroy();
						$("#uploadNewsImg").fileinput('destroy');
						$('#id').val('');
						$('#bannerImg').val('');
						News.Fn.FormClass.Fn.init();
						News.Attr.editType = oprateType;
						switch (oprateType) {
						case 2:
							//修改
							News.Fn.selectNews();
							break;

						default:
							//新增
							
							$(News.Attr.newsModalID).modal({
								  keyboard: true
							});
						
							$("#uploadNewsImg").fileinput({
					            'uploadUrl': Config.requestContextPath+'/file/uploadFiles',
					            language: 'zh', 
					            uploadAsync:false,
					            overwriteInitial: false,
					            initialPreviewAsData: true,
					            uploadExtraData:function (previewId, index) {
					            	return{'uploadPath':'news'};
					            }
					        }).on("filebatchuploadsuccess", function(event, data) {
				                $('#newsImage').val(data.response.data.join(','));
				            }).on('fileerror', function(event, data, msg) {  
				            	 Common.Fn.alert('-上传失败-'+msg);
				            });
							break;
						}
						
					}
				},
				
				
			},
			
			detail:function(){

				var rows = $(News.Attr.tableID).bootstrapTable('getAllSelections');
				if(rows && rows.length===1){
					$('#detailModal').modal({
						  keyboard: true
					});
					console.info(rows);
					$('#content').val(rows[0].newsContent1);
				}else{
					Common.Fn.alert('只能选择一行查看');
				}
				
			
			}
		}
};

$(function(){
	News.Fn.init();
});