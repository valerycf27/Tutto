/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cards;


/**
 *
 * @author 34649
 */
public class Card {

    private boolean used;
    private String name;
    
    public Card(String name){
        this.used=false;
        this.name=name;
    }

    public void setUsed(boolean used) {
        this.used = used;
    }

    public boolean isUsed() {
        return used;
    }

    public String getName() {
        return name;
    }
    
}
