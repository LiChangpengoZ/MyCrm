<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%> 
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%> 
<!DOCTYPE html >
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>请假信息管理</title>
<script type="text/javascript" src="<%= request. getContextPath()%>/jquery-easyui-1.4.1/jquery.min.js"></script>
<script src="<%= request. getContextPath()%>/jquery-easyui-1.4.1/jquery.easyui.min.js" ></script>
<script src="<%= request. getContextPath()%>/jquery-easyui-1.4.1/locale/easyui-lang-zh_CN.js" ></script>
<link rel="stylesheet" type="text/css" href="<%= request. getContextPath()%>/jquery-easyui-1.4.1/themes/gray/easyui.css"/>
<link rel="stylesheet" href="<%= request. getContextPath()%>/jquery-easyui-1.4.1/themes/icon.css" />
<script src="<%= request. getContextPath()%>/js/CONST.js" ></script>
<script src="<%= request. getContextPath()%>/js/tools.js" ></script>
<script src="<%= request. getContextPath()%>/js/leave.js" ></script>
<link rel="stylesheet" href="<%= request. getContextPath()%>/css/leave.css" />
</head>
<body>
	<div class="head">考勤信息管理</div>
	<div id="qjList" hidden="hidden">${qjList}</div>
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
			</div>
		</c:if>
	</c:forEach>	
	<div title="Tab1" style="padding:20px;" >   
	    <table id="dg" class="easyui-datagrid"  style="width:100%;height:auto"
			data-options="
				iconCls: 'icon-edit',
				singleSelect: true,
				toolbar: '#tb',
				url: '<%= request. getContextPath()%>/attendanceController/loadLeave.do',
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
					<th data-options="field:'why',width:150">事由</th>
					<th data-options="field:'status',width:140,formatter: function(value,row,index){return qjList(value)}">请假进度</th>
					<th data-options="field:'time',width:100">申请时间</th>
					<th data-options="field:'beginTime',width:100">起始时间</th>
					<th data-options="field:'endTime',width:100">截止时间</th>
					
					<c:forEach  var="s" items="${ allPerList }">
						<c:if test="${'cxbj'==s.code || 'cxqb'==s.code}">
							<th data-options="field:'_operate',width:130,value:'123',align:'center',formatter:formatOper">操作</th>
						</c:if>
					</c:forEach>	
					
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
			<input hidden="hidden" id="userCodeSelect" type="text" />
			<div class="dialine">事&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;由：<input id="whySelect" type="text" data-options="multiline:true"  class="time easyui-textbox" required="required" style="width:300px;height:50px"></input> </div>
			<div class="dialine">起始日期：<input id="beginTimeSelecte" type="text" class="time easyui-datebox" required="required"></input> </div>
			<div class="dialine">截止日期：<input id="endTimeSelect" type="text" class="time easyui-datebox" required="required"></input> </div>
			<div class="dialine" style="margin-left: 160px;margin-top: 20px;">
				<a id="tijiao" class="easyui-linkbutton" data-options="">确定</a>
				<a style="margin-left: 10px" id="btn2" onclick="javascript:$('#diadiv').window('close')" class="easyui-linkbutton" data-options="">关闭</a>
			</div>				
		</div>
	
	</div>
	
</body>
</html>