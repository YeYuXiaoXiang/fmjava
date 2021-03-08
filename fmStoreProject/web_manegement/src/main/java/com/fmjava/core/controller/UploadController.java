package com.fmjava.core.controller;

import com.fmjava.core.pojo.entity.Result;
import com.fmjava.utils.FastDFSClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/upload")
public class UploadController {

    @Value("${FILE_SERVER_URL}")
    private String FILE_SERVER_URL;

    @RequestMapping("/uploadFile")
    public Result uploadFile(MultipartFile file) throws Exception{

        try {
            FastDFSClient fastDFSClient = new FastDFSClient("classpath:fastDFS/fdfs_client.conf");
            String path = fastDFSClient.uploadFile(file.getBytes(),file.getOriginalFilename(),file.getSize());

            String url = FILE_SERVER_URL + path;
            System.out.println(url);
            return new Result(true, url);

        }catch (Exception e){
            return new Result(false, "上传失败");
        }
    }

    @RequestMapping("/deleteImg")
    public Result deleteImg(String url) throws Exception{

        try {
            String path = url.substring(FILE_SERVER_URL.length());
            FastDFSClient fastDFSClient = new FastDFSClient("classpath:fastDFS/fdfs_client.conf");
            Integer res = fastDFSClient.delete_file(path);

            if (res == 0){
                return new Result(true, "删除成功");
            }else{
                return new Result(false, "删除失败");
            }


        }catch (Exception e){
            return new Result(false, "删除失败");
        }
    }

    @RequestMapping("/uploadImage")
    public Map uploadImage(MultipartFile upfile) throws Exception {
        try {
            FastDFSClient fastDFS = new FastDFSClient("classpath:fastDFS/fdfs_client.conf");
            //上传文件返回文件保存的路径和文件名
            String path = fastDFS.uploadFile(upfile.getBytes(), upfile.getOriginalFilename(), upfile.getSize());
            //拼接上服务器的地址返回给前端
            String url  = FILE_SERVER_URL + path;
            Map<String ,Object > result = new HashMap<>();
            result.put("state","SUCCESS");
            result.put("url",url);
            result.put("title",upfile.getOriginalFilename());
            result.put("original",upfile.getOriginalFilename());
            return result;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }


}
