package DB.Student;

import com.mongodb.MongoClient;
import org.mongodb.morphia.Datastore;
import org.mongodb.morphia.Morphia;

import java.util.List;

/**
 * Created by oslander on 18/01/2016.
 */
public class StudentMongoConnector {
    private MongoClient mongoClient;
    private Morphia morphia;
    private Datastore ds;

    public StudentMongoConnector() {
        this.mongoClient = new MongoClient();
        this.morphia = new Morphia();
        this.morphia.map(Student.class);
        this.ds = morphia.createDatastore(mongoClient,"studentsDB");
    }

    public Student getStudentFromMongoById(String username) {
        Student student= this.ds.get(Student.class, username);
        return student;
    }

    public List<Student> getAllStudentsFromMongo()
    {
        return this.ds.find(Student.class).asList();
    }

    public void insertOrUpdateStudentInMongo(Student student)
    {
        ds.save(student);
    }

    public void deleteStudentFromMongo (String studentId)
    {
        ds.delete(Student.class, studentId);
    }
}
