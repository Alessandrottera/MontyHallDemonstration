package montyHall.Start;

import montyHall.PlayTheGame.PlayerIsScripted;
import montyHall.PlayTheGame.PlayerPlaysRandom;

public class Start {

    public static void main(String args[]){
        System.out.println("Start demonstration of Monty Hall Paradox");
        PlayerIsScripted scriptedGame = new PlayerIsScripted();
        scriptedGame.startMontyHallGameScripted();
        System.out.println("-------------------------------------------------------------------------");
        System.out.println("Now the program will simulate a real life scenario of the Monty Hall Game");
        PlayerPlaysRandom randomGame = new PlayerPlaysRandom();
        randomGame.startMontyHallGameRandomized();
    }
}
