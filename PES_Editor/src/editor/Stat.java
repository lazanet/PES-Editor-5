package editor;

public class Stat
{
  private OptionFile of;
  public int type;
  public int offSet;
  public int shift;
  public int mask;
  public String name;
  public static String[] nation = { "Austria", "Belgium", "Bulgaria", "Croatia", "Czech Republic", "Denmark", "England", "Finland", "France", "Germany", "Greece", "Hungary", "Ireland", "Italy", "Latvia", "Netherlands", "Northern Ireland", "Norway", "Poland", "Portugal", "Romania", "Russia", "Scotland", "Serbia and Montenegro", "Slovakia", "Slovenia", "Spain", "Sweden", "Switzerland", "Turkey", "Ukraine", "Wales", "Cameroon", "Cote d'Ivoire", "Morocco", "Nigeria", "Senegal", "South Africa", "Tunisia", "Costa Rica", "Mexico", "USA", "Argentina", "Brazil", "Chile", "Colombia", "Ecuador", "Paraguay", "Peru", "Uruguay", "Venezuela", "China", "Iran", "Japan", "Saudi Arabia", "South Korea", "Australia", "Albania", "Armenia", "Belarus", "Bosnia and Herzegovina", "Cyprus", "Georgia", "Estonia", "Faroe Islands", "Iceland", "Israel", "Lithuania", "Luxembourg", "Macedonia", "Moldova", "Algeria", "Angola", "Burkina Faso", "Cape Verde", "Congo", "DR Congo", "Egypt", "Equatorial Guinea", "Gabon", "Gambia", "Ghana", "Guinea", "Guinea-Bissau", "Liberia", "Libya", "Mali", "Mauritius", "Mozambique", "Namibia", "Sierra Leone", "Togo", "Zambia", "Zimbabwe", "Canada", "Grenada", "Guadeloupe", "Guatemala", "Honduras", "Jamaica", "Martinique", "Netherlands Antilles", "Panama", "Trinidad and Tobago", "Bolivia", "Guyana", "Uzbekistan", "New Zealand", "Free Nationality" };
  static String[] modFoot = { "R", "L" };
  static String[] modInjury = { "C", "B", "A" };
  static String[] modFK = { "A", "B", "C", "D", "E", "F", "G", "H" };
  static String[] mod18 = { "1", "2", "3", "4", "5", "6", "7", "8" };
  
  public Stat(OptionFile paramOptionFile, int paramInt1, int paramInt2, int paramInt3, int paramInt4, String paramString)
  {
    this.of = paramOptionFile;
    this.type = paramInt1;
    this.offSet = paramInt2;
    this.shift = paramInt3;
    this.mask = paramInt4;
    this.name = paramString;
  }
  
  public int getValue(int paramInt)
  {
    int i = 36920 + paramInt * 124 + this.offSet;
    if (paramInt > 4999) {
      i = 14092 + (paramInt - 32768) * 124 + this.offSet;
    }
    int j = this.of.toInt(this.of.data[i]) << 8 | this.of.toInt(this.of.data[(i - 1)]);
    j >>>= this.shift;
    j &= this.mask;
    return j;
  }
  
  public void setValue(int paramInt1, int paramInt2)
  {
    int i = 36920 + paramInt1 * 124 + this.offSet;
    if (paramInt1 > 4999) {
      i = 14092 + (paramInt1 - 32768) * 124 + this.offSet;
    }
    int j = this.of.toInt(this.of.data[i]) << 8 | this.of.toInt(this.of.data[(i - 1)]);
    int k = 0xFFFF & (this.mask << this.shift ^ 0xFFFFFFFF);
    j &= k;
    paramInt2 &= this.mask;
    paramInt2 <<= this.shift;
    paramInt2 = j | paramInt2;
    this.of.data[(i - 1)] = this.of.toByte(paramInt2 & 0xFF);
    this.of.data[i] = this.of.toByte(paramInt2 >>> 8);
  }
  
  public void setValue(int paramInt, String paramString)
  {
    int i = 0;
    try
    {
      if (this.type == 0) {
        i = new Integer(paramString).intValue();
      }
      if (this.type == 1) {
        i = new Integer(paramString).intValue() - 148;
      }
      if (this.type == 2) {
        i = new Integer(paramString).intValue() - 15;
      }
      int j;
      if (this.type == 3) {
        for (j = 0; j < nation.length; j++) {
          if (paramString.equals(nation[j])) {
            i = j;
          }
        }
      }
      if (this.type == 4) {
        for (j = 0; j < modFoot.length; j++) {
          if (paramString.equals(modFoot[j])) {
            i = j;
          }
        }
      }
      if (this.type == 5) {
        i = new Integer(paramString).intValue() - 1;
      }
      if (this.type == 6) {
        for (j = 0; j < modInjury.length; j++) {
          if (paramString.equals(modInjury[j])) {
            i = j;
          }
        }
      }
      if (this.type == 7) {
        for (j = 0; j < modFK.length; j++) {
          if (paramString.equals(modFK[j])) {
            i = j;
          }
        }
      }
      setValue(paramInt, i);
    }
    catch (NumberFormatException localNumberFormatException) {}
  }
  
  public String getString(int paramInt)
  {
    String str = "";
    int i = getValue(paramInt);
    if (this.type == 0) {
      str = String.valueOf(i);
    }
    if (this.type == 1) {
      str = String.valueOf(i + 148);
    }
    if (this.type == 2) {
      str = String.valueOf(i + 15);
    }
    if (this.type == 3) {
      if ((i >= 0) && (i < nation.length)) {
        str = nation[i];
      } else {
        str = str = String.valueOf(i) + "?";
      }
    }
    if (this.type == 4)
    {
      str = "R";
      if (i == 1) {
        str = "L";
      }
    }
    if (this.type == 5) {
      str = String.valueOf(i + 1);
    }
    if (this.type == 6)
    {
      str = "A";
      if (i == 1) {
        str = "B";
      }
      if (i == 0) {
        str = "C";
      }
    }
    if (this.type == 7) {
      str = modFK[i];
    }
    return str;
  }
}


/* Location:              PESFan Editor 5.08.jar!/editor/Stat.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       0.7.1
 */
