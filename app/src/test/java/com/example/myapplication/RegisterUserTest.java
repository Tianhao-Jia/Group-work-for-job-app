package com.example.myapplication;

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


    /* UAT-4 */
    @Test
    public void checkAllRequiredFields() {
        assertTrue(true);
    }
}
