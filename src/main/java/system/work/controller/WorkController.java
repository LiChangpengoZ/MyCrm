package system.work.controller;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import basetool.FormatDate;
import basetool.FormatEmpty;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import system.user.model.ClassModel;
import system.user.model.PermissionModel;
import system.user.model.UserModel;
import system.work.model.ProjectModel;
import system.work.model.PutWorkModel;
import system.work.model.WorkModel;
import system.work.service.ProjectService;
import system.work.service.PutWorkService;
import system.work.service.WorkService;
import utils.Constants;
import utils.Tools;

/**
 * 作业相关的controller
 * @author 李昌鹏
 */
@Controller
@RequestMapping("/workController")
public class WorkController {

	@Autowired
	@Qualifier("putWorkService")
	PutWorkService<PutWorkModel> putWorkService;
	
	@Autowired
	@Qualifier("workService")
	WorkService<WorkModel> workService;
	
	@Autowired
	@Qualifier("projectService")
	ProjectService<ProjectModel> projectService; 
	
	
	/**
	 * 跳到发布作业页面的方法
	 * @param series
	 * @param session
	 * @return
	 */
	@RequestMapping(value="/toPutWork.do",method = {RequestMethod.GET,RequestMethod.POST})
	public ModelAndView toPutWork(String series,HttpSession session) {
		List<Integer> seriesList= Tools.seriesStr(series);
//		System.out.println("数列："+seriesList);
		
		ModelAndView mav=new ModelAndView();
		mav.addObject("seriesList", seriesList);
		mav.setViewName("work/putWork");
		return mav;
	}
	
	/**
	 * 加载发布作业页
	 * @param putWork
	 * @param session
	 * @return
	 */
	@RequestMapping(value="/loadPutWork.do",method = {RequestMethod.GET,RequestMethod.POST},produces="application/text;charset=utf-8")
	@ResponseBody
	public String loadPutWork(PutWorkModel putWork,HttpSession session,String rows,String page) {
		
		PutWorkModel putWorkTemp=new PutWorkModel();
		
		//排序
		putWorkTemp.setOrderBy("desc");
		putWorkTemp.setSortField("p.time");
		
		//分页,根据传过来的查
		putWorkTemp.setPageNumber(Integer.valueOf(FormatEmpty.Nvl(page)));
		putWorkTemp.setRowsNumber(Integer.valueOf(FormatEmpty.Nvl(rows)));
		putWorkTemp.setBeginNumber((putWorkTemp.getPageNumber()-1)*putWorkTemp.getRowsNumber());
		//权限,如果拥有班级的权限，就根据老师的code查询
		boolean isPer = false;		//是否有这些权限
		@SuppressWarnings("unchecked")
		List<PermissionModel> allPerList=(List<PermissionModel>) session.getAttribute("allPerList");
		for (PermissionModel permissionModel : allPerList) {
			if(Constants.SELECT_CLASS.equals(permissionModel.getCode())) {
				isPer=true;
				putWorkTemp.setTeacherCode(FormatEmpty.Nvl(putWork.getCode()));
			}
		}
		if(!isPer) {
			return "-7";
		}
		Map<String, Object> jsonMap = new HashMap<>();
		try {
			List<PutWorkModel> putWorkList = putWorkService.selectAll(putWorkTemp);
			JSONArray putWorkListJ=JSONArray.fromObject(putWorkList);
//			System.out.println("发布作业集合"+putWorkListJ.toString());
			Integer count=putWorkService.selectCount(putWorkTemp);
//			System.out.println("发布作业总数"+count);
			jsonMap.put("total", count);
			jsonMap.put("rows", putWorkList);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		JSONObject putWorkLimitJ=JSONObject.fromObject(jsonMap);
		return putWorkLimitJ.toString();
	}
	
	/**
	 * 插入发布作业的方法
	 * @param workAttendance
	 * @param session
	 * @return
	 */
	@RequestMapping(value="/insertPutWork.do",method = {RequestMethod.GET,RequestMethod.POST},produces="application/text;charset=utf-8")
	@ResponseBody
	public String insertPutWork(PutWorkModel putWork,HttpSession session) {
		PutWorkModel putWorkTmep=new PutWorkModel();
		//code，teacherCode,time,tm,gradeStandard,descr
		putWorkTmep.setTime(FormatEmpty.Nvl(putWork.getTime()));
		putWorkTmep.setTm(FormatEmpty.Nvl(putWork.getTm()));
		putWorkTmep.setGradeStandard(FormatEmpty.Nvl(putWork.getGradeStandard()));
		putWorkTmep.setDescr(FormatEmpty.Nvl(putWork.getDescr() ));
		
		//从session中取出
		UserModel userForSession= (UserModel) session.getAttribute("user");
		if(userForSession==null) {
			return "-1";
		}
		//添加基本的 id，code，createtime，updatetime，creator，updater，isdelete，iseffect，descr
		putWorkTmep.setTeacherCode(FormatEmpty.Nvl(userForSession.getCode()));
		putWorkTmep.setId(null);
		putWorkTmep.setCode("t"+FormatDate.getDateTimeNoline());
		putWorkTmep.setCreatetime(FormatDate.getDateTime());
		putWorkTmep.setCreator(FormatEmpty.Nvl(userForSession.getCode()));
		putWorkTmep.setIsdelete(Constants.DELETE_N);
		putWorkTmep.setIseffect(Constants.EFFECT_Y);
		
		//插入到关联表
		@SuppressWarnings("unchecked")
		List<ClassModel> classListForSession= (List<ClassModel>) session.getAttribute("classList");
		if(classListForSession==null) {
			return "-1";
		}
		ClassModel classTemp=classListForSession.get(0);
		List<UserModel> userListTemp=classTemp.getuUserList();
		for (UserModel userModel : userListTemp) {
			WorkModel workTemp=new WorkModel();
			workTemp.setStudentCode(FormatEmpty.Nvl(userModel.getCode()));
			workTemp.setPutWorkCode(FormatEmpty.Nvl(putWorkTmep.getCode()));
			workTemp.setId(null);
			workTemp.setCode("m"+UUID.randomUUID().toString());
			workTemp.setCreatetime(FormatDate.getDateTime());
			workTemp.setCreator(FormatEmpty.Nvl(userForSession.getCode()));
			workTemp.setIsdelete(Constants.DELETE_N);
			workTemp.setIseffect(Constants.EFFECT_Y);
			try {
				workService.insert(workTemp);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		//调
		Integer insertConut=0;
		try {
			insertConut=putWorkService.insert(putWorkTmep);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return insertConut.toString();
	}
	
	/**
	 * 修改发布作业
	 * @param workAttendance
	 * @param session
	 * @return
	 */
	@RequestMapping(value="/updatePutWork.do",method = {RequestMethod.GET,RequestMethod.POST},produces="application/text;charset=utf-8")
	@ResponseBody
	public String updatePutWork(PutWorkModel putWork,HttpSession session) {
		//code，teacherCode,time,tm,gradeStandard,descr
		PutWorkModel putWorkTemp=new PutWorkModel();
		putWorkTemp.setCode(FormatEmpty.Nvl(putWork.getCode()));
		putWorkTemp.setTeacherCode(FormatEmpty.Nvl(putWork.getTeacherCode()));
		putWorkTemp.setTime(FormatEmpty.Nvl(putWork.getTime()));
		putWorkTemp.setTm(FormatEmpty.Nvl(putWork.getTm()));
		putWorkTemp.setGradeStandard(FormatEmpty.Nvl(putWork.getGradeStandard()));
		putWorkTemp.setDescr(FormatEmpty.Nvl(putWork.getDescr()));
		
		//从session中取出
		UserModel userForSession= (UserModel) session.getAttribute("user");
		if(userForSession==null) {
			return "-1";
		}
		
		putWorkTemp.setUpdater(FormatEmpty.Nvl(userForSession.getCode()));
		putWorkTemp.setUpdatetime(FormatDate.getDateTime());
		Integer updateConut=0;
		//调
		try {
			updateConut=putWorkService.update(putWorkTemp);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return updateConut.toString();
	}
	
	/**
	 * 删除发布的作业
	 * @param workAttendance
	 * @return
	 */
	@RequestMapping(value="/deleteProject.do",method = {RequestMethod.GET,RequestMethod.POST},produces="application/text;charset=utf-8")
	@ResponseBody
	public String deleteProject(ProjectModel project) {
		ProjectModel projectTemp=new ProjectModel();
		projectTemp.setCode(FormatEmpty.Nvl(project.getCode()));
		
		Integer deleteCount=0;
		try {
			deleteCount=projectService.delete(projectTemp);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return deleteCount.toString();
	}
	
	/**
	 * 跳转到回答作业的方法
	 * @param series
	 * @param session
	 * @return
	 */
	@RequestMapping(value="/toWork.do",method = {RequestMethod.GET,RequestMethod.POST})
	public ModelAndView toWork(String series,HttpSession session) {
		List<Integer> seriesList= Tools.seriesStr(series);
//		System.out.println("数列："+seriesList);
		JSONArray zyListJ=JSONArray.fromObject(Constants.ZY_LIST);
//		System.out.println("作业："+zyListJ.toString());
		ModelAndView mav=new ModelAndView();
		
		//权限的集合，如果有查询班级的，就把班级的学生从session中取到，返回页面
		@SuppressWarnings("unchecked")
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
		
		mav.addObject("zyList", zyListJ.toString());
		mav.addObject("seriesList", seriesList);
		mav.setViewName("work/work");
		return mav;
	}
	
	/**
	 * 加载回答页
	 * @param putWork
	 * @param session
	 * @param rows
	 * @param page
	 * @return
	 */
	@RequestMapping(value="/loadWork.do",method = {RequestMethod.GET,RequestMethod.POST},produces="application/text;charset=utf-8")
	@ResponseBody
	public String loadWork(String cClassCode,WorkModel work,HttpSession session,String rows,String page) {
		WorkModel workTemp=new WorkModel();
		//排序
		workTemp.setOrderBy("desc");
		workTemp.setSortField("u.true_name,p.time");
		
		//默认查看一周以内的,否则根据传过来的查
		if(work.getSbegintime()!=null) {
			workTemp.setSbegintime(FormatEmpty.Nvl(work.getSbegintime()));
		}
		if(work.getSendtime()!=null) {
			workTemp.setSendtime(FormatEmpty.Nvl(work.getSendtime()));
		}
		
		//分页,根据传过来的查
		workTemp.setPageNumber(Integer.valueOf(FormatEmpty.Nvl(page)));
		workTemp.setRowsNumber(Integer.valueOf(FormatEmpty.Nvl(rows)));
		workTemp.setBeginNumber((workTemp.getPageNumber()-1)*workTemp.getRowsNumber());
		
		//从session中取出
		UserModel userForSession= (UserModel) session.getAttribute("user");
		if(userForSession==null) {
			return "-1";
		}
		
		//权限
		boolean isPer = false;		//是否有这些权限
		@SuppressWarnings("unchecked")
		List<PermissionModel> allPerList=(List<PermissionModel>) session.getAttribute("allPerList");
		for (PermissionModel permissionModel : allPerList) {
			if(Constants.SELECT_MYSELF.equals(permissionModel.getCode())) {
				isPer=true;
				workTemp.setStudentCode(FormatEmpty.Nvl(userForSession.getCode()));
			}
			if(Constants.SELECT_CLASS.equals(permissionModel.getCode())) {
				isPer=true;
				ClassModel classtemp=new ClassModel();
				classtemp.setTeacherCode(FormatEmpty.Nvl(userForSession.getCode()));
				classtemp.setCode(cClassCode);
				classtemp.setCode(cClassCode);
				workTemp.setcClass(classtemp);
				workTemp.setStudentCode(FormatEmpty.Nvl(work.getStudentCode()));
			}
			if(Constants.SELECT_ALL.equals(permissionModel.getCode())) {
				ClassModel classtemp=new ClassModel();
				classtemp.setCode(cClassCode);
				workTemp.setcClass(classtemp);
				workTemp.setStudentCode(FormatEmpty.Nvl(work.getStudentCode()));
				isPer=true;
			} 
		}
		if(!isPer) {
			return "-7";
		}
		Map<String, Object> jsonMap = new HashMap<>();
		try {
			List<WorkModel> workList = workService.selectJoin(workTemp);
			JSONArray workListJ=JSONArray.fromObject(workList);
			System.out.println("作业集合"+workListJ.toString());
			session.setAttribute("workListTemp", workList);
			
			Integer count=workService.selectCount(workTemp);
//			System.out.println("作业总数"+count);
			jsonMap.put("total", count);
			jsonMap.put("rows", workList);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		JSONObject workLimitJ=JSONObject.fromObject(jsonMap);
		return workLimitJ.toString();
	}
	
	/**
	 * 评分
	 * @param workAttendance
	 * @param session
	 * @return
	 */
	@RequestMapping(value="/updateWork.do",method = {RequestMethod.GET,RequestMethod.POST},produces="application/text;charset=utf-8")
	@ResponseBody
	public String updateWork(WorkModel work,HttpSession session,String score,String grade) {
		
		//存code，成绩和评级
		WorkModel workTemp=new WorkModel();
		workTemp.setCode(FormatEmpty.Nvl(work.getCode()));
		workTemp.setDescr(FormatEmpty.Nvl(work.getDescr()));
		//判断权限，看谁修改的
		boolean isPer = false;		//是否有这些权限
		@SuppressWarnings("unchecked")
		List<PermissionModel> allPerList=(List<PermissionModel>) session.getAttribute("allPerList");
		for (PermissionModel permissionModel : allPerList) {
			if(Constants.SELECT_MYSELF.equals(permissionModel.getCode())) {
				isPer=true;
				workTemp.setOneselfScore(FormatEmpty.Nvl(score));
				workTemp.setOneselfGrade(FormatEmpty.Nvl(grade));
			}
			if(Constants.SELECT_CLASS.equals(permissionModel.getCode())) {
				isPer=true;
				workTemp.setTeacherScore(FormatEmpty.Nvl(score));
				workTemp.setTeacherGrade(FormatEmpty.Nvl(grade));
			}
		}
		if(!isPer) {
			return "-7";
		}
		
		//从session中取出
		UserModel userForSession= (UserModel) session.getAttribute("user");
		if(userForSession==null) {
			return "-1";
		}
		
		workTemp.setUpdater(FormatEmpty.Nvl(userForSession.getCode()));
		workTemp.setUpdatetime(FormatDate.getDateTime());
		Integer updateConut=0;
		//调
		try {
			updateConut=workService.update(workTemp);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return updateConut.toString();
	}
	
	/**
	 * 跳到项目
	 * @param series
	 * @param session
	 * @return
	 */
	@RequestMapping(value="/toProject.do",method = {RequestMethod.GET,RequestMethod.POST})
	public ModelAndView toProject(String series,HttpSession session) {
		List<Integer> seriesList= Tools.seriesStr(series);
//		System.out.println("数列："+seriesList);
		JSONArray zyListJ=JSONArray.fromObject(Constants.ZY_LIST);
//		System.out.println("作业："+zyListJ.toString());
		ModelAndView mav=new ModelAndView();
		
		//权限的集合，如果有查询班级的，就把班级的学生从session中取到，返回页面
		@SuppressWarnings("unchecked")
		List<PermissionModel> allPerList= (List<PermissionModel>) session.getAttribute("allPerList");
		for (PermissionModel permissionTemp : allPerList) {
			if(Constants.SELECT_CLASS.equals(permissionTemp.getCode()) || Constants.SELECT_ALL.equals(permissionTemp.getCode())) {
				//获取包含学生的班级的集合
				@SuppressWarnings("unchecked")
				List<ClassModel> classList= (List<ClassModel>) session.getAttribute("classList");
				JSONArray classListJ=JSONArray.fromObject(classList);
//				System.out.println("班级："+classListJ.toString());
				mav.addObject("classList", classListJ.toString());
			}
		}
		
		mav.addObject("zyList", zyListJ.toString());
		mav.addObject("seriesList", seriesList);
		mav.setViewName("work/project");
		return mav;
	}
	
	/**
	 * 加载项目页
	 * @param putWork
	 * @param session
	 * @param rows
	 * @param page
	 * @return
	 */
	@RequestMapping(value="/loadProject.do",method = {RequestMethod.GET,RequestMethod.POST},produces="application/text;charset=utf-8")
	@ResponseBody
	public String loadProject(String cClassCode,ProjectModel project,HttpSession session,String rows,String page) {
		ProjectModel projectTemp=new ProjectModel();
		//排序
		projectTemp.setOrderBy("desc");
		projectTemp.setSortField("u.true_name,p.time");
		
		//时间
		if(project.getSbegintime()!=null) {
			projectTemp.setSbegintime(FormatEmpty.Nvl(project.getSbegintime()));
		}
		if(project.getSendtime()!=null) {
			projectTemp.setSendtime(FormatEmpty.Nvl(project.getSendtime()));
		}
		
		//分页,根据传过来的查
		projectTemp.setPageNumber(Integer.valueOf(FormatEmpty.Nvl(page)));
		projectTemp.setRowsNumber(Integer.valueOf(FormatEmpty.Nvl(rows)));
		projectTemp.setBeginNumber((projectTemp.getPageNumber()-1)*projectTemp.getRowsNumber());
		
		//从session中取出
		UserModel userForSession= (UserModel) session.getAttribute("user");
		if(userForSession==null) {
			return "-1";
		}
		
		//权限
		boolean isPer = false;		//是否有这些权限
		@SuppressWarnings("unchecked")
		List<PermissionModel> allPerList=(List<PermissionModel>) session.getAttribute("allPerList");
		for (PermissionModel permissionModel : allPerList) {
			if(Constants.SELECT_MYSELF.equals(permissionModel.getCode())) {
				isPer=true;
				projectTemp.setUserCode(FormatEmpty.Nvl(userForSession.getCode()));
			}
			if(Constants.SELECT_CLASS.equals(permissionModel.getCode())) {
				isPer=true;
				ClassModel classtemp=new ClassModel();
				classtemp.setTeacherCode(FormatEmpty.Nvl(userForSession.getCode()));
				classtemp.setCode(cClassCode);
				classtemp.setCode(cClassCode);
				projectTemp.setcClass(classtemp);
				projectTemp.setUserCode(FormatEmpty.Nvl(project.getUserCode()));
			}
			if(Constants.SELECT_ALL.equals(permissionModel.getCode())) {
				ClassModel classtemp=new ClassModel();
				classtemp.setCode(cClassCode);
				projectTemp.setcClass(classtemp);
				projectTemp.setUserCode(FormatEmpty.Nvl(project.getUserCode()));
				isPer=true;
			} 
		}
		if(!isPer) {
			return "-7";
		}
		Map<String, Object> jsonMap = new HashMap<>();
		try {
			List<ProjectModel> projectList = projectService.selectJoin(projectTemp);
			JSONArray projectListJ=JSONArray.fromObject(projectList);
//			System.out.println("项目集合"+projectListJ.toString());
			Integer count=projectService.selectCount(projectTemp);
//			System.out.println("项目总数"+count);
			jsonMap.put("total", count);
			jsonMap.put("rows", projectList);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		JSONObject projectLimitJ=JSONObject.fromObject(jsonMap);
		return projectLimitJ.toString();
	}
	
	/**
	 * 插入发布作业的方法
	 * @param workAttendance
	 * @param session
	 * @return
	 */
	@RequestMapping(value="/insertProject.do",method = {RequestMethod.GET,RequestMethod.POST},produces="application/text;charset=utf-8")
	@ResponseBody
	public String insertProject(ProjectModel project,HttpSession session) {
		ProjectModel projectTemp=new ProjectModel();
		//time，projectName,descr
		projectTemp.setTime(FormatEmpty.Nvl(project.getTime()));
		projectTemp.setProjectName(FormatEmpty.Nvl(project.getProjectName()));
		projectTemp.setDescr(FormatEmpty.Nvl(project.getDescr()));
		
		//从session中取出
		UserModel userForSession= (UserModel) session.getAttribute("user");
		if(userForSession==null) {
			return "-1";
		}
		
		projectTemp.setUserCode(FormatEmpty.Nvl(userForSession.getCode()));
		projectTemp.setId(null);
		projectTemp.setCode("p"+FormatDate.getDateTimeNoline());
		projectTemp.setCreatetime(FormatDate.getDateTime());
		projectTemp.setCreator(FormatEmpty.Nvl(userForSession.getCode()));
		projectTemp.setIsdelete(Constants.DELETE_N);
		projectTemp.setIseffect(Constants.EFFECT_Y);
		
		//调
		Integer insertConut=0;
		try {
			insertConut=projectService.insert(projectTemp);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return insertConut.toString();
	}
	
	/**
	 * 评分
	 * @param 
	 * @param session
	 * @return
	 */
	@RequestMapping(value="/updateProject.do",method = {RequestMethod.GET,RequestMethod.POST},produces="application/text;charset=utf-8")
	@ResponseBody
	public String updateProject(ProjectModel project,HttpSession session,String score,String grades) {
		
		ProjectModel projectTemp=new ProjectModel();
		//存code
		projectTemp.setCode(FormatEmpty.Nvl(project.getCode()));
		
		//判断权限，看谁修改的
		boolean isPer = false;		//是否有这些权限
		@SuppressWarnings("unchecked")
		List<PermissionModel> allPerList=(List<PermissionModel>) session.getAttribute("allPerList");
		for (PermissionModel permissionModel : allPerList) {
			if(Constants.SELECT_MYSELF.equals(permissionModel.getCode())) {
				isPer=true;
				projectTemp.setStudentScore(FormatEmpty.Nvl(score));
				projectTemp.setStudentGrade(FormatEmpty.Nvl(grades));
			}
			if(Constants.SELECT_CLASS.equals(permissionModel.getCode())) {
				isPer=true;
				projectTemp.setTeacherScore(FormatEmpty.Nvl(score));
				projectTemp.setGrade(FormatEmpty.Nvl(grades));
			}
		}
		if(!isPer) {
			return "-7";
		}
		
		//从session中取出
		UserModel userForSession= (UserModel) session.getAttribute("user");
		if(userForSession==null) {
			return "-1";
		}
		
		projectTemp.setUpdater(FormatEmpty.Nvl(userForSession.getCode()));
		projectTemp.setUpdatetime(FormatDate.getDateTime());
		Integer updateConut=0;
		//调
		try {
			updateConut=projectService.update(projectTemp);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return updateConut.toString();
	}
	
	/**
	 * 删除项目
	 * @param workAttendance
	 * @return
	 */
	@RequestMapping(value="/deletePutWork.do",method = {RequestMethod.GET,RequestMethod.POST},produces="application/text;charset=utf-8")
	@ResponseBody
	public String deletePutWork(PutWorkModel putWork) {
		PutWorkModel putWorkTemp=new PutWorkModel();
		putWorkTemp.setCode(FormatEmpty.Nvl(putWork.getCode()));

		Integer deleteCount=0;
		try {
			deleteCount=putWorkService.delete(putWorkTemp);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return deleteCount.toString();
	}
	
	/**
	 * 导出excel表格
	 */
	@RequestMapping(value="/exportExcel.do",method = {RequestMethod.GET,RequestMethod.POST},produces="application/text;charset=utf-8")
	@ResponseBody
	public void exportExcel(HttpSession session,HttpServletResponse response) {
		
		response.setContentType("application/binary;charset=UTF-8");
		ServletOutputStream out = null;
		try {
			out = response.getOutputStream();
			String fileName = new String(
					("UserInfo " + new SimpleDateFormat("yyyy-MM-dd").format(new Date())).getBytes(), "UTF-8");
			response.setHeader("Content-disposition", "attachment; filename=" + fileName + ".xls");
			
			// 第一步，创建一个workbook，对应一个文件
	        HSSFWorkbook workbook = new HSSFWorkbook();
	        // 第二步，在webbook中添加一个sheet,对应Excel文件中的sheet
	        HSSFSheet hssfSheet = workbook.createSheet("sheet1");
	        // 第三步，在sheet中添加表头第0行,注意老版本poi对Excel的行数列数有限制short
	        HSSFRow hssfRow = hssfSheet.createRow(0);
	        // 第四步，创建单元格，并设置值表头 设置表头居中
	        HSSFCellStyle hssfCellStyle = workbook.createCellStyle();
	        // 居中样式
	        hssfCellStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER);
			
	        HSSFCell hssfCell = null;
	        String[] titles = { "日期", "姓名", "题目", "评分标准", "自评成绩", "师评成绩", "备注" };
	        for (int i = 0; i < titles.length; i++) {
	            hssfCell = hssfRow.createCell(i);// 列索引从0开始
	            hssfCell.setCellValue(titles[i]);// 列名1
	            hssfCell.setCellStyle(hssfCellStyle);// 列居中显示
	        }
			
	        @SuppressWarnings("unchecked")
			List<WorkModel> workListTemp=(List<WorkModel>) session.getAttribute("workListTemp");
			
	        for (int i = 0; i < workListTemp.size(); i++) {
	        	 hssfRow = hssfSheet.createRow(i + 1);
	        	 
	        	 WorkModel workTemp=workListTemp.get(i);
	        	 PutWorkModel putWorkTemp=workTemp.getpPutWork();
	        	 UserModel userTemp=workTemp.getuUser();
	        	 
	        	 hssfRow.createCell(0).setCellValue(FormatEmpty.Nvl(putWorkTemp.getTime()));	//时间
	        	 hssfRow.createCell(1).setCellValue(FormatEmpty.Nvl(userTemp.getTrueName()));	//名字
	        	 hssfRow.createCell(2).setCellValue(FormatEmpty.Nvl(putWorkTemp.getTm()));		//题目
	        	 hssfRow.createCell(3).setCellValue(FormatEmpty.Nvl(putWorkTemp.getGradeStandard()));	//评分标准
	        	 hssfRow.createCell(4).setCellValue(FormatEmpty.Nvl(workTemp.getOneselfScore()));	//自评成绩
	        	 hssfRow.createCell(5).setCellValue(FormatEmpty.Nvl(workTemp.getTeacherScore()));	//师评成绩
	        	 hssfRow.createCell(6).setCellValue(FormatEmpty.Nvl(workTemp.getDescr()));	//备注
			
	        }
	        workbook.write(out);
	        out.flush();
	        out.close();
	        } catch (IOException e1) {
				e1.printStackTrace();
			}
        
	}
	
	
}
