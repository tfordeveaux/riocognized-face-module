package fr.olympicinsa.riocognized;

import java.awt.Image;
import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.core.MatOfRect;
import org.opencv.core.Point;
import org.opencv.core.Rect;
import org.opencv.core.Scalar;
import org.opencv.highgui.Highgui;
import org.opencv.objdetect.CascadeClassifier;

public class FaceDetector {

    public static void main(String[] args) {

        System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
        System.out.println("\nRunning Riocognized FaceDetector");
        String imageParam = (args.length > 0) ? args[0] : "/image.jpg";
        String athletePath;
        
        try {
            athletePath = FaceDetector.class.getResource(imageParam).getPath();
        } catch (Exception e)
        {
            System.err.println("Couldn't load your athlete picture, use London Olympic's Triathlon podium instead ;-)");
            athletePath = FaceDetector.class.getResource("/image.jpg").getPath();
        }
        
        //Load Haar Cascade Classifier
        CascadeClassifier faceDetector = new CascadeClassifier(FaceDetector.class.getResource("/haarcascade_frontalface_alt.xml").getPath());
        //Read image
        Mat image = Highgui.imread(athletePath);
        
        MatOfRect faceDetections = new MatOfRect();
        faceDetector.detectMultiScale(image, faceDetections);

        System.out.println(String.format("Detected %s athletes !", faceDetections.toArray().length));
        
        //Frame recognized athlete faces
        for (Rect rect : faceDetections.toArray()) {
            Core.rectangle(image, new Point(rect.x, rect.y), new Point(rect.x + rect.width, rect.y + rect.height),
                    new Scalar(0, 255, 0));
        }
        
        //Write new file
        String filename = "athletes_detected.png";
        System.out.println(String.format("Writing %s", filename));
        Highgui.imwrite(filename, image);
    }
}