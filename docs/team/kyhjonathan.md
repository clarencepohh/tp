# kyhjonathan - Project Portfolio Page

## Overview
Given below are my contributions to the project.
**New Feature**: Added the ability to save and read tasks to and from a local file.
* What it does: Allows users to save their tasks to a local txt file and read tasks from a local txt file.
* Justification: This feature allows users to save their tasks and read them later, even after the application is closed.
* Highlights: This feature is implemented using the `Storage` class, which is responsible for reading and writing tasks and handling any parsing logic for storage.

**Feature in development**: Exporting and importing of files in ical format.
* What it does: Allows users to export their tasks to an ical file and import tasks from an ical file.
* Justification: This feature allows users to share their tasks with others and import tasks from other applications.
* Highlights: This feature is implemented using the 'icshandler' class, however it is still in development and not yet implemented into the main application.

**Enhancements to existing features**:
* Updated the `TaskList` class to include the ability to add tasks from a list of tasks.
* Refactored taskManager methods to be more modular and easier to read.
* Handled exception handling for parsing of DateTime

**Tools**
* Used ical4j library to handle ical file format.
* Update build.gradle file to include relevant dependencies for ical4j library.