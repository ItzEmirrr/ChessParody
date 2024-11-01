package pieces;

import main.Board;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class Piece {

    public int col, row;
    public int xPos, yPos;

    public boolean isWhite;
    public String name;
    public int value;

    public boolean isFirstMove = true;

    BufferedImage sheet;
    Image duck;
    Image white_mimic;
    Image black_mimic;
    Board board;
    {
        try {
            sheet = ImageIO.read(ClassLoader.getSystemResourceAsStream("pieces.png"));
            duck = ImageIO.read(ClassLoader.getSystemResourceAsStream("duck.png"));
            white_mimic = ImageIO.read(ClassLoader.getSystemResourceAsStream("white_mime.png"));
            black_mimic = ImageIO.read(ClassLoader.getSystemResourceAsStream("black_mime.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    protected int sheetScale = sheet.getWidth() / 6;

    Image sprite;



    public Piece (Board board){
        this.board = board;
    }

    public boolean isValidMovement(int col, int row){
        return true;
    }

    public boolean moveCollision(int col, int row){
        return false;
    }

    public boolean hasJumpedOver(int col, int row){
        return false;
    }

    public void paint(Graphics2D g2d){
        g2d.drawImage(sprite, xPos, yPos, null);
    }


}
