package kr.co.dong.gogo.repository.search;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.jpa.JPQLQuery;
import kr.co.dong.gogo.domain.Board;
import kr.co.dong.gogo.domain.QBoard;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;

import java.util.List;

public class BoardSearchImpl extends QuerydslRepositorySupport implements BoardSearch {

    public BoardSearchImpl(){
        super(Board.class);
    }

    @Override
    public Page<Board> search1(Pageable pageable) {

        //Q도메인 객체
        QBoard board = QBoard.board;

        JPQLQuery<Board> query = from(board);

        query.where(board.title.contains("1"));

        //paging
        this.getQuerydsl().applyPagination(pageable, query);

        //Query 실행 - fetch()
        List<Board> list = query.fetch();

        //countQuery 실행 - fetchCount()
        long count = query.fetchCount();

        return null;
    }

    @Override
    public Page<Board> searchAll(String[] types, String keyword, Pageable pageable) {

        QBoard board = QBoard.board;
        JPQLQuery<Board> query = from(board);

        if( (types != null && types.length > 0 ) && keyword != null){
            BooleanBuilder booleanBuilder = new BooleanBuilder();

            for(String type : types){

                switch (type){
                    case "t" :
                        booleanBuilder.or(board.title.contains(keyword));
                    case "c" :
                        booleanBuilder.or(board.content.contains(keyword));
                    case "w" :
                        booleanBuilder.or(board.writer.contains(keyword));
                        break;
                }
            }
            query.where(booleanBuilder);
        }

        //bno > 0
        query.where(board.bno.gt(0L));

        this.getQuerydsl().applyPagination(pageable, query);

        List<Board> list = query.fetch();

        long count = query.fetchCount();

        return null;
    }
}
