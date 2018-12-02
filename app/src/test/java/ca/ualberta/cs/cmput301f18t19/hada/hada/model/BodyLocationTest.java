/*
 *  CMPUT 301 - Fall 2018
 *
 *  BodyLocationTest.java
 *
 *  12/2/18 11:26 AM
 *
 *  This is a group project for CMPUT 301 course at the University of Alberta
 *  Copyright (C) 2018  Austin Goebel, Anders Johnson, Alex Li,
 *  Cristopher Penner, Joseph Potentier-Neal, Jason Robock
 */

package ca.ualberta.cs.cmput301f18t19.hada.hada.model;

import android.support.annotation.NonNull;
import android.util.Log;

import org.junit.Before;
import org.junit.Test;

import java.util.UUID;

import ca.ualberta.cs.cmput301f18t19.hada.hada.manager.ESUserManager;
import ca.ualberta.cs.cmput301f18t19.hada.hada.model.LoggedInSingleton;
import ca.ualberta.cs.cmput301f18t19.hada.hada.model.Patient;
import ca.ualberta.cs.cmput301f18t19.hada.hada.model.Problem;

import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.withSettings;

/**
 * Tests for the class BodyLocation.
 *
 * @author Christopher Penner
 * @see BodyLocation
 */
public class BodyLocationTest {

    private BodyLocation bodyLocation;

    /**
     * Setup creates a pre defined BodyLocation to test getters
     */
    @Before
    public void setup() {
        this.bodyLocation = new BodyLocation();
        this.bodyLocation.setPhotoUri("///:filesystem");
        this.bodyLocation.setPhotoHTTP("www.test.gov");
        this.bodyLocation.setBodyLocation("test_bodyLocation");
        this.bodyLocation.setParentID("testParent");
    }


    /**
     * Test get file id.
     */
    @Test
    public void testGetFileID() {
        assertNotNull(this.bodyLocation.getFileID());
    }

    /**
     * Test set file id.
     */
    @Test
    public void testSetFileID() {
        String uuid = UUID.randomUUID().toString();
        this.bodyLocation.setFileID(uuid);
        assertEquals(uuid, this.bodyLocation.getFileID());
    }

    /**
     * Test get photo uri.
     */
    @Test
    public void testGetPhotoUri() {
        assertEquals("///:filesystem", this.bodyLocation.getPhotoUri());
    }

    /**
     * Test set photo uri.
     */
    @Test
    public void testSetPhotoUri() {
        this.bodyLocation.setPhotoUri("///:SDcard/folder");
        assertEquals("///:SDcard/folder", this.bodyLocation.getPhotoUri());
    }

    /**
     * Test get photo http.
     */
    @Test
    public void testGetPhotoHTTP() {
        assertEquals("www.test.gov", this.bodyLocation.getPhotoHTTP());
    }

    /**
     * Test set photo http.
     */
    @Test
    public void testSetPhotoHTTP() {
        this.bodyLocation.setPhotoHTTP("www.elasticSearch.com");
        assertEquals("www.elasticSearch.com", this.bodyLocation.getPhotoHTTP());
    }

    /**
     * Test get body location.
     */
    @Test
    public void testGetBodyLocation() {
        assertEquals("test_bodyLocation", this.bodyLocation.getBodyLocation());
    }

    /**
     * Test set body location.
     */
    @Test
    public void testSetBodyLocation() {
        this.bodyLocation.setBodyLocation("program_set_BL");
        assertEquals("program_set_BL", this.bodyLocation.getBodyLocation());
    }

    /**
     * Test get parent id.
     */
    @Test
    public void testGetParentID() {
        assertEquals("testParent", this.bodyLocation.getParentID());
    }

    /**
     * Test set parent id.
     */
    @Test
    public void testSetParentID() {
        this.bodyLocation.setParentID("program_set_parent");
        assertEquals("program_set_parent", this.bodyLocation.getParentID());
    }
}
