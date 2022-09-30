package site.metacoding.firstapp.domain.product;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.mybatis.spring.boot.test.autoconfigure.MybatisTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.context.annotation.Import;

import site.metacoding.firstapp.config.MyBatisConfig;

//SpringBootTest 모든것을 다 메모리에 띄움
//Vscode에서 import 자동 입력 키 : alt + shift + o 

@Import(MyBatisConfig.class) // MyBatisTest가 MyBatisConfig를 못읽음 , 강제로 import해줌
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE) // 실DB사용,
@MybatisTest
public class ProductDaoTest {

    // 단위 테스트는 생성자 주입이 안된다.
    // 외부에서 주입할때 빈 생성자만 주입해준다. -> 생성자 주입이 되지 않는다.
    @Autowired
    private ProductDao productDao;

    // test는 절대 return이 안된다 void사용
    @Test
    public void findById_test() {
        // given - 받아야 할 것 ex) delete에선 id가 필요하다 id를 임의로 준다
        Integer productId = 1;

        // when - 테스트
        Product productPS = productDao.findById(productId);

        // then - 검증
        assertEquals("바나나", productPS.getProductName());
    }

    @Test
    public void findAll_test() {
        List<Product> productListPS = productDao.findAll();
        System.out.println(productListPS.get(0).getProductName());
        assertEquals(2, productListPS.size());
    }

    // Mybatis는 ResultSet을 자바 Entity로 변경해줄때 , 빈생성자를 호출하고 setter가 없어도 매핑해준다.
    // setter을 걸어줄 필요가 없다.

    @Test
    public void insert_test() {
        // given
        String productName = "수박"; // 데이터의 일관성을 위해 생성해서 넘겨줌.
        Integer productPrice = 1000;
        Integer productQty = 100;

        // when
        // insert 의도를 정확하게 하려고 3개가 있는 생성자를 일부러 만들어줌
        // Junit은 메서드 실행전에 트랜젝션이 걸리고, 메서드 실행이 끝나면rollback가 된다. -> 스택안에서만 놀고 끝남.
        Product product = new Product(productName, productPrice, productQty);
        int result = productDao.insert(product);
        // then
        assertEquals(1, result);
    }

    @Test
    public void update_test() {
        // given
        Integer productId = 1;
        String productName = "수박";
        Integer productPrice = 1000;
        Integer productQty = 100;

        Product product = new Product(productName, productPrice, productQty);
        product.setProductId(productId);

        // verify 영속화
        Product productPS = productDao.findById(product.getProductId());
        assertTrue(product == null ? false : true);

        // when
        productPS.update(product);
        int result = productDao.update(productPS);

        // then
        assertEquals(1, result);
    }

    @Test
    public void delete_test() {
        // given
        Integer productId = 1;

        // verify
        Product product = productDao.findById(productId);
        System.out.println("product id :" + product.getProductId());
        System.out.println("product 이름 :" + product.getProductName());
        System.out.println("product 가격 :" + product.getProductPrice());
        System.out.println("product 수량 :" + product.getProductQty());
        assertTrue(product == null ? false : true);

        // when
        int result = productDao.deleteById(productId);

        // then
        assertEquals(1, result);
    }
}
