package esgi.ascl.image.service;

import com.amazonaws.services.s3.model.Bucket;
import com.amazonaws.services.s3.model.S3Object;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.net.URI;
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

    public S3Object getFile(String filename) {
        var s3Object = awsService.getObject(filename);

        var uri = s3Object.getObjectContent().getHttpRequest().getURI();
        System.out.println("URI : " + uri);

        //TODO : Convertir le S3Object en byte[] pour le renvoyer au front ???
        //convert s3Object to byte[]
        // var inputStream = s3Object.getObjectContent();
        // var bytes = IOUtils.toByteArray(inputStream);

        return s3Object;
    }

    public URI putFile(MultipartFile multipartFile) {
        //Envoyer le fichier sur S3
        UUID uuid = UUID.randomUUID();
        try {
            File file = multipartToFile(multipartFile);
            awsService.putObject(String.valueOf(uuid), file);
        } catch (IOException e) {
            e.printStackTrace();
        }

        //  Aller chercher l'image avec l'uuid
        S3Object s3Object = awsService.getObject(String.valueOf(uuid));
        //Retourner l'URI
        return s3Object.getObjectContent().getHttpRequest().getURI();
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

}
