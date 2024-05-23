package dk.sdu.mmmi.textureAnimator;

import dk.sdu.mmmi.common.data.gameproperties.GameData;
import dk.sdu.mmmi.common.textureanimator.ITextureAnimator;
import dk.sdu.mmmi.common.textureanimator.ITextureAnimatorController;

import java.nio.file.Path;

public class TextureAnimatorControlSystem implements ITextureAnimatorController {
    @Override
    public ITextureAnimator createTextureAnimator(GameData gameData, Path directory, int startIndex, int endIndex, float animationSpeed) {
        return new TextureAnimator(gameData, directory, startIndex, endIndex, animationSpeed);
    }
}
