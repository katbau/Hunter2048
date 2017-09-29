import java.awt.*; 
import javax.swing.*;
import java.util.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseListener;
import java.awt.event.MouseEvent;
import java.io.File;

/**
 * Game is class that creats game much like 2048 instead using Hunter teachers as the tiles isntead of numbers. 
 * You use the up, down, left, right arrows to control the tiles.
 * The help menu has the directions and you can always start a new game by clicking new game.
 * @authors Katherine Bau, Sam Grunebaum, Ben Laufer
 * Breakdown - worked together!!
 */
public class Game extends JPanel
{
    private static final Color BACKGROUND_COLOR = new Color (202, 178, 178); 
    private static final Color TITLE_COLOR= new Color (138, 43, 226);
    private static final int TILE_SIZE = 80;
    private static final int S_BTWN = 15;
    private static final String FINAL_FONT = "Arial";
    private Tile [][] tiles;
    private ArrayList <Integer> freeTiles;
    private boolean won;
    private boolean lost;
    private boolean moved;
    /**
     * Creates a 2048 Hunter Edition Game
     * Uses KeyListener and KeyEvent for moving the tiles 
     * Uses MouseListener and MouseEvent for actions
     */
    public Game() 
    {
        startOver();
        setFocusable(true);
        addKeyListener(new KeyListener() {
                @Override
                public void keyTyped(KeyEvent e) {

                }

                @Override
                public void keyPressed(KeyEvent e) {
                    if (!won && !lost)
                    {
                        switch (e.getKeyCode())
                        {
                            case KeyEvent.VK_UP: up(); break;
                            case KeyEvent.VK_DOWN: down(); break;
                            case KeyEvent.VK_LEFT: left(); break;
                            case KeyEvent.VK_RIGHT: right(); break;
                        }
                    }
                    if(moved && !won)
                    {
                        updateFreeTiles();
                        addTile();
                        repaint();
                        checkloss();
                    }
                    repaint();
                }

                @Override
                public void keyReleased(KeyEvent e) {

                }
            });
        addMouseListener(new MouseListener() {
                @Override
                public void mousePressed (MouseEvent e) {

                }

                @Override
                public void mouseReleased(MouseEvent e) {

                }

                @Override
                public void mouseEntered(MouseEvent e) {

                }

                @Override
                public void mouseExited(MouseEvent e) {

                }

                @Override
                public void mouseClicked(MouseEvent e) {
                    if (e.getButton() == MouseEvent.BUTTON1)
                    {
                        if (e.getX()>260 && e.getX()<360)
                        {
                            if (e.getY()>20 && e.getY()<55)
                            {
                                directions();                          
                            }
                            if (e.getY()>70 && e.getY()<105)
                            {                             
                                startOver();
                                repaint();
                            }
                        }
                        if (e.getX()>260 && e.getX()<370)
                        {
                            if(e.getY()>375 && e.getY()<405)
                            {
                                if (won)
                                {
                                    won = false;
                                    repaint();
                                }
                                if (lost)
                                {
                                    startOver();
                                    repaint();
                                }
                            }
                        }
                    }                 

                }
            });
    }

    /**
     * Initializes instance fields 
     */
    private void startOver()
    {
        tiles = new Tile[4][4];
        freeTiles = new ArrayList <Integer>();
        won = false;
        lost = false;
        moved = false;
        for (int row = 0; row<4; row++)
        {
            for(int col=0; col<4; col++)
            {
                tiles[row][col] = new Tile();
                freeTiles.add(row*4+ col);
            }
        }
        addTile();
        addTile();
    }

    /**
     * Moves all the tiles that are able to move up, shift up
     * Dually checks for whether 2048 has been reached and if anything on the board has moved
     * @return whether the tile has moved
     */
    private boolean up()
    {
        for (int col = 0; col< 4; col++)
        {   
            int end=0;
            for (int row = 1; row<4; row++)
            {
                if (tiles[row][col].getValue() !=0)
                {
                    boolean donemove= false;
                    int newplace = row;
                    while (!donemove && newplace>end)
                    {
                        if (tiles[newplace-1][col].getValue()!=0)
                        {                       
                            donemove = true;
                        }
                        else
                        {
                            newplace--;
                        }
                    }                
                    if (donemove && tiles[newplace-1][col].getValue() == tiles[row][col].getValue())
                    {
                        moved = true;
                        tiles[newplace-1][col].merge();
                        if (tiles[newplace-1][col].getValue()==2048)
                            won = true;
                        tiles[row][col].setValue(0);
                        end= newplace;
                    }
                    else if(newplace<row)
                    {
                        moved= true;
                        tiles[newplace][col].setValue(tiles[row][col].getValue());
                        tiles[row][col].setValue(0);
                    }
                }
            }
        }
        return moved;
    }

    /**
     * Moves all the tiles that are able to move down, shift down
     * Dually checks for whether 2048 has been reached and if anything on the board has been moved
     * @return whether the tile has moved
     */
    private boolean down()
    {
        for (int col = 0; col< 4; col++)
        {   
            int end=3;
            for (int row = 2; row>-1; row--)
            {
                if (tiles[row][col].getValue() !=0)
                {
                    boolean donemove= false;
                    int newplace = row;
                    while (!donemove && newplace<end)
                    {
                        if (tiles[newplace+1][col].getValue()!=0)
                        {                       
                            donemove = true;
                        }
                        else
                        {
                            newplace++;
                        }
                    }                
                    if (donemove && tiles[newplace+1][col].getValue() == tiles[row][col].getValue())
                    {
                        moved = true;
                        tiles[newplace+1][col].merge();
                        if (tiles[newplace+1][col].getValue() == 2048)
                            won=true;
                        tiles[row][col].setValue(0);
                        end= newplace;
                    }
                    else if(newplace>row)
                    {
                        moved=true;
                        tiles[newplace][col].setValue(tiles[row][col].getValue());
                        tiles[row][col].setValue(0);
                    }
                }
            }
        }
        return moved;
    }

    /**
     * Moves all the tiles that are able to move left, shift left
     * Dually checks for whether 2048 has been reached and if anything on the board has been moved
     * @return whether the tile has moved
     */
    private boolean left()
    {
        for (int row = 0; row< 4; row++)
        {   
            int end=0;
            for (int col = 1; col<4; col++)
            {
                if (tiles[row][col].getValue() !=0)
                {
                    boolean donemove= false;
                    int newplace = col;
                    while (!donemove && newplace>end)
                    {
                        if (tiles[row][newplace-1].getValue()!=0)
                        {                       
                            donemove = true;
                        }
                        else
                        {
                            newplace--;
                        }
                    }                
                    if (donemove && tiles[row][newplace-1].getValue() == tiles[row][col].getValue())
                    {
                        moved= true;
                        tiles[row][newplace-1].merge();
                        if( tiles[row][newplace-1].getValue()==2048)
                            won=true;
                        tiles[row][col].setValue(0);
                        end= newplace;
                    }
                    else if(newplace<col)
                    {
                        moved = true;
                        tiles[row][newplace].setValue(tiles[row][col].getValue());
                        tiles[row][col].setValue(0);
                    }
                }
            }
        }
        return moved;
    }

    /**
     * Moves all the tiles that are able to move right, shift right
     * Dually checks for whether 2048 has been reached and if anything on the board has been moved
     * @return whether the tile has moved
     */
    private boolean right()
    {
        for (int row = 0; row< 4; row++)
        {   
            int end=3;
            for (int col = 2; col>-1; col--)
            {
                if (tiles[row][col].getValue() !=0)
                {
                    boolean donemove= false;
                    int newplace = col;
                    while (!donemove && newplace<end)
                    {
                        if (tiles[row][newplace+1].getValue()!=0)
                        {                       
                            donemove = true;
                        }
                        else
                        {
                            newplace++;
                        }
                    }                
                    if (donemove && tiles[row][newplace+1].getValue() == tiles[row][col].getValue())
                    {
                        moved = true;
                        tiles[row][newplace+1].merge();
                        if(  tiles[row][newplace+1].getValue() == 2048)
                            won = true;
                        tiles[row][col].setValue(0);
                        end= newplace;
                    }
                    else if(newplace>col)
                    {
                        moved= true;
                        tiles[row][newplace].setValue(tiles[row][col].getValue());
                        tiles[row][col].setValue(0);
                    }
                }
            }
        }
        return moved;
    }

    /**
     * Adds a free tile to a random free spot on the current board of tiles using a random generator 
     * and an array list.
     */
    private void addTile ()
    {
        moved = false;
        Random randomGenerator = new Random();
        int newTileplace= randomGenerator.nextInt(freeTiles.size());
        int removed = freeTiles.remove(newTileplace);
        if (randomGenerator.nextInt(6)<5)
            tiles[removed/4][removed%4].setValue(2);
        else
            tiles[removed/4][removed%4].setValue (4);

    }

    /**
     * Updates the instance field freeTiles to reflect a list of the tiles that have no tile in them
     */
    private void updateFreeTiles()
    {
        freeTiles = new ArrayList <Integer>();
        for (int row = 0; row<4; row++)
        {
            for(int col=0; col<4; col++)
            {
                if (tiles[row][col].getValue() ==0)
                {
                    freeTiles.add(row*4+ col);
                }
            }
        }
    }

    /**
     * Checks whether the player has lost
     */
    public void checkloss()
    {
        if (freeTiles.size() == 0)
        {
            if (!up() && !down() && !left() && !right())
            {
                lost = true;
            }
        }
    }  

    /**
     * Creates a dialog box with the directions to help the user understand the game
     */
    public static void directions ()
    {
        JOptionPane.showMessageDialog(null, "The objective of the game is to level up to Mr. Young by combining\n lower-level teachers with themselves. \n You will have a grid of 16 tiles. Two tiles will be given: teachers of \n lower point values, most likely from the physics department.\n Move up or down, left or right trying to join two equal teacher tiles.\n When two equal teachers are in touch, they will combine and form\n a new higher-level teacher. With each move, a new teacher tile appears. \nIf there are no free tiles on your grid, the game ends. By combining teachers, \n we get higher-level teachers and we can approach Mr. Young, who is the goal of the game.", "Directions!", JOptionPane.INFORMATION_MESSAGE, 
            new ImageIcon("KatBenSam.jpg"));
    }

    /**
     * Overrides the paint function
     * @param Graphics g
     */
    @Override
    public void paint(Graphics g) {
        super.paint(g);
        g.setColor(BACKGROUND_COLOR);
        g.fillRect (0,0, 415, 535);
        g.setColor(new Color(0,0,0)); 
        g.setFont (new Font ("Arial", Font.BOLD, 50));g.drawString("2048", 20, 60);
        g.setColor(TITLE_COLOR);
        g.setFont(new Font("Arial", Font.BOLD, 25)); g.drawString("Hunter", 145, 40);
        g.drawString("Edition", 145, 65);
        g.setColor(TITLE_COLOR);
        g.setFont(new Font("Arial", Font.BOLD, 18)); g.drawString("Can you get to Mr.Young?", 20, 95);
        g.setColor(new Color(204, 163, 221));
        g.fillRoundRect(260, 20, 100, 35, 14, 14);
        g.fillRoundRect(260, 70, 100, 35, 14, 14);
        g.setColor(Color.BLACK);
        g.setFont(new Font("Arial", Font.BOLD, 15)); 
        g.drawString ("Help",290,40); g.drawString("New Game",270, 90);
        for (int row=0; row<4; row++)
        {
            for(int col=0; col<4; col++)
            {
                drawTiles(g, tiles[row][col],  col*TILE_SIZE+(col+1)*S_BTWN, 100+row*TILE_SIZE+(row+1)*S_BTWN);
            }
        }
        if (won)
        {
            g.setColor(new Color (167, 82, 164));
            g.fillRoundRect(10,120,375, 300, 20, 20);
            g.setColor(new Color(225, 179, 223));
            g.setFont(new Font("Arial", Font.BOLD, 40)); 
            g.drawString("You Win!!!", 110, 155);
            g.setFont(new Font ("Arial", Font.BOLD, 20));
            g.drawString("Keep playing to see what lies", 35, 340);
            g.drawString("beyond the math department...", 80, 360);
            g.drawImage((new ImageIcon ("KatBenSamWin.jpg")).getImage(), 90, 170, this);
            g.fillRoundRect(260, 375, 110, 30, 10, 10);
            g.setColor(Color.BLACK);
            g.setFont(new Font("Arial", Font.BOLD, 15));
            g.drawString("Keep Playing!", 270, 395);
        }
        if (lost)
        {
            g.setColor(new Color (167, 82, 164));
            g.fillRoundRect(10,120,375, 300, 20, 20);
            g.setColor(new Color(225, 179, 223));
            g.setFont(new Font("Arial", Font.BOLD, 40)); 
            g.drawString("You Lose!!!", 110, 155);
            g.setFont(new Font ("Arial", Font.BOLD, 20));
            g.drawString("If only physics taught you how to", 35, 340);
            g.drawString("be a winner...", 80, 360);
            g.drawImage((new ImageIcon ("KatBenSamLose.jpg")).getImage(), 90, 170, this);
            g.fillRoundRect(260, 375, 110, 30, 10, 10);
            g.setColor(Color.BLACK);
            g.setFont(new Font("Arial", Font.BOLD, 15));
            g.drawString("Play Again", 270, 395);
        }
    }

    /**
     * Draws the tiles
     * @param Graphics g, tile to be drawn, x coordinate of tile, y coordinate of tile
     */
    private void drawTiles (Graphics g, Tile tile, int x, int y)
    {
        Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING,RenderingHints.VALUE_ANTIALIAS_ON);
        g2d.setRenderingHint(RenderingHints.KEY_STROKE_CONTROL, RenderingHints.VALUE_STROKE_NORMALIZE);
        g2d.drawImage(tile.tileImage(), x, y, this);
    }

    /**
     * Creates the frame in which the game will appear
     */
    public static void main(String[] args) throws InterruptedException {
        JFrame frame = new JFrame("2048 Kat Ben Sam");
        Game game = new Game();
        frame.add(game);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(415, 535);
        frame.setLocationRelativeTo(null);
        frame.setResizable (false);
        frame.setVisible(true);       
    }
}