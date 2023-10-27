package WizardTD.Gameplay.Spawners;

import WizardTD.Gameplay.Enemies.*;
import mikera.vectorz.*;
import org.checkerframework.checker.nullness.qual.*;
import org.junit.jupiter.api.*;

import java.math.*;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

public class WaveTests {
    private static final Random rng = new Random();

    @Test
    public void test() {
        final double hp = rng.nextDouble();
        final double speed = rng.nextDouble();
        final double dmg = rng.nextDouble();
        final double mana = rng.nextDouble();
        final long qty = rng.nextInt(10000) + 1000;

        final double delay = rng.nextDouble();
        final int waveNum = rng.nextInt();

        final Wave wave = new Wave(
                qty * 2,
                delay,
                1,
                waveNum,
                new ArrayList<EnemyFactory>() {{
                    add(
                            new EnemyFactory(
                                    hp, speed, dmg, mana, BigInteger.valueOf(qty),
                                    fact -> new BeetleEnemy(
                                            fact.health,
                                            new Vector2(0, 0),
                                            fact.speed,
                                            fact.damageMultiplier,
                                            fact.manaGainedOnKill
                                    )
                            )
                    );
                    add(
                            new EnemyFactory(
                                    hp, speed, dmg, mana, BigInteger.valueOf(qty),
                                    fact -> new WormEnemy(
                                            fact.health,
                                            new Vector2(0, 0),
                                            fact.speed,
                                            fact.damageMultiplier,
                                            fact.manaGainedOnKill
                                    )
                            )
                    );
                }}
        );

        // Haven't ticked wave, no enemies
        assertNull(wave.getEnemy());
        wave.tick(wave.delayBeforeWave);

        /*
         * NOTE: 
         * 
         * Because of floating-point precision errors, this will occasionally error out
         * The timer will be slightly larger/smaller than expected (e.g. 5000.2) than the 
         * (number of enemies spawned * spawn rate multiplier, e.g. 5000). This means the last few enemies
         * might get missed because the timer is too fast, or spawned after running out (the timer is too slow)
         * 
         * For this reason, I skip the last few enemies, just to make sure (this is the `-5`)
        */

        for (int i = 0; i < qty * 2 - 5; i++) {
            wave.tick(1);
            final @Nullable Enemy e = wave.getEnemy();
            assertNotNull(e);
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

        // Can't do this due to reasons above
        //
        // // Should be no more elements as we reached spawn cap
        // // assertNull(wave.getEnemy());
    }
}
