package WizardTD.Gameplay.Spawners;

import WizardTD.Gameplay.Enemies.*;
import mikera.vectorz.*;
import org.junit.jupiter.api.*;

import java.math.*;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

public class EnemyFactoryTests {
    private static final Random rng = new Random();

    @Test
    public void spawns() {
        final double hp = rng.nextDouble();
        final double speed = rng.nextDouble();
        final double dmg = rng.nextDouble();
        final double mana = rng.nextDouble();
        final long qty = rng.nextInt(10000);

        final EnemyFactory fac = new EnemyFactory(
                hp, speed, dmg, mana, BigInteger.valueOf(qty),
                fact -> new GremlinEnemy(
                        fact.health,
                        new Vector2(0, 0),
                        fact.speed,
                        fact.damageMultiplier,
                        fact.manaGainedOnKill
                )
        );

        for (int i = 0; i < qty; i++) {
            final Enemy e = fac.spawnEnemy();
            assertAll(
                    () -> assertTrue(e.isAlive),
                    () -> assertEquals(hp, e.health),
                    () -> assertEquals(hp, e.maxHealth),
                    () -> assertEquals(0, e.pathProgress),
                    () -> assertEquals(0, e.position.x),
                    () -> assertEquals(0, e.position.y),
                    () -> assertEquals(speed, e.speed),
                    () -> assertEquals(dmg, e.damageMultiplier),
                    () -> assertEquals(mana, e.manaGainedOnKill),
                    () -> assertNull(e.path)
            );
        }
        
        // Should be no more elements as we reached spawn cap
        assertThrows(NoSuchElementException.class, fac::spawnEnemy);
    }
}
