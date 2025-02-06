package com.study.jpa.chap04.repository;

import com.study.jpa.chap04.entity.Goods;
import com.study.jpa.chap04.entity.Purchase;
import com.study.jpa.chap04.entity.User;
import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
@Rollback(false)
class PurchaseRepositoryTest {

    @Autowired
    GoodsRepository goodsRepository;
    @Autowired
    PurchaseRepository purchaseRepository;
    @Autowired
    UserRepository userRepository;

    @Autowired
    EntityManager em;

    private User user1;
    private User user2;
    private User user3;
    private Goods goods1;
    private Goods goods2;
    private Goods goods3;

    @BeforeEach
    void setUp() {
        user1 = User.builder().name("망곰이").build();
        user2 = User.builder().name("하츄핑").build();
        user3 = User.builder().name("쿠로미").build();

        goods1 = Goods.builder().name("뚜비모자").build();
        goods2 = Goods.builder().name("닭갈비").build();
        goods3 = Goods.builder().name("중식도").build();

        userRepository.save(user1);
        userRepository.save(user2);
        userRepository.save(user3);

        goodsRepository.save(goods1);
        goodsRepository.save(goods2);
        goodsRepository.save(goods3);
    }

    @Test
    @DisplayName("유저와 상품을 연결한 구매기록 생성 테스트")
    void createPurchaseTest() {
        //given
        Purchase purchase = Purchase.builder()
                .user(user2)
                .goods(goods1)
                .build();
        //when
        Purchase savedPurchase = purchaseRepository.save(purchase);

        //then
        User user = savedPurchase.getUser();
        Goods goods = savedPurchase.getGoods();

        System.out.println("\n\n\n구매한 상품정보  = " + goods);
        System.out.println("\n\n\n구매한 회원정보  = " + user);
    }

    @Test
    @DisplayName("특정 유저의 모든 구매 정보 목록을 조회한다.")
    void findPurchasesTest() {
        //given
        Purchase purchase1 = Purchase.builder()
                .user(user1)
                .goods(goods1)
                .build();
        Purchase purchase2 = Purchase.builder()
                .user(user1)
                .goods(goods3)
                .build();
        //when
        purchaseRepository.save(purchase1);
        purchaseRepository.save(purchase2);

        em.flush();
        em.clear();

        //then
        User user = userRepository.findById(1L).orElseThrow();
        List<Purchase> purchases = user.getPurchases();

        for (Purchase purchase : purchases) {
            System.out.printf("%s가 구매한 상품명: %s\n"
            , user.getName(), purchase.getGoods().getName());
        }
    }

    @Test
    @DisplayName("특정 상품을 구매한 유저의 목록을 조회한다.")
    void findGoodsListByUser() {
        //given
        Purchase purchase1 = Purchase.builder()
                .user(user2)
                .goods(goods2)
                .build();
        Purchase purchase2 = Purchase.builder()
                .user(user3)
                .goods(goods2)
                .build();
        //when
        purchaseRepository.save(purchase1);
        purchaseRepository.save(purchase2);

        em.flush();
        em.clear();
        //then
        Goods goods = goodsRepository.findById(2L).orElseThrow();

//        goods.getPurchases().forEach(p -> {
//            System.out.printf("%s 상품을 구매한 회원 : %s\n",
//                    goods.getName(), p.getUser().getName());
//        });

        List<String> names = goods.getPurchases().stream()
                .map(p -> p.getUser().getName())
                .collect(Collectors.toList());

        System.out.println("names = " + names);
    }


    @Test
    @DisplayName("회원이 탈퇴하면 구매기록이 모두 삭제되어야 한다")
    void cascadeRemoveTest() {
        //given
        Purchase purchase1 = Purchase.builder()
                .user(user1).goods(goods2).build();

        Purchase purchase2 = Purchase.builder()
                .user(user1).goods(goods3).build();

        Purchase purchase3 = Purchase.builder()
                .user(user2).goods(goods1).build();

        purchaseRepository.save(purchase1);
        purchaseRepository.save(purchase2);
        purchaseRepository.save(purchase3);

        em.flush();
        em.clear();

        User user = userRepository.findById(user1.getId()).orElseThrow();
        List<Purchase> purchases = user.getPurchases();

        System.out.println("\n\nuser1's purchases = " + purchases + "\n\n");
        System.out.println("\n\nall of purchases = " + purchaseRepository.findAll() + "\n\n");

        userRepository.delete(user);

        em.flush();
        em.clear();
        //when

        List<Purchase> purchaseList = purchaseRepository.findAll();

        System.out.println("\n\nafter removing purchaseList = " + purchaseList + "\n\n");

        //then
        assertEquals(1, purchaseList.size());
    }



}