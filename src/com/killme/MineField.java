package com.killme;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.time.Duration;
import java.time.Instant;
import java.util.ArrayList;


public class MineField{
  private boolean gameInProgress = false,gameOver = false;
  private static final int tileSize = 20;
  private JFrame gameWindow;
  private JPanel gamePanel;
  private JMenuItem jmiMinesCustom;
  private JLabel mineCounter;
  private ArrayList<ArrayList<MineButton>> field;
  private int fieldXSize,fieldYSize;
  private final GridBagConstraints gbc = new GridBagConstraints();
  private JButton startButton;
  private static int indexX,indexY,mineCount,minesLeft,mineConcentration = 15;  
  private final Font mineFont = new Font("Tahoma",Font.BOLD,25);
  private Instant start;
  private ImageIcon oneTile,twoTile,threeTile,fourTile,fiveTile,sixTile,sevenTile,eightTile,mineTile,flagTile,hiddenTile,revealedTile,winnerTile,aliveTile,deadTile,wrongFlagTile;
  
  public MineField(){
    initGUI();
  }
  
  private void initGUI(){
    gameWindow = new JFrame();
    
    //panels
    JPanel basePanel = new JPanel();
    JPanel topPanel = new JPanel();
    gamePanel = new JPanel();
    JPanel startPanel = new JPanel();
    JPanel menuPanel = new JPanel();
    
    
    
      

    
    
    
    //menus
    JMenu settingsMenu = new JMenu("Settings");
    JMenu sizeMenu = new JMenu("Field size");
    JMenu mineMenu = new JMenu("Mine count");
    JMenu helpMenu = new JMenu("Help");
    
    //menu items
    JMenuItem jmiWindowSmall = new JMenuItem("Small(20x20)");
    JMenuItem jmiWindowMedium = new JMenuItem("Medium(20x40)");
    JMenuItem jmiWindowLarge = new JMenuItem("Large(40x40)");
    JMenuItem jmiWindowCustom = new JMenuItem("Custom");
    JMenuItem jmiMinesEasy = new JMenuItem("Easy(15%)");
    JMenuItem jmiMinesMedium = new JMenuItem("Medium(20%)");
    JMenuItem jmiMinesHard = new JMenuItem("Hard(30%)");
    JMenuItem jmiAbout = new JMenuItem("About");
    
    basePanel.setPreferredSize(gameWindow.getPreferredSize());
    
    topPanel.setPreferredSize(new Dimension(gameWindow.getWidth(),50));
    topPanel.setBackground(new Color(200,200,200));
    
    startPanel.setOpaque(false);
    
    gamePanel.setBackground(new Color(180,180,180));
    
    gamePanel.setLayout(new GridBagLayout());
    
    basePanel.setLayout(new BorderLayout());
    
    topPanel.setLayout(new BorderLayout());    
    
    basePanel.add(topPanel,BorderLayout.PAGE_START);
    basePanel.add(gamePanel,BorderLayout.CENTER);
    
    startButton = new JButton();
    startButton.setPreferredSize(new Dimension(40,40));
    
    mineCounter = new JLabel();
    mineCounter.setFont(mineFont);
    mineCounter.setPreferredSize(new Dimension(60,80));
    
    startPanel.add(settingsMenu);
    startPanel.add(helpMenu);

    JMenuBar menuBar = new JMenuBar();
    
    gameWindow.setJMenuBar(menuBar);
    
    sizeMenu.add(jmiWindowSmall);
    sizeMenu.add(jmiWindowMedium);
    sizeMenu.add(jmiWindowLarge);
    sizeMenu.add(jmiWindowCustom);
    
    mineMenu.add(jmiMinesEasy);
    mineMenu.add(jmiMinesMedium);
    mineMenu.add(jmiMinesHard);
    
    menuBar.add(settingsMenu);
    menuBar.add(helpMenu);
    
    settingsMenu.add(sizeMenu);
    settingsMenu.add(mineMenu);
    
    helpMenu.add(jmiAbout);
    
    topPanel.add(startPanel,BorderLayout.CENTER);
    startPanel.add(startButton);    
    topPanel.add(mineCounter,BorderLayout.LINE_END);
    
    gameWindow.add(basePanel);
    
    //sprites
    aliveTile = new ImageIcon("Sprites/Alive.png");
    aliveTile.setImage(getScaledImage(aliveTile.getImage(),40,40));
    deadTile = new ImageIcon("Sprites/Dead.png");
    deadTile.setImage(getScaledImage(deadTile.getImage(),40,40));
    winnerTile = new ImageIcon("Sprites/Winner.png");
    winnerTile.setImage(getScaledImage(winnerTile.getImage(),40,40));
    
    oneTile = new ImageIcon("Sprites/One_tile.png");
    oneTile.setImage(getScaledImage(oneTile.getImage(),tileSize,tileSize));
    twoTile = new ImageIcon("Sprites/Two_tile.png");
    twoTile.setImage(getScaledImage(twoTile.getImage(),tileSize,tileSize));
    threeTile = new ImageIcon("Sprites/Three_tile.png");
    threeTile.setImage(getScaledImage(threeTile.getImage(),tileSize,tileSize));
    fourTile = new ImageIcon("Sprites/Four_tile.png");
    fourTile.setImage(getScaledImage(fourTile.getImage(),tileSize,tileSize));
    fiveTile = new ImageIcon("Sprites/Five_tile.png");
    fiveTile.setImage(getScaledImage(fiveTile.getImage(),tileSize,tileSize));
    sixTile = new ImageIcon("Sprites/Six_tile.png");
    sixTile.setImage(getScaledImage(sixTile.getImage(),tileSize,tileSize));
    sevenTile = new ImageIcon("Sprites/Seven_tile.png");
    sevenTile.setImage(getScaledImage(sevenTile.getImage(),tileSize,tileSize));
    eightTile = new ImageIcon("Sprites/Eight_tile.png");
    eightTile.setImage(getScaledImage(eightTile.getImage(),tileSize,tileSize));
    mineTile = new ImageIcon("Sprites/Mine_tile.png");
    mineTile.setImage(getScaledImage(mineTile.getImage(),tileSize,tileSize));
    flagTile = new ImageIcon("Sprites/Flag_tile.png");
    flagTile.setImage(getScaledImage(flagTile.getImage(),tileSize,tileSize));
    wrongFlagTile = new ImageIcon("Sprites/Flag_wrong_tile.png");
    wrongFlagTile.setImage(getScaledImage(wrongFlagTile.getImage(),tileSize,tileSize));
    hiddenTile = new ImageIcon("Sprites/Hidden_tile.png");
    hiddenTile.setImage(getScaledImage(hiddenTile.getImage(),tileSize,tileSize));
    revealedTile = new ImageIcon("Sprites/Revealed_tile.png");
    revealedTile.setImage(getScaledImage(revealedTile.getImage(),tileSize,tileSize));
    
    startButton.setIcon(aliveTile);
    
    gameWindow.setBounds(300,200,310,360);
    gameWindow.setMinimumSize(new Dimension(310,360));
    gameWindow.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    gameWindow.setVisible(true);
    gameWindow.setResizable(false);
    
    field = new ArrayList<ArrayList<MineButton>>();
    
    //window sizes 
    jmiWindowSmall.addActionListener(e -> {
      gameWindow.setSize(new Dimension(310,360));
      startButton.doClick();
      startButton.doClick();
    });
    
    jmiWindowMedium.addActionListener(e -> {
      gameWindow.setSize(new Dimension(610,360));
      startButton.doClick();
      startButton.doClick();
    });
    
    jmiWindowLarge.addActionListener(e -> {
      gameWindow.setSize(new Dimension(610,660));
      startButton.doClick();
      startButton.doClick();
    });
    
    jmiWindowCustom.addActionListener(e -> {
      gameWindow.setResizable(true);
      gamePanel.removeAll();
    });
    
    //mine counts
    
    jmiMinesEasy.addActionListener(e -> {
      gamePanel.removeAll();
      mineConcentration = 15;
    });
    
    jmiMinesMedium.addActionListener(e -> {
      gamePanel.removeAll();
      mineConcentration = 20;
    });
    
    jmiMinesHard.addActionListener(e -> {
      gamePanel.removeAll();
      mineConcentration = 30;
    });
    
    jmiAbout.addActionListener(e -> JOptionPane.showMessageDialog(gameWindow,"Made by Tomas Peniak\n2019","About",JOptionPane.PLAIN_MESSAGE));
    
    //startbutton
    startButton.addActionListener(e -> {
    startButton.setIcon(aliveTile);

    fieldXSize = gamePanel.getWidth()/tileSize;
    fieldYSize = gamePanel.getHeight()/tileSize;

    mineCount = (fieldXSize * fieldYSize)/(100/mineConcentration);
    minesLeft = mineCount;

    gameOver = false;
    gameInProgress = false;

    mineCounter.setText("" +  minesLeft);

    gamePanel.removeAll();

    field.clear();

    for (indexX = 0;indexX < fieldXSize;indexX++ ) {
      field.add(new ArrayList<MineButton>());

      for (indexY = 0 ;indexY < fieldYSize;indexY++ ) {
        gbc.gridx = indexX;
        gbc.gridy = indexY;

        field.get(indexX).add(new MineButton());
        gamePanel.add(field.get(indexX).get(indexY),gbc);
        field.get(indexX).get(indexY).setMinimumSize(new Dimension(tileSize,tileSize));
        field.get(indexX).get(indexY).setIcon(hiddenTile);
        field.get(indexX).get(indexY).setFont(mineFont);

        field.get(indexX).get(indexY).addActionListener(new ActionListener(){
          final int buttonX = indexX;
          final int buttonY = indexY;

          public void actionPerformed(ActionEvent e){
            if (!gameOver && !field.get(buttonX).get(buttonY).isFlagged()){
              revealButton(buttonX,buttonY);
            }
          }
        });

          field.get(indexX).get(indexY).addMouseListener(new MouseAdapter(){
            final int buttonX = indexX;
            final int buttonY = indexY;
            boolean pressed;

              public void mousePressed(MouseEvent e) {
                field.get(buttonX).get(buttonY).getModel().setArmed(true);
                field.get(buttonX).get(buttonY).getModel().setPressed(true);
                pressed = true;
              }

              public void mouseReleased(MouseEvent e) {

                field.get(buttonX).get(buttonY).getModel().setArmed(false);
                field.get(buttonX).get(buttonY).getModel().setPressed(false);

                if (pressed && !gameOver) {
                  if (SwingUtilities.isRightMouseButton(e) && !field.get(buttonX).get(buttonY).isFlagged() && !field.get(buttonX).get(buttonY).isRevealed()) {
                    field.get(buttonX).get(buttonY).setIcon(flagTile);
                    field.get(buttonX).get(buttonY).setFlagged(true);
                    minesLeft--;
                  }
                  else if (SwingUtilities.isRightMouseButton(e) && field.get(buttonX).get(buttonY).isFlagged() && !field.get(buttonX).get(buttonY).isRevealed()) {
                    field.get(buttonX).get(buttonY).setIcon(hiddenTile);
                    field.get(buttonX).get(buttonY).setFlagged(false);
                    minesLeft++;
                  }
                  else if (SwingUtilities.isRightMouseButton(e) && field.get(buttonX).get(buttonY).isRevealed() && checkForFlags(buttonX,buttonY) >= field.get(buttonX).get(buttonY).getAdjacentMines()) {
                    revealAdjacentTiles(buttonX,buttonY);
                  }
                  mineCounter.setText("" + minesLeft);
                }
                pressed = false;
              }

              public void mouseExited(MouseEvent e) {
                pressed = false;
              }

              public void mouseEntered(MouseEvent e) {
                pressed = true;
              }
            });
          }
        }

        gameWindow.invalidate();
        gameWindow.validate();
        gameWindow.repaint();
        gameWindow.setResizable(false);
      });
  }
  
  public boolean mineCheck(int coordinateX,int coordinateY){
    return field.get(coordinateX).get(coordinateY).isMine();
  }
  
  public void generateMines(){
    for (int index = 0;index < mineCount;index++) {
      placeRandomMine();
    }
  }
  
  public void placeRandomMine(){
    int randomX = Math.round((float)Math.random() * (fieldXSize-1));
    int randomY = Math.round((float)Math.random() * (fieldYSize-1));
    
    if(!field.get(randomX).get(randomY).isMine()){
      field.get(randomX).get(randomY).setMine(true);
      //field.get(randomX).get(randomY).setIcon(mineTile);         
    }
    
    else{
      placeRandomMine();
    }
  }
  
  public void generateMap(int coordinateX,int coordinateY){
    generateMines();
    field.get(coordinateX).get(coordinateY).setMine(false);
    generateNumbers();
  }
  
  public void generateNumbers(){
    for (indexX = 0;indexX < fieldXSize;indexX++ ) {    
      for (indexY = 0 ;indexY < fieldYSize;indexY++ ) {
        field.get(indexX).get(indexY).setAdjacentMines(checkForMines(indexX,indexY));
      }
    }
  }
  
  public void revealMines(){
    for (indexX = 0;indexX < fieldXSize;indexX++ ) {    
      for (indexY = 0 ;indexY < fieldYSize;indexY++ ) {
        if(field.get(indexX).get(indexY).isMine() && !field.get(indexX).get(indexY).isFlagged()){
          field.get(indexX).get(indexY).setIcon(mineTile);
        }  
        if(!field.get(indexX).get(indexY).isMine() && field.get(indexX).get(indexY).isFlagged()){
          field.get(indexX).get(indexY).setIcon(wrongFlagTile);
        }  
      }
    }
  }
  
  public boolean gameWon(){
    for (indexX = 0;indexX < fieldXSize;indexX++ ) {    
      for (indexY = 0 ;indexY < fieldYSize;indexY++ ) {
        if(!field.get(indexX).get(indexY).isMine() && !field.get(indexX).get(indexY).isRevealed()){
          return false; 
        }  
      }
    }
    return true;
  }
  
  public int checkForMines(int coordinateX,int coordinateY){
    int returnValue = 0;
    
    if(field.get(coordinateX).get(coordinateY).isMine()){
      return 0;      
    }
    
    if(coordinateY-1 >= 0){
      if(field.get(coordinateX).get(coordinateY-1).isMine()){
        returnValue++;
      }
    }
    
    if(coordinateX+1 <= field.size()-1 && coordinateY-1 >= 0){
      if(field.get(coordinateX+1).get(coordinateY-1).isMine()){
        returnValue++;
      }
    }
    
    if(coordinateX+1 <= field.size()-1){
      if(field.get(coordinateX+1).get(coordinateY).isMine()){
        returnValue++;
      }
    }
    
    if(coordinateX+1 <= field.size()-1 && coordinateY+1 <= field.get(coordinateY).size()-1){
      if(field.get(coordinateX+1).get(coordinateY+1).isMine()){
        returnValue++;
      }
    }
    
    if(coordinateY+1 <= field.get(coordinateY).size()-1){
      if(field.get(coordinateX).get(coordinateY+1).isMine()){
        returnValue++;
      }
    }
    
    if(coordinateX-1 >= 0 && coordinateY+1 <= field.get(coordinateY).size()-1){
      if(field.get(coordinateX-1).get(coordinateY+1).isMine()){
        returnValue++;
      }
    }    
    
    if(coordinateX-1 >= 0){
      if(field.get(coordinateX-1).get(coordinateY).isMine()){
        returnValue++;
      }
    }
    
    if(coordinateX-1 >= 0 && coordinateY-1 >= 0){
      if(field.get(coordinateX-1).get(coordinateY-1).isMine()){
        returnValue++;
      }
    }    
    return returnValue;
  }
  
  public int checkForFlags(int coordinateX,int coordinateY){
    int returnValue = 0;
    
    if(field.get(coordinateX).get(coordinateY).isFlagged()){
      return 0;      
    }
    
    if(coordinateY-1 >= 0){
      if(field.get(coordinateX).get(coordinateY-1).isFlagged()){
        returnValue++;
      }
    }
    
    if(coordinateX+1 <= field.size()-1 && coordinateY-1 >= 0){
      if(field.get(coordinateX+1).get(coordinateY-1).isFlagged()){
        returnValue++;
      }
    }
    
    if(coordinateX+1 <= field.size()-1){
      if(field.get(coordinateX+1).get(coordinateY).isFlagged()){
        returnValue++;
      }
    }
    
    if(coordinateX+1 <= field.size()-1 && coordinateY+1 <= field.get(coordinateY).size()-1){
      if(field.get(coordinateX+1).get(coordinateY+1).isFlagged()){
        returnValue++;
      }
    }
    
    if(coordinateY+1 <= field.get(coordinateY).size()-1){
      if(field.get(coordinateX).get(coordinateY+1).isFlagged()){
        returnValue++;
      }
    }
    
    if(coordinateX-1 >= 0 && coordinateY+1 <= field.get(coordinateY).size()-1){
      if(field.get(coordinateX-1).get(coordinateY+1).isFlagged()){
        returnValue++;
      }
    }    
    
    if(coordinateX-1 >= 0){
      if(field.get(coordinateX-1).get(coordinateY).isFlagged()){
        returnValue++;
      }
    }
    
    if(coordinateX-1 >= 0 && coordinateY-1 >= 0){
      if(field.get(coordinateX-1).get(coordinateY-1).isFlagged()){
        returnValue++;
      }
    }    
    return returnValue;
  }
  
  public void revealAdjacentTiles(int coordinateX,int coordinateY){
    
    if(coordinateY-1 >= 0){
      if(!field.get(coordinateX).get(coordinateY-1).isRevealed() && !field.get(coordinateX).get(coordinateY-1).isFlagged()){
        revealButton(coordinateX,coordinateY-1);
      }     
    }
    
    if(coordinateX+1 <= field.size()-1 && coordinateY-1 >= 0){
      if(!field.get(coordinateX+1).get(coordinateY-1).isRevealed() && !field.get(coordinateX+1).get(coordinateY-1).isFlagged()){
        revealButton(coordinateX+1,coordinateY-1);     
      }  
    }
    
    if(coordinateX+1 <= field.size()-1){
      if(!field.get(coordinateX+1).get(coordinateY).isRevealed() && !field.get(coordinateX+1).get(coordinateY).isFlagged()){                 
        revealButton(coordinateX+1,coordinateY);
      }    
    }
    
    if(coordinateX+1 <= field.size()-1 && coordinateY+1 <= field.get(coordinateY).size()-1){
      if(!field.get(coordinateX+1).get(coordinateY+1).isRevealed() && !field.get(coordinateX+1).get(coordinateY+1).isFlagged()){
        revealButton(coordinateX+1,coordinateY+1);      
      }      
    }
    
    if(coordinateY+1 <= field.get(coordinateY).size()-1){
      if(!field.get(coordinateX).get(coordinateY+1).isRevealed() && !field.get(coordinateX).get(coordinateY+1).isFlagged()){      
        revealButton(coordinateX,coordinateY+1);
      }        
    }
    
    if(coordinateX-1 >= 0 && coordinateY+1 <= field.get(coordinateY).size()-1){
      if(!field.get(coordinateX-1).get(coordinateY+1).isRevealed() && !field.get(coordinateX-1).get(coordinateY+1).isFlagged()){        
        revealButton(coordinateX-1,coordinateY+1);      
      }          
    }    
    
    if(coordinateX-1 >= 0){
      if(!field.get(coordinateX-1).get(coordinateY).isRevealed() && !field.get(coordinateX-1).get(coordinateY).isFlagged()){          
        revealButton(coordinateX-1,coordinateY);
      }            
    }
    
    if(coordinateX-1 >= 0 && coordinateY-1 >= 0){
      if(!field.get(coordinateX-1).get(coordinateY-1).isRevealed() && !field.get(coordinateX-1).get(coordinateY-1).isFlagged()){            
        revealButton(coordinateX-1,coordinateY-1);
      }              
    }
  }
  
  public void revealButton(int coordinateX,int coordinateY){
    long timeElapsed;
    Instant finish;
    if(!gameInProgress){                     //prve tlacidlo
      field.get(coordinateX).get(coordinateY).setMine(true);
      generateMap(coordinateX,coordinateY);
      gameInProgress = true;
      revealButton(coordinateX,coordinateY);
      start = Instant.now();
    } 
    
    else if(mineCheck(coordinateX,coordinateY)){ 
      finish = Instant.now();    
      startButton.setIcon(deadTile);  
      revealMines();                 
      gameOver = true;
      timeElapsed = Duration.between(start, finish).toMillis();
      timeElapsed = timeElapsed /1000;
      mineCounter.setText("" + timeElapsed);
    }
    
    else{
      field.get(coordinateX).get(coordinateY).setRevealed(true);
      
      if(field.get(coordinateX).get(coordinateY).getAdjacentMines() == 0){  
        
        field.get(coordinateX).get(coordinateY).setIcon(revealedTile);                  
        revealAdjacentTiles(coordinateX,coordinateY);
      }
      else{
        switch (field.get(coordinateX).get(coordinateY).getAdjacentMines()) {
          case 1 -> field.get(coordinateX).get(coordinateY).setIcon(oneTile);
          case 2 -> field.get(coordinateX).get(coordinateY).setIcon(twoTile);
          case 3 -> field.get(coordinateX).get(coordinateY).setIcon(threeTile);
          case 4 -> field.get(coordinateX).get(coordinateY).setIcon(fourTile);
          case 5 -> field.get(coordinateX).get(coordinateY).setIcon(fiveTile);
          case 6 -> field.get(coordinateX).get(coordinateY).setIcon(sixTile);
          case 7 -> field.get(coordinateX).get(coordinateY).setIcon(sevenTile);
          case 8 -> field.get(coordinateX).get(coordinateY).setIcon(eightTile);
        }
      }      
    } 
    
    if (gameWon()) {
      finish = Instant.now();
      startButton.setIcon(winnerTile);
      gameOver = true;
      timeElapsed = Duration.between(start, finish).toMillis();
      timeElapsed = timeElapsed /1000;
      mineCounter.setText("" + timeElapsed);
    }  
  }
  
  private Image getScaledImage(Image srcImg, int w, int h){
    BufferedImage resizedImg = new BufferedImage(w, h, BufferedImage.TYPE_INT_ARGB);
    Graphics2D g2 = resizedImg.createGraphics();
    
    g2.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
    g2.drawImage(srcImg, 0, 0, w, h, null);
    g2.dispose();
    
    return resizedImg;
  }
}
