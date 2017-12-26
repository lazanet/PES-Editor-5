package editor;

import java.awt.Image;
import java.awt.image.BufferedImage;
import java.awt.image.ColorModel;
import java.awt.image.DataBufferByte;
import java.awt.image.IndexColorModel;
import java.awt.image.MultiPixelPackedSampleModel;
import java.awt.image.Raster;
import java.awt.image.WritableRaster;

public class Flags
{
  static byte total = 50;
  static int startAdr = 969356;
  static int size = 5184;
  static int paletteSize = 128;
  static int raster = 4096;
  static byte totalB = 100;
  static int startAdrB = 1225548;
  static int sizeB = 2176;
  static int paletteSizeB = 16;
  static int rasterB = 2048;
  
  static boolean flag128Here(OptionFile paramOptionFile, int paramInt)
  {
    boolean bool = false;
    if (paramOptionFile.data[(startAdr + paramInt * size)] == 1) {
      bool = true;
    }
    return bool;
  }
  
  static boolean flag16Here(OptionFile paramOptionFile, int paramInt)
  {
    boolean bool = false;
    if (paramOptionFile.data[(startAdr + 49 * size - paramInt / 2 * size + paramInt % 2 * sizeB)] == 1) {
      bool = true;
    }
    return bool;
  }
  
  static Image get128(OptionFile paramOptionFile, int paramInt, boolean paramBoolean1, boolean paramBoolean2)
  {
    byte[] arrayOfByte1 = new byte[paletteSize];
    byte[] arrayOfByte2 = new byte[paletteSize];
    byte[] arrayOfByte3 = new byte[paletteSize];
    byte[] arrayOfByte4 = new byte[paletteSize];
    byte[] arrayOfByte5 = new byte[raster];
    if ((paramInt >= 0) && (paramInt < total))
    {
      for (int j = 0; j < paletteSize; j++)
      {
        int i = startAdr + 64 + paramInt * size + j * 4;
        arrayOfByte1[j] = paramOptionFile.data[i];
        arrayOfByte2[j] = paramOptionFile.data[(i + 1)];
        arrayOfByte3[j] = paramOptionFile.data[(i + 2)];
        arrayOfByte4[j] = paramOptionFile.data[(i + 3)];
      }
      int i = startAdr + 1088 + paramInt * size;
      System.arraycopy(paramOptionFile.data, i, arrayOfByte5, 0, raster);
      if (paramBoolean1) {
        for (int j = 0; j < paletteSize; j++) {
          arrayOfByte4[j] = -1;
        }
      }
    }
    IndexColorModel localIndexColorModel = new IndexColorModel(8, paletteSize, arrayOfByte1, arrayOfByte2, arrayOfByte3, arrayOfByte4);
    DataBufferByte localDataBufferByte = new DataBufferByte(arrayOfByte5, raster, 0);
    MultiPixelPackedSampleModel localMultiPixelPackedSampleModel = new MultiPixelPackedSampleModel(0, 64, 64, 8);
    WritableRaster localWritableRaster = Raster.createWritableRaster(localMultiPixelPackedSampleModel, localDataBufferByte, null);
    BufferedImage localBufferedImage = new BufferedImage(localIndexColorModel, localWritableRaster, false, null);
    Object localObject;
    if (paramBoolean2) {
      localObject = localBufferedImage.getScaledInstance(32, 32, 1);
    } else {
      localObject = localBufferedImage;
    }
    return (Image)localObject;
  }
  
  static Image get16(OptionFile paramOptionFile, int paramInt, boolean paramBoolean1, boolean paramBoolean2)
  {
    byte[] arrayOfByte1 = new byte[paletteSizeB];
    byte[] arrayOfByte2 = new byte[paletteSizeB];
    byte[] arrayOfByte3 = new byte[paletteSizeB];
    byte[] arrayOfByte4 = new byte[paletteSizeB];
    byte[] arrayOfByte5 = new byte[rasterB];
    int j = startAdr + 49 * size - paramInt / 2 * size + paramInt % 2 * sizeB;
    if ((paramInt >= 0) && (paramInt < totalB))
    {
      for (int k = 0; k < paletteSizeB; k++)
      {
        int i = j + 64 + k * 4;
        arrayOfByte1[k] = paramOptionFile.data[i];
        arrayOfByte2[k] = paramOptionFile.data[(i + 1)];
        arrayOfByte3[k] = paramOptionFile.data[(i + 2)];
        arrayOfByte4[k] = paramOptionFile.data[(i + 3)];
      }
      int i = j + 128;
      System.arraycopy(paramOptionFile.data, i, arrayOfByte5, 0, rasterB);
      if (paramBoolean1) {
        for (int k = 0; k < paletteSizeB; k++) {
          arrayOfByte4[k] = -1;
        }
      }
    }
    IndexColorModel localIndexColorModel = new IndexColorModel(4, paletteSizeB, arrayOfByte1, arrayOfByte2, arrayOfByte3, arrayOfByte4);
    DataBufferByte localDataBufferByte = new DataBufferByte(arrayOfByte5, rasterB, 0);
    MultiPixelPackedSampleModel localMultiPixelPackedSampleModel = new MultiPixelPackedSampleModel(0, 64, 64, 4);
    WritableRaster localWritableRaster = Raster.createWritableRaster(localMultiPixelPackedSampleModel, localDataBufferByte, null);
    BufferedImage localBufferedImage = new BufferedImage(localIndexColorModel, localWritableRaster, false, null);
    Object localObject;
    if (paramBoolean2) {
      localObject = localBufferedImage.getScaledInstance(32, 32, 1);
    } else {
      localObject = localBufferedImage;
    }
    return (Image)localObject;
  }
  
  static boolean set128(OptionFile paramOptionFile, int paramInt, BufferedImage paramBufferedImage)
  {
    boolean bool = false;
    try
    {
      byte[] arrayOfByte1 = new byte['Ā'];
      byte[] arrayOfByte2 = new byte['Ā'];
      byte[] arrayOfByte3 = new byte['Ā'];
      byte[] arrayOfByte4 = new byte['Ā'];
      int[] arrayOfInt = new int[raster];
      Raster localRaster = paramBufferedImage.getData();
      ColorModel localColorModel = paramBufferedImage.getColorModel();
      if ((localColorModel instanceof IndexColorModel))
      {
        IndexColorModel localIndexColorModel = (IndexColorModel)localColorModel;
        if ((paramBufferedImage.getWidth() == 64) && (paramBufferedImage.getHeight() == 64))
        {
          localRaster.getPixels(0, 0, 64, 64, arrayOfInt);
          localIndexColorModel.getReds(arrayOfByte1);
          localIndexColorModel.getGreens(arrayOfByte2);
          localIndexColorModel.getBlues(arrayOfByte3);
          localIndexColorModel.getAlphas(arrayOfByte4);
          int k;
	  int j;
          int m;
          if (arrayOfByte4[0] != 0)
          {
            j = 0;
            for (int i2 = 1; i2 < 128; i2++) {
              if ((j == 0) && (arrayOfByte4[i2] == 0)) {
                j = i2;
              }
            }
            if (j != 0)
            {
              k = arrayOfByte1[0];
              m = arrayOfByte2[0];
              int n = arrayOfByte3[0];
              int i1 = arrayOfByte4[0];
              arrayOfByte1[0] = arrayOfByte1[j];
              arrayOfByte2[0] = arrayOfByte2[j];
              arrayOfByte3[0] = arrayOfByte3[j];
              arrayOfByte4[0] = 0;
              arrayOfByte1[j] = (byte)k;
              arrayOfByte2[j] = (byte)m;
              arrayOfByte3[j] = (byte)n;
              arrayOfByte4[j] = (byte)i1;
              for (int i2 = 0; i2 < raster; i2++) {
                if (arrayOfInt[i2] == 0) {
                  arrayOfInt[i2] = j;
                } else if (arrayOfInt[i2] == j) {
                  arrayOfInt[i2] = 0;
                }
              }
            }
          }
          int i;
          for (j = 0; j < paletteSize; j++)
          {
            i = startAdr + 64 + paramInt * size + j * 4;
            paramOptionFile.data[i] = arrayOfByte1[j];
            paramOptionFile.data[(i + 1)] = arrayOfByte2[j];
            paramOptionFile.data[(i + 2)] = arrayOfByte3[j];
            paramOptionFile.data[(i + 3)] = arrayOfByte4[j];
           // arrayOfByte4[j];
          }
          for (j = 0; j < raster; j++)
          {
            i = startAdr + 1088 + paramInt * size + j;
            paramOptionFile.data[i] = paramOptionFile.toByte(arrayOfInt[j]);
          }
          paramOptionFile.data[(startAdr + paramInt * size)] = 1;
          if (paramInt == count128(paramOptionFile))
          {
            setCount128(paramOptionFile, (byte)(count128(paramOptionFile) + 1));
            j = 0;
            for (k = 0; (j == 0) && (k < 50); k++)
            {
              i = 969198 + k;
              if (paramOptionFile.data[i] == -103)
              {
                paramOptionFile.data[i] = paramOptionFile.toByte(paramInt);
                m = 284 + k;
                System.arraycopy(paramOptionFile.getBytes(m), 0, paramOptionFile.data, startAdr + paramInt * size + 4, 2);
                j = 1;
              }
            }
          }
          bool = true;
        }
      }
    }
    catch (Exception localException)
    {
      localException.printStackTrace();
    }
    return bool;
  }
  
  static boolean set16(OptionFile paramOptionFile, int paramInt, BufferedImage paramBufferedImage)
  {
    boolean bool = false;
    try
    {
      byte[] arrayOfByte1 = new byte['Ā'];
      byte[] arrayOfByte2 = new byte['Ā'];
      byte[] arrayOfByte3 = new byte['Ā'];
      byte[] arrayOfByte4 = new byte['Ā'];
      int[] arrayOfInt = new int[raster];
      int j = startAdr + 49 * size - paramInt / 2 * size + paramInt % 2 * sizeB;
      Raster localRaster = paramBufferedImage.getData();
      ColorModel localColorModel = paramBufferedImage.getColorModel();
      if ((localColorModel instanceof IndexColorModel))
      {
        IndexColorModel localIndexColorModel = (IndexColorModel)localColorModel;
        if ((paramBufferedImage.getWidth() == 64) && (paramBufferedImage.getHeight() == 64))
        {
          localRaster.getPixels(0, 0, 64, 64, arrayOfInt);
          localIndexColorModel.getReds(arrayOfByte1);
          localIndexColorModel.getGreens(arrayOfByte2);
          localIndexColorModel.getBlues(arrayOfByte3);
          localIndexColorModel.getAlphas(arrayOfByte4);
          int m;
          int n;
	  int k;
          if (arrayOfByte4[0] != 0)
          {
            k = 0;
            for (int i3 = 1; i3 < 16; i3++) {
              if ((k == 0) && (arrayOfByte4[i3] == 0)) {
                k = i3;
              }
            }
            if (k != 0)
            {
              m = arrayOfByte1[0];
              n = arrayOfByte2[0];
              int i1 = arrayOfByte3[0];
              int i2 = arrayOfByte4[0];
              arrayOfByte1[0] = arrayOfByte1[k];
              arrayOfByte2[0] = arrayOfByte2[k];
              arrayOfByte3[0] = arrayOfByte3[k];
              arrayOfByte4[0] = 0;
              arrayOfByte1[k] = (byte) m;
              arrayOfByte2[k] = (byte) n;
              arrayOfByte3[k] = (byte) i1;
              arrayOfByte4[k] = (byte) i2;
              for (int i3 = 0; i3 < raster; i3++) {
                if (arrayOfInt[i3] == 0) {
                  arrayOfInt[i3] = k;
                } else if (arrayOfInt[i3] == k) {
                  arrayOfInt[i3] = 0;
                }
              }
            }
          }
          int i;
          for (k = 0; k < paletteSizeB; k++)
          {
            i = j + 64 + k * 4;
            paramOptionFile.data[i] = arrayOfByte1[k];
            paramOptionFile.data[(i + 1)] = arrayOfByte2[k];
            paramOptionFile.data[(i + 2)] = arrayOfByte3[k];
            paramOptionFile.data[(i + 3)] = arrayOfByte4[k];
            //arrayOfByte4[k];
          }
          for (k = 0; k < raster; k += 2)
          {
            i = j + 128 + k / 2;
            paramOptionFile.data[i] = paramOptionFile.toByte(arrayOfInt[k] << 4 | arrayOfInt[(k + 1)]);
          }
          paramOptionFile.data[j] = 1;
          if (paramInt == count16(paramOptionFile))
          {
            setCount16(paramOptionFile, (byte)(count16(paramOptionFile) + 1));
            k = 0;
            for (m = 0; (k == 0) && (m < 100); m++)
            {
              i = 969248 + m;
              if (paramOptionFile.data[i] == -103)
              {
                paramOptionFile.data[i] = paramOptionFile.toByte(50 + paramInt);
                n = 334 + m;
                System.arraycopy(paramOptionFile.getBytes(n), 0, paramOptionFile.data, j + 4, 2);
                k = 1;
              }
            }
          }
          bool = true;
        }
      }
    }
    catch (Exception localException)
    {
      localException.printStackTrace();
    }
    return bool;
  }
  
  static void importFlag(OptionFile paramOptionFile1, int paramInt1, OptionFile paramOptionFile2, int paramInt2)
  {
    int i = startAdr + paramInt1 * size;
    int j = startAdr + paramInt2 * size;
    System.arraycopy(paramOptionFile1.data, i, paramOptionFile2.data, j, size);
  }
  
  static byte count128(OptionFile paramOptionFile)
  {
    return paramOptionFile.data[969196];
  }
  
  static byte count16(OptionFile paramOptionFile)
  {
    return paramOptionFile.data[969197];
  }
  
  static void setCount16(OptionFile paramOptionFile, byte paramByte)
  {
    paramOptionFile.data[969197] = paramByte;
  }
  
  static void setCount128(OptionFile paramOptionFile, byte paramByte)
  {
    paramOptionFile.data[969196] = paramByte;
  }
  
  static byte getFree16(OptionFile paramOptionFile)
  {
    return (byte)(100 - count128(paramOptionFile) * 2 - count16(paramOptionFile));
  }
  
  static byte getFree128(OptionFile paramOptionFile)
  {
    return (byte)((100 - count128(paramOptionFile) * 2 - count16(paramOptionFile)) / 2);
  }
  
  static Image getImage(OptionFile paramOptionFile, int paramInt)
  {
    Image localImage = null;
    int i;
    if (paramInt < 50)
    {
      i = 969198 + paramInt;
      localImage = get128(paramOptionFile, paramOptionFile.data[i], false, false);
    }
    else
    {
      i = 969248 + paramInt - 50;
      int j = paramOptionFile.toInt(paramOptionFile.data[i]) - 50;
      localImage = get16(paramOptionFile, j, false, false);
    }
    return localImage;
  }
  
  static byte getLoc(OptionFile paramOptionFile, int paramInt)
  {
    int i = 969248 + paramInt - 50;
    if (paramInt < 50) {
      i = 969198 + paramInt;
    }
    return paramOptionFile.data[i];
  }
  
  static void deleteImage(OptionFile paramOptionFile, int paramInt)
  {
    int i;
    if (paramInt < 50)
    {
      i = 969198 + paramInt;
      delete128(paramOptionFile, paramOptionFile.data[i]);
    }
    else
    {
      i = 969248 + paramInt - 50;
      int j = paramOptionFile.toInt(paramOptionFile.data[i]) - 50;
      delete16(paramOptionFile, j);
    }
  }
  
  static byte[] getIndex(OptionFile paramOptionFile, int paramInt)
  {
    byte[] arrayOfByte = new byte[2];
    if (paramInt < 50)
    {
      System.arraycopy(paramOptionFile.data, startAdr + paramInt * size + 4, arrayOfByte, 0, 2);
    }
    else
    {
      paramInt -= 50;
      int i = startAdr + 49 * size - paramInt / 2 * size + paramInt % 2 * sizeB;
      System.arraycopy(paramOptionFile.data, i + 4, arrayOfByte, 0, 2);
    }
    return arrayOfByte;
  }
  
  static void delete16(OptionFile paramOptionFile, int paramInt)
  {
    int k;
    byte[] arrayOfByte = getIndex(paramOptionFile, paramInt + 50);
    int i = getInt(paramOptionFile, arrayOfByte) - 334;
    paramOptionFile.data[(969248 + i)] = -103;
    Clubs.unAssEmblem(paramOptionFile, i + 50);
    int j = startAdr + 49 * size - (count16(paramOptionFile) - 1) / 2 * size + (count16(paramOptionFile) - 1) % 2 * sizeB;
    if (paramInt != count16(paramOptionFile) - 1)
    {
      k = startAdr + 49 * size - paramInt / 2 * size + paramInt % 2 * sizeB;
      System.arraycopy(paramOptionFile.data, j, paramOptionFile.data, k, sizeB);
      i = getInt(paramOptionFile, getIndex(paramOptionFile, paramInt + 50)) - 334;
      paramOptionFile.data[(969248 + i)] = paramOptionFile.toByte(paramInt + 50);
    }
    for (k = j; k < j + sizeB; k++) {
      paramOptionFile.data[k] = 0;
    }
    setCount16(paramOptionFile, paramOptionFile.toByte(count16(paramOptionFile) - 1));
  }
  
  static void delete128(OptionFile paramOptionFile, int paramInt)
  {
    byte[] arrayOfByte = getIndex(paramOptionFile, paramInt);
    int i = getInt(paramOptionFile, arrayOfByte) - 284;
    paramOptionFile.data[(969198 + i)] = -103;
    Clubs.unAssEmblem(paramOptionFile, i);
    int j = startAdr + (count128(paramOptionFile) - 1) * size;
    int k;
    if (paramInt != count128(paramOptionFile) - 1)
    {
      k = startAdr + paramInt * size;
      System.arraycopy(paramOptionFile.data, j, paramOptionFile.data, k, size);
      i = getInt(paramOptionFile, getIndex(paramOptionFile, paramInt)) - 284;
      paramOptionFile.data[(969198 + i)] = paramOptionFile.toByte(paramInt);
    }
    for (k = j; k < j + size; k++) {
      paramOptionFile.data[k] = 0;
    }
    setCount128(paramOptionFile, paramOptionFile.toByte(count128(paramOptionFile) - 1));
  }
  
  static int getInt(OptionFile paramOptionFile, byte[] paramArrayOfByte)
  {
    int i = 0;
    if (paramArrayOfByte.length == 2) {
      i = paramOptionFile.toInt(paramArrayOfByte[1]) << 8 | paramOptionFile.toInt(paramArrayOfByte[0]) & 0xFF;
    }
    return i;
  }
  
  static void import16(OptionFile paramOptionFile1, int paramInt1, OptionFile paramOptionFile2, int paramInt2)
  {
    int i = startAdr + 49 * size - paramInt1 / 2 * size + paramInt1 % 2 * sizeB;
    int j = startAdr + 49 * size - paramInt2 / 2 * size + paramInt2 % 2 * sizeB;
    System.arraycopy(paramOptionFile2.data, j + 64, paramOptionFile1.data, i + 64, sizeB - 64);
    if (paramInt1 == count16(paramOptionFile1))
    {
      paramOptionFile1.data[i] = 1;
      setCount16(paramOptionFile1, (byte)(count16(paramOptionFile1) + 1));
      int k = 0;
      for (int m = 0; (k == 0) && (m < 100); m++)
      {
        int n = 969248 + m;
        if (paramOptionFile1.data[n] == -103)
        {
          paramOptionFile1.data[n] = paramOptionFile1.toByte(50 + paramInt1);
          int i1 = 334 + m;
          System.arraycopy(paramOptionFile1.getBytes(i1), 0, paramOptionFile1.data, i + 4, 2);
          k = 1;
        }
      }
    }
  }
  
  static void import128(OptionFile paramOptionFile1, int paramInt1, OptionFile paramOptionFile2, int paramInt2)
  {
    int i = startAdr + paramInt1 * size;
    int j = startAdr + paramInt2 * size;
    System.arraycopy(paramOptionFile2.data, j + 64, paramOptionFile1.data, i + 64, size - 64);
    if (paramInt1 == count128(paramOptionFile1))
    {
      paramOptionFile1.data[i] = 1;
      setCount128(paramOptionFile1, (byte)(count128(paramOptionFile1) + 1));
      int k = 0;
      for (int m = 0; (k == 0) && (m < 50); m++)
      {
        int n = 969198 + m;
        if (paramOptionFile1.data[n] == -103)
        {
          paramOptionFile1.data[n] = paramOptionFile1.toByte(paramInt1);
          int i1 = 284 + m;
          System.arraycopy(paramOptionFile1.getBytes(i1), 0, paramOptionFile1.data, i + 4, 2);
          k = 1;
        }
      }
    }
  }
}


/* Location:              PESFan Editor 5.08.jar!/editor/Flags.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       0.7.1
 */
