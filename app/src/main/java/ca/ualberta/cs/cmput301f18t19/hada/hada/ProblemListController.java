package ca.ualberta.cs.cmput301f18t19.hada.hada;

public class ProblemListController {
    private ProblemList problemList = null;

    public ProblemList getProblemList(Patient patient){
        problemList = patient.getProblems();
        return problemList;
    }
    public void addProblemEntry(Problem problem){
        problemList.addProblem(problem);
        problemList.notifyListeners();
    }
    public void deleteProblemEntry(Problem problem){
        problemList.deleteProblem(problem);
    }

}
