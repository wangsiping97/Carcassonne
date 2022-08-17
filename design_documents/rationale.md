# Rationale

### 1 Game-related Classes

### GameManager

`GameManager` is a class of the gaming system. By delegating players, a decker and a board, it lowers the coupling between methods on the board, methods of drawing the tiles, and scoring. 

### Decker

`Decker` is a class to decide the next tile to draw and the next player to play. By implementing the iterator pattern, it improves extensibility for future use. 

### Player

`Player` is a class for all players. It mainly contains the current score and amount of used meeples. Getters and setters are implemented for interaction with other classes. 

### Board

`Board` is a class for the board of the game. It checks validation of each boundary of each tile on it, and updates the features during the process. 

### Tile

`Tile` is a class that represents tiles. Its content is represented by mapping each side (i.e., direction) to different segments. 

### Feature

`Feature` is an abctract class that has 3 concrete subclasses called `City`, `Road` and `Monastery`. All of these classes would call `isCompleted()`, `getOwner()` and `complete()` during the game to check whether a feature is completed and to update socres. Inheritance is used here so as to lower the representational gap, improve reusability and extensibility of the codes. New features can be added as concrete subclasses of `Feature` in the future. 

### Segment

`Segment` is an **enum** type containing the current types of segment on a tile. It implements `match(Segment)` to help check if the boundaries of two neighboring tiles are matched, and also getters and setters for position, direction and player to get and set information for each segment. It improves extensibility as new segments can be added here easily. 

## 2 Other (helper) Classes

Helper classes are created to improve reusability and extensibility of the codes. 

### Direction

`Direction` is an **enum** type containing 8 different directions. It implements `getOppo()` method to get the opposite direction. It can be reused in different boarding games in the future. 

### Position

`Position` is a class representing the positions on the board. It implements `getAdj(Direction)` to get neighboring positions of different directions. This class, together with `Direction`, can be resued in other similar circumstances in the future. 