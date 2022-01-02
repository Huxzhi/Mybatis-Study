import com.kuang.dao.UserMapper;
import com.kuang.pojo.User;
import com.kuang.utils.MybatisUtils;
import org.apache.ibatis.session.SqlSession;
import org.junit.Test;

import java.util.List;

public class UserMapperTest {
    @Test
    public void getUsers() {
        SqlSession sqlSession = MybatisUtils.getSqlSession();

        //通过反射获取注解值
        UserMapper mapper = sqlSession.getMapper(UserMapper.class);
        List<User> users = mapper.getUsers();
        for (User user :
                users) {
            System.out.println(user);
        }
        sqlSession.close();
    }

    @Test
    public void getUserByID() {
        SqlSession sqlSession = MybatisUtils.getSqlSession();

        //通过反射获取注解值
        UserMapper mapper = sqlSession.getMapper(UserMapper.class);
        User user = mapper.getUserByID(1);
        System.out.println(user);

        sqlSession.close();
    }

    @Test
    public void addUser() {
        SqlSession sqlSession = MybatisUtils.getSqlSession();

        //通过反射获取注解值
        UserMapper mapper = sqlSession.getMapper(UserMapper.class);
        int row = mapper.addUser(new User(6, "xiaoming", "123456"));
        System.out.println(row);

        sqlSession.close();
    }

    @Test
    public void updateUser() {
        SqlSession sqlSession = MybatisUtils.getSqlSession();

        //通过反射获取注解值
        UserMapper mapper = sqlSession.getMapper(UserMapper.class);
        int row = mapper.updateUser(new User(6, "to", "123456"));
        System.out.println(row);

        sqlSession.close();
    }

    @Test
    public void deleteUser() {
        SqlSession sqlSession = MybatisUtils.getSqlSession();

        //通过反射获取注解值
        UserMapper mapper = sqlSession.getMapper(UserMapper.class);
        int row = mapper.deleteUser(6);
        System.out.println(row);

        sqlSession.close();
    }
}