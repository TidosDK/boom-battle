package dk.sdu.mmmi.textureAnimator;

import dk.sdu.mmmi.common.data.Properties.GameData;
import dk.sdu.mmmi.common.services.TextureAnimator.ITextureAnimator;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

public class TextureAnimator implements ITextureAnimator {
    private GameData gameData;
    private Path directory;
    private int currentDirectoryIndex;
    private int startIndex;
    private int endIndex;
    private float animationSpeed;
    private float animationCooldown;
    private List<Path> imagePaths;

    public TextureAnimator(GameData gameData, Path directory, int startIndex, int endIndex, float animationSpeed) {
        this.gameData = gameData;
        this.directory = directory;
        this.currentDirectoryIndex = startIndex;
        this.startIndex = startIndex;
        this.endIndex = endIndex;
        this.animationSpeed = animationSpeed;
        this.animationCooldown = 0;

        this.imagePaths = this.getImagePaths(directory);
        validateIndexes();
    }

    /**
     * Validates whether the indexes are valid or not.
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
     * Get all image paths in a directory.
     *
     * @param directoryPath Directory of the images
     * @return List of image paths
     */
    public List<Path> getImagePaths(Path directoryPath) {
        List<Path> images = new ArrayList<>();
        try (Stream<Path> paths = Files.walk(directoryPath)) {
            paths.filter(Files::isRegularFile)
                    .filter(path -> path.toString().endsWith(".png") || path.toString().endsWith(".jpeg") || path.toString().endsWith(".jpg"))
                    .forEach(images::add);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return images;
    }


    @Override
    public Path getCurrentTexturePath() {
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
