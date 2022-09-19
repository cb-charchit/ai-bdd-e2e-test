package com.cbee;

public interface Authentication {
    Token getTheToken(String integ_name);
    void refreshTheToken();
}
