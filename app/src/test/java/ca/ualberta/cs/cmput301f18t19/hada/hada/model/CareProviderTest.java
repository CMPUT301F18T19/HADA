package ca.ualberta.cs.cmput301f18t19.hada.hada.model;

import org.junit.Test;


import java.util.ArrayList;


import static org.junit.Assert.*;

public class CareProviderTest {

    private CareProvider setup() {
        CareProvider careProvider = new CareProvider();
        careProvider.setUserID("Test User Id");
        careProvider.setPhoneNumber("7801234567");
        careProvider.setEmailAddress("test@ualberta.ca");
        return careProvider;
    }

    @Test
    public void testSetUserID(){
        CareProvider careProvider = setup();
        careProvider.setUserID("Test CP User ID");
        assertEquals("Test CP User ID", careProvider.getUserID());
    }

    @Test
    public void testGetUserID(){
        CareProvider careProvider = setup();
        String returnedID = careProvider.getUserID();
        assertEquals("Test User Id",returnedID);
    }


    @Test
    public void testSetPhoneNumber(){
        CareProvider careProvider = setup();
        careProvider.setPhoneNumber("1809994466");
        assertEquals("1809994466", careProvider.getPhoneNumber());
    }

    @Test
    public void testGetPhoneNumber(){
        CareProvider careProvider = setup();
        String returnedPhoneNumber = careProvider.getPhoneNumber();
        assertEquals("7801234567", returnedPhoneNumber);
    }

    @Test
    public void testSetEmailAddress(){
        CareProvider careProvider = setup();
        careProvider.setEmailAddress("testSet@ualberta.ca");
        assertEquals("testSet@ualberta.ca", careProvider.getEmailAddress());
    }

    @Test
    public void testGetEmailAddress(){
        CareProvider careProvider = setup();
        String returnedEmailAddress = careProvider.getEmailAddress();
        assertEquals("test@ualberta.ca", returnedEmailAddress);
    }
}
