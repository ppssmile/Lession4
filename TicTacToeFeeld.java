public class TicTacToeFeeld {
    private final String labelAI = "O";
    private String[][] gameFeeld;
    private int[][] stateOfAI;
    private boolean gameState;
    private String winnerLabel;
    private int counter;

    TicTacToeFeeld() {
        gameFeeld = new String[][]{{"-", "-", "-",}, {"-", "-", "-",}, {"-", "-", "-",}};
        stateOfAI = new int[gameFeeld.length][gameFeeld.length];
        counter = 9;
        for (int i = 0; i < gameFeeld.length; i++) {
            for (int j = 0; j <gameFeeld.length; j++) {
                stateOfAI[i][j] = 0;
            }
        }
    }

    TicTacToeFeeld(int rangeField) {
        gameFeeld = new String[rangeField][rangeField];
        stateOfAI = new int[rangeField][rangeField];
        for (int i = 0; i < rangeField; i++) {
            for (int j = 0; j < rangeField; j++) {
                gameFeeld[i][j] = "-";
                stateOfAI = new int[gameFeeld.length][gameFeeld.length];
            }
        }
        counter = rangeField * rangeField;
        stateOfAI = new int[gameFeeld.length][gameFeeld.length];
    }

    public String[][] getGameFeeld() {
        return this.gameFeeld;
    }

    public int getCounter(){
        return counter;
    }

    public String getWinnerLabel() {
        return winnerLabel;
    }

    public void setNextStep(String playerLabel, int xPosition, int yPosition) {
        if (isEmpty(xPosition - 1, yPosition - 1)) {
            this.gameFeeld[xPosition - 1][yPosition - 1] = playerLabel;
            counter--;
            checkToWinner(playerLabel, xPosition, yPosition);
            createMaxPlayerLabel();
            doStepAI();
        }
    }

    public void checkToWinner(String playerLabel, int xPosition, int yPosition) {
        if (doCheckToXLine(playerLabel, xPosition - 1) | doCheckToYLine(playerLabel, yPosition - 1) | checkPositiveDiagonal(playerLabel, xPosition - 1, yPosition - 1) | checkNegativeDiagonal(playerLabel, xPosition - 1, yPosition - 1)) {
            this.gameState = true;
            this.winnerLabel = playerLabel;
            return;
        }
        if (doCheckToXLine("O", xPosition - 1) | doCheckToYLine("O", yPosition - 1) | checkPositiveDiagonal("O", xPosition - 1, yPosition - 1) | checkNegativeDiagonal("O", xPosition - 1, yPosition - 1)) {
            this.gameState = true;
            this.winnerLabel = "O";
            return;
        }
        if (counter == 0 && !this.gameState) {
            this.gameState = true;
            this.winnerLabel = "Nobody won!!!";
            return;
        }

    }

    @Override
    public String toString() {
        String formatString = new String();
        for (int i = 0; i < gameFeeld.length; i++) {
            for (int j = 0; j < gameFeeld[0].length; j++) {
                formatString = formatString + String.format("| %s ", this.gameFeeld[i][j]);
            }
            formatString = formatString + String.format("|\n");
        }
        return formatString;
    }

    public String toAIString() {
        String formatString = new String();
        for (int i = 0; i < gameFeeld.length; i++) {
            for (int j = 0; j < gameFeeld[0].length; j++) {
                formatString = formatString + String.format("| %d ", this.stateOfAI[i][j]);
            }
            formatString = formatString + String.format("|\n");
        }
        return formatString;
    }

    public void doStepAI() {
        int max1 = 0;
        if(counter!=0){
            for (int i = 0; i < gameFeeld.length; i++) {
                for (int j = 0; j < gameFeeld.length; j++) {
                    max1 = Math.max(max1, stateOfAI[i][j]);
                }
            }
        }
        if (counter==(gameFeeld.length*gameFeeld.length-1)){
            if (isEmpty(gameFeeld.length/2,gameFeeld.length/2)){
                gameFeeld[gameFeeld.length/2][gameFeeld.length/2]=labelAI;
                counter--;
                return;
            }else {
                gameFeeld[0][0]=labelAI;
                counter--;
                return;
            }
        }
        for (int i = 0; i < gameFeeld.length; i++) {
            for (int j = 0; j < gameFeeld.length; j++) {
                if (stateOfAI[i][j]==max1) {
                    gameFeeld[i][j]=labelAI;
                    counter--;
                    return;
                }
            }
        }
    }

    public boolean isEmpty(int xPosition, int yPosition) {
        if (this.gameFeeld[xPosition][yPosition].equals("-")) return true;
        else {
            return false;
        }
    }

    public boolean isGameState() {
        return gameState;
    }

    public boolean checkPositiveDiagonal(String playerLabel, int xPosition, int yPosition) {
        if (xPosition == yPosition) {
            for (int i = 0; i < this.gameFeeld.length; i++) {
                if (this.gameFeeld[i][i].equals(playerLabel)) {
                    if (i == this.gameFeeld.length - 1) return true;
                    else continue;
                } else return false;
            }
        }
        return false;
    }

    public boolean checkNegativeDiagonal(String playerLabel, int xPosition, int yPosition) {
        if (xPosition + yPosition == this.gameFeeld.length - 1) {
            for (int i = 0; i < this.gameFeeld.length; i++) {
                if (this.gameFeeld[gameFeeld.length - 1 - i][i].equals(playerLabel)) {
                    if (i == this.gameFeeld.length - 1) return true;
                    else continue;
                } else {
                    return false;
                }
            }
        }
        return false;
    }

    public boolean doCheckToXLine(String playerLabel, int xPosition) {
        for (int i = 0; i < this.gameFeeld.length; i++) {
            if (this.gameFeeld[xPosition][i].equals(playerLabel)) {
                continue;
            } else return false;
        }
        return true;
    }

    public boolean doCheckToYLine(String playerLabel, int yPosition) {
        for (int i = 0; i < this.gameFeeld.length; i++) {
            if (this.gameFeeld[i][yPosition].equals(playerLabel)) continue;
            else return false;
        }
        return true;
    }

    public void createMaxPlayerLabel() {
        for (int i = 0; i < gameFeeld.length; i++) {
            for (int j = 0; j < gameFeeld.length; j++) {
                stateOfAI[i][j]= checkXPointToAI(i,j)+checkYPointToAI(i,j)+
                checkPositiveDiagonalToAI(i,j)+ checkNegativeDiagonalToAI(i,j);
            }
        }
    }

    private int checkXPointToAI(int xPosition, int yPosition){
        int check= 0;
        if (!isEmpty(xPosition,yPosition)) return -1;
        for (int i = 0; i < gameFeeld.length; i++) {
            if (gameFeeld[xPosition][i].equals("X")) check++;
        }
        return check;
    }

    private int checkYPointToAI(int xPosition, int yPosition){
        int check= 0;
        if (!isEmpty(xPosition,yPosition)) return 0;
        for (int i = 0; i < gameFeeld.length; i++) {
            if (gameFeeld[i][yPosition].equals("X")) check++;
        }
        return check;
    }

    private int checkPositiveDiagonalToAI(int xPosition, int yPosition){
        int check= 0;
        if (!isEmpty(xPosition,yPosition)) return 0;
        if (xPosition==yPosition){
            for (int i = 0; i < gameFeeld.length; i++) {
                if (i!=xPosition){
                    if (gameFeeld[i][i].equals("X")) check++;
                }
            }
        }
        return check;
    }

    private int checkNegativeDiagonalToAI(int xPosition, int yPosition){
        int check= 0;
        if (!isEmpty(xPosition,yPosition)) return 0;
        if (xPosition+yPosition==gameFeeld.length-1){
            for (int i = 0; i < gameFeeld.length; i++) {
                if (i!=xPosition){
                    if (gameFeeld[i][gameFeeld.length-1-i].equals("X")) check++;
                }
            }
        }
        return check;
    }
}