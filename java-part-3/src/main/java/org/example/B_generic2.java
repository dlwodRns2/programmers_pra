package org.example;

public class B_generic2 <T extends Number> {

    //덧셈
    public T add(T num1, T num2){
        if(num1 instanceof  Integer && num2 instanceof  Integer){
            int result = num1.intValue() + num2.intValue();
            return (T) Integer.valueOf(result);
        }else if(num1 instanceof Double && num2 instanceof Double){
            double result = num1.doubleValue() + num2.doubleValue();
            return (T) Double.valueOf(result);
        }
        throw new UnsupportedOperationException("지원되지 않는 타입입니다.");
    }
    public T subtract(T num1, T num2){
        if(num1 instanceof Integer && num2 instanceof  Integer){
            int result = num1.intValue() - num2.intValue();
            return (T) Integer.valueOf(result);
        }else if(num1 instanceof Double && num2 instanceof Double){
            double result = num1.doubleValue() - num2.doubleValue();
            return (T) Double.valueOf(result);
        }
        throw new UnsupportedOperationException("지원되지 않는 타입입니다.");
    }
    public T multiply(T num1, T num2){
        if(num1 instanceof Integer && num2 instanceof Integer){
            int result = num1.intValue() * num2.intValue();
            return (T) Integer.valueOf(result);
        }else if(num1 instanceof Double &&num2 instanceof Double){
            double result = num1.doubleValue() * num2.doubleValue();
            return (T) Double.valueOf(result);
        }
        throw new UnsupportedOperationException("지원되지 않는 타입입니다.");
    }
    public T divide(T num1, T num2){
        if ( num1 instanceof Integer && num2 instanceof Integer ) {
            int result = num1.intValue() / num2.intValue();
            return (T) Integer.valueOf( result );
        } else if ( num1 instanceof Double && num2 instanceof Double ) {
            double result = num1.doubleValue() / num2.doubleValue();
            return (T) Double.valueOf( result );
        }

        throw new UnsupportedOperationException("지원되지 않는 타입입니다.");
    }
    static void main(String[] args) {
        B_generic2<Integer> intCalc = new B_generic2<>();
        B_generic2<Double> doubleCalc = new B_generic2<>();

        System.out.println("Integer Addition: " + intCalc.add(10, 20));
        System.out.println("Integer Subtraction: " + intCalc.subtract(20, 10));
        System.out.println("Integer Multiplication: " + intCalc.multiply(10, 20));
        System.out.println("Integer Division: " + intCalc.divide(20, 10));

        System.out.println("Double Addition: " + doubleCalc.add(10.5, 20.3));
        System.out.println("Double Subtraction: " + doubleCalc.subtract(20.5, 10.2));
        System.out.println("Double Multiplication: " + doubleCalc.multiply(10.0, 20.0));
        System.out.println("Double Division: " + doubleCalc.divide(20.0, 10.0));
    }
}
