package org.checkfood.util;


public class BarcodeControlSumChecker {

    public static final boolean checkBarcode(String barcode) {
        if (barcode != null) {
            return isNumber(barcode) && checkLenght(barcode) && checkControlSum(barcode);
        }
        return false;
    }

    private static boolean isNumber(String barcode) {
        try {
            Long.parseLong(barcode);
            return true;
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }
        return false;
    }

    private static boolean checkLenght(String barcode) {
        if (barcode.length() == 8 || barcode.length() == 12 || barcode.length() == 13) {
            return true;
        } else {
            return false;
        }
    }

    private static boolean checkControlSum(String barcode) {
        if (barcode.length() == 8) {
            barcode = "00000" + barcode;
        }

        if (barcode.length() == 12) {
            barcode = "0" + barcode;
        }

        int[] numbers = prepareNumbers(barcode);

        int sum_1 = (numbers[1] + numbers[3] + numbers[5] + numbers[7] + numbers[9] + numbers[11]) * 3;
        int sum_2 = numbers[0] + numbers[2] + numbers[4] + numbers[6] + numbers[8] + numbers[10];
        int sum_3 = (sum_1 + sum_2) % 10;
        int checkSum = (10 - sum_3) % 10;

        return checkSum == numbers[12];
    }

    private static int[] prepareNumbers(String barcode) {
        String[] numbersSymvol = barcode.split("");

        int[] numbers = new int[numbersSymvol.length - 1];

        for (int i = 0; i < numbersSymvol.length - 1; i++) {
            numbers[i] = Integer.parseInt(numbersSymvol[i + 1]);
        }

        return numbers;
    }
}
