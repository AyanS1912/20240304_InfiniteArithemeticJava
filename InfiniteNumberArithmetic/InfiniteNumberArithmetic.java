package InfiniteNumberArithmetic;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class InfiniteNumberArithmetic {

    private List<Integer> internalList = new ArrayList<>();

    public InfiniteNumberArithmetic(int inputObject) {
        this.initializeFromArray(convertNumberToArray(inputObject));
    }

    public InfiniteNumberArithmetic(String inputObject) {
        this.initializeFromArray(convertStringToArray(inputObject));
    }

    public InfiniteNumberArithmetic(List<Integer> inputObject) {
        this.initializeFromArray(inputObject);
    }

    public InfiniteNumberArithmetic(InfiniteNumberArithmetic inputObject) {
        this.initializeFromArray(inputObject.internalList);
    }

    private void initializeFromArray(List<Integer> array) {
        this.internalList = new ArrayList<>(array);
    }

    private List<Integer> convertNumberToArray(int num) {
        if (num < 0) {
            throw new IllegalArgumentException("Input cannot be negative.");
        }
        String numString = Integer.toString(num);
        return convertStringToArray(numString);
    }

    private List<Integer> convertStringToArray(String str) {
        if (str.length() == 0) {
            throw new IllegalArgumentException("Empty string is not accepted.");
        }
        if (!str.matches("\\d+")) {
            throw new IllegalArgumentException("String can have decimal numbers only.");
        }
        List<Integer> array = new ArrayList<>();
        for (char c : str.toCharArray()) {
            array.add(Character.getNumericValue(c));
        }
        return array;
    }

    public List<Integer> getInternalList() {
        return new ArrayList<>(internalList);
    }

    public String getNumberAsString() {
        StringBuilder stringBuilder = new StringBuilder();
        for (int digit : internalList) {
            stringBuilder.append(digit);
        }
        return stringBuilder.toString();
    }

    public InfiniteNumberArithmetic addition(InfiniteNumberArithmetic obj) {
        List<Integer> resultArray = addArrays(internalList, obj.internalList);
        return new InfiniteNumberArithmetic(resultArray);
    }

    public InfiniteNumberArithmetic subtraction(InfiniteNumberArithmetic obj) {
        List<Integer> resultArray = subtractNumbers(internalList, obj.internalList);
        return new InfiniteNumberArithmetic(resultArray);
    }

    public InfiniteNumberArithmetic multiplication(InfiniteNumberArithmetic obj) {
        List<Integer> resultArray = multiply(internalList, obj.internalList);
        return new InfiniteNumberArithmetic(resultArray);
    }

    private List<Integer> addArrays(List<Integer> arr1, List<Integer> arr2) {
        if (arr1.isEmpty() || arr2.isEmpty()) {
            throw new IllegalArgumentException("Both inputs should be non empty arrays.");
        }

        int i = arr1.size() - 1;
        int j = arr2.size() - 1;
        int carry = 0;
        List<Integer> result = new ArrayList<>();

        while (i >= 0 || j >= 0) {
            int value1 = (i >= 0) ? arr1.get(i) : 0;
            int value2 = (j >= 0) ? arr2.get(j) : 0;

            int sum = value1 + value2 + carry;
            int rem = sum % 10;
            result.add(0, rem);
            carry = sum / 10;

            i--;
            j--;
        }

        if (carry > 0) {
            result.add(0, carry);
        }

        return result;
    }

    private List<Integer> subtractNumbers(List<Integer> arr1, List<Integer> arr2) {
        if (arr1.isEmpty() || arr2.isEmpty()) {
            throw new IllegalArgumentException("Both inputs should be non empty arrays.");
        }

        // Check if arr1 is smaller than arr2
        boolean isNegative = false;
        if (arr1.size() < arr2.size() || (arr1.size() == arr2.size() && isLessThan(arr1, arr2))) {
            // Swap arr1 and arr2
            List<Integer> temp = arr1;
            arr1 = arr2;
            arr2 = temp;
            isNegative = true;
        }

        List<Integer> result = new ArrayList<>();
        int i = arr1.size() - 1;
        int j = arr2.size() - 1;

        while (i >= 0 || j >= 0) {
            int value1 = (i >= 0) ? arr1.get(i) : 0;
            int value2 = (j >= 0) ? arr2.get(j) : 0;

            if (value1 < value2) {
                arr1.set(i - 1, arr1.get(i - 1) - 1);
                value1 += 10;
            }

            int diff = value1 - value2;
            result.add(0, diff);

            i--;
            j--;
        }

        // Remove leading zeros
        while (!result.isEmpty() && result.get(0) == 0) {
            result.remove(0);
        }

        if (result.isEmpty()) {
            // If result is zero, return just a single zero
            result.add(0);
        } else if (isNegative) {
            // If subtraction result is negative, prepend a minus sign
            result.set(0, -result.get(0));
        }

        return result;
    }

    // Helper method to check if arr1 is less than arr2
    private boolean isLessThan(List<Integer> arr1, List<Integer> arr2) {
        for (int i = 0; i < arr1.size(); i++) {
            int digit1 = arr1.get(i);
            int digit2 = (i < arr2.size()) ? arr2.get(i) : 0;
            if (digit1 < digit2) {
                return true;
            } else if (digit1 > digit2) {
                return false;
            }
        }
        return false; // If both numbers are equal
    }
    private List<Integer> multiply(List<Integer> arr1, List<Integer> arr2) {
        if (arr1.isEmpty() || arr2.isEmpty()) {
            throw new IllegalArgumentException("Both inputs should be non empty arrays.");
        }

        List<Integer> result = new ArrayList<>(Arrays.asList(0));
        int j = arr2.size() - 1;
        int count = 0;

        while (j >= 0) {
            List<Integer> baseSum = new ArrayList<>(Arrays.asList(0));

            while (arr2.get(j) > 0) {
                baseSum = addArrays(baseSum, arr1);
                arr2.set(j, arr2.get(j) - 1);
            }

            for (int i = 0; i < count; i++) {
                baseSum.add(0);
            }
            count++;

            result = addArrays(result, baseSum);
            j--;
        }

        return result;
    }
    
    
    public class DivisionResult {
        private List<Integer> quotient;
        private List<Integer> remainder;

        public DivisionResult(List<Integer> quotient, List<Integer> remainder) {
            this.quotient = quotient;
            this.remainder = remainder;
        }

        public String getQuotientAsString() {
            StringBuilder quotientString = new StringBuilder();
            for (int digit : quotient) {
                quotientString.append(digit);
            }
            return quotientString.toString();
        }

        public String getRemainderAsString() {
            StringBuilder remainderString = new StringBuilder();
            for (int digit : remainder) {
                remainderString.append(digit);
            }
            return remainderString.toString();
        }
    }

    public DivisionResult division(InfiniteNumberArithmetic divisor) {
        // Check if divisor is zero
        if (divisor.internalList.size() == 1 && divisor.internalList.get(0) == 0) {
            throw new IllegalArgumentException("Division by zero is not allowed.");
        }

        List<Integer> quotient = new ArrayList<>();
        List<Integer> remainder = new ArrayList<>(internalList); // Copy of dividend

        while (isGreaterThanOrEqualTo(remainder, divisor.internalList)) {
            remainder = subtractNumbers(remainder, divisor.internalList);
            incrementQuotient(quotient);
        }

        return new DivisionResult(quotient, remainder);
    }

    private boolean isGreaterThanOrEqualTo(List<Integer> arr1, List<Integer> arr2) {
        // Check if arr1 is greater than or equal to arr2
        if (arr1.size() > arr2.size()) {
            return true;
        } else if (arr1.size() < arr2.size()) {
            return false;
        } else {
            for (int i = 0; i < arr1.size(); i++) {
                if (arr1.get(i) > arr2.get(i)) {
                    return true;
                } else if (arr1.get(i) < arr2.get(i)) {
                    return false;
                }
            }
            return true; // Both numbers are equal
        }
    }

    private void incrementQuotient(List<Integer> quotient) {
        int carry = 1;
        for (int i = quotient.size() - 1; i >= 0; i--) {
            int sum = quotient.get(i) + carry;
            quotient.set(i, sum % 10);
            carry = sum / 10;
        }
        if (carry > 0) {
            quotient.add(0, carry);
        }
    }


    public static void main(String[] args) {
        // Test addition
        InfiniteNumberArithmetic num1 = new InfiniteNumberArithmetic(123);
        InfiniteNumberArithmetic num2 = new InfiniteNumberArithmetic(456);
        InfiniteNumberArithmetic result = num1.addition(num2);
        System.out.println("Addition: " + result.getNumberAsString());

 

        // Test multiplication
        InfiniteNumberArithmetic num5 = new InfiniteNumberArithmetic(123);
        InfiniteNumberArithmetic num6 = new InfiniteNumberArithmetic(456);
        result = num5.multiplication(num6);
        System.out.println("Multiplication: " + result.getNumberAsString());
        
        
        // Test subtraction
      InfiniteNumberArithmetic num3 = new InfiniteNumberArithmetic(123);
      InfiniteNumberArithmetic num4 = new InfiniteNumberArithmetic(1000);
      result = num3.subtraction(num4);
      System.out.println("Subtraction: " + result.getNumberAsString());
      
      
   // Test division
      InfiniteNumberArithmetic dividend = new InfiniteNumberArithmetic(1000);
      InfiniteNumberArithmetic divisor = new InfiniteNumberArithmetic(15);
      DivisionResult divisionResult = dividend.division(divisor);
      System.out.println("Division Quotient: " + divisionResult.getQuotientAsString());
      System.out.println("Division Remainder: " + divisionResult.getRemainderAsString());
      }
}


