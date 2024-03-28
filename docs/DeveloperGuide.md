# Developer Guide

## Acknowledgements

{list here sources of all reused/adapted ideas, code, documentation, and third-party libraries -- include links to the original source as well}

## Design & implementation
### UIRenderer Component
**API** : [UIRenderer.java](https://github.com/AY2324S2-CS2113-W13-2/tp/blob/master/src/main/java/ui/UiRenderer.java)

Overview: <br> 
The UIRenderer component is responsible for rendering the user interface. It is used to display messages, week views as well as month views to the user.

How it works:
1. When week view is requested, the UIRenderer component will render the week view.
2. This is done by printing the table in ASCII art format, with the days of the week as columns.
3. The week view will display the tasks for the week, with the tasks for each day displayed in the respective columns.
4. The UIRenderer component will also display the current date at the top of the week view.
5. Similarly, when month view is requested, the UIRenderer component will render the month view.

Here's how UiRenderer links to other components:




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
