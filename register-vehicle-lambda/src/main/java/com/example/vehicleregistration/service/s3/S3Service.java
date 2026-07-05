package com.example.vehicleregistration.service.s3;

import com.example.vehicleregistration.config.S3Config;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;

import java.nio.charset.StandardCharsets;
import java.util.UUID;

/**
 * ============================================================================
 * S3Service
 *
 * Handles operations related to Amazon S3.
 *
 * Responsibilities:
 * - Upload vehicle documents to Amazon S3
 * - Generate unique object keys for uploaded documents
 * ============================================================================
 */
public class S3Service {

    private static final String BUCKET_NAME =
            System.getenv("DOCUMENT_BUCKET");

    private final S3Client s3Client =
            S3Config.getS3Client();

    /**
     * Uploads a document to Amazon S3.
     *
     * @param vehicleNumber Vehicle Number
     * @param documentName Document Name
     * @return Amazon S3 Object Key
     */
    public String uploadDocument(
            String vehicleNumber,
            String documentName) {

        String objectKey = String.format(
                "%s/%s-%s",
                vehicleNumber,
                UUID.randomUUID(),
                documentName
        );

        String content = """
                Vehicle Registration Document

                Vehicle Number : %s
                Document Name  : %s
                """.formatted(vehicleNumber, documentName);

        PutObjectRequest request =
                PutObjectRequest.builder()
                        .bucket(BUCKET_NAME)
                        .key(objectKey)
                        .build();

        s3Client.putObject(
                request,
                RequestBody.fromString(content, StandardCharsets.UTF_8)
        );

        return objectKey;

    }

}