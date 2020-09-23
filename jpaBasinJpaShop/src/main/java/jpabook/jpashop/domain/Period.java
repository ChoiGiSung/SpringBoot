package jpabook.jpashop.domain;

import javax.persistence.Embeddable;
import java.time.LocalDateTime;

//값 타입을 정의하는곳
@Embeddable
//기본 생성자는 있어야함
public class Period {
    private LocalDateTime startDate;
    private LocalDateTime endDate;

    public Period() {
    }

    //여기서 함수를 만들어서 뭐 활용 가능
    public LocalDateTime getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDateTime startDate) {
        this.startDate = startDate;
    }

    public LocalDateTime getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDateTime endDate) {
        this.endDate = endDate;
    }
}
