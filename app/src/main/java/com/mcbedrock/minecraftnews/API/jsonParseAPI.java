package com.mcbedrock.minecraftnews.API;

public class jsonParseAPI {

    /*
    для парса: https://www.minecraft.net/content/minecraft-net/_jcr_content.articles.grid
    structure:
    object
    -> article_grid [20]
    [0, 1, 2, 3...]
    -> 0
       -> default_tile
                     -> sub_header: "Что то типо описания"
                     -> image
                            -> content_type: image
                            -> imageURL: /content/dam/games/minecraft/screenshots/soulsoil-1x1.jpg
                     -> tile_size: 1x1 (???)
                     -> title: "Загаловок"
                     -> article_url: /en-us/article/block-week--soul-soil
                     -> publish_date: 12 August 2021 14:45:06 UTC (нафига?)
       -> primary_category: Deep Dives
     */
}
