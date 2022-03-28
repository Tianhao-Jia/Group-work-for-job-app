package com.example.myapplication;

import android.text.TextUtils;

import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class HaversineDistance {

    //this class will test the HaversineDistance method in the Location class.

    @BeforeClass
    public static void setUpBeforeClass() {
        System.out.println("Set up before class");
    }

    @Before
    public void setUp() throws Exception {
        System.out.println("Set up");
    }

    @Test
    public void haversineDistanceLocationOppositePoles() {
        Location locationA = new Location(-90, -180);
        Location locationB = new Location(90, 180);

        double distance = locationA.getHaversineDistance(locationB);
        double earthCircumference = 40075;

        Assert.assertTrue("different distances than expected", distance <= (earthCircumference / 2));
    }

    @Test
    public void haversineDistanceLocationSameSpot() {
        Location locationA = new Location(90, 180);
        Location locationB = new Location(90, 180);

        double distance = locationA.getHaversineDistance(locationB);

        Assert.assertTrue("different distances than expected", distance == 0);
    }
}
