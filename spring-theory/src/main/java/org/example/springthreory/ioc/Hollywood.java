package org.example.springthreory.ioc;
interface ClickListener{
    void onClick();
}
class LikeAction implements ClickListener{

    @Override
    public void onClick() {
        System.out.println("내 코드 실행: 좋아요!");
    }
}
class Button {
    private ClickListener listener;
    public void setListener(ClickListener listener){
        this.listener=listener;
    }

    public void press(){
        System.out.println("[시스템] 버튼이 눌렸습니다.");
        listener.onClick();
    }
}
public class Hollywood {
    public static void main(String[] args) {

    }

}
