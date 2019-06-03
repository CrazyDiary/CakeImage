package com.example.cakeImage.controller;
import com.example.cakeImage.arithmetic.*;
import com.example.cakeImage.entity.Ahash;
import com.example.cakeImage.entity.Dhash;
import com.example.cakeImage.entity.Phash;
import com.example.cakeImage.service.CommonService;
import com.example.cakeImage.tools.Utility;
import org.opencv.core.Core;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import java.io.Console;
import java.io.File;
import java.util.ArrayList;

import java.util.List;
import java.util.Map;


@Controller
public class ArithmeticController {




    @Autowired
    public CommonService commonservice;

    @ResponseBody
    @RequestMapping("/perception")
    public ArrayList<String> perception(@RequestParam(value = "my_text",required = false)String  my_text, @RequestParam( value = "file")MultipartFile file, String filePath,  HttpSession session){

        String sourceImagePath="";
        String method=(String) session.getAttribute("method");
        System.out.println("method is "+method);

        ArrayList<String >list=new ArrayList<>();
        session.setAttribute("message","This is your message");
//            向前端发送数据
        if ((my_text!=null)&&(Utility.verifyUrl(my_text))) {
            String[] str = my_text.split(",");
            my_text = str[0];
            System.out.println("text is " + my_text);
            try {
                sourceImagePath= Utility.download(my_text);
            } catch (Exception e) {
                System.out.println(e);
                System.out.println("message:" + e.getMessage());
                e.printStackTrace(System.out);
            }
        }else if(file!=null){
            sourceImagePath=  Utility.tool(file,filePath);
        }else{
            System.out.println("没有输入图片");
        }
//            获取原图像地址
        System.out.println("sourceImagePath is "+sourceImagePath);
//              存放相似集

    if (method==null){
        list.add("还没选择算法");
        return list;
    }
//            使用平均值哈希算法
        if(method.contains("ahash")){

//             获取目标图片的指纹
            String sourceCode = SimilarImageSearch.produceFingerPrint(sourceImagePath);
            System.out.println("ahash sourceCode is "+sourceCode);
            ArrayList<Ahash>map=null;
            map=commonservice.findAhash();
            System.out.println("map is "+map.toString());


            if(map!=null){
                for (int i=0;i<map.size();i++){
                    Ahash ahash=new Ahash();
                    ahash=map.get(i);
//                    计算汉明距离
                    if (map.get(i).finger==null)
                    {
                        continue;
                    }
                    int differece= SimilarImageSearch.hammingDistance(map.get(i).finger,sourceCode);
                    if (differece<7){
                        list.add(ahash.address);
                    }

                }
                String test="123";
            }
            for (int i=0;i<list.size();i++){
                System.out.println("相似图像为: "+list.get(i));
            }
//            增强的哈希算法
        }else if(method.contains("phash")){
            List<Phash> similarList=new ArrayList<>();
            System.out.println("phash 算法 ：");

            String sourceCode=PhashArith.PHashGen(sourceImagePath);
            System.out.println("phash sourceCode is "+sourceCode);

            ArrayList<Phash>map=null;
            map=commonservice.findPhash();
            System.out.println("map is "+map.toString());
            if(map!=null){
                for (int i=0;i<map.size();i++){
                    Phash phash=new Phash();
                    phash=map.get(i);
//                    计算汉明距离
                    int differece= SimilarImageSearch.hammingDistance(map.get(i).finger,sourceCode);
                    if (differece<=7){
                        list.add(phash.address);
                    }
                }
                return list;
            }

            for (int i=0;i<list.size();i++){
                System.out.println("相似图像为: "+list.get(i));
            }

//            差异值哈希算法
        }else if(method.contains("dhash")){
            List<Dhash> similarList=new ArrayList<>();
        System.out.println("dhash算法");

            String sourceCode= DHashArith.DHashGen(sourceImagePath);
            System.out.println("dhash sourceCode is "+sourceCode);

            ArrayList<Dhash>map=null;
            map=commonservice.findDhash();
            System.out.println("map is "+map.toString());
            if(map!=null){
                for (int i=0;i<map.size();i++){
                    Dhash dhash=new Dhash();
                    dhash=map.get(i);
//                    计算汉明距离
                    int differece= SimilarImageSearch.hammingDistance(map.get(i).finger,sourceCode);
                    if (differece<=7){
                        list.add(dhash.address);
                    }
                }
            }

            for (int i=0;i<list.size();i++){
                System.out.println("相似图像为: "+list.get(i));
            }
            if (similarList.size()==0)
                System.out.println("没有相似图像");
        }else if(method.contains("sift")){
            Sift sift=new Sift();
            try {
//                保存特征值到本地
//                Sift.saveCharacter(66);
                for (int i=1;i<66;i++){
                    List<MyPoint> list1=  Sift.readCharacterVectors(new File("G:\\java\\webprojects\\CakeImage\\siftdata\\"+i+".txt"));
                    if (list1!=null){
//                        获取原图的特征向量
                      List<MyPoint>list2=  Sift.getCharacterVectors(sourceImagePath);
                     boolean a= Sift.SiftGenerat(list1,list2);
                     if (a){
                         list.add("images/"+(i)+".jpg");
                     }
                    }
                }

            }catch (Exception e){
//                e.printStackTrace();
            }
//            List<MyPoint> list1=  Sift.readCharacterVectors(new File("G:\\java\\webprojects\\CakeImage\\siftdata\\"+"1.txt"));
//            System.out.println("list1 矩阵");
//            if (list1==null)
//                return null;
//            for (int k=0;k<list1.size();k++){
//                MyPoint myPoint=list1.get(k);
//                for (int x=0;x<myPoint.getGrads().length;x++){
//                    System.out.print(" "+myPoint.getGrads()[x]);
//                }
//                System.out.println();
//            }
//            list= sift.isSimilar(5,sourceImagePath);
            System.out.println("sift 方法的相似图片:"+list.toString());
            return list;
        }else{
            list.add("还没选择算法");
            return list;
        }

        return list;
    }
}
