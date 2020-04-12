package cz.kodytek.shop;

import java.time.LocalDate;

public class Playground {

    public static void main(String[] args) {
        System.out.println(String.valueOf(LocalDate.now().minusYears(2).getYear()).substring(2, 4));
    }

}
