package ru.spbau.farutin.test2_03;

import ru.spbau.farutin.test2_03.ai.AIPlayer;
import ru.spbau.farutin.test2_03.ai.AIPlayerOneStepPlanning;
import ru.spbau.farutin.test2_03.ai.AIPlayerRandom;
import ru.spbau.farutin.test2_03.controllers.GameFieldController;
import ru.spbau.farutin.test2_03.network.Client;
import ru.spbau.farutin.test2_03.util.Field;
import ru.spbau.farutin.test2_03.util.Pair;
import ru.spbau.farutin.test2_03.util.Stats;

/**
 * GameLogic - class for handling players turns and moves.
 */
public class GameLogic {
    private static GameLogic instance = new GameLogic();

    private Field field;
    private boolean withAI;
    private boolean network;
    private AIPlayer aiPlayer;
    private int currentPlayer;
    private int thisPlayer;
    private boolean finished;

    private Client client;

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
        network = false;
    }

    /**
     * Start a new network game as first player.
     */
    public void startFirst() {
        field = new Field(3);
        withAI = false;
        currentPlayer = 0;
        thisPlayer = 0;
        finished = false;
        network = true;
    }

    /**
     * Start a new network game as second player.
     */
    public void startSecond() {
        field = new Field(3);
        withAI = false;
        currentPlayer = 0;
        thisPlayer = 1;
        finished = false;
        network = true;
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
        network = false;

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
        if (!finished
            && (!network || currentPlayer == thisPlayer)
            && field.makeMove(currentPlayer, x, y))
        {
            GameFieldController controller = (GameFieldController) UI.getController();

            controller.fillCell(currentPlayer, x, y, network);

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
