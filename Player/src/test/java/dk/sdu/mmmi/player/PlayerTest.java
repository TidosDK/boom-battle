package dk.sdu.mmmi.player;

import dk.sdu.mmmi.common.data.entity.Coordinates;
import dk.sdu.mmmi.common.data.entity.Entity;
import dk.sdu.mmmi.common.data.gameproperties.GameData;
import dk.sdu.mmmi.common.data.gameproperties.GameKeys;
import dk.sdu.mmmi.common.data.world.Map;
import dk.sdu.mmmi.common.data.world.World;
import dk.sdu.mmmi.common.weapon.IWeapon;
import dk.sdu.mmmi.common.services.IGamePluginService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.ServiceLoader;

import static java.util.stream.Collectors.toList;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;

/**
 * This class is used to test the Player class.
 */
public class PlayerTest {

    private Player underlyingPlayer;
    private Player mockPlayer;
    private GameData gameData;
    private GameKeys gameKeys;
    private World world;
    private Path texture;

    @BeforeEach
    void setup() {
        // Creates a new player and mocks the player, game data, game keys, and world.
        texture = Paths.get("Player/src/main/resources/personLeft1.png");
        underlyingPlayer = new Player(texture, 2f, 2f);
        mockPlayer = mock(Player.class);
        gameData = mock(GameData.class);
        gameKeys = mock(GameKeys.class);
        world = mock(World.class);

        // Sets the game data's delta time and scaler.
        when(gameData.getDeltaTime()).thenReturn(0.016f);
        when(gameData.getScaler()).thenReturn(1.6f);

        // Sets the game data and the game keys.
        when(gameData.getKeys()).thenReturn(gameKeys);
        when(gameKeys.getUp()).thenReturn(0);
        when(gameKeys.getRight()).thenReturn(1);
        when(gameKeys.getDown()).thenReturn(2);
        when(gameKeys.getLeft()).thenReturn(3);

        // Assigns a mock Map to the World mock.
        when(world.getMap()).thenReturn(mock(Map.class));
        when(world.getMap().getWidth()).thenReturn(10);
        when(world.getMap().getHeight()).thenReturn(10);
    }

    // Verifies functional requirement F-01
    @Test()
    void testPlayerExists() {
        // Mocks the world to add entities and retrieve the list of entities
        doAnswer(invocation -> {
            Entity entity = invocation.getArgument(0);
            world.getEntities().add(entity);
            return null;
        }).when(world).addEntity(any(Entity.class));
        when(world.getEntities()).thenReturn(new ArrayList<>());

        // This uses the ServiceLoader getter-method for IGamePluginService which is found in the Core module
        for (IGamePluginService plugin : ServiceLoader.load(IGamePluginService.class).stream().map(ServiceLoader.Provider::get).collect(toList())) {
            plugin.start(world, gameData);
        }

        // Checks if the player exists in the world
        boolean playerExists = false;
        for (Entity entity : world.getEntities()) {
            if (entity.getClass().equals(Player.class)) {
                playerExists = true;
                break;
            }
        }

        // Asserts that the player exists in the world
        assertTrue(playerExists);
    }

    // Verifies functional requirement F-01a
    @Test()
    void testMovement() {
        // Configures all keys to not be pressed.
        when(gameData.getKeys().isDown(gameData.getKeys().getUp())).thenReturn(false);
        when(gameData.getKeys().isDown(gameData.getKeys().getRight())).thenReturn(false);
        when(gameData.getKeys().isDown(gameData.getKeys().getDown())).thenReturn(false);
        when(gameData.getKeys().isDown(gameData.getKeys().getLeft())).thenReturn(false);

        // Adds the player to the world.
        List<Entity> players = new ArrayList<>();
        players.add(underlyingPlayer);
        when(world.getEntities(Player.class)).thenReturn(players);

        // Asserts that the player is in the world.
        assertTrue(world.getEntities(Player.class).contains(underlyingPlayer));

        // Sets the coordinates of the player.
        int startXCoordinate = 5;
        int startYCoordinate = 5;
        underlyingPlayer.setCoordinates(new Coordinates(startXCoordinate, startYCoordinate));
        when(mockPlayer.getCoordinates()).thenReturn(underlyingPlayer.getCoordinates());

        // Asserts that the player's coordinates are (2, 2).
        assertEquals(startXCoordinate, mockPlayer.getCoordinates().getX());
        assertEquals(startYCoordinate, mockPlayer.getCoordinates().getY());

        // Declares the player control system.
        PlayerControlSystem playerControlSystem = new PlayerControlSystem();

        // Asserts that the player has moved up.
        float previousYCoordinate = mockPlayer.getCoordinates().getY();
        when(gameData.getKeys().isDown(gameData.getKeys().getUp())).thenReturn(true);
        playerControlSystem.process(world, gameData);
        assertTrue(previousYCoordinate < mockPlayer.getCoordinates().getY());
        when(gameData.getKeys().isDown(gameData.getKeys().getUp())).thenReturn(false);

        // Asserts that the player has moved down.
        previousYCoordinate = mockPlayer.getCoordinates().getY();
        when(gameData.getKeys().isDown(gameData.getKeys().getDown())).thenReturn(true);
        playerControlSystem.process(world, gameData);
        assertTrue(previousYCoordinate > mockPlayer.getCoordinates().getY());
        when(gameData.getKeys().isDown(gameData.getKeys().getDown())).thenReturn(false);

        // Asserts that the player has moved right.
        float previousXCoordinate = mockPlayer.getCoordinates().getX();
        when(gameData.getKeys().isDown(gameData.getKeys().getRight())).thenReturn(true);
        playerControlSystem.process(world, gameData);
        assertTrue(previousXCoordinate < mockPlayer.getCoordinates().getX());
        when(gameData.getKeys().isDown(gameData.getKeys().getRight())).thenReturn(false);

        // Asserts that the player has moved left.
        previousXCoordinate = mockPlayer.getCoordinates().getX();
        when(gameData.getKeys().isDown(gameData.getKeys().getLeft())).thenReturn(true);
        playerControlSystem.process(world, gameData);
        assertTrue(previousXCoordinate > mockPlayer.getCoordinates().getX());
        when(gameData.getKeys().isDown(gameData.getKeys().getLeft())).thenReturn(false);
    }

    // Verifies functional requirement F-01c
    @Test()
    void testWeapons() {
        // Asserts the mock player hasn't placed any weapons.
        assertTrue(mockPlayer.getWeapons().isEmpty(), "The list should be empty as the player hasn't placed any weapons yet.");

        // Adds a weapon to the underlying player.
        when(mockPlayer.getWeapons()).thenReturn(underlyingPlayer.getWeapons());
        IWeapon weapon = mock(IWeapon.class);
        underlyingPlayer.getWeapons().add(weapon);

        // Asserts that the mock player has one weapon.
        assertTrue(mockPlayer.getWeapons().contains(weapon) && mockPlayer.getWeapons().size() == 1, "The list should only contain one weapon as the underlying player has added one weapon.");

        // Removes the weapon from the underlying player.
        underlyingPlayer.removeWeapon(weapon);

        // Asserts that the mock player has no weapons.
        assertEquals(0, mockPlayer.getWeapons().size(), "The list should be empty as the underlying player has removed the weapon.");
    }

    // Verifies functional requirement F-01d & some part of F-01e
    @Test()
    void testPlaceWeapon() {
        // Adds the player to the world and asserts that the player is in the world.
        List<Entity> players = new ArrayList<>();
        players.add(underlyingPlayer);
        when(world.getEntities(Player.class)).thenReturn(players);

        // Defines a list for the mock entities in the world.
        ArrayList<Entity> entities = new ArrayList<>();

        // Configures world.
        when(world.getEntities()).thenReturn(entities);
        doAnswer(invocation -> {
            world.getEntities().add(invocation.getArgument(0));
            return null;
        }).when(world).addEntity(any(Entity.class));

        // Defines the underlying PlayerControlSystem for which the mockPlayerControlSystem will use.
        TestPlayerControlSystem underlyingPlayerControlSystem = new TestPlayerControlSystem();
        underlyingPlayerControlSystem.setMaxWeapons(10);

        // Defines the mock PlayerControlSystem.
        TestPlayerControlSystem mockPlayerControlSystem = mock(TestPlayerControlSystem.class);
        List<IWeapon> mockWeapons = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            mockWeapons.add(mock(MockWeapon.class));
        }

        // Configures the mocking behavior of mockPlayer
        when(mockPlayer.getWeapons()).thenReturn(underlyingPlayer.getWeapons());

        // Configures the mocking behavior of mockPlayerControlSystem
        doAnswer(invocation -> {
            underlyingPlayerControlSystem.process(world, gameData);
            return null;
        }).when(mockPlayerControlSystem).process(world, gameData);


        // Adds 10 weapons to the player and asserts that the player has 10 weapons.
        // Also assures that weapons are only placed when the space key is pressed.
        for (int expectedWeaponsInList = 1; expectedWeaponsInList <= underlyingPlayerControlSystem.getMaxWeapons(); expectedWeaponsInList++) {
            // Mocks the space key to be pressed.
            when(gameData.getKeys().isPressed(gameData.getKeys().getSpace())).thenReturn(true);

            // Processes PlayerControlSystem to place a weapon.
            mockPlayerControlSystem.process(world, gameData);

            // Asserts the player has "expectedWeaponsInList".
            assertEquals(expectedWeaponsInList, mockPlayer.getWeapons().size());

            // Mocks the space key to be unpressed and processes PlayerControlSystem to test if it places a weapon.
            when(gameData.getKeys().isPressed(gameData.getKeys().getSpace())).thenReturn(false);
            mockPlayerControlSystem.process(world, gameData);
        }

        // Does a final check to see if the player can place more weapons than the maxWeapons.
        when(gameData.getKeys().isPressed(gameData.getKeys().getSpace())).thenReturn(true);
        mockPlayerControlSystem.process(world, gameData);
        assertEquals(10, mockPlayer.getWeapons().size());
    }

    // Verifies functional requirement F-01f
    @Test()
    void testTakeDamage() {
        // Arrange
        underlyingPlayer.setLifepoints(2);
        ArrayList<Entity> mockEntityList = new ArrayList<>();

        // Mock the world to return the player above list of entities
        doAnswer(invocation -> {
            ArrayList<Entity> entities = new ArrayList<>();
            for (Entity entity : mockEntityList) {
                if (entity.getClass().equals(Player.class)) {
                    entities.add(entity);
                }
            }
            return entities;
        }).when(world).getEntities(Player.class);

        // Mock world.removeEntity, using the mock list from above
        doAnswer(invocation -> {
            mockEntityList.remove(invocation.getArgument(0));
            return null;
        }).when(world).removeEntity(any(Entity.class));

        // Act
        mockEntityList.add(underlyingPlayer); // Add the player to the mock list.

        PlayerControlSystem playerControlSystem = new PlayerControlSystem();
        playerControlSystem.process(world, gameData); // Should do nothing.

        underlyingPlayer.removeLifepoints(1); // Should take the player to 1 Life points.
        assertEquals(1, underlyingPlayer.getLifepoints());

        underlyingPlayer.removeLifepoints(1); // Should take the player to 0 Life points, killing them.
        assertEquals(0, underlyingPlayer.getLifepoints());
        playerControlSystem.process(world, gameData); // Should remove the player from the world.

        // Asserts the player has been removed from the world.
        assertTrue(mockEntityList.isEmpty());
    }
}
