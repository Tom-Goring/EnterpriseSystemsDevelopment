Repo documentation should go here in the future, but for now - a placeholder.

### Steps to run in Intellij

1. Clone repo to local directory
2. Import folder into Intellij
3. Create run config for glassfish server:
    1. Download glassfish - I'm using Glassfish 5.1 which seems to work for me
    2. Add/Edit Configurations
    3. Add local glassfish server - you may have to click configure and set the path of where you put the glassfish directory.
    4. Add `domain1` as the domain
    5. Move to the deployment tab, and add smartcare-web-interface: war-exploded to be deployed
    6. Edit the update and frame deactivations to your preference, I like update to redeploy classes and resources, and frame deactivation to redeploy resources only.
    7. The glassfish server should now be deployable.

### Steps to run in Netbeans

1. Clone repo to local directory
2. Open project (cloned directory) in netbeans
3. Click Run Project (F6)
4. Select deployment server (it should prompt you to select one)
5. It runs like magic (one place where netbeans actually seems to be better)

### Connecting to a database with JDBC 
Create a file in the `resources` directory called `config.properties`, and then add a line reading:
    
    jdbc_database_url=<YOUR_DERBY_DB_URL_HERE>

Here is mine for an example:

    jdbc_database_url=jdbc:derby:C:/Users/tomgo/IdeaProjects/ESD/DerbyDB
    
A typical result of getting the URL wrong is "Suitable Driver Not Found".
The reason you have to add this is so we can all add our own config files seperate of VCS, so merges don't break the URL.

### Gotchas

- I seem to have trouble with the root/source directory config in Intellij if I clone it through Intellij's built in function, it seems to work better to clone it on the command line and then explictly "Open" the cloned directory with the inbuilt `Open...` option under `File`
- Check your DerbyDB database URL is correct. Should read like: `jdbc_database_url=jdbc:derby:C:/Users/tomgo/IdeaProjects/ESD/DerbyDB`
- When creating a DerbyDB database instance in Intellij, make sure the directory on the end of the File path you provide DOES NOT EXIST. Else you will get an error but no error message explaining why.

### Git Workflow

1. Make sure local repo is up to date with remote with `git fetch` - some IDEs run this periodically anyway. `git pull` will merge remote changes into your local repo if there are any, `fetch` will just download the data.
2. Create new local branch with `git checkout -b <branch_name>`. This branch will not be uploaded to GitHub (i.e. remote) until we push it there.
3. Do some work
4. Stage your changed files with `git add <file>`, `git add .` will stage all stageable files.
5. Create a commit with `git commit`. This snapshots the current state of your staged files.
6. Now you can keep working and continue to create more commits, or you can upload your new work to the remote repo (which in this case is GitHub), by doing `git push`. 
7. Now you can make a PR (pull request) from your branch to master (our main branch), by using GitHub's PR functionality. If the branch you are targetting has had commits that you do not have on your branch, you will have to rebase your branch onto master. You can do this to your branch locally by doing:
    ```
   git fetch origin
   git rebase origin/master
   ```
   Check that your local branch has the desired commits from master by doing `git log` and inspecting the history. When you are satisfied that your branch is good, do a `git push -f` to force push the new history to your remote branch. The PR should now be mergeable without any hairy merge commits.
   
8. Some other people should come review your work and hopefully leave some suggestions/comments on what needs to be changed before a merge. Complete this work and push it to your remote branch, and the you can squash these new commits back into the old ones and `git push -f` to keep the history clean.
9. The PR should then be merged and everybody should be happy