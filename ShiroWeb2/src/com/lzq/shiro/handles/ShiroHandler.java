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
			// ���û�����������з�װ��һ������
			UsernamePasswordToken token = new UsernamePasswordToken(username,password);
			token.setRememberMe(true);
			try {
				// ִ�е�¼
				System.out.println("1 = " + token.hashCode());
				System.out.println(token.getUsername());
				System.out.println(token.getPassword());
				System.out.println("token1 = "+token);
				//�����ﴫ�ݹ�ȥ��ֵ���� ShiroReamls �൱����д�� doGetAuthenticationInfo ����
				currentUser.login(token);
			}
			catch (AuthenticationException ae) {
				System.out.println("��¼ʧ�� = "+ae.getMessage());
			}
		}
		return "redirect:/list.jsp";
	}
	
}
