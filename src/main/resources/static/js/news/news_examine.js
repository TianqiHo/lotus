;var NewsExamine = {
		Attr:{
			tableID:'#newsTable',
			editType:null,
			searchFormID:'#searchForm',
			categoryType:null,
			moduleName:null
		},
		Fn:{
			init:function(){
				NewsExamine.Attr.categoryType = Common.Fn.obtainVariableFromRequestUrl('categoryType');
				NewsExamine.Attr.moduleName = decodeURI(Common.Fn.obtainVariableFromRequestUrl('moduleName'));
				$('#title').append(NewsExamine.Attr.moduleName);
				NewsExamine.Fn.loadTable(NewsExamine.Attr.categoryType);
				
				$(document).off('click','#search').on('click','#search',function(){
				    var $btn = $(this).button('loading');
				    
				    var data = $(NewsExamine.Attr.searchFormID).serializeJson();
				    if(data.beginTime)data.beginTime = data.beginTime+' 00:00:00';
				    if(data.endTime)data.endTime = data.endTime+' 23:59:59';
				    data.platForm=1;
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
									 $(NewsExamine.Attr.tableID).bootstrapTable('load',result.data);
									 
									 $(NewsExamine.Attr.searchFormID)[0].reset();
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
				$(NewsExamine.Attr.tableID).bootstrapTable("destroy");
				$(NewsExamine.Attr.tableID).bootstrapTable({
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
					           }, {
					                 field: 'newsImage',
					                 title: '图片',
					                 align:'center',
					                 formatter:function(value , row , index){
							        	 return "<img class='img-responsive img-thumbnail' height=100px width=90px src ="+value+"/>";
							         },
					         }, {
					        	 field:'createTime',
					        	 title:'创建时间',
					        	 align:'center',
					        	 
					         },{
					        	 field:'updateTime',
					        	 title:'更新时间',
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
					             params.categoryType = NewsExamine.Attr.categoryType;
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
			saveOrUpdateNews:function(status){
				
				var rows = $(NewsExamine.Attr.tableID).bootstrapTable('getAllSelections');
				if(rows && rows.length===1){
					
					$.ajax({
						type:'POST',
						url:Config.requestContextPath+'/news/saveOrUpdateNews',
						data:JSON.stringify(data),
						dataType:'JSON', 
						contentType:'application/json',
						success:function(result,status,xhr){
							if(result.success){
								Common.Fn.alert('操作成功');
								//重新加载数据
								$(NewsExamine.Attr.tableID).bootstrapTable('refresh');
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
					Common.Fn.alert('请选择一条数据');
				}
			}
			
		}
};

$(function(){
	NewsExamine.Fn.init();
});