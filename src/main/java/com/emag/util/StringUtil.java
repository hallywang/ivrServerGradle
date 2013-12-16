package com.emag.util;

import org.apache.commons.lang.StringUtils;

import java.util.*;

/**
 * viavame All Rights Reserved@2008-2009 ����: ������ ����: 2008-12-16 ʱ��: 10:11:27 ˵��: ����ʵ�֡������� �޸���:������ �޸�ʱ��: 2008-12-16
 * 10:11:27
 */
public class StringUtil {

    public static long String2Long(Object obj, long def) {
        long result = def;
        if (!isEmpty(Object2Str(obj))) {
            try {
                result = Long.valueOf(Object2Str(obj));
            } catch (Exception ex) {
                //ex.printStackTrace();
            }
        }

        return result;
    }

    public static int String2Int(String str, int defaultValue) {
        if (str != null) {
            try {
                return Integer.valueOf(str.trim());
            } catch (Exception e) {
            }
        }
        return defaultValue;
    }

    public static double String2Double(String str, double defaultValue) {
        if (str != null) {
            try {
                return Double.valueOf(str.trim());
            } catch (Exception e) {
            }
        }
        return defaultValue;
    }

    public static int[] StringArray2IntArray(String[] str) {
        int[] result = {};
        if (str != null && str.length > 0) {
            result = new int[str.length];
            try {
                for (int i = 0; i < str.length; i++) {
                    result[i] = Integer.parseInt(str[i]);
                }
            } catch (Exception e) {
            }
        }
        return result;
    }

    public static boolean isEmpty(String args) {
        if (args != null && args.trim().length() > 0)
            return false;
        return true;
    }

    public static String Object2Str(Object obj) {
        if (obj != null && !obj.toString().equalsIgnoreCase("null")) {
            return obj.toString().trim();
        }

        return "";
    }

    /**
     * �õ��ļ�ǰ׺����������չ��
     *
     * @param fileName
     * @return
     */
    public static String getFilePreName(String fileName) {
        if (fileName == null || "".equals(fileName)) return "";

        return fileName.substring(0, fileName.lastIndexOf("."));
    }

    /**
     * �õ��ļ���׺
     *
     * @param fileName
     * @return
     */
    public static String getFileExtName(String fileName) {
        if (fileName == null || "".equals(fileName)) return "";

        return fileName.substring(fileName.lastIndexOf(".") + 1, fileName.length());
    }

    /**
     * �õ��ļ��ļ����ƣ��� e:/a.txt �õ�a.txt
     *
     * @param fileName
     * @return
     */
    public static String getFileName(String fileName) {
        if (fileName == null || "".equals(fileName)) return "";

        return fileName.substring(fileName.lastIndexOf("/") + 1, fileName.length());
    }

    /**
     * �õ��ļ�������ǰ׺������·��
     *
     * @param fileName
     * @return
     */
    public static String getSingleExtName(String fileName) {
        return StringUtil.getFilePreName(StringUtil.getFileName(fileName));
    }

    public static String encodeWml(String s) {

        if (s == null) s = "";
        return s.replaceAll("&amp;", "&")
                .replaceAll("&", "&amp;")
                .replaceAll("<", "&lt;")
                .replaceAll(">", "&gt;")
                .replaceAll("'", "&apos;")
                .replaceAll("\"", "&quot;")
                ;
    }

    /**
     * Return left characters of a string according to the given length.
     *
     * @param s     Input String
     * @param limit Max display length of the string.
     * @return cutted string
     */
    public static String cutString(String s, int limit) {
        if (s == null || s.length() == 0)
            return "";

        if (s.length() < limit / 2 || limit < 1)
            return s;

        char c;
        int count = 0;
        for (int i = 0; i < s.length(); i++) {
            c = s.charAt(i);
            if (c > 128 || c < 0) count++;

            count++;

            if (count >= limit) {
                // System.out.println(count);
                int x = count % 2 == 0 ? ++i : i;
                return s.substring(0, x) + "...";
            }
        }

        return s;
    }

    /**
     * �ַ���ת����ʮ������ֵ
     *
     * @param bin String ���ǿ�����Ҫת����ʮ�����Ƶ��ַ���
     * @return
     */
    public static String bin2hex(String bin) {
        char[] digital = "0123456789ABCDEF".toCharArray();
        StringBuffer sb = new StringBuffer("");
        byte[] bs = bin.getBytes();
        int bit;
        for (int i = 0; i < bs.length; i++) {
            bit = (bs[i] & 0x0f0) >> 4;
            sb.append(digital[bit]);
            bit = bs[i] & 0x0f;
            sb.append(digital[bit]);
        }
        return sb.toString();
    }

    /**
     * ʮ������ת���ַ���
     *
     * @param hex String ʮ������
     * @return String ת������ַ���
     */
    public static String hex2bin(String hex) {
        String digital = "0123456789ABCDEF";
        char[] hex2char = hex.toCharArray();
        byte[] bytes = new byte[hex.length() / 2];
        int temp;
        for (int i = 0; i < bytes.length; i++) {
            temp = digital.indexOf(hex2char[2 * i]) * 16;
            temp += digital.indexOf(hex2char[2 * i + 1]);
            bytes[i] = (byte) (temp & 0xff);
        }
        return new String(bytes);
    }


    public static void main(String[] args) {
        // System.out.println(StringUtil.getFilePreName("daf.ads.jpg"));
        //  System.out.println(StringUtil.getFileExtName("daf.ads.jpg"));
        /*  System.out.println(StringUtil.encodeWml("<><><><>''''''\"\""));

       String ha="Mozilla/5.0 (Linux; U; Android 2.2.1; zh-cn; Milestone XT720 Build/MIUI) AppleWebKit/533.1 (KHTML, like Gecko) Version/4.0 Mobile Safari/533.1";
      boolean  b = Pattern.compile(".*android.*|.*juc.*|.*ophone.*".toLowerCase()).matcher(StringUtil.getString(ha).toLowerCase()).matches();

        System.out.println(b);*/

        System.out.println(StringUtils.isAlphanumeric("abc123"));
        System.out.println(StringUtils.isAlphanumeric("12345"));
        System.out.println(StringUtils.isAlphanumeric("eeeee"));
        System.out.println(StringUtils.isAlphanumeric("abc123}"));


    }

    public static String getString(Object obj) {
        if (obj == null) {
            return "";
        }

        return obj.toString();
    }


    /**
     * ������num����Ϊָ������length���ַ����� 1 ��Ϊ001 ��01 ��
     *
     * @param num    ��Ҫ����ġ����֡�
     * @param length ���䳤��
     * @return �������ַ� �� 02,003��
     * @editor:RongWei
     * @editDate:2010-11-02
     */
    public static String addZeroToNum(String num, int length) {

        StringBuffer rst = new StringBuffer();

        int l = num.length();

        for (int i = 0; i < (length - l); i++) {
            rst.append("0");
        }

        rst.append(num);

        return rst.toString();

    }

    public static List<Map.Entry<String, Integer>> orderMap(Map map) {
        List<Map.Entry<String, Integer>> infoIds = new ArrayList<Map.Entry<String, Integer>>(map.entrySet());
//����
        Collections.sort(infoIds, new Comparator<Map.Entry<String, Integer>>() {
            public int compare(Map.Entry<String, Integer> o1,
                               Map.Entry<String, Integer> o2) {
                return (o2.getValue() - o1.getValue());
            }
        });

        return infoIds;
    }

    /**
     * �ж��ַ����Ƿ�������
     *
     * @param str
     * @return
     */
    public static boolean isNumeric(String str) {
        return StringUtils.isNumeric(str);
    }


}