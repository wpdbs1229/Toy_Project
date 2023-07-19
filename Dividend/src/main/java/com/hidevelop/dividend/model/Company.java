package com.hidevelop.dividend.model;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data // getter setter tostring Requir 생성자, //디자인 패턴중 빌더 패턴을 사용하게 해줌
@AllArgsConstructor
@NoArgsConstructor
public class Company {

    private String ticker;
    private String name;

}
