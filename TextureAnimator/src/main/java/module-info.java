import dk.sdu.mmmi.common.services.IGamePluginService;
import dk.sdu.mmmi.common.services.textureanimator.ITextureAnimatorController;
import dk.sdu.mmmi.textureAnimator.TextureAnimatorControlSystem;
import dk.sdu.mmmi.textureAnimator.TextureAnimatorPlugin;

module TextureAnimator {
    requires Common;
    provides ITextureAnimatorController with TextureAnimatorControlSystem;
    provides IGamePluginService with TextureAnimatorPlugin;
}
