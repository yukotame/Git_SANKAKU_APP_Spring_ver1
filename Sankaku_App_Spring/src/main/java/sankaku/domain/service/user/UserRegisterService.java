package sankaku.domain.service.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import sankaku.domain.model.User;
import sankaku.domain.repository.user.UserRepository;

@Service
public class UserRegisterService {

	@Autowired
	UserRepository userRepository;

	@Transactional
	public User register(User user) {

		return userRepository.save(user);
	}
}
