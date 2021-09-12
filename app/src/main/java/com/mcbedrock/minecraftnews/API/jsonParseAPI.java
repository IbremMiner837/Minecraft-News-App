package com.mcbedrock.minecraftnews.API;

public class jsonParseAPI {
    /*
    https://code.google.com/p/google-gson/
    String sURL = "http://freegeoip.net/json/"; //just a string
    // Connect to the URL using java's native library
    URL url = new URL(sURL);
    URLConnection request = url.openConnection();
    request.connect();
    // Convert to a JSON object to print data
    JsonParser jp = new JsonParser(); //from gson
    JsonElement root = jp.parse(new InputStreamReader((InputStream) request.getContent())); //Convert the input stream to a json element
    JsonObject rootobj = root.getAsJsonObject(); //May be an array, may be an object.
    String zipcode = rootobj.get("zip_code").getAsString(); //just grab the zipcode
     */

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
