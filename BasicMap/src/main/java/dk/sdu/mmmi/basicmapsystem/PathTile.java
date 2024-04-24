package dk.sdu.mmmi.basicmapsystem;

import dk.sdu.mmmi.common.data.entity.Entity;

import java.nio.file.Path;
import java.nio.file.Paths;

public class PathTile extends Entity {

    private static final Path PATH_TEXTURE = Paths.get("BasicMap/src/main/resources/basic_map_textures/floor-1.png");

    public PathTile(float width, float height) {
        super(PATH_TEXTURE, width, height);
    }
}
