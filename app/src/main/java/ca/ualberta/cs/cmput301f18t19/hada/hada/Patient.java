/* CMPUT 301 - Fall 2018
 *
 * Version 1.0
 *
 * 2018-10-27
 *
 * This is a group project for CMPUT 301 course at the University of Alberta
 * Copyright (C) 2018  Austin Goebel, Anders Johnson, Alex Li,
 * Cristopher Penner, Joseph Potentier-Neal, Jason Robock
 */

package ca.ualberta.cs.cmput301f18t19.hada.hada;


/**
 * @author Joseph Potentier-Neal
 * @see
 * @version
 */
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
