package ca.ualberta.cs.cmput301f18t19.hada.hada.model;

public class LoggedInSingleton {

        //True if careProvider
        private Boolean isCareProvider = false;
        private String loggedInID = null;

        private LoggedInSingleton() {}

    private LoggedInSingleton() {}

        public void setLoggedInID(String userID){
            this.loggedInID = userID;
        }
        public String getLoggedInID(){
            return this.loggedInID;
        }

        //If called then logged in user is a care provider
        public void setIsCareProvider(Boolean state){
            this.isCareProvider = state;
        }
        public boolean getIsCareProvider(){
            return isCareProvider;
        }


    }
    public static LoggedInSingleton getInstance() {
        return LazyHolder.INSTANCE;
    }

    public void setLoggedInID(String userID){
        this.loggedInID = userID;
    }
    public String getLoggedInID(){
        return this.loggedInID;
    }

    //If called then logged in user is a care provider
    public void setIsCareProvider(Boolean state){
        this.isCareProvider = state;
    }
    public boolean getIsCareProvider(){
        return isCareProvider;
    }


}