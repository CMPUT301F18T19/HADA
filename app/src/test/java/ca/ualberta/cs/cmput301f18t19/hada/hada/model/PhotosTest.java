/*
 *  CMPUT 301 - Fall 2018
 *
 *  PhotosTest.java
 *
 *  12/2/18 2:37 PM
 *
 *  This is a group project for CMPUT 301 course at the University of Alberta
 *  Copyright (C) 2018  Austin Goebel, Anders Johnson, Alex Li,
 *  Cristopher Penner, Joseph Potentier-Neal, Jason Robock
 */

package ca.ualberta.cs.cmput301f18t19.hada.hada.model;

import android.content.Intent;
import android.location.Location;
import android.support.annotation.NonNull;

import com.google.android.gms.maps.model.LatLng;

import org.junit.Before;
import org.junit.Test;

import java.time.LocalDateTime;
import java.time.Month;
import java.util.ArrayList;

import static org.junit.Assert.*;

/**
 * Tests for the class Problem.
 *
 * @author Christopher Penner
 * @see Photos
 */
public class PhotosTest {

    private Photos photos;

    @Before
    public void setup() {
        this.photos = new Photos();
        this.photos.setFileID("testID");
        this.photos.setParentId("testParent");
        ArrayList<String> bitmaps = new ArrayList<>();
        bitmaps.add("sample string");
        this.photos.setBitmaps(bitmaps);
    }

    @Test
    public void getFileID() {
        assertEquals("testID", this.photos.getFileID());
    }

    @Test
    public void setFileID() {
        this.photos.setFileID("program_set_ID");
        assertEquals("program_set_ID", this.photos.getFileID());
    }

    @Test
    public void getParentId() {
        assertEquals("testParent", this.photos.getParentId());
    }

    @Test
    public void setParentId() {
        this.photos.setParentId("program_set_Parent");
        assertEquals("program_set_Parent", this.photos.getParentId());
    }

    @Test
    public void getBitmaps() {
        assertEquals(1, this.photos.getBitmaps().size());
    }

    @Test
    public void setBitmaps() {
        this.photos.setBitmaps(new ArrayList<String>());
        assertEquals(0, this.photos.getBitmaps().size());
    }
}
