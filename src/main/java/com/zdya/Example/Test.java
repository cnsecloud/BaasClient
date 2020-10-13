package com.zdya.Example;

import java.text.SimpleDateFormat;
import java.util.*;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.zdya.Entity.*;
import com.zdya.client.BaasClient;
import com.zdya.enumeration.ProfitContractMethod;
import com.zdya.util.JsonUtil;
import com.zdya.util.em.file.FileUtil;

public class Test {

    private static final String testStr = "这个是上链的字符串this";
    //	private static final String testFilePath = "C:\\Users\\Administrator\\Desktop\\1000.txt";
    private static final String testFilePath = "/Users/apple/Desktop/中盾项目/中盾区块链客户端/testFileInjectFile.txt";

    private static final String appUID = "app.test"; // 应用UID

    public static String stringFormat(String s) {
        return s + "--- 测试工程成长之道, ";
    }


    public static void main(String[] args) throws Exception {
        // 客户端公钥
        // MFwwDQYJKoZIhvcNAQEBBQADSwAwSAJBANFKJJr8RLtxIqf8/B/NSp3ZNq6y6xI/ATC7pmwDKaaleAcvID75UxGh3R00zoIIOOowpA+x+Wo4CxdDvEo6ngECAwEAAQ==
        // 客户端私钥
        // MIIBVQIBADANBgkqhkiG9w0BAQEFAASCAT8wggE7AgEAAkEA0UokmvxEu3Eip/z8H81Kndk2rrLrEj8BMLumbAMppqV4By8gPvlTEaHdHTTOggg46jCkD7H5ajgLF0O8SjqeAQIDAQABAkEAoW+HfOVKPyuth9bkw8Me41NBks//8zsWN/kHDhoCbr+e8Tje7R9LLCPDuValzD09eIfFFpkUISUAmWQkG1UV4QIhAO4lI0N4IFGgLnsdhaGpcegUlsOByA4RBsbma8YIaFufAiEA4PsqSWbNDYoBvsmg1ooO332z0T+xY+cvEeHR23C6ol8CIAcFOUDn7av6WORwPlxBroX09WzWCpu/jL+YU10HKmxpAiBOoVnJJ6W4+qfbf2q4MK48DBnhoyojaXQ5AryuvjpsbwIhAK2Qz14oes+swKgCEB3Ttit/4LVv9hlV4rgbZZNIiNJD

        String algorithm = "SM3WithSM2";
        String baasUrlBase = "http://192.168.3.138:8083/";
        String appPriKey = "MIGTAgEAMBMGByqGSM49AgEGCCqBHM9VAYItBHkwdwIBAQQgLJNyZsvYr3Dm0M/u1Kzbzc+gRK+L2msV/8C/0jrrppugCgYIKoEcz1UBgi2hRANCAAS2C51S/0xV8usBDUyG54wxSrt/OTjOowo3KShx79l6mOO/LokeUsLIqPLEWUoT1OvEPAKE7/jnHxhvfivBL7Z8";
        String baasPubKey = "MFkwEwYHKoZIzj0CAQYIKoEcz1UBgi0DQgAEhjDZPX1KfNV/D0pble7dHYwjdXsRJd2Lu1BIffVg8oUnAUnsX7hfYTFt1uR/kszdZfpK8q62iUAW9N/6OuuuwA==";

        // 1.初始化
        BaasClient client = null;
        try {
            // 客户端工具初始化
            client = new BaasClient(appUID, baasUrlBase, appPriKey, algorithm, baasPubKey);
        } catch (Exception e) {
            e.printStackTrace();
        }
        // 断言 client必须初始化成功才执行后面的操作
        assert client != null;
        BaseObject res = null;


        int iiii = 1;
        do {
            try {
                // 2.文本上链
                long shi = System.currentTimeMillis();
                res = null;
                String str = "test text inject into block chain 测试文本上链 テキストのチェーンをテストします 텍스트 체인 테스트"+iiii;
                res = client.injectText(str);
                System.out.println("******" + iiii++);
                System.out.println("耗费时间(ms):" + (System.currentTimeMillis() - shi));
                System.out.println("文本上链:" + res);
                String txid = ((InjectObject) res).getTxid();
                Thread.sleep(2000);
            } catch (Exception e) {
                System.out.println("文本上链异常:" + e);
                e.printStackTrace();
            }
        } while (true);

        // 3.文件上链
		/*long shi1 = System.currentTimeMillis();
		res = null;
		try {
			res = client.injectFile(testFilePath);
		} catch (Exception e) {
			System.out.println("数据上链异常:" + e);
			e.printStackTrace();
		}
		System.out.println("时间"+(System.currentTimeMillis()-shi1));
		System.out.println("文件上链:" + res);*/

		/*int i = 0;
		while (true){
			i++;
			// 资产用户信息上链
			Map<String, String> map = new HashMap<>();
			// 不可用字段: TXID block_number
			map.put("id", "bbbb");
			map.put("userid", "bbbb"+(9000+i));
			map.put("phone", "19839929355");
			map.put("address", "真2ddd");
			map.put("sex", "0");
			map.put("asset", String.valueOf(9000+i));
			map.put("assetid", String.valueOf(9000+i));
			map.put("_id", "tx7ed62791b7d044738c4a8a283cdd1135");
			String assetUser = JsonUtil.toASCIIJsonString(map);

			AUObject obj = null;
			try {
				// type: 0-->新增 1-->删除 2-->修改
				obj = client.injectAssetUser(assetUser, 0);
			} catch (Exception e) {
				e.printStackTrace();
			}
			System.out.println("res:" + obj);
			// 客户应记录新增返回的_id与txid, 后续进行记录修改与删除过程中应传入_id
			Thread.sleep(900);
		}*/


        // 添加产品与还款计划
        /*String productCode = "1031";
        Map<String, Object> dataMap = new HashMap<>();
        dataMap.put("ProductCode", productCode);
        dataMap.put("ProductName", "223产品");
        dataMap.put("ProductFullName", "223产品-产品全称");
        dataMap.put("ProductSummary", "0");
        dataMap.put("ProductCategory", "大类");
        dataMap.put("ProductStatus", 1);
        dataMap.put("ProductCompany", "发行主体");
        dataMap.put("IssueAmount", 1200000);
        dataMap.put("ProductPeriod", 36);// 产品周期
        dataMap.put("ProductPeriodUnit", 2);
        dataMap.put("RelationProject", 123);
        String dataMapJsonString = JsonUtil.toASCIIJsonString(dataMap);

        List<Map<String, Object>> dataList = new ArrayList<>();
        for (int i = 0; i < 2; i++) {
            Map<String, Object> plan = new HashMap<>();
            plan.put("ID", 1030 + i);
            plan.put("ProductCode", productCode);
            plan.put("InterestPayTerm", i + 1);// 产品批次
            plan.put("CurrentInterestBegin", 20190101);// int(11)
            plan.put("CurrentInterestEnd", 20191231);// int(11)
            plan.put("TensOfThousandsOfReturns", 1.2f);// numeric(16,8)
            plan.put("CurrentInterestRate", 0.023980f);// numeric(32,8)
            dataList.add(plan);
        }
        String dataListJsonString = JSON.toJSONString(dataList);

        InjectObject profitProductContract = client.profitProductContract(ProfitContractMethod.addCodeAndIps.getName(), dataMapJsonString, dataListJsonString);
        System.out.println("ppContract:" + profitProductContract.toString());*/


        // 添加持有记录
        /*Map<String, Object> dataMap = new HashMap<>();
        String productCode = "1030";
        dataMap.put("ProductCode", productCode);
        dataMap.put("SerialNumber", 2);
        String productName = "223产品名称";
        dataMap.put("CID", 1299319032);
        dataMap.put("CustomerName", "李四");
        dataMap.put("ProductName", productName);
        dataMap.put("ProductRegisterAmount", 12000.52);// numeric(16,2)
        String dataMapJsonString = JsonUtil.toASCIIJsonString(dataMap);

        InjectObject profitProductContract = client.profitProductContract(ProfitContractMethod.addRegister.getName(), dataMapJsonString, null);
        System.out.println("ppContract:"+profitProductContract.toString());*/


        // 核对试算表(兑息)
        /*Map<String, Object> dataMap = new HashMap<>();
        String productCode = "1030";
        dataMap.put("ProductCode", productCode);
        dataMap.put("InterestPayTerm", 1);// 兑付期次	int(11)
        dataMap.put("ID", 1231321322);// bigint(20)
        dataMap.put("CID", 1299319032);
        dataMap.put("CustomerName", "李四");
        dataMap.put("InterestPayAmount", 13000.32);// 兑付金额:numeric(16,2)
        dataMap.put("InterestAmount", 287.77);// 含息金额:numeric(16,2)
        dataMap.put("InterestPrincipalAmount", 12000.52);// 含息金额:numeric(16,2)
        dataMap.put("CashCategory", 0);// 兑付类别	int(11)	0兑息，1兑付
        String dataMapJsonString = JsonUtil.toASCIIJsonString(dataMap);

        InjectObject profitProductContract = client.profitProductContract(ProfitContractMethod.checkInterstDetail.getName(), dataMapJsonString, null);
        System.out.println("ppContract:"+profitProductContract.toString());*/


        // 添加还款流水(兑息)
        /*Map<String, Object> dataMap = new HashMap<>();
        String productCode = "1030";
        dataMap.put("ProductCode", productCode);
        dataMap.put("InterestPayTerm", 1);// 兑付期次	int(11)
        dataMap.put("ID", 2);// bigint(20)
        dataMap.put("CID", 1299319032);
        dataMap.put("CustomerName", "李四");
        dataMap.put("InterestPayAmount", 13000.32);// 兑付金额:numeric(16,2)
        dataMap.put("InterestAmount", 287.77);// 含息金额:numeric(16,2)
        dataMap.put("InterestPrincipalAmount", 12000.52);// 含息金额:numeric(16,2)
        dataMap.put("CashCategory", 0);// 兑付类别	int(11)	0兑息，1兑付
        String dataMapJsonString = JsonUtil.toASCIIJsonString(dataMap);

        InjectObject profitProductContract = client.profitProductContract(ProfitContractMethod.addInterstDetail.getName(), dataMapJsonString, null);
        System.out.println("ppContract:"+profitProductContract.toString());*/


        // 核对试算表(兑本)
        /*Map<String, Object> dataMap = new HashMap<>();
        String productCode = "1030";
        dataMap.put("ProductCode", productCode);
        dataMap.put("InterestPayTerm", 1);// 兑付期次	int(11)
        dataMap.put("ID", 1231321322);// bigint(20)
        dataMap.put("CID", 1299319032);
        dataMap.put("CustomerName", "李四");
        dataMap.put("InterestPayAmount", 12288.29);// 兑付金额:numeric(16,2)
        dataMap.put("InterestAmount", 287.77);// 含息金额:numeric(16,2)
        dataMap.put("InterestPrincipalAmount", 12000.52);// 含息金额:numeric(16,2)
        dataMap.put("CashCategory", 1);// 兑付类别	int(11)	0兑息，1兑付
        String dataMapJsonString = JsonUtil.toASCIIJsonString(dataMap);

        InjectObject profitProductContract = client.profitProductContract(ProfitContractMethod.checkInterstDetail.getName(), dataMapJsonString, null);
        System.out.println("ppContract:"+profitProductContract.toString());*/


        // 添加还款流水(兑本)
        /*Map<String, Object> dataMap = new HashMap<>();
        String productCode = "1030";
        dataMap.put("ProductCode", productCode);
        dataMap.put("InterestPayTerm", 1);// 兑付期次	int(11)
        dataMap.put("ID", 1231321322);// bigint(20)
        dataMap.put("CID", 1299319032);
        dataMap.put("CustomerName", "李四");
        dataMap.put("InterestPayAmount", 12288.29);// 兑付金额:numeric(16,2)
        dataMap.put("InterestAmount", 287.77);// 含息金额:numeric(16,2)
        dataMap.put("InterestPrincipalAmount", 12000.52);// 含息金额:numeric(16,2)
        dataMap.put("CashCategory", 1);// 兑付类别	int(11)	0兑息，1兑付
        String dataMapJsonString = JsonUtil.toASCIIJsonString(dataMap);

        InjectObject profitProductContract = client.profitProductContract(ProfitContractMethod.addInterstDetail.getName(), dataMapJsonString, null);
        System.out.println("ppContract:"+profitProductContract.toString());*/


        // 4.下载文本
        // 文本交易id: tx65102a8e0ef24edd92ca06f5d9dad1a2
		/*DownloadObject dow = null;
		try {
			dow = client.downloadText("txce6e9a70c76b4d649feeb753c1661361");
		} catch (Exception e) {
			e.printStackTrace();
		}
		System.out.println("下载文本:" + dow);*/

        // 5.下载文件
		/*DownloadObject downloadFileObject = null;
		res = null;
		String outPath = "/Users/apple/Downloads/1000-dld.txt";
		// /Users/apple/Desktop/pom000.xml
        // String outPath = "/Users/apple/Desktop/pom000.xml";// mac环境下尽量不要放桌面
		try {
			downloadFileObject = client.downloadFile("txee42fe37df8c4302b07ca5c3a357a3fe", null);
		} catch (Exception e) {
			e.printStackTrace();
		}
		System.out.println("下载文件:" + downloadFileObject);*/

        // 6.查询区块
		/*res = null;
		try {
			res = client.queryBlock("207291");
		} catch (Exception e) {
			System.out.println("查询区块异常:" + e);
			e.printStackTrace();
		}
		System.out.println("查询区块:" + res.toString());*/

        // 7.查询交易
		/*res = null;
		try {
			res = client.queryTransaction("tx7c94c1b6613f4891bfa69a8119c5a737" );
		} catch (Exception e) {
			System.out.println("查询交易异常:" + e);
			e.printStackTrace();
		}
		QueryObject object = (QueryObject) res;
		System.out.println("查询交易:" + object.getTransaction());*/

        // 查询资产用户信息
        /*try {
            res = client.queryAssetUser("tx7c94c1b6613f4891bfa69a8119c5a737");
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println("查询au结果:"+((QueryObject) res).getAssetUser());*/


        // 8.验证文本交易
		/*String verifyTextString = "这个是上链的字符串this4555abca-880a-4d15-adfb-fc968a7ad82";
		res = null;
		try {
			res = client.veriText("txab82cfb0846a4f28b194cc0ad465b519", verifyTextString);
		} catch (Exception e) {
			System.out.println("验证交易过程异常:" + e);
			e.printStackTrace();
		}
		System.out.println("验证文本交易:" + res.toString());*/

        // 9.验证文件交易
		/*res = null;
		try {
			res = client.veriFile("txee42fe37df8c4302b07ca5c3a357a3fe", testFilePath);
		} catch (Exception e) {
			System.out.println("验证交易过程异常:" + e);
			e.printStackTrace();
		}
		System.out.println("验证文件交易:" + res.toString());*/

        // 循环调用上链
        // test.trans(client);
    }

    private static void trans(BaasClient client) {
        final BaasClient fclient = client;
        Timer timer = new Timer();

        timer.scheduleAtFixedRate(new TimerTask() {
            public void run() {

                // 数据上链示例1 文本上链
                BaseObject res = null;
                try {
                    Date currentTime = new Date();
                    SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                    String dateString = formatter.format(currentTime);

                    res = fclient.injectText(testStr + ",时间:" + dateString);
                } catch (Exception e) {
                    System.out.println("数据上链异常:" + e);
                    e.printStackTrace();
                }
                System.out.println("res:" + res.toString());

                // 资产用户信息上链
                Map<String, String> map = new HashMap<>();
                // 不可用字段: TXID block_number
                map.put("id", "bbbb");
                map.put("userid", "bbbb" + (9000));
                map.put("phone", "19839929355");
                map.put("address", "真222222");
                map.put("sex", "0");
                map.put("asset", String.valueOf(9000));
                map.put("assetid", String.valueOf(9000));
                map.put("_id", "");
                String assetUser = JsonUtil.toASCIIJsonString(map);

                AUObject o = null;
                try {
                    // type: 0-->新增 1-->删除 2-->修改
                    o = client.injectAssetUser(assetUser, 0);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                System.out.println("res11:" + o);

            }
        }, 200, 1000); // 200ms后执行第一次,之后重复每隔300ms执行一次
    }

}
