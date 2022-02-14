package montyHall.PlayTheGame;

import java.util.Random;

public class PlayerPlaysRandom {

    public void startMontyHallGameRandomized() {
        int switchWins = 0;
        int stayWins = 0;
        int rounds = 100000;
        Random gen = new Random();

        for (int plays = 0; plays < rounds; plays++) {
            int[] doors = {0, 0, 0};
            doors[gen.nextInt(3)] = 1;
            int choice = gen.nextInt(3);
            int shown;

            do {
                shown = gen.nextInt(3);
            }
            while (doors[shown] == 1 || shown == choice);
            stayWins += doors[choice];
            switchWins += doors[3 - choice - shown];
        }

        int switchWinsPercentage = (switchWins * 100) / rounds;
        int stayWinsPercentage = (stayWins * 100) / rounds;

        System.out.println("Game was played " + rounds + " times");
        System.out.println("User won by switching: " + switchWins + " times. If you switch your door, your percentage of winning is: " + switchWinsPercentage + "%");
        System.out.println("User won by not switching: " + stayWins + " times. If you don't switch your door, your percentage of winning is: " + stayWinsPercentage + "%");
    }
}
