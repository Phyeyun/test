package kr.co.dong.gogo.repository;

import kr.co.dong.gogo.domain.Board;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import kr.co.dong.gogo.repository.search.BoardSearch;

public interface BoardRepository extends JpaRepository<Board, Long>, BoardSearch{

    @Query(value = "select  now()", nativeQuery = true)
    String getTime();
}
