<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%> 
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%> 
<!DOCTYPE html >
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>项目情况管理</title>
<script type="text/javascript" src="<%= request. getContextPath()%>/jquery-easyui-1.4.1/jquery.min.js"></script>
<script src="<%= request. getContextPath()%>/jquery-easyui-1.4.1/jquery.easyui.min.js" ></script>
<script src="<%= request. getContextPath()%>/jquery-easyui-1.4.1/locale/easyui-lang-zh_CN.js" ></script>
<link rel="stylesheet" type="text/css" href="<%= request. getContextPath()%>/jquery-easyui-1.4.1/themes/gray/easyui.css"/>
<link rel="stylesheet" href="<%= request. getContextPath()%>/jquery-easyui-1.4.1/themes/icon.css" />
<script src="<%= request. getContextPath()%>/js/CONST.js" ></script>
<script src="<%= request. getContextPath()%>/js/tools.js" ></script>
<script src="<%= request. getContextPath()%>/js/project.js" ></script>
<link rel="stylesheet" href="<%= request. getContextPath()%>/css/project.css" />
</head>
<body>
	<div id="zyList" hidden="hidden">${zyList}</div>
	<div id="classList" hidden="hidden">${classList}</div>
	<div id="userJ" hidden="hidden">${userJ}</div>
	<div class="head">项目情况管理</div>
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
			</div>
		</c:if>
	</c:forEach>	
	<div title="Tab1" style="padding:20px;" >   
	    <table id="dg" class="easyui-datagrid"  style="width:100%;height:auto"
			data-options="
				iconCls: 'icon-edit',
				singleSelect: true,
				toolbar: '#tb',
				url: '<%= request. getContextPath()%>/workController/loadProject.do',
				method: 'post',
				pagination:true,
				pageSize:4, 
				pageList:[4,8,12,16],
				striped:true
			">
			<thead>
				<tr>
					<th hidden="hidden" data-options="field:'code',width:90">code</th>
					<th hidden="hidden" data-options="field:'userCode',width:90">学生code</th>
					<th data-options="field:'time',width:100">日期</th>
					<th data-options="field:'trueName',width:80,formatter:function(value,row){return row.uUser.trueName}">姓名</th>
					<th data-options="field:'projectName',width:200">项目名称</th>
					<th data-options="field:'studentScore',width:75">自评成绩</th>
					<th data-options="field:'studentGrade',width:75,formatter: function(value,row,index){return zyList(value)}">自评等级</th>
					<th data-options="field:'teacherScore',width:75">师评成绩</th>
					<th data-options="field:'grade',width:75,formatter: function(value,row,index){return zyList(value)}">师评等级</th>
					<th data-options="field:'allScore',width:75">总成绩</th>
					<th data-options="field:'descr',width:150">备注</th>
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
					<a id="updatebtn"  href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-add',plain:true" onclick="update()">评分</a>
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
			<div class="dialine">
				姓名：<input disabled="disabled" id="userNameSelecte" type="text" class="easyui-textbox" required="required"></input>
			</div>
			<div class="dialine">
				时间：<input id="timeSelecte" type="text" class="easyui-datebox" required="required"></input>
			</div>  
			<div class="dialine">
				项目：<input id="projectNameSelecte" type="text" class="easyui-textbox" required="required"></input>
			</div>
			<div class="dialine">备注：<input id="descrSelect" type="text" data-options="multiline:true"  class="time easyui-textbox" required="required" style="width:300px;height:50px"></input> </div>
			<div class="dialine" style="margin-left: 160px;margin-top: 20px;">
				<a id="tijiao" class="easyui-linkbutton" data-options="">确定</a>
				<a style="margin-left: 10px" id="btn2" onclick="javascript:$('#diadiv').window('close')" class="easyui-linkbutton" data-options="">关闭</a>
			</div>	
		</div>
		
		<!--设置dialog的样子-->
		<div id="diadiv2">
			<input hidden="hidden" id="codeSelect2" type="text" />	
			<div class="dialine">
				姓名：<input disabled="disabled" id="userNameSelecte2" type="text" class="easyui-textbox" required="required"></input>
				&nbsp;&nbsp;&nbsp;&nbsp;成绩：<input id="score2"  required="required" style="width: 80px" type="text" class="easyui-numberbox" data-options="min:0,precision:2,max:100,min:0"></input>
			</div>  
			<div class="dialine">
				评级：<input  required="required" style="display: inline-block;width: 80px" id="zySelect" class="easyui-combobox" name="dept" data-options="valueField:'code',textField:'name'" />
			</div>
			<div class="dialine">其他：<input id="descrSelect" type="text" data-options="multiline:true"  class="time easyui-textbox" required="required" style="width:300px;height:50px"></input> </div>
			<div class="dialine" style="margin-left: 160px;margin-top: 20px;">
				<a id="tijiao2" class="easyui-linkbutton" data-options="">确定</a>
				<a style="margin-left: 10px" id="btn2" onclick="javascript:$('#diadiv').window('close')" class="easyui-linkbutton" data-options="">关闭</a>
			</div>	
		</div>
	
	</div>
	
</body>
</html>