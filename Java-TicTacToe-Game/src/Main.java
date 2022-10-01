import java.io.*;
import java.lang.*;
import java.util.*;

/*
Goal: Make an OO & extensible Java game.

Class and Object Design for Game:
    1. Board
    2. BoardMarker
    3. Player
    4. GamePiece
    5. RunGame
    6. Score
    7. Turn based
    8. Actions/Ruleset

Requirements:
    Interactive, Display, Turn Based, memory store.
    Ask player name, board numbered, maintain score and win conditions.
    Maintain coding standards and OO Design.
    Inheritance, Encapsulation.
*/

// General Class
abstract class Board{
    int rows;
    int columns;

    public Board(int r, int c){
        rows = r;
        columns = c;
    }
    public void printBoard(){
        return;
    }
}
abstract class BoardMarker{
    int position;
    char symbol;
    String marker;
}
class Player{
    String name;
    int team = 0;
    int score = 0;
    String playerMarker;

    public Player(String name, int team){
        this.name = name;
        this.team = team;
    }
    public Player(String name, int team, String playerMarker){
        this.name = name;
        this.team = team;
        this.playerMarker = playerMarker;
    }

    public int scoreUpdate(int s){
        score += s;
        return score;
    }
    public int getScore(){
        return score;
    }
}
class GamePiece{
    char piece;
    String pieceMark;

    public void GamePiece(char gp, String pm){
        piece = gp;
        pieceMark = pm;
    }
}
interface RunGame{
    public static boolean gameCondition() {
        return false;
    }
    public static void runGame(){
        return;
    }
}
interface Score{
    public static void printScore(){
        return;
    }
}
abstract class Rules{
    String ruleset;

    public static void printRules(){
        return;
    }
}

// Inherited from Game classes
class TTTMarker extends BoardMarker{
    public TTTMarker(int p, char s){
        position = p;
        symbol = s;
    }
    public TTTMarker(int p){
        position = p;
        symbol = '-';
    }
}
class TTTSymbol extends GamePiece{
    static final char symbolX = 'X';
    static final char symbolO = 'O';
}
class RulesTicTacToe extends Rules{
    public static void printRules() {
        System.out.println("Rules:\n" +
                "1. Board size ranges from 3 to 1000\n" +
                "2. Player to choose to play as X or O\n" +
                "3. Game turns will alternate for players\n" +
                "4. Enter position to place marker on board" +
                "5. To win get a row, column or diagonal, full of only Xs or only Os\n");
    }
}
class RulesOrderNChaos extends Rules{
    public static void printRules() {
        System.out.println("Rules:\n" +
                "1. Board size is 6x6\n" +
                "2. Player to choose to play as Order or Chaos\n" +
                "3. Game turns will alternate for players\n" +
                "4. Both players Order and Chaos can choose X or O in each turn\n" +
                "5. Enter position to place marker on board\n" +
                "6. The player Order wins by creating a 5-in-a-row of either Xs or Os.\n" +
                "7. The opponent Chaos endeavors to prevent this and aims to fill the board without completion" +
                "of a line of 5 like pieces.\n");
    }
}
class BoardTicTacToe extends Board{
    TTTMarker[] BoardMap;
    public BoardTicTacToe(int n) {
        super(n, n);
        BoardMap = new TTTMarker[n*n];
        for(int i=0;i<n*n;i++){
            BoardMap[i] = new TTTMarker(i+1);
        }
    }

    public void printBoard(){

        for(int i=0;i<rows;i++){

            for(int k=0;k<columns;k++){
                if(rows*columns>9) System.out.print("+----");
                else System.out.print("+---");
            }
            System.out.print("+\n");

            for(int j=0;j<columns;j++){
                if(BoardMap[(i*columns+j)].symbol == '-')
                    System.out.print("| "+BoardMap[(i*columns+j)].position);
                else
                    System.out.print("| "+BoardMap[(i*columns+j)].symbol);
                if((rows*columns>9 && (i*columns+j)<9) || (rows*columns>9 && BoardMap[(i*columns+j)].symbol != '-'))
                    System.out.print("  ");
                else
                    System.out.print(" ");
            }
            System.out.print("|\n");
        }
        //Last line pattern
        for(int i=1;i<=columns;i++){
            if(rows*columns>9) System.out.print("+----");
            else System.out.print("+---");
        }
        System.out.print("+\n");
    }

    public int setBoard(int position, char symbol){
        BoardMap[position].symbol = symbol;
        return BoardMap[position].position;
    }

    public int getBoardSymbol(int position){
        return BoardMap[position].symbol;
    }
}
class PlayerTTT extends Player{
    public PlayerTTT(String name, int team){
        super(name,team);
    }
    public static int getNumberPlayers(){
        // Default set to 2 players
        int numberPlayers = 2;
        System.out.println("Number of Players : "+numberPlayers);
        return numberPlayers;
    }
    public static Player[] playerSet(int numberPlayers){
        Scanner ip = new Scanner(System.in);
        Player[] players = new Player[numberPlayers];

        for(int i=0; i<numberPlayers;i++){
            // Player Information input
            System.out.println("Player "+(i+1)+" :");
            System.out.print("Please enter your name:");
            players[i] = new Player(ip.nextLine(),i+1);
            System.out.println("Hello "+players[i].name+"!\n");
        }

        return players;
    }
}
class PlayerScore implements Score{
    public static void printScore(int numberPlayers, Player[] players) {
        System.out.println("\nPlayer Scores:");
        for(int i=0;i<numberPlayers;i++)
            System.out.println("Player "+players[i].name+" : "+players[i].score);
    }
}
class RunTicTacToe implements RunGame{

    public static boolean gameCondition(BoardTicTacToe board, Player[] players, int playerX, int playerO){
        // Setting the counters for X and O to check across rows, column, diagonals
        int countXRow = 0;
        int countXColumn = 0;
        int countXDiagonal1 = 0;
        int countXDiagonal2 = 0;
        int countORow = 0;
        int countOColumn = 0;
        int countODiagonal1 = 0;
        int countODiagonal2 = 0;

        for(int i=0;i<board.rows;i++){
            countXRow = 0;
            countXColumn = 0;
            countXDiagonal1 = 0;
            countXDiagonal2 = 0;
            countORow = 0;
            countOColumn = 0;
            countODiagonal1 = 0;
            countODiagonal2 = 0;
            for(int j=0;j<board.rows;j++){
                // For X: Rows, Columns, Diagonals
                if(board.getBoardSymbol(i*board.rows+j) == TTTSymbol.symbolX)
                    countXRow++;
                if(board.getBoardSymbol(j*board.rows+i) == TTTSymbol.symbolX)
                    countXColumn++;
                if(board.getBoardSymbol(j*board.rows+j) == TTTSymbol.symbolX)
                    countXDiagonal1++;
                if(board.getBoardSymbol((j*board.rows+(board.rows-1-j))) == TTTSymbol.symbolX)
                    countXDiagonal2++;
                // For O: Rows, Columns, Diagonals
                if(board.getBoardSymbol(i*board.rows+j) == TTTSymbol.symbolO)
                    countORow++;
                if(board.getBoardSymbol(j*board.rows+i) == TTTSymbol.symbolO)
                    countOColumn++;
                if(board.getBoardSymbol(j*board.rows+j) == TTTSymbol.symbolO)
                    countODiagonal1++;
                if(board.getBoardSymbol((j*board.rows+(board.rows-1-j))) == TTTSymbol.symbolO)
                    countODiagonal2++;
            }
            // Check if any of the counters have reached required sum for win
            if(countXRow==board.rows || countXColumn==board.rows || countXDiagonal1==board.rows || countXDiagonal2==board.rows){
                System.out.println("X Wins!!");
                players[playerX-1].scoreUpdate(1);
                System.out.println("Player "+players[playerX-1].name+" Wins!!!");
                System.out.println("Player "+players[playerX-1].name+" points : "+players[playerX-1].score);
                return true;
            }
            if(countORow==board.rows || countOColumn==board.rows || countODiagonal1==board.rows || countODiagonal2==board.rows){
                System.out.println("O Wins!!");
                players[playerO-1].scoreUpdate(1);
                System.out.println("Player "+players[playerO-1].name+" Wins!!!");
                System.out.println("Player "+players[playerO-1].name+" points : "+players[playerO-1].score);
                return true;
            }
        }

        return false;
    }

    public static void runGame(BoardTicTacToe board, int numberPlayers, Player[] players){
        System.out.println("Starting game");
        // Setting up game variables
        int gameTurn = 0;
        int turnInput = 0;
        boolean gameCheck = false;
        Scanner ip = new Scanner(System.in);
        char SymbolTurn = TTTSymbol.symbolX;
        int playerX=1;
        int playerO=2;
        // Handling user inputs
        System.out.println("\nWhich player wants to be X?\nPlayers:\n1. Player "+players[0].name+"\n2. Player "+players[1].name);
        System.out.print("\nEnter listed Player ID number : ");
        try{
            playerX = ip.nextInt();
        }catch(Exception e){
            System.out.println("Invalid Input. Assigning defaults to players");
            playerX=1;
            playerO=2;
            System.out.println("Player 1 is X.\nPlayer 2 is O.");
            ip.next();
        }

        if(playerX==1) playerO = 2;
        else if (playerX==2) playerO=1;
        else{
            System.out.println("Invalid Input. Assigning defaults to players");
            playerX=1;
            playerO=2;
            System.out.println("Player 1 is X.\nPlayer 2 is O.");
        }

        while(gameTurn<(board.rows*board.columns)){
            System.out.println("\nGame Turn "+(gameTurn+1));
            board.printBoard();
            // User playing inputs
            if (SymbolTurn==TTTSymbol.symbolX) System.out.println("Player Turn : "+players[playerX-1].name);
            else System.out.println("Player Turn : "+players[playerO-1].name);
            System.out.print("Enter a position on board to mark "+SymbolTurn+" : ");

            try{
                turnInput = ip.nextInt();
            }catch (Exception e){
                // Flush the input token, to ask input again
                ip.next();
            }
            // Update the board and game based on user input
            if(SymbolTurn==TTTSymbol.symbolX && turnInput>=1 && turnInput<=(board.rows*board.columns) &&
                    board.getBoardSymbol(turnInput-1)=='-'){
                board.setBoard(turnInput-1, TTTSymbol.symbolX);
                gameTurn++;
                SymbolTurn = TTTSymbol.symbolO;
            }
            else if(SymbolTurn==TTTSymbol.symbolO && turnInput>=1 && turnInput<=(board.rows*board.columns) &&
                    board.getBoardSymbol(turnInput-1)=='-'){
                board.setBoard(turnInput-1, TTTSymbol.symbolO);
                gameTurn++;
                SymbolTurn = TTTSymbol.symbolX;
            }
            else
                System.out.println("Enter valid position. Try Again.");
            // Check game state for any win conditions
            gameCheck = gameCondition(board,players,playerX,playerO);
            if(gameCheck){
                board.printBoard();
                System.out.println("Game Won!!");
                break;
            }
        }

        if(!gameCheck){
            board.printBoard();
            System.out.println("Stalemate!\nGame Over!");
        }

        PlayerScore.printScore(numberPlayers,players);
    }
}

class RunOrderNChaos implements RunGame{

    public static boolean gameCondition(BoardTicTacToe board, Player[] players, int playerOrder,int playerChaos){
        // Setting the counters for X and O to check across rows, column, diagonals
        int countXRow = 0;
        int countXColumn = 0;
        int countXDiagonal1 = 0;
        int countXDiagonal2 = 0;
        int countXDiagonal3 = 0;
        int countXDiagonal4 = 0;
        int countXDiagonal5 = 0;
        int countXDiagonal6 = 0;
        int countORow = 0;
        int countOColumn = 0;
        int countODiagonal1 = 0;
        int countODiagonal2 = 0;
        int countODiagonal3 = 0;
        int countODiagonal4 = 0;
        int countODiagonal5 = 0;
        int countODiagonal6 = 0;
        int[] diagonal3Map = {6,13,20,27,34};
        int[] diagonal4Map = {1,8,15,22,29};
        int[] diagonal5Map = {4,9,14,19,24};
        int[] diagonal6Map = {11,16,21,26,31};


        for(int i=0;i<board.rows;i++){
            countXRow = 0;
            countXColumn = 0;
            countXDiagonal1 = 0;
            countXDiagonal2 = 0;
            countXDiagonal3 = 0;
            countXDiagonal4 = 0;
            countXDiagonal5 = 0;
            countXDiagonal6 = 0;
            countORow = 0;
            countOColumn = 0;
            countODiagonal1 = 0;
            countODiagonal2 = 0;
            countODiagonal3 = 0;
            countODiagonal4 = 0;
            countODiagonal5 = 0;
            countODiagonal6 = 0;

            for(int j=0;j<board.rows;j++) {
                // For X check
                // For X Row
                if (board.getBoardSymbol(i * board.rows + j) == TTTSymbol.symbolX) {
                    countXRow++;
                    if (countORow>0 && countORow!=5) countORow--;
                }
                // For X Column
                if (board.getBoardSymbol(j * board.rows + i) == TTTSymbol.symbolX) {
                    countXColumn++;
                    if (countOColumn>0  && countOColumn!=5) countOColumn--;
                }
                // For X Diagonal 1
                if (board.getBoardSymbol(j * board.rows + j) == TTTSymbol.symbolX) {
                    countXDiagonal1++;
                    if (countODiagonal1>0 && countODiagonal1!=5) countODiagonal1--;
                }
                // For X Diagonal 2
                if (board.getBoardSymbol((j * board.rows + (board.rows - 1 - j))) == TTTSymbol.symbolX){
                    countXDiagonal2++;
                    if (countODiagonal2>0 && countODiagonal2!=5) countODiagonal2--;
                }
                // For X Diagonal 3,4,5,6 Map
                if (j!=5){
                    // For X Diagonal 3
                    if (board.getBoardSymbol(diagonal3Map[j]) == TTTSymbol.symbolX) countXDiagonal3++;
                    // For X Diagonal 4
                    if (board.getBoardSymbol(diagonal4Map[j]) == TTTSymbol.symbolX) countXDiagonal4++;
                    // For X Diagonal 5
                    if (board.getBoardSymbol(diagonal5Map[j]) == TTTSymbol.symbolX) countXDiagonal5++;
                    // For X Diagonal 6
                    if (board.getBoardSymbol(diagonal6Map[j]) == TTTSymbol.symbolX) countXDiagonal6++;
                }

                // For O
                // For O Row
                if(board.getBoardSymbol(i*board.rows+j) == TTTSymbol.symbolO){
                    countORow++;
                    if (countXRow>0 && countXRow!=5) countXRow--;
                }
                // For O Column
                if(board.getBoardSymbol(j*board.rows+i) == TTTSymbol.symbolO) {
                    countOColumn++;
                    if (countXColumn>0 && countXColumn!=5) countXColumn--;
                }
                // For O Diagonal 1
                if(board.getBoardSymbol(j*board.rows+j) == TTTSymbol.symbolO) {
                    countODiagonal1++;
                    if (countXDiagonal1>0 && countXDiagonal1!=5) countXDiagonal1--;
                }
                // For O Diagonal 2
                if(board.getBoardSymbol((j*board.rows+(board.rows-1-j))) == TTTSymbol.symbolO) {
                    countODiagonal2++;
                    if (countXDiagonal2>0 && countXDiagonal2!=5) countXDiagonal2--;
                }
                // For O Diagonal 3,4,5,6 Map
                if (j!=5){
                    // For O Diagonal 3
                    if (board.getBoardSymbol(diagonal3Map[j]) == TTTSymbol.symbolO) countODiagonal3++;
                    // For O Diagonal 4
                    if (board.getBoardSymbol(diagonal4Map[j]) == TTTSymbol.symbolO) countODiagonal4++;
                    // For O Diagonal 5
                    if (board.getBoardSymbol(diagonal5Map[j]) == TTTSymbol.symbolO) countODiagonal5++;
                    // For O Diagonal 6
                    if (board.getBoardSymbol(diagonal6Map[j]) == TTTSymbol.symbolO) countODiagonal6++;
                }
            }
            // Check if any of the counters have reached total of 5 in a row
            if(countXRow>=5 || countXColumn>=5 || countXDiagonal1>=5 || countXDiagonal2>=5 ||
                    countXDiagonal3>=5 || countXDiagonal4>=5 || countXDiagonal5>=5 || countXDiagonal6>=5){
                System.out.println("X Line Found!!\nOrder Wins!!");
                players[playerOrder-1].scoreUpdate(1);
                System.out.println("Player "+players[playerOrder-1].name+" Wins!!!");
                System.out.println("Player "+players[playerOrder-1].name+" points : "+players[playerOrder-1].score);
                return true;
            }
            if(countORow>=5 || countOColumn>=5 || countODiagonal1>=5 || countODiagonal2>=5 ||
                    countODiagonal3>=5 || countODiagonal4>=5 || countODiagonal5>=5 || countODiagonal6>=5){
                System.out.println("O Line Found!!\nOrder Wins!!");
                players[playerOrder-1].scoreUpdate(1);
                System.out.println("Player "+players[playerOrder-1].name+" Wins!!!");
                System.out.println("Player "+players[playerOrder-1].name+" points : "+players[playerOrder-1].score);
                return true;
            }
        }

        return false;
    }

    public static void runGame(BoardTicTacToe board, int numberPlayers, Player[] players) {
        System.out.println("Starting game");
        // Setting up game variables
        int gameTurn = 0;
        int turnInput = 0;
        Scanner ip = new Scanner(System.in);
        char SymbolTurn = TTTSymbol.symbolX;
        int playerOrder = 1;
        int playerChaos = 2;
        String playerTurn = "order";
        boolean gameCheck = false;
        // Handling user inputs
        System.out.println("\nWhich player wants to be Order?\nPlayers:\n1. Player " + players[0].name + "\n2. Player " + players[1].name);
        System.out.print("\nEnter listed Player ID number : ");

        try{
            playerOrder = ip.nextInt();
        }catch(Exception e){
            System.out.println("Invalid Input. Assigning defaults to players");
            playerOrder=1;
            playerChaos=2;
            System.out.println("Player 1 is Order.\nPlayer 2 is Chaos.");
            ip.next();
        }

        if(playerOrder==1) playerChaos = 2;
        else if (playerOrder==2) playerChaos=1;
        else{
            System.out.println("Invalid Input. Assigning defaults to players");
            playerOrder=1;
            playerChaos=2;
            System.out.println("Player 1 is Order.\nPlayer 2 is Chaos.");
        }

        while(gameTurn<(board.rows*board.columns)){
            System.out.println("\nGame Turn "+(gameTurn+1));
            board.printBoard();
            // User playing inputs
            try{
                if (playerTurn.equals("order")) System.out.println("Order Turn\nPlayer Turn : "+players[playerOrder-1].name);
                else System.out.println("Chaos Turn\nPlayer Turn : "+players[playerChaos-1].name);

                System.out.print("Choose "+TTTSymbol.symbolX+" or "+TTTSymbol.symbolO+" to mark on board : ");
                SymbolTurn = ip.next().charAt(0);
                SymbolTurn = Character.toUpperCase(SymbolTurn);
                System.out.print("Enter a position on board to mark it : ");
                turnInput = ip.nextInt();
            }catch (Exception e){
                // Flush the input token, to ask input again
                ip.next();
            }
            // Update the board and game based on user input
            if(SymbolTurn==TTTSymbol.symbolX && turnInput>=1 && turnInput<=(board.rows*board.columns) &&
                    board.getBoardSymbol(turnInput-1)=='-'){
                board.setBoard(turnInput-1, TTTSymbol.symbolX);
                gameTurn++;
                SymbolTurn = TTTSymbol.symbolO;
                if (playerTurn.equals("order")) playerTurn="chaos";
                else playerTurn="order";
            }
            else if(SymbolTurn==TTTSymbol.symbolO && turnInput>=1 && turnInput<=(board.rows*board.columns) &&
                    board.getBoardSymbol(turnInput-1)=='-'){
                board.setBoard(turnInput-1, TTTSymbol.symbolO);
                gameTurn++;
                SymbolTurn = TTTSymbol.symbolX;
                if (playerTurn.equals("order")) playerTurn="chaos";
                else playerTurn="order";
            }
            else
                System.out.println("Enter valid position. Try Again.");
            // Check game state for any win conditions
            gameCheck = RunOrderNChaos.gameCondition(board,players,playerOrder,playerChaos);
            if(gameCheck){
                board.printBoard();
                System.out.println("Game Won!!");
                break;
            }
        }

        if(!gameCheck){
            board.printBoard();
            System.out.println("Chaos Wins!!\nGame Over!");
            players[playerChaos-1].scoreUpdate(1);
            System.out.println("Player "+players[playerChaos-1].name+" Wins!!!");
            System.out.println("Player "+players[playerChaos-1].name+" points : "+players[playerChaos-1].score);
        }

        PlayerScore.printScore(numberPlayers,players);
    }
}

public class Main {
    public static void main(String[] args) {

        //Initialize the objects of the game
        Scanner ip = new Scanner(System.in);
        BoardTicTacToe board;
        int option = 0;
        int numberPlayers=2;
        Player[] players;

        // Input for the game
        System.out.print("\nWelcome to the Java Tic-Tac-Toe game!!!");
        System.out.println("\n=======================================\n");

        numberPlayers = PlayerTTT.getNumberPlayers();
        players = PlayerTTT.playerSet(numberPlayers);

        // Game Menu
        while(true){
            System.out.println("\n=======================================\n");
            System.out.println("\nSelect your game:\n1.Tic-Tac-Toe\n2.Order and Chaos\n3.Scores\n4.Exit");
            System.out.print("Enter:");

            try{
                option = ip.nextInt();
            }catch(Exception e){
                // Flush the input token, to ask input again
                ip.next();
            }

            switch (option){
                case 1:
                    System.out.println("\n=======================================\n");
                    System.out.println("\nTic Tac Toe");
                    RulesTicTacToe.printRules();
                    System.out.print("Choose Board size : ");
                    int size=3;
                    try{
                        size = ip.nextInt();
                        if (size<3 || size>1000) throw new Exception("Out of valid range");
                    }catch(InputMismatchException e){
                        ip.next();
                        System.out.println("Enter valid board size.");
                        break;
                    }catch(Exception e){
                        System.out.println(e.toString()+"\nEnter valid board size.");
                        break;
                    }

                    board = new BoardTicTacToe(size);
                    RunTicTacToe.runGame(board, numberPlayers, players);
                    break;
                case 2:
                    System.out.println("\n=======================================\n");
                    System.out.println("\nOrder N Chaos\n");
                    RulesOrderNChaos.printRules();
                    board = new BoardTicTacToe(6);
                    RunOrderNChaos.runGame(board, numberPlayers, players);
                    break;
                case 3:
                    System.out.println("\n=======================================\n");
                    PlayerScore.printScore(numberPlayers,players);
                    break;
                case 4:
                    System.out.println("\n=======================================\n");
                    System.out.println("Thank You for playing!!\nSee you soon!");
                    System.exit(0);
                    break;
                default:
                    System.out.println("Invalid option. Try again.");
            }

        }

    }
}