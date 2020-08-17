package com.yiyunkj.yidongban.utils;


import org.apache.commons.lang3.StringUtils;

public class DesensitizeUtil {
    /**
     * 只显示第一个汉字，其他隐藏为2个星号<例子：李**>
     *
     * @param fullName
     * @param  index 1 为第index位
     * @return
     */
    public static String left(String fullName,int index) {
        if (StringUtils.isBlank(fullName)) {
            return "";
        }
        if(fullName.length()-1 < index){
            return fullName;
        }
        String name = StringUtils.left(fullName, index);
        return StringUtils.rightPad(name, StringUtils.length(fullName), "*");
    }

    /**
     * [身份证号] 110****58，前面保留3位明文，后面保留2位明文
     *
     * @param name
     * @param index 3
     * @param end 2
     * @return
     */
    public static String around(String name,int index,int end) {
        if (StringUtils.isBlank(name)) {
            return "";
        }
        return StringUtils.left(name, index).concat(StringUtils.removeStart(StringUtils.leftPad(StringUtils.right(name, end), StringUtils.length(name), "*"), "*"));
    }

    /**
     * [固定电话] 后四位，其他隐藏<例子：****1234>
     *
     * @param num
     * @return
     */
    public static String right(String num,int end) {
        if (StringUtils.isBlank(num)) {
            return "";
        }
        return StringUtils.leftPad(StringUtils.right(num, end), StringUtils.length(num), "*");
    }

    /**
     * [姓名] <例子：*哈哈>
     */
    public static String name(String num) {
        if (StringUtils.isBlank(num)) {
            return "";
        }
        String lastStr = num.substring(num.length()-1);
        StringBuilder fastStr =new StringBuilder();
        for (int i = 1; i < num.length(); i++) {
            fastStr.append("*");
        }
        return fastStr.toString()+lastStr;
    }

    /**
     * 强隐藏规则，身份证
     */
    public static String idnum(String num) {
        if (StringUtils.isBlank(num)) {
            return "";
        }
        if(num.length()<15){
            return num;
        }
        String first = num.substring(0,1);
        String end = num.substring(num.length()-1);
        return first + "****************"+end;
    }

    /**
     * 隐藏其他证件,显示前1/3和后1/3段字节，其他用*号代替
     */
    public static String license(String num){
        if(StringUtils.isBlank(num) || num.length()<4){
            return num;
        }
        int index = num.length()/3;
        String first = num.substring(0,index);
        String second = num.substring(index,2*index);
        StringBuilder str = new StringBuilder();
        for(int i=0;i<second.length();i++){
            str.append("*");
        }
        String three = num.substring(2*index);
        return first + str.toString() + three;
    }


    /**
     * 隐藏其他证件,显示前x和后y段字节，其他用*号代替
     */
    public static String license(String num,Integer x,Integer y){
        if(StringUtils.isBlank(num) || num.length()<(x+y)){
            return num;
        }
        int endy =num.length()-y;
        String first = num.substring(0,x);
        String second = num.substring(x,endy);
        StringBuilder str = new StringBuilder();
        for(int i=0;i<second.length();i++){
            str.append("*");
        }
        String three = num.substring(endy);
        return first + str.toString() + three;
    }

    /**
     * 隐藏手机号
     */
    public static String moblie(String num){
        if(StringUtils.isBlank(num) || num.length()<8){
            return num;
        }
        String first = num.substring(0,3);
        String second = num.substring(3,num.length() - 4);
        StringBuilder str = new StringBuilder();
        for(int i=0;i<second.length();i++){
            str.append("*");
        }
        String three = num.substring(num.length() - 4);
        return first + str.toString() + three;
    }

    /**
     * 隐藏固定电话
     */
    public static String phone(String num){
        if(StringUtils.isBlank(num) || num.length()<8){
            return num;
        }
        String first = num.substring(0,4);
        String second = num.substring(4,num.length() - 4);
        StringBuilder str = new StringBuilder();
        for(int i=0;i<second.length();i++){
            str.append("*");
        }
        String three = num.substring(num.length() - 4);
        return first + str.toString() + three;
    }

    /**
     *隐藏邮箱
     */
    public static String mail(String num){
        if(num.indexOf("@") == -1){
            return num;
        }
        int index = num.indexOf("@");
        String head = num.substring(0,index);
        String end = num.substring(index);
        if(head.length() < 3){//小于三位直接返回
            return num;
        }

        String first = head.substring(0,3);
        String second = head.substring(3);
        StringBuilder str = new StringBuilder();
        for(int i=0;i<second.length();i++){
            str.append("*");
        }
        return first + str.toString() + end;
    }

    /**
     * 隐藏钱
     */
    public static String money(String num){
        if(StringUtils.isBlank(num) || num.length()<8){
            return num;
        }
        String first = num.substring(0,6);
        String second = num.substring(6,num.length() - 4);
        StringBuilder str = new StringBuilder();
        for(int i=0;i<second.length();i++){
            str.append("*");
        }
        String three = num.substring(num.length() - 4);
        return first + str.toString() + three;
    }

    public static void main(String[] args) {
        System.out.println(name("330381199311220417@163.com"));
    }
}
