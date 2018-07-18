package com.myapp.service.house;

import com.myapp.SearchProjectApplicationTests;
import com.qiniu.common.QiniuException;
import com.qiniu.http.Response;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.File;

/**
 * @author cgh
 */
public class QiNiuServiceTests extends SearchProjectApplicationTests {
    @Autowired
    private IQiNiuService qiNiuService;

    @Test
    public void testUploadFile(){
        String fileName = "D:/allencai/caiguohui-release/search-project/tmp/ai1.jpg";
        File file = new File(fileName);

        Assert.assertTrue(file.exists());

        try {
            Response response = qiNiuService.uploadFile(file);
            Assert.assertTrue(response.isOK());
        } catch (QiniuException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testDelete(){
        String key = "FsDTkESSR7HzyfwA-ZILlYVft4SB";
        try {
            Response response = qiNiuService.delete(key);
            Assert.assertTrue(response.isOK());
        } catch (QiniuException e) {
            e.printStackTrace();
        }
    }
}
