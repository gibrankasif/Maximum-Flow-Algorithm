package com.gibran.MaximumFlow;

import java.util.Scanner;

public class ConsoleApplication {
    public static void main(String[] args) {





    }

    public void consoleMenu() {
        Scanner fileInput = new Scanner(System.in);




        consoleMenuLoop:
        while(true) {
            System.out.println("--------------------------------------------------------------");
            System.out.println("Press \"A\" to add a Football club");
            System.out.println("Press \"D\" to relegate a Football club");
            System.out.println("Press \"T\" to print the Premier League table chart");
            System.out.println("Press \"F\" to find club statistics and information of a specific club");
            System.out.println("Press \"M\" to add a played match");
            System.out.println("Press \"R\" to generate a random played match");
            System.out.println("Press \"Q\" to quit and save all progress");
            System.out.println("--------------------------------------------------------------");

            Scanner consoleSC = new Scanner(System.in);
            String userChoice = consoleSC.nextLine().toUpperCase();


            switch (userChoice) {
                case "A":
                    break;
                case "D":
                    break;
                case "T":
                    break;
                case "F":
                    break;
                case "M":
                    break;
                case "R":
                    break;
                case "S":
                    break;

                case "Q":
                    break consoleMenuLoop;
                default:
                    System.out.println("Please insert a valid option!");
            }
        }




    }
}
