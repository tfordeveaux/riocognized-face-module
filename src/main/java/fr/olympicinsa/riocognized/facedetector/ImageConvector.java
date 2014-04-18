/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package fr.olympicinsa.riocognized.facedetector;

/**
 *
 * @author imane
 */
public class ImageConvector {
    
// Convert BufferedImage to Mat
     public Mat matify(BufferedImage im) {
        // Convert INT to BYTE
         im = new BufferedImage(im.getWidth(), im.getHeight(),BufferedImage.TYPE_3BYTE_BGR);
        // Convert bufferedimage to byte array
         byte[] pixels = ((DataBufferByte) im.getRaster().getDataBuffer()).getData();
        // Create a Matrix the same size of image
        Mat image = new Mat(im.getHeight(), im.getWidth(), CvType.CV_8UC3);
        // Fill Matrix with image values
        image.put(0, 0, pixels);

        return image;
    }

}
