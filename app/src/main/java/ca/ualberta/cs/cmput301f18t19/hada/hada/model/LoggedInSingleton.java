package ca.ualberta.cs.cmput301f18t19.hada.hada.model;

import ca.ualberta.cs.cmput301f18t19.hada.hada.manager.ESUserManager;

/**
 * Singleton for dealing with the currently logged in user.
 *
 * @version 1.0
 * @author Joe
 * @see CareProvider
 * @see Patient
 * @see ESUserManager
 */
public class LoggedInSingleton {

    //True if careProvider
    private Boolean isCareProvider = false;
    private String loggedInID = null;

    private LoggedInSingleton() {}

    private static class LazyHolder {
        /**
         * An instance of LoggedInSingleton initialized at login.
         */
        static final LoggedInSingleton INSTANCE = new LoggedInSingleton();
    }

    /**
     * Returns an instance of LoggedInSingleton to perform other tasks with.
     *
     * @return the instance
     */
    public static LoggedInSingleton getInstance() {
        return LazyHolder.INSTANCE;
    }

    /**
     * Sets the userID of the user currently logged in.
     *
     * @param userID the user id
     */
    public void setLoggedInID(String userID){
        this.loggedInID = userID;
    }

    /**
     * Returns the userID of the user currently logged in.
     *
     * @return the string
     */
    public String getLoggedInID(){
        return this.loggedInID;
    }

    /**
     * Sets whether the user is a Patient or a CareProvider via a boolean
     * Defaults to false --> Patient
     * True --> CareProvider
     *
     * @param state the state
     */
//If called then logged in user is a care provider
    public void setIsCareProvider(Boolean state){
        this.isCareProvider = state;
    }

    /**
     * Returns the boolean for whether the user is a CareProvider.
     *
     * @return the boolean
     */
    public boolean getIsCareProvider(){
        return isCareProvider;
    }


}