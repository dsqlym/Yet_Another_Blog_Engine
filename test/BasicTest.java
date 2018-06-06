import org.junit.*;
import java.util.*;
import play.test.*;
import models.*;

public class BasicTest extends UnitTest {

   /* @Test
    public void aVeryImportantThingToTest() {
        assertEquals(2, 1 + 1);
    }*/

    //前置执行
    @Before
    public void setup() {
        //工具类
        Fixtures.deleteAll();
    }

    @Test
    public void createAndRetriveUser(){
        //创建新用户并保存
        new User("demo@oopsplay.org","123","savage").save();

        //使用邮箱地址读取用户数据
        User savage=User.find("byEmail","demo@oopsplay.org").first();

        //测试
        assertNotNull(savage);
        assertEquals("savage",savage.fullname);

    }

    @Test
    public void tryConnectAsUser(){

        new User("demo@oopsplay.org","123","savage").save();

        assertNotNull(User.connect("demo@oopsplay.org", "123"));
        assertNull(User.connect("dhl@oopsplay.org", "123"));
        assertNull(User.connect("demo@oopsplay.org", "456"));

    }

    @Test
    public void createPost(){

        //创建并保存用户
        User savage=new User("demo2@oopsplay.org","123","savage").save();

        //发表新的文章
        new Post(savage,"Title","Hello savage").save();

        //测试文章已经成功发表
        assertEquals(1,Post.count());

        //读取savage发布的所有文章
        List<Post> savagePost=Post.find("byAuthor",savage).fetch();

        //测试
        assertEquals(1,savagePost.size());
        Post firstPost=savagePost.get(0);
        assertNotNull(firstPost);
        assertEquals(savage,firstPost.author);
        assertEquals("Title",firstPost.title);
        assertEquals("Hello savage",firstPost.content);
        assertNotNull(firstPost.postedAt);
    }

    @Test
    public void postComment(){
        //创建用户并保存
        User savage=new User("demo3@ooposplay.org","123","savage").save();

        //发表一篇文章
        Post savagePost=new Post(savage,"Title2","Hello savage2").save();

        //发表评论
        new Comment(savagePost,"dhlzj","Hi i am dhl").save();
        new Comment(savagePost,"dhlzj2","Hi i am dhl2").save();

        //读取所有评论
        List<Comment> savagePostComments=Comment.find("byPost",savagePost).fetch();

        //测试
        assertEquals(2,savagePostComments.size());

        Comment firstComment=savagePostComments.get(0);
        assertNotNull(firstComment);
        assertEquals("dhlzj",firstComment.author);
        assertEquals("Hi i am dhl",firstComment.content);
        assertNotNull(firstComment.postedAt);

        Comment secondComment=savagePostComments.get(1);
        assertNotNull(secondComment);
        assertEquals("dhlzj2",secondComment.author);
        assertEquals("Hi i am dhl2",secondComment.content);
        assertNotNull(secondComment.postedAt);
    }

    @Test
    public void useTheCommentsRelation(){
        //创建用户并保存
        User savage=new User("demo4@oopsplay.org","123","savage").save();

        //发布一篇文章
        Post savagePost=new Post(savage,"Title3","Hi i am dhl3").save();

        //添加评论
        savagePost.addComment("dhl01", "good job");
        savagePost.addComment("dhl02", "great");

        //统计
        assertEquals(1,User.count());
        assertEquals(1,Post.count());
        assertEquals(2,Comment.count());

        //读取savage发表的文章
        savagePost=Post.find("byAuthor",savage).first();
        assertNotNull(savagePost);

        //导航到评论
    //    assertEquals(2,savagePost.comments.size());
    //    assertEquals("dhl01",savagePost.comments.get(0).author);

        //删除文章
        savagePost.delete();

        //检查所有评论都已经删除
        assertEquals(1,User.count());
        assertEquals(0,Post.count());
        assertEquals(0,Comment.count());
    }


    @Test
    public void fullTest(){

        Fixtures.load("data.yml");

        //计数
        assertEquals(2,User.count());
        assertEquals(3,Post.count());
        assertEquals(3,Comment.count());

        //连接到用户
        assertNotNull(User.connect("demo@oopsplay.org", "123"));
        assertNotNull(User.connect("demo2@oopsplay.org","123"));
        assertNull(User.connect("demo@oopsplay.org", "456"));
        assertNull(User.connect("dhl@oopsplay.org", "123"));

        //查找所有savage发表的文章
        List<Post> savagePosts=Post.find("author.email","demo@oopsplay.org").fetch();
        assertEquals(1,savagePosts.size());

        //查找所有savage发表文章中的评论
        List<Comment> savageComments=Comment.find("post.author.email","demo@oopsplay.org").fetch();
        assertEquals(2,savageComments.size());

        //查找最新发表的文章
        Post frontPost=Post.find("order by postedAt desc").first();
        assertNotNull(frontPost);
    //    assertEquals("The MVC application",frontPost.title);

        //检查该文章下没有评论
//        assertEquals(2,frontPost.comments.size());

        //发表一条新的评论
        frontPost.addComment("dhlzj", "come on,savage");
//        assertEquals(3,frontPost.comments .size());
        assertEquals(4,Comment.count());

    }

}
