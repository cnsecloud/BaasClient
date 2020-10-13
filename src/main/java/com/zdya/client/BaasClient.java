package com.zdya.client;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.zdya.Entity.*;
import com.zdya.enumeration.ProfitContractMethod;
import com.zdya.util.*;

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.util.*;

public class BaasClient {

    private static final String KEY_UID = "UID";
    private static final String KEY_timestamp = "timestamp";
    private static final String KEY_block_number = "blockNum";
    private static final String KEY_TXID = "TXID";
    private static final String KEY_au = "au";

    private static final String KEY_content = "content";
    private static final String KEY_content_code = "code";
    private static final String KEY_content_reason = "code";
    private static final String KEY_sign = "sign";
    private static final String KEY_version = "version";

    private static final String VALUE_version = "1.0";

    private String appPrivateKey =
            "MIICdgIBADANBgkqhkiG9w0BAQEFAASCAmAwggJcAgEAAoGBAKRFaOqULehW81FN" +
                    "jIOq5ncRAB6zS8EHBPEsItwfDaXBoLLq88wmwshN4CMzHDlasqDrRcp8t1LUpC2V" +
                    "wlIt9Muul1SH7OezJEsb454LH8JOShjGEaMr/nnVg84pNNDS1LowTZ7zZdfKDVJ7" +
                    "Ze1Sb/CRFxsFbT+vfSedD1bQZAiDAgMBAAECgYBN34mpCq3ooq+eL0EZef7fGvQP" +
                    "mbejvgwduGqeJyp1FaF0r+T7NrTH+AL1LNmIZvKfTmk7YHwXjC54XbJM+vQ8h7hF" +
                    "s6/o+cjiGmrUeBS969jZV7MR3jflekHgsDDgnTP0fdvgrUK1CFIWJrqUc5inaSWE" +
                    "4K5wM3zgPPIPCIYHwQJBANlup7+dUPttFSYYcY93CDS8DW9C4mYojQYDwYP1xDLi" +
                    "aJE5vYWGFxm+aoTUIFCzcr2Ryn+t35ER9Q+NnrnFxMsCQQDBaMU0xNJk56mwNR3C" +
                    "SEUFbSxktp0ImRVd05yQiNcMNyARUFdZvZZX2dhM6yl5eIDX/uMcNyUJ6KU0mEON" +
                    "ugwpAkB41KmcLoyGbMRH+2WAWKHSzH6aygyOwRI9uXCdKMLzlCaQLgpIXZAQ2mTP" +
                    "lCKxNkgZWR+zfKi1McmB7y26B1GpAkEAh9ymM1RTi0hlHf+iAPktawtu1Oym0QRg" +
                    "SwG8iJdnoRAUObVbNxIDy4Ce/iFviAHLWG+XcBmYriY77v9K7QMVSQJASg4KQZHk" +
                    "bhCj9wLj6v0aj+hqO0HRIFbjASQh4siticu0r7d7TH/9C2roKaVjenr3R31pnTg5" +
                    "Jb9pVw0pldcnkA==";
    private String appPublicKey = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQCkRWjqlC3oVvNRTYyDquZ3EQAes0vBBwTxLCLcHw2lwaCy6vPMJsLITeAjMxw5WrKg60XKfLdS1KQtlcJSLfTLrpdUh+znsyRLG+OeCx/CTkoYxhGjK/551YPOKTTQ0tS6ME2e82XXyg1Se2XtUm/wkRcbBW0/r30nnQ9W0GQIgwIDAQAB";

    private String UID = "";

    private String signAlorithm = "SHA1WithRSA";

    private String baasPubKey = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQCs5jGjEf87ZBMQ3F8FuPTKHJZcwAagpN48Tua6Gs+hOVULKv4TXIOqPv+MJMVivoMb1cyvXxYzHIXaNdNYfadjISxYc0ekcN3XqZ4HZYnWLC9R7HWm36Xs37UIkLN8aTQH3OHuHoDu9IfIHoT6DIbxCwzwdHGa9iG+hfAtfVyvtQIDAQAB";

    private ZSSigner signer;
    private FaceDigest faceDigest;

    public BaasClient() {
    }

    /**
     * 1.客户端工具初始化方法
     *
     * @param uid           应用唯一标识UID(必传项)
     * @param baasUrlBase   baas系统地址(必传项, 示例:"http://192.126.1.1:8090/")
     * @param privateKey    应用私钥(可传null,传null时将使用默认值)
     * @param algorithm     自定义算法(可传null,默认"SHA1WithRSA")
     * @param baasPublicKey baas系统公钥(可传null,传null时将使用默认值)
     * @throws Exception 需应用捕获的异常
     */
    public BaasClient(String uid, String baasUrlBase, String privateKey, String algorithm, String baasPublicKey) throws Exception {
        if (baasUrlBase == null || baasUrlBase.replace(" ", "").length() == 0) {
            throw new Exception("baas系统地址不能为空");
        }

        UrlUtil.updateAllUrls(baasUrlBase);

        if (uid == null || uid.replace(" ", "").length() == 0) {
            throw new Exception("uid不能为空");
        }
        UID = uid;

        if (algorithm != null) {
            this.signAlorithm = algorithm;
        }
        this.faceDigest = new FaceDigest(this.signAlorithm);

        if (privateKey != null) {
            this.appPrivateKey = privateKey;
        }

        this.signer = new ZSSigner(this.signAlorithm, this.appPrivateKey);

        if (baasPublicKey != null) {
            this.baasPubKey = baasPublicKey;
        }
    }

    /**
     * 2.文本上链
     *
     * @param text 上链的文本内容
     * @return 返回结果对象
     * @throws Exception 需应用捕获的异常
     */
    public InjectObject injectText(String text) throws Exception {
        return inject(text, UID, "1");
    }

    /**
     * 3.文件上链
     *
     * @param filePath 上链的文件路径
     * @return 返回结果对象
     * @throws Exception 需应用捕获的异常
     */
    public InjectObject injectFile(String filePath) throws Exception {
        return inject(filePath, UID, "2");
    }

    /**
     * 资产用户信息上链(增删改)
     *
     * @param assetUser 要操作的资产用户信息JSON格式字符串
     * @param type      操作类型,支持增(0)删(1)改(2)
     * @return 返回结果对象 包含_id与txid键, 都需客户方存储
     * @throws Exception 需应用捕获的异常
     */
    public AUObject injectAssetUser(String assetUser, int type) throws Exception {
        // String ext = JsonUtil.toASCIIJsonString(assetUser);

        type += 4;

        if (type != 4 && type != 5 && type != 6) {
            return Resp.respAUObject("1", "type不合法", "", "", null);
        }

        Map auMap = (Map) JSON.parse(assetUser);
        String _id = (String) auMap.get("_id");
        if (type == 5 || type == 6) {
            if (_id == null || _id.trim().length() == 0) {
                return Resp.respAUObject("1", "删除/修改操作中记录id(_id)不能为空", "", "", null);
            }
        }

        InjectObject injectObject = inject(assetUser, UID, String.valueOf(type));

        if (type == 4) {
            return Resp.respAUObject(injectObject.getCode(), injectObject.getReason(), injectObject.getTxid(), injectObject.getTxid(), injectObject.getTimestamp());
        } else {
            return Resp.respAUObject(injectObject.getCode(), injectObject.getReason(), _id, injectObject.getTxid(), injectObject.getTimestamp());
        }
    }

    /**
     * 收益全产品智能合约调用
     * @param methodName            合约名称
     * @param args                  合约函数入参
     * @return
     * @throws Exception            需应用捕获的异常
     */
    public InjectObject profitProductContract(String methodName, String ...args) throws Exception{
        String secondData = null;
        if (args.length>1){
            secondData = args[1];
        }
        return injectContract(args[0], secondData, methodName, UID);
    }

    /**
     * 4.下载文本
     *
     * @param txid 交易id
     * @return 返回结果对象
     * @throws Exception 需应用捕获的异常
     */
    public DownloadObject downloadText(String txid) throws Exception {
        return download_base(txid, UID, null);
    }

    /**
     * 5.下载文件
     *
     * @param txid           交易id
     * @param outputFilePath 下载到本地的文件路径
     * @return 返回结果对象
     * @throws Exception 需应用捕获的异常
     */
    public DownloadObject downloadFile(String txid, String outputFilePath) throws Exception {
        if (outputFilePath == null)
            outputFilePath = "";
        return download_base(txid, UID, outputFilePath);
    }

    /**
     * 6.查询区块
     *
     * @param blockNumber 要查询的区块号
     * @return 返回结果对象
     * @throws Exception 需应用捕获的异常
     */
    public QueryObject queryBlock(String blockNumber) throws Exception {
        if (blockNumber == null || UID == null) {
            return Resp.respQueryObject("1", "区块号或UID不能为空", null, null, null);
        }

        blockNumber = blockNumber.replace(" ", "");
        UID = UID.replace(" ", "");
        if (blockNumber.length() == 0 || UID.length() == 0) {
            return Resp.respQueryObject("1", "区块号或UID长度不能为0", null, null, null);
        }

        TreeMap<String, String> content = new TreeMap<>();
        content.put(KEY_UID, UID);
        content.put(KEY_block_number, blockNumber);
        content.put(KEY_timestamp, String.valueOf(System.currentTimeMillis()));

        // request baas
        Map respContent_map = httpRequestAndCheckSign(UrlUtil.api_queryBlock, content, baasPubKey);
        return JSON.parseObject(JSON.toJSONString(respContent_map), QueryObject.class);
    }

    /**
     * 7.查询交易简要信息
     *
     * @param txid 要查询的交易id
     * @return 返回结果对象
     * @throws Exception 需应用捕获的异常
     */
    public QueryObject queryTransaction(String txid) throws Exception {
        Map respContent_map = queryTransaction_base(txid, UID, null, null, false);
        return JSON.parseObject(JSON.toJSONString(respContent_map), QueryObject.class);
    }

    /**
     * 7(1).查询资产及用户信息
     *
     * @param txid 资产及用户信息对应的交易id
     * @return 返回结果Map
     * @throws Exception 需应用捕获的异常
     */
    public QueryObject queryAssetUser(String txid) throws Exception {

        Map map = queryTransaction_base(txid, UID, null, null, true);

        return JSON.parseObject(JSON.toJSONString(map), QueryObject.class);
    }

    /**
     * 8. 验证文本交易
     *
     * @param txid   要验证的交易id
     * @param srcTxt 要验证的文本原文
     * @return 返回结果对象
     * @throws Exception 需应用捕获的异常
     */
    public BaseObject veriText(String txid, String srcTxt) throws Exception {
        Map respContent_map = veriTransaction_base(txid, faceDigest.digestToBase64(srcTxt));
        return JSON.parseObject(JSON.toJSONString(respContent_map), BaseObject.class);
    }

    /**
     * 9.验证文件交易
     *
     * @param txid        要验证的交易id
     * @param srcFilePath 要验证的文件原文路径
     * @return 返回结果对象
     * @throws Exception 需应用捕获的异常
     */
    public BaseObject veriFile(String txid, String srcFilePath) throws Exception {

        Map respContent_map = veriTransaction_base(txid, faceDigest.digestFileToBase64(srcFilePath));
        return JSON.parseObject(JSON.toJSONString(respContent_map), BaseObject.class);
    }


    // 验证交易 基础方法
    private Map veriTransaction_base(String TXID, String hash) throws Exception {

        if (TXID == null || UID == null || hash == null)
            return Resp.resp("1", "UID与参数不能为空");

        TXID = TXID.replace(" ", "");
        UID = UID.replace(" ", "");
        hash = hash.replace(" ", "");
        if (TXID.length() == 0 || UID.length() == 0 || hash.length() == 0)
            return Resp.resp("1", "UID与参数长度不能为0");

        TreeMap<String, String> content = new TreeMap<>();
        content.put(KEY_TXID, TXID);
        content.put(KEY_UID, UID);
        content.put("hash", hash);

        return httpRequestAndCheckSign(UrlUtil.api_verify, content, baasPubKey);
    }

    // 下载交易数据 基础方法
    private DownloadObject download_base(String txid, String uid, String outputFilePath) throws Exception {
        if (txid == null || uid == null) {
            return Resp.respDownloadObject("1", "交易id或uid不能为空", "");
        }

        txid = txid.replace(" ", "");
        uid = uid.replace(" ", "");
        if (txid.length() == 0 || uid.length() == 0) {
            return Resp.respDownloadObject("1", "交易id或UID长度不能为0", "");
        }

        TreeMap<String, String> content = new TreeMap<>();
        content.put(KEY_UID, uid);
        content.put(KEY_TXID, txid);
        content.put(KEY_timestamp, String.valueOf(System.currentTimeMillis()));

        // request baas
        Map content_map = httpRequestAndCheckSign(UrlUtil.api_queryTransaction, content, baasPubKey);

        String code = (String) content_map.get(KEY_content_code);
        String reason = (String) content_map.get(KEY_content_reason);

        if (!code.equals("0")) return Resp.respDownloadObject(code, reason, "");
        Map ext_map = (Map) content_map.get("extensions");
        String ext = (String) ext_map.get(txid);
        if (ext == null || ext.length() == 0) return Resp.respDownloadObject("1", "返回对应交易src内容为空", ext);

        Map transaction = (Map) content_map.get("transaction");
        String contentStr = (String) transaction.get("content");
        if (contentStr == null) {
            return Resp.respDownloadObject("1", "返回transaction.content为空", ext);
        }
        Map innerContent_map = (Map) JSONObject.parse(contentStr);
        if (innerContent_map == null) {
            return Resp.respDownloadObject("1", "解析transaction.content出错", ext);
        }
        String type = (String) innerContent_map.get("type");
        if (type == null) {
            return Resp.respDownloadObject("1", "返回transaction.content.type为空", ext);
        }

        reason = "下载成功";
        if (type.equals("1")) return Resp.respDownloadObject(code, reason, ext);

        if (!type.equals("2")) return Resp.respDownloadObject("1", "返回transaction.content.type未知类型", ext);


        String src = "";
        if (outputFilePath != null && outputFilePath.replace(" ", "").length() > 0) {
            HttpDownloadUtil.httpDownloadFileToPath(ext, outputFilePath);
            src = outputFilePath;
        } else {

            String fileType = "dt";

            if (ext.contains("."))
                fileType = ext.substring(ext.lastIndexOf(".") + 1, ext.length()); // [a,b)
            // 下载文件到临时文件路径

            src = System.getProperty("java.io.tmpdir");
            // /var/folders/1j/f3ys4x3x5fzg7d40vw_kjp380000gn/T/
            src = src + UUID.randomUUID().toString().replace("-", "") + "." + fileType;

            reason += "[传入路径不合规,返回临时路径]";
            HttpDownloadUtil.httpDownloadFileToPath(ext, src);
        }

        return Resp.respDownloadObject(code, reason, src);
    }


    /**
     * 查询简要交易信息 基础方法
     *
     * @param TXID           要查询的交易id
     * @param UID            用户身份标识
     * @param outputStream   输出流
     * @param outputFilePath 输出文件路径
     * @return 返回结果Map
     * @throws Exception 需应用捕获的异常
     */
    private Map queryTransaction_base(String TXID, String UID, OutputStream outputStream, String outputFilePath, boolean au) throws Exception {
        if (TXID == null || UID == null) {
            return Resp.resp("1", "交易id或UID不能为空");
        }

        TXID = TXID.replace(" ", "");
        UID = UID.replace(" ", "");
        if (TXID.length() == 0 || UID.length() == 0) {
            return Resp.resp("1", "交易id或UID长度不能为0");
        }

        TreeMap<String, String> content = new TreeMap<>();
        content.put(KEY_UID, UID);
        content.put(KEY_TXID, TXID);
        content.put(KEY_au, au ? "1" : "");
        content.put(KEY_timestamp, String.valueOf(System.currentTimeMillis()));

        // request baas
        Map content_map = httpRequestAndCheckSign(UrlUtil.api_queryTransaction, content, baasPubKey);

        String code = (String) content_map.get(KEY_content_code);
        if (!code.equals("0")) return content_map;

        Map transaction = (Map) content_map.get("transaction");
        String transactionContentString = (String) transaction.get("content");
        Map transactionContentMap = (Map) JSON.parse(transactionContentString);

        String type = (String) transactionContentMap.get("type");
        if (!type.equals("2"))
            return content_map;

        if (outputStream == null && (outputFilePath == null || outputFilePath.replace(" ", "").length() == 0))
            return content_map;

        Map ext_map = (Map) content_map.get("extensions");
        String ext = (String) ext_map.get(TXID);
        if (outputStream != null) {
            HttpDownloadUtil.httpDownloadFile(ext, outputStream);
        }

        if (outputFilePath != null && outputFilePath.replace(" ", "").length() > 0) {
            HttpDownloadUtil.httpDownloadFileToPath(ext, outputFilePath);
        }

        return content_map;
    }

    private InjectObject injectContract(String dataJsonString, String dataListJsonString, String methodName, String UID) throws Exception {

        if (dataJsonString == null || dataJsonString.length() == 0)
            return Resp.respInjectObject("1", "dataJsonString不能为空", "", null);
        if (methodName.equals(ProfitContractMethod.addCodeAndIps.getName())) {
            if (dataListJsonString == null || dataListJsonString.length() == 0)
                return Resp.respInjectObject("1", "dataListJsonString不能为空", "", null);
        }

        if (UID == null || UID.length() == 0)
            return Resp.respInjectObject("1", "UID不能为空", "", null);

        String reqHash = null;

        TreeMap<String, String> content = new TreeMap<>();
        String resourceString = dataJsonString;
        if (methodName.equals(ProfitContractMethod.addCodeAndIps.getName())){
            resourceString+=dataListJsonString;
            content.put("tfae_ips", dataListJsonString);
            content.put("tfae_code", dataJsonString);
        }else if (methodName.equals(ProfitContractMethod.addRegister.getName())){
            content.put("tfae_reg", dataJsonString);
        }else if (methodName.equals(ProfitContractMethod.checkInterstDetail.getName())|| methodName.equals(ProfitContractMethod.addInterstDetail.getName())){
            content.put("tfae_interst_detail", dataJsonString);
        }else{
            return Resp.respInjectObject("1", "未配置的函数名称", "", null);
        }
        reqHash = faceDigest.digestToBase64(resourceString);

        content.put("method", methodName);
        content.put("UID", UID);
        content.put("type", "7");
        content.put("hash", reqHash);
        content.put("timestamp", String.valueOf(System.currentTimeMillis()));
        String content_string = JsonUtil.toASCIIJsonString(content);

        return inject_base(content_string, content, "");
    }

    private InjectObject inject(String ext, String UID, String type) throws Exception {
        if (type == null) {
            return Resp.respInjectObject("1", "type不能为空", "", null);
        }
        List<String> types = new ArrayList<>();
        types.add("1");
        types.add("2");
        types.add("4");
        types.add("5");
        types.add("6");
        if (!types.contains(type))
            return Resp.respInjectObject("1", "type参数错误", "", null);

        if (ext == null || ext.length() == 0)
            return Resp.respInjectObject("1", "上链内容不能为空", "", null);

        if (UID == null || UID.length() == 0)
            return Resp.respInjectObject("1", "UID不能为空", "", null);

        String reqHash = null;

        // 非文件上链
        if (!type.equals("2")) {
            reqHash = faceDigest.digestToBase64(ext);
        } else {
            // upload file
            Map ret_map = null;
            try {
                ret_map = HttpUploadUtil.uploadFile(ext,faceDigest);
            } catch (IOException e) {
                e.printStackTrace();
            }
            String code = (String) ret_map.get("code");
            if (!code.equals("0")) {
                return Resp.respInjectObject(code, (String) ret_map.get("reason"), "", null);
            }
            try {
                reqHash = faceDigest.digestFileToBase64(ext);
            } catch (IOException e) {
                System.out.println("对文件做hash出现异常" + e);
                e.printStackTrace();
            }
            ext = (String) ret_map.get("path");
        }

        TreeMap<String, String> content = new TreeMap<>();
        content.put("UID", UID);
        content.put("type", type);
        content.put("hash", reqHash);
        content.put("timestamp", String.valueOf(System.currentTimeMillis()));
        String content_string = JsonUtil.toASCIIJsonString(content);

        return inject_base(content_string, content, ext);
    }

    private InjectObject inject_base(String content_string, TreeMap<String, String> content, String ext) throws Exception{
        TreeMap<String, String> request_treeMap = new TreeMap<>();
        request_treeMap.put(KEY_version, VALUE_version);
        request_treeMap.put(KEY_content, content_string);
        request_treeMap.put(KEY_sign, signer.signToBase64(content));
        request_treeMap.put("extension", ext);

        String resp = httpRequest(UrlUtil.api_inject, request_treeMap);

        Map resp_map = (Map) JSONObject.parse(resp);
        String sign = (String) resp_map.get("sign");
        Map content_map = (Map) resp_map.get("content");
        boolean res = signer.verifyBaasSign(content_map, sign, baasPubKey);

        if (!res) {
            return Resp.respInjectObject("1", "客户端验签失败", "", null);
        }

        String code = (String) content_map.get("code");
        if (!code.equals("0")) {
            return Resp.respInjectObject(code, (String) content_map.get("reason"), "", null);
        }

        String rs = (String) content_map.get(KEY_content_reason);
        String txid = (String) content_map.get("TXID");
        String ts = (String) content_map.get(KEY_timestamp);
        return Resp.respInjectObject(code, rs, txid, ts);
    }

    private Map httpRequestAndCheckSign(String url, TreeMap content, String baasPubKey) throws Exception {

        String request_string = signer.createSignedDataString(content);

        String resp = httpRequest(url, request_string);

        if (resp.length() <= 0)
            return Resp.resp("1", "返回结果为空");

        Map resp_map = (Map) JSONObject.parse(resp);
        Map respContent_map = (Map) resp_map.get(KEY_content);
        String sign = (String) resp_map.get("sign");

        if (!signer.verifyBaasSign(respContent_map, sign, baasPubKey)) {
            return Resp.resp("1", "验证baas签名失败");
        }

        return respContent_map;
    }

    private String httpRequest(String url, TreeMap request) throws IOException {
        return httpRequest(url, JsonUtil.toASCIIJsonString(request));
    }

    private String httpRequest(String url, String request_string) throws IOException {
        String resp = null;
        Map<String, String> headerMap = new HashMap<>();
        headerMap.put("Content-Type", "application/json;charset=utf-8");
        headerMap.put("accept", "*/*");

        return HTTPUtil.httpPost(url, headerMap, request_string);
    }

}
