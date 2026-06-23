package org.example.springthreory.ioc;
interface Bean{
    String getName();
}
class EthiopiaBean implements Bean{

    @Override
    public String getName() {
        return "에티오피아 원두";
    }
}
class ColombiaBean implements Bean{

    @Override
    public String getName() {
        return "콜롬비아 원두";
    }
}
public class CoffeeMaker {
    //Step1
    //private Bean bean = new ColombiaBean();
    //private Bean bean = new EthiopiaBean();
    private Bean bean;

    public CoffeeMaker(Bean bean){
        this.bean=bean;
    }
    void brew(){
        System.out.println(bean.getName() + "으로 커피를 내립니다.");
    }

    public static void main(String[] args) {
        CoffeeMaker cm = new CoffeeMaker(new ColombiaBean());
        cm.brew();
    }
}
