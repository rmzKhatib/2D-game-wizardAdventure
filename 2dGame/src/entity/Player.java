package entity;

import main.KeyHandler;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.IOException;
import javax.imageio.ImageIO;
import main.GamePanel;

public class Player extends Entity{

    GamePanel gp;
    KeyHandler keyH;

    public final int screenX;
    public final int screenY;

    public Player(GamePanel gp, KeyHandler keyH) {

        this.gp = gp;
        this.keyH = keyH;

        screenX = gp.screenWidth/2 - (gp.tileSize/2); // "- (gp.tileSize/2)" is added to make sure charater is displayed in the center of the screen. This is because the coordinates are for the top left of you charater not its center
        screenY = gp.screenHeight/2 - (gp.tileSize/2);

        solidArea = new Rectangle();
        solidArea.x = 8;
        solidArea.y = 24;
        solidArea.width = 23;
        solidArea.height = 23;

        setDefaultValues();
        getPlayerImage();
    }
    public void setDefaultValues() {

        worldX = gp.tileSize * 49; //starting position x
        worldY = gp.tileSize * 50; // starting position y
        speed = 4;
        direction = "down";
    }
    public void getPlayerImage() {
        System.out.println("Image loading started");
        //load sprite images...
        //load sprite images...
        System.out.println("Image loading ended");
        try {

            up1 = ImageIO.read(getClass().getResourceAsStream("/player/sprite_wizard_up1.png"));
            up2 = ImageIO.read(getClass().getResourceAsStream("/player/sprite_wizard_up2.png"));   
            down1 = ImageIO.read(getClass().getResourceAsStream("/player/sprite_wizard_down1.png"));  
            down2 = ImageIO.read(getClass().getResourceAsStream("/player/sprite_wizard_down2.png"));   
            left1 = ImageIO.read(getClass().getResourceAsStream("/player/sprite_wizard_left1.png"));
            left2 = ImageIO.read(getClass().getResourceAsStream("/player/sprite_wizard_left2.png"));
            right1 = ImageIO.read(getClass().getResourceAsStream("/player/sprite_wizard_right1.png"));
            right2 = ImageIO.read(getClass().getResourceAsStream("/player/sprite_wizard_right2.png"));
        }catch(IOException e) {
            e.printStackTrace();
        }
    }

    public void update() {

        if(keyH.downPressed == true || keyH.upPressed == true || 
        keyH.leftPressed == true || keyH.rightPressed == true) {

            if(keyH.upPressed == true) {
                direction = "up";
            }
            else if (keyH.downPressed == true) {
                direction = "down";
            }
            else if(keyH.leftPressed == true) {
                direction = "left";
            }
            else if(keyH.rightPressed == true) {
                direction = "right";
            }

            //CHECK TILE COLLISION
            collisionOn = false;
            gp.cChecker.checkTile(this);

            //IF COLLISION IS FALSE, PLAYER CAN MOVE
            if(collisionOn == false) {
                switch(direction) {
                    case"up":
                        worldY -= speed;
                        break;
                    case"down":
                        worldY += speed;
                        break;
                    case"left":
                        worldX -= speed;
                        break;
                    case"right":
                        worldX += speed;
                        break;
                }
            }
            spriteCounter++;
            if(spriteCounter > 15) {
                if(spriteNum == 1) {
                    spriteNum = 2;
                }
                else if(spriteNum == 2) {
                    spriteNum = 1;
                }
                spriteCounter = 0;
            }
        }       
 }

    public void draw(Graphics2D g2) {

       // g2.setColor(Color.white);
       // g2.fillRect(x, y, gp.tileSize, gp.tileSize);

        BufferedImage image = null;

        switch(direction) {
            case "up":
                if(spriteNum == 1) {
                    image = up1;
                }
                if(spriteNum == 2) {
                    image = up2;
                }
                //image = up1;
                break;
            case "down":
                if(spriteNum == 1) {
                    image = down1;
                }
                if(spriteNum == 2) {
                    image = down2;
                }
                //image = down1;
                break;   
            case "left":
                if(spriteNum == 1) {
                    image = left1;
                }
                if(spriteNum == 2) {
                    image = left2;
                }
                //image = left1;
                break;
            case "right":
                if(spriteNum == 1) {
                    image = right1;
                }
                if(spriteNum == 2) {
                    image = right2;
                }
                //image = right1;

        }
        g2.drawImage(image, screenX, screenY, gp.tileSize, gp.tileSize, null);

    }
}
