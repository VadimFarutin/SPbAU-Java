package ru.spbau.farutin.homework2_02.ai;

import org.junit.Test;
import ru.spbau.farutin.homework2_02.util.Field;
import ru.spbau.farutin.homework2_02.util.Pair;

import static org.junit.Assert.*;

/**
 * Tests for AIPlayerRandom.
 */
public class AIPlayerRandomTest {
    /**
     * Checks that AI chooses free cell.
     */
    @Test
    public void testChooseMove() throws Exception {
        Field field = new Field(3);
        field.makeMove(0, 1, 2);
        field.makeMove(1, 2, 0);
        field.makeMove(0, 0, 1);
        field.makeMove(1, 2, 2);

        AIPlayerRandom ai = new AIPlayerRandom();
        Pair<Integer, Integer> move = ai.chooseMove(field);

        assertTrue(field.isCellFree(move.first(), move.second()));
    }
}
