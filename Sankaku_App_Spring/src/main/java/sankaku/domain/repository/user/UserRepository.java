package sankaku.domain.repository.user;

import org.springframework.data.jpa.repository.JpaRepository;

import sankaku.domain.model.User;

public interface UserRepository extends JpaRepository<User, String> {


}
