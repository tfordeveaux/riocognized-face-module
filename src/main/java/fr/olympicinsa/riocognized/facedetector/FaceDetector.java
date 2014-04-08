package fr.olympicinsa.riocognized.facedetector;

import java.awt.image.BufferedImage;
import static java.lang.System.exit;
import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.core.MatOfRect;
import org.opencv.core.Point;
import org.opencv.core.Rect;
import org.opencv.core.Scalar;
import org.opencv.highgui.Highgui;
import org.opencv.objdetect.CascadeClassifier;

public class FaceDetector {

    public final static String CASCADE_RIGHT_EAR = "haarcascade_mcs_rightear.xml";
    public final static String CASCADE_PROFILEFACE = "haarcascade_profileface.xml";
    public final static String CASCADE_FRONTALEFACE = "haarcascade_frontalface_alt.xml";

    private int facesDetected;
    private CascadeClassifier haarFilter;
    private static OpenCV openCV;

    /**
     * Face Detector constructor
     *
     */
    public FaceDetector() {
        openCV = OpenCV.getInstance();
        //Load Haar Cascade Classifier
        try {
            haarFilter = new CascadeClassifier(openCV.getLibraryPath() + CASCADE_FRONTALEFACE);
        } catch (Exception e) {
            System.err.println("Can't create FaceDetector");
            exit(0);
        }
    }

    /**
     * Detected faces present in path URL image, and return number of deteced
     * faces.
     *
     * @param imageURL path to image
     * @param output String url to save image result
     * @return int of detected faces
     */
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

    /**
     * Detected faces present in Mat image, and return number of deteced faces.
     *
     * @param image Mat of type CV_8UC3 or CV_8UC1
     * @param output String url to save image result
     * @return int of detected faces
     */
    public int detectFaces(Mat image, String output) {

        MatOfRect faceDetections = new MatOfRect();
        haarFilter.detectMultiScale(image, faceDetections);

        int detected = faceDetections.toArray().length;
        this.facesDetected = detected;
        
        //Frame recognized athlete faces
        for (Rect rect : faceDetections.toArray()) {
            Core.rectangle(image, new Point(rect.x, rect.y), new Point(rect.x + rect.width, rect.y + rect.height),
                new Scalar(0, 255, 0));
        }

        //Write new file
        Highgui.imwrite(output, image);

        return detected;
    }
    
    /**
     * Detected faces present in Mat image, and return bufferedImage.
     *
     * @param image Mat of type CV_8UC3 or CV_8UC1
     * @return int of detected faces
     */
    public BufferedImage detectFacesToBufferedImage(Mat image) {

        MatOfRect faceDetections = new MatOfRect();
        haarFilter.detectMultiScale(image, faceDetections);

        int detected = faceDetections.toArray().length;
        this.facesDetected = detected;
        
        //Frame recognized athlete faces
        for (Rect rect : faceDetections.toArray()) {
            Core.rectangle(image, new Point(rect.x, rect.y), new Point(rect.x + rect.width, rect.y + rect.height),
                new Scalar(0, 255, 0));
        }

        //Write new bufferedImage
        return openCV.matToBufferedImage(image);
    }

    /**
     * detectedFaces getter
     *
     * @return int of detected faces number.
     */
    public int getFacesDetected() {
        return this.facesDetected;
    }

}
