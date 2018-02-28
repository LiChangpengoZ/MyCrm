<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%> 
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%> 
<!DOCTYPE html >
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>发布作业管理</title>
<script type="text/javascript" src="<%= request. getContextPath()%>/jquery-easyui-1.4.1/jquery.min.js"></script>
<script src="<%= request. getContextPath()%>/jquery-easyui-1.4.1/jquery.easyui.min.js" ></script>
<script src="<%= request. getContextPath()%>/jquery-easyui-1.4.1/locale/easyui-lang-zh_CN.js" ></script>
<link rel="stylesheet" type="text/css" href="<%= request. getContextPath()%>/jquery-easyui-1.4.1/themes/gray/easyui.css"/>
<link rel="stylesheet" href="<%= request. getContextPath()%>/jquery-easyui-1.4.1/themes/icon.css" />
<script src="<%= request. getContextPath()%>/js/CONST.js" ></script>
<script src="<%= request. getContextPath()%>/js/tools.js" ></script>
<script src="<%= request. getContextPath()%>/js/putWork.js" ></script>
<link rel="stylesheet" href="<%= request. getContextPath()%>/css/putWork.css" />
</head>
<body>
	<div class="head">发布作业管理</div>
	<div title="Tab1" style="padding:20px;" >   
	    <table id="dg" class="easyui-datagrid"  style="width:100%;height:auto"
			data-options="
				iconCls: 'icon-edit',
				singleSelect: true,
				toolbar: '#tb',
				url: '<%= request. getContextPath()%>/workController/loadPutWork.do',
				method: 'post',
				pagination:true,
				pageSize:4, 
				pageList:[4,8,12,16],
				striped:true
			">
			<thead>
				<tr>
					<th hidden="hidden" data-options="field:'code',width:90">code</th>
					<th hidden="hidden" data-options="field:'teacherCode',width:90">teacherCode</th>
					<th hidden="hidden" data-options="field:'type',width:90">type</th>
					<th data-options="field:'time',width:130">日期</th>
					<th data-options="field:'tm',width:360,formatter:formatCellTooltip">题目</th>
					<th data-options="field:'gradeStandard',width:330,formatter:formatCellTooltip">评分标准</th>
					<th data-options="field:'descr',width:130">备注</th>
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
			<input hidden="hidden" id="	teacherCodeSelect" type="text" />
			<div class="dialine">时间：<input id="timeSelecte" type="text" class="time easyui-datebox" required="required"></input> </div>	
			<div class="dialine">题目：<input id="tmSelect" type="text" data-options="multiline:true"  class="time easyui-textbox" required="required" style="width:300px;height:50px"></input> </div>
			<div class="dialine">标准：<input id="gradeStandardSelect" type="text" data-options="multiline:true"  class="time easyui-textbox" required="required" style="width:300px;height:50px"></input> </div>
			<div class="dialine">描述：<input id="descrSelect" type="text" data-options="multiline:true"  class="time easyui-textbox" required="required" style="width:300px;height:50px"></input> </div>
			<div class="dialine" style="margin-left: 160px;margin-top: 20px;">
				<a id="tijiao" class="easyui-linkbutton" data-options="">确定</a>
				<a style="margin-left: 10px" id="btn2" onclick="javascript:$('#diadiv').window('close')" class="easyui-linkbutton" data-options="">关闭</a>
			</div>	
		</div>
	
	</div>
	
</body>
</html>