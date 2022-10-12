# SantasChallenges
Adds tools for creating custom challenges and games and letting other players play them.

Players can create blank islands to create a custom challenge on for other players to enjoy. Challenges currently support two types.
- Chest Challenges: Requires the player to get certain items into a certain chest to complete the challenge.
- Block Challenges: Requires the player to place a certain block in a given location to complete the challenge.

When creating the challenge players can select an area for the challenge to take place in, the goal type, the goal requirements, the spawn location and the location of the chest/block. This can all be done in game through commands and GUI's.

Once a challenge is created, if someone wants to attempt it, a copy of the structure will be loaded into a void world and then the player teleported to it. Upon completion or backing out, the structure will be removed and the space can be used for another person.
This all happens within one world and the distance between the islands is customisable in order to save on storage space for the server.

Created challenges are added to a list where anyone can check them out and attempt them.

### This is currently very much in development and has fatal bugs. It can be compiled and ran but it is not a smooth experience.

## Some planned features:
- Challenge List:
  - Voting system where players
  - Different sorting styles for the list
     - new: Most recent
     - best: Most upvoted
     - featured: Chosen by admins
  - Official vs Player made challenges distinction (ie server staff made vs player made)
- Challenges:
  - More flexibility in goal creation
  - Customisable rewards
  - Minimum requirements to play
  - Customisable triggers during challenge
- Admin panel 
