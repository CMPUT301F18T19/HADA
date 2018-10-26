package ca.ualberta.cs.cmput301f18t19.hada.hada;

public class Patient extends User {


    //TODO Figure out a better way to implement this private value.
    private String type = "Patient";

    @Override
    public String getType() {
        return this.type;
    }
    //TODO setFullBodyImage() once we know of the data type/structure.
    //public void setFullBodyImage()


}
