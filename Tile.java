import java.awt.Color; 
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import java.io.File;
import java.lang.Character;

/**
 * Tile is a class that the class Game utilizes to hold values, tile colors
 * and tile Images
 * @authors Katherine Bau, Sam Grunebaum, Ben Laufer
 * Breakdown - worked together!!
 */
public class Tile
{
    private int value;
    /**
     * Creates a Tile with the value of 0
     */
    public Tile ()
    {
        value = 0;
    }

    /**
     * Creates a Tile with a specified value
     * @param int value of tile
     */
    public Tile (int n)
    {
        value = n;
    }

    /**
     * Returns the value of the tile
     * @return value, value of tile
     */
    public int getValue()
    {
        return value;
    }

    /**
     * Sets the value of the tile to a specified amount
     * @param int n, the new value of the tile
     */
    public void setValue(int n)
    {
        value = n;
    }     

    /**
     * Doubles the value of the tile
     */
    public void merge ()
    {
        value *=2;
    }

    /**
     * Method returns tile color
     * @return the color of the tile value
     */
    public Color tileColor()
    {      
        switch (value)
        {
            case 2: return new Color (238, 228, 218);
            case 4: return new Color (237, 224, 200);
            case 8: return new Color (242, 177, 121);
            case 16: return new Color (245, 149, 99);
            case 32: return new Color (246, 124, 95);
            case 64: return new Color (246, 94, 59);
            case 128: return new Color (237, 207, 114);
            case 256: return new Color (237, 204, 97);
            case 512: return new Color (237, 200, 80);
            case 1024: return new Color (237, 197, 63);
            case 2048: return new Color (238, 194, 46);
        }
        return new Color(238, 228, 218);     
    }

    /**
     * Method returns tile image
     * @return the image of the tile value
     */
    public Image tileImage()
    {
        String s= "";
        String pic = "";      
        switch (value)
        {
            case 2: s = (new File ("Frankel.jpg")).getAbsolutePath(); break;
            case 4: s = (new File ("Kennedy-Shaffer.jpg")).getAbsolutePath(); break;
            case 8: s = (new File ("Bao.jpg")).getAbsolutePath(); break;
            case 16: s = (new File ("Basias.jpg")).getAbsolutePath(); break;
            case 32: s = (new File ("Weinstein.jpg")).getAbsolutePath(); break;
            case 64: s = (new File ("Butts.jpg")).getAbsolutePath(); break;
            case 128: s = (new File ("Aboody.jpg")).getAbsolutePath(); break;
            case 256: s = (new File ("Detchkov.jpg")).getAbsolutePath(); break;
            case 512: s = (new File ("Siegmann.jpg")).getAbsolutePath(); break;
            case 1024: s = (new File ("Fisher.jpg")).getAbsolutePath(); break;
            case 2048: s = (new File ("Young.jpg")).getAbsolutePath(); break;
            case 4096: s= (new File ("Obama.jpg")).getAbsolutePath(); break;
        }
        for (int i=0; i <s.length(); i++)
        {
            if (Character.getNumericValue(s.charAt(i)) == 92)
            {
                pic += "/";
            }
            else 
                pic += s.charAt(i);
        }
        return new ImageIcon (pic).getImage();
    }    
}