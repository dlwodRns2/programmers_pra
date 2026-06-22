package org.example.springthreory.solidPrac;
interface Machine{
    void print();
    void scan();
    void fax();
}
interface Printer{
    void print();
}
interface Scanner{
    void scan();
}
interface Faxer{
    void fax();
}
class SimplePrinter implements Printer{
    String name="구형 프린터";
    @Override
    public void print() {
        System.out.println("인쇄만 합니다");
    }
}
class SmartMachine implements Printer, Scanner{
    String name = "복합기";
    @Override
    public void print() {
        System.out.println("인쇄");
    }

    @Override
    public void scan() {
        System.out.println("스캔");
    }
}
public class Isp {
    public static void main(String[] args) {
        Printer p = new SimplePrinter();
        p.print();

        SmartMachine sm = new SmartMachine();
        sm.print();
        sm.scan();

    }
}
