package com.monkeyk.os.domain.users;

import lombok.Data;
import org.apache.shiro.subject.SimplePrincipalCollection;
import org.springframework.boot.test.autoconfigure.data.jdbc.DataJdbcTest;

@Data
public class UserDetailInfo extends SimplePrincipalCollection {
    String userName;

}
