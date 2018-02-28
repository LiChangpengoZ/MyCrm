<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html >
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>思途管理系统</title>
<link href="<%= request. getContextPath()%>/css/login.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="<%= request. getContextPath()%>/jquery-easyui-1.4.1/jquery.min.js"></script>
<script type="text/javascript" src="<%= request. getContextPath()%>/js/jquery.cookie.js"></script> 
<script type="text/javascript">
	var a=0;
	function changeCode(o){
		o.src="images/code.jpg?a="+(a++);
	}
	
	$(window).load(function(){
		window.setTimeout(function(){
		var susername=$.cookie('susername');
		var pusername=$.cookie('pusername');
		$(".operator").val(pusername);
		$(".student").val(susername);
		}, 2000);
	});

	function addk1(){
		$.cookie('susername', ''+$(".student").val(), { expires: 7 }); 
	}
	function addk2(){
		$.cookie('pusername', ''+$(".operator").val(), { expires: 7 }); 
	}
 </script>
</head>
<body style="background-color:#1c77ac; background-image:url(images/light.png); background-repeat:no-repeat; background-position:center top; overflow:hidden;" >
    <div class="loginbody">
        <span class="systemlogo"></span> 
        <div class="loginCon"  style="margin-top: -10px;">
        	<ul class="user_choice">
            	<li class="choice_active" id="a1" onclick="changeChoice(this)">学生登录</li><li onclick="changeChoice(this)" id="a2">教师登录</li><li onclick="changeChoice(this)" id="a3">教务登录</li>
            </ul>
            <div class="login_content"> 
                <div class="loginbox login_stu">
                   <form action="<%= request.getContextPath()%>/login.do" method="post" onsubmit="addk1();">
                        <ul>                        
                            <li><input id="suser" onblur="isTrue('s','user')" name="userName" autocomplete="off" type="text" class="loginuser student" /></li>
                            <li><input id="spass" onblur="isTrue('s','pass')" name="password" autocomplete="off" type="password" class="loginpwd" /></li>
                             <li><input name="authCodeImg"  type="text" class="loginchk"  /><img src="<%= request.getContextPath()%>/authCode.do" class="checkPic" onclick="this.src='<%= request.getContextPath()%>/authCode.do?'+Math.random();" /></li>
                             <li><input name="roleCode" type="text" hidden="hidden" value="xs"/></li>
                            <li><input disabled="disabled" id="ssubmit" type="submit" class="loginbtn" value="登录"   /></li>
                        </ul>
                    </form>
                </div> 
                <div class="loginbox login_tch">
                    <form action="<%= request.getContextPath()%>/login.do" method="post" onsubmit="addk1();">
                        <ul>                        
                            <li><input id="tuser" onblur="isTrue('t','user')" AUTOCOMPLETE="off" name="userName" type="text" class="loginuser operator" /></li>
                            <li><input id="tpass" onblur="isTrue('t','pass')"  autocomplete="off" name="password" type="password" class="loginpwd" value="" /></li>
                             <li><input name="authCodeImg"  type="text" class="loginchk"  /><img src="<%= request.getContextPath()%>/authCode.do" class="checkPic" onclick="this.src='<%= request.getContextPath()%>/authCode.do?'+Math.random();" /></li>
                           	 <li><input name="roleCode" type="text" hidden="hidden" value="js"/></li>
                            <li><input disabled="disabled" id="tsubmit" type="submit" class="loginbtn" value="登录"   /></li>
                        </ul>
                    </form>
                </div>
                 <div class="loginbox login_jw">
                   <form action="<%= request.getContextPath()%>/login.do" method="post" onsubmit="addk1();">
                        <ul>   
                            <li><input id="juser" onblur="isTrue('j','user')" name="userName" autocomplete="off" type="text" class="loginuser student" /></li>
                            <li><input id="jpass" onblur="isTrue('j','pass')" name="password" autocomplete="off" type="password" class="loginpwd" /></li>
                             <li><input name="authCodeImg"  type="text" class="loginchk"  /><img src="<%= request.getContextPath()%>/authCode.do" class="checkPic" onclick="this.src='<%= request.getContextPath()%>/authCode.do?'+Math.random();" /></li>
                           	 <li><input name="roleCode" type="text" hidden="hidden" value="jw"/></li>
                            <li><input disabled="disabled" id="jsubmit" type="submit" class="loginbtn" value="登录"   /></li>
                        </ul>
                    </form>
                </div> 
            </div>
        </div>
    </div>
<script>
	var aChoiceBtn = $(".user_choice").children();
	//console.log(aChoiceBtn);
	var aContent = $(".login_content").children();
	//console.log(aContent);
	for(var i=0; i<aChoiceBtn.length; i++){
		aChoiceBtn[i].index = i;
	}

	var c=$.cookie('type');
	if(c=='0') {
		changeChoice(a1);
	}else{
		changeChoice(a2);
	}

	function changeChoice(obj){
		for(var i=0; i<aChoiceBtn.length; i++){
			aChoiceBtn.eq(i).removeClass("choice_active");
		}
		$(obj).addClass("choice_active");
		for(var i=0; i<aContent.length; i++){
			aContent.eq(i).hide();
		}
		$.cookie('type', ''+obj.index, { expires: 7 }); 
		aContent.eq(obj.index).show();	
	}
</script>    
</body>
</html>