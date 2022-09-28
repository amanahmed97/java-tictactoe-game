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
                turnInput=0;
                break;
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

public class Main {
    public static void playerSet(){
        Player player1, player2;
        Scanner ip = new Scanner(System.in);
        // Player 1
        System.out.println("Player 1:");
        System.out.print("Please enter your name:");
        player1 = new Player(ip.nextLine(),1);
        System.out.println("Hello "+player1.name+"!\n");
        // Player 2
        System.out.println("Player 2:");
        System.out.print("Please enter your name:");
        player2 = new Player(ip.nextLine(),2);
        System.out.println("Hello "+player2.name+"!\n");
    }

    public static void main(String[] args) {
        //Initialize the objects of the game
        Scanner ip = new Scanner(System.in);
        BoardTicTacToe board;
//        board.printBoard();

        // Input for the game
        System.out.println("\nWelcome to the Java Tic-Tac-Toe game!!");

        playerSet();

        // Game Menu
        while(true){
            System.out.println("\nSelect your game:\n1.Tic-Tac-Toe\n2.Order and Chaos\n3.Exit");
            System.out.print("Enter:");

            int option = ip.nextInt();

            switch (option){
                case 1:
                    board = new BoardTicTacToe(3);
                    RunTicTacToe.runGame(board);
                    break;
                case 3:
                    System.exit(0);
                    break;
                default:
                    System.out.println("Invalid option. Try again.");
            }

//            System.out.println("Rules: \n Choose Board size");


        }

    }
}