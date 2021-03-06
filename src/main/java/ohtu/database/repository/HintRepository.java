package ohtu.database.repository;

import java.util.List;
import java.util.Set;

import ohtu.model.Hint;
import ohtu.model.Tag;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;


@Component
public interface HintRepository extends JpaRepository<Hint, Long> {
	
	public Page<Hint> findAllByOrderByIdDesc(Pageable pageable);

	public Page<Hint> findByIsReadOrderByIdDesc(Boolean isRead, Pageable pageable);
	
	public Page<Hint> findDistinctByTagsInOrderByIdDesc(Set<Tag> tags, Pageable pageable);
	
	public Page<Hint> findDistinctByIsReadAndTagsInOrderByIdDesc(Boolean isRead, Set<Tag> tags, Pageable pageable);

	public List<Hint> findByIsRead(Boolean isRead);

	
}
