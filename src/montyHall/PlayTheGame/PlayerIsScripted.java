package montyHall.PlayTheGame;

import montyHall.Bo.BetBo;
import org.apache.commons.lang3.ArrayUtils;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PlayerIsScripted {

    public void startMontyHallGameScripted() {
        Map<String, List<BetBo>> montyGamesScenarios = betsScripter();
        printAndCalculateResults(montyGamesScenarios);
    }

    private void printAndCalculateResults(Map<String, List<BetBo>> montyGamesScenarios) {
        int possibleDoorsArrangements = montyGamesScenarios.get("doorsArrangement").size();
        int possiblePlayerChoiceScenarios = montyGamesScenarios.get("playerBets").size();

        List<BetBo> doorsArrangementList = montyGamesScenarios.get("doorsArrangement");
        List<BetBo> playerBetsList = montyGamesScenarios.get("playerBets");
        List<BetBo> possibleOutcomesWithMonty = montyGamesScenarios.get("montyChoices");

        System.out.println("At the beginning of the game we will arrange the doors, so that we have " + possibleDoorsArrangements + " different situations.");
        System.out.println("|A|B|C|");
        doorsArrangementList.stream().forEach(possibleDoors -> {
            possibleDoors.printDoorsInConsole();
        });

        System.out.println("When the player makes his choice there are " + possiblePlayerChoiceScenarios + " possible scenarios.");
        System.out.println("|A|B|C|P|");
        playerBetsList.stream().forEach(playerBet -> {
            playerBet.printPlayerChoicesInConsole();
        });

        System.out.println("If the player chose the losing door, Monty Hall can not open the door with the prize or the one choosen by the player, so his choice cannot be random: he opens the door hiding the goat with a probability of 1");
        System.out.println("If the player chose the winning door, Monty Hall can choose between two doors, his choice is random: he now opens one of the doors hiding a goat with a probability of 1/2");
        System.out.println("This is the table of possible outcomes we have at this stage");
        System.out.println("|A|B|C|P|M|");

        possibleOutcomesWithMonty.stream().forEach(montyChoice -> {
            montyChoice.printMontyChoicesInConsole();
        });

        int winAtFirstStrike = (int) possibleOutcomesWithMonty.stream().filter(bet -> bet.getDoors()[bet.getPlayerChoice()] == 1).count();
        int lostAtFirstStrike = (int) possibleOutcomesWithMonty.stream().filter(bet -> bet.getDoors()[bet.getPlayerChoice()] == 0).count();

        System.out.println("Since the probability of winning is P(W=1) = [" + winAtFirstStrike + " * (1/9 * 1/2)] and the probability of losing is P(W=0) = [" + lostAtFirstStrike + " * (1/9 * 1)] we can assume the probabilities of winning and loosing with or without switching");

        double percentageProbabilityOfVictoryWithNoSwitch = winAtFirstStrike * (1 / 2.0) * (1 / 9.0) * 100.0;
        double percentageProbabilityOfVictoryWithSwitch = winAtFirstStrike * 1.0 * (1 / 9.0) * 100.0;
        DecimalFormat numberFormat = new DecimalFormat("#.00");
        System.out.println("We know that the percentage of winning without switching the door is at " + numberFormat.format(percentageProbabilityOfVictoryWithNoSwitch) + "%");
        System.out.println("So we now also know that if we decide to switch door, the percentage of winning is at " + numberFormat.format(percentageProbabilityOfVictoryWithSwitch) + "%");

    }

    private Map<String, List<BetBo>> betsScripter() {

        Map<String, List<BetBo>> results = new HashMap<>();

        List<BetBo> doorsArrangement;
        List<BetBo> playerBets;
        List<BetBo> montyChoices;

        doorsArrangement = doorsArranger();
        playerBets = playerChoiceScripting(doorsArrangement);
        montyChoices = montyChoiceScripting(playerBets);

        results.put("doorsArrangement", doorsArrangement);
        results.put("playerBets", playerBets);
        results.put("montyChoices", montyChoices);

        return results;
    }

    private List<BetBo> doorsArranger() {
        List<BetBo> doorsArrangementList = new ArrayList<>();
        List<int[]> doorsArrangement = new ArrayList<>();

        int possibleArrangements = 3;
        for (int i = 0; i < possibleArrangements; i++) {
            int[] arrangement = {0, 0, 0};
            arrangement[i] = 1;
            doorsArrangement.add(arrangement);
        }

        for (int[] doors : doorsArrangement) {
            BetBo betBoDoor = new BetBo();
            betBoDoor.setDoors(doors);
            doorsArrangementList.add(betBoDoor);
        }

        return doorsArrangementList;
    }

    private List<BetBo> playerChoiceScripting(List<BetBo> betsPossibleToBeChoosen) {

        List<BetBo> playerBetsScripted = new ArrayList<>();
        int doorsToChoose = 3;
        int playerOptions = 3;

        for (int i = 0; i < doorsToChoose; i++) {
            BetBo bet = betsPossibleToBeChoosen.get(i);
            for (int j = 0; j < playerOptions; j++) {
                BetBo betWithChoice = new BetBo();
                betWithChoice.setDoors(bet.getDoors());
                betWithChoice.setPlayerChoice(j);
                playerBetsScripted.add(betWithChoice);
            }
        }
        return playerBetsScripted;
    }

    private List<BetBo> montyChoiceScripting(List<BetBo> possibleBetsToBeChosen) {
        List<BetBo> montyDoorsOpenedScripted = new ArrayList<>();

        possibleBetsToBeChosen.stream().forEach(bet -> {

            int playerChoiceIndex = bet.getPlayerChoice();
            int[] doors = bet.getDoors();
            boolean isPlayerChoiceCorrect = bet.getDoors()[playerChoiceIndex] == 1;

            if (isPlayerChoiceCorrect) {

                BetBo montyChoosesFirstGoat = new BetBo();
                montyChoosesFirstGoat.setDoors(bet.getDoors());
                montyChoosesFirstGoat.setPlayerChoice(bet.getPlayerChoice());

                BetBo montyChoosesSecondGoat = new BetBo();
                montyChoosesSecondGoat.setDoors(bet.getDoors());
                montyChoosesSecondGoat.setPlayerChoice(bet.getPlayerChoice());

                int montyFirstChoice;
                int montySecondChoice;

                switch (playerChoiceIndex) {
                    case 0:
                        montyFirstChoice = 1;
                        montySecondChoice = 2;
                        break;
                    case 1:
                        montyFirstChoice = 0;
                        montySecondChoice = 2;
                        break;
                    case 2:
                        montyFirstChoice = 0;
                        montySecondChoice = 1;
                        break;
                    default:
                        throw new IllegalStateException("Unexpected value from the choice of the player: " + playerChoiceIndex);
                }

                montyChoosesFirstGoat.setMontyChoice(montyFirstChoice);
                montyChoosesSecondGoat.setMontyChoice(montySecondChoice);

                montyDoorsOpenedScripted.add(montyChoosesFirstGoat);
                montyDoorsOpenedScripted.add(montyChoosesSecondGoat);

            } else {

                BetBo montyChoosesGoatDoor = new BetBo();
                montyChoosesGoatDoor.setDoors(bet.getDoors());
                montyChoosesGoatDoor.setPlayerChoice(bet.getPlayerChoice());

                int montyChoice;
                int indexOfCarDoor = ArrayUtils.indexOf(doors, 1);

                if (indexOfCarDoor == 0) {
                    switch (playerChoiceIndex) {
                        case 1:
                            montyChoice = 2;
                            break;
                        case 2:
                            montyChoice = 1;
                            break;
                        default:
                            throw new IllegalStateException("Unexpected value from the index of car door: " + indexOfCarDoor);
                    }
                } else if (indexOfCarDoor == 1) {
                    switch (playerChoiceIndex) {
                        case 0:
                            montyChoice = 2;
                            break;
                        case 2:
                            montyChoice = 0;
                            break;
                        default:
                            throw new IllegalStateException("Unexpected value from the index of car door: " + indexOfCarDoor);
                    }
                } else {
                    switch (playerChoiceIndex) {
                        case 0:
                            montyChoice = 1;
                            break;
                        case 1:
                            montyChoice = 0;
                            break;
                        default:
                            throw new IllegalStateException("Unexpected value from the index of car door: " + indexOfCarDoor);
                    }
                }
                montyChoosesGoatDoor.setMontyChoice(montyChoice);
                montyDoorsOpenedScripted.add(montyChoosesGoatDoor);
            }
        });

        return montyDoorsOpenedScripted;
    }
}
