package com.killme;

import javax.swing.JButton;

public class MineButton extends JButton{
  private boolean mine,revealed,flagged;
  private int adjacentMines;
  
  public MineButton(){
    super();
    mine = false; 
    adjacentMines = 0;
    revealed = false;
    flagged = false;
  }
  
  public MineButton(boolean mineState,int closeMines,boolean revealedState,boolean flagState){
    super();
    mine = mineState; 
    adjacentMines = closeMines;
    revealed = revealedState;
    flagged = flagState;
  }
  
  public boolean isMine(){
    return this.mine;
  }
  
  public void setMine(boolean mineState){
    this.mine = mineState;
  }
  
  public void setAdjacentMines(int mines){
    this.adjacentMines = mines;
  }
  
  public int getAdjacentMines(){
    return adjacentMines;
  }
  
  public void setRevealed(boolean revealedState){
    this.revealed = revealedState;
  }
  
  public boolean isRevealed(){
    return revealed;
  }
  
  public void setFlagged(boolean flaggedState){
    this.flagged = flaggedState;
  }
  
  public boolean isFlagged(){
    return flagged;
  }
}
