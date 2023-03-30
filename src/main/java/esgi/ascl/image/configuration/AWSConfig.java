package esgi.ascl.image.configuration;


import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.client.builder.AwsClientBuilder;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AWSConfig {

    @Value("${amazon.aws.accesskey}")
    private String access_key;

    @Value("${amazon.aws.secretkey}")
    private String secret_key;

    @Value("${amazon.aws.region}")
    private String region;

    @Value("${amazon.aws.serviceEndPoint}")
    private String serviceEndPoint;


    public AWSStaticCredentialsProvider amazonAWSCredentials() {
        return new AWSStaticCredentialsProvider(
                new BasicAWSCredentials(access_key, secret_key)
        );
    }

    public AwsClientBuilder.EndpointConfiguration endpointConfiguration() {
        return new AwsClientBuilder.EndpointConfiguration(serviceEndPoint, region);
    }

    @Bean
    public AmazonS3 s3(){
        return AmazonS3ClientBuilder
                .standard()
                .withEndpointConfiguration(endpointConfiguration())
                .withCredentials(amazonAWSCredentials())
                .build();
    }

}
