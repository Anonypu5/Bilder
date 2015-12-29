import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.plaf.basic.BasicInternalFrameTitlePane;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

/**
 * Created by Elias on 5/28/2015.
 */
public class Main {
    public Main(CanvasClass canvas) {
        canvas.ops = new Operator[1];
        canvas.ops[0] = new Operator();
        canvas.ops[0].lr = 105;
        canvas.ops[0].vr = 255;
        canvas.ops[0].lg = 150;
        canvas.ops[0].vg = 255;
        canvas.ops[0].lb = 100;
        canvas.ops[0].vb = 255;
        long time = System.currentTimeMillis();
        long counter = 0;
        while (true){
            long deltaTime = System.currentTimeMillis()-time;
            time += deltaTime;
            counter += deltaTime;
            float r = (float)(counter)/1000;
            canvas.ops[0].lr = (int)(Math.sin(r)*100+100);
            float g = (float)(counter)/1000*0.5f;
            canvas.ops[0].lg = (int)(Math.sin(g)*100+100);
            float b = (float)(counter)/1000*2;
            canvas.ops[0].lb = (int)(Math.sin(b)*100+100);
            canvas.render();
            canvas.update();
        }
    }

    public static void main(String[] args) {
        JFileChooser chooser = new JFileChooser();
        FileNameExtensionFilter filter = new FileNameExtensionFilter("images","jpg", "png");
        chooser.setFileFilter(filter);
        chooser.showOpenDialog(null);
        File file = chooser.getSelectedFile();
        BufferedImage bi = null;
        try {
            bi = ImageIO.read(file);
        } catch (IOException e) {
            e.printStackTrace();
        }

        CanvasClass canvas = new CanvasClass(bi);

        new Main(canvas);
    }
}
