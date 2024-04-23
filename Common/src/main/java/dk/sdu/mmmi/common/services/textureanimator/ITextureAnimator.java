package dk.sdu.mmmi.common.services.textureanimator;

import java.nio.file.Path;

public interface ITextureAnimator {

    /**
     * @return Current image path
     */
    Path getCurrentTexturePath();

    /**
     * @return returns the absolute amount of textures in the animation
     */
    int getTextureAmount();

    /**
     * @return returns the index of the current texture in the animation. Index starts at 0.
     */
    int getCurrentTextureIndex();

    /**
     * Sets the current texture index of the animation to the given index.
     *
     * @param index index to set the current texture index to
     */
    void setCurrentTextureIndex(int index);
}
