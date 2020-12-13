package com.bai;

import com.bai.entity.User;
import com.bai.mapper.UserMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
public class MpTest {

  @Autowired
  private UserMapper userMapper;

  //查询user表所有数据
  @Test
  public void findAll() {
    List<User> users = userMapper.selectList(null);
    System.out.println(users);
  }

  //添加操作
  @Test
  public void addUser() {
    User user = new User();
    user.setName("岳不群1");
    user.setAge(70);
    user.setEmail("lucy@qq.com");

//        user.setCreateTime(new Date());
//        user.setUpdateTime(new Date());

    int insert = userMapper.insert(user);
    System.out.println("insert:"+insert);
  }

  //分页查询
  @Test
  public void testPage() {
    //1 创建page对象
    //传入两个参数：当前页 和 每页显示记录数
    Page<User> page = new Page<>(1,3);
    //调用mp分页查询的方法
    //调用mp分页查询过程中，底层封装
    //把分页所有数据封装到page对象里面
    userMapper.selectPage(page,null);

    //通过page对象获取分页数据
    System.out.println(page.getCurrent());//当前页
    System.out.println(page.getRecords());//每页数据list集合
    System.out.println(page.getSize());//每页显示记录数
    System.out.println(page.getTotal()); //总记录数
    System.out.println(page.getPages()); //总页数

    System.out.println(page.hasNext()); //下一页
    System.out.println(page.hasPrevious()); //上一页

  }
}
