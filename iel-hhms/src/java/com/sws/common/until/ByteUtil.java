package com.sws.common.until;import java.util.Date;public final class ByteUtil{	public final static byte GET_AP_NO = 0x21;	public final static byte GET_AP_CODE = 0x22; 	public final static byte SET_AP_NO = 0x23;	public final static byte SET_AP_CODE = 0x24;	public final static byte GET_STATE = 0x25;//欠压状态 	public static byte[] time6Byte() {		Date date = new Date();		byte[] time = new byte[6];		time[0] = int2BCD(DateUtils.getYear(date) - 2000);		time[1] = int2BCD(DateUtils.getMonth(date) + 1);		time[2] = int2BCD(DateUtils.getDay(date));		time[3] = int2BCD(DateUtils.getHour(date));		time[4] = int2BCD(DateUtils.getMin(date));		time[5] = int2BCD(DateUtils.getSecond(date));		return time;	}	public static byte int2BCD(int i) {		byte b = 0;		int move = 0;		while (i > 0) {			b = (byte) (b | (i % 10) << move);			i /= 10;			move += 4;		}		return b;	}	public static byte[] intToByte4(int i) {		byte[] result = new byte[4];		result[0] = (byte) (i & 0xFF);		result[1] = (byte) ((i >> 8) & 0xFF);		result[2] = (byte) ((i >> 16) & 0xFF);		result[3] = (byte) ((i >> 24) & 0xFF);		return result;	}	public static byte[] intToByte3(int i) {		byte[] result = new byte[3];		result[0] = (byte) (i & 0xFF);		result[1] = (byte) ((i >> 8) & 0xFF);		result[2] = (byte) ((i >> 16) & 0xFF);		return result;	}	public static byte[] intToByte2(int i) {		byte[] result = new byte[2];		result[0] = (byte) (i & 0xFF);		result[1] = (byte) ((i >> 8) & 0xFF);		return result;	}	// java带负号号转换	public static byte intToByte(int i) {		return (byte) (i & 0xFF);	}	// 因为java比特最高位是符号位，需要取字节反码	public static int antonymyByte(byte b) {		if (b < 0) {			b = (byte) ~b;			return 255 - b;		}		return b;	}	/**	 * 	 * @param b	 *            对应转换byte[]	 * @param len	 *            需要转换的字节长度	 * @param seq	 *            在byte[]里的字节索引	 * @return	 */	public static Integer byteToInt(byte[] b, int len, int seq) {		if (b == null) {			return 0;		}		int result = 0;		if (len > 0) {			result = antonymyByte(b[seq]) + result;		}		if (len > 1) {			result = (antonymyByte(b[seq + 1]) << 8) + result;		}		if (len > 2) {			result = (antonymyByte(b[seq + 2]) << 16) + result;		}		if (len > 3) {			result = (antonymyByte(b[seq + 3]) << 24) + result;		}		return result;	}	/**      * @功能: 10进制串转为BCD码      * @参数: 10进制串      * @结果: BCD码      */      public static byte[] str2Bcd(String asc) {          int len = asc.length();          int mod = len % 2;          if (mod != 0) {              asc = "0" + asc;              len = asc.length();          }          byte abt[] = new byte[len];          if (len >= 2) {              len = len / 2;          }          byte bbt[] = new byte[len];          abt = asc.getBytes();          int j, k;          for (int p = 0; p < asc.length() / 2; p++) {              if ((abt[2 * p] >= '0') && (abt[2 * p] <= '9')) {                  j = abt[2 * p] - '0';              } else if ((abt[2 * p] >= 'a') && (abt[2 * p] <= 'z')) {                  j = abt[2 * p] - 'a' + 0x0a;              } else {                  j = abt[2 * p] - 'A' + 0x0a;              }              if ((abt[2 * p + 1] >= '0') && (abt[2 * p + 1] <= '9')) {                  k = abt[2 * p + 1] - '0';              } else if ((abt[2 * p + 1] >= 'a') && (abt[2 * p + 1] <= 'z')) {                  k = abt[2 * p + 1] - 'a' + 0x0a;              } else {                  k = abt[2 * p + 1] - 'A' + 0x0a;              }              int a = (j << 4) + k;              byte b = (byte) a;              bbt[p] = b;          }          return bbt;      }  }