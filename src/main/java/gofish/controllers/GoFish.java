package gofish.controllers;

import enums.Ranks;
import views.IView;
import controllers.IController;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Random;

public class GoFish implements IController {
    ArrayList<Ranks> deck = new ArrayList<>();
    ArrayList<Ranks> playerHand = new ArrayList<>();
    ArrayList<Ranks> opponentHand = new ArrayList<>();
    ArrayList<Ranks> countHand = new ArrayList<>();
    int playerPoints = 0;
    int opponentPoints = 0;
    int susLevel = 0;
    int squashyCount = 0;
    int takeStreak = 0;
    static Random rng = new Random();
    private static BufferedReader inputReader = new BufferedReader(new InputStreamReader(System.in));

    private IView view;
    public GoFish(IView view) {
        this.view = view;
    }

    public  void Initialize(){
        deck.add(Ranks.ROUXLS_KAARD);

        for(int i = 0; i <4; i++){
            deck.add(Ranks.JOKER);
            deck.add(Ranks.TWO);
            deck.add(Ranks.THREE);
            deck.add(Ranks.FOUR);
            deck.add(Ranks.FIVE);
            deck.add(Ranks.SIX);
            deck.add(Ranks.SEVEN);
            deck.add(Ranks.EIGHT);
            deck.add(Ranks.NINE);
            deck.add(Ranks.TEN);
            deck.add(Ranks.JACK);
            deck.add(Ranks.QUEEN);
            deck.add(Ranks.KING);
            deck.add(Ranks.ACE);
        }
        for(int i = 0; i <7; i++){
            drawForPlayer();
            drawForOpponent();
        }

    }

    public  void drawForPlayer(){
        int newCard = rng.nextInt(deck.size() - 1);
        if(deck.size() > 2) {
            if (newCard == 1) {
                newCard++;
            } else if (newCard == 0) {
                newCard++;
                newCard++;
            }
        }
        if(deck.size() == 2){
            System.out.println("But there are no more fish in the pond.");
        }else{
            playerHand.add(deck.get(newCard));
            deck.remove(newCard);
        }
    }

    public  void drawForOpponent(){
        int newCard = rng.nextInt(deck.size() - 1);
        if(deck.size() > 2) {
            if (newCard == 1) {
                newCard++;
            } else if (newCard == 0) {
                newCard++;
                newCard++;
            }
        }
        if(deck.size() == 2){
            System.out.println("Not even a nibble.");
        }else{
            opponentHand.add(deck.get(newCard));
            deck.remove(newCard);
        }
    }

    public  void gameLogic(){
        int totalPoints = playerPoints + opponentPoints;
        Initialize();
        boolean streakTextUsed = false;
        for(int i = totalPoints; i < 13;){
            int failChance = rng.nextInt(10);
            System.out.println("\nDeck: " + (deck.size() - 2) + " cards left\nYou: " + playerPoints + "\nThem: " + opponentPoints);
            System.out.println(playerHand);
            if(squashyCount == 1){
                System.out.println("Praise be unto him.");
                System.out.println("His Hand: " + opponentHand);
            }
            String bup = view.promptForString("Please ask for a card.");

            if(bup.equals("SQUASHY")){
                System.out.println("You tell your opponent there is something really cool behind him.\n");
                susLevel+=3;

                if(squashyCount == 1){
                    System.out.println("He immediately calls you out on your antics.\nYeah there was no way he'd let you get away with divine intervention a second time.\nYou have been disqualified.");
                    playerPoints = 0;
                    opponentPoints = 13;
                    i = 13;
                    if(checkForWin(i)){
                        break;
                    }
                    break;
                }
                System.out.println("While he's not looking you perform a ritual consisting of various chants, dances, and hand gestures.\n");
                if(susLevel == 3 && failChance > 4 || susLevel == 4 && failChance > 3 || susLevel == 5 && failChance > 2 || susLevel == 6 && failChance > 1 || susLevel == 7){
                    System.out.println("But your opponent turned back around before you could finish.\nAs a result, you have been disqualified.");
                    playerPoints = 0;
                    opponentPoints = 13;
                    i = 13;
                    if(checkForWin(i)){
                        break;
                    }
                    break;
                }
                else {
                    squashyCount++;
                    System.out.println("You finish the ritual and clean up the candles and chalk before he notices\n");

                    if(susLevel >= 3){
                        System.out.println("That was a pretty risky move, you're lucky your opponent is a bit of an idiot.\nMaybe you shouldn't try asking for help from divine beings again, or cheating for that matter.\nJust saying.");
                    }
                    continue;
                }
            }
            if(bup.equals("MOREFORME")){
                if(susLevel == 3 && failChance > 6 || susLevel == 4 && failChance > 5 || susLevel == 5 && failChance > 4 || susLevel == 6 && failChance > 3 || susLevel == 7 && failChance > 2 || susLevel == 8 && failChance > 1 || susLevel == 9){
                    System.out.println("Your opponent has become aware of your shenanigans, and you are disqualified");
                    playerPoints = 0;
                    opponentPoints = 13;
                    i = 13;
                    if(checkForWin(i)){
                        break;
                    }
                    break;
                }
                else {
                    susLevel++;
                    System.out.println("While your opponent isn't looking, you take another card.");
                    drawForPlayer();
                    if(susLevel == 3){
                        System.out.println("You're starting to look pretty sus, might not want to try that again.");
                    }
                    continue;
                }
            }
            if(bup.equals("MINENOW")){

                if(susLevel == 3 && failChance > 6 || susLevel == 4 && failChance > 5 || susLevel == 5 && failChance > 4 || susLevel == 6 && failChance > 3 || susLevel == 7 && failChance > 2 || susLevel == 8 && failChance > 1 || susLevel == 9){
                    System.out.println("Your opponent has become aware of your shenanigans, and you are disqualified");
                    playerPoints = 0;
                    opponentPoints = 13;
                    i = 13;
                    if(checkForWin(i)){
                        break;
                    }
                    break;
                }
                else {

                    int randNum = rng.nextInt(opponentHand.size());
                    String card = opponentHand.get(randNum).toString();
                    susLevel++;
                    System.out.println("While your opponent is taking a bathroom break you take one of their cards.  it's a " + card + "!");
                    opponentHand.remove(Ranks.valueOf(card));
                    playerHand.add(Ranks.valueOf(card));
                    if (checkForPoint(playerHand, Ranks.TWO)) {
                        playerPoints++;
                        i++;
                    } else if (checkForPoint(playerHand, Ranks.THREE)) {
                        playerPoints++;
                        i++;
                    } else if (checkForPoint(playerHand, Ranks.FOUR)) {
                        playerPoints++;
                        i++;
                    } else if (checkForPoint(playerHand, Ranks.FIVE)) {
                        playerPoints++;
                        i++;
                    } else if (checkForPoint(playerHand, Ranks.SIX)) {
                        playerPoints++;
                        i++;
                    } else if (checkForPoint(playerHand, Ranks.SEVEN)) {
                        playerPoints++;
                        i++;
                    } else if (checkForPoint(playerHand, Ranks.EIGHT)) {
                        playerPoints++;
                        i++;
                    } else if (checkForPoint(playerHand, Ranks.NINE)) {
                        playerPoints++;
                        i++;
                    } else if (checkForPoint(playerHand, Ranks.TEN)) {
                        playerPoints++;
                        i++;
                    } else if (checkForPoint(playerHand, Ranks.JACK)) {
                        playerPoints++;
                        i++;
                    } else if (checkForPoint(playerHand, Ranks.QUEEN)) {
                        playerPoints++;
                        i++;
                    } else if (checkForPoint(playerHand, Ranks.KING)) {
                        playerPoints++;
                        i++;
                    } else if (checkForPoint(playerHand, Ranks.ACE)) {
                        playerPoints++;
                        i++;
                    }
                    if(checkForWin(i)){
                        break;
                    }
                    if(susLevel == 3){
                        System.out.println("You're starting to look pretty sus, might not want to try that again.");
                    }
                    continue;
                }
            }
            if(bup.equals("TAKEALOOKSIE")){
                if(susLevel == 3 && failChance > 6 || susLevel == 4 && failChance > 5 || susLevel == 5 && failChance > 4 || susLevel == 6 && failChance > 3 || susLevel == 7 && failChance > 2 || susLevel == 8 && failChance > 1 || susLevel == 9){
                    System.out.println("Your opponent has become aware of your shenanigans, and you are disqualified");
                    playerPoints = 0;
                    opponentPoints = 13;
                    i = 13;
                    if(checkForWin(i)){
                        break;
                    }
                    break;
                }
                else {
                    susLevel++;
                    String goop = view.promptForString("What do you want to check for?");
                    if(opponentHand.toString().contains(goop.toUpperCase())){
                        System.out.println("Your opponent's hand does contain at least one " + goop.toLowerCase() + ".");
                    } else {
                        System.out.println("There does not seem to be any of those in your opponent's hand");
                    }
                    if(susLevel == 3){
                        System.out.println("You're starting to look pretty sus, might not want to try that again.");
                    }
                    continue;
                }
            }

            if(!playerHand.toString().contains(bup.toUpperCase()) || bup.isEmpty() || !bup.toUpperCase().equals(Ranks.TWO.toString()) && !bup.toUpperCase().equals(Ranks.THREE.toString()) && !bup.toUpperCase().equals(Ranks.FOUR.toString()) && !bup.toUpperCase().equals(Ranks.FIVE.toString()) && !bup.toUpperCase().equals(Ranks.SIX.toString()) && !bup.toUpperCase().equals(Ranks.SEVEN.toString()) && !bup.toUpperCase().equals(Ranks.EIGHT.toString()) && !bup.toUpperCase().equals(Ranks.NINE.toString()) && !bup.toUpperCase().equals(Ranks.TEN.toString()) && !bup.toUpperCase().equals(Ranks.JACK.toString()) && !bup.toUpperCase().equals(Ranks.QUEEN.toString()) && !bup.toUpperCase().equals(Ranks.KING.toString()) && !bup.toUpperCase().equals(Ranks.ACE.toString())){
                System.out.println("You can only ask for cards you have!");
                continue;
            }

            if (opponentHand.toString().contains(bup.toUpperCase())) {

                System.out.println("You ask for " + bup.toUpperCase() + "S, and he hands them over.");
                takeStreak++;
                if(takeStreak >= 3){

                    susLevel++;
                    if(susLevel >= 3 && !streakTextUsed){
                        System.out.println("Your opponent seems suspicious of your guessing streak");
                        streakTextUsed = true;
                    }
                }
                do{
                    playerHand.add(Ranks.valueOf(bup.toUpperCase()));
                    opponentHand.remove(Ranks.valueOf(bup.toUpperCase()));
                    if(opponentHand.size() == 0){
                        drawForOpponent();
                    }
                    if (checkForPoint(opponentHand, Ranks.TWO)) {
                        opponentPoints++;
                        i++;
                    } else if (checkForPoint(opponentHand, Ranks.THREE)) {
                        opponentPoints++;
                        i++;
                    } else if (checkForPoint(opponentHand, Ranks.FOUR)) {
                        opponentPoints++;
                        i++;
                    } else if (checkForPoint(opponentHand, Ranks.FIVE)) {
                        opponentPoints++;
                        i++;
                    } else if (checkForPoint(opponentHand, Ranks.SIX)) {
                        opponentPoints++;
                        i++;
                    } else if (checkForPoint(opponentHand, Ranks.SEVEN)) {
                        opponentPoints++;
                        i++;
                    } else if (checkForPoint(opponentHand, Ranks.EIGHT)) {
                        opponentPoints++;
                        i++;
                    } else if (checkForPoint(opponentHand, Ranks.NINE)) {
                        opponentPoints++;
                        i++;
                    } else if (checkForPoint(opponentHand, Ranks.TEN)) {
                        opponentPoints++;
                        i++;
                    } else if (checkForPoint(opponentHand, Ranks.JACK)) {
                        opponentPoints++;
                        i++;
                    } else if (checkForPoint(opponentHand, Ranks.QUEEN)) {
                        opponentPoints++;
                        i++;
                    } else if (checkForPoint(opponentHand, Ranks.KING)) {
                        opponentPoints++;
                        i++;
                    } else if (checkForPoint(opponentHand, Ranks.ACE)) {
                        opponentPoints++;
                        i++;
                    }

                    if (checkForPoint(playerHand, Ranks.TWO)) {
                        playerPoints++;
                        i++;
                    } else if (checkForPoint(playerHand, Ranks.THREE)) {
                        playerPoints++;
                        i++;
                    } else if (checkForPoint(playerHand, Ranks.FOUR)) {
                        playerPoints++;
                        i++;
                    } else if (checkForPoint(playerHand, Ranks.FIVE)) {
                        playerPoints++;
                        i++;
                    } else if (checkForPoint(playerHand, Ranks.SIX)) {
                        playerPoints++;
                        i++;
                    } else if (checkForPoint(playerHand, Ranks.SEVEN)) {
                        playerPoints++;
                        i++;
                    } else if (checkForPoint(playerHand, Ranks.EIGHT)) {
                        playerPoints++;
                        i++;
                    } else if (checkForPoint(playerHand, Ranks.NINE)) {
                        playerPoints++;
                        i++;
                    } else if (checkForPoint(playerHand, Ranks.TEN)) {
                        playerPoints++;
                        i++;
                    } else if (checkForPoint(playerHand, Ranks.JACK)) {
                        playerPoints++;
                        i++;
                    } else if (checkForPoint(playerHand, Ranks.QUEEN)) {
                        playerPoints++;
                        i++;
                    } else if (checkForPoint(playerHand, Ranks.KING)) {
                        playerPoints++;
                        i++;
                    } else if (checkForPoint(playerHand, Ranks.ACE)) {
                        playerPoints++;
                        i++;
                    }


                }while (opponentHand.toString().contains(bup.toUpperCase()));
                if(checkForWin(i)){
                    break;
                }
                continue;

            }

            if(!opponentHand.toString().contains(bup.toUpperCase())) {
                System.out.println("Go Fish!");
                takeStreak = 0;
                drawForPlayer();
                if (checkForPoint(opponentHand, Ranks.TWO)) {
                    opponentPoints++;
                    i++;
                } else if (checkForPoint(opponentHand, Ranks.THREE)) {
                    opponentPoints++;
                    i++;
                } else if (checkForPoint(opponentHand, Ranks.FOUR)) {
                    opponentPoints++;
                    i++;
                } else if (checkForPoint(opponentHand, Ranks.FIVE)) {
                    opponentPoints++;
                    i++;
                } else if (checkForPoint(opponentHand, Ranks.SIX)) {
                    opponentPoints++;
                    i++;
                } else if (checkForPoint(opponentHand, Ranks.SEVEN)) {
                    opponentPoints++;
                    i++;
                } else if (checkForPoint(opponentHand, Ranks.EIGHT)) {
                    opponentPoints++;
                    i++;
                } else if (checkForPoint(opponentHand, Ranks.NINE)) {
                    opponentPoints++;
                    i++;
                } else if (checkForPoint(opponentHand, Ranks.TEN)) {
                    opponentPoints++;
                    i++;
                } else if (checkForPoint(opponentHand, Ranks.JACK)) {
                    opponentPoints++;
                    i++;
                } else if (checkForPoint(opponentHand, Ranks.QUEEN)) {
                    opponentPoints++;
                    i++;
                } else if (checkForPoint(opponentHand, Ranks.KING)) {
                    opponentPoints++;
                    i++;
                } else if (checkForPoint(opponentHand, Ranks.ACE)) {
                    opponentPoints++;
                    i++;
                }

                if (checkForPoint(playerHand, Ranks.TWO)) {
                    playerPoints++;
                    i++;
                } else if (checkForPoint(playerHand, Ranks.THREE)) {
                    playerPoints++;
                    i++;
                } else if (checkForPoint(playerHand, Ranks.FOUR)) {
                    playerPoints++;
                    i++;
                } else if (checkForPoint(playerHand, Ranks.FIVE)) {
                    playerPoints++;
                    i++;
                } else if (checkForPoint(playerHand, Ranks.SIX)) {
                    playerPoints++;
                    i++;
                } else if (checkForPoint(playerHand, Ranks.SEVEN)) {
                    playerPoints++;
                    i++;
                } else if (checkForPoint(playerHand, Ranks.EIGHT)) {
                    playerPoints++;
                    i++;
                } else if (checkForPoint(playerHand, Ranks.NINE)) {
                    playerPoints++;
                    i++;
                } else if (checkForPoint(playerHand, Ranks.TEN)) {
                    playerPoints++;
                    i++;
                } else if (checkForPoint(playerHand, Ranks.JACK)) {
                    playerPoints++;
                    i++;
                } else if (checkForPoint(playerHand, Ranks.QUEEN)) {
                    playerPoints++;
                    i++;
                } else if (checkForPoint(playerHand, Ranks.KING)) {
                    playerPoints++;
                    i++;
                } else if (checkForPoint(playerHand, Ranks.ACE)) {
                    playerPoints++;
                    i++;
                }
                if(checkForWin(i)){
                    break;
                }
                System.out.println(playerHand);
            }
            for(int j = 0; j < 13;) {
                int randNum = rng.nextInt(opponentHand.size());
                String card = opponentHand.get(randNum).toString();

                if (playerHand.toString().contains(card)) {
                    System.out.println("Your opponent asks for " + card + "S, so you hand them over.");
                    do {
                        opponentHand.add(Ranks.valueOf(card));
                        playerHand.remove(Ranks.valueOf(card));
                        if(playerHand.size() == 0){
                            drawForPlayer();
                        }
                        if (checkForPoint(opponentHand, Ranks.TWO)) {
                            opponentPoints++;
                            i++;
                        } else if (checkForPoint(opponentHand, Ranks.THREE)) {
                            opponentPoints++;
                            i++;
                        } else if (checkForPoint(opponentHand, Ranks.FOUR)) {
                            opponentPoints++;
                            i++;
                        } else if (checkForPoint(opponentHand, Ranks.FIVE)) {
                            opponentPoints++;
                            i++;
                        } else if (checkForPoint(opponentHand, Ranks.SIX)) {
                            opponentPoints++;
                            i++;
                        } else if (checkForPoint(opponentHand, Ranks.SEVEN)) {
                            opponentPoints++;
                            i++;
                        } else if (checkForPoint(opponentHand, Ranks.EIGHT)) {
                            opponentPoints++;
                            i++;
                        } else if (checkForPoint(opponentHand, Ranks.NINE)) {
                            opponentPoints++;
                            i++;
                        } else if (checkForPoint(opponentHand, Ranks.TEN)) {
                            opponentPoints++;
                            i++;
                        } else if (checkForPoint(opponentHand, Ranks.JACK)) {
                            opponentPoints++;
                            i++;
                        } else if (checkForPoint(opponentHand, Ranks.QUEEN)) {
                            opponentPoints++;
                            i++;
                        } else if (checkForPoint(opponentHand, Ranks.KING)) {
                            opponentPoints++;
                            i++;
                        } else if (checkForPoint(opponentHand, Ranks.ACE)) {
                            opponentPoints++;
                            i++;
                        }

                    }while (playerHand.toString().contains(card));
                    if(checkForWin(i)){
                        break;
                    }
                    System.out.println(playerHand);
                    continue;
                }


                if (!playerHand.toString().contains(card)) {
                    System.out.println("Your opponent asks for " + card + "S, you say \"Go Fish!\", and he reaches to draw a card");
                    drawForOpponent();
                    if (checkForPoint(opponentHand, Ranks.TWO)) {
                        opponentPoints++;
                        i++;
                    } else if (checkForPoint(opponentHand, Ranks.THREE)) {
                        opponentPoints++;
                        i++;
                    } else if (checkForPoint(opponentHand, Ranks.FOUR)) {
                        opponentPoints++;
                        i++;
                    } else if (checkForPoint(opponentHand, Ranks.FIVE)) {
                        opponentPoints++;
                        i++;
                    } else if (checkForPoint(opponentHand, Ranks.SIX)) {
                        opponentPoints++;
                        i++;
                    } else if (checkForPoint(opponentHand, Ranks.SEVEN)) {
                        opponentPoints++;
                        i++;
                    } else if (checkForPoint(opponentHand, Ranks.EIGHT)) {
                        opponentPoints++;
                        i++;
                    } else if (checkForPoint(opponentHand, Ranks.NINE)) {
                        opponentPoints++;
                        i++;
                    } else if (checkForPoint(opponentHand, Ranks.TEN)) {
                        opponentPoints++;
                        i++;
                    } else if (checkForPoint(opponentHand, Ranks.JACK)) {
                        opponentPoints++;
                        i++;
                    } else if (checkForPoint(opponentHand, Ranks.QUEEN)) {
                        opponentPoints++;
                        i++;
                    } else if (checkForPoint(opponentHand, Ranks.KING)) {
                        opponentPoints++;
                        i++;
                    } else if (checkForPoint(opponentHand, Ranks.ACE)) {
                        opponentPoints++;
                        i++;
                    }
                    if(checkForWin(i)){
                        break;
                    }
                    break;
                }
            }
        }

        System.out.println("You: " + playerPoints);
        System.out.println("Them: " + opponentPoints);
    }

    public  boolean checkForPoint(ArrayList<Ranks> person, Ranks rank){
        boolean success = false;
        int i = 0;

        if(person.contains(rank)){
            do {
                person.remove(rank);
                countHand.add(rank);
                i++;
            }while (person.contains(rank));
            if(i == 4){
                countHand.removeAll(countHand);
                if(playerHand.size() == 0){
                    drawForPlayer();
                }
                if(opponentHand.size() == 0){
                    drawForOpponent();
                }
                success = true;
            }else {
                person.addAll(countHand);
                countHand.removeAll(countHand);
                i = 0;
            }
        }
        return success;
    }

    public  boolean checkForWin(int i){
        boolean win = false;
        if (i == 13) {
            System.out.println("GAME!");
            if (playerPoints > opponentPoints) {
                System.out.println("YOU WIN!");
            } else if (opponentPoints > playerPoints) {
                System.out.println("YOU LOSE!");
            }
            win = true;
        }
        return win;
    }

    public void run(){
        boolean isInvalid = true;
        System.out.println("Welcome to Go Fish!");
        gameLogic();

            boolean gameRestart = view.promptForBoolean("Try Again?\nY/N");
            if(gameRestart) {
                deck.removeAll(deck);
                playerHand.removeAll(playerHand);
                opponentHand.removeAll(opponentHand);
                playerPoints = 0;
                opponentPoints = 0;
                squashyCount = 0;
                susLevel = 0;
                gameLogic();
            }

    }

}
