package com.zdya.util;

import com.zdya.util.em.security.Signer;

import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.SignatureException;
import java.security.spec.InvalidKeySpecException;

public class FaceSigner {
    private Signer signer;

    public FaceSigner(String algorithm, String privateKey) throws InvalidKeySpecException, NoSuchAlgorithmException {
        signer = Signer.getInstance(algorithm,privateKey);
    }

    public String signToBase64(String sourceForUTF8) throws UnsupportedEncodingException, SignatureException, InvalidKeyException, InvalidKeySpecException, NoSuchAlgorithmException {
        return signer.signToBase64(sourceForUTF8);
    }
    public String signObjectToBase64(Object object) throws UnsupportedEncodingException, SignatureException, InvalidKeyException, InvalidKeySpecException, NoSuchAlgorithmException {
        return signToBase64(JsonUtil.toASCIIJsonString(object));
    }
}
