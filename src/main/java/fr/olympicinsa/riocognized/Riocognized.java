package fr.olympicinsa.riocognized;

import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.core.MatOfRect;
import org.opencv.core.Point;
import org.opencv.core.Rect;
import org.opencv.core.Scalar;
import org.opencv.highgui.Highgui;
import org.opencv.objdetect.CascadeClassifier;

public class Riocognized {

    public static void main(String[] args) {

        System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
        System.out.println("\nRunning Riocognized FaceDetector");
        String imageParam = (args.length > 0) ? args[0] : "/image.jpg";
        String athletePath;
        
        try {
            athletePath = Riocognized.class.getResource(imageParam).getPath();
        } catch (Exception e)
        {
            System.err.println("Couldn't load your athlete picture, use London Olympic's Triathlon podium instead ;-)");
            athletePath = Riocognized.class.getResource("/image.jpg").getPath();
        }
        
        //Load Haar Cascade Classifier
        String haarCascade = Riocognized.class.getResource("/haarcascade_frontalface_alt.xml").getPath();
        String output = "athletes_detected.png";
        System.out.println("Result will be written in : " + output + " ....");
        
        try {
            FaceDetector faceDetector = new FaceDetector(haarCascade);
            int detected = faceDetector.detectFaces(athletePath, output);
            System.out.println("Detected "+ detected +" athletes !");
        } catch (Exception e) {
            System.out.println("Error processiong detection");
        }
    }
}