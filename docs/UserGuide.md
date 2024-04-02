# CLI-nton Task Management User Guide

## Introduction

Welcome to CLI-nton, a powerful task management application designed to help you stay organized and productive. With a simple command-line interface, CLI-nton makes it easy to manage your tasks, whether they're todos, deadlines, or events.

This user guide provides comprehensive instructions on how to use CLI-nton, from getting started to using advanced features. Whether you're a new user or an experienced one, this guide is designed to help you get the most out of CLI-nton.

Read on to learn how to manage your tasks efficiently with CLI-nton!

## Quick Start

To quickly get started with CLI-nton, follow these simple steps:

1. Ensure that you have Java 11 or above installed on your system.
2. Download the latest version of `CLI-nton` from [here](http://link.to/clinton).
3. Open a command terminal and navigate to the directory where you downloaded CLI-nton.
4. Run the application using the command `java -jar clinton.jar`.
5. You're all set to start managing your tasks efficiently!

## Features

CLI-nton offers a variety of features to streamline your task management process:


### Moving to Next Week or Month: `next`

Moves to the next week or month view.

Example of usage:

```
next
```

### Moving to Previous Week or Month: `prev`

Moves to the previous week or month view.

Example of usage:

```
prev
```

### Updating a Task Description: `update`

Updates a task description.

Format: `update <day> <taskIndex> <newDescription>`

Example of usage:

```
update Monday 1 Review User Guide and make edits
```

### Adding a New Task: `add`

Adds a new task.

Format: `add <day> <taskType> <taskDescription>`

Example of usage:

```
add Tuesday meeting Discuss project progress
```

### Deleting a Task: `delete`

Deletes a task.

Format: `delete, <day>, <taskIndex>`
- Deletes the task at the specified index on the given day
- Shows an error message if the task index does not exist

Example of usage:
- Deletes task 2 on Wednesday

```
//deletes task 2 on day 3
delete, 3, 2

//deletes task 1 on day 5
delete, 5, 1
```
- Shows error message if task does not exist

```
//attempts to delete task 1 on day 5, but the task does not exist
delete, 5, 1
```
Output: 
![img.png](img.png)

### Marking a Task as Complete or Incomplete: `mark`

Marks a task as complete or not complete.

Format: `mark, <day>, <taskIndex>`
- Marks task as complete if it is incomplete
- Marks task as incomplete if it is complete

Example of usage:

```
// marks task 3 on day 2 as complete
mark,2,3

// marks task 1 on day 4 as incomplete
mark,4,1
```

### Setting Priority Level for a Task: `priority`

Sets priority level for a task.

Format: `priority, <day>, <taskIndex>, <priority>`
- Priority levels: `H` (high), `M` (medium), `L` (low)
- Default priority level: `L`
- Setting priority level will update any existing priority level for the task to the new level

Example of usage:

```
// sets priority level HIGH for task 1 on day 5
priority,5,1,H

// sets priority level MEDIUM for task 2 on day 3
priority,3,2,M
```

### Switching to Month View: `month`

Switches to the month view.

Example of usage:

```
month
```

### Switching to Week View: `week`

Switches to the week view.

Example of usage:

```
week
```

### Quitting the Application: `quit`

Exits the calendar application.

Example of usage:

```
quit
```

## FAQ

**Q**: How do I transfer my data to another computer?

**A**: You can transfer your data to another computer by copying the data file saved in the CLI-nton application directory. The data file is typically named `clintonData.txt` and contains all your tasks. Simply copy this file to the same location on the other computer to transfer your tasks.

## Command Summary

For a quick reference, here's a summary of available commands:

- Add todo `todo n/TODO_NAME d/DEADLINE`
- Move to next week or month `next`
- Move to previous week or month `prev`
- Update task description `update <day> <taskIndex> <newDescription>`
- Add new task `add <day> <taskType> <taskDescription>`
- Delete task `delete <day> <taskIndex>`
- Mark task as complete or not complete `mark <day> <taskIndex>`
- Set priority level for task `priority <day> <taskIndex> <priority>`
- Switch to month view `month`
- Switch to week view `week`
- Quit the application `quit`

Start managing your tasks efficiently with CLI-nton!