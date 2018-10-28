package ca.ualberta.cs.cmput301f18t19.hada.hada;

public abstract class User {

    private String userID;
    private String phoneNumber;
    private String emailAddress;

    public void setUserID(String newID){
        this.userID = newID;
    }

    public String getUserID(){
        return this.userID;
    }

    public void setPhoneNumber(String newPhoneNumber){
        this.phoneNumber = newPhoneNumber;
    }

    public String getPhoneNumber(){
        return this.phoneNumber;
    }

    public void setEmailAdress(String newEmail){
        this.emailAddress = newEmail;
    }

    public String getEmailAddress(){
        return this.emailAddress;
    }

    abstract public String getType();

}
