package com.arcplus.fm.entity.dto;

import java.io.Serializable;
import lombok.Data;

@Data
public class LoginUserDto implements Serializable {

  private static final long serialVersionUID = 611197991672067628L;

  private String username;
  private String password;
  private String merchantCode;
}
