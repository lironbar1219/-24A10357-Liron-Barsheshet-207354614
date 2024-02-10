package com.example.myfirstapp.Logic;

import java.util.ArrayList;

public class GameManager {
    private int life;
    private int numRows;
    private int numCols;
    private int objectposI;
    private ArrayList<FallingObject> fallingObjects = new ArrayList<>();

    private int posSpaceShip;

    public GameManager(int life, int numRows, int numCols) {
        this.life = life;
        this.numRows = numRows;
        this.numCols = numCols;
        this.posSpaceShip = 1;
    }

    public int getPosSpaceShip() {
        return posSpaceShip;
    }

    public ArrayList<FallingObject> getFallingObjects() {
        return fallingObjects;
    }

    public void moveLeft() {
        if(posSpaceShip != 0) {
            posSpaceShip -= 1;
        }
    }

    public void moveRight() {
        if(posSpaceShip != numCols-1) {
            posSpaceShip +=1;
        }
    }

    public void moveDownAllObjects() {
        for(int i = 0; i < fallingObjects.size(); i++) {
            fallingObjects.get(i).setposI(fallingObjects.get(i).getposI()+1);
        }
    }

    public void dropObject() {
        int posI = -1;
        int posJ = -1;
        Boolean avail;


        posJ = (int)(Math.random() * numCols);
        /*
        do {
            avail = true;
            posJ = (int)(Math.random() * numCols);
            for(int i = 0; i < fallingObjects.size();i++) {
                if(fallingObjects.get(i).getposI() == posI + 1) {
                    if (posJ == 0) {
                        if (fallingObjects.get(i).getposJ() == posJ + 1) {
                            avail = true;
                        }
                    } else if (posJ == numCols - 1) {
                        if (fallingObjects.get(i).getposJ() == posJ - 1) {
                            avail = true;
                        }
                    } else {
                        if ((fallingObjects.get(i).getposJ() == posJ + 1) ||  (fallingObjects.get(i).getposJ() == posJ - 1)) {
                            avail = true;
                        }
                    }
                }
            }
        } while(!avail);
        */
        fallingObjects.add(new FallingObject(posI, posJ));
    }

    public void setLife(int life) {
        this.life = life;
        if(this.life == 0) {  // Only for now to make endless game
            this.life =3;
        }
    }

    public int getLife() {
        return this.life;
    }
}
