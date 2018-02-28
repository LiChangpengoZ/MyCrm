$(function() {
	diadiv();
	loadKqSelect();
	loadUserSelect();
	loadClassCombobox();
})

//图表
function tb() {
	$("#tbdiv").window("open");
	
	 var myChart = echarts.init(document.getElementById('main'));
     var option = {
     		   title : {
     			    text : ''
     			   },
     			   legend : {
     			    data : [ '' ]
     			   },
     			   xAxis : {
     			    data : []
     			   },
     			   yAxis : {},
     			  series : ''
//     			  series : [{"data":[[3,0,1,0,0]],"name":"第一次","type":"bar"},{"data":[[3,0,1,0,0]],"name":"第二次","type":"bar"},{"data":[[3,0,1,0,0]],"name":"第三次","type":"bar"},{"data":[[3,0,1,0,0]],"name":"第四次","type":"bar"},{"data":[[3,0,1,0,0]],"name":"第五次","type":"bar"},{"data":[[3,1,0,0,0]],"name":"第六次","type":"bar"}]
//     			  series : [
//     				  {data:[[3,0,1,0,0]],name:"第一次",type:"bar"},
//     				  {data:[[3,0,1,0,0]],name:"第二次",type:"bar"},
//     				  {data:[[3,0,1,0,0]],name:"第三次",type:"bar"},
//     				  {data:[[3,0,1,0,0]],name:"第四次",type:"bar"},
//     				  {data:[[3,0,1,0,0]],name:"第五次",type:"bar"},
//     				  {data:[[3,1,0,0,0]],name:"第六次",type:"bar"}
//     				  ]	  
     			  }

     // 使用刚指定的配置项和数据显示图表。
     myChart.setOption(option);
     myChart.showLoading();// 加载动画
	
	 $.ajax({
         url:CONST.WEB_URL+"/attendanceController/showBg.do",
         type:"POST",
         dataType:"json",
         contentType: 'application/x-www-form-urlencoded; charset=UTF-8',//防止乱码
         success:function(data){
        	 var seriess= data.series;
        	 
             myChart.hideLoading();
             var option = {
             		title: {
                      text: data.text
                  },
                  tooltip: {},
                  legend : {
                      data :  data.legend_data
                     },
             	    xAxis : {
             	        data : data.xAxis_data
             	       }
                     ,
             	      series : seriess

             	      }

             // 使用刚指定的配置项和数据显示图表。
             myChart.setOption(option);
         }
     });
	
}

//考勤的对应
function kqList(value) {
	var jsonstr=$("#kqList").text();
	var kqList=eval('('+jsonstr+')');
	for (var i = 0; i < kqList.length; i++) {
		if(value==kqList[i].code){
			return kqList[i].name;
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
	$("#userClassSelect").combobox("clear"); 
	$("#time").datebox("clear"); 
	$("#oneSelect").combobox("clear"); 
	$("#twoSelect").combobox("clear"); 
	$("#threeSelect").combobox("clear"); 
	$("#fourSelect").combobox("clear"); 
	$("#fiveSelect").combobox("clear"); 
	$("#sixSelect").combobox("clear"); 
	$("#score").numberbox("clear"); 
	insertSubmit();
}

//提交按钮
function insertSubmit() {
	$("#tijiao").unbind();
	$("#tijiao").bind("click",function(){
		var userCode=$('#userClassSelect').combobox('getValue');
		var time=$("#time").datebox("getValue");
		var oneCheck=$("#oneSelect").combobox('getValue');
		var twoCheck=$("#twoSelect").combobox('getValue');
		var threeCheck=$("#threeSelect").combobox('getValue');
		var fourCheck=$("#fourSelect").combobox('getValue');
		var fiveCheck=$("#fiveSelect").combobox('getValue');
		var sixCheck=$("#sixSelect").combobox('getValue');
		var score=$("#score").val();
		var path=CONST.WEB_URL+"/attendanceController/insertWorkAttendance.do";	
		$.ajax({
			type:"post",
			url:path,
			data:{
				userCode:userCode,
				time:time,
				oneCheck:oneCheck,
				twoCheck:twoCheck,
				threeCheck:threeCheck,
				fourCheck:fourCheck,
				fiveCheck:fiveCheck,
				sixCheck:sixCheck,
				score:score
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
		layer.innerHTML="友情提示：<br/>请选中所要修改的记录";
	}
	if(row != null){
		//弹出dialog
		$("#diadiv").dialog({
			title: '修改'  	//标题  
		})
		$("#diadiv").window("open");
		$("#userClassSelect").combobox('setValue',row.userCode);
		$("#time").datebox('setValue',row.time);
		$("#oneSelect").combobox('setValue',row.oneCheck);
		$("#twoSelect").combobox('setValue',row.twoCheck);
		$("#threeSelect").combobox('setValue',row.threeCheck);
		$("#fourSelect").combobox('setValue',row.fourCheck);
		$("#fiveSelect").combobox('setValue',row.fiveCheck);
		$("#sixSelect").combobox('setValue',row.sixCheck);
		$("#score").textbox('setValue',row.score);
		$("#codeSelect").val(row.code)
		updateSubmit();
	}	
}

//更新的ajax
function updateSubmit() {
	$("#tijiao").unbind();
	$("#tijiao").bind("click",function(){
		var code=$("#codeSelect").val();
		var userCode=$('#userClassSelect').combobox('getValue');
		var time=$("#time").datebox("getValue");
		var oneCheck=$("#oneSelect").combobox('getValue');
		var twoCheck=$("#twoSelect").combobox('getValue');
		var threeCheck=$("#threeSelect").combobox('getValue');
		var fourCheck=$("#fourSelect").combobox('getValue');
		var fiveCheck=$("#fiveSelect").combobox('getValue');
		var sixCheck=$("#sixSelect").combobox('getValue');
		var score=$("#score").val();
		var path=CONST.WEB_URL+"/attendanceController/updateWorkAttendance.do";	
		$.ajax({
			type:"post",
			url:path,
			data:{
				code:code,
				userCode:userCode,
				time:time,
				oneCheck:oneCheck,
				twoCheck:twoCheck,
				threeCheck:threeCheck,
				fourCheck:fourCheck,
				fiveCheck:fiveCheck,
				sixCheck:sixCheck,
				score:score
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
	var path=CONST.WEB_URL+"/attendanceController/deleteWorkAttendance.do";	
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
	
	$("#tbdiv").dialog({
		title: '图表',  	//标题  
	    width: 800,    
	    height: 400,    
	    closed: true,    		//默认关闭状态
	    cache: false,    		//不设置缓存
	    href: '',  				//加载页面
	    modal: true   			//模态，弹出这个框的时候无法操作其他的事情		
	});		
	
}

//加载考勤的下拉框
function loadKqSelect() {
	var jsonstr=$("#kqList").text();
	var kqList=eval('('+jsonstr+')');
	$("#oneSelect").combobox("loadData",kqList); 
	$("#twoSelect").combobox("loadData",kqList); 
	$("#threeSelect").combobox("loadData",kqList); 
	$("#fourSelect").combobox("loadData",kqList); 
	$("#fiveSelect").combobox("loadData",kqList); 
	$("#sixSelect").combobox("loadData",kqList); 
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




