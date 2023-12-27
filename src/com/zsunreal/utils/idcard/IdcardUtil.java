package com.zsunreal.utils.idcard;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Objects;

/**
 * 身份证解析工具
 *
 * @Author zhengdz
 * @Date 2023/01/27  10:08
 */
public class IdcardUtil {
    static HashMap<String, String> provice = new HashMap<>();
    static HashMap<String, String> city = new HashMap<>();
    static HashMap<String, String> area = new HashMap<>();

    static {
        try {
            InputStream is = IdcardUtil.class.getResourceAsStream("file/province.txt");
            String p = getStringFromStream(is);
            provice = init(p);
            is = IdcardUtil.class.getResourceAsStream("file/city.txt");
            p = getStringFromStream(is);
            city = init(p);
            is = IdcardUtil.class.getResourceAsStream("file/area.txt");
            p = getStringFromStream(is);
            area = init(p);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static String getStringFromStream(InputStream in) throws IOException {
        BufferedReader reader;
        reader = new BufferedReader(new InputStreamReader(in, StandardCharsets.UTF_8));

        StringBuilder buffer = new StringBuilder();
        String str;
        while ((str = reader.readLine()) != null) {
            buffer.append(str).append("\n");
        }
        reader.close();

        return buffer.toString();
    }

    public static void main(String[] args) {
        //获取省
        System.out.println(IdcardUtil.getProvince("53"));
        //获取市
        System.out.println(IdcardUtil.getCity("5321"));
        //获取区/县
        System.out.println(IdcardUtil.getArea("532122"));
        //获取年龄
        System.out.println(getAgeByCertNo("532122xxxxxxxxxx17"));
        //获取性别
        System.out.println(getSexByCertNo("532122xxxxxxxxxx17"));
        //获取地址
        System.out.println(IdcardUtil.getAddressByCertNo("532122xxxxxxxxxx17"));
    }

    private static HashMap<String, String> init(String data) {
        HashMap<String, String> hm = new HashMap<String, String>();
        if (Objects.nonNull(data)) {
            String[] datas = data.split("\n");
            for (String d : datas) {
                String[] dd = d.split("\t");
                if (dd.length > 1) {
                    hm.put(dd[0].trim(), dd[1].trim());
                }
            }
        }
        return hm;
    }

    public static String getProvince(String code) {
        String result = "";
        if (provice != null) {
            result = provice.get(code.trim());
        }
        return result;
    }

    public static String getCity(String code) {
        String result = "";
        if (provice != null) {
            result = city.get(code.trim());
        }
        return result;
    }

    public static String getArea(String code) {
        String result = "";
        if (provice != null) {
            result = area.get(code.trim());
        }
        return result;
    }

    /**
     * 根据身份证号获取年龄
     *
     * @param IDCard
     * @return
     */
    public static String getAgeByCertNo(String IDCard) {
        if (IdcardValidator.isIdcard(IDCard)) {
            if (IDCard.length() == 15) {
                IDCard = "19" + IDCard.substring(6, 12);
            } else {
                IDCard = IDCard.substring(6, 14);
            }
            LocalDate beginDateTime = LocalDate.parse(IDCard, DateTimeFormatter.ofPattern("yyyyMMdd"));
            return String.valueOf(Period.between(beginDateTime, LocalDate.now()).getYears());
        }
        return null;
    }


    /**
     * 根据身份证号获取性别
     *
     * @param IDCard
     * @return
     */
    public static String getSexByCertNo(String IDCard) {
        String sexStr = null;
        if (IdcardValidator.isIdcard(IDCard)) {
            if (IDCard.length() == 15) {
                String sex = IDCard.substring(14, 15);
                //偶数表示女性，奇数表示男性
                if (Integer.parseInt(sex) % 2 == 0) {
                    sexStr = "女";
                } else {
                    sexStr = "男";
                }
            } else {
                String sex = IDCard.substring(16, 17);
                //偶数表示女性，奇数表示男性
                if (Integer.parseInt(sex) % 2 == 0) {
                    sexStr = "女";
                } else {
                    sexStr = "男";
                }
            }
        }
        return sexStr;
    }

    public static String getAddressByCertNo(String idCardNo) {
        if (IdcardValidator.isIdcard(idCardNo)) {
            String nativePlaceCode = idCardNo.substring(0, 6);
            String shengCode = idCardNo.substring(0, 2);
            String shiCode = idCardNo.substring(0, 4);
            String sheng = getProvince(shengCode);
            String shi = getCity(shiCode);
            String quxian = getArea(nativePlaceCode);
            String nativePlace = "";
            if (sheng != null) {
                nativePlace += sheng;
            }
            if (shi != null) {
                nativePlace += shi;
            }
            if (quxian != null) {
                nativePlace += quxian;
            }
            return nativePlace;
        }
        return "";
    }

}
