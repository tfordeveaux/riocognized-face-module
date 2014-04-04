package fr.olympicinsa.riocognized;

import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.core.MatOfRect;
import org.opencv.core.Point;
import org.opencv.core.Rect;
import org.opencv.core.Scalar;
import org.opencv.highgui.Highgui;
import org.opencv.objdetect.CascadeClassifier;

public class FaceDetector {
    
    CascadeClassifier haarFilter;
    
    public FaceDetector(String haarURL){
        //Load Haar Cascade Classifier
        haarFilter = new CascadeClassifier(haarURL);
    }

    public int detectFaces(String imageURL, String output) {
        
        //Read image
        Mat image = Highgui.imread(imageURL);
        
        MatOfRect faceDetections = new MatOfRect();
        haarFilter.detectMultiScale(image, faceDetections);

        int detected = faceDetections.toArray().length;
        
        //Frame recognized athlete faces
        for (Rect rect : faceDetections.toArray()) {
            Core.rectangle(image, new Point(rect.x, rect.y), new Point(rect.x + rect.width, rect.y + rect.height),
                    new Scalar(0, 255, 0));
        }
        
        //Write new file
        Highgui.imwrite(output, image);
        
        return detected;
    }
}