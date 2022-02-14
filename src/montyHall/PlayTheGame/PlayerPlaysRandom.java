package montyHall.PlayTheGame;

import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class PlayerPlaysRandom {

    public void startMontyHallGameRandomized() {

        int rounds = 100000;
        Map<String, Integer> results = playTheGame(rounds);

        int switchWins = results.get("switchWins");
        int stayWins = results.get("stayWins");

        String switchWinsPercentage = percentageCalculator(switchWins, rounds);
        String stayWinsPercentage = percentageCalculator(stayWins, rounds);

        System.out.println("Game was played " + rounds + " times");
        System.out.println("User had the opportunity of winning by switching " + switchWins + " times. If you switch your door, your percentage of winning is: " + switchWinsPercentage + "%");
        System.out.println("User had the opportunity of winning by not switching " + stayWins + " times. If you don't switch your door, your percentage of winning is: " + stayWinsPercentage + "%");
    }

    private Map<String, Integer> playTheGame(int rounds) {

        Map<String, Integer> results = new HashMap<>();
        Integer switchWins = 0;
        Integer stayWins = 0;
        Random humanInput = new Random();

        for (int plays = 0; plays < rounds; plays++) {
            int[] doors = {0, 0, 0};
            doors[humanInput.nextInt(3)] = 1;
            int choice = humanInput.nextInt(3);
            int montysDoorChoice;

            do {
                montysDoorChoice = humanInput.nextInt(3);
            }
            while (doors[montysDoorChoice] == 1 || montysDoorChoice == choice);

            stayWins += doors[choice];
            switchWins += doors[3 - choice - montysDoorChoice];
        }

        results.put("stayWins", stayWins);
        results.put("switchWins", switchWins);
        return results;
    }

    private String percentageCalculator(int wins, int rounds) {
        double result = (wins * 100.0) / rounds;
        DecimalFormat numberFormat = new DecimalFormat("#.00");
        return numberFormat.format(result);
    }
}
