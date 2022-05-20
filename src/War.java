import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import static java.lang.Integer.parseInt;

public class War {
    private RingBuffer<String> player1 = new RingBuffer<>(52);
    private RingBuffer<String> player2 = new RingBuffer<>(52);
    private RingBuffer<String> pile = new RingBuffer<>(52);
    private Scanner f;

    public static void main(String args[]){
        War war = new War();
        war.begin();
    }

    private void begin(){
        try {
            f = new Scanner(new File("war.txt"));
        }
        catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        while(f.hasNextLine()) {
            player1 = new RingBuffer<>(52);
            player2 = new RingBuffer<>(52);
            Scanner deck = new Scanner(f.nextLine());
            while (deck.hasNext()) {
                player1.enqueue(deck.next());
            }
            deck = new Scanner(f.nextLine());
            while (deck.hasNext()) {
                player2.enqueue(deck.next());
            }
            int playCount = 0;
            while (!player1.isEmpty() && !player2.isEmpty() && playCount < 100000) {
                String p1card = player1.dequeue();
                String p2card = player2.dequeue();
                int x = intConvert(p1card.substring(0, 1));
                int y = intConvert(p2card.substring(0, 1));
                if (x > y) { //player1wins
                    playCount++;
                    //System.out.println(p1card+" "+p2card+" p1wins" + "playCount");
                    player1.enqueue(p1card);
                    player1.enqueue(p2card);
                    while (!pile.isEmpty())
                        player1.enqueue(pile.dequeue());
                    if(playCount % 250 == 0)
                        System.out.println(player1.size() + " " + player2.size());
                } else if (y > x) { //player2wins
                    playCount++;
                    //System.out.println(p1card+" "+p2card+" p2wins" + " " + "playCount");
                    player2.enqueue(p1card);
                    player2.enqueue(p2card);
                    while (!pile.isEmpty())
                        player2.enqueue(pile.dequeue());
                    if(playCount % 250 == 0)
                        System.out.println(player1.size() + " " + player2.size());
                } else { //tie
                    //System.out.println(p1card+" "+p2card+" tie");
                    pile.enqueue(p1card);
                    pile.enqueue(p2card);
                    pile.enqueue(player1.dequeue());
                    pile.enqueue(player2.dequeue());
                    //System.out.println(player1.size() + " " + player2.size());
                }
            }
            if (player1.isEmpty()) {
                System.out.println("Player 1 Wins");
            } else if (player2.isEmpty()) {
                System.out.println("Player 2 Wins");
            } else {
                System.out.println("Tie game stopped at 100000 plays");
            }
        }
    }

    private int intConvert(String s){
        switch(s){
            case "T": return 10;
            case "J": return 11;
            case "Q": return 12;
            case "K": return 13;
            case "A": return 14;
            default: return parseInt(s);
        }
    }
}
