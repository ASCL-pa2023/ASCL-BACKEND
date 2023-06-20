package esgi.ascl.image.service;

import com.amazonaws.AmazonServiceException;
import com.amazonaws.services.s3.model.Bucket;
import com.amazonaws.services.s3.model.S3Object;
import esgi.ascl.image.configuration.AWSConfig;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.File;
import java.util.List;

@Service
public class AWSService {

    @Value("${amazon.aws.bucketname}")
    private String bucketName;
    private final AWSConfig awsConfig;


    public AWSService(AWSConfig awsConfig) {
        this.awsConfig = awsConfig;
    }

    public void createBucket(String bucketName) {
        if(awsConfig.s3().doesBucketExistV2(bucketName)) {
            System.out.println("Bucket already exists");
            return;
        }
        awsConfig.s3().createBucket(bucketName);
    }

    public List<Bucket> listBuckets() {
        return awsConfig.s3().listBuckets();
    }

    public void deleteBucket(String bucketName) {
        try {
            awsConfig.s3().deleteBucket(bucketName);
        } catch (AmazonServiceException e) {
            System.err.println(e.getErrorMessage());
            System.exit(1);
        }
    }

    public S3Object getObject(String filename){
        var s3Object = new S3Object();
        try {
            s3Object = awsConfig.s3().getObject(bucketName, filename);
        } catch (AmazonServiceException e) {
            System.err.println(e.getErrorMessage());
            System.exit(1);
        }
        return s3Object;
    }

    public void putObject(String filename, File file){
        try {
            awsConfig.s3().putObject(bucketName, filename, file);
        } catch (AmazonServiceException e) {
            System.err.println(e.getErrorMessage());
            System.exit(1);
        }
    }

    public void deleteObject(String filename){
        try {
            awsConfig.s3().deleteObject(bucketName, filename);
        } catch (AmazonServiceException e) {
            System.err.println(e.getErrorMessage());
            System.exit(1);
        }
    }

}
