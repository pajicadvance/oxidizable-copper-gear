# Oxidizable Copper Gear

This mod makes copper tools and armor oxidize over time, much like copper blocks. Can also be used as a framework to make any item oxidizable.

![items](https://cdn.modrinth.com/data/cached_images/c2a7f0b904525ca6fabebc881bf96b0e7eb876a1.png)

Items will oxidize while they're in your inventory, equipped, or dropped on the ground. They won't oxidize if they're stored in a chest for example.

![ingame items](https://cdn.modrinth.com/data/cached_images/b03f6e18ee74b85376d31554c4121cccd5864a41.png)

Oxidization speed is very similar to the speed copper blocks oxidize at. It's also affected by the durability of the item, depending on how much the item durability differs from the current oxidation state.

Crafting recipes:
- Items can be waxed by combining them with honeycomb in the crafting table.
- Items can be dewaxed by combining them with any axe in the crafting table, where the axe will remain and take one durability damage.
- Oxidation on items can be reduced by combining them with any axe in the crafting table, where the axe will remain and take one durability damage.

![waxing](https://cdn.modrinth.com/data/cached_images/3424cc28065c4e5692f8b2d5fa4116d6735afbcc.png)
![dewaxing](https://cdn.modrinth.com/data/cached_images/8b17c8abb3b3ffc837222ec515a46e54b599e7bd.png)
![reducing oxidation](https://cdn.modrinth.com/data/cached_images/5b03df3b7739f4c7973c2145fac37a597dfb376f.png)

## Adding your own oxidizable items

This mod can also be used as a framework to make any item oxidizable by creating a data pack and resource pack. The default data pack which makes copper tools and armor oxidizable can be disabled like any other data pack if desired.
- In the data pack, add your items to the `oxidizable` item tag from this mod ([example](https://github.com/pajicadvance/oxidizable-copper-gear/tree/fabric/src/main/resources/resourcepacks/default_dp)). This tells the mod to:
  - Make your items oxidize over time
  - Allow oxidation-related crafting recipes to be used on your items
  - Add exposed, weathered, oxidized and waxed variants of your items to creative tabs
- In the resource pack, add models and textures for oxidation stages of your items ([example](https://github.com/pajicadvance/oxidizable-copper-gear/tree/fabric/src/main/resources/resourcepacks/default_rp)).
  - The `items` folder has to be under the namespace of the mod which adds the item you're adding assets for, or the `minecraft` namespace in case of vanilla items, as you're overriding the original files.
  - Models and textures can be under any namespace, but this mod's namespace is recommended (`oxidizable_copper_gear`).