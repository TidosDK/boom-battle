package dk.sdu.mmmi.common.services.TextureAnimator;

import java.nio.file.Path;
import java.util.HashMap;

public interface IAnimatable {
    /**
     * Get the active texture path for the given key
     * @param key
     * @return the active texture path as a String
     */
    Path getActiveTexturePath(Integer key);

    /**
     * Add a new animator to the animators HashMap
     * @param key
     * @param animator an ITextureAnimator object
     */
    void addAnimator(Integer key, ITextureAnimator animator);

    /**
     * Get the animators HashMap
     * @return the animators HashMap
     */
    HashMap<Integer, ITextureAnimator> getAnimators();

    /**
     * Set the animators HashMap
     * @param animators
     */
    void setAnimators(HashMap<Integer, ITextureAnimator> animators);
}
