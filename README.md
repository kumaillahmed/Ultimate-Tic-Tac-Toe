# Ultimate Tic-Tac-Toe

A Java implementation of Ultimate Tic-Tac-Toe with a clean separation between game logic, utility code, and user interface.

Ultimate Tic-Tac-Toe is a rule-driven board game with nested state, which makes it a strong project for demonstrating structured design, validation, and testable implementation. This repository focuses on correctness first: rules are enforced explicitly, invalid moves are rejected, and the game state is modeled in a way that keeps the core logic independent from the UI.

## Overview

Ultimate Tic-Tac-Toe extends classic Tic-Tac-Toe by using a 3×3 grid of smaller boards. Each move affects where the next move must be played, so the game stays simple to understand while still requiring careful state handling.

This project was built to keep the implementation readable, maintainable, and easy to test.

## Features

* Full Ultimate Tic-Tac-Toe game logic
* Legal move validation
* Local board win and draw detection
* Global game win and draw detection
* Turn and board routing enforcement
* GUI for interactive play
* JUnit-based testing
* Optional AI extension point

## Project Structure

```text
.
├── src/
│   ├── uttt.game/          # Core game logic
│   ├── uttt.utils/         # Shared utility classes
│   └── uttt.ui/            # Graphical user interface
├── tests/                  # JUnit tests
├── README.md
└── LICENSE
```

The codebase is organized by responsibility. Game rules stay in the game package, shared data types live in utilities, and the UI remains separate from the rule engine.

## Design Principles

### Clear boundaries

The game engine does not depend on the UI. That makes the core logic easier to test and easier to reuse.

### Stable interfaces

The project uses interfaces for the main game components, which keeps the public structure predictable and allows implementations to change without forcing large rewrites.

### Explicit validation

Invalid input is not treated as a special case hidden deep in the code. It is checked directly and rejected early, which keeps the game state consistent.

### Maintainable state handling

The game keeps track of both the local board state and the overall match state. That makes the flow of the game easier to reason about and reduces accidental coupling.

## Testing

The project is designed to be tested as part of normal development, not added later as an afterthought. JUnit tests cover the important rule interactions and edge cases, including invalid moves, win detection, and draw handling.

The GUI can also be used for manual verification and visual inspection.

## Complexity

Most operations are effectively constant time because the board size is fixed.

| Operation           |          Complexity |
| ------------------- | ------------------: |
| Place move          |                O(1) |
| Validate move       |                O(1) |
| Check local result  |                O(1) |
| Check global result |                O(1) |
| AI search           | Depends on strategy |

## Running the Project

Open the project in VSCode and run the main entry point from the game package.

For testing, use the provided JUnit setup and the project workflow described in the assignment.

## Future Improvements

Possible extensions include:

* stronger AI strategies
* undo and redo support
* replay export
* match history
* visual polish in the UI

## Why This Project Matters

This project shows how to build a rule-heavy application in a way that stays readable and testable. It demonstrates practical experience with Java, interface-based design, state management, and writing code that can be maintained over time.

## License

This project is licensed under the **GNU General Public License v3.0 (GPL-3.0)**.

See the LICENSE file for details.
