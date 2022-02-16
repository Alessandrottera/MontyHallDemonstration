package montyHall.Bo;

public class BetBo {

    private int[] doors;
    private int playerChoice;
    private int montyChoice;

    public int[] getDoors() {
        return doors;
    }

    public void setDoors(int[] doors) {
        this.doors = doors;
    }

    public int getPlayerChoice() {
        return playerChoice;
    }

    public void setPlayerChoice(int playerChoice) {
        this.playerChoice = playerChoice;
    }

    public int getMontyChoice() {
        return montyChoice;
    }

    public void setMontyChoice(int montyChoice) {
        this.montyChoice = montyChoice;
    }

    public void printDoorsInConsole() {
        System.out.print("|" + getDoors()[0] + "|" + getDoors()[1] + "|" + getDoors()[2] + "|\n");
    }

    public void printPlayerChoicesInConsole() {
        System.out.print("|" + getDoors()[0] + "|" + getDoors()[1] + "|" + getDoors()[2] + "|" + choosenDoorLetter(getPlayerChoice()) + "|\n");
    }

    public void printMontyChoicesInConsole() {
        System.out.print("|" + getDoors()[0] + "|" + getDoors()[1] + "|" + getDoors()[2] + "|" + choosenDoorLetter(getPlayerChoice()) + "|" + choosenDoorLetter(getMontyChoice()) + "|\n");
    }

    private String choosenDoorLetter(int decision) {
        String playerChoiceLetter;
        switch (decision) {
            case 0:
                playerChoiceLetter = "A";
                break;
            case 1:
                playerChoiceLetter = "B";
                break;
            case 2:
                playerChoiceLetter = "C";
                break;
            default:
                throw new IllegalStateException("Unexpected value in the choice of the door: " + getPlayerChoice());
        }
        return playerChoiceLetter;
    }
}
