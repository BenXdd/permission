package com.mmall.beans;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.util.Set;

@Getter
@Setter
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Mail {

    //邮件的主题
    private String subject;

    //邮件的信息
    private String message;

    //收件人的邮箱, 可能是多个
    private Set<String> receivers;
}
