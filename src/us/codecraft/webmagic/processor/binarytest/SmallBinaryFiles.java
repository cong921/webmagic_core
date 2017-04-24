package us.codecraft.webmagic.processor.binarytest;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

/** JDK 7+. */
public class SmallBinaryFiles {
  
  public static void main(String... aArgs) throws IOException{
    SmallBinaryFiles binary = new SmallBinaryFiles();
    byte[] bytes = binary.readSmallBinaryFile(FILE_NAME);
    log("Small - size of file read in:" + bytes.length);
    binary.writeSmallBinaryFile(bytes, OUTPUT_FILE_NAME);
  }

  final static String FILE_NAME = "E:/test.doc";
  final static String OUTPUT_FILE_NAME = "C:\\Temp\\cottage_output.txt";
  
  byte[] readSmallBinaryFile(String aFileName) throws IOException {
    Path path = Paths.get(aFileName);
    return Files.readAllBytes(path);
  }
  
  void writeSmallBinaryFile(byte[] aBytes, String aFileName) throws IOException {
    Path path = Paths.get(aFileName);
    Files.write(path, aBytes); //creates, overwrites
  }
  
  private static void log(Object aMsg){
    System.out.println(String.valueOf(aMsg));
  }
  
  
}  