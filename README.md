# Uno Game Engine Documentation

[video]()
[report](/asstes/report.md)


## 📌 Introduction
The **Uno Game Engine** is a **Java-based engine** designed to facilitate the development of Uno variations. It provides a **modular, extensible, and developer-friendly framework** that allows customization of Uno rules, card behaviors, and player strategies.

This repository contains:
- **A fully functional Uno engine** (Playable & Simulated modes)
- **A flexible architecture** for extending game rules
- **Design patterns** ensuring maintainability
- **SOLID-compliant OOP principles**

## 📂 Repository Structure

```
📆 uno-game-engine
 ├ 🗂 src
 ┃ ├ 📄 Game.java            # Abstract class for game variations
 ┃ ├ 📄 GameDriver.java      # Entry point for running the game
 ┃ ├ 📄 UnoGame.java         # Standard Uno implementation
 ┃ ├ 📄 UnoGameEngine.java   # Core game loop and rule processing
 ┃ ├ 📄 UnoDeck.java         # Manages draw and discard piles
 ┃ ├ 📄 UnoCard.java         # Represents an Uno card
 ┃ ├ 📄 UnoPlayer.java       # Interface for AI & human players
 ┃ ├ 📄 BasicUnoPlayer.java  # AI player implementation
 ┃ ├ 📄 HumanUnoPlayer.java  # Console-based human player
 ┃ ├ 📄 UnoGameObserver.java # Observer pattern for event handling
 ┃ ├ 📄 UnoColor.java        # Enum for Uno colors
 ┃ ├ 📄 UnoValue.java        # Enum for Uno values
 ┃ ├ 📄 UnoCardFactory.java  # Factory for creating Uno decks
 ┃ ├ 📄 ConsoleColors.java   # ANSI color codes for colored output
 └ 📄 README.md              # Documentation
```

### 1️⃣ Prerequisites
- **Java 8+** must be installed
- Terminal or Command Prompt for running the game

### 2️⃣ Clone the Repository
```sh
git clone https://github.com/your-repo/uno-game-engine.git
cd uno-game-engine
```

### 3️⃣ Compile & Run
```sh
javac src/*.java
java src/GameDriver
```

## 🎮 Game Modes
Upon running the game, you will be prompted to choose:
- **Simulation Mode** (Auto-play, AI players only)
- **Interactive Mode** (Human vs AI or Human vs Human)

## 🛠️ Architecture & Design

### 1️⃣ **Core Components**
- **`Game` (Abstract Class)**
    - Base class for game variations
    - Defines `play()` method for game execution
- **`UnoGameEngine` (Game Loop & Rules)**
    - Manages turns, validates moves, enforces rules
    - Implements **Observer Pattern** to notify UI/logging modules
- **`UnoPlayer` (Interface)**
    - Supports different player implementations (AI, Human, etc.)
- **`UnoDeck` (Deck Management)**
    - Handles draw/discard pile logic
    - Implements **Factory Pattern** for deck generation
- **`UnoGameObserver` (Observer Pattern)**
    - Allows external modules to listen to game events

### 2️⃣ **Design Patterns Used**
🔄 **Factory Pattern** (`UnoCardFactory`) → Generates decks dynamically  
🎮 **Strategy Pattern** (`UnoPlayer`, `BasicUnoPlayer`, `HumanUnoPlayer`) → Different player strategies  
📝 **Observer Pattern** (`UnoGameObserver`) → Event-based game state tracking  
🌐 **Singleton Pattern** (Optional for extensions)

### 3️⃣ **OOP & SOLID Principles**
✅ **Single Responsibility Principle** → Each class has one clear purpose  
✅ **Open/Closed Principle** → New rules can be added without modifying existing logic  
✅ **Liskov Substitution Principle** → AI and Human players share a common interface  
✅ **Interface Segregation Principle** → Separate interfaces for game logic, deck, and players  
✅ **Dependency Inversion Principle** → The engine depends on abstractions, not concrete implementations

## 🔄 Extending the Engine
### ➕ **Adding New Rules**
1. Extend `Game` class and override `play()`
2. Modify `UnoGameEngine` to introduce rule logic
3. Use `UnoGameObserver` to track rule changes

### 🎭 **Creating New Player Types**
1. Implement `UnoPlayer` interface
2. Define `playCard()` strategy
3. Register new player type in `UnoGame.java`

### 🂠 **Custom Cards**
1. Modify `UnoCardFactory` to add new cards
2. Extend `UnoValue` enum for additional effects
3. Modify `UnoGameEngine#processCardEffect()` to handle new actions

## 📝 Example: Custom Rule Implementation
To add a **new "Draw Five" card**, follow these steps:

### 1️⃣ Extend `UnoValue`
```java
public enum UnoValue {
    DRAW_FIVE, // New Card
    ...
}
```
### 2️⃣ Modify `UnoCardFactory`
```java
deck.add(new UnoCard(UnoColor.WILD, UnoValue.DRAW_FIVE));
```
### 3️⃣ Add Rule to `UnoGameEngine`
```java
case DRAW_FIVE:
    UnoPlayer next = getNextPlayer();
    for (int i = 0; i < 5; i++) {
        next.drawCard(deck.drawCard());
    }
    moveToNextPlayer();
    break;
```

## 🛠️ Debugging & Testing
- **Enable Simulation Mode** to verify AI behavior
- **Check console logs** for errors & rule violations
- **Modify player strategies** to test AI behavior


## 👈 Credits
Developed by **Abdallah Abu Hussein** As A part of Atypon Internship Program. 

````