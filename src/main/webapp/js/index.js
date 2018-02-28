$(function() {
	loadMenu();
	streamInit();
	passWordInit();
})

//加载菜单的ajax
function loadMenu() {
	var path=CONST.WEB_URL+"/loadMenu.do";
	$.ajax({
	    url:path,
	    type:"POST",
	    dataType:"json",
	    contentType: 'application/x-www-form-urlencoded; charset=UTF-8',//防止乱码
	    success:function(data){
	    	loadMenuList(data);
	    }
	});		
}

//加载菜单的详情
function loadMenuList(perList) {
	var oneMenu='<div id="aa" style="width:300px;background-color: #F0F9FD;">';
	for (var i = 0; i < perList.length; i++) {
		var oneCode=perList[i].code;
		var onePerName=perList[i].permissionName;
		var sonPerList=perList[i].sonPerList;
		var twoMenu="";
		for (var j = 0; j < sonPerList.length; j++) {
			var twoCode=sonPerList[j].code;
			var twoPerName=sonPerList[j].permissionName;
			var twoUrl=sonPerList[j].url;
			var series=sonPerList[j].series;
			var trueurl=twoUrl+"?series="+series;
			twoMenu+='<div class="adiv" >'+
	        			'<a class="aa" href="javascript:myIfram(\''+twoPerName+'\',\''+trueurl+'\')" >'+
	        				twoPerName+
	        			'</a>'+  
	        		  '</div>'; 
		}
		oneMenu+='<div title="'+onePerName+'"  data-options="" style="overflow:auto;">'+twoMenu+'</div>';
	}
	oneMenu+='</div>';
	$("#zuo").html(oneMenu);
	
	//初始化手风琴
	$('#aa').accordion({    
	    animate:true,
	    fit:true,
	}); 
	
	//移入移出
	ahover();
	//选中不选
	aclick();
}


//追加页面的方法
function myIfram(titleText,url){
	if($("#tt").tabs("exists",titleText)){
		$("#tt").tabs("select",titleText);
	}else{
		var content='<iframe frameborder="0" scrolling="auto" style="width: 100%;height: 100%;" src="'+CONST.WEB_URL+url+'"></iframe>'
		$('#tt').tabs('add',{    		
			title:titleText,    
			content:content,    
			closable:true,    
		});
	}
}  

//a标签的移入移出事件
function ahover() {
	$(".adiv").hover(function(){
		$(this).find("a").css("color","#4098CA");
	},function(){
		$(this).find("a").css("color","black");
	})		
}
//标签的选中不选事件
function aclick() {
	$(".adiv").click(function() {
		$(".adiv").removeClass("se-y");
		$(".adiv").addClass("se-n");
		$(this).removeClass("se-n");
		$(this).addClass("se-y");
	})
	
}
//弹出操作流程框
function alertStream() {
	$("#stream").window("open");
}
//操作流程框的设置
function streamInit() {
	$("#stream").dialog({
		title: ' 使用流程',
	    width: 840,    
	    height: 525,    
	    closed: true,    		//默认关闭状态
	    cache: false,    		//不设置缓存
	    href: '',  				//加载页面
	    modal: true   			//模态，弹出这个框的时候无法操作其他的事情		
	});	
}

//弹出修改密码
function alertPassWord() {
	$("#passWord").window("open");
}
//修改密码框的设置
function passWordInit() {
	$("#passWord").dialog({
		title: ' 修改密码',
	    width: 400,    
	    height: 200,    
	    closed: true,    		//默认关闭状态
	    cache: false,    		//不设置缓存
	    href: '',  				//加载页面
	    modal: true   			//模态，弹出这个框的时候无法操作其他的事情		
	});	
}
//验证密码是否一致
$.extend($.fn.validatebox.defaults.rules, {    
    equals: {    
        validator: function(value,param){    
            return value == $(param[0]).val();    
        },    
        message: '两次输入的密码不一致'   
    }    
});  
//修改密码的ajax
function passUpdate() {
	var oldpass=$("#oldpass").val();
	var newpass=$("#newpass").val();
	
	var path=CONST.WEB_URL+"/passUpdate.do";
	$.ajax({
	    url:path,
	    type:"POST",
	    data: {
	    	password:oldpass,
	    	newpass:newpass
	    	},
	    dataType:"text",
	    contentType: 'application/x-www-form-urlencoded; charset=UTF-8',//防止乱码
	    success:function(data){
	    	switch (data) {
			case "-1":
				alert("请先登录");
				window.location.href=CONST.WEB_URL+"/toLogin.do"
				break;
			case "0":
				Message();
				layer.innerHTML="友情提示：<br/>您输入的密码不正确";
				break;	
			case "-2":
				Message();
				layer.innerHTML="友情提示：<br/>修改失败，请重新修改";
				break;
			case "1":
				alert("修改成功，请重新登录");
				window.location.href=CONST.WEB_URL+"/toLogin.do"
				break;	
			default:
				break;
			}
	    }
	});		
}




