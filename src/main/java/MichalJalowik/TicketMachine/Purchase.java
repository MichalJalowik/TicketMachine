package MichalJalowik.TicketMachine;

import java.util.ArrayList;
import java.util.List;

public class Purchase {

    private List<Ticket> chart = new ArrayList<>();
    private double totalPriceToPay;

    public void addToChart(Ticket ticket){
        chart.add(ticket);
    }

    public void removeFromChart(Ticket ticket){
        chart.remove(ticket);
    }

    public void getChart(){
        System.out.print("//// CART: ");
        double totalPrice = 0;
        for(Ticket ticket : chart){
            System.out.format("(%s %s PLN) ",ticket.getTicketType(), ticket.getTicketPrice());
            totalPrice = totalPrice + ticket.getTicketPrice();
        }
        System.out.format("\\\\\\\\ To be paid %.2f PLN     ",totalPrice);
        totalPriceToPay = totalPrice;
    }

    public double getTotalPriceToPay(){
        return totalPriceToPay;
    }

    public void getTickets(){
        for(Ticket ticket : chart){
            System.out.format("%s %s PLN TICKET",ticket.getTicketType().toUpperCase(), ticket.getTicketPrice());
            System.out.println("");
        }
    }

    public int cartSize(){
        return chart.size();
    }





}
