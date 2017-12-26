package editor;

import java.awt.image.BufferedImage;
import java.awt.image.ColorModel;
import java.awt.image.DataBufferByte;
import java.awt.image.IndexColorModel;
import java.awt.image.MultiPixelPackedSampleModel;
import java.awt.image.Raster;
import java.awt.image.WritableRaster;

public class Logos
{
  static byte total = 80;
  static int startAdr = 920540;
  static int size = 608;
  static int paletteSize = 16;
  static int raster = 512;
  
  static boolean isUsed(OptionFile paramOptionFile, int paramInt)
  {
    boolean bool = false;
    if (paramOptionFile.data[(startAdr + paramInt * size)] == 1) {
      bool = true;
    }
    return bool;
  }
  
  static BufferedImage get(OptionFile paramOptionFile, int paramInt, boolean paramBoolean)
  {
    byte[] arrayOfByte1 = new byte[paletteSize];
    byte[] arrayOfByte2 = new byte[paletteSize];
    byte[] arrayOfByte3 = new byte[paletteSize];
    byte[] arrayOfByte4 = new byte[paletteSize];
    byte[] arrayOfByte5 = new byte[raster];
    int i;
    if ((paramInt >= 0) && (paramInt < total) && (isUsed(paramOptionFile, paramInt)))
    {
      for (int j = 0; j < paletteSize; j++)
      {
        i = startAdr + 32 + paramInt * size + j * 4;
        arrayOfByte1[j] = paramOptionFile.data[i];
        arrayOfByte2[j] = paramOptionFile.data[(i + 1)];
        arrayOfByte3[j] = paramOptionFile.data[(i + 2)];
        arrayOfByte4[j] = paramOptionFile.data[(i + 3)];
      }
      i = startAdr + 96 + paramInt * size;
      System.arraycopy(paramOptionFile.data, i, arrayOfByte5, 0, raster);
      if (paramBoolean) {
        for (int j = 0; j < paletteSize; j++) {
          arrayOfByte4[j] = -1;
        }
      }
    }
    IndexColorModel localIndexColorModel = new IndexColorModel(4, paletteSize, arrayOfByte1, arrayOfByte2, arrayOfByte3, arrayOfByte4);
    DataBufferByte localDataBufferByte = new DataBufferByte(arrayOfByte5, raster, 0);
    MultiPixelPackedSampleModel localMultiPixelPackedSampleModel = new MultiPixelPackedSampleModel(0, 32, 32, 4);
    WritableRaster localWritableRaster = Raster.createWritableRaster(localMultiPixelPackedSampleModel, localDataBufferByte, null);
    BufferedImage localBufferedImage = new BufferedImage(localIndexColorModel, localWritableRaster, false, null);
    return localBufferedImage;
  }
  
  static boolean set(OptionFile paramOptionFile, int paramInt, BufferedImage paramBufferedImage)
  {
    boolean bool = false;
    try
    {
      byte[] arrayOfByte1 = new byte['Ā'];
      byte[] arrayOfByte2 = new byte['Ā'];
      byte[] arrayOfByte3 = new byte['Ā'];
      byte[] arrayOfByte4 = new byte['Ā'];
      int[] arrayOfInt = new int['Ѐ'];
      Raster localRaster = paramBufferedImage.getData();
      ColorModel localColorModel = paramBufferedImage.getColorModel();
      if ((localColorModel instanceof IndexColorModel))
      {
        IndexColorModel localIndexColorModel = (IndexColorModel)localColorModel;
        if ((paramBufferedImage.getWidth() == 32) && (paramBufferedImage.getHeight() == 32))
        {
          localRaster.getPixels(0, 0, 32, 32, arrayOfInt);
          localIndexColorModel.getReds(arrayOfByte1);
          localIndexColorModel.getGreens(arrayOfByte2);
          localIndexColorModel.getBlues(arrayOfByte3);
          localIndexColorModel.getAlphas(arrayOfByte4);
	  int j, i2;
          if (arrayOfByte4[0] != 0)
          {
            j = 0;
            for (i2 = 1; i2 < 16; i2++) {
              if ((j == 0) && (arrayOfByte4[i2] == 0)) {
                j = i2;
              }
            }
            if (j != 0)
            {
              int k = arrayOfByte1[0];
              int m = arrayOfByte2[0];
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
              for (i2 = 0; i2 < 1024; i2++) {
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
            i = startAdr + 32 + paramInt * size + j * 4;
            paramOptionFile.data[i] = arrayOfByte1[j];
            paramOptionFile.data[(i + 1)] = arrayOfByte2[j];
            paramOptionFile.data[(i + 2)] = arrayOfByte3[j];
            paramOptionFile.data[(i + 3)] = arrayOfByte4[j];
          }
          for (j = 0; j < 1024; j += 2)
          {
            i = startAdr + 96 + paramInt * size + j / 2;
            paramOptionFile.data[i] = paramOptionFile.toByte(arrayOfInt[j] << 4 | arrayOfInt[(j + 1)]);
          }
          paramOptionFile.data[(startAdr + paramInt * size)] = 1;
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
  
  static void importLogo(OptionFile paramOptionFile1, int paramInt1, OptionFile paramOptionFile2, int paramInt2)
  {
    int i = startAdr + paramInt1 * size;
    int j = startAdr + paramInt2 * size;
    System.arraycopy(paramOptionFile1.data, i, paramOptionFile2.data, j, size);
  }
  
  static void delete(OptionFile paramOptionFile, byte paramByte)
  {
    int i = startAdr + paramByte * size;
    for (int j = 0; j < size; j++) {
      paramOptionFile.data[(i + j)] = 0;
    }
  }
}


/* Location:              PESFan Editor 5.08.jar!/editor/Logos.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       0.7.1
 */
