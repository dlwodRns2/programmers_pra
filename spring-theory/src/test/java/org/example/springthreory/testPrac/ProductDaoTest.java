package org.example.springthreory.testPrac;

import org.example.springthreory.AppConfig;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

import java.util.NoSuchElementException;

import static org.junit.jupiter.api.Assertions.*;

@SpringJUnitConfig(AppConfig.class)
class ProductDaoTest {

    @Autowired
    private ProductDao dao;

    @BeforeEach
    public void setUp(){
        dao.deleteAll();
    }
    private Product newProduct(String id, String name, int price) {
        Product p = new Product();
        p.setId(id); p.setName(name); p.setPrice(price);
        return p;
    }

    @Test
    void add(){
        //given
        assertEquals(0,dao.getCount());

        //when
        dao.add(newProduct("p1","연필",500));

        //then
        assertEquals(1,dao.getCount());

    }

    @Test
    void get(){
        //given
        Product product = newProduct("p1","연필",500);
        dao.add(product);

        //when
        Product p = dao.get("p1");

        //then
        assertEquals(p.getName(), product.getName());
        assertEquals(p.getPrice(), product.getPrice());
    }

    @Test
    void add_중복_id_예외(){
        //given
        final Product product = newProduct("p1","연필",500);
        dao.add(product);

        //when
        Executable action = new Executable(){
            @Override
            public void execute(){
                dao.add(product);
            }
        };
        assertThrows(IllegalStateException.class,action);
    }

    @Test
    void get_없는_id_예외(){
        Executable action = new Executable(){
            @Override
            public void execute(){
                dao.get("no");
            }
        };
        assertThrows(NoSuchElementException.class,action);
    }

    @Disabled("일부러 틀린 기대값을 넣은 학습용 실패 예제")
    @Test
    void 일부러_실패하는_테스트(){
        dao.add(newProduct("fail_demo", "공책", 1000));
        assertEquals(2, dao.getCount());   // 실제는 1건인데 2 기대 → 실패
    }
}