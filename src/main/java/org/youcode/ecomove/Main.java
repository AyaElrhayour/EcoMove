package org.youcode.ecomove;

import org.youcode.ecomove.db.DBConnection;
import org.youcode.ecomove.submenu.PartnerMenu;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        while (true){
            System.out.println(" -----------------------------");
            System.out.println("|                             |");
            System.out.println("|           EcoMove           |");
            System.out.println("|  1 : Gestion du Partner     |");
            System.out.println("|  2 : Gestion Du Contrats    |");
            System.out.println("|  3 : Gestion Du Offres      |");
            System.out.println("|  4 : Gestion Du Billets     |");
            System.out.println("|  5 : EXIT                   |");
            System.out.println("|                             |");
            System.out.println(" -----------------------------");
            System.out.println();
            System.out.print("Enter Your Choice : ");

            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice){
                case 1:
                    PartnerMenu.partnerManagementMenu();
                    break;
                case 5:
                    System.out.println("Thank you for your hard work! have a good day!");
                    DBConnection.closeConnection();
                    scanner.close();
                    System.exit(0);
                default:
                    System.out.println("Invalid choice. Please select a valid option.");
                    break;

            }
        }


    }
}