# ColdSweat-Indoor

By [Aether Eldridge](https://github.com/AetherEldridge), [DeepSeek Harness](https://www.deepseek.com/en/harness/)

![Minecraft](https://img.shields.io/badge/Minecraft-1.20.1-green)
![Loader](https://img.shields.io/badge/Forge-47.4.20-orange)
![License](https://img.shields.io/badge/License-GPLv3+-yellow)

## Introduction

When playing mod "Cold Sweat", you may notice:

> Why won't I feel warmer when I'm indoors?!!

So here comes the mod.

## Mechanism

This mod will sample the block along 17 directions, 8 horizontal, 8 diagonal-up and 1 vertical, i.e., upper hemisphere, since the floor won let you feel indoors.

Within the specified radius, each step provides certain indoorness. 

The indoorness closer to 1 means the temperature closer to neutral.

More details, see the source code and the mod config.

## License

This project has some AI-Generated code in parts of temperature functions. 
And the author will only provide limited support for personal academic reasons.

This mod used [cold_sweat](https://github.com/Momo-Softworks/Cold-Sweat?tab=License-1-ov-file) as the dependency,
and is licensed under [GPLv3+](LICENSE).

## Hint

Because something indescribable, the Git commit is completely in a mess. But it is recommended to do better for later commits, <del>*though probably this repo will not be noticed by others forever*.</del>

<del>*My English writting do need improvements now...*</del>