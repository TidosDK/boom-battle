package dk.sdu.mmmi.enemy;

import dk.sdu.mmmi.common.data.entity.Entity;
import dk.sdu.mmmi.common.data.entity.TextureLayer;
import dk.sdu.mmmi.common.data.gameproperties.GameData;
import dk.sdu.mmmi.common.data.world.World;
import dk.sdu.mmmi.common.services.IGamePluginService;
import dk.sdu.mmmi.common.services.textureanimator.ITextureAnimator;
import dk.sdu.mmmi.common.services.textureanimator.ITextureAnimatorController;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Collection;
import java.util.ServiceLoader;

import static java.util.stream.Collectors.toList;


public class EnemyPlugin implements IGamePluginService {
    private GameData gameData;

    public Enemy createEnemy() {
        Path defaultTexture = Paths.get("Enemy/src/main/resources/enemy_textures/up/up-7.png");
        Enemy enemy = new Enemy(defaultTexture, gameData.getScaler(), (3.3f / 2.2f) * gameData.getScaler());
        enemy.setTextureLayer(TextureLayer.CHARACTER.getValue());



        for (ITextureAnimatorController animatorController : getITextureAnimatorController()) {
            Path upAnimationPath = Paths.get("Enemy/src/main/resources/enemy_textures/up");
            Path rightAnimationPath = Paths.get("Enemy/src/main/resources/enemy_textures/right");
            Path downAnimationPath = Paths.get("Enemy/src/main/resources/enemy_textures/down");
            Path leftAnimationPath = Paths.get("Enemy/src/main/resources/enemy_textures/left");
            Path stillAnimationPath = Paths.get("Enemy/src/main/resources/enemy_textures/wait");
            Path dieAnimationPath = Paths.get("Enemy/src/main/resources/enemy_textures/dead");


            ITextureAnimator upAnimation = animatorController.createTextureAnimator(gameData, upAnimationPath, 0, 7, 20f);
            ITextureAnimator rightAnimation = animatorController.createTextureAnimator(gameData, rightAnimationPath, 0, 7, 20f);
            ITextureAnimator downAnimation = animatorController.createTextureAnimator(gameData, downAnimationPath, 0, 7, 20f);
            ITextureAnimator leftAnimation = animatorController.createTextureAnimator(gameData, leftAnimationPath, 0, 7, 20f);
            ITextureAnimator stillAnimation = animatorController.createTextureAnimator(gameData, stillAnimationPath, 0, 4, 20f);
            ITextureAnimator dieAnimation = animatorController.createTextureAnimator(gameData, dieAnimationPath, 0, 4, 5f);

            enemy.addAnimator(EnemyAnimations.UP.getValue(), upAnimation);
            enemy.addAnimator(EnemyAnimations.RIGHT.getValue(), rightAnimation);
            enemy.addAnimator(EnemyAnimations.DOWN.getValue(), downAnimation);
            enemy.addAnimator(EnemyAnimations.LEFT.getValue(), leftAnimation);
            enemy.addAnimator(EnemyAnimations.STILL.getValue(), stillAnimation);
            enemy.addAnimator(EnemyAnimations.DIE.getValue(), dieAnimation);
        }

        return enemy;
    }

    @Override
    public void start(World world, GameData gameDataParam) {

        this.gameData = gameDataParam;
        Entity enemy = createEnemy();
        enemy.setY(5);
        enemy.setX(5);
        world.addEntity(enemy);
    }

    @Override
    public void stop(World world, GameData gameDataParam) {
        for (Entity enemy : world.getEntities(Enemy.class)) {
            world.removeEntity(enemy);
        }
    }

    private Collection<? extends ITextureAnimatorController> getITextureAnimatorController() {
        return ServiceLoader.load(ITextureAnimatorController.class).stream().map(ServiceLoader.Provider::get).collect(toList());
    }
}
