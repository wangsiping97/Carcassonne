# Behavioral Contract

> Provide behavioral contracts for the following interaction initiated by the user:
>
> The user attempts to play a tile, without a meeple.
>
> The contract should explicitly describe the preconditions and postconditions for the inter- action, and your behavioral contract should be consistent with your domain model and interaction diagrams. Constructing behavioral contracts should help you envision important changes of internal state of the game when a player interacts with the game. You may provide explicit examples to clarify your contract. For more information on contracts, see Chapter 11 of Larman’s Applying UML and Patterns.

## Preconditions

1. The tiles on the board are all legal*. 
2. The current tile has not been used. 
3. The current tile is valid to be placed in. 
4. The position must be the neighbor of one of the tiles on the board. 
5. The segments of the tile must match those of its neighbors. 
6. It is the user's turn to play the tile. 
7. Every player has a score >= 0. 

## Postconditions

1. The tiles on the board are all legal*. 
2. There is no meeple on the last placed tile. 
3. Every player has a score >= his old score. 



\*  One tile is legal if: 

- It has at least one neighboring tile. 

- For each of its directions, either it has no neighboring tile on this direction, or the segment of its neighbor matches the segment of its direction. 