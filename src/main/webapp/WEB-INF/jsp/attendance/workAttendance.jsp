<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%> 
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%> 
<!DOCTYPE html >
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>考勤信息管理</title>
<script type="text/javascript" src="<%= request. getContextPath()%>/jquery-easyui-1.4.1/jquery.min.js"></script>
<script src="<%= request. getContextPath()%>/jquery-easyui-1.4.1/jquery.easyui.min.js" ></script>
<script src="<%= request. getContextPath()%>/jquery-easyui-1.4.1/locale/easyui-lang-zh_CN.js" ></script>
<link rel="stylesheet" type="text/css" href="<%= request. getContextPath()%>/jquery-easyui-1.4.1/themes/gray/easyui.css"/>
<link rel="stylesheet" href="<%= request. getContextPath()%>/jquery-easyui-1.4.1/themes/icon.css" />
<script src="<%= request. getContextPath()%>/js/CONST.js" ></script>
<script src="<%= request. getContextPath()%>/js/tools.js" ></script>
<script src="<%= request. getContextPath()%>/js/workAttendance.js" ></script>
<link rel="stylesheet" href="<%= request. getContextPath()%>/css/workAttendance.css" />
<script src="<%= request. getContextPath()%>/js/echarts.min.js"></script>
</head>
<body>
	<div class="head">考勤信息管理</div>
	<div id="kqList" hidden="hidden">${kqList}</div>
	<div id="classList" hidden="hidden">${classList}</div>
	<c:forEach  var="s" items="${ seriesList }">
		<c:if test="${8==s}">
			<div class="selectbox">
				<label>
					<b>班级：</b>
				</label>
				<input id="classCombobox" class="selectCom easyui-combobox" name="dept" data-options="valueField:'code',textField:'className',onSelect:changeClassCombobox" />
				<label>
					<b>学生：</b>
				</label>
				<input id="stuCombobox"  class="easyui-combobox" name="dept" data-options="valueField:'code',textField:'trueName'" />
				<label>
					<b>时间区间：</b>
				</label>
				<input id="beginTime"  type="text" class="easyui-datebox"></input> 
				<input id="endTime"  type="text" class="easyui-datebox"></input> 	
				<a id="btn" onclick="javascript:select()" class="easyui-linkbutton" data-options="iconCls:'icon-search'">查询</a>  
				<a style="margin-left: 0px;" id="btn2" onclick="javascript:re()" class="easyui-linkbutton" data-options="iconCls:'icon-redo'">重置</a>  
				<a style="margin-left: 0px;" id="btn3" onclick="javascript:tb()" class="easyui-linkbutton" data-options="iconCls:'icon-large-chart'">图表</a>  
			</div>
		</c:if>
	</c:forEach>	
	<div title="Tab1" style="padding:20px;" >   
	    <table id="dg" class="easyui-datagrid"  style="width:100%;height:auto"
			data-options="
				iconCls: 'icon-edit',
				singleSelect: true,
				toolbar: '#tb',
				url: '<%= request. getContextPath()%>/attendanceController/loadWorkAttendance.do',
				method: 'post',
				pagination:true,
				pageSize:4, 
				pageList:[4,8,12,16],
				striped:true
			">
			<thead>
				<tr>
					<th hidden="hidden" data-options="field:'code',width:90">code</th>
					<th hidden="hidden" data-options="field:'userCode',width:90">usercode</th>
					<th data-options="field:'uTrueName',width:120">姓名</th>
					<th data-options="field:'time',width:140,editor:'numberbox'">日期</th>
					<th data-options="field:'oneCheck',width:100,editor:'textbox',formatter: function(value,row,index){return kqList(value)}">第一次</th>
					<th data-options="field:'twoCheck',width:100,editor:'textbox',formatter: function(value,row,index){return kqList(value)}">第二次</th>
					<th data-options="field:'threeCheck',width:100,editor:'textbox',formatter: function(value,row,index){return kqList(value)}">第三次</th>
					<th data-options="field:'fourCheck',width:100,editor:'textbox',formatter: function(value,row,index){return kqList(value)}">第四次</th>
					<th data-options="field:'fiveCheck',width:100,editor:'textbox',formatter: function(value,row,index){return kqList(value)}">第五次</th>
					<th data-options="field:'sixCheck',width:100,editor:'textbox',formatter: function(value,row,index){return kqList(value)}">第六次</th>
					<th data-options="field:'score',width:60,editor:'textbox'">成绩</th>
				</tr>
			</thead>
		</table>

		<div id="tb" style="height:auto">
			<c:forEach  var="s" items="${ seriesList }">
				<c:if test="${1==s}">
					<a id="addbtn"  href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-add',plain:true" onclick="append()">增加</a>
				</c:if>
			</c:forEach>	
			<c:forEach  var="s" items="${ seriesList }">
				<c:if test="${4==s}">
					<a id="updatebtn"  href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-add',plain:true" onclick="update()">修改</a>
				</c:if>
			</c:forEach>
			<c:forEach  var="s" items="${ seriesList }">
				<c:if test="${2==s}">
					<a id="removebtn" href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-remove',plain:true" onclick="removeit()">删除</a>
				</c:if>
			</c:forEach>	
		</div>
	
		<!--设置dialog的样子-->
		<div id="diadiv">
			<input hidden="hidden" id="codeSelect" type="text" />
			<div class="dialine">姓&nbsp;&nbsp;&nbsp;名：<input id="userClassSelect" class="userClassSelect easyui-combobox" name="dept"  required="required" data-options="valueField:'code',textField:'trueName'" /></div>
			<div class="dialine">日&nbsp;&nbsp;&nbsp;期：<input id="time" type="text" class="time easyui-datebox" required="required"></input> </div>
			<div class="dialine">
				第一次：<input  required="required" style="display: inline-block;width: 80px" id="oneSelect" class="easyui-combobox" name="dept" data-options="valueField:'code',textField:'name'" />
				第二次：<input  required="required" style="display: inline-block;width: 80px" id="twoSelect" class="easyui-combobox" name="dept" data-options="valueField:'code',textField:'name'" />
				第三次：<input  required="required" style="display: inline-block;width: 80px" id="threeSelect" class="easyui-combobox" name="dept" data-options="valueField:'code',textField:'name'" />
			</div>
			<div class="dialine">
				第四次：<input  required="required" style="display: inline-block;width: 80px" id="fourSelect" class="easyui-combobox" name="dept" data-options="valueField:'code',textField:'name'" />
				第五次：<input  required="required" style="display: inline-block;width: 80px" id="fiveSelect" class="easyui-combobox" name="dept" data-options="valueField:'code',textField:'name'" />
				第六次：<input  required="required" style="display: inline-block;width: 80px" id="sixSelect" class="easyui-combobox" name="dept" data-options="valueField:'code',textField:'name'" />
			</div>
			<div class="dialine">成&nbsp;&nbsp;&nbsp;绩：<input id="score"  required="required" style="width: 80px" type="text" class="easyui-numberbox" data-options="min:0,precision:2,max:100,min:0"></input></div>  
			<div class="dialine" style="margin-left: 160px ">
				<a id="tijiao" class="easyui-linkbutton" data-options="">确定</a>
				<a style="margin-left: 10px" id="btn2" onclick="javascript:$('#diadiv').window('close')" class="easyui-linkbutton" data-options="">关闭</a>
			</div>				
		</div>
		
		<!--设置dialog的样子-->
		<div id="tbdiv">
			<div id="main" style="width: 750px;height:350px;"></div>
		</div>
	
	</div>
	
</body>
</html>