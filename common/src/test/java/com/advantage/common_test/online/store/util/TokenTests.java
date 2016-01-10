package com.advantage.common_test.online.store.util;

import com.advantage.common.Token;
import com.advantage.common.TokenJWT;
import com.advantage.common.dto.AccountType;
import com.advantage.common.exceptions.token.SignatureAlgorithmException;
import org.junit.Assert;
import org.junit.Test;

//import org.springframework.beans.factory.annotation.Autowired;

//@RunWith(SpringJUnit4ClassRunner.class)
//@ContextConfiguration(classes = {AdvantageTestContextConfiguration.class})
public class TokenTests {
    private static final long USER_ID = 1234;
    private static final String LOGIN_NAME = "loginName";
    private static final AccountType ROLE = AccountType.ADMIN;
    private static final String completeToken = "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzUxMiJ9.eyJpc3MiOiJ3d3cuYWR3YW50YWdlb25saW5lc2hvcHBpbmcuY29tIiwidXNlcklkIjoxMjM0LCJsb2dpbk5hbWUiOiJsb2dpbk5hbWUiLCJyb2xlIjoiQURNSU4ifQ.Rf6SwLLQejSneHGg53KgMFNeMDbTykO2NJqkh8-tggp8PzWDmFdAyaULORp8iUHprcMXPV8n1rqtWwC9l30WdQ";
    private static final String fakeToken256 = "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJpc3MiOiJ3d3cuYWR3YW50YWdlb25saW5lc2hvcHBpbmcuY29tIiwidXNlcklkIjoxMjM0LCJsb2dpbk5hbWUiOiJsb2dpbk5hbWUiLCJyb2xlIjoiQURNSU4ifQ.3t9bi1UUP95YLdaYZHmGcdpWi8gGRykh2c0lrp6e9so";
//    @Autowired
//    private Environment environment;

    @Test
    public void testBuildToken() throws Exception {
        Token token = new TokenJWT(USER_ID, LOGIN_NAME, ROLE);
        Assert.assertEquals(completeToken, token.generateToken());
        System.out.println("Test testBuildToken complete successful");
    }

    @Test
    public void testParserToken() throws Exception {
        Token token = new TokenJWT(completeToken);
        Assert.assertEquals(USER_ID, token.getUserId());
        Assert.assertEquals(LOGIN_NAME, token.getLoginName());
        Assert.assertEquals(ROLE, token.getAccountType());
        System.out.println("Test testParserToken complete successful");
    }

    @Test(expected = SignatureAlgorithmException.class)
    public void testNegative256ParserToken() throws Exception {
        Token token = new TokenJWT(fakeToken256);
    }
}
