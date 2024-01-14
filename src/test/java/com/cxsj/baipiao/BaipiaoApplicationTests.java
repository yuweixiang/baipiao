package com.cxsj.baipiao;

import com.cxsj.baipiao.dal.dao.OrderMapper;
import com.cxsj.baipiao.domain.Order;
import com.cxsj.baipiao.enums.OrderStatusEnum;

import com.cxsj.baipiao.service.order.OrderService;
import com.cxsj.baipiao.service.order.OrderServiceImpl;
import org.apache.ibatis.session.SqlSessionFactory;
import org.junit.FixMethodOrder;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import javax.sql.DataSource;

@RunWith(SpringRunner.class)
@SpringBootTest(
        webEnvironment = SpringBootTest.WebEnvironment.MOCK,
        classes = BaipiaoApplication.class)
@AutoConfigureMockMvc
@TestPropertySource(
        locations = "classpath:application.properties")
class BaipiaoApplicationTests extends BaseServiceTest{

    @Autowired
    private OrderMapper orderMapper;

    @Resource
    private OrderServiceImpl orderService;

    @Test
     void addOrder() {
        Order order = new Order();
        order.setId(12L);
        order.setPrice(10d);
        order.setUserId(123L);
        order.setStatus(OrderStatusEnum.PAID.getCode());
        orderMapper.insert(order);
    }

}
