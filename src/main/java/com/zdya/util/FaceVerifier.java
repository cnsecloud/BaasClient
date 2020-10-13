package com.zdya.util;

import com.zdya.util.em.security.Verifier;


public class FaceVerifier {

    private Verifier verifier;

    public FaceVerifier(String algorithm) throws Exception {
        verifier = Verifier.getInstance(algorithm);
    }

    public boolean checkString(String sourceForUTF8, String signForBase64, String publicKeyForBase64) {
        try {
            return verifier.verify(sourceForUTF8, signForBase64, publicKeyForBase64);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean checkJsonObject(Object object, String signForBase64, String publicKeyForBase64) {
        return checkString(JsonUtil.toASCIIJsonString(object), signForBase64, publicKeyForBase64);
    }
}
