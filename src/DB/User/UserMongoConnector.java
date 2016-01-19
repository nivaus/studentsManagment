package DB.User;

import com.mongodb.MongoClient;
import org.mongodb.morphia.Datastore;
import org.mongodb.morphia.Morphia;

/**
 * Created by oslander on 18/01/2016.
 */
public class UserMongoConnector {

    private MongoClient mongoClient;
    private Morphia morphia;
    private Datastore ds;

    public UserMongoConnector() {

        this.mongoClient = new MongoClient();
        this.morphia = new Morphia();
        this.morphia.map(User.class);
        this.ds = morphia.createDatastore(mongoClient, "studentsDB");
    }

    public User getUserFromMongoByUsername(String username) {
        User user = ds.get(User.class, username);
        return user;
    }
}