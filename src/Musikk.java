import javafx.application.Application;
import javafx.embed.swing.JFXPanel;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Slider;
import javafx.scene.input.MouseEvent;
import javafx.scene.media.AudioClip;
import javafx.scene.media.AudioSpectrumListener;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.util.Duration;

import javax.sound.sampled.*;
import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.plaf.SliderUI;
import java.io.File;
import java.net.URI;

/**
 * Created by Elias on 5/29/2015.
 */
public class Musikk extends Application{

    static Operator op;

    public static void init(String[] args, Operator op) {
        launch(args);
        Musikk.op = op;
    }

    public void start(Stage stage) throws Exception {
        JFileChooser chooser = new JFileChooser();
        FileNameExtensionFilter filter = new FileNameExtensionFilter("","mp3", "wav");
        chooser.setFileFilter(filter);
        chooser.showOpenDialog(null);

        Media hit = new Media(chooser.getSelectedFile().toURL().toString());
        MediaPlayer mediaPlayer = new MediaPlayer(hit);
        mediaPlayer.play();
        mediaPlayer.setAudioSpectrumInterval(1.0/60.0);

        Slider slider = new Slider();
        slider.setPrefWidth(400);
        Group g = new Group();
        Scene scene = new Scene(g, 400, 20, Color.BLACK);
        stage.setScene(scene);
        g.getChildren().add(slider);
        stage.show();
        mediaPlayer.setAudioSpectrumListener(new test(slider, mediaPlayer, op));

        slider.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                mediaPlayer.seek(Duration.seconds(slider.getValue()));
            }
        });
    }
}class test implements AudioSpectrumListener{
    Slider slider;
    MediaPlayer mediaPlayer;
    Operator op;
    private boolean red;

    public test(Slider slider, MediaPlayer mediaPlayer, Operator op){
        this.slider = slider;
        this.mediaPlayer = mediaPlayer;
        this.op = op;
    }
    @Override
    public void spectrumDataUpdate(double timestamp, double duration, float[] magnitudes, float[] phases) {
        float r = 0;
        for(int i = 0; i < 5; i++){
            r+=magnitudes[i];
        }
        r=r/5;

        float g = 0;
        for(int i = 5; i < 10; i++){
            g+=magnitudes[i];
        }
        g=g/5;

        float b = 0;
        for(int i = 10; i < 15; i++){
            b+=magnitudes[i];
        }
        b=b/5;

        CanvasClass.ops[0].lr = (int)((((float)(r))/-60f)*255f)-50;
        CanvasClass.ops[0].lg = (int)((((float)(g))/-60f)*255f)-50;
        CanvasClass.ops[0].lb = (int)((((float)(b))/-60f)*255f)-50;

        for(int i = 0; i < Canvas2.height.length; i++){
            Canvas2.height[i] = 60+magnitudes[i];
        }

//        System.out.println(magnitudes[0]);
//        if(magnitudes[4] < -55){
//            if(red){
//                Main.redStrikeValue = 0;
//                Main.redStrike = true;
//                red = false;
//            }
//        }else{
//            red = true;
//        }
    }
}
