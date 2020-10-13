package com.zdya.util;

import com.alibaba.fastjson.JSONObject;
import com.zdya.Entity.ResponseEntity;

import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.SignatureException;
import java.util.*;

public class ZSSigner {

    private String busPubKey;
    private String privateKey;
    private FaceSigner faceSigner;
    private FaceVerifier faceVerifier;

    public ZSSigner(String algorithm, String privateKey) throws Exception {
        this.privateKey = privateKey;
        faceSigner = new FaceSigner(algorithm,privateKey);
        faceVerifier = new FaceVerifier(algorithm);
    }

    public void setBusPubKey(String busPubKey) {
        this.busPubKey = busPubKey;
    }

    public String createSignedDataString(Map contentMap){
        TreeMap content_treeMap = new TreeMap(contentMap);
        if (content_treeMap.get("timestamp")==null)
            content_treeMap.put("timestamp",String.valueOf(System.currentTimeMillis()));

        String sign = signToBase64(content_treeMap);

        ResponseEntity model = new ResponseEntity("1.0", sign, content_treeMap);
        String signed = JSONObject.toJSONString(model);

        return signed;
    }

    /**
     * 构造 模拟业务系统 请求/发送/返回/接收 实体字符串
     * @param contentMap
     * @return
     */
    public String createBusinessSignedDataString(Map contentMap){
        TreeMap content = new TreeMap(contentMap);
        String respSign = signToBase64(content);

        ResponseEntity model = new ResponseEntity("1.0", respSign, content);
        return JSONObject.toJSONString(model);
    }

    /**
     * 节点签名
     * @param map
     * @return
     */
    public String signToBase64(TreeMap map) {
        return signToBase64(map,privateKey);
    }

    /**
     * 签名
     * @param map
     * @return
     */
    public String signToBase64Open(TreeMap map, String privateKey) {
        return signToBase64(map,privateKey);
    }

    /**
     * 节点签名
     * @param signResource
     * @return
     */
    public String signToBase64(String signResource) {
        return signToBase64(signResource,privateKey);
    }

    /**
     * 链用户签名 测试使用,正式环境去掉
     * @param signResource
     * @param priKeyBase64
     * @return
     */
    public String chainUserSignToBase64(String signResource,String priKeyBase64) {
        return signToBase64(signResource,priKeyBase64);
    }

    /**
     * 签名
     * @param map
     * @param prikeyBase64
     * @return
     */
    private String signToBase64(TreeMap map,String prikeyBase64) {
//        String signResource = JSONObject.toJSONString(map);
        String signResource = JSONObject.toJSONString(map);

        return signToBase64(signResource,prikeyBase64);
    }

    /**
     * 签名
     * @param signResource
     * @param prikeyBase64
     * @return
     */
    public String signToBase64(String signResource,String prikeyBase64) {

        /*if (signer == null) {
            try {
                signer = Signer.getInstance(ALGU_NAME, prikeyBase64);
            } catch (NoSuchAlgorithmException e) {
                logger.error("没有该算法", e);
                e.printStackTrace();
            } catch (InvalidKeySpecException e) {
                logger.error("不可用的私钥", e);
                e.printStackTrace();
            }
        }

        String signed = null;
        try {
            signed = signer.signToBase64(signResource);
        } catch (UnsupportedEncodingException e) {
            logger.error("不支持的编码",e);
            e.printStackTrace();
        } catch (SignatureException e) {
            logger.error("签名异常",e);
            e.printStackTrace();
        } catch (InvalidKeyException e) {
            logger.error("不可用的私钥",e);
            e.printStackTrace();
        }
        return signed;*/

        String signed = null;
        try {
            signed = faceSigner.signToBase64(signResource);
        }  catch (Exception e) {
            e.printStackTrace();
        }
        return signed;
    }

    /**
     * 验证签名
     * @param signResource
     * @param signedBase64
     * @param pubkeyBase64
     * @return
     */
    public boolean verifySignWithPublicKeyBase64(String signResource,String signedBase64, String pubkeyBase64) {

        /*if (verifier==null) {
            try {
                verifier = Verifier.getInstance(ALGU_NAME);
            } catch (CertificateException e) {
                logger.error("证书异常", e);
                e.printStackTrace();
            } catch (NoSuchProviderException e) {
                logger.error("NoSuchProviderException", e);
                e.printStackTrace();
            } catch (NoSuchAlgorithmException e) {
                logger.error("没有该算法异常", e);
                e.printStackTrace();
            }
        }

        boolean res = false;
        try {
            res = verifier.verify(signResource,signedBase64,pubkeyBase64);
        } catch (UnsupportedEncodingException e) {
            logger.error("不支持的编码",e);
            e.printStackTrace();
        } catch (InvalidKeySpecException e) {
            logger.error("InvalidKeySpecException",e);
            e.printStackTrace();
        } catch (SignatureException e) {
            logger.error("SignatureException",e);
            e.printStackTrace();
        } catch (InvalidKeyException e) {
            logger.error("InvalidKeyException",e);
            e.printStackTrace();
        }
        System.out.println("验签结果:"+res);
        return res;*/
        return faceVerifier.checkString(signResource, signedBase64, pubkeyBase64);
    }

    /**
     * 验证baas系统签名
     */
    public boolean verifyBaasSign(Object content,String signedBase64, String pubKey) {

        return faceVerifier.checkJsonObject(content,signedBase64,pubKey);
    }

    /**
     * 自己写的map按key排序转字符串 有以下遗漏
     * 未处理数组 ...
     *
     * @param map
     * @return
     */
    public String convertMapToSignString(Map<String,Object> map){

        Set<String> keySet = map.keySet();
        List<String> keyList = new ArrayList<String>(keySet);
        Collections.sort(keyList);
//        System.out.println("keyList:"+keyList);
        StringBuilder sBuilder = new StringBuilder();
        sBuilder.append("\"{");
        for (String s:keyList) {
            sBuilder.append("\""+s+"\":");
            Object value = map.get(s);
            if (value.getClass().equals(String.class)){
                sBuilder.append("\""+value+"\"");
            }else if(value instanceof Map){
                sBuilder.append(convertMapToSignString((Map<String, Object>) value));
            }else {
                System.err.println("其他类型:"+value.getClass().getName());
            }
            sBuilder.append(",");
        }
        String lastChar = sBuilder.substring(sBuilder.length()-1);
        if (lastChar.equals(",")){
            sBuilder.deleteCharAt(sBuilder.length()-1);
        }
        sBuilder.append("}\"");

        return sBuilder.toString();
    }



}
