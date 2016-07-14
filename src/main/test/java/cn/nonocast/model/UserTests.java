package cn.nonocast.model;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.Assert;


public class UserTests {
    private static final Logger logger = LoggerFactory.getLogger(UserTests.class);

    @Test
    public void testEnumFromString() {
        Assert.isTrue(User.Role.ADMIN == User.Role.valueOf("ADMIN"));
        Assert.isTrue(User.Role.ADMIN == User.Role.valueOf("admin"));
        Assert.isTrue(User.Role.USER == User.Role.valueOf("USER"));
        Assert.isTrue(User.Role.USER == User.Role.valueOf("user"));
    }
}
