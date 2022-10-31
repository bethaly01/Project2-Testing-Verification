# CS 329 Project: Type Checking

This project is comprised of two parts. The objective is to implement [static type checking](https://en.wikipedia.org/wiki/Type_system#Static_type_checking) for a subset of Java and create tests for the type checker. Read through the assignment writeup found in this repository's root directory for the requirements of this project:

* [Part_1.md](Part_1.md)
* [Part_2.md](Part_2.md)

## Utility Classes

The [`TypeCheckUtils` class](src/main/java/edu/byu/cs329/typechecker/TypeCheckUtils.java) provides a bevy of helper methods in the form of `getName` and `getType` to get the name and type from `ASTNode` instances. Also, don't forget the `AstNodePropertiesUtils` class in the *project-utils* package. Always check them before digging too deep into the `ASTNode` type to find something. More than likely what is needed is already there. There are many examples in both `SymbolTableBuilder` and `TypeCheckBuilder` using `TypeCheckUtils`/`AstNodePropertiesUtils` and working with the different `ASTNode` types for this project. Be sure to look in these files first for examples and clarification to what matters (or does not matter) in any given `ASTNode` type.

### Adding the project-utils Library

The *project-utils* package is an incomplete set of utility code for creating *JDT DOM objects* from input Java files, getting information in and out of a JDT DOM, changing a JDT DOM, and standardizing errors and exceptions. It is a required dependency for the this repository (see line 34 in `pom.xml`).

The library must be added as a git submodule and installed in the local Maven cache for *type-checker* to build. Access to *project-utils* must also be granted to GitHub for the CI/CD pipeline to work on pushes to the **main** branch and pull-request.  

### Adding the Git Submodule

[This blog post](https://github.blog/2016-02-01-working-with-submodules/) gives a good overview of Git Submodules. [The official e-book for Git](https://git-scm.com/book/en/v2/Git-Tools-Submodules) also contains more instructions and details on how to use Git Submodules.

The below steps walk through adding *project-utils* to the *type-checker* repository as a Git Submodule. These steps should be completed by one person in the group on an appropriate feature branch and then merged into the **main** branch for the rest of the group pull down.

  0. Create and switch to an appropriate feature branch.
  1. Go to your *project-utils* repository created for the *constant-propagation* project and copy the URL for the repository.
  2. In the *type-checker* repository, run the following command, replacing the *repository-url* with the URL from step 2:

      `git submodule add <repository-url> project-utils`

  3. Initialize the submodule by running `git submodule update --init`.
  4. Run `git status`. You will notice that there are files now ready to be committed. These files store the metadata of the submodules, including what commit of each submodule is pulled.
  5. Commit the changes and push your feature branch to GitHub. If you are working with a partner, they can pull the branch and run the following to initialize the repository: `git submodule update --init`

### Maven install command

The *project-utils* library must be added to the local Maven repository for it to be visible to `mvn` to satisfy the build dependency. In the newly created, and initialized `project-utils` subfolder, run the `mvn install` command.

The `mvn install` command builds and names a jar file for the project according to the `pom.xml` file in `project-utils` and installs that jar in the local Maven cache.

**Anytime *project-utils* is updated, it must be installed with `mvn install` in order for the code in *type-checker* to see and use the changes.**

### Updating a submodule

Git treats a submodule as its own Git repository, so the code in the `project-utils` subfolder can be modified directly, and changes can be tracked with Git in mostly the same way as usual with a few caveats. The process is generalized in the following:

  * Make, test, commit, and push changes in the `project-utils` submodule as usual with `git` and JUnit. The `git` commands are relative to the folder in which they are run, so anything in the `project-utils` folder is relative to that local `git` repository cloned when the module was initialized. **Revisions must be push to the remote `project-utils` repository before the next part.**
  
  * In the *type-checker* repository, the folder containing `project-utils`, run  `git submodule update --remote --merge`. This command updates submodule metadata to use the newly committed version of `project-utils`. Commit these changes to ensure the project uses the correct version of *project-utils*. 

  * Push the changes on *type-checker* to the remote repository to share the project group. Remind everyone in the group to run `git submodule update` in the *type-checker* repository to get the pushed changes on the submodule.

Don't forget the `mvn install` in `project-utils` in order for the code in *type-checker* to see and use the changes.**

### Adding the Personal Access Token for CI/CD

The GitHub CI/CD for *type-checker* must have access to the named *project-utils* repository in the submodule. The access is enabled with a *personal access token* (PAT). Creating and adding the PAT is a one time process.

1. Log in to GitHub and go to https://github.com/settings/tokens
2. Click on *"Generate new token"*. It may require the GitHub password again.
3. Enter a note for this token describing its purpose: *"CS 329 Type Checker Project GitHub Access"*.
4. Change the expiration date to 90 days.
5. Under scopes, select the *"repo"* scope to allow this token to have full control of private repositories.
6. Scroll down, then click on *"Generate token"*.
7. Copy the PAT. Do not close the page until you have recorded the PAT (see next steps) because once the page is closed, there is no way to get a copy of the PAT again.

After generating the PAT, go to the *type-checker* repository on GitHub to add the token to that repository as follows:

1. Open the GitHub repo for *type-checker* then click on the *Settings* tab.
2. On the left menu, select *"Secrets"*, then click on *"New repository secret"*.
3. Insert `ACCESS_TOKEN` as the secret's name. In the [GitHub workflow file](.github/workflows/maven.yml), this name is referenced to give GitHub Actions access to the *project-utils* private repository.
4. Paste the PAT in the *"value"* section, then click on *"Add secret"*. After doing so, GitHub stores the PAT securely and it is no longer human readable.
# Project2-Testing-Verification
