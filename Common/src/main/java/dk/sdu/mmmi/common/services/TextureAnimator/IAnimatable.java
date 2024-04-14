package dk.sdu.mmmi.common.services.TextureAnimator;

import java.util.HashMap;
import java.util.UUID;

public interface IAnimatable {
    /**
     * Get the active texture path for the given key
     * @param key
     * @return the active texture path as a String
     */
    String getActiveTexturePath(UUID key);

    /**
     * Add a new animator to the animators HashMap
     * @param key
     * @param animator
     */
    public void addAnimator(UUID key, ITextureAnimator animator);

    /**
     * Get the animators HashMap
     * @return the animators HashMap
     */
    HashMap<UUID, ITextureAnimator> getAnimators();

    /**
     * Set the animators HashMap
     * @param animators
     */
    void setAnimators(HashMap<UUID, ITextureAnimator> animators);
}
