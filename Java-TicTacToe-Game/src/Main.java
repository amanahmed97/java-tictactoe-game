import java.io.*;
import java.lang.*;
import java.util.*;

/*
Goal: Make an OO & extensible Java game.

Class and Object Design for Game:
    1. Board
    2. Player
    3. Each matrix pod having object
    4. Turn based
    5. Actions/Ruleset

Requirements:
    Interactive, Display, Turn Based, Active memory store.
    Ask player name, board numbered, maintain score and sin conditions.
    Maintain coding standards and OO Design.
    Inheritance, Encapsulation.

    S - Single-responsiblity Principle
    O - Open-closed Principle
    L - Liskov Substitution Principle
    I - Interface Segregation Principle
    D - Dependency Inversion Principle

*/
// General Class
class Board{
    int rows;
    int columns;

    public Board(int r, int c){
        rows = r;
        columns = c;
    }
}
class BoardMarker{
    int position;
    char symbol;
}
class Player{
    String name =  new String();
    int team = 0;
    int score = 0;

    public Player(String name, int team){
        this.name = name;
        this.team = team;
    }

    public int scoreUpdate(int s){
        score += s;
        return score;
    }
}
class GamePiece{
    char piece;

    public void GamePiece(char gp){
        piece = gp;
    }
}

// Inherit for Game Class
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
//        int dim = 3;

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
                if(rows*columns>9 && (i*columns+j)<9) System.out.print("  ");
                else System.out.print(" ");
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

class RunTicTacToe{

    public static boolean gameCondition(BoardTicTacToe board){
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
                // For X
                if(board.getBoardSymbol(i*board.rows+j) == TTTSymbol.symbolX)
                    countXRow++;
                if(board.getBoardSymbol(j*board.rows+i) == TTTSymbol.symbolX)
                    countXColumn++;
                if(board.getBoardSymbol(j*board.rows+j) == TTTSymbol.symbolX)
                    countXDiagonal1++;
                if(board.getBoardSymbol((j*board.rows+(board.rows-1-j))) == TTTSymbol.symbolX)
                    countXDiagonal2++;
                // For O
                if(board.getBoardSymbol(i*board.rows+j) == TTTSymbol.symbolO)
                    countORow++;
                if(board.getBoardSymbol(j*board.rows+i) == TTTSymbol.symbolO)
                    countOColumn++;
                if(board.getBoardSymbol(j*board.rows+j) == TTTSymbol.symbolO)
                    countODiagonal1++;
                if(board.getBoardSymbol((j*board.rows+(board.rows-1-j))) == TTTSymbol.symbolO)
                    countODiagonal2++;
            }
            if(countXRow==board.rows || countXColumn==board.rows || countXDiagonal1==board.rows || countXDiagonal2==board.rows){
                System.out.println("X Wins!!");
                return true;
            }
            if(countORow==board.rows || countOColumn==board.rows || countODiagonal1==board.rows || countODiagonal2==board.rows){
                System.out.println("O Wins!!");
                return true;
            }
        }

        return false;
    }

    public static void runGame(BoardTicTacToe board){
        System.out.println("Starting game");
        int gameTurn = 0;
        int turnInput = 0;
        Scanner ip = new Scanner(System.in);
        char SymbolTurn = TTTSymbol.symbolX;

        while(gameTurn<(board.rows*board.columns)){
            board.printBoard();
            System.out.print("Enter a position on board to mark "+SymbolTurn+" : ");
            try{
                turnInput = ip.nextInt();
            }catch (Exception e){
                // Flush the input token, to ask input again
                ip.next();
            }

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

            if(gameCondition(board)){
                board.printBoard();
                System.out.println("Game Won!!");
                break;
            }
        }

        if(!gameCondition(board)){
            board.printBoard();
            System.out.println("Stalemate!\nGame Over!");
        }
    }
}

class RunOrderNChaos{

    public static boolean gameCondition(BoardTicTacToe board){
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
            for(int j=0;j<board.rows;j++) {
                // For X check
                // For X Row
                if (board.getBoardSymbol(i * board.rows + j) == TTTSymbol.symbolX) {
                    countXRow++;
                    if (countORow > 0) countORow--;
                }
                // For X Column
                if (board.getBoardSymbol(j * board.rows + i) == TTTSymbol.symbolX) {
                    countXColumn++;
                    if (countOColumn > 0) countOColumn--;
                }
                // For X Diagonal 1
                if (board.getBoardSymbol(j * board.rows + j) == TTTSymbol.symbolX) {
                    countXDiagonal1++;
                    if (countODiagonal1 > 0) countODiagonal1--;
                }
                // For X Diagonal 2
                if (board.getBoardSymbol((j * board.rows + (board.rows - 1 - j))) == TTTSymbol.symbolX){
                    countXDiagonal2++;
                    if (countODiagonal2 > 0) countODiagonal2--;
                }

                // For O
                // For O Row
                if(board.getBoardSymbol(i*board.rows+j) == TTTSymbol.symbolO){
                    countORow++;
                    if (countXRow>0) countXRow--;
                }
                // For O Column
                if(board.getBoardSymbol(j*board.rows+i) == TTTSymbol.symbolO) {
                    countOColumn++;
                    if (countXColumn>0) countXColumn--;
                }
                // For O Diagonal 1
                if(board.getBoardSymbol(j*board.rows+j) == TTTSymbol.symbolO) {
                    countODiagonal1++;
                    if (countXDiagonal1>0) countXDiagonal1--;
                }
                // For O Diagonal 2
                if(board.getBoardSymbol((j*board.rows+(board.rows-1-j))) == TTTSymbol.symbolO) {
                    countODiagonal2++;
                    if (countXDiagonal2>0) countXDiagonal2--;
                }
            }
            if(countXRow>=5 || countXColumn>=5 || countXDiagonal1>=5 || countXDiagonal2>=5){
                System.out.println("X Line Found!!\nOrder Wins!!");
                return true;
            }
            if(countORow>=5 || countOColumn>=5 || countODiagonal1>=5 || countODiagonal2>=5){
                System.out.println("O Line Found!!\nOrder Wins!!");
                return true;
            }
        }

        return false;
    }

    public static void runGame(BoardTicTacToe board){
        System.out.println("Starting game");
        int gameTurn = 0;
        int turnInput = 0;
        Scanner ip = new Scanner(System.in);
        char SymbolTurn = TTTSymbol.symbolX;

        while(gameTurn<(board.rows*board.columns)){
            board.printBoard();
            try{
                System.out.print("Choose "+TTTSymbol.symbolX+" or "+TTTSymbol.symbolO+" to mark on board : ");
                SymbolTurn = ip.next().charAt(0);
                System.out.print("Enter a position on board to mark it : ");
                turnInput = ip.nextInt();
            }catch (Exception e){
                // Flush the input token, to ask input again
                ip.next();
            }

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

            if(RunOrderNChaos.gameCondition(board)){
                board.printBoard();
                System.out.println("Game Won!!");
                break;
            }
        }

        if(!gameCondition(board)){
            board.printBoard();
            System.out.println("Chaos Wins!!\nGame Over!");
        }
    }
}

public class Main {
    public static int getNumberPlayers(){
        int numberPlayers = 2;
        Scanner ip = new Scanner(System.in);
        System.out.print("Enter number of players : ");
        try {
            numberPlayers = ip.nextInt();
        }catch(InputMismatchException e){
            // Flush the input token, to ask input again
            ip.next();
            System.out.print("Invalid non-integer input.\nEnter number of players : ");
            numberPlayers = ip.nextInt();
        }
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

    public static void main(String[] args) {

        //Initialize the objects of the game
        Scanner ip = new Scanner(System.in);
        BoardTicTacToe board;
        int option = 0;
        int numberPlayers=2;
        Player[] players;

        // Input for the game
        System.out.println("\nWelcome to the Java Tic-Tac-Toe game!!");

        numberPlayers = getNumberPlayers();
        players = playerSet(numberPlayers);

        // Game Menu
        while(true){
            System.out.println("\nSelect your game:\n1.Tic-Tac-Toe\n2.Order and Chaos\n3.Exit");
            System.out.print("Enter:");

            try{
                option = ip.nextInt();
            }catch(Exception e){
                // Flush the input token, to ask input again
                ip.next();
            }

            switch (option){
                case 1:
                    System.out.println("\nTic Tac Toe\nRules: \n");
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
                    RunTicTacToe.runGame(board);
                    break;
                case 2:
                    System.out.println("\nOrder N Chaos\nRules: \n");
                    board = new BoardTicTacToe(6);
                    RunOrderNChaos.runGame(board);
                    break;
                case 3:
                    System.exit(0);
                    break;
                default:
                    System.out.println("Invalid option. Try again.");
            }

        }

    }
}