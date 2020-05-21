package com.arcplus.fm.entity.vo;

import lombok.Data;

@Data
public class UserLiteDto {
  private long id;
  private String username;
  private String nickname;
  private int jobType;
}
