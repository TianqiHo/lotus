;var IntegralType = {
		Attr:{
			bannerModalID:'#integraltypeModal',
			formID:'#integraltypeForm',
			tableID:'#integraltypeTable',
			editType:null,
			moduleName:null,
			categoryType:null,
			searchFormID:'#searchForm'
		},
		Fn:{
			init:function(){
				IntegralType.Attr.moduleName = decodeURI(Common.Fn.obtainVariableFromRequestUrl('moduleName'));
				IntegralType.Attr.categoryType = Common.Fn.obtainVariableFromRequestUrl('categoryType');
				
				$('#title').append(IntegralType.Attr.moduleName);
				
				IntegralType.Fn.loadTable();
				IntegralType.Fn.FormClass.Fn.init();
				
			},
			loadTable:function(){
				//先销毁表格
				$(IntegralType.Attr.tableID).bootstrapTable("destroy");
				$(IntegralType.Attr.tableID).bootstrapTable({
					         height: 500,
					         theadClasses: 'thead-blue',//这里设置表头样式
					         classes: 'table table-bordered table-striped table-lg',
					         escape:false, //转义html标签
					         //searchAlign: 'right',
					         //search: true,   //显示搜索框
					         //strictSearch:false,
					         showHeader: true,     //是否显示列头
					         url: Config.requestContextPath+'/integralType/selectIntegralTypes',
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
					                 field: 'id',
					                 align:'center',
					                 title: '序号',
					                 formatter:function(value , row , index){
					                	 return index+1;
					                 }
					         }, {
					                 field: 'integralName',
					                 align:'center',
					                 title: '积分类型名称'
					         }, {
					                 field: 'integralNum',
					                 title: '积分数值',
					                 align:'center'
					         },  {
				                 field: 'integralDailyCeilingNum',
				                 title: '每日上限',
				                 align:'center'
				            }, {
					                 field: 'id',
					                 title: '规则标识',
					                 align:'center',
					         },
					         {
				                 field: 'integrayTypeName',
				                 title: '类型标识',
				                 align:'center',
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
							data:JSON.stringify({categoryType:IntegralType.Attr.categoryType,usePagenation:false}),
							dataType:'JSON',
							contentType:'application/json',
							success:function(result,status,xhr){
								if(result.success){
									var html = '<option>--请选择--</option>';
									$.each(result.data,function(i,every){
										html+='<option value='+every.id+'>'+every.categoryName+'</option>';
									});
									$('#integralType').html(html);
									$("#integralType").selectpicker('refresh');

								}else{
									Common.Fn.alert(result.alertMessage+'【积分类型】');
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
						    
						    var data = $(IntegralType.Attr.searchFormID).serializeJson();
						    if(data.beginTime)data.beginTime = data.beginTime+' 00:00:00';
						    if(data.endTime)data.endTime = data.endTime+' 23:59:59';
						    $.ajax({
								type:'POST',
								url:Config.requestContextPath+'/integralType/selectIntegralTypes',
								data:JSON.stringify(data),
								dataType:'JSON',
								contentType:'application/json',
								success:function(result,status,xhr){
									if(result.success){
										Common.Fn.alert('加载成功',function(){
											 $(IntegralType.Attr.tableID).bootstrapTable('load',result.data);
											 
											 $(IntegralType.Attr.searchFormID)[0].reset();
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
						
						$(IntegralType.Attr.formID).bootstrapValidator({
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
						    	 integralName: {
						          message: '名称验证失败',
						          validators: {
							            notEmpty: {
							              message: '名称不能为空'
							            },
							            stringLength: {  //长度限制
							              min: 1,
							              max: 18,
							              message: '长度必须在1到18位之间'
							            }
							          }
						        },
						       
						        integralType: {
						        	 selector: '#integralType',
							          message: '内容验证失败',
							          validators: {
								            notEmpty: {
								              message: '内容不能为空'
								            }
								        }
							     },
						        integralNum: {
						          validators: {
						            notEmpty: {
						              message: '积分额不能为空'
						            },
						            digits : {
						                message : '积分额必须是正整数'
						            },
						            greaterThan: {
						                value : 1,
						                message : '积分额最小输入1'
						            },
						            lessThan: {
						                value : 100,
						                message : '积分额最大输入100'
						            }
						          }
						        },integralDailyCeilingNum: {
							          validators: {
								            notEmpty: {
								              message: '每日上限额不能为空'
								            },
								            digits : {
								                message : '每日上限额必须是正整数'
								            },
								            greaterThan: {
								                value : 1,
								                message : '每日上限额最小输入1'
								            }
								            
								          }
								        },
						        
						      }
						    });
						/*****************************************************************************/
					},
					
					saveOrUpdateIntegralType:function(){
					
						
						$(IntegralType.Attr.formID).data("bootstrapValidator").validate();
						
						if($(IntegralType.Attr.formID).data('bootstrapValidator').isValid()){
							
							var data = $(IntegralType.Attr.formID).serializeJson();
							$.ajax({
								type:'POST',
								url:Config.requestContextPath+'/integralType/saveOrUpdateIntegralType',
								data:JSON.stringify(data),
								dataType:'JSON',
								contentType:'application/json',
								success:function(result,status,xhr){
									if(result.success){
										Common.Fn.alert(result.alertMessage,function(){
											$(IntegralType.Attr.formID).data('bootstrapValidator').resetForm(true);
											$(IntegralType.Attr.bannerModalID).modal('hide');
											//重新加载数据
											$(IntegralType.Attr.tableID).bootstrapTable('refresh');
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
			selectIntegralType:function(){
				var rows = $(IntegralType.Attr.tableID).bootstrapTable('getAllSelections');
				if(rows && rows.length===1){
					$.ajax({
						type:'POST',
						url:Config.requestContextPath+'/integralType/selectIntegralType',
						data:JSON.stringify({id:rows[0].id}),
						dataType:'JSON',
						contentType:'application/json',
						success:function(result,status,xhr){
							if(result.success){
								$(IntegralType.Attr.formID).data('bootstrapValidator').resetForm(true);
								$(IntegralType.Attr.bannerModalID).modal({
									  keyboard: true
								});
								$('#integralNum').val(result.data.integralNum);
								$('#integralType').val(result.data.integralType);
								$('#integralName').val(result.data.integralName);
								$('#id').val(result.data.id);
								$('#integralDailyCeilingNum').val(result.data.integralDailyCeilingNum);
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
			
			deleteIntegralType:function(){
				var rows = $(IntegralType.Attr.tableID).bootstrapTable('getAllSelections');
				if(rows && rows.length>=1){
					Common.Fn.confirm('确定删除么？',function(){
						var ids = new Array();
						
						$.each(rows,function(i,row){
							ids.push(row.id);
						});
						
						$.ajax({
							type:'POST',
							url:Config.requestContextPath+'/integralType/deleteIntegralTypes',
							data:JSON.stringify({ids:ids.join(',')}),
							dataType:'JSON',
							contentType:'application/json',
							success:function(result,status,xhr){
								if(result.success){
									//重新加载数据
									$(IntegralType.Attr.tableID).bootstrapTable('refresh');
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
						
					openModal:function(oprateType){
						$(IntegralType.Attr.formID).data('bootstrapValidator').resetForm(true);
						$(IntegralType.Attr.formID).data('bootstrapValidator').destroy();
						$('#id').val('');
						IntegralType.Fn.FormClass.Fn.init();
						//$(Banner.Attr.formID).data('bootstrapValidator',null);
						IntegralType.Attr.editType = oprateType;
						switch (oprateType) {
						case 2:
							//修改
							IntegralType.Fn.selectIntegralType();
							break;

						default:
							//新增
							
							$(IntegralType.Attr.bannerModalID).modal({
								  keyboard: true
							});
							break;
						}
						
					}
				},
				
					
				
			}
			
		}
};

$(function(){
	IntegralType.Fn.init();
});