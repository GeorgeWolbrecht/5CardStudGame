#Five Card Stud Poker

Written by GeorgeWolbrecht and Bri-Womack

0. Project Overview

This project is a graphical JavaFX implementation of a 5-Card Stud Poker game played between a single player and an automated dealer. The program features:

Random shuffling and dealing of cards

A full 5-card hand evaluation system (Hand class)

Card replacement via Draw

A final comparison using poker hand rankings

A complete GUI with clickable cards and background styling

Image-based card rendering stored in /cards/

Notable Design Choices

1. Hand Evaluation System

The Hand class fully evaluates:

Straight Flush

Four of a Kind

Full House

Flush

Straight

Three of a Kind

Two Pair

One Pair

High Card

The implementation uses:

Sorted copies of the hand

Count arrays for rank grouping

Dedicated helper arrays (fourOfAKind, threeOfAKind, twoOfAKind, highCard)

Our design allows accurate comparison between hands via Comparable<Hand>.

2. Card Image Rendering

Card images are loaded from /cards/{RANK}{SUIT}.png | ex:AH.png - Ace of hearts

Backside image displayed for the dealer until Showdown

Background uses cardGameBackground.jpg stretched to window size

3. GUI Layout

Dealer and player hands displayed in GridPanes

Status text shows game instructions

Buttons grouped at the bottom with hover effects

Entire window uses a decorated background image

Known Bugs / Limitations

If card images are missing or mis-named, JavaFX will silently fail to render them.

Dealer logic automatically keeps all 5 cards (no AI draw).

Only one round can be played at a time—no chips, scoring, or multiple games tracking.

Contribution Summary

Group Member	Estimated Lines of Code	Contributions
George Wolbrecht	~450 LOC	JavaFX GUI, event handlers, layout, card selection, draw logic, showdown logic, image loading, debugging
Brian Womack	~350 LOC	Hand evaluation logic, Comparable<Hand> implementation, straight/flush detection, card grouping, sorting logic, debugging

Debugging Collaborators

None

Approximate Hours Spent

Total combined group hours: ~28–32 hours