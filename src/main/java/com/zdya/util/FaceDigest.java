package com.zdya.util;

import com.zdya.util.em.security.DigestHandler;
import com.zdya.util.em.security.SecurityUtil;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.io.InputStream;
import java.security.NoSuchAlgorithmException;

/**
 * 摘要信息
 *
 * @author ZhangHongYuan
 * @date 2020-4-26
 */
public class FaceDigest {

    private String algorithm;
    private DigestHandler digestHandler;

    public FaceDigest(String algorithm) {
        this.algorithm = algorithm;
        digestHandler = DigestHandler.getInstance(SecurityUtil.getAlgorithmBeforeWith(algorithm));
    }

    public byte[] digest(String dataInUtf8) throws NoSuchAlgorithmException {
        return digestHandler.digest(dataInUtf8);
    }

    public byte[] digest(byte[] data) throws NoSuchAlgorithmException {
        return digestHandler.digest(data);
    }

    public byte[] digest(InputStream inputStream) throws IOException, NoSuchAlgorithmException {
        return digestHandler.digest(inputStream);
    }

    public byte[] digestFile(String path) throws IOException, NoSuchAlgorithmException {
        return digestHandler.digestFile(path);
    }


    public String digestToBase64(byte[] data) throws NoSuchAlgorithmException {
        return digestHandler.digestToBase64(data);
    }

    public String digestToBase64(String dataInUtf8) throws NoSuchAlgorithmException {
        return digestHandler.digestToBase64(dataInUtf8);
    }

    public String digestToBase64(InputStream inputStream) throws NoSuchAlgorithmException, IOException {
        return digestHandler.digestToBase64(inputStream);
    }

    public String digestFileToBase64(String path) throws IOException, NoSuchAlgorithmException {
        return digestHandler.digestFileToBase64(path);
    }

    public String digestToHex(byte[] data) throws NoSuchAlgorithmException {
        return digestHandler.digestToHex(data);
    }

    public String digestToHex(String dataInUtf8) throws NoSuchAlgorithmException {
        return digestHandler.digestToHex(dataInUtf8);
    }

    public String digestToHex(InputStream inputStream) throws NoSuchAlgorithmException, IOException {
        return digestHandler.digestToHex(inputStream);
    }

    public String digestFileToHex(String path) throws IOException, NoSuchAlgorithmException {
        return digestHandler.digestFileToHex(path);
    }

}
