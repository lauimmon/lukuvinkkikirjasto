package ohtu.database.repository;

import ohtu.model.Hint;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;


@Component
public interface HintRepository extends JpaRepository<Hint, Long> {
	
	public Page<Hint> findAllByOrderByIdDesc(Pageable pageable);

	public Page<Hint> findByIsReadOrderByIdDesc(Boolean isRead, Pageable pageable);

	public List<Hint> findByIsRead(Boolean isRead);

	
}
