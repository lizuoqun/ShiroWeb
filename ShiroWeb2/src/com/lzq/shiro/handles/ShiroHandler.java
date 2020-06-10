package com.lzq.shiro.handles;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/Shiro")
public class ShiroHandler {
	@RequestMapping("/Login")
	public String login(@RequestParam("username") String username, 
					@RequestParam("password") String password) {
		Subject currentUser = SecurityUtils.getSubject();
		if (!currentUser.isAuthenticated()) {
			// 把用户名和密码进行封装成一个对象
			UsernamePasswordToken token = new UsernamePasswordToken(username,password);
			token.setRememberMe(true);
			try {
				// 执行登录
				System.out.println("1 = " + token.hashCode());
				System.out.println(token.getUsername());
				System.out.println(token.getPassword());
				System.out.println("token1 = "+token);
				//从这里传递过去的值给到 ShiroReamls 类当中重写的 doGetAuthenticationInfo 方法
				currentUser.login(token);
			}
			catch (AuthenticationException ae) {
				System.out.println("登录失败 = "+ae.getMessage());
			}
		}
		return "redirect:/list.jsp";
	}
	
}
