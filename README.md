# JPad [Beta]
JPad is a simple, lightweight and cross-platform text editor similar to Notepad with built-in syntax highlighting for over 50 languages. It can also be used as a simple note-taking app with plaintext.

**Note about releases:** For now, only major versions ( left-most number changed --> `x.x.x` ) will be released with installers.

**Jump to**
- [Features](https://github.com/TisLeo/JPad/#features)
- [Planned Features](https://github.com/TisLeo/JPad/#planned-features)
- [Building](https://github.com/TisLeo/JPad/#building)
- [Installation](https://github.com/TisLeo/JPad/#installation)
- [Tech Stack](https://github.com/TisLeo/JPad/#tech-stack)
- [Issues](https://github.com/TisLeo/JPad/#issues)
- [Contribution](https://github.com/TisLeo/JPad/#contribution)
- [Donate](https://github.com/TisLeo/JPad/#donate)
- [Gallery](https://github.com/TisLeo/JPad/#gallery)

## Features
- App and editor themes
- Syntax highlighting for 50+ languages
- Code folding
- Text-wrapping
- Cross-platform

## Planned Features
- Localisation
- Custom editor themes
- Unit tests

## Building
Prerequisites:
- Java 17
- Maven 4.0.0

**Steps**
1. Clone this repo: `git clone https://github.com/tisleo/Jpad`
2. cd to the location of the project, e.g. `cd jpad`
3. Run `mvn clean install`

## Installation
To install JPad, go to the [releases](https://github.com/TisLeo/JPad/releases) page.
### Which do I choose?
- `JPad.jar`: The raw .jar file for those who want something quick and simple. Note: **you must have at least [Java 17](https://www.oracle.com/uk/java/technologies/downloads/#java17) downloaded for the program to work properly**.
- `JPad-v1.0.0-beta_setup.exe`: The Windows exe installer. *This comes with Java 17* - works out of the box.
- `JPad-v1.0.0-beta_setup.dmg`: The macOS app installer. *This comes with Java 17* - works out of the box.

⚠️**DISCLAIMER:** _But, why are the `.exe` and `.app` not signed?_ For the simple reason that it's too expensive to maintain a credible certificate for both platforms right now. **This _may_ cause your antivirus/OS to flag or quarantine the app**. The `.jar` is self-signed.
 - if you don't trust the installers, you can download Java17 and use the `.jar`, or even clone/fork the repo and build it yourself with Maven and go from there. Thanks for understanding.

## Tech Stack
- Language: Java
- Framework: Swing
- Dependency: [RSyntaxTextArea](https://github.com/bobbylight/RSyntaxTextArea/tree/master)
- Build: [Maven](https://maven.apache.org/)
- Packaging: [Launch4J](https://launch4j.sourceforge.net/), [Inno Setup](https://jrsoftware.org/isinfo.php), [jpackage](https://docs.oracle.com/en/java/javase/17/docs/specs/man/jpackage.html)

## Issues
If you find any bugs, open an [issue](https://github.com/TisLeo/JPad/issues) and I will get back to you as soon as possible. If your issue is with the actual text editor area, it's likely from the [RSyntaxTextArea issues](https://github.com/bobbylight/RSyntaxTextArea/issues) (so please refrain from opening issues related to it); I will continue to update JPad with every big release of RSyntaxArea to make sure it works as intended.

## Contribution
See [CONTRIBUTING.md](https://github.com/TisLeo/JPad/blob/main/CONTRIBUTING.md) for more info.

## Donate
Donations are greatly appreciated and are completely optional! They will help me continue to work on projects such as this one :) [Donate here (buy me a coffee)](https://www.buymeacoffee.com/tisleo).

## Gallery
![Editor Themes](https://github.com/TisLeo/JPad/blob/main/gallery/Editor%20Themes.png)
![App Themes](https://github.com/TisLeo/JPad/blob/main/gallery/App%20Themes.png)
![Zoom](https://github.com/TisLeo/JPad/blob/main/gallery/Zoom.png)
![Fonts](https://github.com/TisLeo/JPad/blob/main/gallery/Fonts.png)
