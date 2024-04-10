package dk.sdu.mmmi.textureAnimator;

import dk.sdu.mmmi.common.data.Properties.GameData;
import dk.sdu.mmmi.common.services.TextureAnimator.ITextureAnimator;
import dk.sdu.mmmi.common.services.TextureAnimator.ITextureAnimatorController;

public class TextureAnimatorControlSystem implements ITextureAnimatorController {
    @Override
    public ITextureAnimator createTextureAnimator(GameData gameData, String directory, int startIndex, int endIndex, float animationSpeed) {
        return new TextureAnimator(gameData, directory, startIndex, endIndex, animationSpeed);
    }
}
