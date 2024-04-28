package dk.sdu.mmmi.WeaponSystem;

import dk.sdu.mmmi.common.data.entity.Coordinates;
import dk.sdu.mmmi.common.data.entity.Entity;
import dk.sdu.mmmi.common.data.entity.TextureLayer;
import dk.sdu.mmmi.common.data.gameproperties.GameData;
import dk.sdu.mmmi.common.data.world.GridPosition;
import dk.sdu.mmmi.common.data.world.World;
import dk.sdu.mmmi.common.services.entityproperties.IDamageable;
import dk.sdu.mmmi.common.services.IEntityProcessingService;
import dk.sdu.mmmi.common.services.textureanimator.ITextureAnimatorController;
import dk.sdu.mmmi.common.services.weapon.IWeapon;
import dk.sdu.mmmi.common.services.weapon.IWeaponController;
import org.junit.Before;
import org.junit.Test;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

import static java.util.stream.Collectors.toList;
import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.*;

public class BombTest {

    private World mockWorld;
    private GameData mockGameData;
    private BombControlSystem bombControlSystem;

    // Test doubles. Consider using constructor/field injection for cleaner setup
    private IDamageable mockDamageable;
    private Bomb mockBomb;
    private Explosion mockExplosion;

    @Before
    public void setUp() {
        mockWorld = mock(World.class);
        mockGameData = mock(GameData.class);
        bombControlSystem = new BombControlSystem();

        mockDamageable = mock(IDamageable.class);
        mockBomb = mock(Bomb.class);
        mockExplosion = mock(Explosion.class);

        when(mockBomb.calculateTimeTillExplosion(mockGameData)).thenReturn(0f); // Set time till explosion to 0
        when(mockGameData.getDeltaTime()).thenReturn(1f); // Simulate time passage

        // Prepare the list of entities that the method should return
        List<Entity> bombs = Arrays.asList(mockBomb);

        // Stub the method, passing Bomb.class as the varargs argument
        when(mockWorld.getEntities(Bomb.class)).thenReturn(bombs);

    }


    // Test that the bomb explodes when the time is up
    @Test
    public void testBombExplosion() {
        // Arrange
        when(mockBomb.calculateTimeTillExplosion(mockGameData)).thenReturn(0f); // Fuse time is up

        // Act
        bombControlSystem.process(mockWorld, mockGameData);

        // Assert
        verify(mockBomb).setState(Bomb.State.EXPLODING);
    }





}

