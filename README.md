#Important <br>
I doubt anyone is going to see this, but this wont work with cryptid for a bit because the newest steammoded woont work with cryprtid because of some changes. just remove steammoded from your mods.txt and download the archive manually and install it with the rest of your mods<br> 
https://github.com/Steamodded/smods/archive/refs/tags/old-calc.zip


#Features <br>
I completely redid the code after realizing that it would be a pain to integrate new systems, so I redid it and I realized that installing lovely was an issue, so I made it install automatically when you run the jar. 

A short, bundled jar file that imports mods for belatro, and can search subfolders.

(You will need to have run the game at least once after installing lovely with the script and have java installed)

simply create a file called mods.txt in the directory that holds your jar file, then run it in the command line with java -jar clone1-1.jar 
After running it once you will need to go into steam and change launch options to include "WINEDLLOVERRIDES="version=n,b" %command%." (from lovely)

Example mods.txt: <br>
https://github.com/Steamopollys/Steamodded <br>
https://github.com/MathIsFun0/Talisman <br>
https://github.com/MathIsFun0/Cryptid <br>
https://github.com/SDM0/SDM_0-s-Stuff <br>
https://github.com/larswijn/CardSleeves <br>
https://github.com/Eremel/Galdur <br>
https://github.com/SleepyG11/HandyBalatro <br>
https://github.com/jenwalter666/JensBalatroCollection <br>
https://github.com/betmma/my_balatro_mods

If you want support for your os, give me your mods path and platform (and preferably store) so I can add it

If you want to compile it yourself, you need to have vscode installed, download the source, then unzip it and open the folder in vscode, you then can either run the App.java file in src or run "mvn clean package" to build the project, the compiled jar will be in target

#To-Do: <br>
make it avaliable for mac. (shouldnt be that hard)

#Credits <br>
ethangreen-dev: Lovely, which is automatically downloaded when the program is first run. <br>
Project666: made the first update script and inspired me to make one for linux. <br>
candycaneannihalator(etherware2): helped test out stuff on debian. <br>

