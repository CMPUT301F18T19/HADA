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

import java.time.LocalDateTime;

class Problem {

    private String title;
    private LocalDateTime date;
    private String description;
    private RecordList records = new RecordList();

    public void setTitle(String newTitle) {
        this.title = newTitle;
    }

    public String getTitle() {
        return this.title;
    }

    public void setDate(LocalDateTime newDate) {
        this.date = newDate;
    }

    public LocalDateTime getDate() {
        return this.date;
    }

    public void setDesc(String newDesc) {
        this.description = newDesc;
    }

    public String getDesc() {
        return this.description;
    }

    public void setRecords(RecordList records){
        this.records = records;
    }

    public RecordList getRecords(){
        return this.records;
    }
}