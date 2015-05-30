import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferStrategy;

/**
 * Created by Elias on 5/30/2015.
 */
public class Canvas2 extends Canvas implements Runnable{

    public static float[] height = new float[127];

    public Canvas2(){
        setSize(height.length*10, 180);
        JFrame jf = new JFrame("bilde");
        jf.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        jf.setResizable(false);
        jf.add(this);
        jf.pack();
        jf.setLocationRelativeTo(null);
        jf.setVisible(true);
        new Thread(this,"EQ").start();
    }

    public void render(){
        BufferStrategy bs = getBufferStrategy();
        if (bs== null) {
            createBufferStrategy(3);
            return;
        }

        Graphics g = bs.getDrawGraphics();
        g.setColor(Color.BLACK);
        g.fillRect(0,0,getWidth(),getHeight());
        g.setColor(Color.yellow);
        for(int i = 0; i < height.length; i++){
            g.fillRect(i*10,(int)(180f-height[i]*3f),9,(int)(height[i]*3f));
        }
        g.setColor(Color.white);
        for(int i = 1; i < getWidth()/50; i++){
            g.fillRect(i*50-1,0,1,getHeight());
        }
        g.dispose();
        bs.show();
    }

    @Override
    public void run() {
        while(true){
            render();
        }
    }
}
