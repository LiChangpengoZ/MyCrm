package system.user.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.imageio.ImageIO;
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

import basecore.annotation.Auth;
import basetool.FormatEmpty;
import basetool.FormatAuthCode;
import basetool.FormatMD5;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import system.user.model.ClassModel;
import system.user.model.DictionaryModel;
import system.user.model.PermissionModel;
import system.user.model.UserModel;
import system.user.service.ClassService;
import system.user.service.DictionaryService;
import system.user.service.UserService;
import utils.Constants;

/**
 * user的controller
 * @author 李昌鹏
 */
@Controller
@RequestMapping
public class UserController {
	
	@Autowired
	@Qualifier("userService")
	UserService<UserModel> userService;
	
	@Autowired
	@Qualifier("classService")
	ClassService<ClassModel> classService; 
	
	/**
	 * 登录的方法
	 * @param user
	 * @return
	 */
	@RequestMapping(value="/login.do",method = {RequestMethod.GET,RequestMethod.POST},produces="application/text;charset=utf-8")
	public ModelAndView login(UserModel user,String authCodeImg,HttpSession session) {
		if(authCodeImg==null) {
			return new ModelAndView("login");
		}
		//校验验证码
		if(!authCodeImg.equals(session.getAttribute("authCode"))) {
			return new ModelAndView("login");
		}
		
		//新建临时对象
		UserModel userTemp=new UserModel();
		userTemp.setUserName(FormatEmpty.Nvl(user.getUserName()));
		//把密码加密
		userTemp.setPassword(FormatMD5.encry(FormatEmpty.Nvl(user.getPassword())));
		//把要排序的插进去
		userTemp.setOrderBy("ASC");
		userTemp.setSortField("p.sort_field");
		//根据账号密码和角色查询
		List<UserModel> userList=new ArrayList<UserModel>();
		try {
			userList= userService.selectJoin(userTemp);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		//如果空就返回登录页
		if(userList.isEmpty()) {
			return new ModelAndView("login");
		}else {
			//把用户全部信息存到session中
			session.setAttribute("user", userList.get(0));
			JSONObject userJ=JSONObject.fromObject(userList.get(0));
			session.setAttribute("userJ", userJ.toString());	
//			System.out.println(userJ);
			return new ModelAndView("index");	
		}
		
	}
	
	/**
	 * 验证码的方法
	 * @param request
	 * @param response
	 */
	@RequestMapping(value="/authCode.do",method = {RequestMethod.GET,RequestMethod.POST})
	@ResponseBody
	public void authCode(HttpServletRequest request,HttpServletResponse response) {
		try {
			ImageIO.write(FormatAuthCode.getAuchCodeImg(request), "jpg", response.getOutputStream());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
	}
	
	/**
	 * url登录的方法
	 * @return
	 */
	@RequestMapping(value="/toLogin.do",method = {RequestMethod.GET,RequestMethod.POST})
	public ModelAndView toLogin() {
		return new ModelAndView("login");
	}
	
	
	/**
	 * 修改密码的方法
	 * @param user
	 * @param newpass
	 * @param session
	 * @return -1，代表用户没登录，0代表原始密码不正确，1代表修改成功,-2修改失败
	 */
	@Auth(verifyLogin=true)
	@RequestMapping(value="/passUpdate.do",method = {RequestMethod.GET,RequestMethod.POST})
	@ResponseBody
	public String passUpdate(UserModel user,String newpass,HttpSession session) {
		
		UserModel userSession=(UserModel)session.getAttribute("user");
		if(userSession==null) {
			return "-1";
		}
		String code=userSession.getCode();
		//临时对象
		UserModel userTmep=new UserModel();
		userTmep.setCode(FormatEmpty.Nvl(code));
		userTmep.setPassword(FormatMD5.encry(FormatEmpty.Nvl(user.getPassword())));
		//现在user中有password和code
		try {
			Integer count = null;
			count= userService.selectCount(userTmep);
			if(count==null || count==0) {
				return "0";
			}
			userTmep.setPassword(FormatMD5.encry(FormatEmpty.Nvl(newpass)));
			Integer updateCount;
			updateCount= userService.update(userTmep);
			if (updateCount == 0) {
				return "-2";
			}else {
				return "1";
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return "-2";
	}
	
	/**
	 * 退出的方法
	 * @param session
	 * @return
	 */
	@RequestMapping(value="/exit.do",method = {RequestMethod.GET,RequestMethod.POST},produces="application/text;charset=utf-8")
	public ModelAndView exit(HttpSession session) {
		session.removeAttribute("user");
		return new ModelAndView("login");
	}

	/**
	 * 加载菜单的方法
	 * @param session
	 * @return
	 */
	@Auth(verifyLogin=true)
	@RequestMapping(value="/loadMenu.do",method = {RequestMethod.GET,RequestMethod.POST},produces="application/text;charset=utf-8")
	@ResponseBody
	public String loadMenu(HttpSession session) {
		//从session中获取菜单
		UserModel userTemp=(UserModel) session.getAttribute("user");
		List<PermissionModel> allPerList= userTemp.getpPermissionList();
		//把所有的权限存session中，以便用户授权
		session.setAttribute("allPerList", allPerList);
		JSONArray allPerListJ=JSONArray.fromObject(allPerList);
//		System.out.println("所有权限："+ allPerListJ);
		//遍历全部菜单，如果有查询班级或者查询全部的权限，说明是高级用户登录，根据班查询
		ClassModel classTemp2 = new ClassModel();
		for(PermissionModel permission:allPerList) {
			if(Constants.SELECT_CLASS.equals(permission.getCode())) {
				//根据老师的code，查询出班级包含学生的列表
				classTemp2.setOrderBy("desc");
				classTemp2.setSortField("c.createtime");
				classTemp2.setTeacherCode(userTemp.getCode());
				try {
					List<ClassModel> classList= classService.selectJoin(classTemp2);
					session.setAttribute("classList", classList);
					JSONArray classListJ=JSONArray.fromObject(classList);
//					System.out.println("包含学生的班级"+classListJ);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			if(Constants.SELECT_ALL.equals(permission.getCode())) {
				//查询出所有的班级
				classTemp2.setOrderBy("desc");
				classTemp2.setSortField("c.createtime");
				try {
					List<ClassModel> classList= classService.selectJoin(classTemp2);
					session.setAttribute("classList", classList);
					JSONArray classListJ=JSONArray.fromObject(classList);
//					System.out.println("包含学生的班级"+classListJ);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			
			
		}
		
		
		//把二级菜单放到一级里
		List<PermissionModel> perList=new ArrayList<PermissionModel>();
		for (PermissionModel perAllModel : allPerList) {
			if("0000".equals(perAllModel.getPcode())) {
				List<PermissionModel> sonPerList=new ArrayList<PermissionModel>();
				perAllModel.setSonPerList(sonPerList);
				perList.add(perAllModel);
			}
		}
		for (PermissionModel perAllModel : allPerList) {
			for (PermissionModel perModel : perList) {
				if(perAllModel.getPcode().equals(perModel.getCode())) {
					List<PermissionModel> perTmepList=perModel.getSonPerList();
					perTmepList.add(perAllModel);
					perModel.setSonPerList(perTmepList);
				}
			}
		}
		JSONArray perListJ=JSONArray.fromObject(perList);
//		System.out.println(perListJ);
		
		return perListJ.toString();
	}
	
}
