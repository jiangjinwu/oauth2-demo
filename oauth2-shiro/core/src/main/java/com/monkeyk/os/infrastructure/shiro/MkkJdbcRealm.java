package com.monkeyk.os.infrastructure.shiro;

import com.monkeyk.os.domain.users.UserDetailInfo;
import com.monkeyk.os.domain.users.Users;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authc.*;
import org.apache.shiro.codec.Base64;
import org.apache.shiro.config.ConfigurationException;
import org.apache.shiro.realm.jdbc.JdbcRealm;
import org.apache.shiro.util.ByteSource;
import org.apache.shiro.util.JdbcUtils;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Component;

import java.sql.Connection;
import java.sql.SQLException;

/**
 * 15-6-10
 * <p/>
 * 扩展默认的 JdbcRealm, 在查询用户密码是增加条件 archived = 0 (用于实现逻辑删除)
 * <p/>
 * 该类是一个扩展实现 Realm 的参考
 *
 * @author Shengzhao Li
 */

@Slf4j
public class MkkJdbcRealm extends JdbcRealm implements InitializingBean {

    public static final String AUTHENTICATION_QUERY = "select password from users where archived = 0 and username = ?";

    public static final String USER_ROLES_QUERY = "select r.role_name from user_roles ur,users u,roles r  where ur.users_id = u.id and ur.roles_id = r.id and u.username = ?";

    public static final String PERMISSIONS_QUERY = "select rp.permission from roles_permissions rp,roles r where r.id = rp.roles_id and r.role_name = ?";


    public MkkJdbcRealm() {
        super();
    }


    /**
     * 根据实现的需要, 可以修改具体使用时的查询语句
     *
     * @throws Exception
     */
    @Override
    public void afterPropertiesSet() throws Exception {
        //override
        setAuthenticationQuery(AUTHENTICATION_QUERY);
        setUserRolesQuery(USER_ROLES_QUERY);
        setPermissionsQuery(PERMISSIONS_QUERY);
    }
    /*@Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException{
        //String token = (String) auth.getCredentials();



        UsernamePasswordToken upToken = (UsernamePasswordToken) token;
        String username = upToken.getUsername();

        // Null username is invalid
        if (username == null) {
            throw new AccountException("Null usernames are not allowed by this realm.");
        }

        Connection conn = null;
        SimpleAuthenticationInfo info = null;
        try {
            conn = dataSource.getConnection();

            String password = "test";
            String salt = null;
//            switch (saltStyle) {
//                case NO_SALT:
//                    password = super.getPasswordForUser(conn, username)[0];
//                    break;
//                case CRYPT:
//                    // TODO: separate password and hash from getPasswordForUser[0]
//                    throw new ConfigurationException("Not implemented yet");
//                    //break;
//                case COLUMN:
//                    String[] queryResults = super.getPasswordForUser(conn, username);
//                    password = queryResults[0];
//                    salt = queryResults[1];
//                    break;
//                case EXTERNAL:
//                    password = super.getPasswordForUser(conn, username)[0];
//                    salt = getSaltForUser(username);
//            }

            if (password == null) {
                throw new UnknownAccountException("No account found for user [" + username + "]");
            }
            UserDetailInfo users = new UserDetailInfo();
            users.setUserName(username);
           // info = new SimpleAuthenticationInfo(username, password.toCharArray(), getName());
            info = new SimpleAuthenticationInfo(users, password.toCharArray(), getName());

//            info.setPrincipals(users);
            if (salt != null) {
                if (saltStyle == SaltStyle.COLUMN && saltIsBase64Encoded) {
                    info.setCredentialsSalt(ByteSource.Util.bytes(Base64.decode(salt)));
                } else {
                    info.setCredentialsSalt(ByteSource.Util.bytes(salt));
                }
            }

        } catch (SQLException e) {
            final String message = "There was a SQL error while authenticating user [" + username + "]";
            if (log.isErrorEnabled()) {
                log.error(message, e);
            }

            // Rethrow any SQL errors as an authentication exception
            throw new AuthenticationException(message, e);
        } finally {
            JdbcUtils.closeConnection(conn);
        }

        return info;

    }*/
}
