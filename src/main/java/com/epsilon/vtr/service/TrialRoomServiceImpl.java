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
import java.io.UnsupportedEncodingException;
import java.nio.file.Files;
import java.util.List;

import javax.imageio.ImageIO;
import javax.mail.MessagingException;

import org.apache.commons.codec.binary.Base64;
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
import com.epsilon.vtr.model.Item;
import com.epsilon.vtr.model.Profile;
import com.epsilon.vtr.model.TrailRoom;
import com.epsilon.vtr.vo.TrailRoomVO;

@Service("trialService")
@Transactional
public class TrialRoomServiceImpl implements TrialRoomService {

    @Autowired
    private InventoryService inventoryService;

    @Autowired
    private ProfileService profileService;

    @Autowired
    MailService mailService;

    @Autowired
    private TrailRoomDao trailRoomDao;


    @Override
    public TrailRoom findById(int id){
        return trailRoomDao.findById(id);
    }

    @Override
    public void saveTrailRoom(TrailRoom trailRoom) {
        trailRoomDao.saveTrailRoom(trailRoom);
    }

    @Override
    public void trialRoom() throws IOException {
        System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
        String sourceFile = "src/main/resources/haarcascade/haarcascade_frontalface_alt.xml";
        File f = new File("C:\\Users\\vbora\\Desktop\\vtr\\haarcascade_frontalface_alt.xml");
        List<Profile> profiles = profileService.findAllProfiles();
        for(Profile profile:profiles) {
            String profilePhotoFileName = profile.getProfilePhotoName();
            String profilePhotoFileExtension = determineFileExtension(profile.getProfilePhotoContentType());
            File profilePhotoTempFile = File.createTempFile(profilePhotoFileName,"."+profilePhotoFileExtension);
            FileOutputStream fos = new FileOutputStream(profilePhotoTempFile);
            fos.write(profile.getProfilePhoto());
            fos.close();
            CascadeClassifier faceDetector = new CascadeClassifier(f.getAbsolutePath());
            Mat image = Highgui.imread(profilePhotoTempFile.getAbsolutePath());

            MatOfRect detections = new MatOfRect();
            faceDetector.detectMultiScale(image, detections);

            System.out.println(String.format("Detected %s faces", detections.toArray().length));

            List<Item> findAllItems = inventoryService.findAllItems();
            for(Item item: findAllItems) {
                int x = 0;
                int y= 0;
                for (Rect rect : detections.toArray()) {
                    x = rect.x/3;
                    y = rect.y + rect.height + (rect.y + rect.height) /10;
                }
                List<TrailRoom> trailRoomForThisProfile = trailRoomDao.findAllTrailsByProfileAndItemId(profile.getId(), item.getId());
                if(trailRoomForThisProfile.size() ==0) {
                    File trialFile =  doAction(profilePhotoTempFile,item,x, y);
                    if(trialFile.exists()) {
                        TrailRoom trailRoom = new TrailRoom();
                        trailRoom.setProfileId(profile.getId());
                        trailRoom.setItemId(item.getId());
                        try {
                            byte[] byteArray = Files.readAllBytes(trialFile.toPath());
                            System.out.println(trialFile.length());
                            BufferedImage overlay = ImageIO.read(trialFile);

                            overlay = createResizedCopy(overlay, x+y+((x+y)/2), x+y+((x+y)/4), false);
                            ByteArrayOutputStream baos = new ByteArrayOutputStream();
                            ImageIO.write(overlay, "png", baos);
                            byte[] bytes = baos.toByteArray();
                            trailRoom.setTrailProfilePhoto(bytes);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        trailRoom.setTrailProfilePhotoContentType("png");

                        trailRoom.setTrailProfilePhotoName("combined");
                        trailRoomDao.saveTrailRoom(trailRoom);
                        trialFile.deleteOnExit();
                    }
                }
            }
            profilePhotoTempFile.deleteOnExit();
        }
    }

    private String determineFileExtension(String contentType){
        return "jpg";
    }

    private File doAction(File inputFile,Item item,int x, int y){
        try{
                File itemFile = File.createTempFile(item.getName(),"."+determineFileExtension(item.getPhotoContentType()));
                FileOutputStream fos = new FileOutputStream(itemFile);
                fos.write(item.getPhoto());
                fos.close();

                BufferedImage image = ImageIO.read(inputFile);

                BufferedImage overlay = ImageIO.read(itemFile);

                overlay = createResizedCopy(overlay, x+y+((x+y)/2), x+y+((x+y)/4), false);

                // create the new image, canvas size is the max. of both image sizes
                int w = Math.max(image.getWidth(), overlay.getWidth());
                int h = Math.max(image.getHeight(), overlay.getHeight());
                BufferedImage combined = new BufferedImage(w, h, BufferedImage.TYPE_INT_ARGB);

                // paint both images, preserving the alpha channels
                Graphics g = combined.getGraphics();
                g.drawImage(image, 0, 0, null);
                g.drawImage(overlay, x/2, y, null);

                // Save as new image
                File tempFile = File.createTempFile(inputFile.getName()+"-combined",".png");
                ImageIO.write(combined, "PNG", tempFile);

            return tempFile;
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
    public List<TrailRoom> findAllTrailsFor(Profile profile) {
        return trailRoomDao.findAllTrailsByProfileId(profile.getId());
    }

    @Override
    public List<TrailRoom> findAllTrails() {
        return trailRoomDao.findAllTrails();
    }

    @Override
    public void deleteTrailById(int id) {
        trailRoomDao.deleteTrailById(id);

    }

    @Override
    public void sendEmail(TrailRoom trailRoom) throws UnsupportedEncodingException, MessagingException {
        TrailRoomVO trailRoomVO = new TrailRoomVO();
        Profile profile = profileService.findById(trailRoom.getProfileId());
        trailRoomVO.setFirstName(profile.getFirstName());
        trailRoomVO.setLastName(profile.getLastName());
        trailRoomVO.setEmailAddress(profile.getEmailAddress());
        trailRoomVO.setProfileTrailPhoto(trailRoom.getTrailProfilePhoto());
        byte[] encodeBase64 = Base64.encodeBase64(trailRoom.getTrailProfilePhoto());
        String base64Encoded = new String(encodeBase64, "UTF-8");
        trailRoomVO.setBase64EncodedForProfileTrailPhoto(base64Encoded);

        mailService.sendEmail(trailRoomVO);
    }

}
