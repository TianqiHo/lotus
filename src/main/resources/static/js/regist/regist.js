;
var Regist = {
		Attr:{
			
			registFormId:'#registForm'
		},
		Fn:{
			init:function(){
				Regist.FormClass.validForm();
			}
		},
		FormClass:{
			validForm:function(){
				 $(Regist.Attr.registFormId).bootstrapValidator({
//			        live: 'disabled',
			        message: 'This value is not valid',
			        feedbackIcons: {
			            valid: 'glyphicon glyphicon-ok',
			            invalid: 'glyphicon glyphicon-remove',
			            validating: 'glyphicon glyphicon-refresh'
			        },
			        fields: {
			        	nickName: {
			                group: '.col-lg-10',
			                message: '昵称无效',
			                validators: {
			                    notEmpty: {
			                        message: '昵称不能为空'
			                    }
			                }
			            },
			           
			            userName: {
			            	group: '.col-lg-10',
			                message: '用户名无效',
			                validators: {
			                    notEmpty: {
			                        message: '用户名不能为空'
			                    },
			                    stringLength: {
			                        min: 5,
			                        max: 30,
			                        message: '用户名必须在5-30个字数之间'
			                    },
			                    regexp: {
			                        regexp: /^[a-zA-Z0-9_\.]+$/,
			                        message: 'The username can only consist of alphabetical, number, dot and underscore'
			                    },
			                    different: {
			                        field: 'passWord,confirmPassword',
			                        message: '用户名不能跟密码相同'
			                    }
			                }
			            },
			            passWord: {
			                validators: {
			                    notEmpty: {
			                        message: '登陆密码不能为空'
			                    },
			                   /* identical: {
			                        field: 'confirmPassword',
			                        message: 'The password and its confirm are not the same'
			                    },*/
			                    different: {
			                        field: 'userName',
			                        message: '密码不能与用户名相同'
			                    }
			                }
			            },
			            confirmPassword: {
			                validators: {
			                    notEmpty: {
			                        message: '确认密码不能为空 '
			                    },
			                    identical: {
			                        field: 'passWord',
			                        message: '确认密码和密码不一致'
			                    },
			                    different: {
			                        field: 'userName',
			                        message: '密码不能与用户名相同'
			                    }
			                }
			            },
			            birthday: {/*
			                validators: {
			                    date: {
			                        format: 'YYYY/MM/DD',
			                        message: 'The birthday is not valid'
			                    }
			                }*/
			            },
			            gender: {/*
			                validators: {
			                    notEmpty: {
			                        message: 'The gender is required'
			                    }
			                }*/
			            }
			        }
			    }).on('success.form.bv', function(e) {
		            // Prevent form submission
		            e.preventDefault();
		            // Get the form instance
		            var $form = $(e.target);
		            
		            var data = $form.serializeJson();

		            // Get the BootstrapValidator instance
		           // var bv = $form.data('bootstrapValidator');
		            //console.info(bv);
		            // Use Ajax to submit form data
//		            $.post($form.attr('action'), $form.serialize(), function(result) {
//		                console.log(result);
//		            }, 'json');
		            $.ajax({
						type:'POST',
						url:Config.requestContextPath+'/user/saveOrUpdateUser',
						data:JSON.stringify(data),
						dataType:'JSON',
						contentType:'application/json',
						success:function(result,status,xhr){
							if(result.success){
								Common.Fn.alert('注册成功',function(){
									$(Regist.Attr.registFormId).data('bootstrapValidator').resetForm(true);
									$(Regist.Attr.registFormId).data('bootstrapValidator').destroy();
									window.location.href = '/lotus/index.html';
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
		        });
			},
			
			

		}
};

$(function(){
	Regist.Fn.init();
});