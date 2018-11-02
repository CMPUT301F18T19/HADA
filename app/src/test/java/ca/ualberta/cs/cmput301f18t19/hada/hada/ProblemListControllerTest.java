package ca.ualberta.cs.cmput301f18t19.hada.hada;

import org.junit.Test;

import static org.junit.Assert.*;

public class ProblemListControllerTest {

    @Test
    public void testIsEmpty() {
        ProblemListController problemList = new ProblemListController();
        assertEquals(true, problemList.isEmpty());
        Problem problem = new Problem();
        problemList.addProblem(problem);
        assertEquals(false, problemList.isEmpty());
    }

    @Test
    public void testAddProblem() {
        ProblemListController problemList = new ProblemListController();
        assertTrue(problemList.isEmpty());
        Problem problem = new Problem();
        problem.setTitle("test problem");
        problemList.addProblem(problem);
        assertFalse(problemList.isEmpty());
    }

    @Test
    public void testGetProblem() {
        ProblemListController problemList = new ProblemListController();
        Problem problem = new Problem();
        problem.setTitle("test problem");
        problemList.addProblem(problem);
        assertEquals(problem, problemList.getProblem(0));
        assertEquals(problem.getTitle(), problemList.getProblem(0).getTitle());
    }

    @Test
    public void testGetSize() {
        ProblemListController problemList = new ProblemListController();
        assertEquals(0, problemList.getSize());
        Problem problem = new Problem();
        problemList.addProblem(problem);
        assertEquals(1, problemList.getSize());
    }

    @Test
    public void testDeleteProblem() {
        ProblemListController problemList = new ProblemListController();
        Problem problem1 = new Problem();
        Problem problem2 = new Problem();
        problemList.addProblem(problem1);
        problemList.addProblem(problem2);
        assertEquals(2, problemList.getSize());
        problemList.deleteProblem(0);
        assertEquals(1, problemList.getSize());
        assertEquals(problem2, problemList.getProblem(0));
    }

    @Test
    public void testGetPosition() {
        ProblemListController problemList = new ProblemListController();
        Problem problem1 = new Problem();
        Problem problem2 = new Problem();
        problemList.addProblem(problem1);
        problemList.addProblem(problem2);
        assertEquals(1, problemList.getPos(problem2));
        problemList.deleteProblem(1);
        assertEquals(-1, problemList.getPos(problem2));
    }

    @Test
    public void testInsertProblem() {
        ProblemListController problemList = new ProblemListController();
        Problem problem1 = new Problem();
        Problem problem2 = new Problem();
        problemList.addProblem(problem1);
        problemList.insertProblem(0, problem2);
        assertEquals(0, problemList.getPos(problem2));
        assertEquals(problem2, problemList.getProblem(0));
    }

    @Test
    public void testProblemInList() {
        ProblemListController problemList = new ProblemListController();
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
