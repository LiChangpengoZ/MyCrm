<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%> 
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%> 
<!DOCTYPE html >
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>信息管理系统页面</title>
<script type="text/javascript" src="<%= request. getContextPath()%>/jquery-easyui-1.4.1/jquery.min.js"></script>
<script src="<%= request. getContextPath()%>/jquery-easyui-1.4.1/jquery.easyui.min.js" ></script>
<link rel="stylesheet" type="text/css" href="<%= request. getContextPath()%>/jquery-easyui-1.4.1/themes/gray/easyui.css"/>
<link rel="stylesheet" href="<%= request. getContextPath()%>/jquery-easyui-1.4.1/themes/icon.css" />
<link rel="stylesheet" href="<%= request. getContextPath()%>/css/index.css" />
<script src="<%= request. getContextPath()%>/js/CONST.js" ></script>
<script src="<%= request. getContextPath()%>/js/tools.js" ></script>
<script src="<%= request. getContextPath()%>/js/index.js" ></script>
</head>
<body class="easyui-layout">   
		<!--上，删除title和split，可以消除边框-->
	    <div data-options="region:'north',border:false" style="height:93px;background-image: url('<%= request. getContextPath()%>/images/topbg.gif');">
	    	<div style="float: left;"><img src="<%= request. getContextPath()%>/images/topleft.jpg" /></div>
	    	<div style="float: right;"><img src="<%= request. getContextPath()%>/images/topright.jpg" /></div>
	    	<img style="text-align:left;margin-left: -271px;margin-top: 15px" src="<%= request. getContextPath()%>/images/logo.png" />
	    	<div class="topdiv">
	    		<ul class="topul">
    				<li class="topli"><a href="javascript:alertStream();">使用流程</a></li>
    				<li class="topli"><a href="javascript:alertPassWord();">修改密码</a></li>
  	  				<li class="topli"><a href="<%= request. getContextPath()%>/exit.do">退出</a></li>
    			</ul>
    			<div class="user">
					<span >${user.trueName}</span>
				    <i>消息</i>
				    <b class="msgcount" onclick="openwin();" style="background-image: url(&quot;images/msg.png&quot;);">0</b>
				</div>
	    	</div>
	    	</div> 
	    <!--左-->
	    <div id="zuo" data-options="region:'west',title:'系 统 主 页',split:true,border:false" style="width:200px;height: 100%;">
	    	

	    </div> 
	    <!--中间-->
	    <div data-options="region:'center',border:false" style="padding:5px;background:#eee;height: 100%">
	    	<!--选项卡-->
	    	<div id="tt" class="easyui-tabs" style="width:100%;height:100%;" fit=true>   
			    <div title="欢迎登陆" style="padding:20px;height: 100%;background: url('<%= request. getContextPath()%>/images/welcome.jpg');"  >  
			     
			    </div>   
			     
			</div>  	
	    </div>
	    
	    <!--设置dialog的样子-->
		<div id="stream" style="font-size: 15px">
			<ul>
				<li>
					<h3>1.学生考勤</h3>
					<p>
						&nbsp;&nbsp;&nbsp;班级考勤：每日由班长（有权限）进行考勤（每日三次）。<br/>
    					&nbsp;&nbsp;&nbsp;点击教学管理-学生考勤，可批量考勤（可点击状态右侧的多选按钮，单项批量考勤）<br/>
    					&nbsp;&nbsp;&nbsp;考勤查看：点击学生信息-个人考勤信息查看<br/>
    				</p>
				</li>
				<li>
					<h3>2.作业状况</h3>
					<p> 
						&nbsp;&nbsp;&nbsp;由讲师创建作业后，点击学生信息-作业完成状况<br/>
    					&nbsp;&nbsp;&nbsp;可由学生自己给作业及考试打分，由讲师抽查检验后锁定记录后，学生将不能改分。<br/>
    				</p>
				</li>
				<li>
					<h3>3.个人学习进度</h3>
					<p> 
						&nbsp;&nbsp;&nbsp;每天课程结束后，点击学生信息-个人学习进度，学生录入修改自己的学习掌握状况。<br/>
    				</p>
				</li>
				<li>
					<h3>4.请假申请</h3>
					<p> 
						&nbsp;&nbsp;&nbsp;点击学生信息-请假申请，添加请假申请。该申请自动先向当班讲师发送，讲师同意后，再向教务发送，教务同意后，申请结束。申请过程中，可查看申请进度，拒绝或申请同意时，会发出消息提醒。<br/>
    				</p>
				</li>
				<li>
					<h3>5.学生反馈表</h3>
					<p>
						&nbsp;&nbsp;&nbsp;每周一由讲师发起并创建每个学生的周反馈表，点击学生信息-学生反馈表，周五之前填写修改。<br/>
    				</p>
				</li>
				<li>
					<h3>6.座次查看</h3>
					<p> 
						&nbsp;&nbsp;&nbsp;点击学生信息-座次查看，方便了解同学的信息<br/>
    				</p>
				</li>
				<li>
					<h3>7.企业面试</h3>
					<p>
						&nbsp;&nbsp;&nbsp;快就业时，由就业老师创建并发送通知消息。点击学生信息-企业面试，可查看面试细节。面试过后，必须填写面试结果。<br/>
    				</p>
				</li>
				<li>
					<h3>8.就业状况登记</h3>
					<p>
						&nbsp;&nbsp;&nbsp;点击学生信息-就业状况登记，可由学生或教务老师填写，登记学生的就业成功的状况。就业完成后，学生换工作时也可登录系统，填写登记。方便思途提供再就业及猎头服务<br/>
    				</p>
				</li>
				<li>
					<h3>9.意见反馈</h3>
					<p>	
						&nbsp;&nbsp;&nbsp;点击学生信息-意见反馈，提交对班级-讲师-思途的意见。该反馈为匿名处理。<br/>
    				</p>
				</li>
			</ul>			
		</div>
		
		<div id="passWord">
			<div style="padding: 7px 58px">原密码：<input id="oldpass" class="easyui-validatebox" type="text" data-options="required:true,missingMessage:'密码不能为空'" style="width:200px"></div>
			<div style="padding: 7px 58px">新密码：<input id="newpass" class="easyui-validatebox" type="text" data-options="required:true,missingMessage:'密码不能为空'" style="width:200px"></div>
			<div style="padding: 7px 48px">确认密码：<input id="renewpass" class="easyui-validatebox" validType="equals['#newpass']" type="text" data-options="required:true,missingMessage:'密码不能为空'" style="width:200px"></div>
			<div style="padding: 7px 128px">
				<a id="remove" onclick="javascript:$('#passWord').window('close')" class="easyui-linkbutton" data-options="">取消</a>
				<a style="margin-left: 18px;" id="passSubmit" href="javascript:passUpdate()" class="easyui-linkbutton" data-options="">修改</a>
			</div>				
		</div>
				   
</body>  
</html>