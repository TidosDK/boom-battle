package dk.sdu.mmmi.common.services.TextureAnimator;

import dk.sdu.mmmi.common.data.Properties.GameData;

import java.nio.file.Path;

public interface ITextureAnimatorController {

    /**
     * @param gameData
     * @param directory
     * @param startIndex First image index of directory (inclusive)
     * @param endIndex Last image index of directory (inclusive)
     * @param animationSpeed
     * @return ITextureAnimator
     */
    ITextureAnimator createTextureAnimator(GameData gameData, Path directory, int startIndex, int endIndex, float animationSpeed);
}
