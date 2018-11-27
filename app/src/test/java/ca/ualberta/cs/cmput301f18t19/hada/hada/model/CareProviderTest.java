package ca.ualberta.cs.cmput301f18t19.hada.hada.model;

import org.junit.Test;


import java.util.ArrayList;


import static org.junit.Assert.*;

// TODO stub method
public class CareProviderTest {

    @Test
    public void testSetUserID(){
        CareProvider careProvider = new CareProvider();
        careProvider.setUserID("Test CP User ID");
        assertEquals("Test CP User ID", careProvider.getUserID());
    }

    @Test
    public void testGetUserID(){
        CareProvider careProvider = new CareProvider();
        careProvider.setUserID("Test User Id");
        String returnedID = careProvider.getUserID();
        assertEquals("Test User Id",returnedID);
    }


    @Test
    public void testSetPhoneNumber(){
        CareProvider careProvider = new CareProvider();
        careProvider.setPhoneNumber("7801234567");
        assertEquals("7801234567", careProvider.getPhoneNumber());
    }

    @Test
    public void testGetPhoneNumber(){
        CareProvider careProvider = new CareProvider();
        careProvider.setPhoneNumber("7801234567");
        String returnedPhoneNumber = careProvider.getPhoneNumber();
        assertEquals("7801234567", returnedPhoneNumber);
    }

    @Test
    public void testSetEmailAddress(){
        CareProvider careProvider = new CareProvider();
        careProvider.setEmailAddress("test@ualberta.ca");
        assertEquals("test@ualberta.ca", careProvider.getEmailAddress());
    }

    @Test
    public void testGetEmailAddress(){
        CareProvider careProvider = new CareProvider();
        careProvider.setEmailAddress("test@ualberta.ca");
        String returnedEmailAddress = careProvider.getEmailAddress();
        assertEquals("test@ualberta.ca", returnedEmailAddress);
    }
}
