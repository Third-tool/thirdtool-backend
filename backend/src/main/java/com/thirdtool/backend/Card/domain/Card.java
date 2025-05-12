package com.thirdtool.backend.Card.domain;

import com.thirdtool.backend.Deck.domain.Deck;
import com.thirdtool.backend.Note.domain.Note;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.time.Instant;

@Getter
@RequiredArgsConstructor
@Builder
@Entity
public class Card {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 1000)
    private String content;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "note_id")
    private Note note;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "deck_id")
    private Deck deck;

    @Column(name = "due_date")
    private Instant dueDate;


    private int intervalDays; //다음 복습까지 간격을 알려주는데 사용
    private boolean isArchived;
    private int successCount;
    //복습을 성공하든 실패하든 복습한 횟수
    private int reps;
    private int easeFactor;
    //📌 "카드를 외우는 데 실패한 횟수"
    private int lapses;



    // ====== 학습 로직 ======

    public void extendInterval(int multiplier) {
        this.intervalDays = intervalDays * multiplier;
        this.dueDate = Instant.now().plusSeconds(intervalDays * 86400L);
    }
    // permanent로 보내기 직전
    public boolean isOverOneMonth() {
        return intervalDays > 30;
    }

    public void archive() {
        this.isArchived = true;
        this.dueDate = null;
        this.intervalDays = 0;
    }

    //3day에서 제공하는 4개의 블록 중 하나-> 3day로 복귀하자 permanent용
    public void restoreToGeneralStudy() {
        this.isArchived = false;
        this.intervalDays = 1;
        this.dueDate = Instant.now().plusSeconds(86400L);
    }
    //3day에서 제공하는 4개의 블록 중 하나
    public void successReview() {
        this.reps += 1;
        this.successCount += 1;

        if (successCount == 1) intervalDays += 1;
        else if (successCount == 2) intervalDays += 2;
        else if (successCount == 3) intervalDays += 3;
        else intervalDays *= 2;

        this.dueDate = Instant.now().plusSeconds(intervalDays * 86400L);

        if (intervalDays > 30) {
            archive(); // 영구 저장소로 이동
        }
    }



}
