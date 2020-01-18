package MichalJalowik.TicketMachine;

import java.util.*;

public class MoneyTray {

    private HashMap<Double, Integer> CashAvailable = new HashMap<Double, Integer>();

    public void checkMoneyAvailability(){
        double total = 0;

        for(Double element: CashAvailable.keySet()){
            total = CashAvailable.get(element) * element + total;
        }

        String currency = "PLN";
        System.out.format("\n------ Service mode ON ------    -> | %s%.2f %s | nominal values: %s", "Total: ",total, currency,CashAvailable);
        System.out.println("");
    }

    public boolean isMachineAvailable (){

        for(Double nominalQuantity : CashAvailable.keySet()){
            if(safetyNominalStock()){
                System.out.println("Sorry, machine out of order");
                return false;
            }
        }
        return true;
    }

    public boolean safetyNominalStock (){
        return CashAvailable.get(0.05) < 50 || CashAvailable.get(0.1) < 50 || CashAvailable.get(1.0) < 50
                || CashAvailable.get(2.0) < 30 || CashAvailable.get(5.0) < 10;
    }

    public void putMoney(double nominal, int quantity ) {

        try {
            CashAvailable.put(nominal, (quantity + CashAvailable.get(nominal)));
        } catch (NullPointerException ex){
            CashAvailable.put(nominal, quantity);
        }
    }

    public void takeMoney(double nominal, int quantity ) {
        if(!(CashAvailable.get(nominal) - quantity < 0)){
            CashAvailable.put(nominal,CashAvailable.get(nominal) - quantity);
        }
    }

    public void giveChange(double chargeAmount){
        System.out.println("");
        System.out.format("%s %.2f PLN....","Wait for Your change",chargeAmount);
        System.out.println("");

        double moneyLeftToPay = chargeAmount;

        for(Map.Entry<Double, Integer> nominal : sortChartDescending()) {

            while (Math.round(moneyLeftToPay*100)/100d >= nominal.getKey() ){
                takeMoney(nominal.getKey(),1);
                System.out.println(nominal.getKey() + " MONEY BILL");
                moneyLeftToPay = moneyLeftToPay - nominal.getKey();
            }
        }
    }

    public Set<Map.Entry<Double, Integer>> sortChartDescending(){
        Set<Map.Entry<Double, Integer>> entries = CashAvailable.entrySet();

//        for(Map.Entry<Double, Integer> entry : entries){

        TreeMap<Double, Integer> sortedCashAvailable = new TreeMap<>(CashAvailable);
        Set<Map.Entry<Double, Integer>> mappings = sortedCashAvailable.entrySet();

//        for(Map.Entry<Double, Integer> mapping : mappings){
//            //System.out.println(mapping.getKey() + " ==> " + mapping.getValue());
//            System.out.print(mapping + " ");
//        }

        //System.out.println(mappings);

        //System.out.println("comparator ponizej");

        Comparator<Map.Entry<Double, Integer>> valueComparator = new Comparator<Map.Entry<Double, Integer>>() {

            @Override
            public int compare(Map.Entry<Double, Integer> e1, Map.Entry<Double, Integer> e2) {
                Double v1 = e1.getKey();
                Double v2 = e2.getKey();
                return v2.compareTo(v1);
            }
        };

        List<Map.Entry<Double, Integer>> listOfEntries = new ArrayList<Map.Entry<Double, Integer>>(entries);
        Collections.sort(listOfEntries, valueComparator);

        LinkedHashMap<Double, Integer> sortedByValueUsingComparator = new LinkedHashMap<Double, Integer>(listOfEntries.size());

        for(Map.Entry<Double, Integer> entry : listOfEntries){
            sortedByValueUsingComparator.put(entry.getKey(), entry.getValue());
        }

        //System.out.println("HashMap after sorting entries by values ");
        Set<Map.Entry<Double, Integer>> entrySetSortedByValue = sortedByValueUsingComparator.entrySet();

        //System.out.println(entrySetSortedByValue);

        return entrySetSortedByValue;

    }



}
