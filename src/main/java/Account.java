import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="accounts")
public class Account {

    @Id
    @GeneratedValue
    private Integer id;
    private Integer clientId;

    // TODO исправить на бигдецимал, или что-то более подходящее для денег
    private Integer ammount;

}
