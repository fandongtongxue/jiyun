package com.xiaobngkj.jiyun.Controller;

import com.qiniu.common.QiniuException;
import com.qiniu.common.Zone;
import com.qiniu.storage.BucketManager;
import com.qiniu.storage.Configuration;
import com.qiniu.util.Auth;
import com.qiniu.util.Json;
import com.qiniu.util.StringMap;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
public class QiniuBucketController {
    private StringMap stringMap;

    @PostMapping("/qiniu/getBucketList")
    public String getBucketList(@RequestParam Map<String, String> params) {
        stringMap = new StringMap();
        //设置需要操作的账号的AK和SK
        String ACCESS_KEY = params.get("AK");
        String SECRET_KEY = params.get("SK");
        Auth auth = Auth.create(ACCESS_KEY, SECRET_KEY);

        //指定区域，可以用 Zone.autoZone() 自动获取
        Zone z = Zone.zone0();
        Configuration c = new Configuration(z);

        //实例化一个BucketManager对象
        BucketManager bucketManager = new BucketManager(auth, c);

        try {
            stringMap.put("data", bucketManager.buckets());
            stringMap.put("code", 0);
            stringMap.put("message", "获取BucketList成功");
        } catch (QiniuException e) {
//            throw new RuntimeException(e);
            stringMap.put("data", null);
            stringMap.put("code", -1);
            stringMap.put("message", e.error());
        }

        return Json.encode(stringMap);
    }


    @PostMapping("/qiniu/getBucketDomainList")
    public String getBucketDomainList(@RequestParam Map<String, String> params) {
        stringMap = new StringMap();
        //设置需要操作的账号的AK和SK
        String ACCESS_KEY = params.get("AK");
        String SECRET_KEY = params.get("SK");
        Auth auth = Auth.create(ACCESS_KEY, SECRET_KEY);

        //指定区域，可以用 Zone.autoZone() 自动获取
        Zone z = Zone.zone0();
        Configuration c = new Configuration(z);

        //实例化一个BucketManager对象
        BucketManager bucketManager = new BucketManager(auth, c);

        try {
            stringMap.put("data", bucketManager.domainList(params.get("bucket")));
            stringMap.put("code", 0);
            stringMap.put("message", "获取BucketList成功");
        } catch (QiniuException e) {
//            throw new RuntimeException(e);
            stringMap.put("data", null);
            stringMap.put("code", -1);
            stringMap.put("message", e.error());
        }

        return Json.encode(stringMap);
    }

    @PostMapping("/qiniu/deleteBucket")
    public String deleteBucketList(@RequestParam Map<String, String> params) {
        stringMap = new StringMap();
        //设置需要操作的账号的AK和SK
        String ACCESS_KEY = params.get("AK");
        String SECRET_KEY = params.get("SK");
        Auth auth = Auth.create(ACCESS_KEY, SECRET_KEY);

        //指定区域，可以用 Zone.autoZone() 自动获取
        Zone z = Zone.zone0();
        Configuration c = new Configuration(z);

        //实例化一个BucketManager对象
        BucketManager bucketManager = new BucketManager(auth, c);

        try {
            bucketManager.deleteBucket(params.get("bucket"));
            stringMap.put("data", null);
            stringMap.put("code", 0);
            stringMap.put("message", "删除Bucket成功");
        } catch (QiniuException e) {
//            throw new RuntimeException(e);
//            {"error":"drop non empty bucket is not allowed","error_code":"BucketNotEmpty"}
            stringMap.put("data", null);
            stringMap.put("code", -1);
            stringMap.put("message", e.error());
        }
        return Json.encode(stringMap);
    }

    @PostMapping("/qiniu/createBucket")
    public String createBucket(@RequestParam Map<String, String> params) {
        stringMap = new StringMap();
        //设置需要操作的账号的AK和SK
        String ACCESS_KEY = params.get("AK");
        String SECRET_KEY = params.get("SK");
        Auth auth = Auth.create(ACCESS_KEY, SECRET_KEY);

        //指定区域，可以用 Zone.autoZone() 自动获取
        Zone z = Zone.zone0();
        Configuration c = new Configuration(z);

        //实例化一个BucketManager对象
        BucketManager bucketManager = new BucketManager(auth, c);

        try {
            bucketManager.createBucket(params.get("bucket"), params.get("region"));
            stringMap.put("data", null);
            stringMap.put("code", 0);
            stringMap.put("message", "创建Bucket成功");
        } catch (QiniuException e) {
//            throw new RuntimeException(e);
//            {"error":"drop non empty bucket is not allowed","error_code":"BucketNotEmpty"}
            stringMap.put("data", null);
            stringMap.put("code", -1);
            stringMap.put("message", e.error());
        }
        return Json.encode(stringMap);
    }
}
