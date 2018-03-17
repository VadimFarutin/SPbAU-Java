package ru.spbau.farutin.homework2_02.ai;

import org.jetbrains.annotations.NotNull;
import ru.spbau.farutin.homework2_02.util.Field;
import ru.spbau.farutin.homework2_02.util.Pair;

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
    public @NotNull Pair<Integer, Integer> chooseMove(@NotNull Field field) {
        int size = field.getSize();
        int x, y;

        while (true) {
            x = random.nextInt(size);
            y = random.nextInt(size);

            if (field.isCellFree(x, y)) {
                break;
            }
        }

        return new Pair<>(x, y);
    }
}
