# SantasChallenges
Adds tools for creating custom games and challenges, then letting other players play them.

Players can create blank islands to create a custom challenge on for other players to enjoy. Challenges currently support two types.
- Chest Challenges: Requires the player to get certain items into a certain chest to complete the challenge.
- Block Challenges: Requires the player to place a certain block in a given location to complete the challenge.

When creating the challenge players can select an area for the challenge to take place in, the goal type, the goal requirements, the spawn location and the location of the chest/block. This can all be done in game through commands and GUI's.

Once a challenge is created, if someone wants to attempt it, a copy of the structure will be loaded into a void world and then the player teleported to it. Upon completion or backing out, the structure will be removed and the space can be used for another person.
This all happens within one world and the distance between the islands is customisable in order to save on storage space for the server.

Created challenges are added to a list where anyone can check them out and attempt them.

So what are these challenges and how are they created? To explain I will create a basic challenge where the player will spawn on an island with only a lake and a lava bucket. They will be asked to provide 16 cobblestone to the chest to compelete the challenge.

![2022-10-13_15 44 02](https://user-images.githubusercontent.com/90232143/195534893-3ac5a17d-928e-4db3-a407-72f211243585.png)

Around the lake are some saplings in flower pots, and the player will have to figure out that to make a pickaxe they will need to grow those trees.
To remind the player what there goal is I'm going to use a very subtle technique of placing a sign next to the chest. (Note the player could also look up the challenges requirements at any time in `/santaschallenges gui`.)

![2022-10-13_15 44 31](https://user-images.githubusercontent.com/90232143/195535345-e4be5960-fb84-4d85-8ed3-3cb375a75d13.png)

In order to give the player a lava bucket I'll place it in the chest, so it will appear in there when a fresh challenge is started

![image](https://user-images.githubusercontent.com/90232143/195536703-d1504761-6187-4195-bf04-6eea527fba3c.png)

Next I will select the corners for the bounding box of this challenge similar to using WorldEdit, and I will type `/santaschallenges confirm` to confirm the selection.

![2022-10-13_15 44 57](https://user-images.githubusercontent.com/90232143/195535697-2d445f4a-9cb8-4509-946f-77debbe72869.png)

I will then be prompted with a GUI asking what type of challenge I would like to create. Since this requires items in a chest, I will choose the chest option.
In the next window I will place 16 cobblestone in to set the required goal.

![image](https://user-images.githubusercontent.com/90232143/195536898-3f1f26f4-323b-4576-bd69-6775c2ce6a47.png)
![image](https://user-images.githubusercontent.com/90232143/195536949-c118d506-662c-4faa-ad7d-eb685408e068.png)

Finally I will right click the chest that will be the goal chest, and the block the player should spawn on.

![2022-10-13_15 45 59](https://user-images.githubusercontent.com/90232143/195536002-2828738a-92f6-4635-a5df-7116e4b37e3b.png)
![2022-10-13_15 46 10](https://user-images.githubusercontent.com/90232143/195536006-af5ab400-17f4-4411-8e78-c07984b1d1a5.png)

Success! The challenge will now appear in `/santaschallenges gui` and selecting it will generate a new island that I can start playing on.

![image](https://user-images.githubusercontent.com/90232143/195536413-2d71e61d-9085-4f09-a8b8-c85f81a322cf.png)
![image](https://user-images.githubusercontent.com/90232143/195536449-7edab5fb-a15d-4b1e-8094-090cd029291c.png)

### This is currently very much in development and not fully functional. It can be ran and challenges can be created and played, but it is not polished and nothing happens once challenges are won.

## Some planned features:
- Challenge List:
  - Voting system where players can upvote and downvote challenges
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
