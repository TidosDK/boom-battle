package dk.sdu.mmmi.common.services.TextureAnimator;

import dk.sdu.mmmi.common.data.Data.GameData;
import dk.sdu.mmmi.common.data.Entity.Entity;

public interface ITextureAnimatorController {

    /**
     * @param gameData
     * @param directory
     * @param startIndex First image index of directory (inclusive)
     * @param endIndex Last image index of directory (inclusive)
     * @param animationSpeed
     * @return ITextureAnimator
     */
    ITextureAnimator createTextureAnimator(GameData gameData, String directory, int startIndex, int endIndex, float animationSpeed);
}
