import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferInt;

/**
 * Created by Elias on 5/28/2015.
 */
public class CanvasClass extends Canvas {

    public BufferedImage image;
    public JFrame jf;
    public BufferedImage screen;
    public int[] screenArray;
    public int[] imageArray;
    public static Operator[] ops;

    public CanvasClass(BufferedImage bi){
        image = bi;
        screen = new BufferedImage(image.getWidth(),image.getHeight(),BufferedImage.TYPE_INT_RGB);
        screenArray = ((DataBufferInt)screen.getRaster().getDataBuffer()).getData();
        imageArray = image.getRGB(0,0,image.getWidth(),image.getHeight(),null,0,image.getWidth());

        setSize(image.getWidth(),image.getHeight());

        jf = new JFrame("bilde");
        jf.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        jf.setResizable(false);
        jf.add(this);
        jf.pack();
        jf.setLocationRelativeTo(null);
        jf.setVisible(true);
    }

    public void render(){
        BufferStrategy bs = getBufferStrategy();
        if (bs== null) {
            createBufferStrategy(3);
            return;
        }

        Graphics g = bs.getDrawGraphics();
        g.drawImage(screen, 0, 0, null);
        g.dispose();
        bs.show();
    }

    public void update(){
        for(int i = 0; i < screenArray.length; i++){
            screenArray[i] = 0x000000;
        }
        for(int i = 0; i < ops.length; i++){
            screenArray = ops[i].operation(imageArray,screenArray);
        }
    }
}class Operator{

    int vr=0,vg=0,vb=0,
            lr=0,lg=0,lb=0;

    public int[] operation(int[] source, int[] image){
        for(int i = 0; i < source.length; i++){

            int hex = source[i];
            int r = (hex & 0xFF0000) >> 16;
            int g = (hex & 0xFF00) >> 8;
            int b = (hex & 0xFF);
            int nr=0,ng=0,nb=0;

            if(r > lr){
                nr = vr;
            }
            if(g > lg){
                ng = vg;
            }
            if(b > lb){
                nb = vb;
            }

            int hex2 = image[i];
            int r2 = (hex2 & 0xFF0000) >> 16;
            int g2 = (hex2 & 0xFF00) >> 8;
            int b2 = (hex2 & 0xFF);

            r2 += nr;
            b2 += nb;
            g2 += ng;

            if(r2 > 255)
                r2 = 255;
            if(b2 > 255)
                b2 = 255;
            if(g2 > 255)
                g2 = 255;

            image[i] = (r2<<16) + (g2<<8) + b2;
        }

        return image;
    }
}
