package com.kuang.dao;

import com.kuang.pojo.User;
import com.kuang.utils.MybatisUtils;
import org.apache.ibatis.session.SqlSession;
import org.junit.Test;

import java.util.List;

public class UserDaoTest {
    @Test
    public void test() {
        //第一步：获取SqlSession对象
        SqlSession sqlSession = MybatisUtils.getSqlSession();
        try {
            //方式一：getMapper
            UserMapper userMapper = sqlSession.getMapper(UserMapper.class);
            List<User> userList = userMapper.getUserList();

            //方拾二：
            //List<User> userList1=sqlSession.selectList("com.kuang.dao.UserDao.getUserList");

            for (User user : userList) {
                System.out.println(user);
            }

        } catch (Exception e) {
            //建议使用finally确保关闭连接，但事实上不会抓取到错误，所以没什么用
            e.printStackTrace();
        } finally {
            //关闭SqlSession
            sqlSession.close();
        }


    }

    @Test
    public void getUserById() {
        SqlSession sqlSession = MybatisUtils.getSqlSession();

        UserMapper userMapper = sqlSession.getMapper(UserMapper.class);
        User userById = userMapper.getUserById(1);
        System.out.println(userById);

        sqlSession.close();
    }

    @Test
    public void addUser() {
        SqlSession sqlSession = MybatisUtils.getSqlSession();
        UserMapper mapper = sqlSession.getMapper(UserMapper.class);
        int res = mapper.addUser(new User(5, "小米", "123233"));
        if (res > 0) {
            System.out.println("插入成功！");
        }
        //增删改一定要 提交事务
        sqlSession.commit();
        sqlSession.close();
    }

    @Test
    public void updateUser() {
        SqlSession sqlSession = MybatisUtils.getSqlSession();
        UserMapper mapper = sqlSession.getMapper(UserMapper.class);
        int res = mapper.updateUser(new User(4, "呵呵", "123233"));
        if (res > 0) {
            System.out.println("修改成功！");
        }
        //增删改一定要 提交事务
        sqlSession.commit();
        sqlSession.close();
    }

    @Test
    public void deleteUser() {
        SqlSession sqlSession = MybatisUtils.getSqlSession();
        UserMapper mapper = sqlSession.getMapper(UserMapper.class);
        int res = mapper.deleteUser(5);
        if (res > 0) {
            System.out.println("删除成功！");
        }
        //增删改一定要 提交事务
        sqlSession.commit();
        sqlSession.close();
    }
}
