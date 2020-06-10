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

		// �� AuthenticationToken ת��Ϊ UsernamePasswordToken ����
		UsernamePasswordToken UpToken = (UsernamePasswordToken) token;

		// ��UsernamePasswordToken���л�ȡusername
		String username = UpToken.getUsername();
		System.out.println("getUserName = " + username);

		// ����username��ѯpassword
		System.out.println("�����ݿ⵱�в�ѯ . . .");

		// �����ڣ��׳��쳣
		if ("unknow".equals(username)) {
			throw new UnknownAccountException("�û�������");
		}
		// �ж��Ƿ���Ҫ�׳������쳣 AuthenticationException
		if ("monter".equals(username)) {
			throw new LockedAccountException("�û�������");
		}

		// �����û����������AuthenticationInfo����
		// principal : ��֤��ʵ����Ϣ�������� username,Ҳ���������ݱ��Ӧ���û���ʵ�������
		Object principal = username;
		// credentials : ����
		Object credentials = "123456";
		// realmName : ��ǰRealm�����name�����ø����getName����
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

		System.out.println("realm ����");
		System.out.println(info);

		return info;

	}

}
