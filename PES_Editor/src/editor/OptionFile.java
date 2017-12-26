package editor;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Enumeration;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import java.util.zip.ZipOutputStream;
import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;

public class OptionFile
{
  public static final int LENGTH = 1250304;
  public byte[] data = new byte[1250304];
  private byte[][] xboxMaxEntryData;
  private String[] xboxMaxEntry;
  private int dataEntry;
  private byte[] headerData;
  public String fileName;
  public String gameID;
  public static final String we9ID = "BISLPM-66009WWE9OPT";
  public static final String pes5ID = "BESLES-53544PES5OPT";
  public static final String pes5itID = "BESLES-53545PES5OPT";
  public static final String we9uID = "BASLUS-21220WWE9OPT";
  private static final byte[] sharkport = { 13, 0, 0, 0, 83, 104, 97, 114, 107, 80, 111, 114, 116, 83, 97, 118, 101 };
  String gameName;
  String saveName;
  String notes;
  private Mac mac;
  private Mac macWE9u;
  static final int[] block = { 12, 5144, 9508, 14044, 36872, 657712, 803608, 822940, 969192, 1228568, 1250316 };
  static final int[] blockSize = { 4948, 1232, 4520, 22816, 620000, 145880, 19320, 146240, 259364, 21016 };
  private int[] key = { 5734, 5777, 5777, 5701, 5758, 5780, 5786, 5783, 5701, 5752, 5780, 5768, 5768, 5770, 5783, 5701, 5740, 5766, 5778, 5770, 5701, 5734, 5783, 5770, 5701, 5735, 5770, 5777, 5780, 5779, 5772, 5701, 5753, 5780, 5701, 5756, 5738, 5715, 5542, 5733, 5554, 5610, 5556, 5550, 5543, 5617, 5562, 5595, 5543, 5618, 5543, 5581, 5563, 5654, 5558, 5582, 5543, 5613, 5572, 5586, 5564, 5555, 5543, 5610, 5542, 5735, 5543, 5612, 5543, 5577, 5543, 5639, 5543, 5644, 5544, 5738, 5544, 5736, 5544, 5736, 5544, 5553, 5543, 5617, 5544, 5771, 5542, 5760, 5544, 5763, 5543, 5653, 5550, 5665, 5558, 5575, 5543, 5594, 5543, 5609, 5544, 5742, 5542, 5760, 5544, 5747, 5544, 5755, 5544, 5548, 5544, 5560, 5543, 5610, 5556, 5577, 5561, 5545, 5543, 5594, 5543, 5609, 5543, 5575, 5543, 5646, 5561, 5790, 5543, 5583, 5552, 5556, 5543, 5646, 5543, 5644, 5543, 5594, 5543, 5575, 5542, 5734, 5552, 5637, 5558, 5744, 5543, 5583, 5564, 5554, 5543, 5633, 5543, 5613, 5543, 5575, 5553, 5607, 5557, 5777, 5543, 5639, 5542, 5734, 5544, 5547, 5542, 5760, 5544, 5754, 5542, 5760, 5551, 5625, 5543, 5617, 5544, 5792, 5544, 5550, 5544, 5560, 5544, 5770, 5544, 5735, 5544, 5734, 5543, 5610, 5544, 5771, 5542, 5760, 5544, 5763, 5543, 5617, 5543, 5639, 5543, 5645, 5555, 5643, 5543, 5645, 5543, 5653, 5543, 5596, 5543, 5646, 5543, 5617, 5543, 5618, 5544, 5750, 5542, 5760, 5544, 5541, 5543, 5614, 5558, 5619, 5543, 5596, 5543, 5646, 5556, 5651, 5561, 5746, 5543, 5653, 5551, 5593, 5543, 5595, 5543, 5646, 5543, 5594, 5542, 5734, 5543, 5590, 5543, 5604, 5543, 5644, 5543, 5611, 5543, 5594, 5543, 5609, 5543, 5637, 5551, 5653, 5543, 5594, 5543, 5575, 5553, 5605, 5543, 5645, 5543, 5603, 5543, 5588, 5543, 5612, 5542, 5734, 5556, 5577, 5561, 5545, 5555, 5779, 5543, 5636, 5543, 5644, 5543, 5647, 5543, 5646, 5543, 5611, 5543, 5590, 5543, 5604, 5543, 5644, 5543, 5611, 5543, 5594, 5543, 5609, 5543, 5637, 5558, 5791, 5559, 5645, 5543, 5594, 5543, 5609, 5543, 5613, 5543, 5575, 5555, 5563, 5543, 5614, 5543, 5613, 5543, 5646, 5543, 5617, 5543, 5610, 5544, 5772, 5544, 5550, 5544, 5786, 5544, 5552, 5563, 5773, 5555, 5795, 5543, 5617, 5543, 5602, 5543, 5636, 5543, 5614, 5549, 5608, 5554, 5547, 5550, 5600, 5543, 5594, 5543, 5633, 5543, 5596, 5542, 5735, 5669 };
  private static final byte[] keyPC = { 115, 96, -31, -58, 31, 60, -83, 66, 11, 88, -71, -2, 55, -76, 5, -6, -93, 80, -111, 54, 79, 44, 93, -78, 59, 72, 105, 110, 103, -92, -75, 106, -45, 64, 65, -90, Byte.MAX_VALUE, 28, 13, 34, 107, 56, 25, -34, -105, -108, 101, -38, 3, 48, -15, 22, -81, 12, -67, -110, -101, 40, -55, 78, -57, -124, 21, 74, 51, 32, -95, -122, -33, -4, 109, 2, -53, 24, 121, -66, -9, 116, -59, -70, 99, 16, 81, -10, 15, -20, 29, 114, -5, 8, 41, 46, 39, 100, 117, 42, -109, 0, 1, 102, 63, -36, -51, -30, 43, -8, -39, -98, 87, 84, 37, -102, -61, -16, -79, -42, 111, -52, 125, 82, 91, -24, -119, 14, -121, 68, -43, 10, -13, -32, 97, 70, -97, -68, 45, -62, -117, -40, 57, 126, -73, 52, -123, 122, 35, -48, 17, -74, -49, -84, -35, 50, -69, -56, -23, -18, -25, 36, 53, -22, 83, -64, -63, 38, -1, -100, -115, -94, -21, -72, -103, 94, 23, 20, -27, 90, -125, -80, 113, -106, 47, -116, 61, 18, 27, -88, 73, -50, 71, 4, -107, -54, -77, -96, 33, 6, 95, 124, -19, -126, 75, -104, -7, 62, 119, -12, 69, 58, -29, -112, -47, 118, -113, 108, -99, -14, 123, -120, -87, -82, -89, -28, -11, -86, 19, Byte.MIN_VALUE, -127, -26, -65, 92, 77, 98, -85, 120, 89, 30, -41, -44, -91, 26, 67, 112, 49, 86, -17, 76, -3, -46, -37, 104, 9, -114, 7, -60, 85, -118 };
  byte format = -1;
  byte game = -1;
  
  public OptionFile()
  {
    for (int i = 0; i < this.key.length; i++) {
      this.key[i] += 1815543808;
    }
    SecretKeySpec localSecretKeySpec1 = new SecretKeySpec(PES4Utils.authKeyBytes, "HmacSHA1");
    SecretKeySpec localSecretKeySpec2 = new SecretKeySpec(PES4Utils.authKeyBytesWE9u, "HmacSHA1");
    try
    {
      this.mac = Mac.getInstance(localSecretKeySpec1.getAlgorithm());
      this.mac.init(localSecretKeySpec1);
      this.macWE9u = Mac.getInstance(localSecretKeySpec2.getAlgorithm());
      this.macWE9u.init(localSecretKeySpec2);
    }
    catch (InvalidKeyException localInvalidKeyException) {}catch (NoSuchAlgorithmException localNoSuchAlgorithmException) {}
  }
  
  public boolean readXPS(File paramFile)
  {
    this.format = -1;
    this.game = -1;
    boolean bool = true;
    String str = PES4Utils.getExtension(paramFile);
    if ((str != null) && (str.equals("zip"))) {
      bool = readXmax(paramFile);
    } else {
      try
      {
        RandomAccessFile localRandomAccessFile = new RandomAccessFile(paramFile, "r");
        byte[] arrayOfByte3;
        if ((str != null) && (str.equals("xps")))
        {
          long l = localRandomAccessFile.length() - 1250304L - 4L;
          localRandomAccessFile.seek(21L);
          int i = PES4Utils.swabInt(localRandomAccessFile.readInt());
          arrayOfByte3 = new byte[i];
          localRandomAccessFile.read(arrayOfByte3);
          this.gameName = new String(arrayOfByte3, "ISO-8859-1");
          i = PES4Utils.swabInt(localRandomAccessFile.readInt());
          arrayOfByte3 = new byte[i];
          localRandomAccessFile.read(arrayOfByte3);
          this.saveName = new String(arrayOfByte3, "ISO-8859-1");
          i = PES4Utils.swabInt(localRandomAccessFile.readInt());
          arrayOfByte3 = new byte[i];
          localRandomAccessFile.read(arrayOfByte3);
          this.notes = new String(arrayOfByte3, "ISO-8859-1");
          this.headerData = new byte[(int)l - 33 - this.gameName.length() - this.saveName.length() - this.notes.length()];
          localRandomAccessFile.read(this.headerData);
          this.gameID = new String(this.headerData, 6, 19);
          this.format = 0;
          if (this.gameID.equals("BESLES-53545PES5OPT")) {
            this.game = 1;
          } else if (this.gameID.equals("BASLUS-21220WWE9OPT")) {
            this.game = 2;
          } else {
            this.game = 0;
          }
        }
        else if ((str != null) && (str.equals("xsv")))
        {
          this.format = 2;
          byte[] arrayOfByte1 = new byte[1261568];
          localRandomAccessFile.read(arrayOfByte1);
          System.arraycopy(arrayOfByte1, 0, this.data, 0, 5132);
          System.arraycopy(arrayOfByte1, 16396, this.data, 5132, 1245172);
          byte[] arrayOfByte2 = new byte[20];
          localRandomAccessFile.read(arrayOfByte2);
          arrayOfByte3 = this.mac.doFinal(arrayOfByte1);
          byte[] arrayOfByte4 = this.macWE9u.doFinal(arrayOfByte1);
          if (sigGood(arrayOfByte2, arrayOfByte3)) {
            this.game = 0;
          } else if (sigGood(arrayOfByte2, arrayOfByte4)) {
            this.game = 2;
          } else {
            bool = false;
          }
        }
        else
        {
          this.format = 1;
        }
        if ((this.format == 0) || (this.format == 1))
        {
          localRandomAccessFile.read(this.data);
          if (this.format == 1) {
            convertData();
          }
          decrypt();
        }
        localRandomAccessFile.close();
      }
      catch (Exception localException)
      {
        localException.printStackTrace();
        bool = false;
      }
    }
    if (bool) {
      this.fileName = paramFile.getName();
    } else {
      this.fileName = null;
    }
    return bool;
  }
  
  private void convertData()
  {
    int i = 0;
    for (int j = 0; j < 1250304; j++)
    {
      this.data[j] = ((byte)(this.data[j] ^ keyPC[i]));
      if (i < 255) {
        i++;
      } else {
        i = 0;
      }
    }
  }
  
  public boolean saveXPS(File paramFile)
  {
    this.data[45] = 1;
    this.data[46] = 1;
    this.data['ᝆ'] = 1;
    this.data['ᝇ'] = 1;
    boolean bool = true;
    if (this.format == 3)
    {
      checkSums();
      bool = saveXmax(paramFile);
    }
    else
    {
      if ((this.format == 0) || (this.format == 1)) {
        encrypt();
      }
      checkSums();
      if (this.format == 1) {
        convertData();
      }
      try
      {
        RandomAccessFile localRandomAccessFile = new RandomAccessFile(paramFile, "rw");
        if (this.format == 0)
        {
          this.saveName = paramFile.getName();
          this.saveName = this.saveName.substring(0, this.saveName.length() - 4);
          localRandomAccessFile.write(sharkport);
          localRandomAccessFile.writeInt(PES4Utils.swabInt(this.gameName.length()));
          localRandomAccessFile.writeBytes(this.gameName);
          localRandomAccessFile.writeInt(PES4Utils.swabInt(this.saveName.length()));
          localRandomAccessFile.writeBytes(this.saveName);
          localRandomAccessFile.writeInt(PES4Utils.swabInt(this.notes.length()));
          localRandomAccessFile.writeBytes(this.notes);
          localRandomAccessFile.write(this.headerData);
        }
        if (this.format == 2) {
          localRandomAccessFile.write(getXBdata());
        } else {
          localRandomAccessFile.write(this.data);
        }
        if (this.format == 0)
        {
          localRandomAccessFile.write(0);
          localRandomAccessFile.write(0);
          localRandomAccessFile.write(0);
          localRandomAccessFile.write(0);
        }
        localRandomAccessFile.close();
      }
      catch (Exception localException)
      {
        localException.printStackTrace();
        bool = false;
      }
      if (this.format == 1) {
        convertData();
      }
      if ((this.format == 0) || (this.format == 1)) {
        decrypt();
      }
    }
    return bool;
  }
  
  public byte toByte(int paramInt)
  {
    byte b;
    if (paramInt > 127) {
      b = (byte)(paramInt - 256);
    } else {
      b = (byte)paramInt;
    }
    return b;
  }
  
  public int toInt(byte paramByte)
  {
    int i = paramByte;
    if (i < 0) {
      i += 256;
    }
    return i;
  }
  
  private boolean readXmax(File paramFile)
  {
    boolean bool = true;
    try
    {
      ZipFile localZipFile = new ZipFile(paramFile);
      this.xboxMaxEntryData = new byte[localZipFile.size()][];
      this.xboxMaxEntry = new String[localZipFile.size()];
      this.dataEntry = -1;
      String str1 = "UDATA/4b4e0030/5252897584A1/KONAMI-XBOX-PES5OPT.xsv";
      String str2 = "UDATA/4b4e002f/5252897584A1/KONAMI-XBOX-WE9UOPT.xsv";
      Enumeration localEnumeration = localZipFile.entries();
      Object localObject;
      byte[] arrayOfByte2;
      for (int i = 0; i < localZipFile.size(); i++)
      {
        localObject = (ZipEntry)localEnumeration.nextElement();
        if (!((ZipEntry)localObject).isDirectory())
        {
          this.xboxMaxEntry[i] = ((ZipEntry)localObject).getName();
          arrayOfByte2 = readZipEntry(localZipFile, (ZipEntry)localObject);
          if (arrayOfByte2 != null)
          {
            this.xboxMaxEntryData[i] = arrayOfByte2;
            String str3 = ((ZipEntry)localObject).getName();
            if (str3.equals(str1))
            {
              this.format = 3;
              this.game = 0;
              this.dataEntry = i;
            }
            else if (str3.equals(str2))
            {
              this.format = 3;
              this.game = 2;
              this.dataEntry = i;
            }
          }
        }
        else
        {
          this.xboxMaxEntry[i] = "dir";
        }
      }
      localZipFile.close();
      if ((this.dataEntry != -1) && (this.xboxMaxEntryData[this.dataEntry].length == 1261588))
      {
        byte[] arrayOfByte1 = new byte[20];
        System.arraycopy(this.xboxMaxEntryData[this.dataEntry], 0, this.data, 0, 5132);
        System.arraycopy(this.xboxMaxEntryData[this.dataEntry], 16396, this.data, 5132, 1245172);
        System.arraycopy(this.xboxMaxEntryData[this.dataEntry], 1261568, arrayOfByte1, 0, 20);
        localObject = new byte[1261568];
        System.arraycopy(this.xboxMaxEntryData[this.dataEntry], 0, localObject, 0, 1261568);
        if (this.game == 2) {
          arrayOfByte2 = this.macWE9u.doFinal((byte[])localObject);
        } else {
          arrayOfByte2 = this.mac.doFinal((byte[])localObject);
        }
        int j = 0;
        do
        {
          if (arrayOfByte1[j] != arrayOfByte2[j]) {
            bool = false;
          }
          j++;
          if (!bool) {
            break;
          }
        } while (j < 20);
      }
      else
      {
        bool = false;
      }
    }
    catch (Exception localException)
    {
      localException.printStackTrace();
      bool = false;
    }
    return bool;
  }
  
  private boolean saveXmax(File paramFile)
  {
    boolean bool = true;
    int i = 2048;
    try
    {
      BufferedInputStream localBufferedInputStream = null;
      FileOutputStream localFileOutputStream = new FileOutputStream(paramFile);
      ZipOutputStream localZipOutputStream = new ZipOutputStream(new BufferedOutputStream(localFileOutputStream));
      byte[] arrayOfByte = new byte[i];
      this.xboxMaxEntryData[this.dataEntry] = getXBdata();
      for (int k = 0; k < this.xboxMaxEntry.length; k++) {
        if (!this.xboxMaxEntry[k].equals("dir"))
        {
          ByteArrayInputStream localByteArrayInputStream = new ByteArrayInputStream(this.xboxMaxEntryData[k]);
          localBufferedInputStream = new BufferedInputStream(localByteArrayInputStream, i);
          localZipOutputStream.putNextEntry(new ZipEntry(this.xboxMaxEntry[k]));
          int j = 0;
          while ((j = localBufferedInputStream.read(arrayOfByte, 0, i)) != -1) {
            localZipOutputStream.write(arrayOfByte, 0, j);
          }
          localBufferedInputStream.close();
        }
      }
      localZipOutputStream.close();
    }
    catch (Exception localException)
    {
      localException.printStackTrace();
      bool = false;
    }
    return bool;
  }
  
  private void decrypt()
  {
    for (int i = 1; i < 10; i++)
    {
      int j = 0;
      for (int k = block[i]; k < block[i] + blockSize[i]; k += 4)
      {
        int m = byteToInt(k);
        int n = m - this.key[j] + 1815549477 ^ 0x6C371625;
        this.data[k] = toByte(n & 0xFF);
        this.data[(k + 1)] = toByte(n >>> 8 & 0xFF);
        this.data[(k + 2)] = toByte(n >>> 16 & 0xFF);
        this.data[(k + 3)] = toByte(n >>> 24 & 0xFF);
        j++;
        if (j == 367) {
          j = 0;
        }
      }
    }
  }
  
  private void encrypt()
  {
    for (int i = 1; i < 10; i++)
    {
      int j = 0;
      for (int k = block[i]; k < block[i] + blockSize[i]; k += 4)
      {
        int m = byteToInt(k);
        int n = this.key[j] + ((m ^ 0x6C371625) - 1815549477);
        this.data[k] = toByte(n & 0xFF);
        this.data[(k + 1)] = toByte(n >>> 8 & 0xFF);
        this.data[(k + 2)] = toByte(n >>> 16 & 0xFF);
        this.data[(k + 3)] = toByte(n >>> 24 & 0xFF);
        j++;
        if (j == 367) {
          j = 0;
        }
      }
    }
  }
  
  private int byteToInt(int paramInt)
  {
    int[] arrayOfInt = new int[4];
    arrayOfInt[0] = toInt(this.data[paramInt]);
    arrayOfInt[1] = toInt(this.data[(paramInt + 1)]);
    arrayOfInt[2] = toInt(this.data[(paramInt + 2)]);
    arrayOfInt[3] = toInt(this.data[(paramInt + 3)]);
    int i = arrayOfInt[0] | arrayOfInt[1] << 8 | arrayOfInt[2] << 16 | arrayOfInt[3] << 24;
    return i;
  }
  
  public void checkSums()
  {
    for (int i = 0; i < 10; i++)
    {
      int j = 0;
      for (int k = block[i]; k < block[i] + blockSize[i]; k += 4) {
        j += byteToInt(k);
      }
      this.data[(block[i] - 8)] = toByte(j & 0xFF);
      this.data[(block[i] - 7)] = toByte(j >>> 8 & 0xFF);
      this.data[(block[i] - 6)] = toByte(j >>> 16 & 0xFF);
      this.data[(block[i] - 5)] = toByte(j >>> 24 & 0xFF);
    }
  }
  
  public byte[] getBytes(int paramInt)
  {
    byte[] arrayOfByte = new byte[2];
    arrayOfByte[0] = toByte(paramInt & 0xFF);
    arrayOfByte[1] = toByte(paramInt >>> 8 & 0xFF);
    return arrayOfByte;
  }
  
  public static byte[] readZipEntry(ZipFile paramZipFile, ZipEntry paramZipEntry)
  {
    ByteArrayOutputStream localByteArrayOutputStream = new ByteArrayOutputStream();
    try
    {
      int i = 2048;
      BufferedInputStream localBufferedInputStream = new BufferedInputStream(paramZipFile.getInputStream(paramZipEntry));
      byte[] arrayOfByte = new byte[i];
      BufferedOutputStream localBufferedOutputStream = new BufferedOutputStream(localByteArrayOutputStream, i);
      int j;
      while ((j = localBufferedInputStream.read(arrayOfByte, 0, i)) != -1) {
        localBufferedOutputStream.write(arrayOfByte, 0, j);
      }
      localBufferedOutputStream.flush();
      localBufferedOutputStream.close();
      localBufferedInputStream.close();
    }
    catch (IOException localIOException)
    {
      localIOException.printStackTrace();
    }
    return localByteArrayOutputStream.toByteArray();
  }
  
  private byte[] getXBdata()
  {
    byte[] arrayOfByte1 = new byte[1261568];
    System.arraycopy(this.data, 0, arrayOfByte1, 0, 5132);
    System.arraycopy(this.data, 5132, arrayOfByte1, 16396, 1245172);
    byte[] arrayOfByte2 = new byte[1261588];
    System.arraycopy(arrayOfByte1, 0, arrayOfByte2, 0, arrayOfByte1.length);
    if (this.game == 2) {
      System.arraycopy(this.macWE9u.doFinal(arrayOfByte1), 0, arrayOfByte2, arrayOfByte1.length, 20);
    } else {
      System.arraycopy(this.mac.doFinal(arrayOfByte1), 0, arrayOfByte2, arrayOfByte1.length, 20);
    }
    return arrayOfByte2;
  }
  
  private boolean sigGood(byte[] paramArrayOfByte1, byte[] paramArrayOfByte2)
  {
    boolean bool = true;
    for (int i = 0; (i < 20) && (bool); i++) {
      if (paramArrayOfByte1[i] != paramArrayOfByte2[i]) {
        bool = false;
      }
    }
    return bool;
  }
}


/* Location:              PESFan Editor 5.08.jar!/editor/OptionFile.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       0.7.1
 */