package system.attendance.controller;


import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import basetool.FormatEmpty;
import basetool.FormatDate;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import system.attendance.model.LeaveModel;
import system.attendance.model.WorkAttendanceModel;
import system.attendance.service.LeaveService;
import system.attendance.service.WorkAttendanceService;
import system.user.model.ClassModel;
import system.user.model.DictionaryModel;
import system.user.model.PermissionModel;
import system.user.model.UserModel;
import utils.Constants;
import utils.Tools;

/**
 * 考勤的control
 * @author 李昌鹏
 */
@Controller
@RequestMapping("/attendanceController")
public class AttendanceController {

	@Autowired
	@Qualifier("workAttendanceService")
	WorkAttendanceService<WorkAttendanceModel> workAttendanceService;
	
	@Autowired
	@Qualifier("leaveService")
	LeaveService<LeaveModel> leaveService;
	
	/**
	 * 跳到考勤页的方法
	 * @param session
	 * @return
	 */
	@RequestMapping(value="/toWorkAttendance.do",method = {RequestMethod.GET,RequestMethod.POST})
	public ModelAndView toWorkAttendance(String series,HttpSession session) {
		List<Integer> seriesList= Tools.seriesStr(series);
//		System.out.println("数列："+seriesList);
		
		ModelAndView mav=new ModelAndView();
		JSONArray kqListJ=JSONArray.fromObject(Constants.KQ_LIST);
//		System.out.println(kqListJ.toString());
		@SuppressWarnings("unchecked")
		//权限的集合，如果有查询班级的，就把班级的学生从session中取到，返回页面
		List<PermissionModel> allPerList= (List<PermissionModel>) session.getAttribute("allPerList");
		for (PermissionModel permissionTemp : allPerList) {
			if(Constants.SELECT_CLASS.equals(permissionTemp.getCode()) || Constants.SELECT_ALL.equals(permissionTemp.getCode())) {
				//获取包含学生的班级的集合
				@SuppressWarnings("unchecked")
				List<ClassModel> classList= (List<ClassModel>) session.getAttribute("classList");
				JSONArray classListJ=JSONArray.fromObject(classList);
				mav.addObject("classList", classListJ.toString());
			}
		}
		mav.addObject("seriesList", seriesList);
		mav.addObject("kqList", kqListJ.toString());
		mav.setViewName("attendance/workAttendance");
		return mav;
	}
	
	/**
	 * 考勤页的加载方法
	 * @return
	 */
	@RequestMapping(value="/loadWorkAttendance.do",method = {RequestMethod.GET,RequestMethod.POST},produces="application/text;charset=utf-8")
	@ResponseBody
	public String loadWorkAttendance(String cClassCode,WorkAttendanceModel workAttendance,HttpSession session,String rows,String page) {
		
		WorkAttendanceModel workAttendanceTemp =new WorkAttendanceModel();
		//排序
		workAttendanceTemp.setOrderBy("desc");
		workAttendanceTemp.setSortField("w.time");
		
		//默认查看一周以内的,否则根据传过来的查
		if(workAttendance.getSbegintime()==null) {
//			workAttendanceTemp.setSbegintime(FormatDate.getDateBeforWeek());
		}else {
			workAttendanceTemp.setSbegintime(FormatEmpty.Nvl(workAttendance.getSbegintime()));
		}
		if(workAttendance.getSendtime()==null) {
//			workAttendanceTemp.setSendtime(FormatDate.getDate());
		}else {
			workAttendanceTemp.setSendtime(FormatEmpty.Nvl(workAttendance.getSendtime()));
		}
		//分页,根据传过来的查
		workAttendanceTemp.setPageNumber(Integer.valueOf(FormatEmpty.Nvl(page)));
		workAttendanceTemp.setRowsNumber(Integer.valueOf(FormatEmpty.Nvl(rows)));
		workAttendanceTemp.setBeginNumber((workAttendanceTemp.getPageNumber()-1)*workAttendanceTemp.getRowsNumber());
		//判断权限
		UserModel user=(UserModel) session.getAttribute("user");
		@SuppressWarnings("unchecked")
		List<PermissionModel> allPerList=(List<PermissionModel>) session.getAttribute("allPerList");
		String userCode=FormatEmpty.Nvl(user.getCode());
		
		//如果查询自己的权限，就根据自己的code查询，如果是查询班级的权限，就根据老师的code查询
		Boolean isPer=false;		//判断是否有这三个权限
		for (PermissionModel permissionModel : allPerList) {
			if (Constants.SELECT_MYSELF.equals(permissionModel.getCode())) {
				workAttendanceTemp.setUserCode(userCode);
				isPer=true;
				continue;
			}
			if(Constants.SELECT_CLASS.equals(permissionModel.getCode())) {
				ClassModel classtemp=new ClassModel();
				classtemp.setTeacherCode(userCode);
				classtemp.setCode(cClassCode);
				workAttendanceTemp.setcClass(classtemp);
				workAttendanceTemp.setUserCode(workAttendance.getUserCode());
				isPer=true;
			} 
			if(Constants.SELECT_ALL.equals(permissionModel.getCode())) {
				ClassModel classtemp=new ClassModel();
				classtemp.setCode(cClassCode);
				workAttendanceTemp.setcClass(classtemp);
				workAttendanceTemp.setUserCode(workAttendance.getUserCode());
				isPer=true;
			} 
		}
		if(!isPer) {
			return "-7";
		}
		//查，现在对象中有开始结束时间，分页中的三个，用户code/教师code/没有
		Map<String, Object> jsonMap = new HashMap<>();
		try {
			List<WorkAttendanceModel> workAttendanceList = workAttendanceService.selectJoin(workAttendanceTemp);
			JSONArray workAttendanceListJ=JSONArray.fromObject(workAttendanceList);
//			System.out.println("考勤集合"+workAttendanceListJ);
			//用作表格
			session.setAttribute("workAttendanceListBg",workAttendanceList);
			Integer count=workAttendanceService.selectCount(workAttendanceTemp);
//			System.out.println("考勤总数"+count);
			jsonMap.put("total", count);
			jsonMap.put("rows", workAttendanceList);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		JSONObject workAttendanceListJ=JSONObject.fromObject(jsonMap);
		
		return workAttendanceListJ.toString();
	}
	
	/**
	 * 插入考勤的方法
	 * @return
	 */
	@RequestMapping(value="/insertWorkAttendance.do",method = {RequestMethod.GET,RequestMethod.POST},produces="application/text;charset=utf-8")
	@ResponseBody
	public String insertWorkAttendance(WorkAttendanceModel workAttendance,HttpSession session) {
		WorkAttendanceModel workAttendanceTemp =new WorkAttendanceModel();
		//userCode,time,oneCheck,twoCheck,threeCheck,fourCheck,fiveCheck,sixCheck,score
		workAttendanceTemp.setUserCode(FormatEmpty.Nvl(workAttendance.getUserCode()));
		workAttendanceTemp.setTime(FormatEmpty.Nvl(workAttendance.getTime()));
		workAttendanceTemp.setOneCheck(FormatEmpty.Nvl(workAttendance.getOneCheck()));
		workAttendanceTemp.setTwoCheck(FormatEmpty.Nvl(workAttendance.getTwoCheck()));
		workAttendanceTemp.setThreeCheck(FormatEmpty.Nvl(workAttendance.getThreeCheck()));
		workAttendanceTemp.setFourCheck(FormatEmpty.Nvl(workAttendance.getFourCheck()));
		workAttendanceTemp.setFiveCheck(FormatEmpty.Nvl(workAttendance.getFiveCheck()));
		workAttendanceTemp.setSixCheck(FormatEmpty.Nvl(workAttendance.getSixCheck()));
		workAttendanceTemp.setScore(FormatEmpty.Nvl(workAttendance.getScore()));
		
		//从session中取出
		UserModel userForSession= (UserModel) session.getAttribute("user");
		if(userForSession==null) {
			return "-1";
		}
		//添加基本的 id，code，createtime，updatetime，creator，updater，isdelete，iseffect，descr
		workAttendanceTemp.setId(null);
		workAttendanceTemp.setCode("w"+FormatDate.getDateTimeNoline());
		workAttendanceTemp.setCreatetime(FormatDate.getDateTime());
		workAttendanceTemp.setCreator(FormatEmpty.Nvl(userForSession.getCode()));
		workAttendanceTemp.setIsdelete(Constants.DELETE_N);
		workAttendanceTemp.setIseffect(Constants.EFFECT_Y);
		
		//调
		Integer insertConut=0;
		try {
			//先根据用户code和事件查询是否存在
			WorkAttendanceModel workAttendanceCountTemp=new WorkAttendanceModel(); 
			workAttendanceCountTemp.setTime(FormatEmpty.Nvl(workAttendance.getTime()));
			workAttendanceCountTemp.setUserCode(FormatEmpty.Nvl(workAttendance.getUserCode()));
			Integer reCount= workAttendanceService.selectCountByUserCodeAndTime(workAttendanceCountTemp);
			if(reCount!=0) {
				return "-2";
			}
			insertConut=workAttendanceService.insert(workAttendanceTemp);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return insertConut.toString();
	}
	
	/**
	 * 修改考勤
	 * @param workAttendance
	 * @param session
	 * @return
	 */
	@RequestMapping(value="/updateWorkAttendance.do",method = {RequestMethod.GET,RequestMethod.POST},produces="application/text;charset=utf-8")
	@ResponseBody
	public String updateWorkAttendance(WorkAttendanceModel workAttendance,HttpSession session) {
		WorkAttendanceModel workAttendanceTemp =new WorkAttendanceModel();
		//userCode,time,oneCheck,twoCheck,threeCheck,fourCheck,fiveCheck,sixCheck,score
		workAttendanceTemp.setUserCode(FormatEmpty.Nvl(workAttendance.getUserCode()));
		workAttendanceTemp.setTime(FormatEmpty.Nvl(workAttendance.getTime()));
		workAttendanceTemp.setOneCheck(FormatEmpty.Nvl(workAttendance.getOneCheck()));
		workAttendanceTemp.setTwoCheck(FormatEmpty.Nvl(workAttendance.getTwoCheck()));
		workAttendanceTemp.setThreeCheck(FormatEmpty.Nvl(workAttendance.getThreeCheck()));
		workAttendanceTemp.setFourCheck(FormatEmpty.Nvl(workAttendance.getFourCheck()));
		workAttendanceTemp.setFiveCheck(FormatEmpty.Nvl(workAttendance.getFiveCheck()));
		workAttendanceTemp.setSixCheck(FormatEmpty.Nvl(workAttendance.getSixCheck()));
		workAttendanceTemp.setScore(FormatEmpty.Nvl(workAttendance.getScore()));
		
		//从session中取出
		UserModel userForSession= (UserModel) session.getAttribute("user");
		if(userForSession==null) {
			return "-1";
		}
		
		workAttendanceTemp.setCode(FormatEmpty.Nvl(workAttendance.getCode()));
		workAttendanceTemp.setUpdater(FormatEmpty.Nvl(userForSession.getCode()));
		workAttendanceTemp.setUpdatetime(FormatDate.getDateTime());
		Integer updateConut=0;
		//调
		try {
			updateConut=workAttendanceService.update(workAttendanceTemp);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return updateConut.toString();
	}
	
	/**
	 * 删除考勤
	 * @param workAttendance
	 * @return
	 */
	@RequestMapping(value="/deleteWorkAttendance.do",method = {RequestMethod.GET,RequestMethod.POST},produces="application/text;charset=utf-8")
	@ResponseBody
	public String deleteWorkAttendance(WorkAttendanceModel workAttendance) {
		WorkAttendanceModel workAttendanceTemp=new WorkAttendanceModel();
		workAttendanceTemp.setCode(FormatEmpty.Nvl(workAttendance.getCode()));
		
		Integer deleteCount=0;
		try {
			deleteCount=workAttendanceService.delete(workAttendanceTemp);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return deleteCount.toString();
	}
	
	/**
	 * 跳到请假页的方法
	 * @param session
	 * @return
	 */
	@RequestMapping(value="/toLeave.do",method = {RequestMethod.GET,RequestMethod.POST})
	public ModelAndView toLeave(String series,HttpSession session) {
		List<Integer> seriesList= Tools.seriesStr(series);
//		System.out.println("数列："+seriesList);
		ModelAndView mav=new ModelAndView();
		JSONArray qjListJ=JSONArray.fromObject(Constants.QJ_LIST);
//		System.out.println("请假字典列表："+qjListJ.toString());
		@SuppressWarnings("unchecked")
		//权限的集合，如果有查询班级的，就把班级的学生从session中取到，返回页面
		List<PermissionModel> allPerList= (List<PermissionModel>) session.getAttribute("allPerList");
		for (PermissionModel permissionTemp : allPerList) {
			if(Constants.SELECT_CLASS.equals(permissionTemp.getCode()) || Constants.SELECT_ALL.equals(permissionTemp.getCode())) {
				//获取包含学生的班级的集合
				@SuppressWarnings("unchecked")
				List<ClassModel> classList= (List<ClassModel>) session.getAttribute("classList");
				JSONArray classListJ=JSONArray.fromObject(classList);
//				System.out.println("班级列表（包含学生）："+classListJ.toString());
				mav.addObject("classList", classListJ.toString());
			}
		}
		mav.addObject("seriesList", seriesList);
		mav.addObject("qjList", qjListJ.toString());
		mav.setViewName("attendance/leave");
		return mav;
	}
	
	/**
	 * 请假页的加载方法
	 * @return
	 */
	@RequestMapping(value="/loadLeave.do",method = {RequestMethod.GET,RequestMethod.POST},produces="application/text;charset=utf-8")
	@ResponseBody
	public String loadLeave(String cClassCode,LeaveModel leave,HttpSession session,String rows,String page) {
		
		LeaveModel leaveTmep =new LeaveModel();
		//排序
		leaveTmep.setOrderBy("desc");
		leaveTmep.setSortField("l.time");
		
		//默认查看一周以后的,否则根据传过来的查
		if(leave.getSbegintime()==null) {
//			leaveTmep.setSbegintime(FormatDate.getDate());
		}else {
			leaveTmep.setSbegintime(FormatEmpty.Nvl(leave.getSbegintime()));
		}
		if(leave.getSendtime()==null) {
//			leaveTmep.setSendtime(FormatDate.getDateAfterWeek());
		}else {
			leaveTmep.setSendtime(FormatEmpty.Nvl(leave.getSendtime()));
		}
		//分页,根据传过来的查
		leaveTmep.setPageNumber(Integer.valueOf(FormatEmpty.Nvl(page)));
		leaveTmep.setRowsNumber(Integer.valueOf(FormatEmpty.Nvl(rows)));
		leaveTmep.setBeginNumber((leaveTmep.getPageNumber()-1)*leaveTmep.getRowsNumber());
		//判断权限
		UserModel user=(UserModel) session.getAttribute("user");
		@SuppressWarnings("unchecked")
		List<PermissionModel> allPerList=(List<PermissionModel>) session.getAttribute("allPerList");
		String userCode=FormatEmpty.Nvl(user.getCode());
		
		//如果查询自己的权限，就根据自己的code查询，如果是查询班级的权限，就根据老师的code查询
		for (PermissionModel permissionModel : allPerList) {
			if (Constants.SELECT_MYSELF.equals(permissionModel.getCode())) {
				leaveTmep.setUserCode(userCode);
				continue;
			}
			if(Constants.SELECT_CLASS.equals(permissionModel.getCode())) {
				ClassModel classtemp=new ClassModel();
				classtemp.setTeacherCode(userCode);
				classtemp.setCode(cClassCode);
				leaveTmep.setcClass(classtemp);
				leaveTmep.setUserCode(leave.getUserCode());
				//只能查看审批中的
				leaveTmep.setStatus(Constants.LEAVE_SP);
			} 
			if(Constants.SELECT_ALL.equals(permissionModel.getCode())) {
				ClassModel classtemp=new ClassModel();
				classtemp.setCode(cClassCode);
				leaveTmep.setcClass(classtemp);
				leaveTmep.setUserCode(leave.getUserCode());
				//只查看老师审核通过的
				leaveTmep.setStatus(Constants.LEAVE_JSTG);
			} 
		}
		//查，现在对象中有开始结束时间，分页中的三个，用户code/教师code/没有
		Map<String, Object> jsonMap = new HashMap<>();
		try {
			List<LeaveModel> leaveList = leaveService.selectJoin(leaveTmep);
			JSONArray leaveListJ=JSONArray.fromObject(leaveList);
//			System.out.println("请假集合"+leaveListJ);
			Integer count=leaveService.selectCount(leaveTmep);
//			System.out.println("请假总数"+count);
			jsonMap.put("total", count);
			jsonMap.put("rows", leaveList);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		JSONObject leaveListJ=JSONObject.fromObject(jsonMap);
//		System.out.println("请假的datagrid："+leaveListJ.toString());
		return leaveListJ.toString();
	}
	
	/**
	 * 插入请假的方法,只有学生可以插
	 * @return
	 */
	@RequestMapping(value="/insertLeave.do",method = {RequestMethod.GET,RequestMethod.POST},produces="application/text;charset=utf-8")
	@ResponseBody
	public String insertLeave(LeaveModel leave,HttpSession session) {
		//从session中取出
		UserModel userForSession= (UserModel) session.getAttribute("user");
		if(userForSession==null) {
			return "-1";
		}
		LeaveModel leaveTmep=new LeaveModel();
		//why，beginTime，endTime
		leaveTmep.setUserCode(FormatEmpty.Nvl(userForSession.getCode()));
		leaveTmep.setWhy(FormatEmpty.Nvl(leave.getWhy()));
		leaveTmep.setTime(FormatDate.getDate());
		leaveTmep.setBeginTime(FormatEmpty.Nvl(leave.getBeginTime()));
		leaveTmep.setEndTime(FormatEmpty.Nvl(leave.getEndTime()));
		leaveTmep.setStatus(Constants.LEAVE_SP);
		
		//添加基本的 id，code，createtime，updatetime，creator，updater，isdelete，iseffect，descr
		leaveTmep.setId(null);
		leaveTmep.setCode("l"+FormatDate.getDateTimeNoline());
		leaveTmep.setCreatetime(FormatDate.getDateTime());
		leaveTmep.setCreator(FormatEmpty.Nvl(userForSession.getCode()));
		leaveTmep.setIsdelete(Constants.DELETE_N);
		leaveTmep.setIseffect(Constants.EFFECT_Y);
		
		//调
		Integer insertConut=0;
		try {
			insertConut=leaveService.insert(leaveTmep);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return insertConut.toString();
	}
	
	/**
	 * 修改请假
	 * @param workAttendance
	 * @param session
	 * @return
	 */
	@RequestMapping(value="/updateLeave.do",method = {RequestMethod.GET,RequestMethod.POST},produces="application/text;charset=utf-8")
	@ResponseBody
	public String updateLeave(LeaveModel leave,HttpSession session) {
		LeaveModel leaveTmep=new LeaveModel();
		
		@SuppressWarnings("unchecked")
		List<PermissionModel> allPerList=(List<PermissionModel>) session.getAttribute("allPerList");
		for (PermissionModel permissionModel : allPerList) {
			//如果是学生就修改
			if (Constants.SELECT_MYSELF.equals(permissionModel.getCode())) {
				leaveTmep.setCode(FormatEmpty.Nvl(leave.getCode()));
				leaveTmep.setUserCode(FormatEmpty.Nvl(leave.getUserCode()));
				leaveTmep.setWhy(FormatEmpty.Nvl(leave.getWhy()));
				leaveTmep.setBeginTime(FormatEmpty.Nvl(leave.getBeginTime()));
				leaveTmep.setEndTime(FormatEmpty.Nvl(leave.getEndTime()));
				continue;
			}
			//通过是老师，就根据code修改状态，老师通过或者驳回
			if(Constants.SELECT_CLASS.equals(permissionModel.getCode())) {
				leaveTmep.setCode(FormatEmpty.Nvl(leave.getCode()));
				if("tg".equals(FormatEmpty.Nvl(leave.getPassOrBack()))) {
					leaveTmep.setStatus(Constants.LEAVE_JSTG);
				}
				if("bh".equals(FormatEmpty.Nvl(leave.getPassOrBack()))) {
					leaveTmep.setStatus(Constants.LEAVE_BH);
				}
				continue;
			}
			//如果是拥有全部的权限，就根据code修改状态，通过或者驳回
			if(Constants.SELECT_ALL.equals(permissionModel.getCode())) {
				leaveTmep.setCode(FormatEmpty.Nvl(leave.getCode()));
				if("tg".equals(FormatEmpty.Nvl(leave.getPassOrBack()))) {
					leaveTmep.setStatus(Constants.LEAVE_JWTG);
				}
				if("bh".equals(FormatEmpty.Nvl(leave.getPassOrBack()))) {
					leaveTmep.setStatus(Constants.LEAVE_BH);
				}
				continue;
			}
		}	
		
		
		//从session中取出
		UserModel userForSession= (UserModel) session.getAttribute("user");
		if(userForSession==null) {
			return "-1";
		}
		
		leaveTmep.setUpdater(FormatEmpty.Nvl(userForSession.getCode()));
		leaveTmep.setUpdatetime(FormatDate.getDateTime());
		
		Integer updateConut=0;
		//调
		try {
			updateConut=leaveService.update(leaveTmep);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return updateConut.toString();
	}
	
	/**
	 * 删除考勤
	 * @param workAttendance
	 * @return
	 */
	@RequestMapping(value="/deleteLeave.do",method = {RequestMethod.GET,RequestMethod.POST},produces="application/text;charset=utf-8")
	@ResponseBody
	public String deleteLeave(LeaveModel leave) {
		
		LeaveModel leaveTmep=new LeaveModel();
		leaveTmep.setCode(FormatEmpty.Nvl(leave.getCode()));
		
		Integer deleteCount=0;
		try {
			deleteCount=leaveService.delete(leaveTmep);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return deleteCount.toString();
	}
	
	/**
	 * 显示表格
	 * @param workAttendance
	 * @return
	 * @throws UnsupportedEncodingException 
	 */
	@RequestMapping(value="/showBg.do",method = {RequestMethod.GET,RequestMethod.POST},produces="application/text;charset=utf-8")
	@ResponseBody
	public String showBg(HttpSession session,HttpServletRequest request, HttpServletResponse response) throws UnsupportedEncodingException {
		@SuppressWarnings("unchecked")
		List<WorkAttendanceModel> workAttendanceList=  (List<WorkAttendanceModel>) session.getAttribute("workAttendanceListBg");
		JSONArray workAttendanceListJ=JSONArray.fromObject(workAttendanceList);
		
		request.setCharacterEncoding("UTF-8");// 防止乱码
		response.setContentType("text/html;charset=utf-8");
		
//		Map<String, Object> l = new HashMap<>();
//		l.put("text", "考勤信息图表");
//		l.put("legend_data", Arrays.asList("销量"));
//		l.put("xAxis_data", Arrays.asList("衬衫", "羊毛衫", "雪纺衫", "裤子", "高跟鞋", "袜子"));
//		l.put("series_name", "销量");
//		l.put("series_data", Arrays.asList(5, 20, 36, 10, 10, 20));
//		l.put("series_type", "bar");
//		JSONObject json = JSONObject.fromObject(l);
		
		Map<String, Object> l = new HashMap<>();
		l.put("text", "考勤信息图表");
		List<DictionaryModel> kqList=Constants.KQ_LIST;
		List<String> kqNameList=new ArrayList<>();
		for (DictionaryModel dictionaryFor : kqList) {
			kqNameList.add(FormatEmpty.Nvl(dictionaryFor.getName()));
		}
		
		l.put("legend_data",  Arrays.asList("第一次", "第二次", "第三次", "第四次", "第五次", "第六次"));
		l.put("xAxis_data", Arrays.asList("正常", "迟到", "早退", "旷课", "请假"));
		//遍历
		//需要一个五种状态里面有六次打卡的二维数组，对
//		int [][] result= {{0,0,0,0,0,0},{0,0,0,0,0,0},{0,0,0,0,0,0},{0,0,0,0,0,0},{0,0,0,0,0,0}};
		//六次打卡，每次都有五种状态，错
		int[][] a = {{0,0,0,0,0},{0,0,0,0,0},{0,0,0,0,0},{0,0,0,0,0},{0,0,0,0,0},{0,0,0,0,0}};
		for (WorkAttendanceModel workAttendanceFor : workAttendanceList) {
			sss(workAttendanceFor.getOneCheck(), 1, a);
			sss(workAttendanceFor.getTwoCheck(), 2, a);
			sss(workAttendanceFor.getThreeCheck(), 3, a);
			sss(workAttendanceFor.getFourCheck(), 4, a);
			sss(workAttendanceFor.getFiveCheck(), 5, a);
			sss(workAttendanceFor.getSixCheck(), 6, a);
		}
		
		List<Map<String, Object>> series=new ArrayList<>();
		Map<String, Object> map1=new HashMap<>();
		map1.put("name", "第一次");
		map1.put("data", arrayToList(a[0]));
		map1.put("type", "bar");
		Map<String, Object> map2=new HashMap<>();
		map2.put("name", "第二次");
		map2.put("data", arrayToList(a[1]));
		map2.put("type", "bar");
		Map<String, Object> map3=new HashMap<>();
		map3.put("name", "第三次");
		map3.put("data", arrayToList(a[2]));
		map3.put("type", "bar");
		Map<String, Object> map4=new HashMap<>();
		map4.put("name", "第四次");
		map4.put("data", arrayToList(a[3]));
		map4.put("type", "bar");
		Map<String, Object> map5=new HashMap<>();
		map5.put("name", "第五次");
		map5.put("data", arrayToList(a[4]));
		map5.put("type", "bar");
		Map<String, Object> map6=new HashMap<>();
		map6.put("name", "第六次");
		map6.put("data", arrayToList(a[5]));
		map6.put("type", "bar");
		
		series.add(map1);
		series.add(map2);
		series.add(map3);
		series.add(map4);
		series.add(map5);
		series.add(map6);
		l.put("series", series);
		
		JSONObject json = JSONObject.fromObject(l);
		return json.toString();
	}
	
	
	/**
	 * @param check
	 * @param index
	 * @param a
	 */
	private void sss(String check, int index,int[][] a) {
		int[] aa = a[index-1];
		int aalen = aa.length;
		for(int j = 0; j < aalen;j++) {
			if(("k"+(j+1)).equals(check)) {
				a[index-1][j]++;
			}
		}
	}
	
	/**
	 * int数组转换成集合
	 */
	private List<Integer> arrayToList(int[] temp) {
		List<Integer> list=new ArrayList<>();
		for (int tempInt : temp) {
			list.add(tempInt);
		}
		return list;
	}
	
}
