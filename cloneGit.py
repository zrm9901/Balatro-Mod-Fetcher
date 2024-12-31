from git import Repo
import os
import shutil
file = open('mods.txt')
x = file.read().splitlines()
for y in x:
    path = os.getenv('APPDATA') + "\\Balatro\\Mods"
    folder = y.split("/")
    path = path +"\\" + folder[-1]
    print(folder)
    print(path)

    if os.path.exists(path):
        print("path exists, skipping")
    else:
        Repo.clone_from(y, path, depth=1)
        if not (os.path.exists(path + "\\" + "assets")):
            contents = os.listdir(path)
            print(contents)
            for j in contents:
                if j != '.git' and j != '.github' and j != 'README.md':
                    print(j)
                    shutil.move((path + "\\" + j), os.getenv('APPDATA') + "\\Balatro\\Mods")