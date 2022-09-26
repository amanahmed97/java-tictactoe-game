import java.io.*;
import java.lang.*;
import java.util.*;

public class Main {
    public static void main(String[] args) {

        System.out.println("Hello world!");
        System.out.println("May I know your name?");
        Scanner ip = new Scanner(System.in);
        System.out.println("Enter name: ");
        String name = ip.nextLine();
        System.out.println("Hello "+name+"!");


    }
}