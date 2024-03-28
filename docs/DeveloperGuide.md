# Developer Guide

## Acknowledgements

{list here sources of all reused/adapted ideas, code, documentation, and third-party libraries -- include links to the original source as well}

## Design & implementation
## UiRenderer Component
### API: [UiRenderer.java](https://github.com/AY2324S2-CS2113-W13-2/tp/blob/master/src/main/java/ui/UiRenderer.java)

### Overview: <br> 
The UiRenderer component is responsible for rendering the user interface. It is used to display messages, week views as well as month views to the user.

### How it works:
1. When week view is requested, the UiRenderer component will render the week view.
2. This is done by printing the table in ASCII art format, with the days of the week as columns.
3. The week view will display the tasks for the week, with the tasks for each day displayed in the respective columns.
4. The UiRenderer component will also display the current date at the top of the week view.
5. Similarly, when month view is requested, the UiRenderer component will render the month view.

### printWeekHeader Method
`printWeekHeader` Method

The `printWeekHeader` method is responsible for rendering the header section of the week view, including the names of the days and optionally the dates.

#### Method signature:
```
public static void printWeekHeader(LocalDate startOfView, DateTimeFormatter dateFormatter, boolean isMonthView)
```

#### Parameters
- startOfView: The starting date of the week view.
- dateFormatter: Formatter for displaying dates.
- isMonthView: A boolean flag indicating if the month view is being rendered.

#### Method Functionality
- Prints a horizontal divider to delineate the start of the header.
- Displays the names of the days as column headers.
- If not in month view, prints the dates for the respective week.

### printWeekBody Method
The printWeekBody method displays the body of the week view, showing tasks for each day in their respective columns.

#### Method Signature
```
public static void printWeekBody(LocalDate startOfWeek, DateTimeFormatter dateFormatter, TaskManager taskManager)
```

#### Parameters
- startOfWeek: The starting date of the week for which tasks are displayed.
- dateFormatter: Formatter for displaying dates.
- taskManager: The TaskManager instance managing tasks.

#### Method Functionality
- Determines the maximum number of tasks in any day of the week to set the row count.
- Iterates through each day, displaying tasks or placeholders if there are fewer tasks on certain days.

### Month View Rendering
The month view utilizes the printWeekHeader method with the isMonthView parameter set to true, limiting the display to only include week headers without individual tasks.

### Displaying Help and User Commands
`printHelp` Method

The printHelp method provides users with a list of available commands and their descriptions, aiding in navigation and task management.

#### Method Signature
```
public static void printHelp()
```

#### Method Functionality
- Prints a structured list of commands and their purposes within the user interface.
- Highlights key functionalities like navigating views, adding, and updating tasks.

{Describe the design and implementation of the product. Use UML diagrams and short code snippets where applicable.}


## Product scope
### Target user profile

{Describe the target user profile}

### Value proposition

{Describe the value proposition: what problem does it solve?}

## User Stories

|Version| As a ... | I want to ... | So that I can ...|
|--------|----------|---------------|------------------|
|v1.0|new user|see usage instructions|refer to them when I forget how to use the application|
|v2.0|user|find a to-do item by name|locate a to-do without having to go through the entire list|

## Non-Functional Requirements

{Give non-functional requirements}

## Glossary

* *glossary item* - Definition

## Instructions for manual testing

{Give instructions on how to do a manual product testing e.g., how to load sample data to be used for testing}
