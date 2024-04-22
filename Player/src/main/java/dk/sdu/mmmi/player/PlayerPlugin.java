package dk.sdu.mmmi.player;

import dk.sdu.mmmi.common.data.Entity.TextureLayer;
import dk.sdu.mmmi.common.data.Properties.GameData;
import dk.sdu.mmmi.common.data.World.World;
import dk.sdu.mmmi.common.services.IGamePluginService;
import dk.sdu.mmmi.common.data.Entity.Entity;
import dk.sdu.mmmi.common.services.TextureAnimator.ITextureAnimator;
import dk.sdu.mmmi.common.services.TextureAnimator.ITextureAnimatorController;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Collection;
import java.util.ServiceLoader;

import static java.util.stream.Collectors.toList;

public class PlayerPlugin implements IGamePluginService {
    private GameData gameData;

    public Player createPlayer() {
        Path defaultTexture = Paths.get("Player/src/main/resources/player_textures/up/up-7.png");
        Player player = new Player(defaultTexture, gameData.getScaler(), (3.3f / 2.2f) * gameData.getScaler());
        player.setTextureLayer(TextureLayer.CHARACTER.getValue());



        for (ITextureAnimatorController animatorController : getITextureAnimatorController()) {
            Path upAnimationPath = Paths.get("Player/src/main/resources/player_textures/up");
            Path rightAnimationPath = Paths.get("Player/src/main/resources/player_textures/right");
            Path downAnimationPath = Paths.get("Player/src/main/resources/player_textures/down");
            Path leftAnimationPath = Paths.get("Player/src/main/resources/player_textures/left");

            ITextureAnimator upAnimation = animatorController.createTextureAnimator(gameData, upAnimationPath, 0, 7, 20f);
            ITextureAnimator rightAnimation = animatorController.createTextureAnimator(gameData, rightAnimationPath, 0, 7, 20f);
            ITextureAnimator downAnimation = animatorController.createTextureAnimator(gameData, downAnimationPath, 0, 7, 20f);
            ITextureAnimator leftAnimation = animatorController.createTextureAnimator(gameData, leftAnimationPath, 0, 7, 20f);

            player.addAnimator(PlayerAnimations.UP.getValue(), upAnimation);
            player.addAnimator(PlayerAnimations.RIGHT.getValue(), rightAnimation);
            player.addAnimator(PlayerAnimations.DOWN.getValue(), downAnimation);
            player.addAnimator(PlayerAnimations.LEFT.getValue(), leftAnimation);
        }

        return player;
    }

    @Override
    public void start(World world, GameData gameDataParam) {
        this.gameData = gameDataParam;
        Entity player = createPlayer();
        player.setY(0);
        player.setX(0);
        world.addEntity(player);
    }

    @Override
    public void stop(World world, GameData gameDataParam) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    private Collection<? extends ITextureAnimatorController> getITextureAnimatorController() {
        return ServiceLoader.load(ITextureAnimatorController.class).stream().map(ServiceLoader.Provider::get).collect(toList());
    }
}
