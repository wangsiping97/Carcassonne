# Response

## Domain Model

1.1

1.3

## Object Model

6.1 -- > add generateCurrPlayer() method

6.2 

- Update Deck

  > This has a high representational gap with what a Deck normally stores. A deck is typically something that has a list of cards/tiles that just can shuffle and hand out tiles. Yours is storing state of unrelated aspects to the deck.

- Update Tile

  > Tiles shouldn't know where they are. The board is already storing this infomration and this leads to a high representational gap.
  >
  > Tiles should be immutable. The rotate field is making the tile mutable.

- Update Feature

  > Which methods are abstract?
  >
  > It is unclear what methods are concrete and what code will be reused.

- Update GameManager and Feature about scoring

  > It is unclear how scoring will be handled.

- Update Segment and add SegmentType

  > Does each segment have a field for each of the types of segements? This should be represented in a list so we see each of the different kinds of segements rather than having them as attributes.
  >
  > Enums are constants, you shouldn't have fields that you can change constantly such as Player. This defeats the purpose of enums.

- Update Board

  > Roads and cities are features! Why are you storing them in this manner when you have a class to handle it? This is poor responsibility assignment.

- Add cardinalities and relationship names

  > Add 1 here for cardinalities so that one GameManager has 2-5 players.
  >
  > Add cardinalities to all your relationships!
  >
  > What is this relationship? Just an association?
  >
  > What is this cardinality "n"?

## Interaction 1

- Remove Deck

  > I would think carefully if this is the best way to design for the deck to have a current tile and keeping state in the deck class.

- Add tile as parameter of the function

  > How do you know where the tile is?

- Update `for` condition

  > Your direction enum has more than just the 4 adjacent tile directions. Specify which ones you are checking so that this is accurate.

- Update the logics inside `for`

  > Before checking boundary, you will also want to check that there is an abutting tile and the position is not already occupied. 

## Interaction 2

- Update functions

  > consistency: this method does not exist in your object model.

- Keep "All directions"

  > Specify that this is the full 8 direction (comparing to the 4 needed in the tile placement).

- Remove `checkExists`, change logics

  > Is this meant to be every tile around the current 'tile'? Show how this is calculated.
  >
  > This will work for monesteries specifically, but I'm not sure which side you would choose for other features or how you would know which side to choose.

- Move scoring process to GameManager

  > Is this the correct responsibility assignment for the feature to be assigning scores? Features are information storing elements and it seems odd to allow them to have such functionality. 