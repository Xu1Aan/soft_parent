package com.soft.common.utils;

import com.google.gson.Gson;
import com.qiniu.common.Zone;
import com.qiniu.http.Response;
import com.qiniu.storage.Configuration;
import com.qiniu.storage.UploadManager;
import com.qiniu.storage.model.DefaultPutRet;
import com.qiniu.util.Auth;

import java.util.Date;

public class QiniuUploadUtil {

    private static final String accessKey = "RttDKxeS-f-Vosd9-o2g66-dRHpxhK2XTRBtxzTy";
    private static final String secretKey = "Q_wOly_gKftGoivfG7l-MFwjTFX6duS2QHv3e3vt";
    private static final String bucket = "pzhuweb";
    private static final String domain = "http://qpufgr1xb.hn-bkt.clouddn.com/";
    private UploadManager manager;

    public QiniuUploadUtil() {
        //初始化基本配置
        Configuration cfg = new Configuration(Zone.zone2());
        //创建上传管理器
        manager = new UploadManager(cfg);
    }

	//文件名 = key
	//文件的byte数组
    public String upload(String imgName , byte [] bytes) {
        Auth auth = Auth.create(accessKey, secretKey);
        //构造覆盖上传token
        String upToken = auth.uploadToken(bucket,imgName);
        try {
            Response response = manager.put(bytes, imgName, upToken);
            DefaultPutRet putRet = new Gson().fromJson(response.bodyString(), DefaultPutRet.class);
            //返回请求地址
            return domain+putRet.key+"?t="+new Date().getTime();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return null;
    }
}
