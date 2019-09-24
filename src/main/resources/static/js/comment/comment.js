;var Comment = {
		Attr:{
			tableID:'#newsTable',
			editType:null,
			searchFormID:'#searchForm',
			categoryType:null,
			moduleName:null,
			commentTableID:'#commentTable'
		},
		Fn:{
			init:function(){
				
				Comment.Attr.categoryType = Common.Fn.obtainVariableFromRequestUrl('categoryType');
				Comment.Attr.moduleName = decodeURI(Common.Fn.obtainVariableFromRequestUrl('moduleName'));
				
				$('#title').append(Comment.Attr.moduleName);
				
				Comment.Fn.loadTable(Comment.Attr.categoryType);
				
				$(document).off('click','#search').on('click','#search',function(){
				    var $btn = $(this).button('loading');
				    
				    var data = $(Comment.Attr.searchFormID).serializeJson();
				    if(data.beginTime)data.beginTime = data.beginTime+' 00:00:00';
				    if(data.endTime)data.endTime = data.endTime+' 23:59:59';
				    
				    data['categoryType'] = Comment.Attr.categoryType;
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
									 $(Comment.Attr.tableID).bootstrapTable('load',result.data);
									 
									 $(Comment.Attr.searchFormID)[0].reset();
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
				
			},
			loadTable:function(){
				//先销毁表格
				$(Comment.Attr.tableID).bootstrapTable("destroy");
				$(Comment.Attr.tableID).bootstrapTable({
			         height: 410,
			         theadClasses: 'thead-blue',//这里设置表头样式
			         classes: 'table table-bordered table-striped table-lg',
			         escape:false, //转义html标签
			         showHeader: true,     //是否显示列头
			         url: Config.requestContextPath+'/news/selectNewss',
			         method: 'post',                      //请求方式（*）
			         showRefresh: true,                  //是否显示刷新按钮
			         showColumns: true,                  //显示所有的列控制
			         minimumCountColumns:5,//当列的数量大于等于5时 才显示列选择按钮
			         locale: 'zh-CN', //中文支持
			         showLoading: true,
			         pagination: true,                   //是否显示分页（*）
			         pageNumber: 1,                       //初始化加载第一页，默认第一页
			         pageSize: 15,                       //每页的记录行数（*）
			         pageList: '[2,5,15,20]',        //可供选择的每页的行数
			         sidePagination: 'server',           //分页方式：client客户端分页，server服务端分页（*
			         showPaginationSwitch:true,
			         paginationHAlign: 'left',
			         clickToSelect: true,                //是否启用点击选中行
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
			           },{
			        	 visible: false,
			        	 field:'createTime',
			        	 title:'创建时间',
			        	 align:'center',
			        	 
			         },
			         {
			        	 visible: false,
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
			             params.categoryType = Comment.Attr.categoryType;
			            return params;
			         },
			         
			         onClickRow:function(row, $element, field){
			        	 Comment.Fn.loadCommentTable(row.id);
			              
			         },
			         onLoadSuccess: function (data) {
			        	 if(data && data.list && data.list.length>=1){
			        		 Comment.Fn.loadCommentTable(data.list[0].id);
			        	 }else{
			        		 Comment.Fn.loadCommentTable(null);
			        	 }
			            
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
			loadCommentTable:function(newsId){
				//先销毁表格
				$(Comment.Attr.commentTableID).bootstrapTable("destroy");
				$(Comment.Attr.commentTableID).bootstrapTable({
					         height: 410,
					         theadClasses: 'thead-blue',//这里设置表头样式
					         classes: 'table table-bordered table-striped table-lg',
					         escape:false, //转义html标签
					         showHeader: true,     //是否显示列头
					         url: Config.requestContextPath+'/comment/selectComments',
					         method: 'post',                      //请求方式（*）
					         showRefresh: true,                  //是否显示刷新按钮
					         showColumns: true,                  //显示所有的列控制
					         minimumCountColumns:1,//当列的数量大于等于1时 才显示列选择按钮
					         locale: 'zh-CN', //中文支持
					         showLoading: true,
					         pagination: true,                   //是否显示分页（*）
					         pageNumber: 1,                       //初始化加载第一页，默认第一页
					         pageSize: 15,                       //每页的记录行数（*）
					         pageList: '[2,5,15,20]',        //可供选择的每页的行数
					         sidePagination: 'server',           //分页方式：client客户端分页，server服务端分页（*
					         showPaginationSwitch:true,
					         paginationHAlign: 'left',
					         clickToSelect: true,                //是否启用点击选中行
					         totalField:'total',
					         dataField:'list',
					         toolbar: '#toolbar',//指定工具栏
					         toolbarAlign: 'left',//工具栏对齐方式
					         columns: [{
					             checkbox: true
					         }, {
					                 field: 'valid',
					                 align:'center',
					                 title: '序号',
					                 formatter:function(value , row , index){
					                	 return index+1;
					                 }
					         }, 
					         {
					                 field: 'sayWhat',
					                 align:'center',
					                 title: '评论内容'
					         },
					         { field: 'createByName',
					                 title: '评论人',
					                 align:'center',
					           },{
					        	 field:'createTime',
					        	 title:'评论时间',
					        	 align:'center',
					        	 
					         },{
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
					         },
					         {
					        	 visible: false,
				                 field: 'id',
				                 title: '评论ID',
				                 align:'center'
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
					             params.categoryType = Comment.Attr.categoryType;
					             params.articalId = newsId;
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
			saveOrUpdateComment:function(status){
				
				var rows = $(Comment.Attr.commentTableID).bootstrapTable('getAllSelections');
				if(rows && rows.length===1){
					$.ajax({
						type:'POST',
						url:Config.requestContextPath+'/comment/saveOrUpdateComment',
						data:JSON.stringify({id:rows[0].id,status:status}),
						dataType:'JSON', 
						contentType:'application/json',
						success:function(result,status,xhr){
							if(result.success){
								Common.Fn.alert('操作成功');
								$(Comment.Attr.commentTableID).bootstrapTable('refresh');
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
					Common.Fn.alert('请选择一条评论数据');
				}
			}
		}
};

$(function(){
	Comment.Fn.init();
});