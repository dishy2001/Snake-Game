import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.Arrays;
import java.util.Random;

public class Panel extends JPanel implements ActionListener{
    //declaring size of window to be constant throughout
    static final int width=1280;
    static final int height=720;

    static final int unit=60; //size of each individual box
    //frequency and place where food will spawn randomly
    Timer timer;  //frequency where food will spawn randomly
    Random random;  //place where food will spawn randomly

    int fx; //location of food in x axis
    int fy; //location of food in y axis
    int foodeaten; // how much food is eaten

    //initial or starting body length of snake
    int bodylength=3; //cell of snake

    boolean game_flag=false; //game is running or not

    char direction='R'; //direction to where snake is heading and r is by default so at first snake will move to right direction by default

    static final int delay=160;

    static final int gsize=(width*height)/(unit*unit); //number of total units
    final int[] x_snake =new int[gsize];
    final int[] y_snake =new int[gsize];


    Panel(){
        //constructor to fill size
        this.setPreferredSize(new Dimension(width,height));
        this.setBackground(Color.GRAY);
        this.setFocusable(true);
        this.addKeyListener(new MyKey()); // mykey will decide what will happen for the particular pressed key

        random=new Random();
        Game_start();
    }

    public void Game_start(){
        newFoodPosition();
        game_flag=true; // we know game has started
        timer=new Timer(delay,this); // it'll tell how much delay has happened
        timer.start();// start timer as soon as game starts

    }

    public void paintComponent(Graphics graphic){
        //this function to call draw function
        super.paintComponent(graphic);
        draw(graphic);
    }

    public void draw(Graphics graphic){
        if(game_flag){
            //to fill location of the food unit with an oval
            graphic.setColor(Color.orange);
            graphic.fillOval(fx,fy,unit,unit);

            for(int i=0;i<bodylength;i++){
                //to fill the head of the snake
                if(i==0){
                    graphic.setColor(Color.green);
                    graphic.fillRect(x_snake[i],y_snake[i],unit,unit);
                }
                //to fill the rest of the body
                else{
                    graphic.setColor(Color.orange);
                    graphic.fillRect(x_snake[i],y_snake[i],unit,unit);
                }
            }
            //for displaying score
            graphic.setColor(Color.red);
            graphic.setFont((new Font("Comic Sans",Font.BOLD,40)));
            FontMetrics font_me=getFontMetrics(graphic.getFont());
            graphic.drawString("Score:"+foodeaten,(width-font_me.stringWidth("Score"+foodeaten))/2,graphic.getFont().getSize());
        }

        else{
            gameOver(graphic);
        }
    }
    //movement of snake
    public void move(){
        //updating the whole body of snake apart from it's head
        for(int i=bodylength;i>0;i--){
            x_snake[i]=x_snake[i-1];
            y_snake[i]=y_snake[i-1];
        }
        //update head of snake according the direction
        switch(direction) {
            case 'U': //up
                y_snake[0] = y_snake[0] - unit;
                break;
            case 'L': //left
                x_snake[0] = x_snake[0] - unit;
                break;
            case 'D': //down
                y_snake[0] = y_snake[0] + unit;
                break;
            case 'R': //right
                x_snake[0] = x_snake[0] + unit;
                break;
        }
    }
    public void gameOver(Graphics graphic) {
        //to display the score
        graphic.setColor(Color.red);
        graphic.setFont(new Font("Comic Sans",Font.BOLD,40));
        FontMetrics font_me=getFontMetrics(graphic.getFont());
        graphic.drawString("Score: "+foodeaten,(width-font_me.stringWidth("Score: "+foodeaten))/2,graphic.getFont().getSize());

        //to display game over text
        graphic.setColor(Color.red);
        graphic.setFont(new Font("Comic Sans",Font.BOLD,80));
        FontMetrics font_me1=getFontMetrics(graphic.getFont());
        graphic.drawString("GAME OVER!",(width-font_me1.stringWidth("GAME OVER!"))/2,height/2);

        //to display prompt to replay
        graphic.setColor(Color.red);
        graphic.setFont(new Font("Comic Sans",Font.BOLD,40));
        FontMetrics font_me2=getFontMetrics(graphic.getFont());
        graphic.drawString("Press R to replay"+foodeaten,(width-font_me2.stringWidth("Press R to replay"))/2,height/2-150);
    }
    public void newFoodPosition(){
        //to set display of food at random coordinates
        fx=random.nextInt((int)(width/gsize))*unit;
        fy=random.nextInt((int)(height/gsize))*unit;
    }
    public void checkit(){
        //for checking collision of snake with itself and walls
        for(int i=bodylength;i>0;i--){
            if((x_snake[0]==x_snake[i])&&(y_snake[0]==y_snake[i])){
                game_flag=false;
            }
        }
        if((x_snake[0]<0)){
            game_flag=false;
        }
        else if((x_snake[0]>width)){
            game_flag=false;
        }
        else if((y_snake[0]<0)){
            game_flag=false;
        }
        else if((y_snake[0]>height)){
            game_flag=false;
        }
        if(!game_flag){
            timer.stop();
        }

    }
    public void eat(){
        if((x_snake[0]==fx)&&(y_snake[0]==fy)){
            bodylength++;
            foodeaten++;
            newFoodPosition();
        }
    }
    public class MyKey extends KeyAdapter{
        @Override
        public void keyPressed(KeyEvent e) {
            switch(e.getKeyCode()) {
                case KeyEvent.VK_LEFT:
                    if (direction != 'R') {
                        direction = 'L';
                    }
                    break;
                case KeyEvent.VK_UP:
                    if (direction != 'D') {
                        direction = 'U';
                    }
                    break;
                case KeyEvent.VK_RIGHT:
                    if (direction != 'L') {
                        direction = 'R';
                    }
                    break;
                case KeyEvent.VK_DOWN:
                    if (direction != 'U') {
                        direction = 'D';
                    }
                    break;
                case KeyEvent.VK_R: // for restarting game
                    if (!game_flag) {
                        foodeaten = 0;
                        bodylength = 3;
                        direction = 'R';
                        Arrays.fill(x_snake, 0);
                        Arrays.fill(y_snake, 0);
                        Game_start();
                    }
                    break;
            }
        }
    }
    @Override
    public void actionPerformed(ActionEvent arg0){
        if(game_flag){
            move();
            eat();
            checkit();

        }
        repaint();
    }
}

