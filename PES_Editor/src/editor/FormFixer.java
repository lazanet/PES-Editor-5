package editor;

public class FormFixer
{
  public static void fixForm(OptionFile paramOptionFile, int paramInt, boolean paramBoolean)
  {
    byte[] arrayOfByte1 = new byte[64];
    byte[] arrayOfByte2 = new byte[32];
    if (((paramInt >= 0) && (paramInt < 64)) || ((paramInt >= 74) && (paramInt < 212)))
    {
      int j = paramInt;
      if (paramInt > 73) {
        j -= 10;
      }
      int k;
      int m;
      int n;
      if (paramInt < 74)
      {
        k = 23;
        m = 664054 + paramInt * k * 2;
        n = 657712 + paramInt * k;
      }
      else
      {
        k = 32;
        m = 667458 + (paramInt - 74) * k * 2;
        n = 659414 + (paramInt - 74) * k;
      }
      System.arraycopy(paramOptionFile.data, m, arrayOfByte1, 0, k * 2);
      System.arraycopy(paramOptionFile.data, n, arrayOfByte2, 0, k);
      int i, i1;
      for (i1 = 0; i1 < k; i1++)
      {
        i = 670512 + 628 * j + 6232 + i1;
        System.arraycopy(arrayOfByte1, paramOptionFile.toInt(paramOptionFile.data[i]) * 2, paramOptionFile.data, m + i1 * 2, 2);
        System.arraycopy(arrayOfByte2, paramOptionFile.toInt(paramOptionFile.data[i]), paramOptionFile.data, n + i1, 1);
      }
      if (paramBoolean) {
        for (int i2 = 0; i2 < 6; i2++)
        {
          i1 = 0;
          for (int i3 = 0; (i1 == 0) && (i3 < 32); i3++)
          {
            i = 670512 + 628 * j + 6232 + i3;
            if (paramOptionFile.data[i] == paramOptionFile.data[(676846 + 628 * j + i2)])
            {
              paramOptionFile.data[(676846 + 628 * j + i2)] = paramOptionFile.toByte(i3);
              i1 = 1;
            }
          }
        }
      }
      for (i1 = 0; i1 < 32; i1++)
      {
        i = 670512 + 628 * j + 6232 + i1;
        paramOptionFile.data[i] = paramOptionFile.toByte(i1);
      }
    }
  }
  
  public static void fixAll(OptionFile paramOptionFile)
  {
    for (int i = 0; i < 212; i++) {
      fixForm(paramOptionFile, i, true);
    }
  }
}


/* Location:              PESFan Editor 5.08.jar!/editor/FormFixer.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       0.7.1
 */
