package ru.spbau.farutin.test2_03.ai;

import org.jetbrains.annotations.NotNull;
import ru.spbau.farutin.test2_03.util.Field;
import ru.spbau.farutin.test2_03.util.Pair;

import java.util.Random;

/**
 * AIPlayerRandom - simplest AIPlayer implementation.
 */
public class AIPlayerRandom implements AIPlayer {
    private Random random = new Random();

    /**
     * Chooses a random free cell.
     * @param field current field
     * @return pair of chosen coordinates
     */
    @Override
    public @NotNull
    Pair<Integer, Integer> chooseMove(@NotNull Field field) {
        int size = field.getSize();

        while (true) {
            int x = random.nextInt(size);
            int y = random.nextInt(size);

            if (field.isCellFree(x, y)) {
                return new Pair<>(x, y);
            }
        }
    }
}
