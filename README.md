# PlayerPets

Vanilla style player pets and owners mod. Now you can finally own other players! Same mechanics as a vanilla wolf :3

Why make this? for me and my boyfriend :3

This is my first mod. dunno why i am publihsing this. it works, and maybe someone else wants it?

### Current Features:
- tame any player by using a bone on them (vanilla based mechanics, particles, and tame chance)
- any player can be an owner or a pet, simply whoever tames who first with a bone. tamed players cannot tame others or be tamed by anyone else (unless they run away first)
- /playerpets runaway: run away from your current owner (wipes your nbt data, lets you own other players or become someone elses pet)
- /playerpets owner: veiw your current owner (dumps your current nbt data)

### ToDo List
- [ ] figure out what 1.20.x versions work
- [ ] figure out if mod is client or server sided (or both)
- [x] tame any player by using a bone on them (vanilla based mechanics, particles, and tame chance)
- [x] any player can be an owner or a pet, simply whoever tames who first with a bone. tamed players cannot tame others or be tamed by anyone else (unless they run away first)
- [x] accessor to expose mixins
- [ ] owners can force their pet to sit
- [ ] if a pet is too far from their owner they get teleported closer (unless sitting)
- [ ] visual collar when tamed
- [ ] feed and heal your pet by right clicking them with food
- [ ] integration for jade/waila/whtit (when looking at a pet, show show owns them)
- [ ] integration for cosmetic nametags (owner can right click a named nametag onto their pet to auto equip the nametag into their trinket slot, changing their display name)
- [ ] integration for a player leash mod (so i dont have to code leash mechanics because i cannot)
- [x] add debug messages and system prints to aid develpoment
- [x] proper commands registered under /playerpets
- [x] /playerpets runaway: run away from your current owner (wipes your nbt data, lets you own other players or become someone elses pet)
- [x] /playerpets owner: veiw your current owner (dumps your current nbt data)
- [ ] Store username instead of UUID in NBT
- [ ] use UUID to update username in case username changes (somehow)
- [x] rewrote how nbt data is read and stored (multiple effects)
- [x] rewrote handler file, split functions into scoped files, and placed a proper flowchart to handle righ click events

### Bug List
- [ ] modrinth page not accurate
- [x] commands cant access mixins 
- [x] improper mixin access from handler causes crash
- [x] commands not registered properly
- [x] double click on player interact (bone gets used twice also)
- [x] can attempt to tame a player even if they are already tamed (consumes bones)
- [x] owner cant have multiple pets
- [x] duplicate nbt data on owner and pet
- [x] sync issues between owner and pet nbt data

# why not planned? why ai?

other versions, loaders, and making my own leash mechanics are not planned, and yes, this project is nearly 100% ai.

it was already hell to build this mod for the specific version and loader me and bae wanted to use it on, so im sorry but i cannt do other versions or loaders

and leashing is technically impossible because players are not entities. i do NOT have the ability to make a workaround, i know they exist, other mods have done it, but i cant. those mod wont have issues with mine tho, they should conpliment each other very well

i might be able to add an optional integration with a player leash mod with my owner mechanic, but thats the extent of it

i nornally hate using ai for coding, i usually hand code absolutly everything (check my website, its entirely hand coded) i had to fight ai ALOT on this project, i absolutly hated it, and i cant even say i made the end product.

but, i dont know java, all i know is html and css (not even much js) and this was my first ever minecraft mod. all i really wanted to do was make a simple pet mod for me and my boyfriend and maybe elarn some java along the way. i wasnt even sure about publuishing this publicly but, may as well? it does actually work, and maybe someoen else wants it?

so thats why the no license. well this isnt my code, its nearly 100% from ai which comes from who knows where. can you imagine claiming it as my own or demanding copyright respect? heck no. public domain it is.

## License

this prohect is open and unlicensed, do whatever you want. and if you would like to contribute, please do! if you want to port to another loader or version, that wpuld be awesome! if you want to edit the code, publish your own version, or make changes for your own preferences, knock yourself out! :3

(if you make your own mod, changing the namespace could be useful to avoid mod conflict but not necessary)

you can see the file here: https://github.com/khaylapuppy/PlayerPets/blob/main/LICENSE

or from its origin link: https://unlicense.org

or just read it below:

### License Below:

This is free and unencumbered software released into the public domain.

Anyone is free to copy, modify, publish, use, compile, sell, or
distribute this software, either in source code form or as a compiled
binary, for any purpose, commercial or non-commercial, and by any
means.

In jurisdictions that recognize copyright laws, the author or authors
of this software dedicate any and all copyright interest in the
software to the public domain. We make this dedication for the benefit
of the public at large and to the detriment of our heirs and
successors. We intend this dedication to be an overt act of
relinquishment in perpetuity of all present and future rights to this
software under copyright law.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND,
EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF
MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT.
IN NO EVENT SHALL THE AUTHORS BE LIABLE FOR ANY CLAIM, DAMAGES OR
OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE,
ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR
OTHER DEALINGS IN THE SOFTWARE.

For more information, please refer to <https://unlicense.org>