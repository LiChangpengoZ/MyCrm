$(function() {
	diadiv();
	loadUserSelect();
	loadClassCombobox();
})

//请假的对应
function qjList(value) {
	var jsonstr=$("#qjList").text();
	var qjList=eval('('+jsonstr+')');
	for (var i = 0; i < qjList.length; i++) {
		if(value==qjList[i].code){
			return qjList[i].name;
		}
	}
}

//增加
function append(){				
	//弹出dialog
	$("#diadiv").dialog({
		title: '增加'  	//标题  
	})
	$("#diadiv").window("open");
	$("#codeSelect").val("");
	$("#whySelect").textbox("clear"); 
	$("#beginTimeSelecte").datebox("clear");
	$("#endTimeSelect").datebox("clear");
	insertSubmit();
}

//提交按钮
function insertSubmit() {
	$("#tijiao").unbind();
	$("#tijiao").bind("click",function(){
		var why=$("#whySelect").textbox("getValue");
		var beginTime=$("#beginTimeSelecte").datebox('getValue');
		var endTime=$("#endTimeSelect").datebox('getValue');
		var path=CONST.WEB_URL+"/attendanceController/insertLeave.do";	
		$.ajax({
			type:"post",
			url:path,
			data:{
				why:why,
				beginTime:beginTime,
				endTime:endTime
			},
			dataType:"text",
			success:function(data){
				$("#dg").datagrid('reload');
				if("1"==data){
					$('#diadiv').window('close')
					Message();
					layer.innerHTML="友情提示：<br/>增加成功";
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
		layer.innerHTML="友情提示：<br/>请选中所要修改的记录";
	}
	if("q1"!=row.status){
		Message();
		layer.innerHTML="友情提示：<br/>已审批的记录不能修改";
	}
	if(row != null && "q1"==row.status){
		//弹出dialog
		$("#diadiv").dialog({
			title: '修改'  	//标题  
		})
		$("#diadiv").window("open");
		$("#codeSelect").val(row.code);
		$("#userCodeSelect").val(row.userCode);
		$("#whySelect").textbox('setValue',row.why);
		$("#beginTimeSelecte").datebox('setValue',row.beginTime);
		$("#endTimeSelect").datebox('setValue',row.endTime);
		updateSubmit();
	}	
}

//更新的ajax
function updateSubmit() {
	$("#tijiao").unbind();
	$("#tijiao").bind("click",function(){
		var code=$("#codeSelect").val();
		var userCode=$("#userCodeSelect").val();
		var why=$("#whySelect").textbox("getValue");
		var beginTime=$("#beginTimeSelecte").datebox('getValue');
		var endTime=$("#endTimeSelect").datebox('getValue');
		var path=CONST.WEB_URL+"/attendanceController/updateLeave.do";	
		$.ajax({
			type:"post",
			url:path,
			data:{
				code:code,
				userCode:userCode,
				why:why,
				beginTime:beginTime,
				endTime:endTime
			},
			dataType:"text",
			success:function(data){
				$("#dg").datagrid('reload');
				if("1"==data){
					$('#diadiv').window('close')
					Message();
					layer.innerHTML="友情提示：<br/>修改成功";
				} else{
					Message();
					layer.innerHTML="友情提示：<br/>修改失败";
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
	if("q1"!=row.status){
		Message();
		layer.innerHTML="友情提示：<br/>已审批的记录不能修改";
	}
	if(row != null && "q1"==row.status){
		$.messager.confirm('确认','您确认想要删除记录吗？',function(r){    
		    if (r){    
		    	deleteSubmit(row);	
		    }    
		});  
	}
}

//删除的ajax
function deleteSubmit(row) {
	var code=row.code;
	var path=CONST.WEB_URL+"/attendanceController/deleteLeave.do";	
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
		//title: 'My Dialog',  	//标题  
	    width: 430,    
	    height: 250,    
	    closed: true,    		//默认关闭状态
	    cache: false,    		//不设置缓存
	    href: '',  				//加载页面
	    modal: true   			//模态，弹出这个框的时候无法操作其他的事情		
	});		
}



//加载用户的下拉框
function loadUserSelect() {
	var jsonstr=$("#classList").text();
	var classList=eval('('+jsonstr+')');
	var uUserList=classList[0].uUserList;
	$("#userClassSelect").combobox("loadData",uUserList); 
}

//加载班级的下拉框
function loadClassCombobox() {
	var jsonstr=$("#classList").text();
	var classList=eval('('+jsonstr+')');
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
var userCode="";
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
	var path=CONST.WEB_URL+"/attendanceController/loadWorkAttendance.do";	
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

//按钮列
function formatOper(value,row,index){  
    return '<a href="javascript:passOrBack(\'tg\')">通过</a>&nbsp;&nbsp;/&nbsp;&nbsp;<a href="javascript:passOrBack(\'bh\')">驳回</a>';  
}  

//通过或者驳回
function passOrBack(passOrBack) {
	var row= $('#dg').datagrid('getSelected');
	var code=row.code;
	var path=CONST.WEB_URL+"/attendanceController/updateLeave.do";	
	$.ajax({
		type:"post",
		url:path,
		data:{
			code:code,
			passOrBack:passOrBack
		},
		dataType:"text",
		success:function(data){
			$("#dg").datagrid('reload');
			if("1"==data){
				$('#diadiv').window('close')
				Message();
				layer.innerHTML="友情提示：<br/>操作成功";
			} else{
				Message();
				layer.innerHTML="友情提示：<br/>操作失败";
			}
		}
	});	
	
}

