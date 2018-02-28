package interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import basecore.annotation.Auth;
import system.user.model.UserModel;


/**
 * 权限拦截器
 * @author 李昌鹏
 */
public class AuthInterceptor extends HandlerInterceptorAdapter{

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		HandlerMethod method = (HandlerMethod) handler;
		Auth auth = method.getMethod().getAnnotation(Auth.class);
		String baseUri = request.getContextPath();
		String path = request.getServletPath();
		UserModel user= (UserModel) request.getSession().getAttribute("user");
		
		//用户认证
		//如果写了注释，并且true的情况下认证
		if(auth != null && auth.verifyLogin()) {
			if(user==null) {
				response.setStatus(504);
				response.sendRedirect(baseUri + "/toLogin.do");
				return false;
			}
			
		}
		
		return super.preHandle(request, response, handler);
	}
	
}
