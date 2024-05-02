package dk.sdu.mmmi.WeaponSystem;

import dk.sdu.mmmi.common.data.entity.Coordinates;
import dk.sdu.mmmi.common.data.entity.Entity;
import dk.sdu.mmmi.common.data.gameproperties.GameData;
import dk.sdu.mmmi.common.data.world.GridPosition;
import dk.sdu.mmmi.common.data.world.World;
import dk.sdu.mmmi.common.services.entityproperties.IDamageable;

import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;


import java.nio.file.Paths;
import java.util.*;


import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.*;

public class BombTest {
    /**
     * Mock objects for testing.
     */

    private World mockWorld;
    private GameData mockGameData;
    private BombControlSystem bombControlSystem;

    private IDamageable mockDamageable;
    private Bomb mockBomb;
    private Explosion mockExplosion;

    /**
     * Setup the test environment.
     */
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

    /**
     * Create a mock blast area for testing.
     *
     * @return A collection of coordinates representing the blast area
     */
    private Collection<Coordinates> createMockBlastArea() {
        // Create a mock blast area
        Collection<Coordinates> blastArea = new ArrayList<>();
        // Populate the blast area with mock coordinates
        // Assume the bomb has a blast radius of 1 for simplicity
        blastArea.add(new Coordinates(0, 0)); // The bomb's location
        blastArea.add(new Coordinates(0, 1)); // North
        blastArea.add(new Coordinates(1, 0)); // East
        blastArea.add(new Coordinates(0, -1)); // South
        blastArea.add(new Coordinates(-1, 0)); // West
        return blastArea;
    }

    /**
     * Test that the bomb's state is set to EXPLODING when the time is up.
     */

    @Test
    public void testBombExplosionTimer() {
        // Arrange
        when(mockBomb.calculateTimeTillExplosion(mockGameData)).thenReturn(0f); // Fuse time is up

        // Act
        bombControlSystem.process(mockWorld, mockGameData);

        // Assert
        verify(mockBomb).setState(Bomb.State.EXPLODING);
    }

    /**
     * Test that a bomb creates an explosion entity for each coordinate in the blast area.
     */
    @Test
    public void testBombExplosionCreation() {
        // Arrange
        Collection<Coordinates> blastArea = createMockBlastArea();
        when(mockBomb.calculateTimeTillExplosion(mockGameData)).thenReturn(0f); // Fuse time is up
        when(mockBomb.calculateBlastArea(mockWorld)).thenReturn(blastArea); // Return a mock blast area
        when(mockBomb.getFireExplosionTexturePath(any(Coordinates.class)))
                .thenReturn(Paths.get("explosion.png")); // Return a path for explosion texture
        when(mockGameData.getScaler()).thenReturn(1f); // Return a scaler for the explosion

        // Act
        bombControlSystem.process(mockWorld, mockGameData);

        // Assert
        verify(mockBomb).setState(Bomb.State.EXPLODING);

        // Capture and verify the Explosions added to the world
        ArgumentCaptor<Explosion> explosionCaptor = ArgumentCaptor.forClass(Explosion.class);
        verify(mockWorld, times(blastArea.size())).addEntity(explosionCaptor.capture());

        List<Explosion> capturedExplosions = explosionCaptor.getAllValues();
        for (Explosion explosion : capturedExplosions) {
            System.out.println("Explosion Coordinates: " + explosion.getCoordinates());
            System.out.println("Explosion Texture Path: " + explosion.getTexturePath());
            System.out.println("Explosion Bomb: " + explosion.getBomb());
        }


        // Assert each coordinate in the blast area has a corresponding Explosion
        for (Coordinates coord : blastArea) {
            boolean matchFound = capturedExplosions.stream().anyMatch(explosion -> {
                boolean coordinatesMatch = explosion.getCoordinates().getGridPosition().equals(coord.getGridPosition());
                boolean texturePathMatch = explosion.getTexturePath().equals(Paths.get("explosion.png"));
                boolean bombMatch = explosion.getBomb() == mockBomb;

                // Output the result of each match check for debugging
                System.out.println("Matching " + coord + ": Coordinates match? " + coordinatesMatch
                        + ", Texture path match? " + texturePathMatch + ", Bomb match? " + bombMatch);

                return coordinatesMatch && texturePathMatch && bombMatch;
            });

            assertTrue("No explosion found for coordinate: " + coord, matchFound);
        }
    }

    /**
     * Test that bomb deals damage to damageable entities in the blast area.
     */
    @Test
    public void testDealDamageReducesDamagableHealth() {
        // Arrange
        int expectedDamage = 25; // Arbitrary value
        int initialHealth = 100;
        GridPosition entityPosition = new GridPosition(0, 1); // Entity's grid position

        // Mock an Entity that is also IDamageable
        Entity mockEntity = mock(Entity.class, withSettings().extraInterfaces(IDamageable.class));
        IDamageable damageableEntity = (IDamageable) mockEntity;

        // Setup the entity's grid position
        when(mockEntity.getGridPosition()).thenReturn(entityPosition);
        when(damageableEntity.getLifepoints()).thenReturn(initialHealth);

        // Setup for removeLifepoints call
        doNothing().when(damageableEntity).removeLifepoints(expectedDamage);

        // Mock the world to return the mockEntity when queried for entities
        when(mockWorld.getEntities()).thenReturn(new ArrayList<>(Collections.singletonList(mockEntity)));

        // Setup the bomb's behavior
        Collection<Coordinates> mockBlastArea = createMockBlastArea();
        when(mockBomb.calculateBlastArea(mockWorld)).thenReturn(mockBlastArea);
        when(mockBomb.getDamagePoints()).thenReturn(expectedDamage);

        // Act
        bombControlSystem.process(mockWorld, mockGameData);

        // Assert
        verify(damageableEntity).removeLifepoints(expectedDamage);
    }


}