package MichalJalowik.TicketMachine;

import java.util.InputMismatchException;
import java.util.Scanner;

public class TicketMachine {

    private MoneyTray moneyTray = new MoneyTray();
    private Scanner scanner = new Scanner(System.in);
    private boolean serviceMode;
    private Purchase purchase;

    public void takeTicket() {

        moneyTray.isMachineAvailable();
        purchase = new Purchase();
        menu();
    }

    public void menu() {

        int exit = 0;
        System.out.println("");
        System.out.println("Welcome to MJ ticket machine");
        System.out.println("");

        while (exit != 9 && moneyTray.isMachineAvailable()) {

            try {

                System.out.format("%-30s%-5s(1)%-20s(2)%-20s(3)%-20s(9)%-20s ", "Choose one option", "->", "Add ticket", "Remove ticket", "Pay", "Exit");
                purchase.getChart();

                int whatToDo = scanner.nextInt();
                scanner.nextLine();

                switch (whatToDo) {

                    case 1:
                        addTicket();
                        break;

                    case 2:
                        removeTicket();
                        break;

                    case 3:
                        pay();

                    case 9:
                        System.out.println("");
                        System.out.println("Thanks for using MJ ticket machine");
                        exit = 9;

                }
            } catch (InputMismatchException ex) {
                System.out.println("Invalid value " + ex + " - turn on again");
                break;
            }

        }
        scanner.close();
    }

    public void addTicket() {
        System.out.format("%-30s%-5s(1)%-20s(2)%-20s(3)%-20s(9)%-20s ", "Add ticket type to cart", "->", (Ticket.ALL_DAY + " " + Ticket.ALL_DAY.getTicketPrice() + " PLN"),
                (Ticket.DISCOUNT + " " + Ticket.DISCOUNT.getTicketPrice() + " PLN"), (Ticket.NORMAL + " " + Ticket.NORMAL.getTicketPrice() + " PLN"), "Exit");

        int addType = scanner.nextInt();
        scanner.nextLine();
        switch (addType) {
            case 1:
                purchase.addToChart(Ticket.ALL_DAY);
                break;
            case 2:
                purchase.addToChart(Ticket.DISCOUNT);
                break;
            case 3:
                purchase.addToChart(Ticket.NORMAL);
                break;
        }
    }

    public void removeTicket() {
        System.out.format("%-30s%-5s(1)%-20s(2)%-20s(3)%-20s(9)%-20s ", "Remove ticket type from cart", "->", (Ticket.ALL_DAY + " " + Ticket.ALL_DAY.getTicketPrice() + " PLN"),
                (Ticket.DISCOUNT + " " + Ticket.DISCOUNT.getTicketPrice() + " PLN"), (Ticket.NORMAL + " " + Ticket.NORMAL.getTicketPrice() + " PLN"), "Exit");

        int removeType = scanner.nextInt();
        scanner.nextLine();
        switch (removeType) {
            case 1:
                purchase.removeFromChart(Ticket.ALL_DAY);
                break;
            case 2:
                purchase.removeFromChart(Ticket.DISCOUNT);
                break;
            case 3:
                purchase.removeFromChart(Ticket.NORMAL);
                break;
        }
    }

    public void pay() {
        double purchaseMoneyGiven = 0;
        double moneyLeftToPay = purchase.getTotalPriceToPay() - purchaseMoneyGiven;

        while (round(moneyLeftToPay) != 0 && moneyTray.isMachineAvailable()) {
            System.out.println("");
            System.out.format("%-30s%-5s%-17s%-2.2f%4.3s ", "Insert nominal", "->", "To be paid left: ", moneyLeftToPay, "PLN");

            purchaseMoneyGiven = scanner.nextDouble();
            scanner.nextLine();

            if (checkNominal(purchaseMoneyGiven)) {

                moneyTray.putMoney(purchaseMoneyGiven, 1);
                System.out.println("");
                moneyLeftToPay = moneyLeftToPay - purchaseMoneyGiven;
                showMoneyTray();

                if (round(moneyLeftToPay) < 0) {
                    double chargeAmount = Math.round(moneyLeftToPay * 100) / 100.0d * -1;
                    moneyTray.giveChange(chargeAmount);
                    printTickets();
                    break;
                } else if (round(moneyLeftToPay) == 0) {
                    printTickets();
                }

            } else {
                System.out.println("Invalid value");
            }
        }
    }

    public double round (double moneyLeftToPay){
        return Math.round(moneyLeftToPay * 100) / 100.0d;
    }

    private boolean checkNominal (double purchaseMoneyGiven){
        return purchaseMoneyGiven == 0.05 || purchaseMoneyGiven == 0.10 || purchaseMoneyGiven == 0.20 || purchaseMoneyGiven == 0.50 ||
                purchaseMoneyGiven == 1 || purchaseMoneyGiven == 2 || purchaseMoneyGiven == 5 || purchaseMoneyGiven == 10 || purchaseMoneyGiven == 20;
    }

    public void printTickets() {
        System.out.println("");
        System.out.println("printing " + purchase.cartSize() + " tickets....");
        purchase.getTickets();
        showMoneyTray();
    }

    public void service() {
        serviceModeOn(true);
        moneyTray.putMoney(0.05, 100);
        moneyTray.putMoney(0.10, 51);
        moneyTray.putMoney(1, 100);
        moneyTray.putMoney(2, 50);
        moneyTray.putMoney(5, 20);
        moneyTray.putMoney(10, 10);
        moneyTray.putMoney(20, 10);
        moneyTray.takeMoney(10, 5);
        moneyTray.takeMoney(20, 5);
        showMoneyTray();
    }

    public boolean serviceModeOn(boolean on){
        return this.serviceMode = on;
    }

    private void showMoneyTray() {
        if (serviceMode) {
            moneyTray.checkMoneyAvailability();
        }
    }
}
