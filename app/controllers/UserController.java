package controllers;

import models.Post;
import models.User;
import play.Play;
import play.data.validation.Required;
import play.db.jpa.JPABase;
import play.mvc.Before;
import play.mvc.Controller;

import java.util.List;

public class UserController extends Controller {

    @Before
    static void addDefaults() {
        renderArgs.put("blogTitle", Play.configuration.getProperty("blog.title"));
        renderArgs.put("blogBaseline", Play.configuration.getProperty("blog.baseline"));
    }

    public static void add( User user) {
        JPABase base = user.save();
        renderJSON(base);
    }

    public static void update( User user) {
        //renderJSON(base);
    }

    public static void delete( User user) {
        JPABase base = user.delete();
        renderJSON(base);
    }

    public static void query(@Required Long id) {
        User user = User.findById(id);
        renderJSON(user);
    }

    public static void list(String usernmae) {
        List<JPABase> all = User.findAll();
        renderJSON(all);
    }


}