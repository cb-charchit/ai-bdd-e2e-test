package com.cbee;

public interface Authentication {
    Token getToken(String integ_name);
    void refreshTheToken();
}
