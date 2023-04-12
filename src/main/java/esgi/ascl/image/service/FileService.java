package esgi.ascl.image.service;

import com.amazonaws.services.s3.model.Bucket;
import com.amazonaws.services.s3.model.S3Object;
import com.amazonaws.util.IOUtils;
import esgi.ascl.image.exceptions.ConversionException;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

@Service
public class FileService {

    private final AWSService awsService;

    public FileService(AWSService awsService) {
        this.awsService = awsService;
    }


    public List<Bucket> getAllBucket() {
        return awsService.listBuckets();
    }

    public S3Object OLDgetFile(String filename) {
        var s3Object = awsService.getObject(filename);
        var uri = s3Object.getObjectContent().getHttpRequest().getURI();
        System.out.println("URI : " + uri);

        return s3Object;
    }

    public byte[] getFile(String filename){
        var s3Object = awsService.getObject(filename);
        try {
            return s3ObjectToByteArray(s3Object);
        } catch (ConversionException e) {
            throw new RuntimeException(e);
        }
    }

    public S3Object putFile(MultipartFile multipartFile) {
        //Envoyer le fichier sur S3
        UUID uuid = UUID.randomUUID();
        try {
            File file = multipartToFile(multipartFile);
            awsService.putObject(String.valueOf(uuid), file);
        } catch (IOException e) {
            e.printStackTrace();
        }

        //Aller chercher l'image avec l'uuid
        return awsService.getObject(String.valueOf(uuid));
    }


    /**
     * Convert MultipartFile to File
     * <br>
     * Post n°24 from : https://stackoverflow.com/questions/24339990/how-to-convert-a-multipart-file-to-file
     * @param multipartFile
     * @return File
     * @throws IOException
     */
    private File multipartToFile(MultipartFile multipartFile) throws IOException {
        File convFile = new File(System.getProperty("java.io.tmpdir") + "/" + Objects.requireNonNull(multipartFile.getOriginalFilename()));
        multipartFile.transferTo(convFile);
        return convFile;
    }

    /**
     * Convert S3Object to byte[]
     * @param s3Object
     * @return byte[]
     * @throws ConversionException
     */
    private byte[] s3ObjectToByteArray(S3Object s3Object) throws ConversionException {
        var inputStream = s3Object.getObjectContent();
        byte[] bytes;
        try{
            bytes = IOUtils.toByteArray(inputStream);
        } catch (IOException e) {
            e.printStackTrace();
            throw new ConversionException("S3Object to byte[] error");
        }
        return bytes;
    }

}
