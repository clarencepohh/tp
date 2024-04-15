# Nicholas Tan Yun Yu - Project Portfolio Page

## Overview
**About CLI-nton**: <br>
CLI-nton is a desktop application for managing tasks, optimized for use via a Command Line Interface (CLI). It is a Java application that uses a GUI, but the primary way to interact with it is by typing commands into a CLI. If you can type fast, CLI-nton can get your task management done faster than traditional GUI apps.

### Summary of Contributions
- [**Check out my code here!**](https://nus-cs2113-ay2324s2.github.io/tp-dashboard/?search=nicholastanyy&breakdown=true&sort=groupTitle%20dsc&sortWithin=title&since=2024-02-23&timeframe=commit&mergegroup=&groupSelect=groupByRepos&checkedFileTypes=docs~functional-code~test-code~other&tabOpen=true&tabType=authorship&tabAuthor=NicholasTanYY&tabRepo=AY2324S2-CS2113-W13-2%2Ftp%5Bmaster%5D&authorshipIsMergeGroup=false&authorshipFileTypes=docs~functional-code~test-code~other&authorshipIsBinaryFileTypeChecked=false&authorshipIsIgnoredFilesChecked=false)

- **Enhancements implemented**:
  - Enhanced the overall UI for the application, including the logic behind printing table formats and tasks in the week and month view
  - Implemented feature to mark and set a priority to the tasks in the task list
  - Modified `runtest.sh` to build the project and run tests in one command
  - Implemented `changeExpected.sh` to automatically update expected output files for tests
  - Implemented an avatar which is displayed whenever the application starts and a command is ran, to give the application more personality

- **Contributions to the UG**:
  - Added documentation for the `mark` and `priority` command
  - Wrote the FAQ page, especially for known issues
- **Contributions to the DG**:
    - Added sequence diagram for printing a week's view
    - Added class diagram for the `UiRenderer` class
    - Wrote documentation for the methods in the `UiRenderer` class
- **Contributions to team-based tasks**:
    - Helped to debug and fix issues that my teammates faced
    - Helped check for code quality and ensure that the coding standards are met
    - Helped to review most PRs and provide feedback
    - Helped to manage the issue tracker and assign issues to the team members
    - Helped to manage the release process and ensure that the release is smooth
    - Helped to manage the milestone setting
    - Helped set meeting agenda and manage the flow of the meeting
    - Extensively reviewed teammates PR to reduce the number of bugs in the code before it is merged
    - Incorporated gradle checks in text-ui-test so teammates can run all tests (JUnit and Ui) using one command
- **Review/mentoring contributions**:
    - [Link to PR code review contributions](https://github.com/AY2324S2-CS2113-W13-2/tp/issues?q=reviewed-by%3ANicholasTanYY+)
- **A feature I had particular difficulty implementing** <br>
The logic behind printing the tasks using wrapped lines in the week view was challenging for me. This implementation is encapsulated in the  `printTasksInWeek` method and its nested components in the `UiRenderer` class.
<br><br>
Before this change was made, the tasks that were printed were truncated in a single line, meaning that if the task description occupied more space than the allocated box width (which is true for all tasks), the description would be cut off. This was not ideal as it made the task description unreadable.
- **Other enhancements I attempted**: <br>
I implemented a feature to make the application neater by centralising all UI components (Avatar, Avatar words, table for week view and month view etc).
<br><br>
After this, I also implemented a feature that changed the size of the avatar printed based on the size of the terminal.
<br><br>
These were done by having a class which calculates the dimensions of the terminal. This code is not included in the unused folder as it is a code snippet modified from something I found online. The code uses an intelligent way of finding terminal dimensions, using ANSI codes to control the position of the cursor. Then using a TerminalController class (in unused code segment), I was able to send keyboard inputs to the terminal to fix some issues with the outputs to the terminal that arose during the calculation of terminal dimensions.
<br><br>
I successfully managed to centralise some components and fit corresponding avatar sizes but ran into major issues when trying to run the text-ui-tests. This feature took a lot of effort to implement. I had to abandon this feature as I was worried that it could cause more problems to other implementations in the future, like not working in other terminal types or conflicting with other Ui implementations.