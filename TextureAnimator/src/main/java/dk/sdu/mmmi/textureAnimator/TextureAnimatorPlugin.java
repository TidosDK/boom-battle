package dk.sdu.mmmi.textureAnimator;

import dk.sdu.mmmi.common.data.gameproperties.GameData;
import dk.sdu.mmmi.common.data.world.World;
import dk.sdu.mmmi.common.services.IGamePluginService;
import dk.sdu.mmmi.common.services.textureanimator.IAnimatable;

import java.util.Collection;
import java.util.ServiceLoader;

import static java.util.stream.Collectors.toList;

public class TextureAnimatorPlugin implements IGamePluginService {
    @Override
    public void start(World world, GameData gameData) {
        System.out.println("Module started: TextureAnimator");
    }

    @Override
    public void stop(World world, GameData gameData) {
        for (IAnimatable animatable : getIAnimatable()) {
            animatable.setAnimators(null);
        }
    }

    private Collection<? extends IAnimatable> getIAnimatable() {
        return ServiceLoader.load(IAnimatable.class).stream().map(ServiceLoader.Provider::get).collect(toList());
    }
}
