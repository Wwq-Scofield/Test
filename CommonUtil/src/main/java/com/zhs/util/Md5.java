package com.zhs.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class Md5
{
  public static String md5(byte[] bin, boolean upperCase)
    throws NoSuchAlgorithmException
  {
    return Hex.hex(md5bin(bin), upperCase);
  }
  
  public static String md5(byte[] bin)
    throws NoSuchAlgorithmException
  {
    return md5(bin, false);
  }
  
  public static String md5(String string, boolean upperCase)throws UnsupportedEncodingException, NoSuchAlgorithmException
  {
    return Hex.hex(md5bin(string), upperCase);
  }
  
  public static String md5(String string) throws UnsupportedEncodingException, NoSuchAlgorithmException
  {
    return md5(string, false);
  }
  
  public static String md5(InputStream is, boolean upperCase)
    throws NoSuchAlgorithmException, IOException
  {
    return Hex.hex(md5bin(is), upperCase);
  }
  
  public static String md5(InputStream is)
    throws NoSuchAlgorithmException, IOException
  {
    return md5(is, false);
  }
  
  public static String md5(File file, boolean upperCase)
    throws NoSuchAlgorithmException, IOException
  {
    return Hex.hex(md5bin(file), upperCase);
  }
  
  public static String md5(File file)
    throws NoSuchAlgorithmException, IOException
  {
    return md5(file, false);
  }
  
  public static byte[] md5bin(byte[] bin)
    throws NoSuchAlgorithmException
  {
    MessageDigest digest = MessageDigest.getInstance("MD5");
    return digest.digest(bin);
  }
  
  public static byte[] md5bin(String string)
    throws UnsupportedEncodingException, NoSuchAlgorithmException
  {
    return md5bin(string.getBytes("utf-8"));
  }
  
  public static byte[] md5bin(InputStream is)
    throws NoSuchAlgorithmException, IOException
  {
    MessageDigest digest = MessageDigest.getInstance("MD5");
    byte[] buffer = new byte[1024];
    int read = 0;
    while ((read = is.read(buffer)) > 0) {
      digest.update(buffer, 0, read);
    }
    return digest.digest();
  }
  
  public static byte[] md5bin(File file)
    throws NoSuchAlgorithmException, IOException
  {
    InputStream is = null;
    try
    {
      is = new FileInputStream(file);
      return md5bin(is);
    }
    finally
    {
      IOUtil.closeQuietly(is);
    }
  }
  /**
   * MD5加密 输入一个String(需要加密的文本)，得到一个加密输出String（加密后的文本）
   */
  public static String getMD5(String s) {
    char hexDigits[] = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f'};
    byte[] btInput = s.getBytes();
    // 获得MD5摘要算法的 MessageDigest 对象
    MessageDigest mdInst = null;
    try {
      mdInst = MessageDigest.getInstance("MD5");
    } catch (NoSuchAlgorithmException e) {
      e.printStackTrace();
    }
    // 使用指定的字节更新摘要
    mdInst.update(btInput);
    // 获得密文
    byte[] md = mdInst.digest();
    // 把密文转换成十六进制的字符串形式
    int j = md.length;
    char str[] = new char[j * 2];
    int k = 0;
    for (int i = 0; i < j; i++) {
      byte byte0 = md[i];
      str[k++] = hexDigits[byte0 >>> 4 & 0xf];
      str[k++] = hexDigits[byte0 & 0xf];
    }
    return new String(str);
  }
}
