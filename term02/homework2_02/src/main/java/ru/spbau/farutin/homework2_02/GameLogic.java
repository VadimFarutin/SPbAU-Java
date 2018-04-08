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
    private static GameLogic instance = new GameLogic();

    private Field field;
    private boolean withAI;
    private AIPlayer aiPlayer;
    private int currentPlayer;
    private boolean finished;

    public static GameLogic getInstance() {
        return instance;
    }

    /**
     * Start a new hot seat game.
     */
    public void startHotSeat() {
        field = new Field(3);
        withAI = false;
        currentPlayer = 0;
        finished = false;
    }

    /**
     * Starts a new game with AI.
     * @param level level of AI to play with
     */
    public void startAI(int level) {
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
    public void requestMove(int x, int y) {
        if (!finished && field.makeMove(currentPlayer, x, y)) {
            GameFieldController controller = (GameFieldController) UI.getController();

            controller.fillCell(currentPlayer, x, y);

            if (field.checkWin(currentPlayer)) {
                controller.setPlayerWin(currentPlayer);
                finished = true;

                if (withAI) {
                    if (currentPlayer == 0) {
                        Stats.addWin();
                    } else {
                        Stats.addLose();
                    }
                }

                return;
            }

            if (field.isFull()) {
                controller.setPlayerWin(-1);
                finished = true;

                if (withAI) {
                    Stats.addDraw();
                }

                return;
            }

            currentPlayer = 1 - currentPlayer;
            controller.changePlayerTurn(currentPlayer);

            if (currentPlayer == 1 && withAI) {
                Pair pair = aiPlayer.chooseMove(field);
                requestMove((Integer) pair.first(), (Integer) pair.second());
            }
        }
    }
}
