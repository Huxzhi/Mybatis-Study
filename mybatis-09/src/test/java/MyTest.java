import com.kuang.dao.UserMapper;
import com.kuang.pojo.User;
import com.kuang.utils.MybatisUtils;
import org.apache.ibatis.session.SqlSession;
import org.junit.Test;

public class MyTest {


    @Test
    public void test() {
        SqlSession sqlSession = MybatisUtils.getSqlSession();
        UserMapper mapper = sqlSession.getMapper(UserMapper.class);
        User user = mapper.queryUsersById(1);

        System.out.println(user);

        //mapper.updateUser(new User(2, "aaaaaa", "222222"));
        sqlSession.clearCache();//手动清理缓存

        System.out.println("====================");
        User user2 = mapper.queryUsersById(1);

        System.out.println(user == user2);
        sqlSession.close();
    }

    @Test
    public void test2() {
        SqlSession sqlSession = MybatisUtils.getSqlSession();
        SqlSession sqlSession2 = MybatisUtils.getSqlSession();

        UserMapper mapper = sqlSession.getMapper(UserMapper.class);
        User user = mapper.queryUsersById(1);
        System.out.println(user);
        sqlSession.close();
        
        UserMapper mapper2 = sqlSession2.getMapper(UserMapper.class);
        User user2 = mapper2.queryUsersById(1);
        System.out.println(user);
        sqlSession.close();

        System.out.println(user == user2);

    }
}
