# Balatro-Mod-Fetcher
A short, bundled py file that imports mods for belatro, and can search subfolders.


You need to have Python installed for it to work, then you need to make a mods.txt with the github repositories you want to clone from in it. This project also works with subfolders like JensBalatroCollection, and will extract them to the main mods folder.
You will need to have run the game at least once and this will only work on windows for now. if you want me to add support for your os, include the store you got the game from and where the mods folder is. You need to place each github url on a new line.

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

Just a warning, some of the JensBalatroCollection stuff is incompatable with my_balatro_mods, so you will need to delete Betmma_Abilities and Betmma_Spells manually for your game to launch.

This was inspired from the original mods fetcher from the cryptid github, but I was annoyed that it didnt search subfolders so I made this.

Btw, when updating you will have to delete the things you want updated in the mods folder, as trying to delete them in python runs into some permission stuff that I couldnt get working
