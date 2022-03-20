package sankaku.app.user;

import java.io.Serializable;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import lombok.Data;

@Data
public class UserForm implements Serializable {

	@NotBlank
	@Size(min=0, max=20 )
	private String userId;

	@NotBlank
	private String name;

	@NotBlank
	private String password;

	@NotBlank
	@Email(message = "Invalid E-mail Format")
	private String email;


	//コンストラクタ
	public UserForm() {
	}

	public UserForm(String userId, String name, String password, String email) {
		super();
		this.userId = userId;
		this.name = name;
		this.password = password;
		this.email = email;
	}




}
