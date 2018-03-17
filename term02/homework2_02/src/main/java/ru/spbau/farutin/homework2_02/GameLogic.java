package ru.spbau.farutin.homework2_02;

import ru.spbau.farutin.homework2_02.ai.AIPlayer;
import ru.spbau.farutin.homework2_02.ai.AIPlayerOneStepPlanning;
import ru.spbau.farutin.homework2_02.ai.AIPlayerRandom;
import ru.spbau.farutin.homework2_02.controllers.GameFieldController;
import ru.spbau.farutin.homework2_02.util.Field;
import ru.spbau.farutin.homework2_02.util.Pair;
import ru.spbau.farutin.homework2_02.util.Stats;

/**
 * GameLogic - class for handling players turns and moves.
 */
public class GameLogic {
    private static Field field;
    private static boolean withAI;
    private static AIPlayer aiPlayer;
    private static int currentPlayer;
    private static boolean finished;

    /**
     * Start a new hot seat game.
     */
    public static void startHotSeat() {
        field = new Field(3);
        withAI = false;
        currentPlayer = 0;
        finished = false;
    }

    /**
     * Starts a new game with AI.
     * @param level level of AI to play with
     */
    public static void startAI(int level) {
        field = new Field(3);
        withAI = true;
        currentPlayer = 0;
        finished = false;

        if (level == 0) {
            aiPlayer = new AIPlayerRandom();
        } else {
            aiPlayer = new AIPlayerOneStepPlanning();
        }
    }

    /**
     * Tries to make move with given coordinates.
     * If attempt is successful, game is not finished and opponent is AI,
     * then makes opponent's move as well.
     * @param x first coordinate
     * @param y second coordinate
     */
    public static void moveRequest(int x, int y) {
        if (!finished && field.makeMove(currentPlayer, x, y)) {
            GameFieldController controller = (GameFieldController) UI.getController();

            controller.fillCell(currentPlayer, x, y);

            if (field.checkWin(currentPlayer)) {
                controller.playerWin(currentPlayer);
                finished = true;

                if (withAI) {
                    if (currentPlayer == 0) {
                        Stats.win();
                    } else {
                        Stats.lose();
                    }
                }

                return;
            }

            if (field.isFull()) {
                controller.playerWin(-1);
                finished = true;

                if (withAI) {
                    Stats.draw();
                }

                return;
            }

            currentPlayer = 1 - currentPlayer;
            controller.playerTurn(currentPlayer);

            if (currentPlayer == 1 && withAI) {
                Pair pair = aiPlayer.chooseMove(field);
                moveRequest((Integer) pair.first(), (Integer) pair.second());
            }
        }
    }
}
