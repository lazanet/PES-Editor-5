package editor;

public class Kits
{
  static int totalN = 64;
  static int startAdrN = 822940;
  static int sizeN = 352;
  static int totalC = 138;
  static int startAdrC = 845468;
  static int sizeC = 544;
  
  static boolean logoUsed(OptionFile paramOptionFile, int paramInt1, int paramInt2)
  {
    int i = startAdrC + 256 + sizeC * paramInt1 + paramInt2 * 24 + 2;
    if (paramInt1 >= totalC)
    {
      paramInt1 -= totalC;
      i = startAdrN + 256 + sizeN * paramInt1 + paramInt2 * 24 + 2;
    }
    return paramOptionFile.data[i] == 1;
  }
  
  static byte getLogo(OptionFile paramOptionFile, int paramInt1, int paramInt2)
  {
    int i = startAdrC + 256 + sizeC * paramInt1 + paramInt2 * 24 + 3;
    if (paramInt1 >= totalC)
    {
      paramInt1 -= totalC;
      i = startAdrN + 256 + sizeN * paramInt1 + paramInt2 * 24 + 3;
    }
    return paramOptionFile.data[i];
  }
  
  static void setLogo(OptionFile paramOptionFile, int paramInt1, int paramInt2, byte paramByte)
  {
    int i = startAdrC + 256 + sizeC * paramInt1 + paramInt2 * 24 + 3;
    if (paramInt1 >= totalC)
    {
      paramInt1 -= totalC;
      i = startAdrN + 256 + sizeN * paramInt1 + paramInt2 * 24 + 3;
    }
    paramOptionFile.data[i] = paramByte;
  }
  
  static void setLogoUnused(OptionFile paramOptionFile, int paramInt1, int paramInt2)
  {
    int i = startAdrC + 256 + sizeC * paramInt1 + paramInt2 * 24 + 2;
    if (paramInt1 >= totalC)
    {
      paramInt1 -= totalC;
      i = startAdrN + 256 + sizeN * paramInt1 + paramInt2 * 24 + 2;
    }
    paramOptionFile.data[i] = 0;
    paramOptionFile.data[(i + 1)] = 88;
  }
  
  static void importKit(OptionFile paramOptionFile1, int paramInt1, OptionFile paramOptionFile2, int paramInt2)
  {
    int i = startAdrC + sizeC * paramInt1;
    int j = startAdrC + sizeC * paramInt2;
    int k = sizeC;
    if (paramInt1 >= totalC)
    {
      paramInt1 -= totalC;
      i = startAdrN + sizeN * paramInt1;
      paramInt2 -= totalC;
      j = startAdrN + sizeN * paramInt2;
      k = sizeN;
    }
    System.arraycopy(paramOptionFile2.data, j, paramOptionFile1.data, i, k);
  }
}


/* Location:              PESFan Editor 5.08.jar!/editor/Kits.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       0.7.1
 */