package sankaku.domain.model;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import lombok.Data;

@Entity
@Table(name="user")
@Data
public class User implements Serializable{

	@Id
	@Column(name="user_id")
	private String userId;

	@Enumerated(EnumType.STRING)
	@Column(name="authority")
	private Authority authority;

	@Column(name="user_name")
	private String userName;

	@Column(name="password")
	private String password;

	@Column(name="email")
	private String email;

    @OneToMany(mappedBy = "user")
    protected List<Schedule> schedules;

}
