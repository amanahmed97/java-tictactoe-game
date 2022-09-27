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
// Inherit for Game Class

class BoardTicTacToe extends Board{

    public BoardTicTacToe(int r, int c) {
        super(r, c);
    }

    public void printBoard(){
        int dim = 3;

        for(int i=0;i<dim;i++){

            for(int k=1;k<=dim;k++){
                System.out.print("+---");
            }
            System.out.print("+\n");

            for(int j=0;j<dim;j++){
                System.out.print("| "+(i*3+j+1)+" ");
            }
            System.out.print("|\n");
        }
        //Last line pattern
        for(int i=1;i<=dim;i++){
            System.out.print("+---");
        }
        System.out.print("+\n");
    }

}

public class Main {

    public static void main(String[] args) {
        //Initialize the objects of the game
        Scanner ip = new Scanner(System.in);
        BoardTicTacToe board = new BoardTicTacToe(3,3);
        board.printBoard();
        Player player1, player2;

        // Input for the game
        System.out.println("\nWelcome to the Java Tic-Tac-Toe game!!");

        // Player 1
        System.out.println("Player 1:");
        System.out.print("Please enter your name:");
        player1 = new Player(ip.nextLine(),1);
        // Player 2
        System.out.println("Hello "+player1.name+"!\n");
        System.out.println("Player 2:");
        System.out.print("Please enter your name:");
//        player2 = new Player(ip.nextLine(),2);
//        System.out.println("Hello "+player2.name+"!\n");

        // Start the game
        while(true){
            System.out.println("\nSelect your game:\n1.Tic-Tac-Toe\n2.Order and Chaos\n3.Exit");
            System.out.print("Enter:");

            int option = ip.nextInt();
            if (option==3) break;

//            System.out.println("Rules: \n Choose Board size");
            System.out.println("Starting game");


        }

    }
}