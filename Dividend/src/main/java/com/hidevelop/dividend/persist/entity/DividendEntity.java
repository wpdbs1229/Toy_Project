package com.hidevelop.dividend.persist.entity;

import com.hidevelop.dividend.model.Dividend;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity(name = "DIVIDEND")
@Getter
@ToString
@NoArgsConstructor
@Table(
        //복합 유니크 키 설정 ( 중복 데이터 저장을 방지하는 제약조건, 복합컬럼을 지정할 수 있음
        uniqueConstraints = {
                @UniqueConstraint(
                        columnNames = {"companyId", "date"}
                )
        }
)
public class DividendEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long companyId;

    private LocalDateTime date;

    private String dividend;

    public DividendEntity(Long companyId, Dividend dividend){
        this.companyId = companyId;
        this.date = dividend.getDate();
        this.dividend =dividend.getDividend();
    }
}
