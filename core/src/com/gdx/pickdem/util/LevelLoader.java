package com.gdx.pickdem.util;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.gdx.pickdem.Level;
import com.gdx.pickdem.entity.Platform;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import java.io.File;
import java.util.Comparator;

public class LevelLoader {

    public static final String TAG = LevelLoader.class.toString();

    public static Level load(String levelName, Viewport viewport) {

        Level level = new Level(viewport);
        String path = "levels" + File.separator + levelName + "." + "dt";

        FileHandle file = Gdx.files.internal(path);
        JSONParser parser = new JSONParser();
        try {
            JSONObject rootJsonObject = (JSONObject) parser.parse(file.reader());

            JSONObject composite = (JSONObject) rootJsonObject.get("composite");

            JSONArray platforms = (JSONArray) composite.get("sImage9patchs");
            loadPlatforms(platforms, level);


        } catch (Exception ex) {
            Gdx.app.error(TAG, ex.getMessage());
            Gdx.app.error(TAG, "ERROR AQUI DENTRO");
            ex.printStackTrace();
        }

        return level;
    }

    private static float safeGetFloat(JSONObject object, String key){
        Number number = (Number) object.get(key);
        return (number == null) ? 0 : number.floatValue();
    }

    private static void loadPlatforms(JSONArray array, Level level) {

        Array<Platform> platformArray = new Array<Platform>();

        for (Object object : array) {
            final JSONObject platformObject = (JSONObject) object;

            final float x = safeGetFloat(platformObject, "x");

            // TODO: Get the y position of the platform
            // Use the LEVEL_Y_KEY constant we defined
            // Not that this is the BOTTOM of the platform, not the top
            // Also note that if the platform is at (0, 0), the x and y keys will be missing from the JSON
            // Hence the need for the safeGetFloat() method defined above
            final float y = safeGetFloat(platformObject, "y");

            final float width = ((Number) platformObject.get("width")).floatValue();

            // TODO: Get the platform height
            final float height = ((Number) platformObject.get("height")).floatValue();

            // TODO: Optional, log the location and dimensions of the platform
            Gdx.app.log(TAG,
                    "Loaded a platform at x = " + x + " y = " + (y + height) + " w = " + width + " h = " + height);

            // TODO: Make a new platform with the dimensions we loaded
            // Remember that the y position we loaded is the platform bottom, not top
            final Platform platform = new Platform(x, y + height, width, height);

            // TODO: Add the platform to the platformArray
            platformArray.add(platform);

            // TODO: Get the platform identifier
            // Use the LEVEL_IDENTIFIER_KEY constant
            final String identifier = (String) platformObject.get("itemIdentifier");
        }

        // TODO: Sort the platform array by descending position of the top of the platform
        // We're doing this so lower platforms aren't hidden by higher platforms
        // This one is tough. Check out the solution project if you run into trouble
        platformArray.sort(new Comparator<Platform>() {
            @Override
            public int compare(Platform o1, Platform o2) {
                if (o1.top < o2.top) {
                    return 1;
                } else if (o1.top > o2.top) {
                    return -1;
                }
                return 0;
            }
        });

        // TODO: Add all the platforms from platformArray to the level
        level.getPlatforms().addAll(platformArray);
    }

}
