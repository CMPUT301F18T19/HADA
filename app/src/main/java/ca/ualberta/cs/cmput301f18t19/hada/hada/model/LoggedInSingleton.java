package ca.ualberta.cs.cmput301f18t19.hada.hada.model;

public class LoggedInSingleton {

        private String loggedInID = null;
        private LoggedInSingleton() {}

        private static class LazyHolder {
            static final LoggedInSingleton INSTANCE = new LoggedInSingleton();
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
    }
