package com.xiaobngkj.jiyun.Controller;

import com.qiniu.common.QiniuException;
import com.qiniu.common.Zone;
import com.qiniu.http.Response;
import com.qiniu.storage.BucketManager;
import com.qiniu.storage.Configuration;
import com.qiniu.util.Auth;
import com.qiniu.util.Json;
import com.qiniu.util.StringMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Map;

public class QiniuFileController {
    private StringMap stringMap;

    @GetMapping("/qiniu/getFileList")
    public String getBucketList(@RequestParam Map<String, Object> params) {
        stringMap = new StringMap();
        //设置需要操作的账号的AK和SK
        String ACCESS_KEY = (String)params.get("AK");
        String SECRET_KEY = (String)params.get("SK");
        Auth auth = Auth.create(ACCESS_KEY, SECRET_KEY);

        //指定区域，可以用 Zone.autoZone() 自动获取
        Zone z = Zone.zone0();
        Configuration c = new Configuration(z);

        //实例化一个BucketManager对象
        BucketManager bucketManager = new BucketManager(auth, c);

        try {
            Response response = bucketManager.listV1((String)params.get("bucket"), (String)params.get("prefix"), (String)params.get("marker"), (int)params.get("limit"), (String)params.get("delimiter"));
            stringMap.put("data", response.jsonToArray());
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
}
