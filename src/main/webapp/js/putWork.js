$(function() {
	diadiv();
})

//增加
function append(){				
	//弹出dialog
	$("#diadiv").dialog({
		title: '增加'  	//标题  
	})
	$("#diadiv").window("open");
	$("#codeSelect").val("");
	$("#teacherCodeSelect").val("");
	$("#timeSelecte").datebox("clear"); 
	$("#tmSelect").textbox("clear"); 
	$("#gradeStandardSelect").textbox("clear"); 
	$("#descrSelect").textbox("clear"); 
	insertSubmit();
}

//提交按钮
function insertSubmit() {
	$("#tijiao").unbind();
	$("#tijiao").bind("click",function(){
		var time=$("#timeSelecte").datebox("getValue"); 
		var tm=$("#tmSelect").textbox("getValue"); 
		var gradeStandard=$("#gradeStandardSelect").textbox("getValue"); 
		var descr=$("#descrSelect").textbox("getValue"); 
		var path=CONST.WEB_URL+"/workController/insertPutWork.do";	
		$.ajax({
			type:"post",
			url:path,
			data:{
				time:time,
				tm:tm,
				gradeStandard:gradeStandard,
				descr:descr
			},
			dataType:"text",
			success:function(data){
				$("#dg").datagrid('reload');
				if("1"==data){
					$('#diadiv').window('close')
					Message();
					layer.innerHTML="友情提示：<br/>增加成功";
				} else{
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
	if(row != null){
		//弹出dialog
		$("#diadiv").dialog({
			title: '修改'  	//标题  
		})
	
		$("#diadiv").window("open");
		$("#codeSelect").val(row.code);
		$("#teacherCodeSelect").val(row.teacherCode);
		$("#timeSelecte").datebox('setValue',row.time);
		$("#tmSelect").textbox('setValue',row.tm);
		$("#gradeStandardSelect").textbox('setValue',row.gradeStandard);
		$("#descrSelect").textbox('setValue',row.descr);
		updateSubmit();
	}	
}

//更新的ajax
function updateSubmit() {
	$("#tijiao").unbind();
	$("#tijiao").bind("click",function(){
		var code= $("#codeSelect").val();
		var teacherCode= $("#teacherCodeSelect").val();
		var time=$("#timeSelecte").datebox('getValue');
		var tm=$("#tmSelect").textbox('getValue');
		var gradeStandard= $("#gradeStandardSelect").textbox('getValue');
		var descr=$("#descrSelect").textbox('getValue');
	
		var path=CONST.WEB_URL+"/workController/updatePutWork.do";	
		$.ajax({
			type:"post",
			url:path,
			data:{
				code:code,
				teacherCode:teacherCode,
				time:time,
				tm:tm,
				gradeStandard:gradeStandard,
				descr:descr
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
	if(row != null){
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
	var path=CONST.WEB_URL+"/workController/deletePutWork.do";	
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
	    height: 320,    
	    closed: true,    		//默认关闭状态
	    cache: false,    		//不设置缓存
	    href: '',  				//加载页面
	    modal: true   			//模态，弹出这个框的时候无法操作其他的事情		
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

function formatCellTooltip(value, row, index) {
	var content = '';
	var abValue = value +'';
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

