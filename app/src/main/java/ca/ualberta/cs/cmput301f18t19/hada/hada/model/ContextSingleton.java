package ca.ualberta.cs.cmput301f18t19.hada.hada.model;

import android.content.Context;

public class ContextSingleton {
    private Context context;
    private ContextSingleton(){}

    private static class LazyHolder{
        static final ContextSingleton INSTANCE = new ContextSingleton();
    }

    public static ContextSingleton getInstance(){
        return LazyHolder.INSTANCE;
    }
    public void setContext(Context context){
        this.context = context;
    }
    public Context getContext(){
        return this.context;
    }
}
