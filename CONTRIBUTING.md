## How to contribute

The purpose of this page is to provide you with the minimum valuable information about how to contribute to the
project.

### How to reach us 🗞

- you can join the slack community [here](https://androiddevs.it/)
- and follow the open [discussion on the repository](https://github.com/androiddevelopersitalia/aditogether/discussions)

### Set up the project ⚙️

- Have a JDK >= 11 installed (even the bundled one with AS/IJ)
- Clone the repository
- Run `./scripts/tuner.sh` to set up the local tools used by this repository
- Make sure to install the required IDEA plugins, from the pop-up shown when the project is opened, to have a smoother experience in the IDE

### Branch & commits convention 📙

We have decided to use the [Conventional Commits](https://www.conventionalcommits.org/en/v1.0.0/), this means that there
is a specific naming convention for branch and about how to write the commits message.

For instance if you are working on a new feature the branch name must have the `feat/` prefix and then a reference about
the feature:

```
feat/1-adding-profile-section
```

The same thing for the commit message:

```shell
git commit -m "feat(profile): implemented profile section"
```

__Note:__ code reviews are ___mandatory___, if you are pushing your contribution, you must create a pull request.

### Lint & Static code analysis 🎨

We love to keep our code clean and consistent, so we have decided to integrate a plugin which validates it. The detekt
configuration is available [here](https://github.com/androiddevelopersitalia/aditogether/blob/main/detekt/config.yml).

__Note:__ if you have run `./scripts/tuner.sh`, all these checks are executed on pre-commit.