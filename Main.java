import java.util.Scanner;

public class Main {

    public static void main(String[] args){

        Scanner sc = new Scanner(System.in);
        TicTacToeFeeld ticTacToeFeeld = new TicTacToeFeeld(3);
        int x = 0, y = 0;
        try {
            do {
                System.out.printf("Enter your X-position 1-%d:\n", ticTacToeFeeld.getGameFeeld().length);
                x = sc.nextInt();
                //                Проверка на попадание в поле игры
                if (0 > x | x > ticTacToeFeeld.getGameFeeld().length) {
                    System.out.println("Please! Enter X-position from 1 to 3!!!!");
                    continue;
                }
                System.out.printf("Enter your Y-position 1-%d:\n", ticTacToeFeeld.getGameFeeld().length);
                y = sc.nextInt();
                //                Проверка на попадание в поле игры
                if (0 > y | y > ticTacToeFeeld.getGameFeeld().length) {
                    System.out.println("Please! Enter Y-position from 1 to 3!!!!");
                    continue;
                }
                ticTacToeFeeld.setNextStep("X", x, y);
                System.out.println(ticTacToeFeeld.toString() + "gameState = " + ticTacToeFeeld.isGameState());
                //Проверка работы матрицы ожидаемого хода противника
                System.out.println(ticTacToeFeeld.toAIString());
                //                Обратный отсчет количества ходов
                System.out.println(ticTacToeFeeld.getCounter());
            } while (!ticTacToeFeeld.isGameState());
        } finally {
            System.out.printf("\n\nState of game is " + ticTacToeFeeld.isGameState() + ":\nWinner: %s\nGame field:\n%s",
                    ticTacToeFeeld.getWinnerLabel(), ticTacToeFeeld.toString());
            sc.close();
        }
    }
}