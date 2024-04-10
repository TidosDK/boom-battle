import dk.sdu.mmmi.common.services.TextureAnimator.ITextureAnimatorController;
import dk.sdu.mmmi.textureAnimator.TextureAnimatorControlSystem;

module TextureAnimator {
    requires Common;
    provides ITextureAnimatorController with TextureAnimatorControlSystem;
}
