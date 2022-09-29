package site.metacoding.firstapp.domain.product;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import org.mybatis.spring.boot.test.autoconfigure.MybatisTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.web.WebAppConfiguration;

//SpringBootTest 모든것을 다 메모리에 띄움
//Vscode에서 import 자동 입력 키 : alt + shift + o 

@SpringBootTest
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
}
