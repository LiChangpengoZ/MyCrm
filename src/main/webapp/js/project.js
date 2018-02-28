$(function() {
	diadiv();
	loadZyList();
//	loadUserSelect();
	loadClassCombobox();
})

//考勤的对应
function zyList(value) {
	var jsonstr=$("#zyList").text();
	var zyList=eval('('+jsonstr+')');
	for (var i = 0; i < zyList.length; i++) {
		if(value==zyList[i].code){
			return zyList[i].name;
		}
	}
}

//加载作业标准
function loadZyList() {
	var jsonstr=$("#zyList").text();
	var zyList=eval('('+jsonstr+')');
	$("#zySelect").combobox("loadData",zyList); 
}

//增加
function append(){
	//弹出dialog
	$("#diadiv").dialog({
		title: '增加'  	//标题  
	})
	$("#diadiv").window("open");
	$("#codeSelect").val("");
	$("#userNameSelecte").textbox("clear"); 
	$("#timeSelecte").datebox("clear"); 
	$("#projectNameSelecte").textbox("clear"); 
	$("#descrSelect").textbox("clear"); 
	
	var jsonstr=$("#userJ").text();
	var userJ=eval('('+jsonstr+')');
	$("#userNameSelecte").textbox('setValue',userJ.trueName);
	
	insertSubmit();
}

//提交按钮
function insertSubmit() {
	$("#tijiao").unbind();
	$("#tijiao").bind("click",function(){
		
		var time=$("#timeSelecte").datebox("getValue");
		var projectName=$("#projectNameSelecte").textbox('getValue');
		var descr=$("#descrSelect").textbox('getValue');
		
		var path=CONST.WEB_URL+"/workController/insertProject.do";	
		$.ajax({
			type:"post",
			url:path,
			data:{
				time:time,
				projectName:projectName,
				descr:descr
			},
			dataType:"text",
			success:function(data){
				$("#dg").datagrid('reload');
				if("1"==data){
					$('#diadiv').window('close')
					Message();
					layer.innerHTML="友情提示：<br/>增加成功";
				} else if("-2"==data){
					Message();
					layer.innerHTML="友情提示：<br/>该考勤已存在，请重新选择";	
				}else{
					Message();
					layer.innerHTML="友情提示：<br/>增加失败";
				}
			}
		});	
	})
}


//修改
function update(){				
	var row= $('#dg').datagrid('getSelected');
	if(row==null){
		Message();
		layer.innerHTML="友情提示：<br/>请选中所要评分的记录";
	}
	if(row != null){
		//弹出dialog
		$("#diadiv2").dialog({
			title: '评分'  	//标题  
		})
		$("#diadiv2").window("open");
		$("#codeSelect2").val(row.code)
		$("#userNameSelecte2").textbox('setValue',row.uUser.trueName);
		updateSubmit();
	}	
}

//更新的ajax
function updateSubmit() {
	$("#tijiao2").unbind();
	$("#tijiao2").bind("click",function(){
		var code=$("#codeSelect2").val();
		var score=$("#score2").val();
		var grades=$("#zySelect").combobox('getValue');
		var path=CONST.WEB_URL+"/workController/updateProject.do";	
		$.ajax({
			type:"post",
			url:path,
			data:{
				code:code,
				score:score,
				grades:grades
			},
			dataType:"text",
			success:function(data){
				$("#dg").datagrid('reload');
				if("1"==data){
					$('#diadiv2').window('close')
					Message();
					layer.innerHTML="友情提示：<br/>评分成功";
				} else{
					Message();
					layer.innerHTML="友情提示：<br/>评分失败";
				}
			}
		});	
	})
}

//删除
function removeit(){
	var row= $('#dg').datagrid('getSelected');
	if(row==null){
		Message();
		layer.innerHTML="友情提示：<br/>请选中所要删除的记录";
	}
	if(row != null){
		if(row.teacherScore != ""){
			Message();
			layer.innerHTML="友情提示：<br/>教师评价过的项目无法进行删除操作";
		}else{
			$.messager.confirm('确认','您确认想要删除记录吗？',function(r){    
				if (r){    
					deleteSubmit(row);	
				}    
			});  
		}
		
	}
}

//删除的ajax
function deleteSubmit(row) {
	var code=row.code;
	var path=CONST.WEB_URL+"/workController/deleteProject.do";	
	$.ajax({
		type:"post",
		url:path,
		data:{
			code:code
		},
		dataType:"text",
		success:function(data){
			$("#dg").datagrid('reload');
			if("1"==data){
				$('#diadiv').window('close')
				Message();
				layer.innerHTML="友情提示：<br/>删除成功";
			} else{
				Message();
				layer.innerHTML="友情提示：<br/>删除失败";
			}
		}
	});	
}

function diadiv() {
	$("#diadiv").dialog({
		title: 'My Dialog',  	//标题  
	    width: 430,    
	    height: 250,    
	    closed: true,    		//默认关闭状态
	    cache: false,    		//不设置缓存
	    href: '',  				//加载页面
	    modal: true   			//模态，弹出这个框的时候无法操作其他的事情		
	});	
	
	$("#diadiv2").dialog({
		title: 'My Dialog',  	//标题  
	    width: 430,    
	    height: 250,    
	    closed: true,    		//默认关闭状态
	    cache: false,    		//不设置缓存
	    href: '',  				//加载页面
	    modal: true   			//模态，弹出这个框的时候无法操作其他的事情		
	});	
}


////加载用户的下拉框
//function loadUserSelect() {
//	var jsonstr=$("#classList").text();
//	var classList=eval('('+jsonstr+')');
//	var uUserList=classList[0].uUserList;
//	$("#userClassSelect").combobox("loadData",uUserList); 
//}

//加载班级的下拉框
function loadClassCombobox() {
	var jsonstr1=$("#classList").text();
	var classList=eval('('+jsonstr1+')');
	$("#classCombobox").combobox("loadData",classList);
}
//班级下拉框的改变 事件
function changeClassCombobox() {
	$("#stuCombobox").combobox("clear"); 
	$("#userClassSelect").combobox("clear");
	var classCombobox=$("#classCombobox").combobox('getValue')
	var jsonstr=$("#classList").text();
	var classList=eval('('+jsonstr+')');
	for (var i = 0; i < classList.length; i++) {
		if(classList[i].code==classCombobox){
			var uUserList=classList[i].uUserList;
			$("#stuCombobox").combobox("loadData",uUserList);
			$("#userClassSelect").combobox("loadData",uUserList); 
		}
	}
}


var classCode="";
var studentCode="";
var beginTime="";
var endTime="";
//查询
function select() {
	classCode=$('#classCombobox').combobox('getValue');
	userCode=$('#stuCombobox').combobox('getValue');
	beginTime=$("#beginTime").datebox("getValue");
	endTime=$("#endTime").datebox("getValue");
	var pageNumber=$('#dg').datagrid('getPager').data('pagination').options.pageNumber;
	var pageSize=$('#dg').datagrid('getPager').data('pagination').options.pageSize;
	var path=CONST.WEB_URL+"/workController/loadProject.do";	
	$.ajax({
		type:"post",
		url:path,
		data:{
			cClassCode:classCode,
			userCode: userCode,
			sbegintime:beginTime,
			sendtime:endTime,
			page:"1",
			rows:pageSize
		},
		dataType:"json",
		success:function(data){
			$('#dg').datagrid('loadData',data);
			dataGrideQueryParams();
		}
	});	
	
}

//赋值查询条件
function dataGrideQueryParams() {
	$('#dg').datagrid('load',{
		cClassCode:classCode,
		userCode: userCode,
		sbegintime:beginTime,
		sendtime:endTime
	});
}

//重置方法
function re() {
	$("#classCombobox").combobox("clear");
	$("#stuCombobox").combobox("clear"); 
	$("#beginTime").datebox("clear"); 
	$("#endTime").datebox("clear"); 
}

//题目的初始化
function formatCellTooltipForTm(value, row, index) {
	var content = '';
	var value = row.pPutWork.tm + "";
	var abValue = row.pPutWork.tm +'';
	if(value != undefined){
		if(value.length>=22) {
			abValue = value.substring(0,19) + "...";
			content = '<a id="show1" title="' + value + '" >' + abValue + '</a>';     
		}else{
			content = '<a id="show2" title="' + abValue + '" >' + abValue + '</a>';
		} 
		
	}  
	return content;
}
//评分标准的初始化
function formatCellTooltipForGradeStandard(value, row, index) {
	var content = '';
	var value = row.pPutWork.gradeStandard + "";
	var abValue = row.pPutWork.gradeStandard +'';
	if(value != undefined){
		if(value.length>=22) {
			abValue = value.substring(0,19) + "...";
			content = '<a id="show1" title="' + value + '" >' + abValue + '</a>';     
		}else{
			content = '<a id="show2" title="' + abValue + '" >' + abValue + '</a>';
		} 
		
	}  
	return content;
}

