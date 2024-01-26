# better-wurst
Enhanced some modules of Wurst.

![截图 2024-01-23 01-14-40](https://github.com/xbound/better-wurst/assets/54498130/d250f818-40af-406d-b9f9-1576f1746e37)
![截图 2024-01-22 04-15-05](https://github.com/xbound/better-wurst/assets/54498130/3aea35ea-380f-4ef4-944b-74d69481379b)
![截图 2024-01-22 04-15-35](https://github.com/xbound/better-wurst/assets/54498130/02b28b88-dd28-4ae0-9f11-77caefb50856)
![截图 2024-01-22 04-28-39](https://github.com/xbound/better-wurst/assets/54498130/9a98ff61-1dc6-4f83-99f8-d5871289dfc6)
![截图 2024-01-25 18-54-37](https://github.com/xbound/better-wurst/assets/54498130/5d242231-dc1b-4806-a1db-ef64cd1e4c8d)
![截图 2024-01-25 18-54-58](https://github.com/xbound/better-wurst/assets/54498130/32b1b1e7-915a-4e6b-9832-f08908adb10f)
![截图 2024-01-25 18-57-18](https://github.com/xbound/better-wurst/assets/54498130/2bdca89d-5967-4eaf-9198-81371110d33a)

Other changes:

add command .renamer can easily rename items repeat-ly (like aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa)

fixed command .potion not work

command .help can print all with the argument 0

command .enchant can list all enchantments or specific one and  level

hack Excavator can use /fill to clear a large area if player has the OP permission,even if the area contains more than 32768 blocks.

better hack CrashChest (more difficult for server to detect)

better hack Freecam ,flys like creative more

fixed hack ProphuntEsp not work

Add hack GhastSimulator,allows player to shoot fireball of Ghast or Blaze and (black/blue) skull  of Wither and more.OP is required.

How to use:

(Release is available if you do not want to build.)

Select a branch except main.

Download the source code from https://github.com/Wurst-Imperium/Wurst7 with a corrected branch.

Replace files in src folder (cp -rf)

Build
```
  ./gradlew genSources
  ./gradlew build
```
