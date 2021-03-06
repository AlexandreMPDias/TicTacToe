package view;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;

/**
 * ImageLoader class.
 * Class created to statically load all images to decrease I/O times during runtime.
 * Generates files on {@code BufferedImage} and {@code ImageIcon}.
 */
class ImageLoader {

  private static Map<ImageID, BufferedImage> map;

  static {
    map = new HashMap<>();
    for (ImageID im : ImageID.values()) {
      try {
        map.put(im, loadBuf(im));
      } catch (IOException e) {
        e.printStackTrace();
      }
    }
  }

  static private BufferedImage loadBuf(ImageID image) throws IOException {
    String location = "data\\image\\";
    File file = new File(location + image.getName());
    return ImageIO.read(file);
  }

  static ImageIcon getIcon(ImageID image) {
    return new ImageIcon(map.get(image));
  }

  static BufferedImage getBufImg(ImageID image) {
    return map.get(image);
  }

  public enum ImageID {
    BG1("BG1.jpg"), BG2("BG2.jpg"), BG3("BG3.jpg"), BG4("BG4.jpg"), BG5("BG5.jpg"), BG6(
        "BG6.jpg"), BG7("BG7.jpg"), BG8("BG8.jpg"), BG9("BG9.jpg"), O("O.png"), X("X.png"), Icon(
        "Icon.jpg"),
    AlertIcon("alert.gif"), Ask("interrogation.jpg");

    private String imageName;

    ImageID(String name) {
      imageName = name;
    }

    public String getName() {
      return imageName;
    }
  }

}
