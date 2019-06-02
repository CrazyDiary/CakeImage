package com.example.cakeImage.arithmetic;


import java.awt.image.BufferedImage;
import java.nio.BufferOverflowException;
import java.util.ArrayList;
import java.util.List;
/**
 * @ Author     ：CrazyCake.
 * @ Date       ：Created in 19:27 2019/5/1
 * @ Description：sift算法
 * @ Modified By：
 * @Version: 1.0$
 */
public class SimilarImageSearch {
    static String  fileName= PictureProcessin.path+"images/";

    /**
     * @ Author     ：CrazyCake
     * @ Date       ：Created in 20:04 2019/5/11
     * @ Description：
     * @ Return     ：
     */
//    使用平均值哈希算法生成指纹
    public static List<String> produceAllImages(int count){
        List<String>hashCodes=new ArrayList<>();
        String hashCode=null;
        for (int i = 0; i < count; i++)
        {
            hashCode = produceFingerPrint(fileName  + (i + 1) + ".jpg");
            hashCodes.add(hashCode);
        }
        return hashCodes;
    }

//    计算汉明距离
    public static int hammingDistance(String sourceHashCode,String hashCode){

        int difference=0;
        int len=sourceHashCode.length();
        for (int i=0;i<len;i++){
            if(sourceHashCode!=null&&hashCode!=null&&sourceHashCode.charAt(i)!=hashCode.charAt(i)){
                difference++;
            }
        }
        return difference;
    }
//    生成图片指纹
    public static String produceFingerPrint(String filename){
        System.out.println("filename is "+filename);
        BufferedImage source=null;
        try {
             source = PictureProcessin.readPNGImage(filename);//读文件
        }catch (Exception e){
            e.printStackTrace();
        }
        int width=8;
        int height=8;
//        第一步，缩小尺寸
        if (source==null)
            return null;
        BufferedImage thumb=PictureProcessin.thumb(source,width,height,false);
//        第二步，简化色彩
//        将缩小后的图片，转为64级灰度，也就是说，所有像素点总共只有64种颜色.
        int []pixels=new int[width*height];
        for (int i=0;i<width;i++){
            for (int j=0;j<height;j++){
                pixels[i*height+j]=PictureProcessin.rgbToGray(thumb.getRGB(i,j));
            }
        }
//        第三步，计算平均值
        int avgPixel =PictureProcessin.average(pixels);
//        第四步，比较像素的灰度
//        将每个像素的灰度，与平均值进行比较，大于或等于平均值，记为1：小于平均值，记为0.
      int[]comps=new int[width*height];
      for (int i=0;i<comps.length;i++){
          if(pixels[i]>=avgPixel){
              comps[i]=1;
          }else{
              comps[i]=0;
          }
      }
//      第五步，计算哈希值
//       将上一步的比较结果，组合在一起，就构成了一个64位的整数，这就是这张图[片的指纹
     StringBuffer hashCode=new StringBuffer();
      for (int i=0;i<comps.length;i+=4){
          int result=comps[i]*(int)Math.pow(2,3)+comps[i+1]*(int)Math.pow(2,2)+comps[i + 2] * (int) Math.pow(2, 1) + comps[i + 3];
          hashCode.append(binaryToHex(result));
      }
//       得到指纹以后，就可以对比不同的图片，看看64位中有多少位是不一样的。
      return hashCode.toString();
    }
//    二进制转为十六进制
    public static char binaryToHex(int binary){
        char ch=' ';
        switch (binary){
            case 0:
                ch = '0';
                break;
            case 1:
                ch = '1';
                break;
            case 2:
                ch = '2';
                break;
            case 3:
                ch = '3';
                break;
            case 4:
                ch = '4';
                break;
            case 5:
                ch = '5';
                break;
            case 6:
                ch = '6';
                break;
            case 7:
                ch = '7';
                break;
            case 8:
                ch = '8';
                break;
            case 9:
                ch = '9';
                break;
            case 10:
                ch = 'a';
                break;
            case 11:
                ch = 'b';
                break;
            case 12:
                ch = 'c';
                break;
            case 13:
                ch = 'd';
                break;
            case 14:
                ch = 'e';
                break;
            case 15:
                ch = 'f';
                break;
            default:
                ch = ' ';
        }
        return ch;
    }
}
