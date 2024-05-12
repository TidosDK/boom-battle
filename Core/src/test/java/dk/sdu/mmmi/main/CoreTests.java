package dk.sdu.mmmi.main;

import dk.sdu.mmmi.common.data.entity.Entity;
import dk.sdu.mmmi.common.data.world.World;

import static org.junit.jupiter.api.Assertions.assertEquals;

import dk.sdu.mmmi.common.services.entityproperties.IActor;
import org.junit.jupiter.api.Test;

public class CoreTests {
    @Test
    public void multipleActorsTest() {
        // Arrange
        Main main = new Main();
        main.create();

        // Act
        World world = World.getInstance();

        // Assert
        int expectedAmountOfIActorEntities = 2;

        for (Entity entity : world.getEntities()) {
            if (expectedAmountOfIActorEntities == 0) {
                break;
            }
            if (entity instanceof IActor) {
                expectedAmountOfIActorEntities--;
            }
        }

        assertEquals(0, expectedAmountOfIActorEntities);
        main.render();
    }
}
