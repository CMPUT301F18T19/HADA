package ca.ualberta.cs.cmput301f18t19.hada.hada.controller;

import org.junit.Test;

import ca.ualberta.cs.cmput301f18t19.hada.hada.model.Problem;

import static org.junit.Assert.*;

public class ProblemControllerTest {

    @Test
    public void testIsEmpty() {
        ProblemController problemList = new ProblemController();
        assertEquals(true, problemList.isEmpty());
        Problem problem = new Problem();
        problemList.addProblem(problem);
        assertEquals(false, problemList.isEmpty());
    }

    @Test
    public void testAddProblem() {
        ProblemController problemList = new ProblemController();
        assertTrue(problemList.isEmpty());
        Problem problem = new Problem();
        problem.setTitle("test problem");
        problemList.addProblem(problem);
        assertFalse(problemList.isEmpty());
    }

    @Test
    public void testGetProblem() {
        ProblemController problemList = new ProblemController();
        Problem problem = new Problem();
        problem.setTitle("test problem");
        problemList.addProblem(problem);
        assertEquals(problem, problemList.getProblem(0));
        assertEquals(problem.getTitle(), problemList.getProblem(0).getTitle());
    }

    @Test
    public void testGetSize() {
        ProblemController problemList = new ProblemController();
        assertEquals(0, problemList.getSize());
        Problem problem = new Problem();
        problemList.addProblem(problem);
        assertEquals(1, problemList.getSize());
    }

    @Test
    public void testDeleteProblem() {
        ProblemController problemList = new ProblemController();
        Problem problem1 = new Problem();
        Problem problem2 = new Problem();
        problemList.addProblem(problem1);
        problemList.addProblem(problem2);
        assertEquals(2, problemList.getSize());
        problemList.deleteProblem(problem1);
        assertEquals(1, problemList.getSize());
        assertEquals(problem2, problemList.getProblem(0));
    }

    @Test
    public void testGetPosition() {
        ProblemController problemList = new ProblemController();
        Problem problem1 = new Problem();
        Problem problem2 = new Problem();
        problemList.addProblem(problem1);
        problemList.addProblem(problem2);
        assertEquals(1, problemList.getPos(problem2));
        problemList.deleteProblem(problem2);
        assertEquals(-1, problemList.getPos(problem2));
    }

    @Test
    public void testInsertProblem() {
        ProblemController problemList = new ProblemController();
        Problem problem1 = new Problem();
        Problem problem2 = new Problem();
        problemList.addProblem(problem1);
        problemList.insertProblem(0, problem2);
        assertEquals(0, problemList.getPos(problem2));
        assertEquals(problem2, problemList.getProblem(0));
    }

    @Test
    public void testProblemInList() {
        ProblemController problemList = new ProblemController();
        Problem problem1 = new Problem();
        Problem problem2 = new Problem();
        Problem problem3 = new Problem();
        problemList.addProblem(problem1);
        problemList.addProblem(problem2);
        assertTrue(problemList.inList(problem1));
        assertFalse(problemList.inList(problem3));
        problemList.addProblem(problem3);
        assertTrue(problemList.inList(problem3));
    }
}
