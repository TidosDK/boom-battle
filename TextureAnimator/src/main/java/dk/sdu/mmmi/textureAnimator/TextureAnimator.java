package dk.sdu.mmmi.textureAnimator;

import dk.sdu.mmmi.common.data.Properties.GameData;
import dk.sdu.mmmi.common.services.TextureAnimator.ITextureAnimator;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

public class TextureAnimator implements ITextureAnimator {
    private GameData gameData;
    private String directory;
    private int currentDirectoryIndex;
    private int startIndex;
    private int endIndex;
    private float animationSpeed;
    private float animationCooldown;
    private List<String> imagePaths;

    public TextureAnimator(GameData gameData, String directory, int startIndex, int endIndex, float animationSpeed) {
        this.gameData = gameData;
        this.directory = directory;
        this.currentDirectoryIndex = startIndex;
        this.startIndex = startIndex;
        this.endIndex = endIndex;
        this.animationSpeed = animationSpeed;
        this.animationCooldown = 0;

        this.imagePaths = getImagePaths(directory);
        validateIndexes();
    }

    /**
     * Validates whether the indexes are valid or not
     */
    public void validateIndexes() {
        if (startIndex < 0) {
            throw new IllegalArgumentException("startIndex must be greater than or equal to 0");
        }

        if (imagePaths != null && endIndex > imagePaths.size() - 1) {
            throw new IllegalArgumentException("endIndex must be less or equal to the number of images in the directory");
        }
    }

    /**
     * Get all image paths in a directory
     * @param directory Directory of the images
     * @return List of image paths
     */
    public List<String> getImagePaths(String directory) {
        Path directoryPath = Paths.get(directory);
        Stream<Path> paths = null;
        try {
            paths = Files.walk(directoryPath);
        } catch (IOException e) {
            e.printStackTrace();
        }

        List<String> images = new ArrayList<>();
        if (paths != null) {
            paths.forEach(path -> {
                if (Files.isRegularFile(path)) { // Is a regular file (not a directory)
                    if (path.toString().endsWith(".png") || path.toString().endsWith(".jpeg")) {
                        images.add(path.toString());
                    }
                }
            });
            paths.close();
        }
        return images;
    }

    @Override
    public String getCurrentImagePath() {
        if (animationCooldown > 1) {
            animationCooldown = 0;

            // iterates from startIndex (inclusive) to endIndex (inclusive)
            currentDirectoryIndex = (currentDirectoryIndex + 1) % (endIndex - startIndex + 1);
        } else {
            animationCooldown += animationSpeed * gameData.getDeltaTime();
        }

        return imagePaths.get(currentDirectoryIndex);
    }
}
