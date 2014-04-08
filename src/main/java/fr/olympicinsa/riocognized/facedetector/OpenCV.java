package fr.olympicinsa.riocognized.facedetector;

import java.awt.image.BufferedImage;
import java.awt.image.DataBufferByte;
import static java.lang.System.exit;
import org.opencv.core.Core;
import org.opencv.core.CvType;
import org.opencv.core.Mat;
import org.opencv.core.MatOfRect;
import org.opencv.core.Point;
import org.opencv.core.Rect;
import org.opencv.core.Scalar;
import org.opencv.highgui.Highgui;
import org.opencv.objdetect.CascadeClassifier;

public class OpenCV {

    private static String libraryPath;
    private static OpenCV INSTANCE = null;

    /**
     * Load OpenCV JNI Native Library
     */
    public static void loadLibrary() {
        try {
            System.load(libraryPath + "libopencv_java248.so");
        } catch (Exception e) {
            System.out.println("classPath=" + System.getProperty("java.library.path"));
        }
    }

    /**
     * OpenCV constructor
     *
     * @param libraryURL String url path to library
     *
     */
    private OpenCV(String libraryURL) {
        OpenCV.libraryPath = libraryURL;
        //Load OpenCV library
        try {
            loadLibrary();
        } catch (Exception e) {
            System.err.println("Can't initialize OpenCV");
        }
    }

    /**
     * OpenCV default constructor
     */
    private OpenCV() {
        OpenCV.libraryPath = "/opt/openCV/";
        //Load OpenCV library
        try {
            loadLibrary();
        } catch (Exception e) {
            System.err.println("Can't initialize OpenCV");
        }
    }

    /**
     * OpenCV default accessor
     *
     * @return OpenCV Instance
     */
    public static OpenCV getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new OpenCV();
        }
        return INSTANCE;
    }

    /**
     * OpenCV default accessor
     *
     * @return OpenCV Instance
     */
    public static OpenCV getInstance(String libURL) {
        if (INSTANCE == null) {
            INSTANCE = new OpenCV(libURL);
        }
        return INSTANCE;
    }

    /**
     * Converts/writes a Mat into a BufferedImage.
     *
     * @param matrix Mat of type CV_8UC3 or CV_8UC1
     * @return BufferedImage of type TYPE_3BYTE_BGR or TYPE_BYTE_GRAY
     */
    public BufferedImage matToBufferedImage(Mat matrix) {
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

    /**
     * Converts/writes a BufferedImage into a Mat.
     *
     * @param image BufferedImage of type TYPE_3BYTE_BGR or TYPE_BYTE_GRAY
     * @return matrix Mat of type CV_8UC3 or CV_8UC1
     */
    public Mat BufferedImagetoMat(BufferedImage image) {
        int rows = image.getWidth();
        int cols = image.getHeight();
        byte[] data = ((DataBufferByte) image.getRaster().getDataBuffer()).getData();
        Mat mat = new Mat(cols, rows, CvType.CV_8UC3);
        mat.put(0, 0, data);
        return mat;
    }

    /**
     * libraryPath getter
     *
     * @return String of library path
     */
    public String getLibraryPath() {
        return OpenCV.libraryPath;
    }
}
