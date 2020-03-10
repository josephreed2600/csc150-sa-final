package gofish.controllers;

import controllers.IController;
import enums.Ranks;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Random;
import views.IView;

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

  private IView view;

  public GoFish(IView view) {
    this.view = view;
  }

  public void Initialize() {
    deck.add(Ranks.ROUXLS_KAARD);

    for (int i = 0; i < 4; i++) {
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
    for (int i = 0; i < 7; i++) {
      drawForPlayer();
      drawForOpponent();
    }
  }

  public void drawForPlayer() {
    int newCard = rng.nextInt(deck.size() - 1);
    if (deck.size() > 2) {
      if (newCard == 1) {
        newCard++;
      } else if (newCard == 0) {
        newCard++;
        newCard++;
      }
    }
    if (deck.size() == 2) {
      view.display("But there are no more fish in the pond.\n");
    } else {
      playerHand.add(deck.get(newCard));
      deck.remove(newCard);
    }
  }

  public void drawForOpponent() {
    int newCard = rng.nextInt(deck.size() - 1);
    if (deck.size() > 2) {
      if (newCard == 1) {
        newCard++;
      } else if (newCard == 0) {
        newCard++;
        newCard++;
      }
    }
    if (deck.size() == 2) {
      view.display("Not even a nibble.\n");
    } else {
      opponentHand.add(deck.get(newCard));
      deck.remove(newCard);
    }
  }

  public void gameLogic() {
    int totalPoints = playerPoints + opponentPoints;
    Initialize();
    boolean streakTextUsed = false;
    for (int i = totalPoints; i < 13; ) {
      int failChance = rng.nextInt(10);
      view.display(
          "\nDeck: "
              + (deck.size() - 2)
              + " cards left\nYou:  "
              + playerPoints + " points"
              + "\nThem: "
              + opponentPoints + " points"
							+ "\n");
      if (squashyCount == 1) {
        view.display("Praise be unto him.\n");
        view.display("His hand:\n" + opponentHand + "\n");
				view.display("Your hand:\n");
			}
			view.display(playerHand+"\n");

      String bup = view.promptForString("Please ask for a card.\n> ");
			view.display("\n");

      if (bup.equals("SQUASHY")) {
        view.display("You tell your opponent there is something really cool behind him.\n");
        susLevel += 3;

        if (squashyCount == 1) {
          view.display(
              "He immediately calls you out on your antics.\nYeah there was no way he'd let you get away with divine intervention a second time.\nYou have been disqualified.\n");
          playerPoints = 0;
          opponentPoints = 13;
          i = 13;
          if (checkForWin(i)) {
            break;
          }
          break;
        }
        view.display(
            "While he's not looking you perform a ritual consisting of various chants, dances, and hand gestures.\n");
        if (susLevel == 3 && failChance > 4
            || susLevel == 4 && failChance > 3
            || susLevel == 5 && failChance > 2
            || susLevel == 6 && failChance > 1
            || susLevel == 7) {
          view.display(
              "But your opponent turned back around before you could finish.\nAs a result, you have been disqualified.\n");
          playerPoints = 0;
          opponentPoints = 13;
          i = 13;
          if (checkForWin(i)) {
            break;
          }
          break;
        } else {
          squashyCount++;
          view.display(
              "You finish the ritual and clean up the candles and chalk before he notices\n");

          if (susLevel >= 3) {
            view.display(
                "That was a pretty risky move, you're lucky your opponent is a bit of an idiot.\nMaybe you shouldn't try asking for help from divine beings again, or cheating for that matter.\nJust saying.\n");
          }
          continue;
        }
      }
      if (bup.equals("MOREFORME")) {
        if (susLevel == 3 && failChance > 6
            || susLevel == 4 && failChance > 5
            || susLevel == 5 && failChance > 4
            || susLevel == 6 && failChance > 3
            || susLevel == 7 && failChance > 2
            || susLevel == 8 && failChance > 1
            || susLevel == 9) {
          view.display(
              "Your opponent has become aware of your shenanigans, and you are disqualified\n");
          playerPoints = 0;
          opponentPoints = 13;
          i = 13;
          if (checkForWin(i)) {
            break;
          }
          break;
        } else {
          susLevel++;
          view.display("While your opponent isn't looking, you take another card.\n");
          drawForPlayer();
          if (susLevel == 3) {
            view.display(
                "You're starting to look pretty sus, might not want to try that again.\n");
          }
          continue;
        }
      }
      if (bup.equals("MINENOW")) {

        if (susLevel == 3 && failChance > 6
            || susLevel == 4 && failChance > 5
            || susLevel == 5 && failChance > 4
            || susLevel == 6 && failChance > 3
            || susLevel == 7 && failChance > 2
            || susLevel == 8 && failChance > 1
            || susLevel == 9) {
          view.display(
              "Your opponent has become aware of your shenanigans, and you are disqualified\n");
          playerPoints = 0;
          opponentPoints = 13;
          i = 13;
          if (checkForWin(i)) {
            break;
          }
          break;
        } else {

          int randNum = rng.nextInt(opponentHand.size());
          String card = opponentHand.get(randNum).toString();
          susLevel++;
          view.display(
              "While your opponent is taking a bathroom break you take one of their cards.  it's a "
                  + card
                  + "!\n");
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
          if (checkForWin(i)) {
            break;
          }
          if (susLevel == 3) {
            view.display(
                "You're starting to look pretty sus, might not want to try that again.\n");
          }
          continue;
        }
      }
      if (bup.equals("TAKEALOOKSIE")) {
        if (susLevel == 3 && failChance > 6
            || susLevel == 4 && failChance > 5
            || susLevel == 5 && failChance > 4
            || susLevel == 6 && failChance > 3
            || susLevel == 7 && failChance > 2
            || susLevel == 8 && failChance > 1
            || susLevel == 9) {
          view.display(
              "Your opponent has become aware of your shenanigans, and you are disqualified\n");
          playerPoints = 0;
          opponentPoints = 13;
          i = 13;
          if (checkForWin(i)) {
            break;
          }
          break;
        } else {
          susLevel++;
          String goop = view.promptForString("What do you want to check for?");
          if (opponentHand.toString().contains(goop.toUpperCase())) {
            view.display(
                "Your opponent's hand does contain at least one " + goop.toLowerCase() + ".\n");
          } else {
            view.display("There does not seem to be any of those in your opponent's hand\n");
          }
          if (susLevel == 3) {
            view.display(
                "You're starting to look pretty sus, might not want to try that again.\n");
          }
          continue;
        }
      }

      if (!playerHand.toString().contains(bup.toUpperCase())
          || bup.isEmpty()
          || !bup.toUpperCase().equals(Ranks.TWO.toString())
              && !bup.toUpperCase().equals(Ranks.THREE.toString())
              && !bup.toUpperCase().equals(Ranks.FOUR.toString())
              && !bup.toUpperCase().equals(Ranks.FIVE.toString())
              && !bup.toUpperCase().equals(Ranks.SIX.toString())
              && !bup.toUpperCase().equals(Ranks.SEVEN.toString())
              && !bup.toUpperCase().equals(Ranks.EIGHT.toString())
              && !bup.toUpperCase().equals(Ranks.NINE.toString())
              && !bup.toUpperCase().equals(Ranks.TEN.toString())
              && !bup.toUpperCase().equals(Ranks.JACK.toString())
              && !bup.toUpperCase().equals(Ranks.QUEEN.toString())
              && !bup.toUpperCase().equals(Ranks.KING.toString())
              && !bup.toUpperCase().equals(Ranks.ACE.toString())) {
        view.display("You can only ask for cards you have!\n");
        continue;
      }

      if (opponentHand.toString().contains(bup.toUpperCase())) {

        view.display("You ask for " + bup.toLowerCase() + (bup.toLowerCase().equals("six")?"e":"") + "s, and he hands them over.\n");
        takeStreak++;
        if (takeStreak >= 3) {

          susLevel++;
          if (susLevel >= 3 && !streakTextUsed) {
            view.display("Your opponent seems suspicious of your guessing streak\n");
            streakTextUsed = true;
          }
        }
        do {
          playerHand.add(Ranks.valueOf(bup.toUpperCase()));
          opponentHand.remove(Ranks.valueOf(bup.toUpperCase()));
          if (opponentHand.size() == 0) {
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

        } while (opponentHand.toString().contains(bup.toUpperCase()));
        if (checkForWin(i)) {
          break;
        }
        continue;
      }

      if (!opponentHand.toString().contains(bup.toUpperCase())) {
        view.display("Go Fish!\n\n");
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
        if (checkForWin(i)) {
          break;
        }
        //view.display(playerHand+"\n");
      }
      for (int j = 0; j < 13; ) {
        int randNum = rng.nextInt(opponentHand.size());
        String card = opponentHand.get(randNum).toString();

        if (playerHand.toString().contains(card)) {
          view.display("Your opponent asks for " + card.toString().toLowerCase() + (card.toLowerCase().equals("six")?"e":"") + "s, so you hand them over.\n");
          do {
            opponentHand.add(Ranks.valueOf(card));
            playerHand.remove(Ranks.valueOf(card));
            if (playerHand.size() == 0) {
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

          } while (playerHand.toString().contains(card));
          if (checkForWin(i)) {
            break;
          }
          view.display(playerHand+"\n");
          continue;
        }

        if (!playerHand.toString().contains(card)) {
          view.display(
              "Your opponent asks for "
                  + card.toString().toLowerCase()
                  + (card.toLowerCase().equals("six")?"e":"")
                  + "s, you say \"Go Fish!\", and he reaches to draw a card.\n");
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
          if (checkForWin(i)) {
            break;
          }
          break;
        }
      }
    }

    view.display("You:  " + playerPoints + " points\n");
    view.display("Them: " + opponentPoints + " points\n");
  }

  public boolean checkForPoint(ArrayList<Ranks> person, Ranks rank) {
    boolean success = false;
    int i = 0;

    if (person.contains(rank)) {
      do {
        person.remove(rank);
        countHand.add(rank);
        i++;
      } while (person.contains(rank));
      if (i == 4) {
        countHand.removeAll(countHand);
        if (playerHand.size() == 0) {
          drawForPlayer();
        }
        if (opponentHand.size() == 0) {
          drawForOpponent();
        }
        success = true;
      } else {
        person.addAll(countHand);
        countHand.removeAll(countHand);
        i = 0;
      }
    }
    return success;
  }

  public boolean checkForWin(int i) {
    boolean win = false;
    if (i == 13) {
      view.display("GAME!\n");
      if (playerPoints > opponentPoints) {
        view.display("YOU WIN!\n");
      } else if (opponentPoints > playerPoints) {
        view.display("YOU LOSE!\n");
      }
      win = true;
    }
    return win;
  }

  public void run() {
    boolean isInvalid = true;
    view.display("\nWelcome to Go Fish!\n");
    gameLogic();

    boolean gameRestart = view.promptForBoolean("Try Again? [Y/N]");
    if (gameRestart) {
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
