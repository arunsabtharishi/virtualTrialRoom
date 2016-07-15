package com.epsilon.vtr.service;

import java.awt.AlphaComposite;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.util.List;

import javax.imageio.ImageIO;

import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.core.MatOfRect;
import org.opencv.core.Rect;
import org.opencv.highgui.Highgui;
import org.opencv.objdetect.CascadeClassifier;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.epsilon.vtr.dao.TrailRoomDao;
import com.epsilon.vtr.model.Employee;
import com.epsilon.vtr.model.EmployeeTrailRoom;
import com.epsilon.vtr.model.Item;

@Service("virtualTrialService")
@Transactional
public class VirtualTrialServiceImpl implements VirtualTrialService {

    String path = "src/main/resources/temp/";

    @Autowired
    private InventoryService inventoryService;

    @Autowired
    private TrailRoomDao trailRoomDao;

    @Override
    public void virtualTrial(Employee employee) throws IOException {
        System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
        String sourceFile = "src/main/resources/haarcascade/haarcascade_frontalface_alt.xml";
        File f = new File("C:\\Users\\vbora\\Desktop\\vtr\\haarcascade_frontalface_alt.xml");
        String fileName = employee.getProfilePhotoName();
        String fileExtension = determineFileExtension(employee.getProfilePhotoContentType());
        File tempFile = File.createTempFile(fileName,"."+fileExtension);
        FileOutputStream fos = new FileOutputStream(tempFile);
        fos.write(employee.getProfilePhoto());
        fos.close();
        CascadeClassifier faceDetector = new CascadeClassifier(f.getAbsolutePath());
        Mat image = Highgui.imread(tempFile.getAbsolutePath());

        MatOfRect detections = new MatOfRect();
        faceDetector.detectMultiScale(image, detections);

        System.out.println(String.format("Detected %s faces", detections.toArray().length));

        List<Item> findAllItems = inventoryService.findAllItems();
        findAllItems.forEach( item -> {
            int x = 0;
            int y= 0;
            for (Rect rect : detections.toArray()) {
                x = rect.x/3;
                y = rect.y + rect.height + (rect.y + rect.height) /10;
            }
            File trialFile =  doAction(tempFile,item,x, y);
            if(trialFile.exists()) {
                EmployeeTrailRoom employeeTrailRoom = new EmployeeTrailRoom();
                employeeTrailRoom.setEmpId(employee.getId());
                employeeTrailRoom.setItemId(item.getId());
                employeeTrailRoom.setEmpProfilePhoto(employee.getProfilePhoto());
                employeeTrailRoom.setEmpProfilePhotoContentType(employee.getProfilePhotoContentType());
                employeeTrailRoom.setEmpProfilePhotoName(employee.getProfilePhotoName());
                byte[] byteArray;
                try {
                    byteArray = Files.readAllBytes(trialFile.toPath());
                    System.out.println(trialFile.length());
                    BufferedImage overlay = ImageIO.read(trialFile);

                    overlay = createResizedCopy(overlay, x+y+((x+y)/2), x+y+((x+y)/4), false);
                    ByteArrayOutputStream baos = new ByteArrayOutputStream();
                    ImageIO.write(overlay, "png", baos);
                    byte[] bytes = baos.toByteArray();
                    employeeTrailRoom.setEmpTrailProfilePhoto(bytes);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                employeeTrailRoom.setEmpTrailProfilePhotoContentType("png");

                employeeTrailRoom.setEmpTrailProfilePhotoName("combined");
               // trailRoomDao.saveTrailRoom(employeeTrailRoom);
                trialFile.deleteOnExit();
            }
        });
        tempFile.deleteOnExit();
    }

    private String determineFileExtension(String contentType){
        return "jpg";
    }

    private File doAction(File inputFile,Item item,int x, int y){
        try{
                //File itemFile = File.createTempFile(item.getItemName(),"."+determineFileExtension(item.getItemPhotoContentType()));
                //FileOutputStream fos = new FileOutputStream(itemFile);
                //fos.write(item.getItemPhoto());
                //fos.close();

                BufferedImage image = ImageIO.read(inputFile);

                //BufferedImage overlay = ImageIO.read(itemFile);

                //overlay = createResizedCopy(overlay, x+y+((x+y)/2), x+y+((x+y)/4), false);

                // create the new image, canvas size is the max. of both image sizes
                //int w = Math.max(image.getWidth(), overlay.getWidth());
               // int h = Math.max(image.getHeight(), overlay.getHeight());
              //  BufferedImage combined = new BufferedImage(w, h, BufferedImage.TYPE_INT_ARGB);

                // paint both images, preserving the alpha channels
                //Graphics g = combined.getGraphics();
                //g.drawImage(image, 0, 0, null);
               // g.drawImage(overlay, x/2, y, null);

                // Save as new image
               // File tempFile = File.createTempFile(inputFile.getName()+"-combined",".png");
               // ImageIO.write(combined, "PNG", tempFile);



            /* load source images
            File shirtFile = new File("C:\\Users\\vbora\\Desktop\\vtr\\shirt.jpg");
            //BufferedImage image = ImageIO.read(inputFile);

            BufferedImage overlay = ImageIO.read(shirtFile);

            overlay = createResizedCopy(overlay, x+y+((x+y)/2), x+y+((x+y)/4), false);


            int w = Math.max(image.getWidth(), overlay.getWidth());
            int h = Math.max(image.getHeight(), overlay.getHeight());
            BufferedImage combined = new BufferedImage(w, h, BufferedImage.TYPE_INT_ARGB);

            Graphics g = combined.getGraphics();
            g.drawImage(image, 0, 0, null);
            g.drawImage(overlay, x/2, y, null);

            File tempFile = File.createTempFile(inputFile.getName()+"-combined",".png");
            ImageIO.write(combined, "PNG", tempFile);
            */

           // return tempFile;
        }catch(Exception e)
        {
            System.out.println("exception ");
        }
        return null;
    }

    BufferedImage createResizedCopy(Image originalImage,
            int scaledWidth, int scaledHeight,
            boolean preserveAlpha)
    {
        System.out.println("resizing...");
        int imageType = preserveAlpha ? BufferedImage.TYPE_INT_RGB : BufferedImage.TYPE_INT_ARGB;
        BufferedImage scaledBI = new BufferedImage(scaledWidth, scaledHeight, imageType);
        Graphics2D g = scaledBI.createGraphics();
        if (preserveAlpha) {
            g.setComposite(AlphaComposite.Src);
        }
        g.drawImage(originalImage, 0, 0, scaledWidth, scaledHeight, null);
        g.dispose();
        return scaledBI;
    }

    @Override
    public List<EmployeeTrailRoom> findAllTrailsFor(Employee employee) {
        // TODO Auto-generated method stub
        return null;
    }


}
