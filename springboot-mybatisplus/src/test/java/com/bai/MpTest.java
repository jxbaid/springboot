package com.bai;

import com.bai.entity.User;
import com.bai.mapper.UserMapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Arrays;
import java.util.HashMap;
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
    user.setName("xxx");
    user.setAge(20);
    user.setEmail("xx@qq.com");
//        user.setCreateTime(new Date());
//        user.setUpdateTime(new Date());
    int insert = userMapper.insert(user);
    System.out.println("insert:"+insert);
  }

  //修改操作
  @Test
  public void updateById() {
    User user = new User();
    user.setId(1338125791241760769L);
    user.setAge(28);
    userMapper.updateById(user);
  }

  //测试乐观锁
  @Test
  public void testOptimisticLocker() {
    //根据id查询数据
    User user = userMapper.selectById(1338125791241760769L);
    //进行修改
    user.setName("林志玲");
    userMapper.updateById(user);
  }

  //多个id批量查询
  @Test
  public void testSelectBatchIds() {
    List<User> users = userMapper.selectBatchIds(Arrays.asList(1L, 2L, 3L));
    System.out.println(users);
  }
  @Test
  public void testSelectByMap(){

    HashMap<String, Object> map = new HashMap<>();
    map.put("name", "Jone");
    map.put("age", 18);
    List<User> users = userMapper.selectByMap(map);
    users.forEach(System.out::println);
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

  //删除操作 物理删除
  @Test
  public void testDeleteById(){
    int result = userMapper.deleteById(1338070705400524802L);
    System.out.println(result);
  }
  //批量删除
  @Test
  public void testDeleteBatchIds() {
    int result = userMapper.deleteBatchIds(Arrays.asList(3,4L));
    System.out.println(result);
  }
  /**
   * 测试 逻辑删除后的查询：
   * 不包括被逻辑删除的记录
   */
  @Test
  public void testLogicDeleteSelect() {
    User user = new User();
    List<User> users = userMapper.selectList(null);
    users.forEach(System.out::println);
  }


  //mp实现复杂查询操作
  @Test
  public void testSelectQuery() {
    //创建QueryWrapper对象
    QueryWrapper<User> wrapper = new QueryWrapper<>();

    //通过QueryWrapper设置条件
    //ge、gt、le、lt
    //查询age>=30记录
    //第一个参数字段名称，第二个参数设置值
//        wrapper.ge("age",30);

    //eq、ne
    //wrapper.eq("name","lilei");
    //wrapper.ne("name","lilei");

    //between
    //查询年龄 20-30
    // wrapper.between("age",20,30);

    //like
    //wrapper.like("name","岳");

    //orderByDesc
    // wrapper.orderByDesc("id");

    //last
    //wrapper.last("limit 1");

    //指定要查询的列
    wrapper.select("id","name");

    List<User> users = userMapper.selectList(wrapper);
    System.out.println(users);

  }





}
