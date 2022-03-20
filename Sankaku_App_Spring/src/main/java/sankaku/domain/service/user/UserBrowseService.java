package sankaku.domain.service.user;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import sankaku.domain.model.User;
import sankaku.domain.repository.user.UserRepository;

@Service
public class UserBrowseService {

	@Autowired
	UserRepository userRepository;

	@Transactional
	@PreAuthorize("hasRole('ADMIN')")
	public List<User> userFindAll() {

		return userRepository.findAll();
	}
}
