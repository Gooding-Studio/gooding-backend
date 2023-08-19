package com.dnd.gooding.domain.record.repository;

import com.dnd.gooding.domain.record.model.Record;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;

import javax.persistence.EntityManager;
import java.util.List;
import java.util.Optional;

import static com.dnd.gooding.domain.file.model.QFile.file;
import static com.dnd.gooding.domain.record.model.QRecord.record;
import static com.dnd.gooding.domain.user.model.QUser.user;

public class RecordRepositoryImpl implements RecordRepositoryCustom {

    private final EntityManager em;
    private final JPAQueryFactory queryFactory;

    public RecordRepositoryImpl(EntityManager em) {
        this.em = em;
        this.queryFactory = new JPAQueryFactory(em);
    }

    @Override
    public Optional<List<Record>> findByUserId(Long id) {
        return Optional.ofNullable(queryFactory
                .select(record).distinct()
                .from(record)
                .join(record.files, file).fetchJoin()
                .where(userIdEquals(id))
                .fetch());
    }

    @Override
    public Record findByRecordId(Long recordId) {
        return queryFactory
                .select(record).distinct()
                .from(record)
                .join(record.files, file).fetchJoin()
                .where(recordIdEquals(recordId))
                .fetchOne();
    }

    @Override
    public void thumbnailUpdate(Long recordId, String thumbnailUrl) {
        Long count = queryFactory
                .update(record)
                .set(record.thumbnailUrl, thumbnailUrl)
                .where(recordIdEquals(recordId))
                .execute();
        em.flush();
        em.clear();
    }

    private BooleanExpression recordIdEquals(Long recordId) {
        return record.id.eq(recordId);
    }

    private BooleanExpression userIdEquals(Long id) {
        return record.user.id.eq(id);
    }
}