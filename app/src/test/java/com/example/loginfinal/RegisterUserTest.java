package com.example.loginfinal;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class RegisterUserTest {
    static RegisterUser registerActivity;

    @BeforeClass
    public static void setup() {
        registerActivity = new RegisterUser();
    }

    @AfterClass
    public static void tearDown() {
        System.gc();
    }

    @Test
    public void validFirstName() {
        assertTrue(registerActivity.checkFirstName("George"));
    }

    @Test
    public void invalidFirstName() {
        assertFalse(registerActivity.checkFirstName("GMoney$$$"));
    }

    @Test
    public void validLastName() {
        assertTrue(registerActivity.checkFirstName("Smith"));
    }

    @Test
    public void validEmail() {
        assertTrue(registerActivity.checkEmail("george.smith@dal.ca"));
    }

    @Test
    public void invalidEmail() {
        assertFalse(registerActivity.checkEmail("notAmEmail"));
    }
}
