package com.lzq.shiro.realms;

import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.LockedAccountException;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.realm.AuthenticatingRealm;
import org.apache.shiro.util.ByteSource;

public class ShiroRealm extends AuthenticatingRealm {

	@Override
	protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
		System.out.println("doGetAuthenticationInfo = " + token.hashCode());
		System.out.println("token2 = " + token);

		// 把 AuthenticationToken 转换为 UsernamePasswordToken 类型
		UsernamePasswordToken UpToken = (UsernamePasswordToken) token;

		// 从UsernamePasswordToken当中获取username
		String username = UpToken.getUsername();
		System.out.println("getUserName = " + username);

		// 根据username查询password
		System.out.println("在数据库当中查询 . . .");

		// 不存在，抛出异常
		if ("unknow".equals(username)) {
			throw new UnknownAccountException("用户不存在");
		}
		// 判断是否需要抛出其他异常 AuthenticationException
		if ("monter".equals(username)) {
			throw new LockedAccountException("用户被锁定");
		}

		// 根据用户情况来构建AuthenticationInfo对象
		// principal : 认证的实体信息。可以是 username,也可以是数据表对应的用户的实体类对象。
		Object principal = username;
		// credentials : 密码
		Object credentials = "123456";
		// realmName : 当前Realm对象的name，调用父类的getName方法
		String realmName = getName();
		ByteSource credentialsSalt = ByteSource.Util.bytes(username);
		if ("admin".equals(username)) {
			credentials = "038bdaf98f2037b31f1e75b5b4c9b26e"; // YWRtaW4= admin YQ==a
		} else if ("user".equals(username)) {
			credentials = "098d2c478e9c11555ce2823231e02ec1";
		}
//				else {
//			credentials=credentialsSalt;
//		}

		System.out.println("credentialsSalt = " + credentialsSalt);

		SimpleAuthenticationInfo info = new SimpleAuthenticationInfo(principal, credentials, credentialsSalt,
				realmName);

		System.out.println("realm 结束");
		System.out.println(info);

		return info;

	}

}
