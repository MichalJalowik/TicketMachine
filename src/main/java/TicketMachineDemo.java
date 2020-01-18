import MichalJalowik.TicketMachine.*;

public class TicketMachineDemo {

    public static void main(String[] Args){

        TicketMachine ticketMachine = new TicketMachine();
        ticketMachine.service();
        ticketMachine.takeTicket();
    }
}

