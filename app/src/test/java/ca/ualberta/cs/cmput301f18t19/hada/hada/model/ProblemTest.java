/*
 *  CMPUT 301 - Fall 2018
 *
 *  ProblemTest.java
 *
 *  11/27/18 3:34 PM
 *
 *  This is a group project for CMPUT 301 course at the University of Alberta
 *  Copyright (C) 2018  Austin Goebel, Anders Johnson, Alex Li,
 *  Cristopher Penner, Joseph Potentier-Neal, Jason Robock
 */

package ca.ualberta.cs.cmput301f18t19.hada.hada.model;

import android.support.annotation.NonNull;

import org.junit.Test;
import static org.junit.Assert.*;
import java.time.LocalDateTime;
import java.time.Month;
import java.util.ArrayList;

/**
 * Tests for the class Problem.
 *
 * @see Problem
 * @author Christopher Penner
 */
public class ProblemTest{

    /**
     * Setup creates a pre defined Problem to test getters
     */
    @NonNull
    private Problem setup() {
        return new Problem("Title",
                LocalDateTime.of(1975,
                        Month.JANUARY,
                        31,
                        23, 59,59),
                "Testing desc");
    }

    /**
     * Test set title.
     */
    @Test
    public void testSetTitle(){
        Problem problem = setup();
        problem.setTitle("Red mark on arm");
        assertEquals("Red mark on arm", problem.getTitle());
    }

    /**
     * Test get title.
     */
    @Test
    public void testGetTitle(){
        Problem problem = setup();
        String returnedTitle =  problem.getTitle();
        assertEquals("Title", returnedTitle);
    }

    /**
     * Test set date started.
     */
    @Test
    public void testSetDateStarted(){
        Problem problem = setup();
        LocalDateTime date = LocalDateTime.now();
        problem.setDate(date);
        assertEquals(date,problem.getDate());
    }

    /**
     * Test get date started.
     */
    @Test
    public void testGetDateStarted(){
        Problem problem = setup();
        LocalDateTime date = LocalDateTime.of(1975, Month.JANUARY, 31,
                23, 59,59);
        LocalDateTime returnedDate = problem.getDate();
        assertEquals(date,returnedDate);
    }

    /**
     * Test set desc.
     */
    @Test
    public void testSetDesc(){
        Problem problem = setup();
        problem.setDesc("This is a test desc for this problem");
        assertEquals("This is a test desc for this problem", problem.getDesc());
    }

    /**
     * Test get desc.
     */
    @Test
    public void testGetDesc(){
        Problem problem = setup();
        String returnedDesc = problem.getDesc();
        assertEquals("Testing desc", returnedDesc);
    }
}
