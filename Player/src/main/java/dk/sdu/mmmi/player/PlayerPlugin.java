package dk.sdu.mmmi.player;

import dk.sdu.mmmi.common.data.Properties.GameData;
import dk.sdu.mmmi.common.data.World.World;
import dk.sdu.mmmi.common.enums.animations;
import dk.sdu.mmmi.common.services.IGamePluginService;
import dk.sdu.mmmi.common.data.Entity.Entity;
import dk.sdu.mmmi.common.services.TextureAnimator.ITextureAnimator;
import dk.sdu.mmmi.common.services.TextureAnimator.ITextureAnimatorController;

import java.util.Collection;
import java.util.ServiceLoader;

import static java.util.stream.Collectors.toList;

public class PlayerPlugin implements IGamePluginService {
    private GameData gameData;
    private Entity player;

    public Player createPlayer() {
        Player player = new Player("Player/src/main/resources/up/up-7.png", gameData.getScaler(), (3.3f / 2.2f) * gameData.getScaler());

        if (!getITextureAnimatorController().isEmpty()) {
            ITextureAnimatorController animatorController = getITextureAnimatorController().stream().findFirst().get();

            ITextureAnimator upAnimation = animatorController.createTextureAnimator(gameData, "Player/src/main/resources/up", 0, 7, 20f);
            ITextureAnimator rightAnimation = animatorController.createTextureAnimator(gameData, "Player/src/main/resources/right", 0, 7, 20f);
            ITextureAnimator downAnimation = animatorController.createTextureAnimator(gameData, "Player/src/main/resources/down", 0, 7, 20f);
            ITextureAnimator leftAnimation = animatorController.createTextureAnimator(gameData, "Player/src/main/resources/left", 0, 7, 20f);

            player.addAnimator(animations.UP, upAnimation);
            player.addAnimator(animations.RIGHT, rightAnimation);
            player.addAnimator(animations.DOWN, downAnimation);
            player.addAnimator(animations.LEFT, leftAnimation);
        }

        return player;
    }

    @Override
    public void start(World world, GameData gameData) {
        this.gameData = gameData;
        player = createPlayer();
        player.setY(0);
        player.setX(0);
        world.addEntity(player);
    }

    @Override
    public void stop(World world, GameData gameData) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    private Collection<? extends ITextureAnimatorController> getITextureAnimatorController() {
        return ServiceLoader.load(ITextureAnimatorController.class).stream().map(ServiceLoader.Provider::get).collect(toList());
    }
}
