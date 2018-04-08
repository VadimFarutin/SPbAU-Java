package ru.spbau.farutin.homework2_02.ai;

import org.junit.Test;
import ru.spbau.farutin.homework2_02.util.Field;
import ru.spbau.farutin.homework2_02.util.Pair;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.*;

/**
 * Tests for AIPlayerOneStepPlanning.
 */
public class AIPlayerOneStepPlanningTest {
    /**
     * Checks that AI choose winning move if possible.
     */
    @Test
    public void testChooseMoveAIWins() throws Exception {
        Field field = new Field(3);
        field.makeMove(0, 0, 0);
        field.makeMove(0, 0, 1);
        field.makeMove(1, 1, 0);
        field.makeMove(1, 1, 2);

        AIPlayerOneStepPlanning ai = new AIPlayerOneStepPlanning();
        Pair<Integer, Integer> move = ai.chooseMove(field);

        assertThat(move.first(), is(1));
        assertThat(move.second(), is(1));
    }

    /**
     * Checks that AI won't let player win if possible.
     */
    @Test
    public void testChooseMovePlayerNotWins() throws Exception {
        Field field = new Field(3);
        field.makeMove(0, 0, 0);
        field.makeMove(0, 0, 1);
        field.makeMove(1, 1, 0);
        field.makeMove(1, 2, 1);

        AIPlayerOneStepPlanning ai = new AIPlayerOneStepPlanning();
        Pair<Integer, Integer> move = ai.chooseMove(field);

        assertThat(move.first(), is(0));
        assertThat(move.second(), is(2));
    }
}
