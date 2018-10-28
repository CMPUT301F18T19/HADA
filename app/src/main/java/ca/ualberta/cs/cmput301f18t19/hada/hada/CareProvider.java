package ca.ualberta.cs.cmput301f18t19.hada.hada;

public class CareProvider extends User {

    private String type = "Care Provider";

    @Override
    public String getType() {
        return this.type;
    }
}
