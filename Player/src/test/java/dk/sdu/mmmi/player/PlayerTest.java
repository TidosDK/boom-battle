package dk.sdu.mmmi.player;

import dk.sdu.mmmi.common.data.Entity.Coordinates;
import dk.sdu.mmmi.common.data.Entity.Entity;
import dk.sdu.mmmi.common.data.Properties.GameData;
import dk.sdu.mmmi.common.data.Properties.GameKeys;
import dk.sdu.mmmi.common.data.World.World;
import dk.sdu.mmmi.common.data.World.Map;
import dk.sdu.mmmi.common.services.Entity.Weapon.IWeapon;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

/**
 * This class is used to test the Player class.
 */
public class PlayerTest {

    Player underlyingPlayer;
    Player mockPlayer;
    GameData gameData;
    GameKeys gameKeys;
    World world;

    @BeforeEach
    void setup() {
        // Creates a new player and mocks the player, game data, game keys, and world.
        underlyingPlayer = new Player("Player/src/main/resources/personLeft1.png", 2f, 2f);
        mockPlayer = mock(Player.class);
        gameData = mock(GameData.class);
        gameKeys = mock(GameKeys.class);
        world = mock(World.class);

        // Sets the game data's delta time and scaler.
        when(gameData.getDeltaTime()).thenReturn(0.016f);
        when(gameData.getScaler()).thenReturn(1.6f);

        // Sets the game data and the game keys.
        when(gameData.getKeys()).thenReturn(gameKeys);
        when(gameKeys.getUP()).thenReturn(0);
        when(gameKeys.getRIGHT()).thenReturn(1);
        when(gameKeys.getDOWN()).thenReturn(2);
        when(gameKeys.getLEFT()).thenReturn(3);

        // Assigns a mock Map to the World mock.
        when(world.getMap()).thenReturn(mock(Map.class));
        when(world.getMap().getWidth()).thenReturn(10);
        when(world.getMap().getHeight()).thenReturn(10);
    }

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

    @Test()
    void testMovement() {
        // Configures all keys to not be pressed.
        when(gameData.getKeys().isDown(gameData.getKeys().getUP())).thenReturn(false);
        when(gameData.getKeys().isDown(gameData.getKeys().getRIGHT())).thenReturn(false);
        when(gameData.getKeys().isDown(gameData.getKeys().getDOWN())).thenReturn(false);
        when(gameData.getKeys().isDown(gameData.getKeys().getLEFT())).thenReturn(false);

        // Adds the player to the world.
        List<Entity> players = new ArrayList<>();
        players.add(underlyingPlayer);
        when(world.getEntities(Player.class)).thenReturn(players);

        // Asserts that the player is in the world.
        assertTrue(world.getEntities(Player.class).contains(underlyingPlayer));

        // Sets the coordinates of the player.
        int start_x_coordinate = 5;
        int start_y_coordinate = 5;
        underlyingPlayer.setCoordinates(new Coordinates(start_x_coordinate, start_y_coordinate));
        when(mockPlayer.getCoordinates()).thenReturn(underlyingPlayer.getCoordinates());

        // Asserts that the player's coordinates are (2, 2).
        assertEquals(start_x_coordinate, mockPlayer.getCoordinates().getX());
        assertEquals(start_y_coordinate, mockPlayer.getCoordinates().getY());

        // Declares the player control system.
        PlayerControlSystem playerControlSystem = new PlayerControlSystem();

        // Asserts that the player has moved up.
        float previous_y_coordinate = mockPlayer.getCoordinates().getY();
        when(gameData.getKeys().isDown(gameData.getKeys().getUP())).thenReturn(true);
        playerControlSystem.process(world, gameData);
        assertTrue(previous_y_coordinate < mockPlayer.getCoordinates().getY());
        when(gameData.getKeys().isDown(gameData.getKeys().getUP())).thenReturn(false);

        // Asserts that the player has moved down.
        previous_y_coordinate = mockPlayer.getCoordinates().getY();
        when(gameData.getKeys().isDown(gameData.getKeys().getDOWN())).thenReturn(true);
        playerControlSystem.process(world, gameData);
        assertTrue(previous_y_coordinate > mockPlayer.getCoordinates().getY());
        when(gameData.getKeys().isDown(gameData.getKeys().getDOWN())).thenReturn(false);

        // Asserts that the player has moved right.
        float previous_x_coordinate = mockPlayer.getCoordinates().getX();
        when(gameData.getKeys().isDown(gameData.getKeys().getRIGHT())).thenReturn(true);
        playerControlSystem.process(world, gameData);
        assertTrue(previous_x_coordinate < mockPlayer.getCoordinates().getX());
        when(gameData.getKeys().isDown(gameData.getKeys().getRIGHT())).thenReturn(false);

        // Asserts that the player has moved left.
        previous_x_coordinate = mockPlayer.getCoordinates().getX();
        when(gameData.getKeys().isDown(gameData.getKeys().getLEFT())).thenReturn(true);
        playerControlSystem.process(world, gameData);
        assertTrue(previous_x_coordinate > mockPlayer.getCoordinates().getX());
        when(gameData.getKeys().isDown(gameData.getKeys().getLEFT())).thenReturn(false);
    }

    @Test()
    void testTakeDamage() {
        // Arrange

        underlyingPlayer.setLifepoints(1);
        ArrayList<Entity> mockEntityList = new ArrayList<>();

        // Mock the world to return the player above list of entities
        doAnswer(invocation -> {
            ArrayList<Entity> entities = new ArrayList<>();
            for (Entity entity : mockEntityList) {
                if(entity.getClass().equals(Player.class)) {
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

        mockEntityList.add(underlyingPlayer); // Add the player to the mock list

        PlayerControlSystem playerControlSystem = new PlayerControlSystem();
        playerControlSystem.process(world, gameData); // Should do nothing
        underlyingPlayer.removeLifepoints(1); // Should take the player to 0 Life points, killing them.
        playerControlSystem.process(world, gameData); // Should remove the player from the world

        // Assert

        assertTrue(mockEntityList.isEmpty());
    }
}
