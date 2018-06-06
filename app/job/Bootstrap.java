package job;

import models.*;
import play.db.jpa.JPA;
import play.jobs.*;
import play.test.*;

@OnApplicationStart
public class Bootstrap extends Job{

    public void doJob(){
//        JPA.createEntityManager();
//        System.out.println(JPA.isInitialized());
//        JPA.startTx(JPA.DEFAULT,false);
////        //检查数据库是否为空
//        if(User.count() ==0){
//            Fixtures.load("data.yml");
//        }
//        JPA.closeTx(JPA.DEFAULT);
    }

}