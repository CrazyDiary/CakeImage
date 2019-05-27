package com.example.cakeImage.arithmetic;

import org.mybatis.spring.boot.autoconfigure.MybatisAutoConfiguration;

import java.util.Arrays;

/**
 * @ Author     ：CrazyCake
 * @ Date       ：Created in 17:29 2019/5/27
 * @ Description：特征点类
 * @ Modified By：
 * @Version: 1.0$
 */
public class MyPoint {
    private int preX;
    private int preY;

    private int x;
    private int y;
//    高斯金字塔组内第s张
    private int s;
//    处于第几组（第几层金字塔）
    private int octave;
//    关键点方向
    private double theta;
//    特征向量---梯度分布统计
    private double[]grads;
//    是否匹配过了
    private boolean isMatched;
    public MyPoint(int x,int y,int s,int octave,double theta,double[]grads,boolean isMatch){
        this.x=x;
        this.y=y;
        this.s=s;
        this.octave=octave;
        this.grads=grads;
        this.theta=theta;
        this.isMatched=isMatch;
    }
//    默认构造函数
    public MyPoint(){

    }

    @Override
    public String toString() {
        return "MyPoint{" +
                "preX=" + preX +
                ", preY=" + preY +
                ", x=" + x +
                ", y=" + y +
                ", s=" + s +
                ", octave=" + octave +
                ", theta=" + theta +
                ", grads=" + Arrays.toString(grads) +
                ", isMatched=" + isMatched +
                '}';
    }

    public void setPreX(int preX) {
        this.preX = preX;
    }

    public void setPreY(int preY) {
        this.preY = preY;
    }

    public void setX(int x) {
        this.x = x;
    }

    public void setY(int y) {
        this.y = y;
    }

    public void setS(int s) {
        this.s = s;
    }

    public void setOctave(int octave) {
        this.octave = octave;
    }

    public void setTheta(double theta) {
        this.theta = theta;
    }

    public void setGrads(double[] grads) {
        this.grads = grads;
    }

    public void setMatched(boolean matched) {
        isMatched = matched;
    }

    public int getPreX() {
        return preX;
    }

    public int getPreY() {
        return preY;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getS() {
        return s;
    }

    public int getOctave() {
        return octave;
    }

    public double getTheta() {
        return theta;
    }

    public double[] getGrads() {
        return grads;
    }

    public boolean isMatched() {
        return isMatched;
    }
}
