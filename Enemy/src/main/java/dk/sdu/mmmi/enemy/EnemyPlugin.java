package dk.sdu.mmmi.enemy;

import dk.sdu.mmmi.common.data.entity.Entity;
import dk.sdu.mmmi.common.data.gameproperties.GameData;
import dk.sdu.mmmi.common.data.world.World;
import dk.sdu.mmmi.common.services.IGamePluginService;
import dk.sdu.mmmi.common.services.textureanimator.ITextureAnimator;
import dk.sdu.mmmi.common.services.textureanimator.ITextureAnimatorController;

import java.util.Collection;
import java.util.ServiceLoader;

import static java.util.stream.Collectors.toList;


public class EnemyPlugin implements IGamePluginService {
    private GameData gameData;
    private Entity player;

    public Enemy createEnemy() {
        Enemy player = new Enemy("Enemy/src/main/resources/enemy_textures/up/up-7.png", gameData.getScaler(), (3.3f / 2.2f) * gameData.getScaler());

        if (!getITextureAnimatorController().isEmpty()) {
            ITextureAnimatorController animatorController = getITextureAnimatorController().stream().findFirst().get();

            ITextureAnimator upAnimation = animatorController.createTextureAnimator(gameData, "Enemy/src/main/resources/enemy_textures/up", 0, 7, 20f);
            ITextureAnimator rightAnimation = animatorController.createTextureAnimator(gameData, "Enemy/src/main/resources/enemy_textures/right", 0, 7, 20f);
            ITextureAnimator downAnimation = animatorController.createTextureAnimator(gameData, "Enemy/src/main/resources/enemy_textures/down", 0, 7, 20f);
            ITextureAnimator leftAnimation = animatorController.createTextureAnimator(gameData, "Enemy/src/main/resources/enemy_textures/left", 0, 7, 20f);

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
        player = createEnemy();
        player.setY(3);
        player.setX(3);
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
