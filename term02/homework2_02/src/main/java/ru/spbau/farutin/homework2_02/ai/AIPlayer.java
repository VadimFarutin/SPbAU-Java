package ru.spbau.farutin.homework2_02.ai;

import org.jetbrains.annotations.NotNull;
import ru.spbau.farutin.homework2_02.util.Field;
import ru.spbau.farutin.homework2_02.util.Pair;

/**
 * AIPlayer - interface for AI opponent.
 */
public interface AIPlayer {
    /**
     * Analyses current situation and chooses a cell.
     * @param field current field
     * @return pair of chosen coordinates
     */
    @NotNull Pair<Integer, Integer> chooseMove(@NotNull Field field);
}
