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

    CascadeClassifier haarFilter;

    public static void loadLibrary() {
        try {
            System.load("/opt/openCV/libopencv_java248.so");
        } catch (Exception e) {
            System.out.println("classPath=" + System.getProperty("java.library.path"));
        }
    }

    public FaceDetector(String haarURL) {
        loadLibrary();
        //Load Haar Cascade Classifier
        try {
            haarFilter = new CascadeClassifier(haarURL);
        } catch (Exception e) {
            System.err.println("Can't create FaceDetector");
            exit(0);
        }
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

    public int detectFaces(Mat image, String output) {

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
     * Converts/writes a Mat into a BufferedImage.
     *
     * @param matrix Mat of type CV_8UC3 or CV_8UC1
     * @return BufferedImage of type TYPE_3BYTE_BGR or TYPE_BYTE_GRAY
     */
    public static BufferedImage matToBufferedImage(Mat matrix) {
        int cols = matrix.cols();
        int rows = matrix.rows();
        int elemSize = (int) matrix.elemSize();
        byte[] data = new byte[cols * rows * elemSize];
        int type;

        matrix.get(0, 0, data);

        switch (matrix.channels()) {
            case 1:
                type = BufferedImage.TYPE_BYTE_GRAY;
                break;

            case 3:
                type = BufferedImage.TYPE_3BYTE_BGR;

                // bgr to rgb
                byte b;
                for (int i = 0; i < data.length; i = i + 3) {
                    b = data[i];
                    data[i] = data[i + 2];
                    data[i + 2] = b;
                }
                break;

            default:
                return null;
        }

        BufferedImage image = new BufferedImage(cols, rows, type);
        image.getRaster().setDataElements(0, 0, cols, rows, data);

        return image;
    }
}
