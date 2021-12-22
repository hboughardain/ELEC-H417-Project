# Communication networks: protocols and architectures project (ELEC-H417)

Mingle - _Basic chat application enabling communication between multiple users_

# Usage

Java version used:
- Java 1.8.0_292 (Amazon Corretto 8)

Used libraries:
- JavaFX 8.0.202-ea (included in Java 1.8.0_292)
- Apache Commons Lang 3.12.0 (included in project files as `.jar` file)

IDE used:
- IntelliJ 2021.2.3

## Compilation

Compiling the program through IntelliJ:
- Open the folder cloned by GitHub on your PC as an IntelliJ project.
- Go to **File** > **Project Structure** > **Project Settings** > **Project**.
- Define **Project SDK** as `corretto-1.8` ; this version can directly be downloaded via IntelliJ by selecting **Add SDK** > **Download JDK**, and then choosing the following fields:
  - Version : `1.8`.
  - Vendor : `Amazon Corretto 1.8.0_292`.
- Define **Project language level** as `SDK default (8 - Lambdas, type annotations etc.)`.
- Click on **Add Configuration**, choose **Application** among the proposed choices, and define **Main Class** as `ClientMain`.
- Repeat the previous step by instead adding a new configuration for the `ServerMain` class.

## Execution 

Executing the program through IntelliJ:
- Compile the program through IntelliJ as seen in the previous section **Compilation**.
- Click on **Run '`Configuration`'**, where `Configuration` corresponds to the name given to the created configurations; this has to be done for `ServerMain` and `ClientMain`, in that order.

# Configuration

No major difference exists between the configurations on the Windows, Linux and MacOS operating systems.

# Functioning

- Create an account, if not done already.
- Log in using your credentials.
- Click on the user with which you would like to talk, and start sending them a message by typing in the text field and clicking **Send**.

# License

- none
