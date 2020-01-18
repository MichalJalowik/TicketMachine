package MichalJalowik.TicketMachine;

public enum Ticket {
    ALL_DAY(6.8, "All Day"), DISCOUNT(1.4, "Discount"), NORMAL(2.8, "Normal");

    private final double ticketPrice;
    private final String ticketType;

    Ticket(double ticketPrice, String ticketType) {
        this.ticketPrice = ticketPrice;
        this.ticketType = ticketType;
    }

    public double getTicketPrice() {
        return ticketPrice;
    }

    public String getTicketType() {
        return ticketType;
    }
}
