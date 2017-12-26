# PES-Editor-5
###### Fork of purplehaze's excellent PESFan PES5/WE9 Editor with PSD import (and ugliest code you will ever see)
\
Based on decompiled [PESFan editor 5.08](http://www.purplehaze.eclipse.co.uk/downloads/PESFan_Editor_5.08.zip) from [purplehaze](http://www.purplehaze.eclipse.co.uk/downloads.html) - as I don't have enough spare time to rebase it from PES2009 source as I did for PES6. Since this code was litteraly taken from [`jd`](http://jd.benow.ca/) output, it looks horrible and barely works (it had several hundred errors that had to be manualy fixed). If you with to do something useful with it, you should look into [PES-Editor-6 source](https://github.com/lazanet/PES-Editor-6), and try to incorporate PES5 offsets in it.

I have no idea if Eclipse would build it oob. I used ` sh build.sh ` and ` sh run.sh ` on Ubuntu with base OpenJDK, and then packed jar manually.  

### New features
* PSD search (based on [pesstatsdatabase-API](https://github.com/lazanet/pesstatsdatabase-API) of mine) - at Tools->Get PSD Stats
* PSD stat paste (at player edit dialog)

### Binary builds
[HERE!](https://github.com/lazanet/PES-Editor-5/releases/)
 
### TODO
Not touching this code again even with a 10-foot pole.

### Contributing
Feel free to fork this repo if you have courage, and send pull requests.
  
#### Copyright part:
 * Copyright 2008-9 Compulsion
 * <pes_compulsion@yahoo.co.uk>
 * <http://www.purplehaze.eclipse.co.uk/>
 * <http://uk.geocities.com/pes_compulsion/>
 *
 * PES Editor is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * PES Editor is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with PES Editor.  If not, see <http://www.gnu.org/licenses/>.
 *
