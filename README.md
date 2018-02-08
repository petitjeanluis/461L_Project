# 461L_Project

ADDING PROJECT TO ECLIPSE

1. Download git bash.
2. In your eclipse you eclipse workspace folder add a folder for this project, I suggest naming it "461L_Project".
3. Open git bash and navigate to the folder created in step 2. Alternatively, you can go to the file in your file browser, right-click on the file and then press "Git Bash Here".
4. enter "git init"
5. enter "git remote add main-remote https://github.com/petitjeanluis/461L_Project.git"
6. enter "git pull main-remote master", at this point you will have downloaded the master branch from the project repo
7. Open project in eclipse: File -> Import -> General -> Existing Projects Into Workspace -> browse the project folder -> Finish
*if you do it this way, "main-remote" will be the name of the remote created

OR

1. Download git bash.
2. Open git bash and navigate to your exlipse workspave. Alternatively, you can go to the file in your file browser, right-click on the file and then press "Git Bash Here".
3. enter "git clone https://github.com/petitjeanluis/461L_Project.git"
4. Open project in eclipse: File -> Import -> General -> Existing Projects Into Workspace -> browse the project folder -> Finish
*if you do it this way, "origin" will be the name of the remote created

TO UPLOAD STUFF
1. enter "git add ."
2. enter "git commit . -m"your commit message here""
3. enter "git push {remote-name} {branch}"

TO DOWNLOAD STUFF
1. enter "git pull {remote name} {branch}"
