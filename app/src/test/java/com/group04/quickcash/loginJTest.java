package com.group04.quickcash;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;



public class loginJTest {
    static LoginActivity login;
    @BeforeClass

    public static void setup() {
        login = new LoginActivity();
    }

    @AfterClass

    public  static void tearDown(){
        System.gc();
    }

    @Test
    public void loginSuccess(){

    }



}
